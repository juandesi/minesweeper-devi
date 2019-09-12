package desi.juan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import desi.juan.model.DefaultGame;
import desi.juan.model.FinishedGame;
import desi.juan.model.Game;
import desi.juan.model.cell.AloneCell;
import desi.juan.model.cell.Cell;
import desi.juan.model.cell.Mine;
import desi.juan.model.cell.MineAdjacentCell;
import desi.juan.model.cell.RevealedCell;

/**
 * A simple GSON based serializer
 */
public class GameSerializer {

  private static final RuntimeTypeAdapterFactory<Game> GAME_ADAPTER = RuntimeTypeAdapterFactory.of(Game.class, "status")
    .registerSubtype(DefaultGame.class, "started")
    .registerSubtype(FinishedGame.class, "finished");

  private static final RuntimeTypeAdapterFactory<Cell> CELL_ADAPTER = RuntimeTypeAdapterFactory.of(Cell.class, "type")
    .registerSubtype(AloneCell.class, "alone")
    .registerSubtype(Mine.class, "mine")
    .registerSubtype(RevealedCell.class, "revealed")
    .registerSubtype(MineAdjacentCell.class, "mine-adjacent");

  private final boolean pretty;

  public GameSerializer() {
    this(false);
  }

  public GameSerializer(boolean pretty) {
    this.pretty = pretty;
  }

  public String serialize(Game game) {
    Gson gson = getGson();
    return gson.toJson(game, Game.class);
  }

  public Game deserialize(String content) {
    Gson gson = getGson();
    return gson.fromJson(content, Game.class);
  }

  private Gson getGson() {
    GsonBuilder builder = new GsonBuilder()
      .registerTypeAdapterFactory(CELL_ADAPTER)
      .registerTypeAdapterFactory(GAME_ADAPTER);
    if (pretty) {
      builder.setPrettyPrinting();
    }
    return builder.create();
  }

}
