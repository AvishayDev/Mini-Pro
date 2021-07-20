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


        double V = 0.26;
        Point3D p0 = new Point3D(-0.001,-0.001,0.998);
        Point3D p1 = new Point3D(0,0,1);
        Point3D p2 = new Point3D(-1,0,0);
        Point3D p3 = new Point3D(0,-1,0);
        Point3D p4 = new Point3D(-2,2,1);
        Vector u = p1.subtract(p0).normalize();
        Vector v = p2.subtract(p0).normalize();
        Vector w = p3.subtract(p0).normalize();
        Vector e = p4.subtract(p0).normalize();
        out.println("uv = "+u.dotProduct(v));
        out.println("vw = "+v.dotProduct(w));
        out.println("uw = "+w.dotProduct(e));
        out.println("vw = "+e.dotProduct(u));
        out.println("sum = "+(u.dotProduct(v)+v.dotProduct(w)+w.dotProduct(e)+e.dotProduct(u)));

        out.println(new Vector(1,1,2).dotProduct(Vector.Z));

    }


}



