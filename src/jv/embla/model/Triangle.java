package jv.embla.model;

import java.awt.Color;

public class Triangle extends Model {
  public int[] a, b, c;
  
  public Triangle(int[] a, int[] b, int[] c, Color col, String id) {
    super(-1, -1, null, id);
    this.a = a;
    this.b = b;
    this.c = c;
    this.color = col;
  }
  
  public boolean isEqual(Triangle old) {
    return (this.x == old.x &&
	    this.y == old.y &&
	    this.a == old.a &&
	    this.b == old.b &&
	    this.c == old.c);
  }

    public Model clone() {
	Triangle temp = new Triangle(a, b, c, color, ID);
	for (Model child : children) {
	    temp.addChild(child.clone());
	}
	return temp;
    }

    public String toString(int tab) {
	StringBuffer stringReturn = new StringBuffer();

	for (int i = 0; i < tab; i++)
	    stringReturn.append(" ");
	stringReturn.append("(deftriangle {:a " + a.toString() + " :b " + b.toString() + " :c " + c.toString() + " :id " + ID + " :color " + color + "}");

	for (Model child: children) {
	    stringReturn.append('\n');
	    stringReturn.append(child.toString(tab + 2));
	}

	stringReturn.append(')');

	return stringReturn.toString();
    }

}
