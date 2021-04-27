package geometries;

import primitives.*;

import java.util.List;

/***
 * Represents Sphere with 3D point and radius.
 */
public class Sphere implements Geometry{
    private Point3D center;
    private double radius;

    /***
     * Make's Sphere from 3D point that
     * represents the center of the Sphere and radius
     * @param center center of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = new Point3D(center.getX(), center.getY(), center.getZ());
        if (Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Please Don't Choose radius zero");
        this.radius = radius;
    }

    /***
     * Make's Sphere from 3 coordinates and radius.
     * From the 3 coordinates it make 3D point and use it
     * @param x First coordinate for the 3D point
     * @param y Second coordinate for the 3D point
     * @param z Third coordinate for the 3D point
     * @param radius Radius to the Sphere
     */
    public Sphere(Coordinate x, Coordinate y, Coordinate z, double radius) {
        this.center = new Point3D(x, y, z);
        if (Util.alignZero(radius)<=0)
            throw new IllegalArgumentException("Please Don't Choose radius zero");
        this.radius = radius;
    }

    /***
     * This function returns the normal of the sphere? Null for now
     * @param point A point3D object
     * @return The normal vector?
     */
    @Override
    public Vector getNormal(Point3D point) {

        return point.subtract(this.center).normalized();
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
     * This method receives a ray and returns a list of all the intersections points. In case there are none, null will be returned
     * @param ray The ray which we find the intersections to the object
     * @return A list of the intersection points in form of Point3D. In case there are no intersections, null will be returned
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector u;
        try {
            u = this.center.subtract(ray.getP0());
        } catch (IllegalArgumentException e) {
            //p0 on the center point
            return List.of(ray.getPoint(radius));
        }
        //double tm = Math.abs(u.dotProduct(ray.getDir()));
        double tm =u.dotProduct(ray.getDir());
        double d2 = u.lengthSquared()-(tm*tm);
        double checkValue = (radius*radius)-d2;
        if(Util.alignZero(checkValue) <= 0)
            // there are no intersections
            return null;

        double th =Math.sqrt(checkValue);
        double t1 = tm+th;

        if (Util.alignZero(t1) <= 0)
            //if true, p0 is on the sphere or out of it => no points
            return null;

        double t2 = tm - th;
        //if t2<=0 dont take, else take both
        if (Util.alignZero(t2) <= 0)
            //if true, p0 on the sphere or in it => one point
            return List.of(ray.getPoint(t1));

        //if pass all of this it mean p0 cross twice the sphere
        return List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}
