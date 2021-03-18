package unittests.geometries;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Avihai & Avishay
 */
public class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Cylinder cylinder =new Cylinder(new Vector(1,0,0),new Point3D(0,0,1),1,2);
        Point3D pointOnUpBase=new Point3D(2,0.5,1);
        Point3D pointOnDownBase=new Point3D(0,0.5,1);
        Point3D pointOnCylinder =new Point3D(1,1,1);
        // TC01: check for good vector at up base
        assertEquals("Can't Calc Up Base",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnUpBase));
        // TC02: check for good vector at down base
        assertEquals("Can't Calc Down Base",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnDownBase));
        // TC03: check for good vector at body object
        assertEquals("Can't Calc Body Point",new Vector(new Point3D(0,1,0)),cylinder.getNormal(pointOnCylinder));

        // =============== Boundary Values Tests ==================
        pointOnUpBase = new Point3D(0,1,1);
        pointOnDownBase = new Point3D(2,1,1);

        //TC04: check for right choose of vector at up base and side
        assertEquals("Don't Choose UP correct vector",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnUpBase));
        //TC04: check for right choose of vector at down base and side
        assertEquals("Don't Choose DOWN correct vector",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnDownBase));
    }

    @Test
    public void testFindIntersectionPoint(){

    }
}