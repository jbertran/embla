package embla.view.glShapes;

import java.awt.Color;

import embla.model.Model;

public interface IGLShape {
	
	public void render();
	public void colorToVBO(Color c);
	public void positionToVBO(float vertices []);
	public void propagate(Model m);
	
}
