package scene;

import geometries.*;
import primitives.*;
import elements.*;

public class Scene {

    private String name = null;
    private Geometries geometries = null;
    private Color background = Color.BLACK;
    private AmbientLight ambient = new AmbientLight(Color.BLACK, 0);
}
