package desi.juan.model.cell;

import desi.juan.model.DefaultGame;
import desi.juan.model.Position;

public class RevealedCell implements Cell {

  private final Cell delegate;

  public RevealedCell(Cell delegate) {
    this.delegate = delegate;
  }

  @Override
  public Position getPosition() {
    return delegate.getPosition();
  }

  @Override
  public String getSymbol() {
    return delegate.getSymbol();
  }

  @Override
  public DefaultGame reveal(DefaultGame game) {
    // does nothing
    return game;
  }

  @Override
  public String print() {
    return getSymbol() + "";
  }

  @Override
  public boolean isHidden() {
    return false;
  }
}
