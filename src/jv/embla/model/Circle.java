package jv.embla.model;

import java.awt.Color;

public class Circle extends Model {
  public int radius;

  // Create a circle.
  public Circle(int x, int y, int radius, Color c, String id) {
    super(x, y, null, id);
    this.radius = radius;
    this.color = c;
  }
  
  public boolean isEqual(Circle old) {
    return (this.x == old.x &&
	    this.y == old.y &&
	    this.color == old.color &&
	    this.radius == old.radius);
  }
}