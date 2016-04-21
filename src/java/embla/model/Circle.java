package embla.model;

public class Circle extends Model {
  public int radius;

  // Create a circle.
  public Circle(int x, int y, int radius) {
    super(x, y);
    this.radius = radius;
  }
}
