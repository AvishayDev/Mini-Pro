package unittests.geometries;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static geometries.Intersectable.GeoPoint;

/**
 * Unit tests for geometries.Triangle class
 * @author Avihai & Avishay
 */
public class TriangleTests {


    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here, values from here: https://web.ma.utexas.edu/users/m408m/Display12-5-4.shtml
        Triangle triangle = new Triangle(new Point3D(-1,1,2), new Point3D(-4, 2,2), new Point3D(-2,1,5));
        Vector normal = new Vector(3,9,1).normalize();

        assertEquals("Bad normal to triangle", normal, triangle.getNormal(new Point3D(-1,1,2)));
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionPoint(){

        Triangle triangle = new Triangle(new Point3D(0,0,1),new Point3D(-1,0,0),new Point3D(0,-1,0));
        Ray rayCheck;
        Point3D pointCheck;
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the triangle Inside
        pointCheck = new Point3D(-0.25d,-0.25d,0.5d);
        rayCheck =new Ray(new Vector(-1,-1,0),new Point3D(0,0,0.5d));
        assertEquals("POINT Ray intersects the plane dont work", pointCheck,triangle.findIntersections(rayCheck).get(0));
        assertEquals("NUM OF POINTS Ray intersects the plane dont work", 1,triangle.findIntersections(rayCheck).size());

        // TC02: Ray not intersects the triangle Outside against edge
        rayCheck = new Ray(new Vector(-1,-1,0),new Point3D(0.5d,-0.5d,0.5d));
        assertEquals("Ray not intersects the triangle dont work", null, triangle.findIntersections(rayCheck));

        // TC03: Ray not intersects the triangle Outside against vertex
        rayCheck = new Ray(new Vector(-1,-1,0),new Point3D(0.5d,0.5d,1.5d));
        assertEquals("Ray not intersects the triangle dont work", null, triangle.findIntersections(rayCheck));

        // =============== Boundary Values Tests ==================

        // TC11: Ray intersects the triangle before On edge
        rayCheck = new Ray(new Vector(-0.5d,-0.5d,0), new Point3D(0.25d,-0.5d,0.25d));
        assertEquals("Ray intersects the triangle On edge dont work", null, triangle.findIntersections(rayCheck));

        // TC12: Ray intersects the triangle before In vertex
        rayCheck = new Ray(new Vector(-0.5d,-0.5d,0), new Point3D(0.25d,0.25d,1));
        assertEquals("Ray intersects the triangle In vertex dont work", null, triangle.findIntersections(rayCheck));

        // TC13: Ray intersects the triangle before On edge's continuation
        rayCheck = new Ray(new Vector(-0.5d,-0.5d,0),new Point3D(0.25d,0.75d,1.5d));
        assertEquals("Ray intersects the triangle On edge's continuation dont work", null, triangle.findIntersections(rayCheck));

    }

}