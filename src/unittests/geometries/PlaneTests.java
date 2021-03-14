package unittests.geometries;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Plane class
 * @author Avihai & Avishay
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)}
     */
    @Test
    public void testConstaractorPoint3D(){

    }

    /**
     * Test method for {@link geometries.Plane#Plane(Point3D, Vector)}
     */
    @Test
    public void testConstaractorNormal(){

    }

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
}