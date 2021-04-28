package renderer;

import elements.AmbientLight;
import elements.Light;
import geometries.Geometries;
import primitives.*;
import scene.Scene;

import java.util.List;

public abstract class RayTracerBase {

    Geometries geometries;
    Color background = Color.BLACK;
    //AmbientLight ambient = new AmbientLight(Color.BLACK, 0);
    Color ambient;
    protected Scene scene;
    List<Light> lights;

    public RayTracerBase(Scene scene){
        //this.scene = scene;
        throw new IllegalArgumentException();
    }

    abstract Color traceRay(Ray ray);

    abstract java.awt.Color castRay(Ray ray);
}
