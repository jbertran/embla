package embla.utils;

import embla.model.Model;
import embla.model.Shape;
import embla.model.Circle;
import embla.model.Rectangle;

import java.util.ArrayList;
import java.util.Optional;

// GOM — Game Object Model — is a tree corresponding to world.
public class GOM {
  // Keep a list to ensure that ID are unique.
  private ArrayList<String> ids;

  // The world. Obviously.
  private Model world;

  // Create the world, and create the root.
  public GOM(String id) {
    this.world = new Model(-1, -1, null, id);
    ids = new ArrayList<>();
    ids.add(id);
  }

  // Return a precise element defined by ID.
  public Optional<Model> getElementById(final String id) {
    return world.getElementById(id);
  }
}
