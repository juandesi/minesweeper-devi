package desi.juan.model.cell;

import desi.juan.model.DefaultGame;
import desi.juan.model.Position;

public class AloneCell extends UnrevealedCell implements Cell {

  public AloneCell(Position position) {
    super(position);
  }

  @Override
  public String getSymbol() {
    return " ";
  }

  @Override
  public DefaultGame reveal(DefaultGame game) {
    Position position = getPosition();
    Cell[][] grid = game.getGrid();
    grid[position.getX()][position.getY()] = new RevealedCell(this);
    DefaultGame mutatedGame = new DefaultGame(game.getId(), grid);
    for (Position p : position.getAdjacentPositions()) {
      if (mutatedGame.isValidPosition(p)) {
        Cell cell = game.getGrid()[p.getX()][p.getY()];
        if (cell.isHidden()) {
          mutatedGame = cell.reveal(game);
        }
      }
    }
    return mutatedGame;
  }

}
