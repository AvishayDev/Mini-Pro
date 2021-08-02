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
    public static Point3D p = new Point3D(0.54, -0.5, 1.1);

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
        Vector temp1 = p1.subtract(p);
        Vector temp2 = p2.subtract(p);
        Vector temp3 = p3.subtract(p);
        out.println(temp1.crossProductValue(temp2));
        out.println(temp2.crossProductValue(temp3));
        out.println(temp3.crossProductValue(temp1));

    }

}



