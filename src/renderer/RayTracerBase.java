package renderer;

import elements.AmbientLight;
import elements.Light;
import geometries.Geometries;
import primitives.*;
import scene.Scene;

import java.util.List;

public interface RayTracerBase {

    Geometries geometries = null;
    Color background = Color.BLACK;
    //AmbientLight ambient = new AmbientLight(Color.BLACK, 0);
    Color ambient = null;
    List<Light> lights = null;

    Color traceRay(Ray ray);

    java.awt.Color castRay(Ray ray);
}
