package unittests.geometries;

import geometries.Cube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class CubeTest {

    private static Cube cube = new Cube(0, -2, 0, 2, 0, 4);

    @Test
    public void findGeoIntersections() {

        Ray rayCheck;
        // ============ Equivalence Partitions Tests ==============

        // TC0-1: the ray intersect the Cube twice on body
        rayCheck = new Ray(new Point3D(-1, -1, 0), new Vector(1, 0, 1));
        assertEquals("TC0-1: the ray intersect the Cube twice on body", List.of(new Point3D(0, -1, 1), new Point3D(2, -1, 3)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC0-2: the ray intersect the Cube once on body
        rayCheck = new Ray(new Point3D(1, -1, 0.5), new Vector(-1, 0, 0.5));
        assertEquals("TC0-2: the ray intersect the Cube once on body", List.of(new Point3D(0, -1, 1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC0-3: the ray no intersect the Cube
        rayCheck = new Ray(new Point3D(-1, -1, 0.5), new Vector(1, -2, 0.5));
        assertEquals("TC0-3: the ray no intersect the Cube", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));


        // =============== Boundary Values Tests ==================

        // ----------  intersection Boundary -----------

        // TC1-0: the ray intersect the Cube on vertex
        rayCheck = new Ray(new Point3D(-1, -1, 0.5), new Vector(1, 1, 3.5));
        assertEquals("TC1-0: the ray intersect the Cube on vertex", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-1: the ray intersect the Cube on edge
        rayCheck = new Ray(new Point3D(-1, -1, 0.5), new Vector(1, 0, 3.5));
        assertEquals("TC1-1: the ray intersect the Cube on edge", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-11: the ray parallel to X axis cross twice
        rayCheck = new Ray(new Point3D(-1, -1, 1), new Vector(2, 0, 0));
        assertEquals("TC1-11: the ray parallel to X axis cross twice", List.of(new Point3D(0, -1, 1), new Point3D(2, -1, 1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-12: the ray parallel to Y axis cross twice
        rayCheck = new Ray(new Point3D(1, 3, 1), new Vector(0, -2, 0));
        assertEquals("TC1-12: the ray parallel to Y axis cross twice", List.of(new Point3D(1, 0, 1), new Point3D(1, -2, 1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-13: the ray parallel to Z axis cross twice
        rayCheck = new Ray(new Point3D(1, -1, -1), new Vector(0, 0, 2));
        assertEquals("TC1-13: the ray parallel to Z axis cross twice", List.of(new Point3D(1, -1, 0), new Point3D(1, -1, 4)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-23: the ray parallel to X axis cross once
        rayCheck = new Ray(new Point3D(1, -1, 1), new Vector(2, 0, 0));
        assertEquals("TC1-23: the ray parallel to X axis cross once", List.of(new Point3D(2, -1, 1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-24: the ray parallel to Y axis cross once
        rayCheck = new Ray(new Point3D(1, -1, 1), new Vector(0, 2, 0));
        assertEquals("TC1-24: the ray parallel to Y axis cross once", List.of(new Point3D(1, 0, 1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-25: the ray parallel to Z axis cross once
        rayCheck = new Ray(new Point3D(1, -1, 1), new Vector(0, 0, 1));
        assertEquals("TC1-25: the ray parallel to Z axis cross once", List.of(new Point3D(1, -1, 4)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-26: the ray parallel to X axis dont cross
        rayCheck = new Ray(new Point3D(1, 1, 1), new Vector(2, 0, 0));
        assertEquals("TC1-26: the ray parallel to X axis dont cross", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-27: the ray parallel to Y axis dont cross
        rayCheck = new Ray(new Point3D(1, 0.5, 1), new Vector(0, 0.5, 0));
        assertEquals("TC1-27: the ray parallel to Y axis dont cross", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-28: the ray parallel to Z axis dont cross
        rayCheck = new Ray(new Point3D(1, 1, 1), new Vector(0, 0, 1));
        assertEquals("TC1-28: the ray parallel to Z axis dont cross", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // ------------ starting Boundary -----------

        //**** vertex ****
        // TC1-2: the ray start on vertex go inside the Cube cross once
        rayCheck = new Ray(new Point3D(0, -2, 4), new Vector(1, 2, -3));
        assertEquals("", List.of(new Point3D(1,0,1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-3: the ray start on vertex inside the Cube cross another vertex
        rayCheck = new Ray(new Point3D(0, -2, 4), new Vector(2, 2, -4));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-4: the ray start on vertex inside the Cube cross edge
        rayCheck = new Ray(new Point3D(0, -2, 4), new Vector(2, 2, -3));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-5: the ray start on vertex go outside
        rayCheck = new Ray(new Point3D(0, -2, 4), new Vector(1, 1, 1));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-5: the ray start on vertex parallel to Cube
        rayCheck = new Ray(new Point3D(0, -2, 4), new Vector(0, 2, 0));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        //**** edge ****
        // TC1-6: the ray start on edge go inside the Cube cross once
        rayCheck = new Ray(new Point3D(0, -1, 4), new Vector(1, 1, -3));
        assertEquals("", List.of(new Point3D(1,0,1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-7: the ray start on edge go inside the Cube cross vertex
        rayCheck = new Ray(new Point3D(0, -1, 4), new Vector(2, 1, -4));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-8: the ray start on edge go inside the Cube cross another edge
        rayCheck = new Ray(new Point3D(0, -1, 4), new Vector(2, 0, -4));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-9: the ray start on edge go outside
        rayCheck = new Ray(new Point3D(0, -1, 4), new Vector(1, 1, 1));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-10: the ray start on edge parallel to Cube
        rayCheck = new Ray(new Point3D(0, -1, 4), new Vector(1, 1, 0));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));


        //**** inside Cube ****
        // TC1-14: the ray start inside the Cube cross once
        rayCheck = new Ray(new Point3D(1, -1, 3), new Vector(-1, 0, -1));
        assertEquals("", List.of(new Point3D(0,-1,2)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-15: the ray start inside the Cube cross vertex
        rayCheck = new Ray(new Point3D(1, -1, 3), new Vector(1, -1, 1));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-16: the ray start inside the Cube cross edge
        rayCheck = new Ray(new Point3D(1, -1, 3), new Vector(0, -1, 1));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-17: the ray start inside the Cube parallel to Cube
        rayCheck = new Ray(new Point3D(1, -1, 3), new Vector(1, -0.5, 0));
        assertEquals("", List.of(new Point3D(2,-1.5,3)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));


        //**** onside Cube ****
        // TC1-18: the ray start onside go inside the Cube cross once
        rayCheck = new Ray(new Point3D(0, -1, 1), new Vector(2, -0.5, 2));
        assertEquals("", List.of(new Point3D(2,-1.5,3)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-19: the ray start onside go inside the Cube cross vertex
        rayCheck = new Ray(new Point3D(0, -1, 1), new Vector(2, -1, 3));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-20: the ray start onside go inside the Cube cross edge
        rayCheck = new Ray(new Point3D(0, -1, 1), new Vector(2, 0, 3));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-21: the ray start onside go outside
        rayCheck = new Ray(new Point3D(0, -1, 1), new Vector(-1, 0, 1));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-22: the ray start onside parallel to Cube go inside
        rayCheck = new Ray(new Point3D(0, -1, 1), new Vector(1, 0, 0));
        assertEquals("", List.of(new Point3D(2,-1,1)), cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

        // TC1-29: the ray start onside parallel to Cube go outside
        rayCheck = new Ray(new Point3D(0, -1, 1), new Vector(-1, 0, 0));
        assertEquals("", null, cube.findGeoIntersections(rayCheck, Double.POSITIVE_INFINITY));

    }

    @Test
    public void getNormal() {
    }

    @Test
    public void findGeoIntersectionsParticular() {
    }
}