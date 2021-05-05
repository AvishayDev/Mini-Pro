package unittests.primitives;

import geometries.Intersectable;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class RayTests {


    @Test
    public void findClosestPoint() {

        Ray ray = new Ray(new Point3D(1,1,1), new Vector(1,1,1));
        Point3D B = new Point3D(2,1,1); // Closest
        Point3D C = new Point3D(2,2,2);
        Point3D D = new Point3D(3,3,4);
        Point3D E = new Point3D(0,0,0);
        Point3D F = new Point3D(-1,-0.5,3);

        // ============ Equivalence Partitions Tests ==============

        //TC0-0: point in the middle of the list

        assertEquals("TC0-0: point in the middle of the list",B,ray.findClosestPoint(List.of(C,D,B,F,E)));

        // =============== Boundary Values Tests ==================

        //TC1-0: empty list
        assertNull("TC1-0: empty list", ray.findClosestPoint(List.of()));

        //TC1-1: first point in the list is the closet

        assertEquals("TC1-1: first point in the list is the closet",B,ray.findClosestPoint(List.of(B,C,D,E,F)));

        //TC1-2: last point in the list is closet

        assertEquals("TC1-2: last point in the list is closet",B,ray.findClosestPoint(List.of(F,E,D,C,B)));

    }

    @Test
    public void findClosestGeoPoint() {

        Ray ray = new Ray(new Point3D(1,1,1), new Vector(1,1,1));
        Point3D B = new Point3D(2,1,1); // Closest
        Point3D C = new Point3D(2,2,2);
        Point3D D = new Point3D(3,3,4);
        Point3D E = new Point3D(0,0,0);
        Point3D F = new Point3D(-1,-0.5,3);
        Triangle triangle = new Triangle(new Point3D(0,2,0),new Point3D(2,0,0),new Point3D(4,4,4.5d));

        Intersectable.GeoPoint pB = new Intersectable.GeoPoint(triangle,B);
        Intersectable.GeoPoint pC = new Intersectable.GeoPoint(triangle,C);
        Intersectable.GeoPoint pD = new Intersectable.GeoPoint(triangle,D);
        Intersectable.GeoPoint pE = new Intersectable.GeoPoint(triangle,E);
        Intersectable.GeoPoint pF = new Intersectable.GeoPoint(triangle,F);
        // ============ Equivalence Partitions Tests ==============

        //TC0-0: point in the middle of the list

        assertEquals("TC0-0: point in the middle of the list",pB,ray.findClosestGeoPoint(List.of(pC,pD,pB,pF,pE)));

        // =============== Boundary Values Tests ==================

        //TC1-0: empty list
        assertNull("TC1-0: empty list", ray.findClosestGeoPoint(List.of()));

        //TC1-1: first point in the list is the closet

        assertEquals("TC1-1: first point in the list is the closet",pB,ray.findClosestGeoPoint(List.of(pB,pC,pD,pE,pF)));

        //TC1-2: last point in the list is closet

        assertEquals("TC1-2: last point in the list is closet",pB,ray.findClosestGeoPoint(List.of(pF,pE,pD,pC,pB)));

    }
}