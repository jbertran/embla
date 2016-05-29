package jv.embla.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.*;

import jv.embla.model.*;
import jv.embla.view.glShapes.*;
import jv.embla.utils.KeyCallbacks;
import jv.embla.view.glUtils.TextureLoader;

public class RenderEngine extends Thread {
  // The window handle
  private long window;
  private GLFWKeyCallback keyCallback;

  private GLFWErrorCallback errorCallback;

  private HashMap<String, GLShape> glShapes;
  private TextureLoader loader;
  private Optional<HashMap<String, Model>> changes;
  private Model world, old;
  private ArrayList<Model> newList;
  private String title;
  private Object atomic;
  public int width, height;

  public RenderEngine(int width, int height, String title) {
    super();
    this.width = width;
    this.height = height;
    this.title = title;
    this.glShapes = new HashMap<>();
    this.loader = new TextureLoader();
    this.world = new Model(-1, -1, null, "root");
    this.old = null;
    this.newList = new ArrayList<>();
    this.changes = Optional.empty();
    this.atomic = new Object();
  }

  public TextureLoader getLoader() {
    return loader;
  }

  public Model getWorld() {
    return world;
  }

  public Model getOldWorld() {
    return old;
  }

  public void setOldWorld(Model old) {
    this.old = old;
  }

  public void setChanges(HashMap<String, Model> hmap) {
    this.changes = Optional.of(hmap);
  }

  public void addGLShape(Model m) {
    newList.add(m);
  }

  public void updateWorld(Model m) {
    this.world = m;
    this.old = null;
  }

  public void clear() {
    synchronized(atomic) {
      this.glShapes.clear();
      this.newList.clear();
      this.world = new Model(-1, -1, null, "root");
      this.old = null;
      this.changes = Optional.empty();
    }
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
      this.world = new Model(-1, -1, null, "root");
      this.old = null;
      this.glShapes.clear();
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
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

    // Shader language options
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
    // glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    // glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

    // Create the window
    window = glfwCreateWindow(width, height, title, NULL, NULL);
    if ( window == NULL )
    throw new RuntimeException("Failed to create the GLFW window");

    // Setup a key callback.
    glfwSetKeyCallback(window, keyCallback = new KeyCallbacks());

    // Get the resolution of the primary monitor
    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    // Center our window
    glfwSetWindowPos(window, (vidmode.width() - width) / 2,
    (vidmode.height() - height) / 2);
    // Make the OpenGL context current
    glfwMakeContextCurrent(window);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);

    /** Setup the projection, enable textures **/
    GL.createCapabilities();
    System.out.println(GL11.glGetString(GL11.GL_VERSION));
  }

  public void glLoop() {
    try {
      // Set the clear color
      glClearColor(0.4f, 0.6f, 0.9f, 0f);

      while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
        synchronized(atomic) {
          glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
          // Add the shapes that may have been created
          if (newList.size() > 0) {
            for (Model m : newList)
            glShapes.put(m.ID, GLShapeFactory.makeShape(this, m));
            newList.clear();
          }
          // Draw the model with OpenGL, including changes if applicable
          redraw();

          GLFW.glfwSwapBuffers(window);

          // Poll for window events.
          glfwPollEvents();
        }
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
    } finally {
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
