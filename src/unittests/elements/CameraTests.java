package unittests.elements;

import geometries.*;
import org.junit.Test;
import elements.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
public class CameraTests {

    /**
     * Test method for
     * {@link elements.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void constructRay() {

        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setViewPlaneCenter(10).setViewPlaneSize(6, 6);


        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        camera.resetPixelSize(3,3);
        assertEquals("Bad ray", new Ray(new Vector(2, -2, -10), Point3D.ZERO),
                camera.constructRay(3, 3, 0, 0));

        // TC02: 4X4 Corner (0,0)
        camera.setViewPlaneSize(8, 8);
        camera.resetPixelSize(4,4);
        assertEquals("Bad ray", new Ray(new Vector(3, -3, -10), Point3D.ZERO),
                camera.constructRay(4, 4, 0, 0));

        // TC03: 4X4 Side (0,1)
        assertEquals("Bad ray", new Ray(new Vector(1, -3, -10), Point3D.ZERO),
                camera.constructRay(4, 4, 1, 0));

        // TC04: 4X4 Inside (1,1)
        assertEquals("Bad ray", new Ray(new Vector(1, -1, -10), Point3D.ZERO),
                camera.constructRay(4, 4, 1, 1));

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        camera.setViewPlaneSize(6, 6);
        camera.resetPixelSize(3,3);
        assertEquals("Bad ray", new Ray(new Vector(0, 0, -10), Point3D.ZERO),
                camera.constructRay(3, 3, 1, 1));

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals("Bad ray", new Ray(new Vector(0, -2, -10), Point3D.ZERO),
                camera.constructRay(3, 3, 1, 0));

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals("Bad ray", new Ray(new Vector(2, 0, -10), Point3D.ZERO),
                camera.constructRay(3, 3, 0, 1));
    }

    @Test
    public void changeAngle() {

        Scene scene1 = new Scene("Test scene");

        Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1),
                new Vector(0, 1, 0)).setViewPlaneCenter(1000) //
                .setViewPlaneSize(150, 150) //
                //.changeAngle(0)
                .changeDirection(new Point3D(0, 0, -1000), new Point3D(0, 0, -120));

        Geometry sphere = new Sphere(new Point3D(0, 0, -120), 50) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

        Geometry square1 = new Polygon(new Point3D(60, 0, -120),
                new Point3D(0, 60, -120), new Point3D(-60, 0, -120), new Point3D(0, -60, -120))
                .setEmission(new Color(java.awt.Color.GREEN)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

        scene1.geometries.add(sphere, square1);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional2", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }
}