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

        //tests wait for answer: TC1-21 TC1-23 TC1-32 TC1-34
        //tests dont pass: TC1-38

        Cylinder cylinder= new Cylinder(new Vector(0,0,1),new Point3D(0,0,0),1,2);
        Ray rayCheck;

        // ============ Equivalence Partitions Tests ==============

        // TC0-1: the ray intersect the Cylinder twice on body
        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,2,0));
        assertEquals("TC01: the ray intersect the Cylinder twice", List.of(new Point3D(0,-1,1.5d),new Point3D(0,1,0.5d)),cylinder.findIntersections(rayCheck));

        //TC0-5: the ray intersect the Cylinder once in the body and once in the base
        rayCheck = new Ray(new Vector(0,-2,1), new Point3D(0,2,1));
        assertEquals("TC05: the ray intersect the Cylinder twice", List.of(new Point3D(0,1,1.5),new Point3D(0,0,2)),cylinder.findIntersections(rayCheck) );

        //TC0-6: the ray intersect the Cylinder twice in bases (not parallel to axis)
        rayCheck = new Ray(new Vector(0,1,3), new Point3D(0,-1,-1));
        assertEquals("TC06: the ray intersect the Cylinder twice", List.of(new Point3D(0,-2/3d,0), new Point3D(0,0,2)), cylinder.findIntersections(rayCheck));

        // TC0-2: the ray intersect the Cylinder once in body
        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,0,1));
        assertEquals("TC02: the ray intersect the Cylinder once", List.of(new Point3D(0,-1,1.5d)),cylinder.findIntersections(rayCheck));

        //TC0-7: the ray intersect the Cylinder one in upper base (not parallel to axis)
        rayCheck = new Ray(new Vector(0,1,3), new Point3D(0,-1/3d,1));
        assertEquals("TC07: the ray intersect the Cylinder once in upper base", List.of(new Point3D(0,0,2)), cylinder.findIntersections(rayCheck));

        //TC0-8: the ray intersect the Cylinder one in down base (not parallel to axis)
        rayCheck = new Ray(new Vector(0,-2/3d,-2), new Point3D(0,-1/3d,1));
        assertEquals("TC08: the ray intersect the Cylinder once in lower base", List.of(new Point3D(0,-2/3d,0)), cylinder.findIntersections(rayCheck));

        // TC0-3: the ray not intersect the Cylinder side by body
        rayCheck = new Ray(new Vector(0,-1,1),new Point3D(0,-2,0));
        assertEquals("TC03: the ray not intersect the Cylinder", null,cylinder.findIntersections(rayCheck));

        // TC0-9: the ray not intersect the Cylinder above the base
        rayCheck = new Ray(new Vector(0,1,3),new Point3D(0,1,5));
        assertEquals("TC09: the ray not intersect the Cylinder", null,cylinder.findIntersections(rayCheck));

        // TC0-4: the ray not intersect the Cylinder but continuation intersect
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,2,1.5d));
        assertEquals("TC0-4: the ray not intersect the Cylinder but continuation intersect", null,cylinder.findIntersections(rayCheck));


        // =============== Boundary Values Tests ==================

        //------ start on p0 -------

        //TC1-1: ray start on p0 intersect body
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,0,0));
        assertEquals("TC1-1: ray start on p0 intersect body", List.of(new Point3D(0,1,1)), cylinder.findIntersections(rayCheck));

        //TC1-2: ray start on p0 intersect upper base
        rayCheck = new Ray(new Vector(0,0.5,2),new Point3D(0,0,0));
        assertEquals("TC1-2: ray start on p0 intersect upper base", List.of(new Point3D(0,0.5,2)), cylinder.findIntersections(rayCheck));

        //TC1-3: ray start on p0 intersect upper base parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,0,0));
        assertEquals("TC1-3: ray start on p0 intersect upper base parallel to axisRay", List.of(new Point3D(0,0,2)), cylinder.findIntersections(rayCheck));

        //TC1-13: ray start on p0 not intersect
        rayCheck = new Ray(new Vector(-1,-1,-1),new Point3D(0,0,0));
        assertEquals("TC1-13: ray start on p0 not intersect", null, cylinder.findIntersections(rayCheck));

        //TC1-14: ray start on p0 not intersect parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,-1),new Point3D(0,0,0));
        assertEquals("TC1-14: ray start on p0 not intersect parallel to axisRay", null, cylinder.findIntersections(rayCheck));

        //TC1-21: ray start on p0 orthogonal to axisRay
        //rayCheck = new Ray(new Vector(0,1,0),new Point3D(0,0,0));
        //assertEquals("TC1-21: ray start on p0 orthogonal to axisRay", null, cylinder.findIntersections(rayCheck));


        //------ start on down base -------

        //TC1-4: ray start on down base intersect upper base
        rayCheck = new Ray(new Vector(0,-0.5,2),new Point3D(0,0.5d,0));
        assertEquals("TC1-4: the ray intersect the Cylinder once in upper base", List.of(new Point3D(0,0,2)), cylinder.findIntersections(rayCheck));

        //TC1-5: ray start on down base intersect body
        rayCheck = new Ray(new Vector(1,-0.5,1),new Point3D(0,0.5d,0));
        assertEquals("TC1-5: the ray intersect the Cylinder once in body", List.of(new Point3D(1,0,1)), cylinder.findIntersections(rayCheck));

        //TC1-6: ray start on down base intersect upper base parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,2),new Point3D(0,0.5d,0));
        assertEquals("TC1-6: the ray intersect the Cylinder once in upper base, parallel to axisRay", List.of(new Point3D(0,0.5d,2)), cylinder.findIntersections(rayCheck));

        //TC1-15: ray start on down base not intersect
        rayCheck = new Ray(new Vector(0,-0.5d,-2),new Point3D(0,0.5d,0));
        assertEquals("TC0-15: the ray not intersect the Cylinder", null,cylinder.findIntersections(rayCheck));

        //TC1-16: ray start on down base not intersect parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,-2),new Point3D(0,0.5d,0));
        assertEquals("TC0-15: the ray not intersect the Cylinder, parallel to axisRay", null,cylinder.findIntersections(rayCheck));

        //TC1-22: ray start on down base orthogonal to axisRay
        rayCheck = new Ray(new Vector(0,0.5,0),new Point3D(0,0.5d,0));
        assertEquals("TC1-22: ray start on down base orthogonal to axisRay", List.of(new Point3D(0,1,0)), cylinder.findIntersections(rayCheck));

        //------ start on p1 -------

        //TC1-7: ray start on p1 intersect body
        rayCheck = new Ray(new Vector(0,1,-1.5d),new Point3D(0,0,2));
        assertEquals("TC1-7: ray start on p1 intersect body", List.of(new Point3D(0,1,0.5d)), cylinder.findIntersections(rayCheck));

        //TC1-8: ray start on p1 intersect down base
        rayCheck = new Ray(new Vector(0,0.5d,-2),new Point3D(0,0,2));
        assertEquals("TC1-8: ray start on p1 intersect down base", List.of(new Point3D(0,0.5d,0)), cylinder.findIntersections(rayCheck));

        //TC1-9: ray start on p1 intersect down base parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,-1),new Point3D(0,0,2));
        assertEquals("TC1-9: ray start on p1 intersect down base parallel to axisRay", List.of(new Point3D(0,0,0)), cylinder.findIntersections(rayCheck));

        //TC1-17: ray start on p1 not intersect
        rayCheck = new Ray(new Vector(1,1,3),new Point3D(0,0,2));
        assertEquals("TC1-17: ray start on p1 not intersect", null, cylinder.findIntersections(rayCheck));

        //TC1-18: ray start on p1 not intersect parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,0,2));
        assertEquals("TC1-18: ray start on p1 not intersect parallel to axisRay", null, cylinder.findIntersections(rayCheck));

        //TC1-23: ray start on p1 orthogonal to axisRay
        //rayCheck = new Ray(new Vector(0,1,0),new Point3D(0,0,2));
        //assertEquals("TC1-23: ray start on p1 orthogonal to axisRay", null, cylinder.findIntersections(rayCheck));


        //------ start on upper base -------

        //TC1-10: ray start on upper base intersect down base
        rayCheck = new Ray(new Vector(0,-0.5,-2),new Point3D(0,0.5d,2));
        assertEquals("TC1-10: the ray intersect the Cylinder once in lower base", List.of(new Point3D(0,0,0)), cylinder.findIntersections(rayCheck));

        //TC1-11: ray start on upper base intersect body
        rayCheck = new Ray(new Vector(0,0.5,-1),new Point3D(0,0.5d,2));
        assertEquals("TC1-11: the ray intersect the Cylinder once in body", List.of(new Point3D(0,1,1)), cylinder.findIntersections(rayCheck));

        //TC1-12: ray start on upper base intersect down base parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,-2),new Point3D(0,0.5,2));
        assertEquals("TC1-12: the ray intersect the Cylinder once in lower base, parallel to axisRay", List.of(new Point3D(0,0.5d,0)), cylinder.findIntersections(rayCheck));

        //TC1-19: ray start on upper base not intersect
        rayCheck = new Ray(new Vector(0,0.5d,1),new Point3D(0,0.5d,2));
        assertEquals("TC0-19: the ray not intersect the Cylinder", null,cylinder.findIntersections(rayCheck));

        //TC1-20: ray start on upper base not intersect parallel to axisRay
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,0.5d,2));
        assertEquals("TC0-15: the ray not intersect the Cylinder, parallel to axisRay", null,cylinder.findIntersections(rayCheck));

        //TC1-24: ray start on upper base orthogonal to axisRay
        rayCheck = new Ray(new Vector(0,0.5,0),new Point3D(0,0.5d,2));
        assertEquals("TC1-22: ray start on upper base orthogonal to axisRay", List.of(new Point3D(0,1,2)), cylinder.findIntersections(rayCheck));

        //------ intersection of body and base ------

        //TC1-25: ray intersect 2 intersections of body and base
        rayCheck = new Ray(new Vector(0,-3,3),new Point3D(0,2,-1));
        assertEquals("TC1-25: ray intersect 2 intersections of body and base", List.of( new Point3D(0,-1,2),new Point3D(0,1,0)), cylinder.findIntersections(rayCheck));

        //TC1-26: ray intersect intersection of body and base and body
        rayCheck = new Ray(new Vector(0,-4,2),new Point3D(0,3,-1));
        assertEquals("TC1-26: ray intersect intersection of body and base and body", List.of(new Point3D(0,-1,1), new Point3D(0,1,0)), cylinder.findIntersections(rayCheck));

        //TC1-27: ray intersect intersection of body and base and base
        rayCheck = new Ray(new Vector(0,-3,4),new Point3D(0,2.5d,-2));
        assertEquals("TC1-27: ray intersect intersection of body and base and base", List.of(new Point3D(0,1,0),new Point3D(0,-0.5d,2)), cylinder.findIntersections(rayCheck));

        //TC1-28: ray start on intersection of body and base not parallel not orthogonal to axisRay go outside
        rayCheck = new Ray(new Vector(0,1,2),new Point3D(0,1,0));
        assertEquals("TC1-28: ray start on intersection of body and base not parallel not orthogonal to axisRay go outside", null, cylinder.findIntersections(rayCheck));

        //TC1-29: ray start on intersection of body and base parallel to axisRay down base
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,1,0));
        assertEquals("TC1-29: ray start on intersection of body and base parallel to axisRay down base", null, cylinder.findIntersections(rayCheck));

        //TC1-30: ray start on intersection of body and base parallel to axisRay upper base
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,1,2));
        assertEquals("TC1-30: ray start on intersection of body and base parallel to axisRay upper base", null, cylinder.findIntersections(rayCheck));

        //TC1-31: ray start on intersection of body and base orthogonal to axisRay upper base go outside
        rayCheck = new Ray(new Vector(0,1,0),new Point3D(0,1,2));
        assertEquals("TC1-31: ray start on intersection of body and base orthogonal to axisRay upper base go outside", null, cylinder.findIntersections(rayCheck));

        //TC1-32: ray start on intersection of body and base orthogonal to axisRay upper base go inside
        //rayCheck = new Ray(new Vector(0,-1,0),new Point3D(0,1,2));
        //assertEquals("TC1-32: ray start on intersection of body and base orthogonal to axisRay upper base go inside", null, cylinder.findIntersections(rayCheck));

        //TC1-33: ray start on intersection of body and base orthogonal to axisRay down base go outside
        rayCheck = new Ray(new Vector(0,1,0),new Point3D(0,1,0));
        assertEquals("TC1-33: ray start on intersection of body and base orthogonal to axisRay down base go outside", null, cylinder.findIntersections(rayCheck));

        //TC1-34: ray start on intersection of body and base orthogonal to axisRay down base go inside
        //rayCheck = new Ray(new Vector(0,-1,0),new Point3D(0,1,0));
        //assertEquals("TC1-34: ray start on intersection of body and base orthogonal to axisRay down base go inside", null, cylinder.findIntersections(rayCheck));

        //TC1-35: ray start on intersection of body and base and intersect body
        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,1,0));
        assertEquals("TC1-35: ray start on intersection of body and base and intersect body", List.of(new Point3D(0,-1,1)), cylinder.findIntersections(rayCheck));

        //TC1-36: ray start on intersection of body and base and intersect base
        rayCheck = new Ray(new Vector(0,-1.5d,2),new Point3D(0,1,0));
        assertEquals("TC1-36: ray start on intersection of body and base and intersect base", List.of(new Point3D(0,-0.5d,2)), cylinder.findIntersections(rayCheck));

        //TC1-37: ray start in Cylinder and intersect intersection of body and base
        rayCheck = new Ray(new Vector(0,-1,1),new Point3D(0,0,1));
        assertEquals("TC1-37: ray start in Cylinder and intersect intersection of body and base", List.of(new Point3D(0,-1,2)), cylinder.findIntersections(rayCheck));

        //TC1-38: ray intersect tangent point in body and base intersection
        rayCheck = new Ray(new Vector(0,-1,2),new Point3D(0,2,0));
        assertEquals("TC1-38: ray intersect tangent point in body and base intersection", null, cylinder.findIntersections(rayCheck));



    }
}