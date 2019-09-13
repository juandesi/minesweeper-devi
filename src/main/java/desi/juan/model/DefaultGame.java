package desi.juan.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import desi.juan.model.cell.Cell;
import desi.juan.model.error.RevealedMineException;

public class DefaultGame extends Game {

  public DefaultGame(int id, Cell[][] grid) {
    super(id, grid);
  }

  @Override
  public Game revealCell(int x, int y) {
    try {
      DefaultGame result = grid[x][y].reveal(this);
      if (result.isSolved()) {
        // do something when the game is finished!
        return new FinishedGame(result);
      }
      return result;
    } catch (RevealedMineException e) {
      return new FinishedGame(this);
    }
  }

  @Override
  public boolean isFinished() {
    return isSolved();
  }

}
