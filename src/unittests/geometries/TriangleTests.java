package unittests.geometries;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;


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

    }
}