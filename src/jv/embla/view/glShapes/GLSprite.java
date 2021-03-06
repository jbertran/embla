package jv.embla.view.glShapes;

import java.awt.Color;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import jv.embla.engine.RenderEngine;
import jv.embla.model.Model;
import jv.embla.model.Sprite;
import jv.embla.view.glUtils.TextureLoader;

public class GLSprite extends GLShape implements IGLShape {

	private static final Color TRANSP_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	private TextureLoader loader;
	private GLTexture texture = null;
	private int vbo_texcoords;

	public GLSprite(String e_ID, RenderEngine engine, TextureLoader loader, String path,
	int x, int y, int width, int height) {
		super(e_ID, engine, "shaders/texture_vertex.glsl", "shaders/texture_fragment.glsl");
		// Bookkeeping general info
		summit_count = 4;
		this.loader = loader;

		// Manage buffer contents
		toProjection(x, y, width, height, path);
		colorToVBO(TRANSP_COLOR);
		setupTexCoords();
		setupTexData(path);

		/** Bind extra attribute for the shader program's texture input **/
		GL20.glBindAttribLocation(shader_progid, 2, "in_TextureCoord");

		/** Set the VAO **/
		GL30.glBindVertexArray(vao_shapeid);
		// Positions -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertexid);
		GL20.glVertexAttribPointer(0, summit_count, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		// Color -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(1);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colorid);
		GL20.glVertexAttribPointer(1, summit_count, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(1);
		// Texture -- data already bound. No touching!
		GL20.glEnableVertexAttribArray(2);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texcoords);
		GL20.glVertexAttribPointer(2, summit_count, GL11.GL_FLOAT, false, 0, 0);
		// GL11.glTexCoordPointer(texture.size(), GL11.GL_FLOAT, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL20.glDisableVertexAttribArray(2);
		// End
		GL30.glBindVertexArray(0);
	}

	private void toProjection(int x, int y, int width, int height, String path) {
		// Update coordinates
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
		// Update position
		positionToVBO(glpos);
		// Update texture if necessary
		setupTexData(path);
	}

	private void setupTexData(String path) {
		if (this.texture == null || !(this.texture.path().equals(path))) {
			this.texture = loader.loadTexture(path);
			this.texture.bindToGL();
		}
	}
	
	private void setupTexCoords() {
		vbo_texcoords = GL15.glGenBuffers();
		float [] coords = new float [] { 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };
		FloatBuffer buffer = BufferUtils.createFloatBuffer(summit_count * 2);
		buffer.put(coords);
		buffer.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texcoords);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void render() {
		GL20.glUseProgram(shader_progid);
		GL30.glBindVertexArray(vao_shapeid);
		// Positions
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		texture.bind();
		GL11.glDrawArrays(GL11.GL_QUADS, 0, summit_count);
		texture.unbind();
		GL20.glDisableVertexAttribArray(0);
		/**
		// Color -- transparent anyway
		GL20.glEnableVertexAttribArray(1);
		GL11.glDrawArrays(GL11.GL_COLOR_ARRAY, 0, 1);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
		**/
		/**
		// Texture vertices
		texture.bind();
		GL20.glEnableVertexAttribArray(2);
		GL11.glDrawArrays(GL11.GL_TEXTURE_2D, 0, summit_count);
		GL20.glDisableVertexAttribArray(2);
		texture.unbind();
		**/
	}

	@Override
	public void propagate(Model m) {
		if (m instanceof Sprite)
		toProjection(m.x, m.y, ((Sprite) m).width, ((Sprite) m).height, ((Sprite) m).path);
		else
		throw new RuntimeException("Model node / openGL draw call mismatch");
	}
}
