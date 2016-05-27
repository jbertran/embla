package jv.embla.view.glShapes;

import jv.embla.model.*;
import jv.embla.engine.*;
import jv.embla.view.glShapes.*;

public class GLShapeFactory {
    public static GLCircle makeCircle(RenderEngine re, Model m) {
	Circle c = (Circle) m;
	return new GLCircle(c.ID, re, c.x, c.y, c.radius, c.color);
    }

    public static GLRectangle makeRectangle(RenderEngine re, Model m) {
	Rectangle r = (Rectangle) m;
	return new GLRectangle(r.ID, re, r.x, r.y, r.width, r.height, r.color);
    }

    public static GLTriangle makeTriangle(RenderEngine re, Model m) {
	Triangle t = (Triangle) m;
	return new GLTriangle(t.ID, re, t.a, t.b, t.c, t.color);
    }

    public static GLSprite makeSprite(RenderEngine re, Model m) {
	Sprite s = (Sprite) m;
	return new GLSprite(s.ID, re, re.getLoader(), s.path, s.x, s.y, s.width, s.height);    
    }

    public static GLShape makeShape(RenderEngine re, Model m) {
	if (m instanceof Circle)
	    return makeCircle(re, m);
	else if (m instanceof Rectangle)
	    return makeRectangle(re, m);
	else if (m instanceof Triangle)
	    return makeTriangle(re, m);
	else if (m instanceof Sprite)
	    return makeSprite(re, m);
	else
	    throw new RuntimeException("Attempting to create unknown model node");
    }
}
