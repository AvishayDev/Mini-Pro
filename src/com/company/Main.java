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


    public static int radius = 3;

    /**
     * Main program to tests initial functionality of the 1st stage
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {


        double V = 0.26;
        Point3D p0 = new Point3D(1.74,-0.5,1.5);
        Point3D p1 = new Point3D(0,0,1);
        Point3D p2 = new Point3D(1.5,0,1);
        Point3D p3 = new Point3D(2,-1,2);
        Point3D p4 = new Point3D(0,1,0);
        Point3D p5 = new Point3D(0.5,-1,2);
        Vector u = p1.subtract(p0).normalize();
        Vector v = p2.subtract(p0).normalize();
        Vector w = p3.subtract(p0).normalize();
        Vector e = p4.subtract(p0).normalize();
        Vector f = p5.subtract(p0).normalize();

        //check
        Polygon poly = new Polygon(p3,p2,p4,p1,p5);

        out.println("uv = "+u.dotProduct(v));
        out.println("vw = "+v.dotProduct(w));
        out.println("wa = "+w.dotProduct(e));
        out.println("ef = "+e.dotProduct(f));
        out.println("fu = "+f.dotProduct(u));
        out.println("sum = "+(u.dotProduct(v)+v.dotProduct(w)+w.dotProduct(e)+e.dotProduct(f)+f.dotProduct(u)));

       // out.println(new Vector(1,1,2).dotProduct(Vector.Z));

    }


}



