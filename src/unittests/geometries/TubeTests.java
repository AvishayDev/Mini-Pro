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
        assertEquals("TC01: the ray intersect the Tube twice", List.of(new Point3D(0,-1,1.5d),new Point3D(0,1,0.5d)),tube.findIntersections(rayCheck));

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

        // TC1-13: ray parallel to axisRay in tube start above level of p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(0.5,0,1));
        assertEquals("TC1-13: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-14: ray parallel to axisRay in tube start on same level as p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(0.5,0,0));
        assertEquals("TC1-14: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-15: ray parallel to axisRay in tube start down level of p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(0.5,0,-1));
        assertEquals("TC1-15: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-16: ray parallel to axisRay on tube start above level of p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(1,0,1));
        assertEquals("TC1-16: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-17: ray parallel to axisRay on tube start on same level as p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(1,0,0));
        assertEquals("TC1-17: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-18: ray parallel to axisRay on tube start down level of p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(1,0,-1));
        assertEquals("TC1-18: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-19: ray parallel to axisRay outside the tube start above level of p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(2,0,1));
        assertEquals("TC1-19: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-20: ray parallel to axisRay outside the tube start on same level as p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(2,0,0));
        assertEquals("TC1-20: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-21: ray parallel to axisRay outside the tube start down level of p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(2,0,-1));
        assertEquals("TC1-21: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-27: ray parallel to axisRay in tube start above p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(0,0,1));
        assertEquals("TC1-27: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-28: ray parallel to axisRay in tube start down p0 (expected no points)
        rayCheck = new Ray(new Vector(0,0,1), new Point3D(0,0,-1));
        assertEquals("TC1-28: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // ************** start on p0 **************

        // TC1-22: ray start on p0 not parallel not orthogonal to axisRay (expected one point)
        rayCheck = new Ray(new Vector(1,1,1),new Point3D(0,0,0));
        assertEquals("TC1-22: ray start on p0 not parallel not orthogonal to axisRay intersect the Tube once", List.of(new Point3D(0.7071067811865476d,0.7071067811865476d,0.7071067811865476d)),tube.findIntersections(rayCheck));

        // TC1-23: ray start on p0 orthogonal to axisRay (expected one point)
        rayCheck = new Ray(new Vector(1,0,0),new Point3D(0,0,0));
        assertEquals("TC02: the ray intersect the Tube once", List.of(new Point3D(1,0,0)),tube.findIntersections(rayCheck));

        // TC1-24: ray start on p0 parallel to axisRay (expected no points)
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,0,0));
        assertEquals("TC1-24: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // TC1-25: ray start on p0 parallel to axisRay negative direction
        rayCheck = new Ray(new Vector(0,0,-1),new Point3D(0,0,0));
        assertEquals("TC1-25: the ray not intersect the Tube and parallel to axisRay", null,tube.findIntersections(rayCheck));

        // ************** orthogonal to axisRay **************

        // TC1-46: ray start in tube orthogonal to axisRay above p0
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(0,0,1));
        assertEquals("TC1-46: ray start in tube orthogonal to axisRay above p0", List.of(new Point3D(-1,0,1)),tube.findIntersections(rayCheck));

        // TC1-47: ray start in tube orthogonal to axisRay down p0
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(0,0,-1));
        assertEquals("ray start in tube orthogonal to axisRay down p0", List.of(new Point3D(-1,0,-1)),tube.findIntersections(rayCheck));

        // TC1-48: Ray starts before the tangent point orthogonal to axisRay
        rayCheck = new Ray(new Vector(1,0,0),new Point3D(-1,1,0));
        assertEquals("TC1-48: Ray starts before the tangent point orthogonal to axisRay", null ,tube.findIntersections(rayCheck));

        // TC1-49: Ray starts at the tangent point orthogonal to axisRay
        rayCheck = new Ray(new Vector(1,0,0),new Point3D(0,1,0));
        assertEquals("TC1-49: Ray starts at the tangent point orthogonal to axisRay", null ,tube.findIntersections(rayCheck));

        // TC1-50: Ray starts after the tangent point orthogonal to axisRay
        rayCheck = new Ray(new Vector(1,0,0),new Point3D(1,1,0));
        assertEquals("TC1-50: Ray starts after the tangent point orthogonal to axisRay", null ,tube.findIntersections(rayCheck));


        // ------ goes inside -------
        // TC1-26: ray start on tube orthogonal to axisRay above level of p0 intersect axisRay (expected 1 point)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,1));
        assertEquals("TC1-26: the ray intersect once with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,1)),tube.findIntersections(rayCheck));

        // TC1-29: ray start on tube orthogonal to axisRay intersect p0 (expected 1 point)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,0));
        assertEquals("TC1-29: the ray intersect once with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,0)),tube.findIntersections(rayCheck));

        // TC1-30: ray start on tube orthogonal to axisRay down level of p0 intersect axisRay (expected 1 point)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,-1));
        assertEquals("TC1-30: the ray intersect once with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,-1)),tube.findIntersections(rayCheck));

        // TC1-31: ray start in tube orthogonal to axisRay above level of p0 intersect axisRay (expected 1 point)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(0.5,0,1));
        assertEquals("TC1-31: the ray intersect once with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,1)),tube.findIntersections(rayCheck));

        // TC1-32: ray start in tube orthogonal to axisRay intersect p0 (expected 1 point)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(0.5,0,0));
        assertEquals("TC1-32: the ray intersect once with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,0)),tube.findIntersections(rayCheck));

        // TC1-33: ray start in tube orthogonal to axisRay down level of p0 intersect axisRay (expected 1 point)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,-1));
        assertEquals("TC1-33: the ray intersect once with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,-1)),tube.findIntersections(rayCheck));

        // TC1-34: ray start outside the tube orthogonal to axisRay above level of p0 intersect axisRay (expected 2 points)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(2,0,1));
        assertEquals("TC1-34: the ray intersect twice with the Tube and orthogonal to axisRay", List.of(new Point3D(-1,0,1),new Point3D(1,0,1) ),tube.findIntersections(rayCheck));

        // TC1-35: ray start outside the tube orthogonal to axisRay intersect p0 (expected 2 points)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(2,0,0));
        assertEquals("TC1-35: the ray intersect twice with the Tube and orthogonal to axisRay", List.of( new Point3D(-1,0,0),new Point3D(1,0,0)),tube.findIntersections(rayCheck));

        // TC1-36: ray start outside the tube orthogonal to axisRay down level of p0 intersect axisRay (expected 2 points)
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(2,0,-1));
        assertEquals("TC1-36: the ray intersect twice with the Tube and orthogonal to axisRay", List.of( new Point3D(-1,0,-1),new Point3D(1,0,-1)),tube.findIntersections(rayCheck));

        // ------ goes outside -------
        // TC1-37: ray start on tube orthogonal to axisRay above level of p0 continuation intersect axisRay
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,1));
        assertEquals("TC1-37: ray start on tube orthogonal to axisRay above level of p0 continuation intersect axisRay", List.of(new Point3D(-1,0,1)),tube.findIntersections(rayCheck));

        // TC1-38: ray start on tube orthogonal to axisRay continuation intersect p0
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,0));
        assertEquals("TC1-38: ray start on tube orthogonal to axisRay continuation intersect p0", List.of(new Point3D(-1,0,0)),tube.findIntersections(rayCheck));

        // TC1-39: ray start on tube orthogonal to axisRay down level of p0 continuation intersect axisRay
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(1,0,-1));
        assertEquals("TC1-39: ray start on tube orthogonal to axisRay down level of p0 continuation intersect axisRay", List.of(new Point3D(-1,0,-1)),tube.findIntersections(rayCheck));

        // TC1-40: ray start in tube orthogonal to axisRay above level of p0 continuation intersect axisRay
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(-0.5d,0,1));
        assertEquals("TC1-40: ray start in tube orthogonal to axisRay above level of p0 continuation intersect axisRay", List.of(new Point3D(-1,0,1)),tube.findIntersections(rayCheck));

        // TC1-41: ray start in tube orthogonal to axisRay continuation intersect p0
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(-0.5d,0,0));
        assertEquals("TC1-41: ray start in tube orthogonal to axisRay continuation intersect p0", List.of(new Point3D(-1,0,0)),tube.findIntersections(rayCheck));

        // TC1-42: ray start in tube orthogonal to axisRay down level of p0 continuation intersect axisRay
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(-0.5d,0,-1));
        assertEquals("TC1-42: ray start in tube orthogonal to axisRay down level of p0 continuation intersect axisRay", List.of(new Point3D(-1,0,-1)),tube.findIntersections(rayCheck));

        // TC1-43: ray start outside the tube orthogonal to axisRay above level of p0 continuation intersect axisRay
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(-1.5d,0,1));
        assertEquals("TC1-43: ray start outside the tube orthogonal to axisRay above level of p0 continuation intersect axisRay", null ,tube.findIntersections(rayCheck));

        // TC1-44: ray start outside the tube orthogonal to axisRay continuation intersect p0
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(-1.5d,0,0));
        assertEquals("TC1-44: ray start outside the tube orthogonal to axisRay continuation intersect p0", null ,tube.findIntersections(rayCheck));

        // TC1-45: ray start outside the tube orthogonal to axisRay down level of p0 continuation intersect axisRay
        rayCheck = new Ray(new Vector(-1,0,0),new Point3D(-1.5d,0,-1));
        assertEquals("TC1-45: ray start outside the tube orthogonal to axisRay down level of p0 continuation intersect axisRay", null ,tube.findIntersections(rayCheck));




    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionsRay2() {
        Tube tube1 = new Tube(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)), 1d);
        Vector vAxis = new Vector(0, 0, 1);
        Tube tube2 = new Tube( new Ray(new Point3D(1, 1, 1), vAxis),1d);
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
        ray = new Ray(new Point3D(1, 1, 2), new Vector(1, 1, 0));
        assertNull("Must not be intersections", tube1.findIntersections(ray));

        // TC02: Ray's crosses the tube (2 points)
        ray = new Ray(new Point3D(0, 0, 0), new Vector(2, 1, 1));
        List<Point3D> result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections", List.of(new Point3D(0.4, 0.2, 0.2), new Point3D(2, 1, 1)), result);

        // TC03: Ray's starts within tube and crosses the tube (1 point)
        ray = new Ray(new Point3D(1, 0.5, 0.5), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 1)), result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line is parallel to the axis (0 points)
        // TC11: Ray is inside the tube (0 points)
        ray = new Ray(new Point3D(0.5, 0.5, 0.5), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC12: Ray is outside the tube
        ray = new Ray(new Point3D(0.5, -0.5, 0.5), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC13: Ray is at the tube surface
        ray = new Ray(new Point3D(2, 1, 0.5), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC14: Ray is inside the tube and starts against axis head
        ray = new Ray(new Point3D(0.5, 0.5, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC15: Ray is outside the tube and starts against axis head
        ray = new Ray(new Point3D(0.5, -0.5, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC16: Ray is at the tube surface and starts against axis head
        ray = new Ray(new Point3D(2, 1, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC17: Ray is inside the tube and starts at axis head
        ray = new Ray(new Point3D(1, 1, 1), vAxis);
        assertNull("must not be intersections", tube2.findIntersections(ray));

        // **** Group: Ray is orthogonal but does not begin against the axis head
        // TC21: Ray starts outside and the line is outside (0 points)
        ray = new Ray(new Point3D(0, 2, 2), new Vector(1, 1, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC22: The line is tangent and the ray starts before the tube (0 points)
        ray = new Ray(new Point3D(0, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC23: The line is tangent and the ray starts at the tube (0 points)
        ray = new Ray(new Point3D(1, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC24: The line is tangent and the ray starts after the tube (0 points)
        ray = new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC25: Ray starts before (2 points)
        ray = new Ray(new Point3D(0, 0, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections", List.of(new Point3D(0.4, 0.2, 2), new Point3D(2, 1, 2)), result);
        // TC26: Ray starts at the surface and goes inside (1 point)
        ray = new Ray(new Point3D(0.4, 0.2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 2)), result);
        // TC27: Ray starts inside (1 point)
        ray = new Ray(new Point3D(1, 0.5, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 2)), result);
        // TC28: Ray starts at the surface and goes outside (0 points)
        ray = new Ray(new Point3D(2, 1, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC29: Ray starts after
        ray = new Ray(new Point3D(4, 2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC30: Ray starts before and crosses the axis (2 points)
        ray = new Ray(new Point3D(1, -1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections", List.of(new Point3D(1, 0, 2), new Point3D(1, 2, 2)), result);
        // TC31: Ray starts at the surface and goes inside and crosses the axis
        ray = new Ray(new Point3D(1, 0, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 2)), result);
        // TC32: Ray starts inside and the line crosses the axis (1 point)
        ray = new Ray(new Point3D(1, 0.5, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 2)), result);
        // TC33: Ray starts at the surface and goes outside and the line crosses the
        // axis (0 points)
        ray = new Ray(new Point3D(1, 2, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC34: Ray starts after and crosses the axis (0 points)
        ray = new Ray(new Point3D(1, 3, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC35: Ray start at the axis
        ray = new Ray(new Point3D(1, 1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 2)), result);

        // **** Group: Ray is orthogonal to axis and begins against the axis head
        // TC41: Ray starts outside and the line is outside (
        ray = new Ray(new Point3D(0, 2, 1), new Vector(1, 1, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC42: The line is tangent and the ray starts before the tube
        ray = new Ray(new Point3D(0, 2, 1), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC43: The line is tangent and the ray starts at the tube
        ray = new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC44: The line is tangent and the ray starts after the tube
        ray = new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0));
        assertNull("must not be intersections", tube2.findIntersections(ray));
        // TC45: Ray starts before
        ray = new Ray(new Point3D(0, 0, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections", List.of(new Point3D(0.4, 0.2, 1), new Point3D(2, 1, 1)), result);
        // TC46: Ray starts at the surface and goes inside
        ray = new Ray(new Point3D(0.4, 0.2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 1)), result);
        // TC47: Ray starts inside
        ray = new Ray(new Point3D(1, 0.5, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 1)), result);
        // TC48: Ray starts at the surface and goes outside
        ray = new Ray(new Point3D(2, 1, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC49: Ray starts after
        ray = new Ray(new Point3D(4, 2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC50: Ray starts before and goes through the axis head
        ray = new Ray(new Point3D(1, -1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections", List.of(new Point3D(1, 0, 1), new Point3D(1, 2, 1)), result);
        // TC51: Ray starts at the surface and goes inside and goes through the axis
        // head
        ray = new Ray(new Point3D(1, 0, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 1)), result);
        // TC52: Ray starts inside and the line goes through the axis head
        ray = new Ray(new Point3D(1, 0.5, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 1)), result);
        // TC53: Ray starts at the surface and the line goes outside and goes through
        // the axis head
        ray = new Ray(new Point3D(1, 2, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC54: Ray starts after and the line goes through the axis head
        ray = new Ray(new Point3D(1, 3, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC55: Ray start at the axis head
        ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 1)), result);

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // begins against axis head
        Point3D p0 = new Point3D(0, 2, 1);
        // TC61: Ray's line is outside the tube
        ray = new Ray(p0, new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC62: Ray's line crosses the tube and begins before
        ray = new Ray(p0, new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 2), new Point3D(0.4, 1.8, 1.2)), result);
        // TC63: Ray's line crosses the tube and begins at surface and goes inside
        ray = new Ray(new Point3D(0.4, 1.8, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 1.8)), result);
        // TC64: Ray's line crosses the tube and begins inside
        ray = new Ray(new Point3D(1, 1.5, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(2, 1, 1.5)), result);
        // TC65: Ray's line crosses the tube and begins at the axis head
        ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(1, 2, 2)), result);
        // TC66: Ray's line crosses the tube and begins at surface and goes outside
        ray = new Ray(new Point3D(2, 1, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC67: Ray's line is tangent and begins before
        ray = new Ray(p0, new Vector(0, 2, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC68: Ray's line is tangent and begins at the tube surface
        ray = new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC69: Ray's line is tangent and begins after
        ray = new Ray(new Point3D(2, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // does not begin against axis head
        double sqrt2 = Math.sqrt(2);
        double denomSqrt2 = 1 / sqrt2;
        double value1 = 1 - denomSqrt2;
        double value2 = 1 + denomSqrt2;
        // TC71: Ray's crosses the tube and the axis
        ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new Point3D(value1, value1, 2 + value1), new Point3D(value2, value2, 2 + value2)), result);
        // TC72: Ray's crosses the tube and the axis head
        ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 2 intersections", 2, result.size());
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Bad intersections",
                List.of(new Point3D(value1, value1, value1), new Point3D(value2, value2, value2)), result);
        // TC73: Ray's begins at the surface and goes inside
        // TC74: Ray's begins at the surface and goes inside crossing the axis
        ray = new Ray(new Point3D(value1, value1, 2 + value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(value2, value2, 2 + value2)), result);
        // TC75: Ray's begins at the surface and goes inside crossing the axis head
        ray = new Ray(new Point3D(value1, value1, value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(value2, value2, value2)), result);
        // TC76: Ray's begins inside and the line crosses the axis
        ray = new Ray(new Point3D(0.5, 0.5, 2.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(value2, value2, 2 + value2)), result);
        // TC77: Ray's begins inside and the line crosses the axis head
        ray = new Ray(new Point3D(0.5, 0.5, 0.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(value2, value2, value2)), result);
        // TC78: Ray's begins at the axis
        ray = new Ray(new Point3D(1, 1, 3), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull("must be intersections", result);
        assertEquals("must be 1 intersections", 1, result.size());
        assertEquals("Bad intersections", List.of(new Point3D(value2, value2, 2 + value2)), result);
        // TC79: Ray's begins at the surface and goes outside
        ray = new Ray(new Point3D(2, 1, 2), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC80: Ray's begins at the surface and goes outside and the line crosses the
        // axis
        ray = new Ray(new Point3D(value2, value2, 2 + value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);
        // TC81: Ray's begins at the surface and goes outside and the line crosses the
        // axis head
        ray = new Ray(new Point3D(value2, value2, value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull("Bad intersections", result);

    }
}