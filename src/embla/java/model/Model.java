package embla.java.model;

// GOM of the world.
public class Model {
  // To parse the GOM.
  public String   final ID;
  public String[]       class;
  // Need to add the potential texture.

  // If all objects need others variables, add it here.
  // Every objects has coordinates.
  public int x, y;

  // To construct the tree.
  // Every objects could have children.
  public ArrayList<Model> children;

  // Common constructors to all elements. May have or not classes or ID.
  public Model(int x, int y) {
    this.x = x;
    this.y = y;

    this.ID = null;
    this.class = null;
    this.children = new ArrayList<>();
  }
  public Model(int x, int y, String id) {
    this.x = x;
    this.y = y;

    this.ID = id;
    this.class = null;
    this.children = new ArrayList<>();
  }
  public Model(int x, int y, String[] class) {
    this.x = x;
    this.y = y;

    this.ID = null;
    this.class = class;
    this.children = new ArrayList<>();
  }
  public Model(int x, int y, String[] class, String id) {
    this.x = x;
    this.y = y;

    this.ID = id;
    this.class = class;
    this.children = new ArrayList<>();
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

    Model result;
    for (Model child: children) {
      result = child.getElementById(id);
      if (result.isPresent())
        return Optional.of(result);
    }

    return Optional.empty();
  }
}
