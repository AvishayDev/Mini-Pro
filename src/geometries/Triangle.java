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
        Vector normal1 = vec1.crossProduct(vec2).normalize();
        Vector normal2 = vec2.crossProduct(vec3).normalize();
        Vector normal3 = vec3.crossProduct(vec1).normalize();

        //calc signs
        double checkSign1 = normal1.dotProduct(rayDir);
        double checkSign2 = normal2.dotProduct(rayDir);
        if (Util.alignZero(checkSign1*checkSign2)<=0)
            //if least from zero => not equal signs
            //if zero => outside triangle
            return null;

        //so use N1 sign to calc the N3 sign (don't care because N2 same sign)
        checkSign1 = normal3.dotProduct(rayDir);
        if (Util.alignZero(checkSign1*checkSign2)<=0)
            //if least from zero => not equal signs
            //if zero => outside triangle
            return null;


        //if pass everything the point in triangle
        return planeIntersections;
    }

    // note, pretty much copy paste from above
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        List<Point3D> planeIntersections = plane.findIntersections(ray);

        if (planeIntersections == null)
            return null;

        //create vectors
        Point3D point0 = ray.getP0();
        Vector rayDir = ray.getDir();
        Vector vec1 = vertices.get(0).subtract(point0);
        Vector vec2 = vertices.get(1).subtract(point0);
        Vector vec3 = vertices.get(2).subtract(point0);
        Vector normal1 = vec1.crossProduct(vec2).normalize();
        Vector normal2 = vec2.crossProduct(vec3).normalize();
        Vector normal3 = vec3.crossProduct(vec1).normalize();

        //calc signs
        double checkSign1 = normal1.dotProduct(rayDir);
        double checkSign2 = normal2.dotProduct(rayDir);
        if (Util.alignZero(checkSign1*checkSign2)<=0)
            //if least from zero => not equal signs
            //if zero => outside triangle
            return null;

        //so use N1 sign to calc the N3 sign (don't care because N2 same sign)
        checkSign1 = normal3.dotProduct(rayDir);
        if (Util.alignZero(checkSign1*checkSign2)<=0)
            //if least from zero => not equal signs
            //if zero => outside triangle
            return null;


        //if pass everything the point in triangle
        return List.of(new GeoPoint(this,planeIntersections.get(0)));
    }
}
