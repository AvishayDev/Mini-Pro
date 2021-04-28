package renderer;

import static geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{


    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    Color calcColor(GeoPoint point, Ray ray){
        return null;
    }

    @Override
    public Color traceRay(Ray ray) {
        //if no intersections the color is background color
        return null;
    }

    @Override
    public java.awt.Color castRay(Ray ray) {
        return null;
    }

    private Color calcColor(Point3D point){
        return null;
    }

}
