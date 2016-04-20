package embla_opengl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL20;


public class Utils {
	
	// TODO: shader cleanup
	private static ArrayList<ArrayList<Integer>> shader_programs;
	
	public static void add_program(int progid, int vertid, int fragid) {
	
	}
	
	public static void delete_programs() {
		
	}
	
	public static int loadShader(String filename, int type) {
	    StringBuilder shaderSource = new StringBuilder();
	    int shaderID = 0;
	     
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader(filename));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            shaderSource.append(line).append("\n");
	        }
	        reader.close();
	    } catch (IOException e) {
	        System.err.println("Could not read file.");
	        e.printStackTrace();
	        System.exit(-1);
	    }
	     
	    shaderID = GL20.glCreateShader(type);
	    GL20.glShaderSource(shaderID, shaderSource);
	    GL20.glCompileShader(shaderID);
	     
	    return shaderID;
	}
}
