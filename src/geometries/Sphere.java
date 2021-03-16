package geometries;

import primitives.*;

import java.util.List;

/***
 * Represents Sphere with 3D point and radius.
 */
public class Sphere implements Geometry{
    Point3D center;
    double radius;

    /***
     * Make's Sphere from 3D point that
     * represents the center of the Sphere and radius
     * @param center center of the sphere
     * @param radius radius of the sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = new Point3D(center.getX(), center.getY(), center.getZ());
        if(Util.isZero(radius) || radius < 0)
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
    public Sphere(Coordinate x,Coordinate y,Coordinate z, double radius){
        this.center = new Point3D(x,y,z);
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Please Don't Choose radius zero");
        this.radius=radius;
    }

    /***
     * This function returns the normal of the sphere? Null for now
     * @param point A point3D object
     * @return The normal vector?
     */
    @Override
    public Vector getNormal(Point3D point) {

        return new Vector(point.subtract(this.center).getHead()).normalize();
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
     *
     * @return the center of the Sphere represents with 3D point.
     */
    public Point3D getCenter() {
        return center;
    }

    /***
     *
     * @return the radius of the Sphere
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
