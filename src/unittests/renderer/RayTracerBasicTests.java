package unittests.renderer;

import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * this class testing the functions in RayTracerBasic class
 */
public class RayTracerBasicTests {

    private Scene scene1 = new Scene("Test scene");

    /**
     * Test method for {@link renderer.RayTracerBasic#traceRay(primitives.Ray)}.
     */
    @Test
    public void traceRay() {

        RayTracerBasic tracer = new RayTracerBasic(scene1);
        Color finalColor = new Color(100, 100, 100);
        Geometry triangle = new Triangle(new Point3D(0, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 1, 0)).setEmission(finalColor);
        Geometry sphere = new Sphere(new Point3D(0, 0, 0), 1).setEmission(finalColor);
        Geometries geometries = new Geometries(triangle);
        Geometries geometries1 = new Geometries(sphere);
        Ray rayCheck;

        // ============ Equivalence Partitions Tests ==============

        //TC0-1: the ray cross geometry once
        scene1.setGeometries(geometries);
        rayCheck = new Ray(new Vector(0.25, 0.25, -1), new Point3D(0, 0, 1));
        assertEquals("TC0-1: the ray cross geometry once", finalColor, tracer.traceRay(rayCheck));

        //TC0-2: the ray cross the geometry twice
        scene1.setGeometries(geometries1);
        rayCheck = new Ray(new Vector(0, 0, -1), new Point3D(0, 0, 2));
        assertEquals("TC0-2: the ray cross the geometry twice", finalColor, tracer.traceRay(rayCheck));

        //TC0-3: the ray not cross geometry (background)
        rayCheck = new Ray(new Vector(1, 0, 0), new Point3D(0, 0, 3));
        assertEquals("TC0-3: the ray not cross geometry (background)", Color.BLACK, tracer.traceRay(rayCheck));

        // =============== Boundary Values Tests ==================

        //TC1-1: no geometries in the scene
        scene1.setGeometries(new Geometries());
        assertEquals("TC1-1: no geometries in the scene", Color.BLACK, tracer.traceRay(rayCheck));

    }
}