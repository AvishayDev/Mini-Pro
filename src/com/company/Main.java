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

        double[][] matrix = new double[3][3];
        matrix[0] = new double[]{-2, 3, -1};
        matrix[1] = new double[]{5, -1, 4};
        matrix[2] = new double[]{4, -8, 2};

        out.println(matrix3Det(matrix));

    }

    public static boolean intersectBorder(Ray axisRay, Ray ray) {

        Point3D p0Axis = axisRay.getP0();
        Vector dirAxis = axisRay.getDir();

        Point3D p0Ray = ray.getP0();
        Vector dirRay = ray.getDir();

        if (p0Axis.equals(p0Ray)) // same position - that is the point of intersection
            return true;

        boolean goDot = false;

        try {
            dirAxis.crossProduct(dirRay);
        } catch (IllegalArgumentException e) {
            goDot = true;
        }

        if (!goDot) {
            return true;
        }
        return true;
    }


    public static double[] MatrixSolve(double[][] matrix) {

        double[] returnValues = new double[3];

        double a = matrix3Det();
    }

    public static double matrix3Det(double[][] matrix) {
        //   | a1  b1  c1 |
        //   | a2  b2  c2 |
        //   | a3  b3  c3 |

        double returnValue = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[2][1] * matrix[1][2]);
        returnValue = returnValue - matrix[1][0] * (matrix[0][1] * matrix[2][2] - matrix[2][1] * matrix[0][2]);
        returnValue = returnValue + matrix[2][0] * (matrix[0][1] * matrix[1][2] - matrix[1][1] * matrix[0][2]);
        return returnValue;
    }
}



