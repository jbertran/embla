package embla.model;

public class Rectangle extends Model {
  public int length, height;

  // Creates a rectangle.
  public Rectangle(int x, int y, int length, int height) {
    super(x, y);
    this.length = length;
    this.height = height;
  }
  public Rectangle(int x, int y, int length, int height, String id) {
    super(x, y, id);
    this.length = length;
    this.height = height;
  }
  public Rectangle(int x, int y, int length, int height, String[] classes) {
    super(x, y, classes);
    this.length = length;
    this.height = height;
  }
  public Rectangle(int x, int y, int length, int height, String[] classes, String id) {
    super(x, y, classes, id);
    this.length = length;
    this.height = height;
  }
}
