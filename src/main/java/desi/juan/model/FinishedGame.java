package desi.juan.model;

public class FinishedGame extends Game {

  public FinishedGame(Game delegate) {
    super(delegate.getId(), delegate.getGrid());
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public Game revealCell(int x, int y) {
    return this;
  }

}
