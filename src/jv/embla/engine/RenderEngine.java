package jv.embla.engine;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
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
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import jv.embla.model.Circle;
import jv.embla.model.Model;
import jv.embla.model.Rectangle;
import jv.embla.model.Sprite;
import jv.embla.model.Triangle;
import jv.embla.view.glShapes.GLCircle;
import jv.embla.view.glShapes.GLRectangle;
import jv.embla.view.glShapes.GLShape;
import jv.embla.view.glShapes.GLSprite;
import jv.embla.view.glShapes.GLTriangle;
import jv.embla.view.glUtils.TextureLoader;

public class RenderEngine implements Runnable {
	// The window handle
	private long window;
	private GLFWKeyCallback   keyCallback;

	float [] pos = { 1f, 1f, -1f, 1f, 1f, -1f };
	float [] posR = { 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f };
	float [] posC = { 0.0f, 0.0f };private GLFWErrorCallback errorCallback;

	private HashMap<String, GLShape> glShapes;
	private TextureLoader loader;
	private Optional<HashMap<String, Model>> changes;
	private Model world;
	private String title;
	public int width, height;

	public RenderEngine(int width, int height, String title) {
		super();
		this.width = width;
		this.height = height;
		this.title = title;
		this.glShapes = new HashMap<>();
		this.loader = new TextureLoader();
		this.world = new Model(-1, -1, null, "root");
		this.changes = Optional.empty();
	}

	public void run() {
		System.out.println("Running LWJGL " + Version.getVersion());
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

		// Shader language options
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		// Create the window
		window = glfwCreateWindow(width, height, title, NULL, NULL);
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
		System.out.println(GL11.glGetString(GL11.GL_VERSION));
		
		/*
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-1, 1, -1, 1, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		*/
	}
	
	// TODO: move this logic to a constructor from a Model instance for each GLShape
	public void addGLShape(Model m) {
		if (m instanceof Circle) {
			Circle c = (Circle) m;
			glShapes.put(c.ID, new GLCircle(c.ID, this, c.x, c.y, c.radius, c.color));
		}
		else if (m instanceof Rectangle) {
			Rectangle r = (Rectangle) m;
			glShapes.put(r.ID, new GLRectangle(r.ID, this, r.x, r.y, r.width, r.height, r.color));
		}
		else if (m instanceof Triangle) {
			Triangle t = (Triangle) m;
			glShapes.put(t.ID, new GLTriangle(t.ID, this, t.a, t.b, t.c, t.color));
		}
		else if (m instanceof Sprite) {
			Sprite s = (Sprite) m;
			glShapes.put(s.ID, new GLSprite(s.ID, this, this.loader, s.path, s.x, s.y, s.width, s.height));
		}
		else {
			throw new RuntimeException("Attempted to push model known of an unknown type");
		}
	}
	
	public void updateWorld(Model m) {
		this.world = m;
	}

	public void glLoop() {
		// Set the clear color
		glClearColor(0.4f, 0.6f, 0.9f, 0f);

		// Run the rendering loop until the user has attempted to close the window
		int[][] tpos = new int[3][];
		tpos[0] = new int[] {100, 100};
		tpos[1] = new int[] {100, 200};
		tpos[2] = new int[] {50, 200};
		Rectangle r = new Rectangle(width/4, 3*height/4, width/2, height/2, Color.red, "rect");
		Circle c = new Circle(width/2, height/2, height/3, Color.yellow, "circ");
		Triangle t = new Triangle(tpos[0], tpos[1], tpos[2], Color.blue, "tri");
		Sprite s = new Sprite(0, height, width/8, height/8, "resources/link.png", "sprite");
		// world.addChild(t);
		// t.addChild(r);
		// t.addChild(c);
		world.addChild(s);

		GLRectangle rect = new GLRectangle("rect", this, width/4, 3*height/4, width/2, height/2, Color.red);
		GLCircle circ = new GLCircle("circ", this, width/2, height/2, height/3, Color.yellow);
		GLTriangle tri = new GLTriangle("tri", this, new int[] {0, 0}, new int[]{width/2, height}, new int[]{width, 0}, Color.blue);
		GLSprite sprite = new GLSprite("sprite", this, this.loader, "resources/link.png", 0, height, width/8, height/8);

		glShapes.put(rect.id(), rect);
		glShapes.put(circ.id(), circ);
		glShapes.put(tri.id(), tri);
		glShapes.put(sprite.id(), sprite);

		try {
			while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

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
		try {
			HashMap<String, Model> modelchanges = changes.get();
			for (Model modelch : modelchanges.values()) {
				GLShape s = glShapes.get(modelch.ID);
				if (s != null)
					s.propagate(modelch);
				else
					throw new RuntimeException("Attempted to propagate changes to GLShape unknown to the engine");
			}
			changes = Optional.empty();
		} catch (NoSuchElementException e) {
		}	finally {
			// Changes are propagated, now draw the world
			draw_model_item(world);
		}
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
