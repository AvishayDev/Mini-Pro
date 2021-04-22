package scene;

import geometries.*;
import primitives.*;
import elements.*;

import java.util.List;

public class Scene {

    String name = null;
    Geometries geometries = null;
    Color background = Color.BLACK;
    AmbientLight ambient = new AmbientLight(Color.BLACK, 0);
    List<LightSource> lights = null;
}

