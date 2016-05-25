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
}
