package embla.model;

public class Rectangle extends Model {
  public int length, height;

  // Creates a rectangle.
  public Rectangle(int x, int y, int length, int height, String id) {
    super(x, y, null, id);
    this.length = length;
    this.height = height;
  }
}
