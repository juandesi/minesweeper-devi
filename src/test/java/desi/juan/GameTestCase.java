package desi.juan;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import desi.juan.model.DefaultGame;
import desi.juan.model.Game;
import desi.juan.model.Position;
import desi.juan.model.cell.AloneCell;
import desi.juan.model.cell.Cell;
import desi.juan.model.cell.Mine;
import desi.juan.model.cell.MineAdjacentCell;
import desi.juan.model.cell.RevealedCell;
import desi.juan.model.error.RevealedMineException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GameTestCase {

  private static final String id = "noId";

  @Rule
  public ExpectedException expectedException = ExpectedException.none();


  @Test
  public void hitAloneCell() {
    Cell[][] testGrid = createTestGrid();
    testGrid[0][0] = new Mine(new Position(1, 2));
    testGrid[1][1] = new MineAdjacentCell(((byte) 1), new Position(1, 1));
    testGrid[1][0] = new MineAdjacentCell(((byte) 1), new Position(1, 0));
    testGrid[0][1] = new MineAdjacentCell(((byte) 1), new Position(0, 1));

    DefaultGame game = new DefaultGame(id, testGrid);
    Game newGameStatus = game.revealCell(5, 5);

    assertRevealedCell(newGameStatus.getCell(5, 5), " ");
    assertRevealedCell(newGameStatus.getCell(1, 1), "1");
    assertRevealedCell(newGameStatus.getCell(1, 0), "1");
    assertRevealedCell(newGameStatus.getCell(0, 1), "1");
    assertThat(newGameStatus.print(), is("|□|1| | | | | | |\n"
                                       + "|1|1| | | | | | |\n"
                                       + "| | | | | | | | |\n"
                                       + "| | | | | | | | |\n"
                                       + "| | | | | | | | |\n"
                                       + "| | | | | | | | |\n"
                                       + "| | | | | | | | |\n"
                                       + "| | | | | | | | |\n"));
}

  @Test
  public void hitMine() {
    expectedException.expect(RevealedMineException.class);

    Cell[][] testGrid = createTestGrid();
    testGrid[5][5] = new Mine(new Position(5, 5));
    new DefaultGame(id, testGrid).revealCell(5, 5);
  }

  @Test
  public void hitCellWith2AdjacentMines() {
    Cell[][] testGrid = createTestGrid();
    testGrid[0][0] = new Mine(new Position(1, 2));
    testGrid[1][1] = new MineAdjacentCell(((byte) 1), new Position(1, 1));
    testGrid[1][0] = new MineAdjacentCell(((byte) 1), new Position(1, 0));
    testGrid[0][1] = new MineAdjacentCell(((byte) 1), new Position(0, 1));

    DefaultGame game = new DefaultGame(id, testGrid);
    Game newGameStatus = game.revealCell(1, 1);

    assertRevealedCell(newGameStatus.getCell(1, 1), "1");
    assertThat(newGameStatus.print(), is("|□|□|□|□|□|□|□|□|\n"
                                       + "|□|1|□|□|□|□|□|□|\n"
                                       + "|□|□|□|□|□|□|□|□|\n"
                                       + "|□|□|□|□|□|□|□|□|\n"
                                       + "|□|□|□|□|□|□|□|□|\n"
                                       + "|□|□|□|□|□|□|□|□|\n"
                                       + "|□|□|□|□|□|□|□|□|\n"
                                       + "|□|□|□|□|□|□|□|□|\n"));
  }

  private Cell[][] createTestGrid() {
    Cell[][] grid = new Cell[8][8];
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        grid[x][y] = new AloneCell(new Position(x, y));
      }
    }
    return grid;
  }

  private void assertRevealedCell(Cell cell, String symbol) {
    assertThat(cell, is(instanceOf(RevealedCell.class)));
    assertThat(cell.getSymbol(), is(symbol));
  }
}
