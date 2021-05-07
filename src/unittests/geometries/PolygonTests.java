/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.*;
import org.junit.Test;
import static geometries.Intersectable.GeoPoint;
import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionPoint(){

        Polygon polygon = new Polygon(new Point3D(1,0,0),new Point3D(-0.5,1.5,0),
                new Point3D(-1,1,1),new Point3D(-1,0,2),new Point3D(1,-1.5,1.5));
        Ray rayCheck;
        // ============ Equivalence Partitions Tests ==============

        // TC01: the ray intersect the polygon
        rayCheck = new Ray(new Vector(1,0,1),new Point3D(0,-0.5,0));
        assertEquals("POINT Ray intercet the Polygon dosent work", List.of(new Point3D(0.75d,-0.5d,0.75d)),polygon.findIntersections(rayCheck));
        assertEquals("NUM Ray intercet the Polygon dosent work", 1,polygon.findIntersections(rayCheck).size());

        // TC02: the ray dont intersect the polygon against edge
        rayCheck = new Ray(new Vector(1,0,1), new Point3D(-1,-1,2));
        assertEquals("Ray dont intercet the Polygon against edge dosent work", null,polygon.findIntersections(rayCheck));

        // TC03: the ray dont intersect the polygon against vertex
        rayCheck = new Ray(new Vector(1,0,1), new Point3D(-2,0,2.5d));
        assertEquals("Ray dont intercet the Polygon against vertex dosent work", null,polygon.findIntersections(rayCheck));


        // =============== Boundary Values Tests ==================

        // TC11: the ray dont intersect the polygon On edge
        rayCheck = new Ray(new Vector(1,0,1), new Point3D(-1.5d,0.5d,1));
        assertEquals("Ray dont intercet the Polygon On edge dosent work", null,polygon.findIntersections(rayCheck));


        // TC12: the ray dont intersect the polygon In vertex
        rayCheck = new Ray(new Vector(1,0,1),new Point3D(-1.5d,0,1.5d));
        assertEquals("Ray dont intercet the Polygon In vertex dosent work", null,polygon.findIntersections(rayCheck));


        // TC13: the ray dont intersect the polygon On edge's continuation
        rayCheck = new Ray(new Vector(1,0,1), new Point3D(-1.5d,-0.5d,2));
        assertEquals("Ray dont intercet the Polygon On edge's continuation dosent work", null,polygon.findIntersections(rayCheck));


        // TC14: the ray dont intersect the polygon On edge's continuation intersection
        rayCheck = new Ray(new Vector(1,0,1), new Point3D(-14/7d,3/7,12/7));
        assertEquals("Ray dont intercet the Polygon On edge's continuation intersection dosent work", null,polygon.findIntersections(rayCheck));


    }

}
