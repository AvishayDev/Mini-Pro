package com.company;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.ImageWriter;

import static java.lang.System.out;
import static primitives.Util.*;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {


    public static double V = 0.6;
    public static Point3D p = new Point3D(0.46, -0.5, 0.9);

    /**
     * Main program to tests initial functionality of the 1st stage
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {

        Point3D p1 = new Point3D(0.1, -0.5, 0);
        Point3D p2 = new Point3D(0, 0, 2);
        Point3D p3 = new Point3D(1, -1, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        Vector temp12 = p1.subtract(p2);
        Vector temp23 = p2.subtract(p3);
        Vector temp31 = p3.subtract(p1);
        Vector temp = p.subtract(p1);
        out.println(0.46+1.8*-0.5+0.9*-0.4);

    }

}



