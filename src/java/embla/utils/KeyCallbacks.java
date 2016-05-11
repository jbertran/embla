package java.embla.utils;

import java.embla.model.*;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFW;

import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.java.api.Clojure;

// Class used to handle key callbacks from GLFW.
public class KeyCallbacks extends GLFWKeyCallback {
  // Wrapper to send informations to Clojure. Useful for core.async.
  private void updateChan(Keyword key, Keyword state) {
    // Require Clojure functions.
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("embla.signals"));
    IFn update = Clojure.var("embla.signals", "update-chan");

    // And send it.
    update.invoke(key, state);
  }

  // Turn the int to the Clojure keyword.
  private Keyword keywordState(int action) {
    if ( action == GLFW.GLFW_PRESS ) {
      return Keyword.intern("press");
    } else if ( action == GLFW.GLFW_RELEASE ) {
      return Keyword.intern("release");
    } else if ( action == GLFW.GLFW_REPEAT ) {
      return Keyword.intern("repeat");
    }

    // If we're here... No comment.
    throw new IllegalArgumentException("Unable to reach this. LWJGL bug. " + action);
  }

  // Send the useful information to Clojure.
  @Override
  public void invoke (long window, int key, int scancode, int action, int mods) {
    if (action != GLFW.GLFW_REPEAT) {
      Keyword state = keywordState(action);
      switch (key) {
        case GLFW.GLFW_KEY_Z:
        updateChan(Keyword.intern("Z"), state);
        break;
        case GLFW.GLFW_KEY_Q:
        updateChan(Keyword.intern("Q"), state);
        break;
        case GLFW.GLFW_KEY_S:
        updateChan(Keyword.intern("S"), state);
        break;
        case GLFW.GLFW_KEY_D:
        updateChan(Keyword.intern("D"), state);
        break;
        case GLFW.GLFW_KEY_SPACE:
        updateChan(Keyword.intern("SPACE"), state);
        break;
      }
    }
    // Don't want to push repeat on the channel.
  }
}
