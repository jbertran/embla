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

    public Model clone() {
	Circle temp = new Circle(x, y, radius, color, ID);
	for (Model child : children) {
	    temp.addChild(child.clone());
	}
	return temp;
    }

    public String toString(int tab) {
	StringBuffer stringReturn = new StringBuffer();

	for (int i = 0; i < tab; i++)
	    stringReturn.append(" ");
	stringReturn.append("(defcircle {:x " + x + " :y " + y + " :radius " + radius + " :id " + ID + " :color " + color + "}");

	for (Model child: children) {
	    stringReturn.append('\n');
	    stringReturn.append(child.toString(tab + 2));
	}

	stringReturn.append(')');

	return stringReturn.toString();
    }
}
