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
     * Test method for {@link geometries.Cylinder#Cylinder(Ray, double, double)}
     */
    @Test
    public void testConstaractorRay(){


    }

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(Vector, Point3D, double, double)}
     */
    @Test
    public void testConstaractorVector(){

    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Cylinder cylinder =new Cylinder(new Vector(1,0,1),new Point3D(0,0,1),1,2);
        Point3D pointOnUpBase=new Point3D(2,0.5,1);
        Point3D pointOnDownBase=new Point3D(0,0.5,1);
        Point3D pointOnCylinder =new Point3D(1,1,1);
        assertEquals("Can't Calc Up Base",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnUpBase));
        assertEquals("Can't Calc Down Base",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnDownBase));
        assertEquals("Can't Calc Down Base",new Vector(new Point3D(0,1,0)),cylinder.getNormal(pointOnCylinder));
    }

    @Test
    public void testFindIntersectionPoint(){

    }
}