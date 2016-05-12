package embla.view.glShapes;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class GLTexture {
	
	static final int TARGET = GL11.GL_TEXTURE_2D;
	static final int FILTER = GL11.GL_NEAREST;
	static final int WRAP = GL12.GL_CLAMP_TO_EDGE;
	boolean hasAlpha;

	int texID;
	public int width, height, size;
	FloatBuffer texData;
	
	public GLTexture(int width, int height, int size, boolean hasAlpha, FloatBuffer texData) {
		this.texID = GL11.glGenTextures();
		this.texData = texData;
		this.width = width;
		this.height = height;
		this.size = size;
		this.hasAlpha = hasAlpha;
	}
	
	public void bind() {
		GL11.glBindTexture(TARGET, texID);
	}
	
	public void unbind() {
		GL11.glBindTexture(TARGET, 0);
	}
	
	public void bindToGL() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(TARGET, texID);
		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, texID);
		GL11.glTexParameteri(TARGET, GL11.GL_TEXTURE_MIN_FILTER, FILTER);
		GL11.glTexParameteri(TARGET, GL11.GL_TEXTURE_MAG_FILTER, FILTER);
		GL11.glTexParameteri(TARGET, GL11.GL_TEXTURE_WRAP_S, WRAP);
		GL11.glTexParameteri(TARGET, GL11.GL_TEXTURE_WRAP_T, WRAP);
		// TODO: account for alpha or not
		GL11.glTexImage2D(TARGET, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texData);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(TARGET, 0);
	}
}
