package jv.embla.model;

public class Shape extends Model {
  private int[][] points;
  
  // Create a Shape.
  public Shape(int x, int y, int[][] points, String id) {
    super(x, y, null, id);
    this.points = points;
  }
  
  public boolean isEqual(Shape old) {
    return (this.x == old.x &&
	    this.y == old.y &&
	    this.points == old.points);
  }

    public Model clone() {
	Shape temp = new Shape(x, y, points, ID);
	for (Model child : children) {
	    temp.addChild(child.clone());
	}
	return temp;
    }

    public String toString(int tab) {
	StringBuffer stringReturn = new StringBuffer();

	for (int i = 0; i < tab; i++)
	    stringReturn.append(" ");
	stringReturn.append("(defshape {:x " + x + " :y " + y + " :points " + points + " :id " + ID + "}");

	for (Model child: children) {
	    stringReturn.append('\n');
	    stringReturn.append(child.toString(tab + 2));
	}

	stringReturn.append(')');

	return stringReturn.toString();

    }
}
