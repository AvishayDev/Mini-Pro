package unittests.geometries;

import geometries.Plane;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Plane class
 * @author Avihai & Avishay
 */
public class PlaneTests {


    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here, values from here: https://web.ma.utexas.edu/users/m408m/Display12-5-4.shtml
        Plane plane = new Plane(new Point3D(-1,1,2), new Point3D(-4, 2,2), new Point3D(-2,1,5));
        Vector normal = new Vector(3,9,1).normalize();

        assertEquals("Bad normal to plane", normal, plane.getNormal());
        assertEquals("Bad normal to plane", normal, plane.getNormal(new Point3D(-1,1,2)));
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionPoint(){

        Plane plane = new Plane(new Point3D(-1d/3d,-1d/3d,1d/3d),new Vector(-1,-1,1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        Ray rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,-1,-0.5));
        Point3D pointCheck =new Point3D(0,-1,0);
        //point check
        assertEquals("POINT Ray intersects the plane dont work", pointCheck,plane.findIntersections(rayCheck).get(0));
        //num of points check
        assertEquals("NUM OF POINTS Ray intersects the plane dont work", 1,plane.findIntersections(rayCheck).size());

        // TC02: Ray does not intersect the plane
        rayCheck =new Ray(new Vector(0,0,1),new Point3D(0,-2,0));
        assertEquals("Ray intersects the plane dont work", null,plane.findIntersections(rayCheck));

        // =============== Boundary Values Tests ==================

        // TC11: Ray is parallel to the plane and not included
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,-2,0));
        assertEquals("Ray is parallel to the plane dont work", null,plane.findIntersections(rayCheck));

        // TC12: Ray is parallel to the plane and included
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,-1,0));
        assertEquals("Ray is parallel to the plane included dont work", null,plane.findIntersections(rayCheck));

        // TC13: Ray is orthogonal to the plane and cross the plane
        rayCheck = new Ray(plane.getNormal(),new Point3D(0.25d,0.25d,0.75d));
        pointCheck = new Point3D(0,0,1);
        assertEquals(" POINT Ray is orthogonal to the plane included dont work",pointCheck ,plane.findIntersections(rayCheck).get(0));
        assertEquals("NUM OF POINTS Ray is orthogonal to the plane included dont work",1 ,plane.findIntersections(rayCheck).size());

        // TC14: Ray is orthogonal to the plane start on plane
        rayCheck = new Ray(plane.getNormal(),new Point3D(0,0,1));
        assertEquals("Ray is orthogonal to the plane start on plane dont work", null,plane.findIntersections(rayCheck));

        // TC15: Ray is orthogonal to the plane not on plane
        rayCheck = new Ray(plane.getNormal(),new Point3D(0,0,2));
        assertEquals("Ray is orthogonal to the plane not included dont work", null,plane.findIntersections(rayCheck));

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(0,0,1));
        assertEquals("Ray is neither orthogonal to the plane start on plane dont work", null,plane.findIntersections(rayCheck));

        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane
        rayCheck = new Ray(new Vector(-1,0,0),plane.getQ0());
        assertEquals("Ray is neither orthogonal nor parallel to the plane start on q0 dont work", null,plane.findIntersections(rayCheck));


    }
}