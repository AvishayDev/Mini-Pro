package renderer;

import geometries.Borderable;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * This ABSTRACT class represents the selection color for pixels in
 * the image
 */
public abstract class RayTracerBase {

    /**
     * the scene we work on
     */
    protected Scene scene;

    /**
     * constructor for initialize the scene object
     *
     * @param scene the scene of the image
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public RayTracerBase setVBH() {
        Borderable.setEnabled();
        return this;
    }

    /***
     * this function calculate the color of the pixel
     * the ray cross
     * @param ray the ray from the camera throw the scene
     * @return the color of the pixel of the ray
     */
    public abstract Color traceRay(Ray ray);

    /***
     * this function calculate the color of the pixel
     * the rays cross
     * @param rays the list of rays from the camera throw the scene
     * @return the color of the pixel of the ray
     */
    public abstract Color traceRays(List<Ray> rays);

}
