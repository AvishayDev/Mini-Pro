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


    /**
     * Main program to tests initial functionality of the 1st stage
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {


        double V = 0.26;
        Point3D p0 = new Point3D(-1.4, 1.9, 1.5);
        Point3D p1 = new Point3D(1, -1, 2);
        Point3D p2 = new Point3D(-2, -1, 5);
        Point3D p3 = new Point3D(1, -2, 3);
        Point3D p4 = new Point3D(-3, 3, 2);
        Point3D p5 = new Point3D(0, 1, 1);

        //check
        Polygon poly = new Polygon(p1, p3, p2, p4, p5);

        Vector u = p1.subtract(p0).normalize();
        Vector v = p3.subtract(p0).normalize();
        Vector w = p2.subtract(p0).normalize();
        Vector e = p4.subtract(p0).normalize();
        Vector f = p5.subtract(p0).normalize();

        double uValue = u.dotProduct(v);
        double vValue = v.dotProduct(w);
        double wValue = w.dotProduct(e);
        double eValue = e.dotProduct(f);
        double fValue = f.dotProduct(u);

        out.println("uv = " + uValue);
        out.println("vw = " + vValue);
        out.println("wa = " + wValue);
        out.println("ef = " + eValue);
        out.println("fu = " + fValue);
        out.println("sum = " + Math.acos(uValue + vValue + wValue + eValue + fValue));

        if(uValue + vValue + wValue + eValue + fValue > Math.PI*2)
            out.println("yes");

    }


}



