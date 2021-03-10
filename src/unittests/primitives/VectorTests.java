package unittests.primitives;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class VectorTests {

    @Test
    public void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 3);
        Vector minus_v1= new Vector(-1,-2,-3);

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
        } catch (Exception e) {}
    }

    @Test
    public void subtract() {

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

    @Test
    public void scale() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-1, -2, -3);
        Vector v3 = new Vector(3,6,9);

        // Test that scaling vectors works properly
        assertEquals("scale(double) method doesn't work properly", v1.scale(-1), v2);
        assertEquals("scale(double) method doesn't work properly", v1.scale(3), v3);
        assertNotEquals("scale(double) method doesn't work properly", v1.scale(0), v1);

        // =============== Boundary Values Tests ==================
        // test zero vector from scaling by 0
        try {
            v1.scale(0);
            fail("scale(double) for 0 does not throw an exception");
        } catch (Exception e) {}
    }

    @Test
    public void crossProduct() {
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

    @Test
    public void dotProduct() {
    }

    @Test
    public void lengthSquared() {
        // test lengthSquared...
        Vector v1 = new Vector(1, 2, 3);
        assertTrue("ERROR: lengthSquared() wrong value", !isZero(v1.lengthSquared() - 14));
    }

    @Test
    public void length() {
        // test length...
        assertTrue("ERROR: length() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
    }

    @Test
    public void normalize() {
    }

    @Test
    public void normalized() {
    }
}