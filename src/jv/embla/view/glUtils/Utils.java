package jv.embla.view.glUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;


public class Utils {

	// TODO: shader cleanup
	private static ArrayList<ArrayList<Integer>> shader_programs;

	public static void add_program(int progid, int vertid, int fragid) {
		System.out.println("Todo");
	}

	public static void delete_programs() {
		System.out.println("Todo");
	}

	public static int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;
		// File reading
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read shader file " + filename);
			e.printStackTrace();
			System.exit(-1);
		}
		// Compile shader
		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		return shaderID;
	}

	public static void checkAndAttach( int programID, int shaderID) {
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
			throw new RuntimeException("Unable to compile shader\n"
			+ GL20.glGetShaderInfoLog(shaderID, GL20.glGetShaderi(shaderID, GL20.GL_INFO_LOG_LENGTH)));
		else
			GL20.glAttachShader(programID, shaderID);
	}
}
