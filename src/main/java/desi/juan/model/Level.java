package desi.juan.model;

public enum Level {
  EASY(1), MEDIUM(2), HARD(3);

  private final int multiplier;

  Level(int multiplier) {
    this.multiplier = multiplier;
  }

  public int getMultiplier() {
    return multiplier;
  }
}
