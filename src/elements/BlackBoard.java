package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * This is a class dedicated for bonuses calculations on a virtual board, such as generating random points on a board,
 * creating rays from point to points etc.
 */
public class BlackBoard {

    /**
     * This method receives a point of the center of the board, the board's height and weight, its direction vectors
     * and the amount of different points you want to generate on that board.
     * It returns a list of the generated points.
     *
     * @param center      This point represents the center of the received board.
     * @param halfHeight  Double that represents the half of height of the board.
     * @param halfWidth   Double that represents the half of width of the board.
     * @param vUp         Vector that represent the Y axis of the board.
     * @param vRight      Vector that represent the X axis of the board.
     * @param numOfPoints The amount of generated points on the board.
     * @return A list of all the generated points.
     */
    public static List<Point3D> findPoints(Point3D center, double halfHeight, double halfWidth, Vector vUp, Vector vRight, int numOfPoints) {
        List<Point3D> points = new LinkedList<>();
        points.add(center); // The center point must be included

        for (int i = 1; i < numOfPoints; i++) {
            double x = Util.random(-halfWidth, halfWidth);
            double y = Util.random(-halfHeight, halfHeight);

            Point3D pCenter = center;
            if (!Util.isZero(x))
                //use pC instead of pIJ
                pCenter = pCenter.add(vRight.scale(x));
            if (!Util.isZero(y))
                pCenter = pCenter.add(vUp.scale(y));
            points.add(pCenter);
        }
        return points;
    }

    /**
     * This method receives a point of the center of the board, the board's height and weight, its direction vectors
     * and the amount of different points you want to generate on that board.
     * It returns a list of the generated points.
     *
     * @param center      This point represents the center of the received board.
     * @param radius      Double that represents the radius of the board.
     * @param vUp         Vector that represent the Y axis of the board.
     * @param vRight      Vector that represent the X axis of the board.
     * @param numOfPoints The amount of generated points on the board.
     * @return A list of all the generated points.
     */
    public static List<Point3D> findPoints(Point3D center, double radius, Vector vUp, Vector vRight, int numOfPoints) {
        List<Point3D> points = new LinkedList<>();
        points.add(center); // The center point must be included
        double radiusSquared = radius * radius;

        for (int i = 1; i < numOfPoints; i++) {
            double x, y;
            do {
                x = Util.random(-radius, radius);
                y = Util.random(-radius, radius);
            } while (x * x + y * y >= radiusSquared);

            Point3D pCenter = center;
            if (!Util.isZero(x))
                //use pC instead of pIJ
                pCenter = pCenter.add(vRight.scale(x));
            if (!Util.isZero(y))
                pCenter = pCenter.add(vUp.scale(y));
            points.add(pCenter);
        }
        return points;
    }

    /**
     * Method that receives a point and a list of points, and returns a list of rays from the point to the
     * list of points, or from the list of points to the points, depends whether you send reversed or not.
     *
     * @param point    One single Point3D
     * @param points   A list of many Point3D
     * @param reversed True if you want rays from list of points to the point. False otherwise.
     * @return A list of rays from the point to the list of points or vice verse.
     */
    public static List<Ray> raysFromPointToPoints(Point3D point, List<Point3D> points, boolean reversed) {
        List<Ray> rays = new LinkedList<>();
        for (Point3D p : points)
            rays.add(new Ray(p, reversed ? point.subtract(p) : p.subtract(point)));
        return rays;
    }

    public static List<Ray> raysWithDelta(Point3D pIntersection) {
        //todo all
        return null;
    }
}
