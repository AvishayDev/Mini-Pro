package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        return super.findIntersections(ray);
    } // Need to change this method into unique method of triangle
}
