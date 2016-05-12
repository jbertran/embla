package embla.model;

public class Circle extends Model {
  public int radius;

  // Create a circle.
  public Circle(int x, int y, int radius, String id) {
    super(x, y, null, id);
    this.radius = radius;
  }
}
