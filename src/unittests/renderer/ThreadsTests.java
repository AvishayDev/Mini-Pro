package unittests.renderer;

import elements.Camera;
import elements.DirectionalLight;
import geometries.Geometry;
import geometries.Sphere;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

/**
 * Test rendering an image
 */
public class ThreadsTests {
    private final Camera camera = new Camera(new Point3D(0, 0, 1000), //
            new Vector(0, 0, -1), new Vector(0, 1, 0)).setViewPlaneCenter(1000).
            setViewPlaneSize(150, 150);

    private final Scene scene = new Scene("Test scene");
    private static final Color color = new Color(200, 0, 0);
    private static final Material mat = new Material() //
            .setKd(0.5).setKs(0.5).setShininess(60);
    private static Geometry sphere = new Sphere(new Point3D(-50, -50, -100), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

    /**
     * Produce a scene with a 3D model and render it into a png image
     */
    @Test
    public void teapot() {
        scene.geometries.add(sphere);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        ImageWriter imageWriter = new ImageWriter("teapot1", 500, 500);
        Render render = new Render().setImageWriter(imageWriter).setCamera(camera).setRayTracer(new RayTracerBasic(scene)) //
                .setMultithreading(3).setDebugPrint();
        render.renderImage(); //render.printGrid(50, java.awt.Color.YELLOW);
        //render.printGrid(50, new Color(256,256,256));
        render.writeToImage();
    }

}
