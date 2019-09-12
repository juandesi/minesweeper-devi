package desi.juan.model.cell;

import desi.juan.model.DefaultGame;
import desi.juan.model.Position;

public interface Cell {

  Position getPosition();

  String getSymbol();

  DefaultGame reveal(DefaultGame game);

  String print();

  boolean isHidden();

}
