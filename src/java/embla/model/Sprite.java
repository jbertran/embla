package embla.model;

public class Sprite extends Model {
  public String path;
  public int width, height;
  
  public Sprite(int x, int y, int width, int height, String path, String id) {
    super(x, y, null, id);
    this.width = width;
    this.height = height;
    this.path = path;
  }
  
  public boolean isEqual(Sprite old) {
    return (this.x == old.x &&
	    this.y == old.y &&
	    this.width == old.width &&
	    this.height == old.height &&
	    this.path == old.path);
  }
}
