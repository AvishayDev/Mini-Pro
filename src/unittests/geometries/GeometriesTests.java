package unittests.geometries;

import geometries.*;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Geometries class
 * @author Avihai & Avishay
 */
public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void findIntersections() {
        //list of geometries (meanwhile without bonus)
        //Plane, Sphere, Triangle
        Geometries geometries = new Geometries(new Plane(new Point3D(-1/3d,-1/3d,1/3),new Vector(-1,-1,1)),
                new Triangle(new Point3D(0,0,1),new Point3D(-1,0,0),new Point3D(0,-1,0)),
                new Sphere(new Point3D(0,0,1),1));
        Ray rayCheck;

        // ============ Equivalence Partitions Tests ==============

        // TC01: the ray cross some of the geometries
        //the ray cross Plane and Triangle
        rayCheck = new Ray(new Vector(-0.5d,-0.5d,0), new Point3D(-0.5d,0,0.1d));

        //check points
        assertEquals("POINTS Ray cross Plane and Triangle dosent work", List.of(new Point3D(-0.7,-0.2,0.1),new Point3D(-0.7,-0.2,0.1)),geometries.findIntersections(rayCheck));
        //check num of points
        assertEquals("NUM Ray cross Plane and Triangle dosent work", 2 ,geometries.findIntersections(rayCheck).size());


        // =============== Boundary Values Tests ==================

        //TC11: geometries is null
        Geometries geometries1 = new Geometries();
        assertEquals("Geometries is empty dosent work", null ,geometries1.findIntersections(rayCheck));

        //TC12: Ray dont cross any geometry
        rayCheck = new Ray(new Vector(-1,-1,0),new Point3D(-1,-1,0));
        assertEquals("Ray dosent cross geometries dosent work", null ,geometries.findIntersections(rayCheck));

        //TC13: Ray cross only one geometry
        //ray cross sphere
        rayCheck = new Ray(new Vector(0,0,1),new Point3D(0,0,1.5d));
        assertEquals("Ray cross only one geometry dosent work", new Point3D(0,0,2) ,geometries.findIntersections(rayCheck).get(0));

        //TC14: Ray cross all geometries
        rayCheck = new Ray(new Vector(-1,-1,0),new Point3D(-0.2d,-0.2d,0.5d));
        //check points
        assertEquals("POINTS Ray cross all geometries dosent work", List.of(new Point3D(-0.25d,-0.25d,0.5d),new Point3D(-0.25d,-0.25d,0.5d),new Point3D(-0.61d,-0.61d,0.5d)) ,geometries.findIntersections(rayCheck));
        //check num of points
        assertEquals("NUM Ray cross all geometries dosent work", 3 ,geometries.findIntersections(rayCheck).size());


    }
}