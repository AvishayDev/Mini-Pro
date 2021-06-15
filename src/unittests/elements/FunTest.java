package unittests.elements;


import elements.Camera;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.RayTracerAdvanced;
import renderer.RayTracerBasic;
import renderer.Render;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

public class FunTest {
    private Scene scene2 = new Scene("Test scene").setBackground(new Color(102,0,0));
    private Camera camera2 = new Camera(new Point3D(0, 0, 1200), new Vector(0, 0, 1),
            new Vector(0, 1, 0))//
            .setViewPlaneCenter(1000) //
            .setViewPlaneSize(150, 150)
            .setApertureSize(5,5).setFocalDistance(1000).setNumOfRaysDOF(30);

    //--------------- Lights --------------------
    LightSource centerLight =new PointLight(new Color(225, 225, 153), new Point3D(150, 50, 100)) //
            .setKl(0.0005).setKq(0.0005).setRadius(20);

    LightSource rightLight =new SpotLight(new Color(225, 225, 153), new Point3D(150, 0, 30),new Vector(0,2,-1),1) //
            .setKl(0.0005).setKq(0.0005).setRadius(20);
    LightSource leftLight =new SpotLight(new Color(225, 225, 153), new Point3D(50, 100, 30),new Vector(0,-2,-1),1)//
            .setKl(0.0005).setKq(0.0005).setRadius(20);
    //-------------- Materials --------------------

    Material boxMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(100);
    Material handHeldMaterial = new Material().setKd(1.0).setShininess(100);
    Material MiddleMaterial = new Material().setKd(0.2).setKs(0.8).setShininess(100);
    Material EnergyMaterial = new Material().setKd(0.2).setKs(0.8).setKt(0.4).setShininess(300);
    Material swordMaterial = new Material().setKd(0.2).setKs(0.8).setShininess(300);

    //-------------- Emotions ---------------------

    Color boxColor = new Color(90, 90, 90);
    Color handHeldColor = new Color(92,64,51);
    Color MiddleGoldColor = new Color(225,193,110);
    Color EnergyColor = new Color(135,206,250);
    Color swordColor = new Color(180,180,180);
    Color white = new Color(256,256,256);
    //-------------- Geometries --------------------

    // ************* Box Creating ******************
    Geometry underPlate = new Polygon(new Point3D(0, 0, 0), new Point3D(200, 0, 0), new Point3D(200, 100, 0), new Point3D(0, 100, 0))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry oneSide = new Polygon(new Point3D(0, 0, 0), new Point3D(0, 100, 0), new Point3D(0, 100, 20), new Point3D(0, 0, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry twoSide = new Polygon(new Point3D(0, 0, 0), new Point3D(200, 0, 0), new Point3D(200, 0, 20), new Point3D(0, 0, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry thirdSide = new Polygon(new Point3D(200, 0, 0), new Point3D(200, 100, 0), new Point3D(200, 100, 20), new Point3D(200, 0, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry fourSide = new Polygon(new Point3D(0, 100, 0), new Point3D(200, 100, 0), new Point3D(200, 100, 20), new Point3D(0, 100, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry upperPlate1 = new Polygon(new Point3D(0, 0, 20), new Point3D(200, 0, 20), new Point3D(200, 5, 20), new Point3D(0, 5, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry upperPlate2 = new Polygon(new Point3D(0, 100, 20), new Point3D(200, 100, 20), new Point3D(200, 95, 20), new Point3D(0, 95, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry upperPlate3 = new Polygon(new Point3D(0, 0, 20), new Point3D(5, 0, 20), new Point3D(5, 100, 20), new Point3D(0, 100, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry upperPlate4 = new Polygon(new Point3D(200, 0, 20), new Point3D(195, 0, 20), new Point3D(195, 100, 20), new Point3D(200, 100, 20))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry insidePlate1 = new Polygon(new Point3D(5, 5, 20), new Point3D(195, 5, 20), new Point3D(195, 5, 5), new Point3D(5, 5, 5))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry insidePlate2 = new Polygon(new Point3D(5, 5, 20), new Point3D(5, 95, 20), new Point3D(5, 95, 5), new Point3D(5, 5, 5))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry insidePlate3 = new Polygon(new Point3D(5, 95, 20), new Point3D(195, 95, 20), new Point3D(195, 95, 5), new Point3D(5, 95, 5))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry insidePlate4 = new Polygon(new Point3D(195, 5, 20), new Point3D(195, 95, 20), new Point3D(195, 95, 5), new Point3D(195, 5, 5))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);
    Geometry insidePlate5 = new Polygon(new Point3D(5, 5, 5), new Point3D(195, 5, 5), new Point3D(195, 95, 5), new Point3D(5, 95, 5))
            .setEmission(boxColor) //
            .setMaterial(boxMaterial);

    Geometries swordBox = new Geometries(underPlate, oneSide, twoSide, thirdSide, fourSide, upperPlate1, upperPlate2, upperPlate3, upperPlate4,
            insidePlate1, insidePlate2, insidePlate3, insidePlate4,insidePlate5);

    //************** Hand Held *****************
    Geometry startBall = new Sphere(new Point3D(13,50,9),4)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    // Vector vec, Point3D point, double radius, double height
    Vector swordDir = new Vector(1,0,0);
    double lowHandSize = 2;
    double highHandSize =4;
    Geometry cylinder1 = new Cylinder(swordDir,new Point3D(16,50,9),lowHandSize,highHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder2 = new Cylinder(swordDir,new Point3D(20,50,9),highHandSize,highHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder3 = new Cylinder(swordDir,new Point3D(24,50,9),lowHandSize,lowHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder4 = new Cylinder(swordDir,new Point3D(26,50,9),highHandSize,highHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder5 = new Cylinder(swordDir,new Point3D(30,50,9),lowHandSize,lowHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder6 = new Cylinder(swordDir,new Point3D(32,50,9),highHandSize,highHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder7 = new Cylinder(swordDir,new Point3D(36,50,9),lowHandSize,lowHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder8 = new Cylinder(swordDir,new Point3D(38,50,9),highHandSize,highHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder9 = new Cylinder(swordDir,new Point3D(42,50,9),lowHandSize,lowHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);
    Geometry cylinder10 = new Cylinder(swordDir,new Point3D(44,50,9),highHandSize,highHandSize)
            .setEmission(handHeldColor) //
            .setMaterial(handHeldMaterial);

    Geometries HandHeld = new Geometries(startBall,cylinder1,cylinder2,cylinder3,cylinder4,cylinder5,
            cylinder6,cylinder7,cylinder8,cylinder9,cylinder10);

    // ****************** Middle Sword *******************
    double height=10;
    List<Point3D> points = new LinkedList<Point3D>(List.of(new Point3D(53,41.34,height),new Point3D(68.2,37.44,height),new Point3D(68.7,34.94,height),new Point3D(52.2,37.34,height),
            new Point3D(51.9,32.34,height),new Point3D(69.2,30.94,height),new Point3D(70.2,26.94,height),new Point3D(52.4,29.34,height),
            new Point3D(53.4,25.34,height),new Point3D(71.9,23.94,height),new Point3D(74.6,19.94,height),new Point3D(55.1,21.34,height),
            new Point3D(57.6,17.34,height),new Point3D(78.6,16.94,height),new Point3D(82.6,13.94,height),new Point3D(60.6,14.34,height),
            new Point3D(64.6,10.84,height),new Point3D(86.6,10.94,height),new Point3D(91.6,7.94,height),new Point3D(69.6,8.34,height)));

    Geometry middle1 = new Polygon(points.get(0),points.get(1),points.get(2),points.get(3))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle2 = new Polygon(points.get(2),points.get(3),points.get(4),points.get(5))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle3 = new Polygon(points.get(4),points.get(5),points.get(6),points.get(7))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle4 = new Polygon(points.get(6),points.get(7),points.get(8),points.get(9))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle5 = new Polygon(points.get(8),points.get(9),points.get(10),points.get(11))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle6 = new Polygon(points.get(10),points.get(11),points.get(12),points.get(13))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle7 = new Polygon(points.get(12),points.get(13),points.get(14),points.get(15))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle8 = new Polygon(points.get(14),points.get(15),points.get(16),points.get(17))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle9 = new Polygon(points.get(16),points.get(17),points.get(18),points.get(19))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);

    List<Point3D> points1 = new LinkedList<Point3D>(List.of(new Point3D(53,50+50-41.34,height),new Point3D(68.2,50+50-37.44,height),new Point3D(68.7,50+50-34.94,height),new Point3D(52.2,50+50-37.34,height),
            new Point3D(51.9,50+50-32.34,height),new Point3D(69.2,50+50-30.94,height),new Point3D(70.2,50+50-26.94,height),new Point3D(52.4,50+50-29.34,height),
            new Point3D(53.4,50+50-25.34,height),new Point3D(71.9,50+50-23.94,height),new Point3D(74.6,50+50-19.94,height),new Point3D(55.1,50+50-21.34,height),
            new Point3D(57.6,50+50-17.34,height),new Point3D(78.6,50+50-16.94,height),new Point3D(82.6,50+50-13.94,height),new Point3D(60.6,50+50-14.34,height),
            new Point3D(64.6,50+50-10.84,height),new Point3D(86.6,50+50-10.94,height),new Point3D(91.6,50+50-7.94,height),new Point3D(69.6,50+50-8.34,height)));

    Geometry middle11 = new Polygon(points1.get(0),points1.get(1),points1.get(2),points1.get(3))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle21 = new Polygon(points1.get(2),points1.get(3),points1.get(4),points1.get(5))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle31 = new Polygon(points1.get(4),points1.get(5),points1.get(6),points1.get(7))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle41 = new Polygon(points1.get(6),points1.get(7),points1.get(8),points1.get(9))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle51 = new Polygon(points1.get(8),points1.get(9),points1.get(10),points1.get(11))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle61 = new Polygon(points1.get(10),points1.get(11),points1.get(12),points1.get(13))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle71 = new Polygon(points1.get(12),points1.get(13),points1.get(14),points1.get(15))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle81 = new Polygon(points1.get(14),points1.get(15),points1.get(16),points1.get(17))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry middle91 = new Polygon(points1.get(16),points1.get(17),points1.get(18),points1.get(19))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);

    Geometry toMiddle1 = new Polygon(new Point3D(48,50+Math.sqrt(7),12),new Point3D(48,50-Math.sqrt(7),12),new Point3D(56,38,22),new Point3D(56,62,22))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry toMiddle2 = new Polygon(new Point3D(48,50+Math.sqrt(7),6),new Point3D(48,50+Math.sqrt(7),12),new Point3D(56,62,22),new Point3D(56,62,0))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry toMiddle3 = new Polygon(new Point3D(48,50-Math.sqrt(7),6),new Point3D(48,50-Math.sqrt(7),12),new Point3D(56,38,22),new Point3D(56,38,0))
            .setEmission(MiddleGoldColor) //
            .setMaterial(MiddleMaterial);
    Geometry EnergyBall = new Sphere(new Point3D(64,50,10),14)
            .setEmission(EnergyColor) //
            .setMaterial(EnergyMaterial);

    Geometries Middle = new Geometries(toMiddle1,toMiddle2,toMiddle3,EnergyBall);
    Geometries MiddleRight = new Geometries(middle1,middle2,middle3,middle4,middle5,middle6,middle7,middle8,middle9);
    Geometries MiddleLeft = new Geometries(middle11,middle21,middle31,middle41,middle51,middle61,middle71,middle81,middle91);

    // ****************** Sword *******************

    double startSward = 4*Math.sqrt(10);
    Geometry right = new Polygon(new Point3D(64+startSward,50,13),new Point3D(170,50,13),new Point3D(170,57,10),new Point3D(60+startSward,57,10))
            .setEmission(swordColor)//
            .setMaterial(swordMaterial);
    Geometry left = new Polygon(new Point3D(64+startSward,50,13),new Point3D(170,50,13),new Point3D(170,43,10),new Point3D(60+startSward,43,10))
            .setEmission(swordColor)//
            .setMaterial(swordMaterial);
    Geometry topRight = new Triangle(new Point3D(170,50,13),new Point3D(190,50,10),new Point3D(170,57,10))
            .setEmission(swordColor)//
            .setMaterial(swordMaterial);
    Geometry topLeft = new Triangle(new Point3D(170,50,13),new Point3D(190,50,10),new Point3D(170,43,10))
            .setEmission(swordColor)//
            .setMaterial(swordMaterial);
    double startSine = 13-(12d/7d)+0.01;
    Geometry swordSineRight = new Polygon(new Point3D(64+startSward,54,startSine),new Point3D(170,54,startSine),new Point3D(170,55.5,startSine-(4.5d/7d)),new Point3D(64+startSward,55.5,startSine-(4.5d/7d)))
            .setEmission(white)//
            .setMaterial(swordMaterial);
    Geometry swordSineRightTop = new Polygon(new Point3D(170,54,startSine),new Point3D(170,55.5,startSine-(4.5d/7d)),new Point3D(175,54,10.5))
            .setEmission(white)//
            .setMaterial(swordMaterial);

    Geometries sword = new Geometries(right,topRight,swordSineRight,swordSineRightTop,left,topLeft);

    @Test
    public void FunSwordTest() {

       // double distance = Math.sqrt(500);
        camera2.changeDirection(new Point3D(-350, -100, 120), new Point3D(100, 50, 10));
        camera2.rotate(-270);
        scene2.geometries.add(swordBox,HandHeld,Middle,MiddleRight,MiddleLeft,sword);
        scene2.lights.add(leftLight);scene2.lights.add(rightLight);scene2.lights.add(centerLight);
        boolean AdvancedRun = false;

        ImageWriter imageWriter = new ImageWriter("SwordBasic1", 1000, 1000);
        Render render;
        if(AdvancedRun) {
            render = new Render()//
                    .setImageWriter(imageWriter) //
                    .setCamera(camera2) //
                    .setRayTracer(new RayTracerAdvanced(scene2))
                    .setMultithreading(3).setDebugPrint();

            render.renderImageAdvanced();
        }else{
            render = new Render()//
                    .setImageWriter(imageWriter) //
                    .setCamera(camera2) //
                    .setRayTracer(new RayTracerBasic(scene2));

            render.renderImage();
        }

        render.writeToImage();
    }
}
