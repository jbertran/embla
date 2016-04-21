package embla.view;

import java.awt.Color;
import java.awt.geom.Point2D;

public interface IShape {

	public void render();
	public void bindSprite();
	public void setColor(Color c);
	public void setPosition(float vertices []);

}
