package desi.juan.persistence;

import java.util.Optional;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoAdapter {

  private final MongoClient client;

  public MongoAdapter() {
    String connectionString = System.getProperty("mongo.juan.desi");
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

  public void saveGame(String game) {
    MongoCollection<Document> games = getGames();
    games.insertOne(Document.parse(game));
  }

  public Optional<String> retrieveGame(Integer id) {
    Document found = getGames().find(new BasicDBObject("id", id)).first();
    if (found == null) {
      return Optional.empty();
    }
    return Optional.of(found.toJson());
  }

  private MongoCollection<Document> getGames() {
    return client.getDatabase("buscaminas").getCollection("games");
  }
}
