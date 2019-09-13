package desi.juan.service;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import desi.juan.Application;
import desi.juan.model.DefaultGame;
import desi.juan.model.Game;
import desi.juan.model.GameFactory;
import desi.juan.model.Level;
import desi.juan.model.Position;
import desi.juan.persistence.GameSerializer;
import desi.juan.persistence.MongoAdapter;
import desi.juan.service.dto.MessageDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service {

  private static final MongoAdapter db = new MongoAdapter();
  private static final GameFactory factory = new GameFactory();
  private static final GameSerializer serializer = new GameSerializer(true);

  private final Map<Integer, Game> games = new ConcurrentHashMap<>();

  @RequestMapping(value = "/games", method = GET, produces = "application/json")
  public ResponseEntity<Object> getGames() {
    return ResponseEntity.ok(serializer.serialize(db.getAllGames()));
  }

  @RequestMapping(value = "/games", method = POST, produces = "application/json")
  public String newGame(@RequestParam(required = false) Level level) {
    Integer id = db.getNextId();
    DefaultGame game = factory.create(id, level == null ? Level.EASY : level);
    games.put(id, game);
    db.saveGame(game);
    return "{ \"id\": " + id.toString() + " }";
  }

  @RequestMapping(value = "/games/{id}", method = GET, produces = "application/json")
  public ResponseEntity<Object> getGame(@PathVariable Integer id) {
    Optional<Game> result = getOptionalGame(id);
    return result.isPresent() ? ResponseEntity.ok(serializer.serialize(result.get())) : ResponseEntity.notFound().build();
  }

  @RequestMapping(value = "/games/{id}/view", method = GET)
  public ResponseEntity<String> getGameView(@PathVariable Integer id) {
    Optional<Game> result = getOptionalGame(id);
    return result.isPresent() ? ResponseEntity.ok(result.get().print()) : ResponseEntity.notFound().build();
  }

  @RequestMapping(value = "/games/{id}/reveal", method = PUT, produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> reveal(@PathVariable Integer id, @RequestBody Position position) {
    Optional<Game> game = getOptionalGame(id);
    if (!game.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    if (!game.get().isValidPosition(position)) {
      return ResponseEntity.badRequest().body(new MessageDTO("There is no cell at " + position.toString()));
    }
    Game result = game.get().revealCell(position.getX(), position.getY());
    if (result == game.get()) {
      return ResponseEntity.ok(new MessageDTO("Cell at " + position + " is already revealed"));
    }
    games.put(id, result);
    db.updateGame(result);
    if (result.isFinished()) {
      if (result.isSolved()) {
        return ResponseEntity.ok(new MessageDTO("You won the game!"));
      }
      return ResponseEntity.ok(new MessageDTO("Cell at " + position + " has exploded, You lose. Game is finished"));
    }
    return ResponseEntity.ok(new MessageDTO("Cell at " + position + " has been revealed successfully"));
  }

  private Optional<Game> getOptionalGame(Integer id) {
    if (!games.containsKey(id)) {
      Optional<Game> game = db.retrieveGame(id);
      if (game.isPresent()) {
        games.put(id, game.get());
      } else {
        return Optional.empty();
      }
    }
    return Optional.of(games.get(id));
  }
}
