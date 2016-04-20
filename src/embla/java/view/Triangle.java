package embla_opengl;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Triangle extends Shape implements IShape {
	
	
	public Triangle(float [] pos, Color col) {
		super();
		
		// Bookkeeping general info
		summit_count = 3;
		
		// Manage buffer contents
		setColor(col);
		setPosition(pos);
		
		/** Set the VAO **/
		GL30.glBindVertexArray(vaoid);
		// Positions -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertexid);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
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
		GL30.glBindVertexArray(vaoid);
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
	
	public void setPosition(float [] summits) {
		FloatBuffer vertices = BufferUtils.createFloatBuffer(summit_count * 4);
		vertices.clear();
		if (summits.length != summit_count * 2)
			throw new Error("Supplied number of vertices does not match!");
		for (int i = 0; i < summits.length; i+=2) { 
			vertices.put(summits[i]);
			vertices.put(summits[i+1]);
			vertices.put(0f);
			vertices.put(1f);
		}
		vertices.flip();
		setCoordinates(vertices);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertexid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void setColor(Color c) {
		FloatBuffer color = BufferUtils.createFloatBuffer(summit_count * 4);
		color.clear();
		int [] chans = {c.getRed(), c.getGreen(), c.getBlue()};
		for (int i = 0; i < summit_count; i ++)
			for (int v : chans)
				color.put(v/255);
			color.put(1f);
		System.out.println(color);
		for (int i = 0; i < color.limit(); i++)	
			System.out.println(color.get(i));
		color.flip();
		setColors(color);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colorid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, color, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void bindSprite() {
		// TODO Auto-generated method stub
		
	}
}
