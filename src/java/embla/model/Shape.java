package embla.model;

public class Shape extends Model {
  public int[] angles;

  // Create a Shape.
  public Shape(int x, int y, int[] angles) {
    super(x, y);
    this.angles = angles;
  }
}
