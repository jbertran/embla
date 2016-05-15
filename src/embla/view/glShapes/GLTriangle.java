package embla.view.glShapes;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import embla.model.Model;
<<<<<<< HEAD
import embla.model.Triangle;
=======
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
import embla.view.glUtils.GameEngine;

public class GLTriangle extends GLShape implements IGLShape {
	float[] a;
	float[] b;
	float[] c;
	
<<<<<<< HEAD
	public GLTriangle(String e_ID, GameEngine engine, int[] a, int[] b, int[] c, Color col) {
=======
	public GLTriangle(String e_ID, GameEngine engine, float [] pos, Color col) {
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
		super(e_ID, engine);
		
		// Bookkeeping general info
		summit_count = 3;
		
<<<<<<< HEAD
		// Delegate color & position definition
		toProjection(a, b, c, col);
=======
		// Manage buffer contents
		colorToVBO(col);
		positionToVBO(pos);
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
		
		/** Set the VAO **/
		GL30.glBindVertexArray(vao_shapeid);
		// Positions -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertexid);
<<<<<<< HEAD
		GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);
=======
		GL20.glVertexAttribPointer(0, summit_count, GL11.GL_FLOAT, false, 0, 0);
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
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

	@Override
	public void render() {
		GL20.glUseProgram(shader_progid);
		GL30.glBindVertexArray(vao_shapeid);
		// Positions
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, summit_count);
		GL20.glDisableVertexAttribArray(0);
		// Color
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawArrays(GL11.GL_COLOR_ARRAY, 0, 1);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
	}
<<<<<<< HEAD
	
	private void toProjection(int[] a, int[] b, int[] c, Color col) {
		float[] glpos = new float[6];
		int i = 0;
		glpos[i++] = xToProjection(a[0]);
		glpos[i++] = yToProjection(a[1]);
		glpos[i++] = xToProjection(b[0]);
		glpos[i++] = yToProjection(b[1]);
		glpos[i++] = xToProjection(c[0]);
		glpos[i++] = yToProjection(c[1]);
		String p = "";
		for (int j = 0; j < 6; j++)
			p += glpos[j] + ", ";
		System.out.println(p);
		positionToVBO(glpos);
		colorToVBO(col);
	}

	@Override
	public void propagate(Model m) {
		if (m instanceof Triangle)
			toProjection(((Triangle) m).a, ((Triangle) m).b, ((Triangle) m).c, m.color);
		else
			throw new RuntimeException("Model node / openGL draw call mismatch");
=======

	@Override
	public void propagate(Model m) {
		// TODO Auto-generated method stub
		
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
	}
}
