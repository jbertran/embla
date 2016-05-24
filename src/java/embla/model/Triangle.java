package embla.model;

import java.awt.Color;

public class Triangle extends Model {
  public int[] a, b, c;
  
  public Triangle(int[] a, int[] b, int[] c, Color col, String id) {
    super(-1, -1, null, id);
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public boolean isEqual(Triangle new) {
    return (this.x == new.x &&
	    this.y == new.y &&
	    this.a == new.a &&
	    this.b == new.b &&
	    this.c == new.c);
  }
}
