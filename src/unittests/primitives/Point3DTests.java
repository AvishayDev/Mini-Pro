package unittests.primitives;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * class for test the functions in Point3D
 */
public class Point3DTests {

    /**
     * Test method for {@link primitives.Point3D#add(Vector)}.
     */
    @Test
    public void testAdd() {
        Point3D p1 = new Point3D(4, 5, 3);
        Vector v2 = new Vector(1, 2, 3);
        Vector minus_v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        Point3D p3 = new Point3D(5, 7, 6);
        Point3D p4 = new Point3D(3,3,0);

        // Test that addition of vector is proper
        assertEquals("add(vector) method doesn't work properly", p1.add(v2), p3);
        assertEquals("add(vector) method doesn't work properly", p1.add(minus_v2), p4);
        assertNotEquals("add(vector) method doesn't work properly", p3.add(minus_v2), p3);
    }

    /**
     * Test method for {@link primitives.Point3D#subtract(Point3D)}.
     */
    @Test
    public void testSubtract() {
        Point3D p1 = new Point3D(4, 5, 3);
        Point3D p2 = new Point3D(1, 2, 3);
        Point3D minus_p2 = new Point3D(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(3,3,0);
        Vector v4 = new Vector(5, 7, 6);


        // Test that subtraction of points is proper
        assertEquals("subtract(Point3D) method doesn't work properly", p1.subtract(p2), v3);
        assertEquals("subtract(Point3D) method doesn't work properly", p1.subtract(minus_p2), v4);
        assertNotEquals("subtract(Point3D) method doesn't work properly", p2.subtract(minus_p2), v4);

        // =============== Boundary Values Tests ==================
        // test zero vector from subtraction of identical points
        try {
            p1.subtract(p1);
            fail("subtract(Point3D) for identical Point3Ds does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D)}.
     */
    @Test
    public void testDistanceSquared() {
        Point3D p1 = new Point3D(4, 5, 3);
        Point3D p2 = new Point3D(1,2,1);

        // test distanceSquared returns accurate value
        assertTrue("ERROR: distanceSquared(Point3D) wrong value", isZero(p1.distanceSquared(p2) - 22));
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(Point3D)}.
     */
    @Test
    public void testDistance() {
        Point3D p1 = new Point3D(0,3,4);
        Point3D p2 = new Point3D(0,0,0);

        // test distance returns accurate value
        assertTrue("ERROR: distance(Point3D) wrong value", isZero(p1.distance(p2) - 5));
    }
}