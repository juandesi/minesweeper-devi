package desi.juan.model;

import java.util.List;

import desi.juan.model.cell.Cell;

public class FinishedGame implements Game {

  private final DefaultGame delegate;

  FinishedGame(DefaultGame delegate) {
    this.delegate = delegate;
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
  public Cell getCell(int x, int y) {
    return delegate.getCell(x, y);
  }

  @Override
  public boolean isValidPosition(Position position) {
    return delegate.isValidPosition(position);
  }

  @Override
  public List<Cell> getAllCells() {
    return delegate.getAllCells();
  }

  @Override
  public boolean isSolved() {
    return delegate.isSolved();
  }

  @Override public Cell[][] getGrid() {
    return delegate.getGrid();
  }

  @Override
  public String print() {
    return delegate.print();
  }

  @Override
  public String getId() {
    return delegate.getId();
  }
}
