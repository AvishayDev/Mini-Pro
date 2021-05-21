package primitives;

import static geometries.Intersectable.GeoPoint;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/***
 * Represents Ray with vector and 3D point.
 */
public class Ray {
    /**
     * the start of the ray
     */
    private final Point3D p0;
    /**
     * the direction of the ray
     */
    private final Vector dir;


    private static final double DELTA = 0.1;

    /***
     * Make's a Ray using vector and 3D point
     * @param vec vector for the ray
     * @param point point for the ray
     */
    public Ray(Vector vec, Point3D point) {
        dir = vec.normalized();
        p0 = point;
    }

    /***
     * Make's a Ray using vector and 3D point
     * @param point point for the ray
     * @param vec vector for the ray
     */
    public Ray(Point3D point, Vector vec) {
        dir = vec.normalized();
        p0 = point;
    }


    /***
     * Make's a Ray using vector and 3D point and move the ray
     * by delta with the normal vector
     * @param point point for the ray
     * @param vec vector for the ray
     */
    public Ray(Point3D point, Vector vec, Vector normal) {

        double normalDirAngle = normal.dotProduct(vec);
        Vector calcVec = normal.scale(normalDirAngle > 0 ? DELTA : - DELTA);
        dir = vec.normalized();
        p0 = point.add(calcVec);
    }

    /***
     * Equals function, which returns whether the current object and the received objects are equal
     * @param o The received object
     * @return True if the objects are equal, and false if they aren't.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }


    /***
     * This method receives a list of Point3Ds, and returns the point that have the lowest distance from the head of the ray
     * If list is empty or null sent, null will be returned
     * @param points A list of Point3Ds
     * @return The closest point to the head of the ray
     */
    public Point3D findClosestPoint(List<Point3D> points) {
        if (points == null || points.isEmpty())
            return null;

        int pointsSize = points.size();
        Point3D lowPoint = points.get(0);
        Point3D optionalPoint;
        double distance = p0.distance(lowPoint);
        double distance2;

        for (int i = 1; i < pointsSize; i++) {
            optionalPoint = points.get(i);
            distance2 = p0.distance(optionalPoint);
            if (distance2 < distance) {
                distance = distance2;
                lowPoint = optionalPoint;
            }
        }

        return lowPoint;
    }

    /***
     * This method receives a list of GeoPoints, and returns the geoPoint that have the lowest distance from the head of the ray
     * If list is empty or null sent, null will be returned
     * @param points A list of GeoPoints
     * @return The closest point to the head of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null)
            return null;

        GeoPoint lowPoint = null;
        double distance = Double.POSITIVE_INFINITY;

        for (var p : points) {
            double distance2 = p0.distance(p.point);
            if (distance2 < distance) {
                distance = distance2;
                lowPoint = p;
            }
        }

        return lowPoint;
    }

    /***
     * calculate the place of point from p0 by t scale
     * @param t the scale to v
     * @return the point multiply by t from p0 in the angle of dir
     */
    public Point3D getPoint(double t) {

        return p0.add(dir.scale(t));
    }


    /***
     * Getter for the Point3D field of the ray
     * @return The p0 variable
     */
    public Point3D getP0() {
        return p0;
    }

    /***
     * Getter for the Vector field of the ray
     * @return The dir variable
     */
    public Vector getDir() {
        return dir;
    }

    /***
     * To string function
     * @return Information about the Point3D's coordinates
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

}
