package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;


/***
 * Represents Triangle with Plane and three 3D points.
 */
public class Triangle extends Polygon {

    /***
     * Triangle constructor based on vertices list. The list have 3 points
     * and more can't be added. The polygon must be convex.
     * @param point1 First point of the Triangle
     * @param point2 second point of the Triangle
     * @param point3 Third point of the Triangle
     */
    public Triangle(Point3D point1, Point3D point2, Point3D point3) {
        super(point1, point2, point3);
        checkLines.add(point3.subtract(point1));
        checkLines.add(point1.subtract(point2));
        checkLines.add(point2.subtract(point3));
    }

    /***
     * To string function
     * @return Returns information about the triangle's vertices and its plane
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    @Override
    protected void move(Vector direction, double t) {

        vertices.set(0, vertices.get(0).add(direction, t));
        vertices.set(1, vertices.get(1).add(direction, t));
        vertices.set(2, vertices.get(2).add(direction, t));
    }

    /**
     * DAN THINKING:
     * This method receives a ray and his max distance and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none or pass the max distance, null will be returned.
     *
     * @param ray         The ray which we find the intersections to the object.
     * @param maxDistance the maximum distance for the ray to go
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
  /*  @Override
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray, double maxDistance) {

        List<GeoPoint> planeIntersections = plane.findGeoIntersectionsParticular(ray, maxDistance);
        //because we care about the distance in the plane we
        //don't need to care about it here

        if (planeIntersections == null)
            return null;

        try {
            GeoPoint p = planeIntersections.get(0);

            Vector v1 = vertices.get(0).subtract(p.point);
            Vector v2 = vertices.get(1).subtract(p.point);
            Vector v3 = vertices.get(2).subtract(p.point);

            Vector v1t = v1.crossProduct(v2);
            Vector v2t = v2.crossProduct(v3);
            if (v1t.dotProduct(v2t) < 0)
                return null;
            Vector v3t = v3.crossProduct(v1);
            if (v1t.dotProduct(v3t) < 0)
                return null;

            p.geometry = this;
            return planeIntersections;

        } catch (IllegalArgumentException ignore) {
            return null;
        }
    }*/
    /**
     * MY THINKING:
     *
     * This method receives a ray and his max distance and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none or pass the max distance, null will be returned.
     *
     * @param ray         The ray which we find the intersections to the object.
     * @param maxDistance the maximum distance for the ray to go
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray, double maxDistance) {

        List<GeoPoint> planeIntersections = plane.findGeoIntersectionsParticular(ray, maxDistance);
        //because we care about the distance in the plane we
        //don't need to care about it here

        if (planeIntersections == null)
            return null;
        GeoPoint geo = planeIntersections.get(0);
        try {
            double sign = geo.point.subtract(vertices.get(1)).crossProductValue(checkLines.get(1));
            if (Util.alignZero(sign * geo.point.subtract(vertices.get(0)).crossProductValue(checkLines.get(0))) <= 0)
                return null;
            if (Util.alignZero(sign * geo.point.subtract(vertices.get(2)).crossProductValue(checkLines.get(2))) <= 0)
                return null;
            geo.geometry = this;
            return planeIntersections;
        } catch (IllegalArgumentException e) {
            return null;
        }

    }

}
