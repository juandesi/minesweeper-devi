package desi.juan.model.cell;

import desi.juan.model.DefaultGame;
import desi.juan.model.Position;
import desi.juan.model.error.RevealedMineException;

public class Mine extends UnrevealedCell implements Cell {

  public Mine(Position position) {
    super(position);
  }

  @Override
  public String getSymbol() {
    return "*";
  }

  @Override
  public DefaultGame reveal(DefaultGame game) {
    throw new RevealedMineException("A Mine has exploded, you lose the game");
  }
}
