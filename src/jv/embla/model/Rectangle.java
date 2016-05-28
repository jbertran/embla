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

    public Model clone() {
	Rectangle temp = new Rectangle(x, y, width, height, color, ID);
	for (Model child : children) {
	    temp.addChild(child.clone());
	}
	return temp;
    }
    
    public String toString(int tab) {
	StringBuffer stringReturn = new StringBuffer();

	for (int i = 0; i < tab; i++)
	    stringReturn.append(" ");
	stringReturn.append("(defrect {:x " + x + " :y " + y + " :width " + width + " :height " + height + " :id " + ID + " :color " + color + "}");

	for (Model child: children) {
	    stringReturn.append('\n');
	    stringReturn.append(child.toString(tab + 2));
	}

	stringReturn.append(')');

	return stringReturn.toString();
    }

}
