package embla.view.glUtils;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;
import java.util.HashMap;
import java.util.Optional;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import embla.model.Circle;
import embla.model.Model;
import embla.model.Rectangle;
import embla.view.glShapes.GLCircle;
import embla.view.glShapes.GLRectangle;
import embla.view.glShapes.GLShape;
import embla.view.glShapes.GLSprite;

public class GameEngine {
	// The window handle
	private long window;
	private GLFWKeyCallback   keyCallback;
	
	float [] pos = { 1f, 1f, -1f, 1f, 1f, -1f };
	float [] posR = { 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f };
	float [] posC = { 0.0f, 0.0f };private GLFWErrorCallback errorCallback;

	private HashMap<String, GLShape> glShapes;
	private TextureLoader loader = new TextureLoader();
	private Optional<Model[]> changes;
	private Model world;
	public int width, height;
	
	public GameEngine(int width, int height) {
		this.width = width;
		this.height = height;
		this.glShapes = new HashMap<>();
		this.world = new Model(-1, -1, null, "root");
		this.changes = Optional.empty();
	}
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		try {
			init();
			glLoop();

			// Destroy window and window callbacks
			glfwDestroyWindow(window);
			keyCallback.release();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// Terminate GLFW and free the GLFWErrorCallback
			glfwTerminate();
			System.out.println("LWJGL loop terminating.");
			errorCallback.release();
		}
	}

	public void init() {
		// Setup an error callback. 
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( glfwInit() != GLFW_TRUE )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(width, height, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
					glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this in our rendering loop
			}
		});

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(
				window,
				(vidmode.width() - width) / 2,
				(vidmode.height() - height) / 2
				);
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		/** Setup the projection, enable textures **/
		GL.createCapabilities();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-1, 1, -1, 1, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void glLoop() {
		// Set the clear color
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close the window
		
		Rectangle r = new Rectangle(width/4, 3*height/4, width/2, height/2, "rect");
		Circle c = new Circle(width/2, height/2, height/3, "circ");
		world.addChild(r);
		r.addChild(c);
		
		GLRectangle rect = new GLRectangle("rect", this, width/4, 3*height/4, width/2, height/2, Color.red);
		GLCircle circ = new GLCircle("circ", this, width/2, height/2, height/3, Color.yellow);
		GLSprite sprite = new GLSprite("sprite", this, this.loader, "resources/link.gif", 0, height, width, height);
		glShapes.put(rect.id(), rect);
		glShapes.put(circ.id(), circ);
		
		try {
			while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
	
				/**
				rect.render();
				circ.render();
				sprite.render();
				**/
				/**
				 * draw the model with OpenGL, including changes if applicable
				 */
				redraw();

				GLFW.glfwSwapBuffers(window); // swap the color buffers

				// Poll for window events. The key callback above will only be
				// invoked during this call.
				glfwPollEvents();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void redraw() {
		// Propagate model changes to GL buffers
		if (changes.isPresent()) {
			for (Model modelch : changes.get()) 
				glShapes.get(modelch.ID).propagate(modelch);
		}
		// Redraw the scene
		draw_model_item(world);
	}
	
	public void draw_model_item(Model mod) {
		// Draw the item
		GLShape s = glShapes.get(mod.ID);
		if (mod.ID.equals("root"))
			draw_children(mod);
		else if (s != null) {
			s.render();
			draw_children(mod);
		}
		else
			throw new RuntimeException("Attempted to render GLShape not known to the engine");
	}
	
	private void draw_children(Model mod) {
		for (Model m : mod.children)
			draw_model_item(m);
	}
}
