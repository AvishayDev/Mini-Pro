package unittests.elements;


import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

public class FunTest {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1),
            new Vector(0, 1, 0))//
            .setViewPlaneCenter(1000) //
            .setViewPlaneSize(150, 150);

    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1),
            new Vector(0, 1, 0)) //
            .setViewPlaneCenter(1000)//
            .setViewPlaneSize(200, 200); //

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry sphere = new Sphere(new Point3D(-50, -50, -100), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
    private static Geometries square = new Geometries(new Polygon(new Point3D(0, 0, -50), new Point3D(50, 0, -100), new Point3D(50, 50, -150), new Point3D(0, 50, -100))
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Polygon(new Point3D(0, 0, -50), new Point3D(-50, 0, -100), new Point3D(-50, 50, -150), new Point3D(0, 50, -100))
                    .setEmission(new Color(java.awt.Color.BLUE)) //
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Polygon(new Point3D(0, 0, -50), new Point3D(-50, 0, -100), new Point3D(-50, -50, -150), new Point3D(0, -50, -100))
                    .setEmission(new Color(java.awt.Color.BLUE)) //
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
            new Polygon(new Point3D(0, 0, -50), new Point3D(50, 0, -100), new Point3D(50, -50, -150), new Point3D(0, -50, -100))
                    .setEmission(new Color(java.awt.Color.BLUE)) //
                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))
    );

    private static Geometry cylinder = new Cylinder(new Ray(new Point3D(-50, -50, -100), new Vector(1, 2, 3)), 50, 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(200));

    private static Geometry tube = new Tube(new Ray(new Point3D(50, 50, -150), new Vector(1, 2, 3)), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(200));



    @Test
    public void FunSwordTest(){



        scene2.geometries.add();
        scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setKl(0.0005).setKq(0.0005));

        ImageWriter imageWriter = new ImageWriter("SwordTests", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        //render.printGrid(100,new Color(256,256,256));
        render.writeToImage();
    }
}
