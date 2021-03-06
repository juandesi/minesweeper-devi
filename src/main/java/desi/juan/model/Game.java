package desi.juan.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import desi.juan.model.cell.Cell;

public abstract class Game {

  protected final int id;
  protected final Cell[][] grid;

  public Game(int id, Cell[][] grid) {
    this.id = id;
    this.grid = grid;
  }

  public abstract Game revealCell(int x, int y);

  public abstract boolean isFinished();

  public Cell getCell(int x, int y) {
    return grid[x][y];
  }

  public boolean isValidPosition(Position position) {
    Integer x = position.getX();
    Integer y = position.getY();
    return x >= 0 && x < grid.length && y < grid[0].length && y >= 0;
  }

  public List<Cell> getAllCells() {
    List<Cell> cells = new ArrayList<>();
    for (Cell[] rows : grid) {
      cells.addAll(Arrays.asList(rows));
    }
    return cells;
  }

  public boolean isSolved() {
    return getAllCells().stream().noneMatch(Cell::isHidden);
  }

  public Cell[][] getGrid() {
    return grid.clone();
  }

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

  public int getId() {
    return id;
  }
}
