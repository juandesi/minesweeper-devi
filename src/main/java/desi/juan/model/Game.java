package desi.juan.model;

import java.util.List;

import desi.juan.model.cell.Cell;

public interface Game {

  boolean isFinished();

  Game revealCell(int x, int y);

  Cell getCell(int x, int y);

  boolean isValidPosition(Position position);

  List<Cell> getAllCells();

  boolean isSolved();

  Cell[][] getGrid();

  String print();

  String getId();
}
