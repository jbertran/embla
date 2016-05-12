package embla.model;

public class Rectangle extends Model {
  public int width, height;

  // Creates a rectangle.
  public Rectangle(int x, int y, int width, int height, String id) {
    super(x, y, null, id);
    this.width = width;
    this.height = height;
  }
}
