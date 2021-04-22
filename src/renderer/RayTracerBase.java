package renderer;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.*;
import scene.Scene;

public interface RayTracerBase {

     Geometries geometries = null;
     Color background = Color.BLACK;
     AmbientLight ambient = new AmbientLight(Color.BLACK, 0);

    Color traceRay(Ray ray);

    java.awt.Color castRay(Ray ray);
}
