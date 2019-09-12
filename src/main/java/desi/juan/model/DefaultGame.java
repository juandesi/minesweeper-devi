package desi.juan.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import desi.juan.model.cell.Cell;

public class DefaultGame extends Game {

  public DefaultGame(String id, Cell[][] grid) {
    super(id, grid);
  }

  @Override
  public Game revealCell(int x, int y) {
    DefaultGame result = grid[x][y].reveal(this);
    if (result.isSolved()) {
      // do something when the game is finished!
      return new FinishedGame(result);
    }
    return result;
  }

  @Override
  public boolean isFinished() {
    return isSolved();
  }

}
