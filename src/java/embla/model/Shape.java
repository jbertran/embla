package java.embla.model;

public class Shape extends Model {
  public int[] angles;

  // Create a Shape.
  public Shape(int x, int y, String id) {
    super(x, y, null, id);
    this.angles = null;
  }
}
