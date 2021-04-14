package unittests.geometries;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        //TC05: check for right choose of vector at down base and side
        assertEquals("Don't Choose DOWN correct vector",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnDownBase));
        //TC05: point on up base
        pointOnUpBase = cylinder.getAxisRay().getP0();
        assertEquals("cant find normal for up center base",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnUpBase));
        //TC06: point on down base
        pointOnDownBase = new Point3D(2,0,1);
        assertEquals("cant find normal for up center base",cylinder.getAxisRay().getDir(),cylinder.getNormal(pointOnDownBase));
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionPoint(){

        Cylinder cylinder= new Cylinder(new Vector(0,0,2),new Point3D(0,0,0),1,2);
        Ray rayCheck;

        // ============ Equivalence Partitions Tests ==============

        // TC0-1: the ray intersect the Cylinder twice on body
        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,2,0));
        assertEquals("TC01: the ray intersect the Cylinder twice", List.of(new Point3D(0,-1,1.5d),new Point3D(0,1,0.5d)),cylinder.findIntersections(rayCheck));

        //TC0-5: the ray intersect the Cylinder once in the body and once in the base
        rayCheck = new Ray(new Vector(0,-2,1), new Point3D(0,2,1));
        assertEquals("TC05: the ray intersect the Cylinder twice", List.of(new Point3D(0,1,1.5), new Point3D(0,0,2)));

        //TC0-6: the ray intersect the Cylinder twice in bases (not parallel to axis)
        rayCheck = new Ray(new Vector(0,1,3), new Point3D(0,-1,-1));
        assertEquals("TC06: the ray intersect the Cylinder twice", List.of(new Point3D(0,-0.6666666d,0), new Point3D(0,0,2)));

        // TC0-2: the ray intersect the Cylinder once in body
        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,0,1));
        assertEquals("TC02: the ray intersect the Cylinder once", List.of(new Point3D(0,-1,1.5d)),cylinder.findIntersections(rayCheck));

        //TC0-7: the ray intersect the Cylinder one in upper base (not parallel to axis)
        rayCheck = new Ray(new Vector(0,1,3), new Point3D(0,-0.3333333d,1));
        assertEquals("TC07: the ray intersect the Cylinder once in upper base", List.of(new Point3D(0,0,2)));

        //TC0-8: the ray intersect the Cylinder one in down base (not parallel to axis)
        rayCheck = new Ray(new Vector(0,-0.66666666d,-2), new Point3D(0,-0.3333333d,1));
        assertEquals("TC08: the ray intersect the Cylinder once in lower base", List.of(new Point3D(0,-0.6666666d,0)));

        // TC0-3: the ray not intersect the Cylinder side by body
        rayCheck = new Ray(new Vector(0,-1,1),new Point3D(0,-2,0));
        assertEquals("TC03: the ray not intersect the Cylinder", null,cylinder.findIntersections(rayCheck));

        // TC0-9: the ray not intersect the Cylinder above the base
        rayCheck = new Ray(new Vector(0,1,3),new Point3D(0,1,5));
        assertEquals("TC03: the ray not intersect the Cylinder", null,cylinder.findIntersections(rayCheck));

        // TC0-4: the ray not intersect the Cylinder but continuation intersect
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,2,1.5d));
        assertEquals("TC0-4: the ray not intersect the Cylinder but continuation intersect", null,cylinder.findIntersections(rayCheck));


        // =============== Boundary Values Tests ==================


        // ************ Base Checking *************

        //TC1-1: ray start on p0 



    }
}