package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

        O =new Point3D(0,1,0);

        assertEquals(" t==0 dont work",new Vector(0,1,0),tube.getNormal(O) );
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionPoint(){

        Tube tube = new Tube(new Ray(new Vector(0,0,1),new Point3D(0,0,0)),1);
        Ray rayCheck;
        // ============ Equivalence Partitions Tests ==============

        // TC0-1: the ray intersect the Tube twice

        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,2,0));
        assertEquals("TC01: the ray intersect the Tube twice", List.of(new Point3D(0,1,0.5d),new Point3D(0,-1,1.5d)),tube.findIntersections(rayCheck));

        // TC0-2: the ray intersect the Tube once

        rayCheck = new Ray(new Vector(0,-2,1),new Point3D(0,0,1));
        assertEquals("TC02: the ray intersect the Tube once", List.of(new Point3D(0,-1,1.5d)),tube.findIntersections(rayCheck));

        // TC0-3: the ray not intersect the Tube
        rayCheck = new Ray(new Vector(0,-1,1),new Point3D(0,-2,0));
        assertEquals("TC03: the ray not intersect the Tube", null,tube.findIntersections(rayCheck));

        // TC0-4: the ray not intersect the Tube but continuation intersect
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,2,0));
        assertEquals("TC0-4: the ray not intersect the Tube but continuation intersect", null,tube.findIntersections(rayCheck));


        // =============== Boundary Values Tests ==================

        // TC1-1: Ray starts at tube and goes inside not orthogonal
        rayCheck = new Ray(new Vector(-1,-1,-1),new Point3D(0,1,1));
        assertEquals("TC1-1: Ray starts at tube and goes inside", List.of(new Point3D(-1,0,0)),tube.findIntersections(rayCheck));

        // TC1-2: Ray starts at tube and goes outside not orthogonal
        rayCheck = new Ray(new Vector(0,1,-1),new Point3D(0,1,1));
        assertEquals("TC1-2: Ray starts at tube and goes outside", null,tube.findIntersections(rayCheck));

        // TC1-3: Ray starts before the tube intersect p0 not orthogonal
        rayCheck = new Ray(new Vector(0,-2,-2),new Point3D(0,2,2));
        assertEquals("TC1-3: Ray starts before the tube intersect p0", List.of(new Point3D(0,-1,-1),new Point3D(0,1,1)),tube.findIntersections(rayCheck));

        // TC1-4: Ray starts at tube and goes inside intersect p0 not orthogonal
        rayCheck = new Ray(new Vector(0,-1,-1),new Point3D(0,1,1));
        assertEquals("TC1-4: Ray starts at tube and goes inside intersect p0", List.of(new Point3D(0,-1,-1)),tube.findIntersections(rayCheck));

        // TC1-5: Ray starts inside and continuation intersect p0 not orthogonal
        rayCheck = new Ray(new Vector(0,-0.5d,-0.5d),new Point3D(0,-0.5d,-0.5d));
        assertEquals("TC1-5: Ray starts inside and continuation intersect p0", List.of(new Point3D(0,-1,-1)),tube.findIntersections(rayCheck));

        // TC1-7: Ray starts at tube and goes outside and continuation intersect p0 not orthogonal
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,1,1));
        assertEquals("TC1-7: Ray starts at tube and goes outside and continuation intersect p0", null,tube.findIntersections(rayCheck));

        // TC1-8: Ray starts after tube and continuation intersect p0 not orthogonal
        rayCheck = new Ray(new Vector(0,1,1),new Point3D(0,2,2));
        assertEquals("TC1-8: Ray starts after tube and continuation intersect p0", null,tube.findIntersections(rayCheck));

        // TC1-9: Ray starts before the tangent point not orthogonal
        rayCheck = new Ray(new Vector(0,2,3),new Point3D(-1,-1,-1));
        assertEquals("TC1-9: Ray starts before the tangent point not orthogonal", null,tube.findIntersections(rayCheck));

        // TC1-10: Ray starts at the tangent point not orthogonal
        rayCheck = new Ray(new Vector(0,1,2),new Point3D(-1,0,0));
        assertEquals("TC1-10: Ray starts at the tangent point not orthogonal", null,tube.findIntersections(rayCheck));

        // TC1-11: Ray starts after the tangent point not orthogonal
        rayCheck = new Ray(new Vector(0,0.5d,1.5d),new Point3D(-1,0.5,0.5));
        assertEquals("TC1-11: Ray starts after the tangent point not orthogonal", null,tube.findIntersections(rayCheck));

        // TC1-12: Ray starts outside tube and p0 orthogonal to q0 not orthogonal to tube

        // ************** parallel to axisRay **************

        // TC1-13: ray parallel to axisRay in tube start above level of p0
        // TC1-14: ray parallel to axisRay in tube start on same level as p0
        // TC1-15: ray parallel to axisRay in tube start down level of p0
        // TC1-16: ray parallel to axisRay on tube start above level of p0
        // TC1-17: ray parallel to axisRay on tube start on same level as p0
        // TC1-18: ray parallel to axisRay on tube start down level of p0
        // TC1-19: ray parallel to axisRay outside the tube start above level of p0
        // TC1-20: ray parallel to axisRay outside the tube start on same level as p0
        // TC1-21: ray parallel to axisRay outside the tube start down level of p0
        // TC1-27: ray parallel to axisRay in tube start above p0
        // TC1-28: ray parallel to axisRay in tube start down p0

        // ************** start on p0 **************

        // TC1-22: ray start on p0 not parallel not orthogonal to axisRay
        // TC1-23: ray start on p0 orthogonal to axisRay
        // TC1-24: ray start on p0 parallel to axisRay
        // TC1-25: ray start on p0 parallel to axisRay negative direction

        // ************** orthogonal to axisRay **************

        // ------ goes inside -------
        // TC1-26: ray start on tube orthogonal to axisRay above level of p0 intersect axisRay
        // TC1-29: ray start on tube orthogonal to axisRay intersect p0
        // TC1-30: ray start on tube orthogonal to axisRay down level of p0 intersect axisRay
        // TC1-31: ray start in tube orthogonal to axisRay above level of p0 intersect axisRay
        // TC1-32: ray start in tube orthogonal to axisRay intersect p0
        // TC1-33: ray start in tube orthogonal to axisRay down level of p0 intersect axisRay
        // TC1-34: ray start outside the tube orthogonal to axisRay above level of p0 intersect axisRay
        // TC1-35: ray start outside the tube orthogonal to axisRay intersect p0
        // TC1-36: ray start outside the tube orthogonal to axisRay down level of p0 intersect axisRay

        // ------ goes outside -------
        // TC1-37: ray start on tube orthogonal to axisRay above level of p0 continuation intersect axisRay
        // TC1-38: ray start on tube orthogonal to axisRay continuation intersect p0
        // TC1-39: ray start on tube orthogonal to axisRay down level of p0 continuation intersect axisRay
        // TC1-40: ray start in tube orthogonal to axisRay above level of p0 continuation intersect axisRay
        // TC1-41: ray start in tube orthogonal to axisRay continuation intersect p0
        // TC1-42: ray start in tube orthogonal to axisRay down level of p0 continuation intersect axisRay
        // TC1-43: ray start outside the tube orthogonal to axisRay above level of p0 continuation intersect axisRay
        // TC1-44: ray start outside the tube orthogonal to axisRay continuation intersect p0
        // TC1-45: ray start outside the tube orthogonal to axisRay down level of p0 continuation intersect axisRay

        // TC1-46: ray start in tube orthogonal to axisRay above p0
        // TC1-47: ray start in tube orthogonal to axisRay down p0
        // TC1-48: Ray starts before the tangent point orthogonal to axisRay
        // TC1-49: Ray starts at the tangent point orthogonal to axisRay
        // TC1-50: Ray starts after the tangent point orthogonal to axisRay



    }
}