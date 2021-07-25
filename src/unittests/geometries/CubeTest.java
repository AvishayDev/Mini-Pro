package unittests.geometries;

import geometries.Cube;
import org.junit.Test;
import primitives.Point3D;

import java.util.List;

import static org.junit.Assert.*;

public class CubeTest {

    private static Cube cube = new Cube(0,-2,0,2,0,4);
    @Test
    public void findGeoIntersections() {


        // ============ Equivalence Partitions Tests ==============

        // TC0-1: the ray intersect the Cube twice on body
        //assertEquals("TC0-1: the ray intersect the Cube twice on body", );

        // TC0-2: the ray intersect the Cube once on body

        // TC0-3: the ray no intersect the Cube


        // =============== Boundary Values Tests ==================

        // ----------  intersection Boundary -----------

        // TC1-0: the ray intersect the Cube on vertex
        // TC1-1: the ray intersect the Cube on edge
        // TC1-11: the ray parallel to X axis
        // TC1-12: the ray parallel to Y axis
        // TC1-13: the ray parallel to Z axis

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
        // TC1-6: the ray start inside the Cube cross once
        // TC1-7: the ray start inside the Cube cross vertex
        // TC1-8: the ray start inside the Cube cross edge
        // TC1-10: the ray start inside the Cube parallel to Cube
    }

    @Test
    public void getNormal() {
    }

    @Test
    public void findGeoIntersectionsParticular() {
    }
}