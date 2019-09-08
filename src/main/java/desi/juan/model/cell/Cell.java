package desi.juan.model.cell;

import desi.juan.model.Game;
import desi.juan.model.Position;

public interface Cell {

  Position getPosition();

  String getSymbol();

  Game reveal(Game game);

  String print();

  boolean isHidden();

}
