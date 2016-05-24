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
  
  public boolean isEqual(Sprite new) {
    return (this.x == new.x &&
	    this.y == new.y &&
	    this.width == new.width &&
	    this.height == new.height &&
	    this.path == new.path);
  }
}
