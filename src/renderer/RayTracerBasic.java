package renderer;

import static geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * this class extends RayTracerBase abstract class
 * and have more functions for Specific calculations
 */
public class RayTracerBasic extends RayTracerBase{


    /**
     * constructor that calls super constructor
     * @param scene the scene for the image
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    /*
    Color calcColor(GeoPoint point, Ray ray){
        return null;
    }
*/

    /***
     *
     * implements the function 'traceRay', calc the color of
     * ray came from the camera
     * @param ray the ray from the camera throw the scene
     * @return the color of the pixel of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) return scene.background;
        Point3D closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }


    @Override
    public java.awt.Color castRay(Ray ray) {
        return null;
    }

     //
     // meanwhile only return ambientLight color
     // @param point
     // @return
     //
    private Color calcColor(Point3D point){
        return scene.ambientLight.getIntensity();
    }

}
