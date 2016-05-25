package jv.embla.model;

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
  
  public boolean isEqual(Rectangle old) {
    return (this.x == old.x &&
	    this.y == old.y &&
	    this.color == old.color &&
	    this.width == old.width &&
	    this.height == old.height);
  }
}
