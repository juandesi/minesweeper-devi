package desi.juan.service;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import desi.juan.model.DefaultGame;
import desi.juan.model.Game;
import desi.juan.model.GameFactory;
import desi.juan.model.Level;
import desi.juan.model.Position;
import desi.juan.persistence.MongoAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service {

  private static final MongoAdapter db = new MongoAdapter();
  private static final GameSerializer serializer = new GameSerializer();
  private static final GameFactory factory = new GameFactory();

  private final Map<String, Game> games = new ConcurrentHashMap<>();

  @RequestMapping(value = "/game", method = POST)
  public String newGame(@RequestParam(required = false) Level level) {
    Integer id = db.getNextId();
    DefaultGame game = factory.create(id, level == null ? Level.EASY : level);
    games.put(id.toString(), game);
    db.saveGame(serializer.serialize(game));
    return id.toString();
  }

  @RequestMapping(value = "/game/{id}", method = GET)
  public ResponseEntity<Object> getGame(@PathVariable String id) {
    Game game = games.get(id);
    if (game == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(serializer.serialize(game));
  }

  @RequestMapping(value = "/game/{id}/view", method = GET)
  public ResponseEntity<String> getGameView(@PathVariable String id) {
    Game game = games.get(id);
    if (game == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(game.print());
  }

  @RequestMapping(value = "/game/{id}/reveal", method = PUT)
  public ResponseEntity<String> reveal(@PathVariable String id, @RequestBody Position position) {
    Game game = games.get(id);
    if (game == null) {
      return ResponseEntity.notFound().build();
    }
    if (!game.isValidPosition(position)) {
      return ResponseEntity.badRequest().body("There is no cell at " + position.toString());
    }
    Game result = game.revealCell(position.getX(), position.getY());
    games.put(game.getId() + "", result);
    return ResponseEntity.ok("Cell at " + position + " has been revealed successfully");
  }

  @RequestMapping(value = "/game/{id}/save", method = POST)
  public ResponseEntity<Object> save(@PathVariable String id) {
    Game game = games.get(id);
    if (game == null) {
      return ResponseEntity.notFound().build();
    }
    String serialized = serializer.serialize(game);
    db.saveGame(serialized);
    return ResponseEntity.ok("Game id [" + id + "] saved correctly");
  }
}
