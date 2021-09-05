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
     * MY THINKING:
     * <p>
     * This method receives a ray and his max distance and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none or pass the max distance, null will be returned.
     * - UNIQE METHON AND FINDING!! -
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
            Vector vec0 = vertices.get(0).subtract(geo.point);
            Vector vec1 = vertices.get(1).subtract(geo.point);
            Vector vec2 = vertices.get(2).subtract(geo.point);
            double midValue = vec1.crossProductValue(vec2);
            if (Util.alignZero(vec0.crossProductValue(vec1) * midValue) <= 0)
                return null;
            if (Util.alignZero(midValue * vec2.crossProductValue(vec0)) <= 0)
                return null;
            geo.geometry = this;
            return planeIntersections;
        } catch (IllegalArgumentException e) {
            return null;
        }

    }

}
