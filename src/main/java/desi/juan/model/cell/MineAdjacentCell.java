package desi.juan.model.cell;

import desi.juan.model.DefaultGame;
import desi.juan.model.Position;

public class MineAdjacentCell extends UnrevealedCell implements Cell {

  private final byte number;

  public MineAdjacentCell(byte number, Position position) {
    super(position);
    if (number > 8 || number < 0) {
      throw new IllegalStateException("A cell cannot have more than 8 adjacent mines");
    }
    this.number = number;
  }

  @Override
  public String getSymbol() {
    return number + "";
  }

  @Override
  public DefaultGame reveal(DefaultGame game) {
    Cell[][] grid = game.getGrid();
    Position position = getPosition();
    grid[position.getX()][position.getY()] = new RevealedCell(this);
    return new DefaultGame(game.getId(), grid);
  }

}
