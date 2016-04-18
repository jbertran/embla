package embla.java.model;

public class Rectangle extends Model {
  public int length, height;

  // Create a rectangle.
  public Rectangle(int x, int y, int length, int height) {
    super(x, y);
    this.length = length;
    this.height = height;
  }
}
