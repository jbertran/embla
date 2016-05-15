package embla.model;
<<<<<<< HEAD:src/embla/model/Circle.java

import java.awt.Color;
=======
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090:src/embla/model/Circle.java

public class Circle extends Model {
  public int radius;

  // Create a circle.
  public Circle(int x, int y, int radius, Color c, String id) {
    super(x, y, null, id);
    this.radius = radius;
    this.color = c;
  }
}
