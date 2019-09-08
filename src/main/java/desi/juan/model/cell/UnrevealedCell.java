package desi.juan.model.cell;

import desi.juan.model.Position;

abstract class UnrevealedCell implements Cell {

  private final Position position;

  UnrevealedCell(Position position) {
    this.position = position;
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public boolean isHidden() {
    return true;
  }

  @Override
  public String print() {
    return "□";
  }
}
