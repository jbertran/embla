package embla.view.glShapes;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import embla.model.Circle;
import embla.model.Model;
import embla.view.glUtils.GameEngine;

public class GLCircle extends GLShape implements IGLShape {

	static final int slices = 50;
	float radius;
	float[] center;
	
	public GLCircle(String e_ID, GameEngine engine, int x, int y, int radius, Color c) {
		super(e_ID, engine);
		// Bookkeeping general info
		summit_count = 1 + (2*slices);
		
		// Delegate color & position definition
		toProjection(x, y, radius, c);
		
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

	/** 
	 * Our projection is brought back to a 2x2 square BL -1;-1 TR 1;1
	 */
	private void toProjection(int x, int y, int radius, Color c) {
		float glx = ((float) (2*x) / engine.width) - 1;
		float gly = ((float) (2*y) / engine.height) - 1;
		this.center = new float[]{ glx, gly }; 
		this.radius = Math.min((float) radius / engine.width, (float) radius / engine.height);
		System.out.println(center[0] + "," + center[1]);
		System.out.println(this.radius);
		positionToVBO(getSlices());
		colorToVBO(c);
	}

	private float[] getSlices() {
		float[] vertices = new float[summit_count * 2];
		float slicestep = (3.14159265f*2f)/slices;
		float angle = 0;
		vertices[0] = center[0];
		vertices[1] = center[1];
		for (int sl = 0, i = 2; sl < slices; sl++, i += 4) {
			vertices[i] = (float) (center[0] + (Math.cos(angle)*radius));
			vertices[i+1] = (float) (center[1] + (Math.sin(angle)*radius));
			angle += slicestep;
			vertices[i+2] = (float) (center[0] + (Math.cos(angle)*radius));
			vertices[i+3] = (float) (center[1] + (Math.sin(angle)*radius));
		}
		return vertices;
	}

	@Override
	public void render() {
		GL20.glUseProgram(shader_progid);
		GL30.glBindVertexArray(vao_shapeid);
		// Positions
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, summit_count);
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
		if (m instanceof Circle) {
			toProjection(m.x, m.y, ((Circle) m).radius, m.color);
		} else
			throw new RuntimeException("Model node / openGL draw call mismatch");
	}

}
