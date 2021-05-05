package renderer;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * This ABSTRACT class represents the selection color for pixels in
 * the image
 */
public abstract class RayTracerBase {


    protected Scene scene;


    /**
     * constructor for initialize the scene object
     *
     * @param scene the scene of the image
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
       // throw new IllegalArgumentException();
    }

    /***
     * this function calculate the color of the pixel
     * the ray cross
     * @param ray the ray from the camera throw the scene
     * @return the color of the pixel of the ray
     */
    public abstract Color traceRay(Ray ray);

}
