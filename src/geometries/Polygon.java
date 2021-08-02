package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {


    private List<Vector> checkLines = new ArrayList<Vector>();

    /**
     * List of polygon's vertices
     */
    protected List<Point3D> vertices;

    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getNormal(Point3D.ZERO);

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }

        checkLines.add(vertices[vertices.length - 1].subtract(vertices[0]));
        //the length is 4 and above..
        for (int i = 0; i < vertices.length - 1; i++) {
            checkLines.add(vertices[i].subtract(vertices[i + 1]));
        }


    }

    /**
     * find the minimum and the maximum of the geometry border
     */
    @Override
    public void findMinMaxParticular() {

        // mix point coordinates
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        minZ = Double.POSITIVE_INFINITY;

        // max point coordinates
        maxX = Double.NEGATIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        maxZ = Double.NEGATIVE_INFINITY;

        for (Point3D p : vertices) {
            double pX = p.getX();
            double pY = p.getY();
            double pZ = p.getZ();
            //calc min
            if (pX < minX)
                minX = pX;
            if (pY < minY)
                minY = pY;
            if (pZ < minZ)
                minZ = pZ;

            //calc max
            if (pX > maxX)
                maxX = pX;
            if (pY > maxY)
                maxY = pY;
            if (pZ > maxZ)
                maxZ = pZ;
        }

    }

    @Override
    protected void move(Vector direction, double t) {

        int size = vertices.size();
        for (int i = 0; i < size; i++)
            vertices.set(i, vertices.get(i).add(direction, t));

    }

    /***
     * This function returns the normal of the Polygon? Null for now
     * @param point A point3D object
     * @return The normal vector?
     */
    @Override
    public Vector getNormal(Point3D point) {
        return plane.getNormal(point);
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

        List<GeoPoint> planeIntersections = plane.findGeoIntersectionsParticular(ray, maxDistance);
        //because we care about the distance in the plane we
        //don't need to care about it here

        int verticesSize = vertices.size();
        //check if the point on the plane
        if (planeIntersections == null)
            //when try to make v if one of them is the ZERO VECTOR
            //it mean the p0 on vertex => null

            //when try to make N if one of them is same as normal vector
            //it mean the p0 on the plane => null
            return null;

        GeoPoint geo = planeIntersections.get(0);
        try {
            double sign1 = geo.point.subtract(vertices.get(0)).crossProductValue(checkLines.get(0));
            double sign2;
            for (int i = 1; i < verticesSize; i++) {
                sign2 = geo.point.subtract(vertices.get(i)).crossProductValue(checkLines.get(i));
                if (Util.alignZero(sign1 * sign2) <= 0)
                    return null;
                sign1 = sign2;
            }

            geo.geometry = this;
            return planeIntersections;
        } catch (IllegalArgumentException e) {
            return null;
        }


    }
}
