package unittests.geometries;

import geometries.Cube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class CubeTest {

    private static Cube cube = new Cube(0,-2,0,2,0,4);
    @Test
    public void findGeoIntersections() {

        Ray rayCheck;
        // ============ Equivalence Partitions Tests ==============

        // TC0-1: the ray intersect the Cube twice on body
        rayCheck = new Ray(new Point3D(-1,-1,0),new Vector(1,0,1));
        assertEquals("TC0-1: the ray intersect the Cube twice on body", List.of(new Point3D(0,-1,1),new Point3D(2,-1,3)),cube.findGeoIntersections(rayCheck,Double.POSITIVE_INFINITY));

        // TC0-2: the ray intersect the Cube once on body
        rayCheck = new Ray(new Point3D(1,-1,0.5),new Vector(-1,0,0.5));
        assertEquals("TC0-2: the ray intersect the Cube once on body", List.of(new Point3D(0,-1,1)),cube.findGeoIntersections(rayCheck,Double.POSITIVE_INFINITY));

        // TC0-3: the ray no intersect the Cube
        rayCheck = new Ray(new Point3D(-1,-1,0.5),new Vector(1,-2,0.5));
        assertEquals("", null,cube.findGeoIntersections(rayCheck,Double.POSITIVE_INFINITY));


        // =============== Boundary Values Tests ==================

        // ----------  intersection Boundary -----------

        // TC1-0: the ray intersect the Cube on vertex
        rayCheck = new Ray(new Point3D(-1,-1,0.5),new Vector(1,1,3.5));
        assertEquals("", null,cube.findGeoIntersections(rayCheck,Double.POSITIVE_INFINITY));

        // TC1-1: the ray intersect the Cube on edge
        rayCheck = new Ray(new Point3D(-1,-1,0.5),new Vector(1,0,3.5));
        assertEquals("", null,cube.findGeoIntersections(rayCheck,Double.POSITIVE_INFINITY));

        // TC1-11: the ray parallel to X axis cross twice
        // TC1-12: the ray parallel to Y axis cross twice
        // TC1-13: the ray parallel to Z axis cross twice
        // TC1-23: the ray parallel to X axis cross once
        // TC1-24: the ray parallel to Y axis cross once
        // TC1-25: the ray parallel to Z axis cross once
        // TC1-26: the ray parallel to X axis dont cross
        // TC1-27: the ray parallel to Y axis dont cross
        // TC1-28: the ray parallel to Z axis dont cross

        // ------------ starting Boundary -----------

        //**** vertex ****
        // TC1-2: the ray start on vertex go inside the Cube cross once
        // TC1-3: the ray start on vertex inside the Cube cross another vertex
        // TC1-4: the ray start on vertex inside the Cube cross edge
        // TC1-5: the ray start on vertex go outside
        // TC1-5: the ray start on vertex parallel to Cube

        //**** edge ****
        // TC1-6: the ray start on edge go inside the Cube cross once
        // TC1-7: the ray start on edge inside the Cube cross vertex
        // TC1-8: the ray start on edge inside the Cube cross another edge
        // TC1-9: the ray start on edge go outside
        // TC1-10: the ray start on edge parallel to Cube

        //**** inside Cube ****
        // TC1-14: the ray start inside the Cube cross once
        // TC1-15: the ray start inside the Cube cross vertex
        // TC1-16: the ray start inside the Cube cross edge
        // TC1-17: the ray start inside the Cube parallel to Cube

        //**** onside Cube ****
        // TC1-18: the ray start onside go inside the Cube cross once
        // TC1-19: the ray start onside go inside the Cube cross vertex
        // TC1-20: the ray start onside go inside the Cube cross edge
        // TC1-21: the ray start onside go outside
        // TC1-22: the ray start onside parallel to Cube
    }

    @Test
    public void getNormal() {
    }

    @Test
    public void findGeoIntersectionsParticular() {
    }
}