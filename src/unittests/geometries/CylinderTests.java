package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Avihai & Avishay
 */
public class CylinderTests {


    /**
     * Test method for {@link geometries.Cylinder#Cylinder(Ray, double, double)}
     */
    @Test
    public void testConstaractorRay(){


    }

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(Vector, Point3D, double, double)}
     */
    @Test
    public void testConstaractorVector(){

    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Sphere sphere = new Sphere(new Point3D(0,0,0),2);
        Point3D point1 = new Point3D(0,0,2);
        Vector vec1 = new Vector(0,0,1);

        assertEquals("Normal value not valid",vec1,new Vector(point1.subtract(sphere.getCenter()).getHead()).normalize());

    }
}