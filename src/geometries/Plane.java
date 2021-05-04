package geometries;

import primitives.*;

import java.util.List;

/***
 * Represents Plane with vector and 3D point.
 */
public class Plane extends Geometry {

    //Note
    private Point3D q0;
    private Vector normal;

    /***
     * Make's Plane with 3D point and vector.
     * @param q0 Start point of the Plane
     * @param normal Vector of the Plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalized();
    }

    /***
     * Create Plane from 3 points.
     * From this points it will make a normal vector and start point
     * @param point1 1st point
     * @param point2 2nd point
     * @param point3 3rd point
     * @throws IllegalArgumentException when the points are on the same line
     */
    public Plane(Point3D point1, Point3D point2, Point3D point3) {
        q0 = point1;
        Vector vec1 = point2.subtract(point1);
        Vector vec2 = point3.subtract(point1);
        normal = vec1.crossProduct(vec2).normalize();
    }

    /***
     * This function returns the normal of the sphere? Null for now
     * @param point A point3D object
     * @return The normal vector
     */
    @Override
    public Vector getNormal(Point3D point) {
        return getNormal();
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
     * This method is a getter for the field q0
     * @return The start point of the Plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /***
     * This method is a getter for the field normal
     * @return The vector of the Plane
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * This method receives a ray and returns a list of all the intersections points. In case there are none, null will be returned
     *
     * @param ray The ray which we find the intersections to the object
     * @return A list of the intersection points in form of Point3D. In case there are no intersections, null will be returned
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector u;
        double t;
        double t1;

        try {
            u = q0.subtract(ray.getP0());
            t = normal.dotProduct(u);
            t1 = normal.dotProduct(ray.getDir());
            if (Util.isZero(t1))
                //its mean p0 is start on the plane => no intersections
                //or the dir is orthogonal to the normal => no intersections
                return null;
            t = t / t1;

        } catch (IllegalArgumentException e) {
            //its mean p0==q0 => no intersections
            return null;
        }

        if (Util.alignZero(t) <= 0)
            //if t==0 its mean p0 on the plane
            //and if t<0 its mean p0 is over the plane
            return null;

        //if pass all of this, there is intersection in the plane
        return List.of(ray.getPoint(t));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }
}
