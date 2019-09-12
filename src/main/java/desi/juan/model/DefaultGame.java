package desi.juan.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import desi.juan.model.cell.Cell;

public class DefaultGame implements Game {

  private final String id;
  private final Cell[][] grid;

  public DefaultGame(String id, Cell[][] grid) {
    this.id = id;
    this.grid = grid;
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

  @Override
  public Cell getCell(int x, int y) {
    return grid[x][y];
  }

  @Override
  public boolean isValidPosition(Position position) {
    Integer x = position.getX();
    Integer y = position.getY();
    return x >= 0 && x < grid.length && y < grid[0].length && y >= 0;
  }

  @Override
  public List<Cell> getAllCells() {
    List<Cell> cells = new ArrayList<>();
    for (Cell[] rows : grid) {
      cells.addAll(Arrays.asList(rows));
    }
    return cells;
  }

  @Override
  public boolean isSolved() {
    return getAllCells().stream().noneMatch(Cell::isHidden);
  }

  @Override
  public Cell[][] getGrid() {
    return grid.clone();
  }

  @Override
  public String print() {
    StringBuilder builder = new StringBuilder();
    for (int x = 0; x < grid.length; x++) {
      builder.append("|");
      for (int y = 0; y < grid[x].length; y++) {
        Cell cell = grid[x][y];
        builder.append(cell.print()).append("|");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  @Override
  public String getId() {
    return id;
  }
}
