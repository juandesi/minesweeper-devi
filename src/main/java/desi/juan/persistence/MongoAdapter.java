package desi.juan.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import desi.juan.model.Game;
import org.bson.Document;

public class MongoAdapter {

  private static final GameSerializer serializer = new GameSerializer(true);

  private final MongoClient client;

  public MongoAdapter() {
    String connectionString = System.getenv().get("mongo.juan.desi");
    final MongoClientURI mongoClientURI = new MongoClientURI(connectionString);
    this.client = new MongoClient(mongoClientURI);
  }

  public Integer getNextId() {
      Document first = getGames().find(new BasicDBObject()).sort(new BasicDBObject("id", -1)).first();
      if (first == null) {
        return 0;
      }
      Integer max = first.getInteger("id");
      return max + 1;
  }

  public void saveGame(Game game) {
    MongoCollection<Document> games = getGames();
    games.insertOne(Document.parse(serializer.serialize(game)));
  }

  public void updateGame(Game game) {
    DeleteResult result = getGames().deleteOne(new BasicDBObject("id", game.getId()));
    if (result.wasAcknowledged()) {
      saveGame(game);
    }
  }

  public Optional<Game> retrieveGame(Integer id) {
    Document found = getGames().find(new BasicDBObject("id", id)).first();
    if (found == null) {
      return Optional.empty();
    }
    return Optional.of(serializer.deserialize(found.toJson()));
  }

  public List<String> getAllGames() {
    // should be paginated
    List<String> array = new ArrayList<>();
    for (String game : getGames().find().map(e -> e.toJson())) {
      array.add(game);
    }
    return array;
  }

  private MongoCollection<Document> getGames() {
    return client.getDatabase("buscaminas").getCollection("games");
  }
}
