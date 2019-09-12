package desi.juan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import desi.juan.model.cell.AloneCell;
import desi.juan.model.cell.Cell;
import desi.juan.model.cell.Mine;
import desi.juan.model.cell.MineAdjacentCell;

public class GameFactory {

  private static final int GRID_WIDTH = 8;
  private static final int GRID_HEIGHT = 8;
  private static final int MINES_NUM = 10;
  private static final Random RANDOM = new Random();

  public DefaultGame create(String id, Level level) {
    int multiplier = level.getMultiplier();
    int width = GRID_WIDTH * multiplier;
    int height = GRID_HEIGHT * multiplier;
    Cell[][] grid = new Cell[width][height];

    List<Position> mineLocations = getMineLocations(MINES_NUM * multiplier * multiplier, width, height);
    for (Position mine : mineLocations) {
      grid[mine.getX()][mine.getY()] = new Mine(mine);
    }

    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        Position position = new Position(x, y);
        if (mineLocations.stream().noneMatch(p -> p.equals(position))) {
          byte adjacentMines = countAdjacentMines(position, mineLocations);
          grid[x][y] = adjacentMines > 0 ? new MineAdjacentCell(adjacentMines, position) : new AloneCell(position);
        }
      }
    }
    return new DefaultGame(id, grid);
  }

  private byte countAdjacentMines(Position position, List<Position> mineLocations) {
    // max adjacent mines is 8, (N, E, W, S, SE, SW, NW, NE) :)
    return (byte) mineLocations.stream()
      .filter(minePosition -> minePosition.getAdjacentPositions().stream().anyMatch(position::equals))
      .count();
  }

  private List<Position> getMineLocations(int numberOfMines, int width, int height) {
    List<Position> mines = new ArrayList<>();
    while (mines.size() < numberOfMines) {
      Position mine = new Position(RANDOM.nextInt(width), RANDOM.nextInt(height));
      if (!mines.contains(mine)) {
        mines.add(mine);
      }
    }
    return mines;
  }
}
