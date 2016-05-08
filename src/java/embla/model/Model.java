package embla.model;

import java.util.ArrayList;
import java.util.Optional;

// GOM of the world.
public class Model {
  // To parse the GOM.
  public final String ID;
  public String[] classes;
  // Need to add the potential texture.

  // If all objects need others variables, add it here.
  // Every objects has coordinates.
  public int x, y;

  // To construct the tree.
  // Every objects could have children.
  public ArrayList<Model> children;

  // Boolean if the model has been changed.
  private boolean changed;

  // Common constructors to all elements. May have or not classes or ID.
  public Model(int x, int y, String[] classes, String id) {
    this.x = x;
    this.y = y;

    this.ID = id;
    this.classes = classes;
    this.children = new ArrayList<>();
    this.changed = false;
  }

  // Add the child to the children of the element.
  public void addChild(Model child) {
    this.children.add(child);
  }

  // Every ID is unique => return exactly one element at most.
  public Optional<Model> getElementById(final String id) {
    if (this.ID.equals(id)) {
      return Optional.of(this);
    }

    Optional<Model> result;
    for (Model child: children) {
      result = child.getElementById(id);
      if (result.isPresent())
      return result;
    }

    return Optional.empty();
  }

  // Print the model.
  public String toString(int tab) {
    StringBuffer stringReturn = new StringBuffer();

    for (int i = 0; i < tab; i++)
      stringReturn.append(" ");
    stringReturn.append(ID);
    stringReturn.append(" ");
    stringReturn.append(classes);

    for (Model child: children) {
      stringReturn.append('\n');
      stringReturn.append(child.toString(tab + 2));
    }

    return stringReturn.toString();
  }
}
