package geometries;

import primitives.*;

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

    /**
     * This method receives a ray and returns a list of all the intersections points. In case there are none, null will be returned
     *
     * @param ray The ray which we find the intersections to the object
     * @return A list of the intersection points in form of Point3D. In case there are no intersections, null will be returned
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> planeIntersections = plane.findIntersections(ray);

        if (planeIntersections == null)
            return null;

        //create vectors
        Point3D point0 = ray.getP0();
        Vector rayDir = ray.getDir();
        Vector vec1 = vertices.get(0).subtract(point0);
        Vector vec2 = vertices.get(1).subtract(point0);
        Vector vec3 = vertices.get(2).subtract(point0);
        Vector N1 = vec1.crossProduct(vec2).normalize();
        Vector N2 = vec2.crossProduct(vec3).normalize();
        Vector N3 = vec3.crossProduct(vec1).normalize();

        //calc signs
        double checksign1 = N1.dotProduct(rayDir);
        if (Util.isZero(checksign1))
            //if zero => no intersections
            return null;

        //else the sign is not zero so check for next normal
        double checksign2 = N2.dotProduct(rayDir);
        if (!Util.checkSign(checksign1, checksign2) || Util.isZero(checksign2))
            //if sign is not equal or N2 is zero => no intersections
            return null;

        //else N1,N2 not zero and same sign!
        //so use N1 sign to calc the N3 sign (dont care because N2 same sign)
        checksign1 = N3.dotProduct(rayDir);
        if (!Util.checkSign(checksign1, checksign2) || Util.isZero(checksign1))
            //if sign is not equal or N2 is zero => no intersections
            return null;

        //if pass everything the point in triangle
        return planeIntersections;
    }
}
