package unittests.elements;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1),
            new Vector(0, 1, 0))//
            .setViewPlaneDistance(1000) //
            .setViewPlaneSize(150, 150);

    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1),
            new Vector(0, 1, 0)) //
            .setViewPlaneDistance(1000)//
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


    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void FunTests() {

        //camera1.changeAngle(30);
        scene1.geometries.add(square);
        scene1.geometries.add(cylinder);
        //scene1.geometries.add(tube);
        scene1.lights.add(new PointLight(new Color(100, 400, 100), new Point3D(50, 50, -50)));
        scene1.lights.add(new PointLight(new Color(500, 300, 100), new Point3D(-30, -30, -20)));
        ImageWriter imageWriter = new ImageWriter("cylinderPoint1", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1))//
                .setNumOfRaysAA(50);
        render.renderImage();
        //render.printGrid(100,new Color(256,256,256));
        render.writeToImage();

    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
                .setKl(0.00001).setKq(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Vector(1, 1, -2), new Point3D(-50, -50, 50), 3) //
                .setKl(0.00001).setKq(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setKl(0.0005).setKq(0.0005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        //render.printGrid(100,new Color(256,256,256));
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Vector(-2, -2, -1), new Point3D(10, -10, -130), 3) //
                .setKl(0.0001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spot light
     */
    @Test
    public void sphereSpotSharp() {

        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Vector(1, 1, -2), new Point3D(-50, -50, 50), 3)
                .setKc(1)
                .setKl(0.000005)
                .setKq(0.00000025));

        ImageWriter imageWriter = new ImageWriter("sphereSpotSharpTry2", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();

    }

    /**
     * Produce a picture of a two triangles lighted by a narrow spot light
     */
    @Test
    public void trianglesSpotSharp() {

        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(new Color(800, 400, 400), new Vector(-2, -2, -1), new Point3D(10, -10, -130), 3)
                .setKc(1)
                .setKl(0.000005)
                .setKq(0.00000025));

        ImageWriter imageWriter = new ImageWriter("trianglesSpotSharpTry2", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();

    }

    /**
     * Produce a picture of a two triangles lighted by a narrow spot light
     */
    @Test
    public void testsSS() {

        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.geometries.add(new Sphere(new Point3D(-60, -80, -150), 20)
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene2.lights.add(new SpotLight(new Color(800, 400, 400), new Vector(-2, -2, -1), new Point3D(10, -10, -130), 3)
                .setKc(1)
                .setKl(0.000005)
                .setKq(0.00000025)
                .setRadius(40));

        ImageWriter imageWriter = new ImageWriter("trianglesSpotSharpTry1", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                //.setRayTracer(new RayTracerBasic(scene2));
                .setRayTracer(new RayTracerAdvanced(scene2))
                .setNumOfRaysSS(100);
        render.renderImage();
        //render.printGrid(50,new Color(300,300,300));
        render.writeToImage();

    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void DOFTests() {
        camera1.setFocalDistance(1050).setApertureSize(30, 30);

        Geometry sphere2 = new Sphere(new Point3D(0,0,-200),40)
                .setEmission(new Color(100,100,100))
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

        scene1.geometries.add(sphere,sphere2);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Vector(0, 0, -1), new Point3D(100, 40, 700), 1)//
                .setKl(0.00001).setKq(0.000001));
        ImageWriter imageWriter = new ImageWriter("DOFtest", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1))//;
                .setNumOfRaysDOF(50);
        render.renderImage();
        //render.printGrid(100,new Color(256,256,256));
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a narrow spot light
     */
    @Test
    public void complexPicture() {

        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.geometries.add(new Sphere(new Point3D(-60, -80, -150), 20)
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene2.lights.add(new SpotLight(new Color(800, 400, 400), new Vector(-2, -2, -1), new Point3D(10, -10, -130), 3)
                .setKc(1)
                .setKl(0.000005)
                .setKq(0.00000025)
                .setRadius(40));

        ImageWriter imageWriter = new ImageWriter("trianglesSpotSharpTry1", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerAdvanced(scene2))
                .setNumOfRaysSS(100);
        render.renderImage();
        //render.printGrid(50,new Color(300,300,300));
        render.writeToImage();

    }
}