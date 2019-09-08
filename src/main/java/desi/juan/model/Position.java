package desi.juan.model;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

public class Position {

  private final Integer x;
  private final Integer y;

  public Position(Integer x, Integer Y) {
    this.x = x;
    this.y = Y;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  public List<Position> getAdjacentPositions() {
    return ImmutableList.of(new Position(x - 1, y -1),
                     new Position(x + 1, y + 1),
                     new Position(x - 1, y + 1),
                     new Position(x + 1, y - 1),
                     new Position(x - 1 , y),
                     new Position(x , y - 1),
                     new Position(x , y + 1),
                     new Position(x + 1, y));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position p = (Position) obj;
      return this.x.equals(p.x) && this.y.equals(p.y);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this);
  }
}
