package embla.view.glShapes;

import java.awt.Color;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import embla.view.glUtils.GameEngine;
import embla.view.glUtils.Utils;

public abstract class GLShape implements IGLShape {

	String e_ID;
	GameEngine engine;
	int summit_count;
	
	int vao_shapeid;
	int vbo_colorid;
	int vbo_vertexid;
	int shader_progid;
	int shader_vertid;
	int shader_fragid;
	
	boolean disposed = false;
	
	public GLShape(String e_ID, GameEngine engine, String vertshader, String fragshader) {
		this.e_ID = e_ID;
		this.engine = engine;
		
		// Shaders
		shader_vertid = Utils.loadShader(vertshader, GL20.GL_VERTEX_SHADER);
		shader_fragid = Utils.loadShader(fragshader, GL20.GL_FRAGMENT_SHADER);
		shader_progid = GL20.glCreateProgram();
		Utils.checkAndAttach(shader_progid, shader_vertid);
		Utils.checkAndAttach(shader_progid, shader_fragid);
		GL20.glBindAttribLocation(shader_progid, 0, "in_position");
		GL20.glBindAttribLocation(shader_progid, 1, "in_color");
		GL20.glLinkProgram(shader_progid);
		if (GL20.glGetProgrami(shader_progid, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
			throw new RuntimeException("Unable to link shader program");
		GL20.glValidateProgram(shader_progid);

		// VBOs
		vbo_vertexid = GL15.glGenBuffers();
		vbo_colorid = GL15.glGenBuffers();

		// VAO
		vao_shapeid = GL30.glGenVertexArrays();
	}

	public GLShape(String e_ID, GameEngine engine) {
		this(e_ID, engine, "shaders/id_vertex.glsl", "shaders/id_fragment.glsl");
	}
	
	public void bindColors(FloatBuffer buffer) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colorid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void bindCoordinates(FloatBuffer buffer) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertexid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void positionToVBO(float [] summits) {
		FloatBuffer vertices = BufferUtils.createFloatBuffer(summit_count * 4);
		vertices.clear();
		if (summits.length != summit_count * 2) {
			throw new RuntimeException("Supplied number of vertices does not match!");
		}
		for (int i = 0; i < summits.length; i+=2) { 
			vertices.put(summits[i]);
			vertices.put(summits[i+1]);
			vertices.put(0f);
			vertices.put(1f);
		}
		vertices.flip();
		bindCoordinates(vertices);
	}
	
	public void colorToVBO(Color c) {
		GL20.glUseProgram(shader_progid);
		int colorLoc = GL20.glGetUniformLocation(shader_progid, "color");
		GL20.glUniform4f(colorLoc, c.getRed()/255, c.getGreen()/255, c.getBlue()/255, c.getAlpha()/255);
		GL20.glUseProgram(0);
	}
	
	/**
	 * Dispose of the shader-related information related to the shape. 
	 */
	public void disposeShaders() {
		// Unbind
		GL20.glUseProgram(0);
		// Detach
		GL20.glDetachShader(shader_progid, shader_vertid);
		GL20.glDetachShader(shader_progid, shader_fragid);
		// Delete
		GL20.glDeleteShader(shader_vertid);
		GL20.glDeleteShader(shader_fragid);
		GL20.glDeleteProgram(shader_progid);
		disposed = true;
	}
	
<<<<<<< HEAD
	/**
	 * Projection calculation
	 */
	protected float hToProjection(int h) {
		return ((float) (2*h) / engine.height);
	}
	
	protected float yToProjection(int y) {
		return hToProjection(y) - 1;
	}
	
	protected float wToProjection(int w) {
		return ((float) (2*w) / engine.width);
	}
	
	protected float xToProjection(int x) {
		return wToProjection(x) - 1;
	}
	
	protected float dToProjection(int d) {
		return Math.min((float) d / engine.width, (float) d / engine.height);
	}
	
	
=======
>>>>>>> e1f7bac4b3a009f807ccdee591f26ae24a6dd090
	public String id() {
		return e_ID;
	}
	
	public abstract void render();
}
