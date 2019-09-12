package desi.juan.persistence;

import static java.util.Arrays.asList;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import desi.juan.model.Game;
import org.bson.Document;

public class MongoAdapter {

  private final MongoClient client;

  public MongoAdapter() {
    String connectionString = System.getProperty("mongo.juan.desi");
    final MongoClientURI mongoClientURI = new MongoClientURI(connectionString);
    this.client = new MongoClient(mongoClientURI);
  }

  public Integer getNextId() {
    try {
      String max = getGames().aggregate(asList(new Document("$group", new Document("_id", null)
        .append("max", new Document("$max", "$id")))))
        .first().getString("max");
      Integer nextId = Integer.parseInt(max) + 1;
      return nextId;
    } catch (Exception e) {
      // case we started from scratch
      return 0;
    }
  }

  public void saveGame(String game) {
    MongoCollection<Document> games = getGames();
    games.insertOne(Document.parse(game));
  }

  public Game retrieveGame(String id) {
    MongoCollection<Document> games = getGames();
    games.find();
    return null;
  }

  private MongoCollection<Document> getGames() {
    return client.getDatabase("buscaminas").getCollection("games");
  }
}
