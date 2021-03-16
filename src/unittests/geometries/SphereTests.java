package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Vector;

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

    }
}