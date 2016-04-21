package embla.view;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

// Y a pas besoin d'import, c'est visibilité package nan ?
import embla.view.Utils;

public abstract class Shape implements IShape {

	static int FLOAT_OFFSET = Float.SIZE;
	int summit_count;

	int vaoid;
	int vbo_colorid;
	int vbo_vertexid;
	int shader_progid;
	int shader_vertid;
	int shader_fragid;

	public Shape() {
		// Shaders
		shader_vertid = Utils.loadShader("shaders/id_vertex.glsl", GL20.GL_VERTEX_SHADER);
		shader_fragid = Utils.loadShader("shaders/id_fragment.glsl", GL20.GL_FRAGMENT_SHADER);
		shader_progid = GL20.glCreateProgram();
		GL20.glAttachShader(shader_progid, shader_vertid);
		GL20.glAttachShader(shader_progid, shader_fragid);
		GL20.glBindAttribLocation(shader_progid, 0, "in_position");
		GL20.glBindAttribLocation(shader_progid, 1, "in_color");
		GL20.glLinkProgram(shader_progid);
		GL20.glValidateProgram(shader_progid);

		// VBOs
		vbo_vertexid = GL15.glGenBuffers();
		vbo_colorid = GL15.glGenBuffers();

		// VAO
		vaoid = GL30.glGenVertexArrays();
	}

	public void setColors(FloatBuffer buffer) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colorid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	public void setCoordinates(FloatBuffer buffer) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_colorid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	public abstract void render();
}
