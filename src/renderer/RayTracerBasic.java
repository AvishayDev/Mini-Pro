package renderer;

import static geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

public class RayTracerBasic implements RayTracerBase{



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
}
