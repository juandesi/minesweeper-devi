package desi.juan.model;

import desi.juan.model.cell.Cell;
import desi.juan.model.cell.RevealedCell;

public class FinishedGame extends Game {

  private final boolean solved;

  public FinishedGame(Game delegate) {
    super(delegate.getId(), revealAll(delegate.getGrid()));
    this.solved = delegate.isSolved();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public Game revealCell(int x, int y) {
    return this;
  }

  @Override
  public boolean isSolved() {
    return solved;
  }

  private static Cell[][] revealAll(Cell[][] grid) {
    Cell[][] target = new Cell[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        target[i][j] = new RevealedCell(grid[i][j]);
      }
    }
    return target;
  }

}
