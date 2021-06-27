package geometries;

import primitives.*;

import java.util.List;

/***
 * Represents Sphere with 3D point and radius.
 */
public class Sphere extends Geometry {

    /**
     * A Point3D that is in the location of the center of the sphere.
     */
    private Point3D center;
    /**
     * A double value that represent the value of the radius of the sphere.
     */
    private double radius;

    /***
     * Make's Sphere from 3D point that
     * represents the center of the Sphere and radius
     * @param center center of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        if (Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Please Don't Choose radius zero");

        this.radius = radius;
    }

    /***
     * Alternative to the constructor above, different order of parameters.
     * @param radius radius of the sphere
     * @param center center of the sphere
     */
    public Sphere(double radius, Point3D center) {
        this(center, radius);
    }

    /**
     * find the minimum and the maximum of the geometry border
     */
    @Override
    public void findMinMaxParticular() {

        minX = center.getX() - radius;
        minY = center.getY() - radius;
        minZ = center.getZ() - radius;

        maxX = minX + 2 * radius;
        maxY = minY + 2 * radius;
        maxZ = minZ + 2 * radius;

    }

    /***
     * This function returns the normal of the sphere? Null for now
     * @param point A point3D object
     * @return The normal vector?
     */
    @Override
    public Vector getNormal(Point3D point) {
        return point.subtract(center).normalize();
    }

    /***
     * To string function
     * @return Information about the sphere's center and radius
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    /***
     * This method is a getter for the field center
     * @return the center of the Sphere represents with 3D point.
     */
    public Point3D getCenter() {
        return center;
    }

    /***
     * This method is a getter for the field radius
     * @return the radius of the Sphere
     */
    public double getRadius() {
        return radius;
    }

    /**
     * This method receives a ray and his max distance and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none or pass the max distance, null will be returned.
     *
     * @param ray         The ray which we find the intersections to the object.
     * @param maxDistance the maximum distance for the ray to go
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray, double maxDistance) {
        Vector u;
        try {
            u = center.subtract(ray.getP0());
        } catch (IllegalArgumentException e) {
            //p0 on the center point
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        double tm = u.dotProduct(ray.getDir());
        double d2 = u.lengthSquared() - (tm * tm);
        double checkValue = (radius * radius) - d2;

        if (Util.alignZero(checkValue) <= 0)
            // there are no intersections
            return null;

        double th = Math.sqrt(checkValue);
        double t1 = tm + th;

        if (Util.alignZero(t1) <= 0)
            //if true, p0 is on the sphere or out of it => no points
            return null;

        boolean checkMaxT1 = Util.alignZero(t1 - maxDistance) < 0;

        double t2 = tm - th;
        if (Util.alignZero(t2 - maxDistance) >= 0)
            return null;
        //if t2 <=0 don't take, else take both
        if (Util.alignZero(t2) <= 0)

            //if true, p0 on the sphere or in it => one point
            return checkMaxT1 ? List.of(new GeoPoint(this, ray.getPoint(t1))) : null;


        //if pass all of this it mean p0 cross twice the sphere
        return checkMaxT1 //
                ? List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1))) //
                : List.of(new GeoPoint(this, ray.getPoint(t2)));
    }
}
