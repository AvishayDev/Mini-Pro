package unittests.renderer;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BlackBoard {

    /**
     * This method receives a point of the center of the board, the board's height and weight, its direction vectors
     * and the amount
     * @param center
     * @param height
     * @param width
     * @param vUp
     * @param vRight
     * @param numOfPoints
     * @return
     */
    public static List<Point3D> FindPoints(Point3D center, double height, double width, Vector vUp, Vector vRight, int numOfPoints) {
        List<Point3D> points3D = new LinkedList<>();
        points3D.add(center); // The center point must be included


        double halfHeight = height / 2;
        double halfWeight = width / 2;
        Point3D pCenter;

        double rndHeight;
        double rndWeight;

        for (int i = 0; i < numOfPoints; i++) {

            pCenter = center;

            rndWeight = Util.random(-halfWeight, halfWeight);
            rndHeight = Util.random(-halfHeight, halfHeight);

            if (!Util.isZero(rndWeight))
                //use pC instead of pIJ
                pCenter = pCenter.add(vRight.scale(rndWeight));
            if (!Util.isZero(rndHeight))
                pCenter = pCenter.add(vUp.scale(rndHeight));

            points3D.add(pCenter);

        }
        return points3D;
    }

    public static List<Ray> raysFromPointToPoints(Point3D point, List<Point3D> points, boolean reversed)
    {
        List<Ray> rays = new LinkedList<>();
        Ray ray;
        if(!reversed)
        {
            for (Point3D p: points)
            {
                ray=new Ray(point, p.subtract(point));
                rays.add(ray);
            }
        }
        else
        {
            for (Point3D p: points)
            {
                ray=new Ray(p, point.subtract(p));
                rays.add(ray);
            }
        }

        return rays;
    }


}
