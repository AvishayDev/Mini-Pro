package com.company;

import geometries.Plane;
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


    public static int radius = 3;

    /**
     * Main program to tests initial functionality of the 1st stage
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {
        if (Util.isZero(1.032039148168894E-12))
            out.println("yes");

        Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 2, 1));
        Ray axis = new Ray(new Point3D(0.5, 0, 0), new Vector(-1, 1, 1));
        //intersectBorder(axis,ray);

    }

    public static boolean intersectBorder(Ray axisRay, Ray ray) {

        // ray1
        Point3D p0Axis = axisRay.getP0();
        Vector dirAxis = axisRay.getDir();
        //ray 2
        Point3D p0Ray = ray.getP0();
        Vector dirRay = ray.getDir();

        Vector n;
        try {
            n = dirAxis.crossProduct(dirRay);
            return n.dotProduct(p0Axis.subtract(p0Ray)) / n.length() <= radius;
        } catch (IllegalArgumentException e) {
            //if catch or parallel or p0Axis==p0Ray
            return p0Axis.distance(p0Ray) <= radius;
        }
    }

}



