package geometries;
import primitives.*;

import java.util.List;


/***
 * Represents Plane with vector and 3D point.
 */
public class Plane implements Geometry {
    Point3D q0;
    Vector normal;

    /***
     * Make's Plane with 3D point and vector.
     * @param q0 Start point of the Plane
     * @param normal Vector of the Plane
     */
    public Plane(Point3D q0,Vector normal){
        this.q0 = new Point3D(q0.getX(), q0.getY(), q0.getZ());
        this.normal= normal.normalized();
    }

    /***
     * Create Plane from 3 points.
     * From this points it will make a normal vector and start point
     * @param point1 1st point
     * @param point2 2nd point
     * @param point3 3rd point
     */
    public Plane(Point3D point1,Point3D point2, Point3D point3){
        q0=point1;
        Vector vec1 = point2.subtract(point1);
        Vector vec2 = point3.subtract(point1);
        normal = new Vector(vec1.crossProduct(vec2).getHead()).normalize();
    }

    /***
     * This function returns the normal of the sphere? Null for now
     * @param point A point3D object
     * @return The normal vector?
     */
    @Override
    public Vector getNormal(Point3D point) {
        return normal;
    }

    /***
     * To string method
     * @return Returns information about the plane's Point3D field and the Vector field
     */
    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    /***
     *
     * @return The start point of the Plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /***
     *
     * @return The vector of the Plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
