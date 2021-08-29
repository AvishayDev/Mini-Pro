package com.company;

import elements.BlackBoard;
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
        printPoints(new Vector(1,2,3).normalize(),new Point3D(1,-1,2));
        //out.println(System.currentTimeMillis());
    }


    /**
     * this function make a cyrcle of points around vector to check the affection
     * of cross-Pro-Val by the position of the point around the vector.
     * -- PROGRESS DON'T DONE YET! --
     * printPoints(new Vector(1,2,3),new Point3D(1,-1,2));
     * @param vec   the crossProductValue vector to check
     * @param point the center point of the cyrcle creation
     */
    public static void printPoints(Vector vec, Point3D point) {
        Point3D startPoint = point.add(vec);
        Vector orthoVec = vec.getOrthogonal().normalize();
        Vector crossVec = vec.crossProduct(orthoVec).normalize();
        Vector checkVec;
        Point3D tempPoint;
        for (double i = -1.0; i <= 1.0; i += 0.1) {
            tempPoint = point.add(orthoVec, i).add(crossVec, Math.sqrt(1 - (i * i)));
            checkVec = tempPoint.subtract(startPoint).normalize();
            out.println(vec.crossProductValue(checkVec));
        }
    }
}



