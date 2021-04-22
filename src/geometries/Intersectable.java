package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * This interface includes one method that finds the intersections between the current object a ray
 */
public interface Intersectable {


    static class GeoPoint {
        public Geometry geometry;
        public Point3D point;
    }

    /**
     * This method recives a ray and returns a list of all the interction points. In case there are none, null will be returned
     * @param ray The ray which we find the intersections to the object
     * @return A list of the intersection points in form of Point3D. In case there are no intersections, null will be returned
     */
    List<Point3D> findIntersections(Ray ray);
    //List<GeoPoint> findIntersections(Ray ray);
}
