package embla.view.glShapes;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import embla.model.Model;
import embla.model.Rectangle;
import embla.view.glUtils.GameEngine;

public class GLRectangle extends GLShape implements IGLShape {

	public GLRectangle(String e_ID, GameEngine engine, int x, int y, int width, int height, Color c) {
		super(e_ID, engine);
	
		// Bookkeeping general info
		summit_count = 4;
		
		// Manage buffer contents
		toProjection(x, y, width, height, c);
		
		/** Set the VAO **/
		GL30.glBindVertexArray(vao_shapeid);
		// Positions -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertexid);
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		// Color -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colorid);
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(1);
		// End
		GL30.glBindVertexArray(0);
	}
	
	private void toProjection(int x, int y, int width, int height, Color c) {
		float [] glpos = new float [8];
		float glw = ((float) (2*width) / engine.width);
		float glh = ((float) (2*height) / engine.height);
		float glx = ((float) (2*x) / engine.width) -1;
		float gly = ((float) (2*y) / engine.width) -1;
		glpos[0] = glx;
		glpos[1] = gly;
		glpos[2] = glx + glw;
		glpos[3] = gly;
		glpos[4] = glx + glw;
		glpos[5] = gly - glh;
		glpos[6] = glx;
		glpos[7] = gly - glh;
		positionToVBO(glpos);
		colorToVBO(c);
	}

	@Override
	public void render() {
		GL20.glUseProgram(shader_progid);
		GL30.glBindVertexArray(vao_shapeid);
		// Positions
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_QUADS, 0, summit_count);
		GL20.glDisableVertexAttribArray(0);
		// Color
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawArrays(GL11.GL_COLOR_ARRAY, 0, 1);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
	}

	@Override
	public void propagate(Model m) {
		if (m instanceof Rectangle)
			toProjection(m.x, m.y, ((Rectangle) m).width, ((Rectangle) m).height, m.color);
		else 
			throw new RuntimeException("Model node / openGL draw call mismatch");
	}

}
