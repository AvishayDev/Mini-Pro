package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Avihai & Avishay
 */
public class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#Sphere(Point3D, double)}
     */
    @Test
    public void testConstaractorCenter(){

    }

    /**
     * Test method for {@link geometries.Sphere#Sphere(Coordinate, Coordinate, Coordinate, double)}
     */
    @Test
    public void testConstaractorCoordinate(){

    }


    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sphere = new Sphere(new Point3D(0,0,0),2);
        Point3D point1 = new Point3D(0,0,2);
        Vector vec1 = new Vector(0,0,1);

        assertEquals("Normal value not valid",vec1,sphere.getNormal(point1));


    }

    @Test
    public void testFindIntersectionPoint(){
        // Test 1 : Ray from the outside of the sphere, should hit it twice
        Sphere sphere1 = new Sphere(new Point3D(1,1,1), 1);
        Ray ray1 = new Ray(new Vector(1,0,2) , new Point3D(0,1,0));
        List<Point3D> list1 = new ArrayList<Point3D>();
        list1.add(new Point3D(0.2,1,0.4)); list1.add(new Point3D(1,1,2));
        assertEquals("Two intersection points doesn't return proper value", list1, sphere1.findIntersections(ray1));




    }
}