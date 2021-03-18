package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;


/**
 * Unit tests for geometries.Tube class
 * @author Avihai & Avishay
 */
public class TubeTests {


    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tube = new Tube(new Vector(1,0,0), new Point3D(0,0,0), 1);
        Point3D P = new Point3D(5,0,1); // Point on the tube

        Point3D O = new Point3D(5,0,0); // is projection of P on cylinder's ray
        Vector normal = new Vector(0,0,1);

        assertEquals("Bad normal to tube", normal, tube.getNormal(P));
        // =============== Boundary Values Tests ==================

        try{
            tube.getNormal(new Point3D(0,1,0));
            fail("The point is Perpendicular to the central axis");
        }catch (IllegalArgumentException e) {}
    }

    @Test
    public void testFindIntersectionPoint(){

    }
}