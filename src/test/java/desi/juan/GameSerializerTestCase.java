package desi.juan;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


import java.io.IOException;
import java.io.InputStream;

import desi.juan.model.DefaultGame;
import desi.juan.model.FinishedGame;
import desi.juan.model.Game;
import desi.juan.model.Position;
import desi.juan.model.cell.Cell;
import desi.juan.model.cell.Mine;
import desi.juan.model.cell.MineAdjacentCell;
import desi.juan.persistence.GameSerializer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class GameSerializerTestCase {

  private GameSerializer serializer = new GameSerializer(true);
  private Game game = new DefaultGame(1, initCells());
  private Game finished = new FinishedGame(game);

  @Test
  public void serialize() throws IOException {
    String serialized = serializer.serialize(game);
    assertThat(serialized, is(equalTo(getResourceAsString("default.json"))));
  }

  @Test
  public void deserialize() {
    String defaultGame = getResourceAsString("default.json");
    Game deserialized = serializer.deserialize(defaultGame);
    assertThat(deserialized.getId(), equalTo(game.getId()));
    assertThat(deserialized.getCell(1, 1), is(instanceOf(MineAdjacentCell.class)));
    assertThat(deserialized.getCell(1, 0), is(instanceOf(Mine.class)));
  }

  @Test
  public void serializeFinishedGame() {
    String serialized = serializer.serialize(finished);
    assertThat(serialized, is(equalTo(getResourceAsString("finished.json"))));
  }

  @Test
  public void deserializeFinishedGame() {
    String defaultGame = getResourceAsString("finished.json");
    Game deserialized = serializer.deserialize(defaultGame);
    assertThat(deserialized.getId(), equalTo(finished.getId()));
    assertThat(deserialized.getCell(1, 1), is(instanceOf(MineAdjacentCell.class)));
    assertThat(deserialized.getCell(1, 0), is(instanceOf(Mine.class)));
  }

  private String getResourceAsString(String name) {
    try {
      InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
      return IOUtils.toString(stream, UTF_8).trim();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Cell[][] initCells() {
    Cell[][] cells = new Cell[8][8];
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells.length; j++) {
        Position position = new Position(i, j);
        cells[i][j] = i == j ? new MineAdjacentCell(((byte) 6), position) : new Mine(position);
      }
    }
    return cells;
  }
}
