package unittests.primitives;

import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;
import static primitives.Util.*;

/**
 * Unit tests for primitives.Vector class
 * @author Avihai & Avishay
 */

public class VectorTests {

    /**
     * Test method for {@link primitives.Vector#Vector(Coordinate,Coordinate,Coordinate)}.
     */
    @Test
    public void testConstructorCoordinate() {
        Coordinate zero = new Coordinate(0); // Coordinate at 0 location

        // Checking that zero vector can't be created
        try { // test zero vector
            new Vector(zero, zero, zero);
            fail("ERROR: zero vector does not throw an exception when coordinates constructor is used");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#Vector(double,double,double)}.
     */
    @Test
    public void testConstructorDouble() {

        // Checking that zero vector can't be created
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception when doubles constructor is used");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link primitives.Vector#Vector(primitives.Point3D)}.
     */
    @Test
    public void testConstructorPoint3D() {
        Point3D point3DZero = new Point3D(0, 0, 0); // Point3D at the location (0,0,0)

        // Checking that zero vector can't be created
        try { // test zero vector
            new Vector(point3DZero);
            fail("ERROR: zero vector does not throw an exception when Point3D constructor is used");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    public void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 3);
        Vector minus_v1 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(5, 7, 6);

        // Test that addition of vector is proper
        assertEquals("add(vector) method doesn't work properly", v1.add(v2), v3);
        assertEquals("add(vector) method doesn't work properly", v2.add(v1), v3);
        assertNotEquals("add(vector) method doesn't work properly", v3.add(v1), v2);

        // =============== Boundary Values Tests ==================
        // test zero vector from addition of opposite vectors
        try {
            v1.add(minus_v1);
            fail("add(vector) for opposite vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Vector)}.
     */
    @Test
    public void testSubtract() {

        Vector v3 = new Vector(5, 7, 6);

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 3);

        // Test that subtraction of vector is proper
        assertEquals("subtract(vector) method doesn't work properly", v3.subtract(v2), v1);
        assertEquals("subtract(vector) method doesn't work properly", v3.subtract(v1), v2);
        assertNotEquals("subtract(vector) method doesn't work properly", v2.subtract(v1), v3);

        // =============== Boundary Values Tests ==================
        // test zero vector from subtraction of identical vectors
        try {
            v1.subtract(v1);
            fail("subtract(vector) for identical vectors does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-1, -2, -3);
        Vector v3 = new Vector(3,6,9);

        // Test that scaling vectors works properly
        assertEquals("scale(double) method doesn't work properly", v1.scale(-1), v2);
        assertEquals("scale(double) method doesn't work properly", v1.scale(3), v3);
        assertNotEquals("scale(double) method doesn't work properly", v1.scale(2), v1);

        // =============== Boundary Values Tests ==================
        // test zero vector from scaling by 0
        try {
            v1.scale(0);
            fail("scale(double) for 0 does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // test dotProduct() between orthogonal vectors should be zero
        assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
        // test Dot-Product values are correct
        assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2)+28));
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    public void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);

        // test lengthSquared returns accurate value
        assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void testLength() {

        // test length returns accurate value
        assertTrue("ERROR: length() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    public void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());

        // ============ Equivalence Partitions Tests ==============
        Vector vCopyNormalize = vCopy.normalize();

        // Test that normalize() doesn't create a new vector, but changes the vector itself
        assertEquals("ERROR: normalize() function creates a new vector", vCopy, vCopyNormalize);
        // Test that the returned value is indeed the unit vector
        assertTrue("ERROR: normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
    }

    /**
     * Test method for {@link Vector#normalized()}
     */
    @Test
    public void testNormalized() {
        Vector v = new Vector(1, 2, 3);

        // ============ Not Equivalence Partitions Tests ==============
        Vector u = v.normalized();
        // Test that the returned value is indeed the unit vector
        assertTrue("ERROR: normalized() result is not a unit vector", isZero(u.length() - 1.0));
        // Test that normalized() method creates a new vector, and doesn't change the vector itself
        assertNotEquals("ERROR: normalized() function does not create a new vector", u, v);
    }

}