package embla.model;

import java.awt.Color;

public class Rectangle extends Model {
  public int width, height;

  // Creates a rectangle.
  public Rectangle(int x, int y, int width, int height, Color color, String id) {
    super(x, y, null, id);
    this.width = width;
    this.height = height;
    this.color = color;
  }
}
