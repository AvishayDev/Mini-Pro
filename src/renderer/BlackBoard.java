package renderer;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

public class BlackBoard {

    /**
     * This method receives a point of the center of the board, the board's height and weight, its direction vectors
     * and the amount of different points you want to generate on that board.
     * It returns a list of the generated points.
     *
     * @param center      This point represents the center of the received board.
     * @param height      Double that represents the height of the board.
     * @param width       Double that represents the width of the board.
     * @param vUp         Vector that represent the Y axis of the board.
     * @param vRight      Vector that represent the X axis of the board.
     * @param numOfPoints The amount of generated points on the board.
     * @return A list of all the generated points.
     */
    public static List<Point3D> FindPointsRectangle(Point3D center, double height, double width, Vector vUp, Vector vRight, int numOfPoints) {
        List<Point3D> points3D = new LinkedList<>();
        points3D.add(center); // The center point must be included


        double halfHeight = height / 2;
        double halfWeight = width / 2;
        Point3D pCenter;

        double rndHeight;
        double rndWeight;

        for (int i = 1; i < numOfPoints; i++) {

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
        Ray ray;
        if (!reversed) {
            for (Point3D p : points) {
                ray = new Ray(point, p.subtract(point));
                rays.add(ray);
            }
        } else {
            for (Point3D p : points) {
                ray = new Ray(p, point.subtract(p));
                rays.add(ray);
            }
        }

        return rays;
    }

    public static List<Ray> raysWithDelta(Point3D point, List<Point3D> points, Vector normal){
        List<Ray> rays = new LinkedList<>();
        Ray ray;
        for (Point3D p : points) {
            ray = new Ray(point, p.subtract(point),normal);
            rays.add(ray);
        }
        return rays;
    }



    public static List<Point3D> FindPointsCircle(Point3D center, double radius, Vector vUp, Vector vRight, int numOfPoints) {

        /*
        we sent 2*radius to make the points choose by the size of the circle
        we sent 1.27*numOfPoints to cover the size differences between rectangle and circle
         */
        List<Point3D> point3DS = FindPointsRectangle(center, 2*radius, 2*radius, vUp, vRight, (int) (numOfPoints * 1.27d));

        //erase the points out of the circle
        for (int i = 0; i < point3DS.size(); i++) {
            if (point3DS.get(i).distance(center) > radius) {
                point3DS.remove(i);
                i--;
            }
        }

        return point3DS;
    }

}
