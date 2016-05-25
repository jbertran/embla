package jv.embla.model;

import java.awt.Color;

public class Triangle extends Model {
  public int[] a, b, c;
  
  public Triangle(int[] a, int[] b, int[] c, Color col, String id) {
    super(-1, -1, null, id);
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public boolean isEqual(Triangle old) {
    return (this.x == old.x &&
	    this.y == old.y &&
	    this.a == old.a &&
	    this.b == old.b &&
	    this.c == old.c);
  }
}
