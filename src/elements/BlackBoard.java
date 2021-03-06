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

    public static List<Ray> findRays(Point3D sourcePoint, Point3D centerPlate, double halfHeight, double halfWidth, Vector vUp, Vector vRight, int numOfPoints, boolean reversed) {
        List<Ray> rays = new LinkedList<>();
        rays.add(new Ray(sourcePoint, centerPlate.subtract(sourcePoint))); // The center point must be included

        for (int i = 1; i < numOfPoints; i++) {
            double x = Util.random(-halfWidth, halfWidth);
            double y = Util.random(-halfHeight, halfHeight);

            Point3D pCenter = centerPlate;
            if (!Util.isZero(x))
                //use pC instead of pIJ
                pCenter = pCenter.add(vRight, x);
            if (!Util.isZero(y))
                pCenter = pCenter.add(vUp, y);

            rays.add(reversed ? new Ray(pCenter, sourcePoint.subtract(pCenter)) : new Ray(sourcePoint, pCenter.subtract(sourcePoint)));

        }
        return rays;
    }

    /**
     * This method receives a source point ,point of the center of the board, the board's height and weight, its direction vectors
     * and the amount of different points you want to generate on that board.
     * It returns a list of the generated rays.
     *
     * @param sourcePoint This is the start point of the rays
     * @param centerPlate This point represents the center of the received board.
     * @param radius      Double that represents the radius of the board.
     * @param vUp         Vector that represent the Y axis of the board.
     * @param vRight      Vector that represent the X axis of the board.
     * @param numOfPoints The amount of generated points on the board.
     * @return A list of rays, from the pStart to the pCenter, changed by Delta.
     */
    public static List<Ray> findRays(Point3D sourcePoint, Point3D centerPlate, double radius, Vector vUp, Vector vRight, int numOfPoints) {
        List<Ray> rays = new LinkedList<>();
        rays.add(new Ray(sourcePoint, centerPlate.subtract(sourcePoint))); // The center point must be included
        double radiusSquared = radius * radius;

        for (int i = 1; i < numOfPoints; i++) {
            double x = Util.random(-radius, radius);
            double yChoose = Math.sqrt(radiusSquared-(x*x));
            double y = Util.random(-yChoose, yChoose);

            Point3D pCenter = centerPlate;
            if (!Util.isZero(x))
                //use pC instead of pIJ
                pCenter = pCenter.add(vRight, x);
            if (!Util.isZero(y))
                pCenter = pCenter.add(vUp, y);

            rays.add(new Ray(sourcePoint, pCenter.subtract(sourcePoint)));
        }
        return rays;
    }
    /**
     * This method receives start and center points, the direction vector from start to center (preferably normalized)
     * a normal to the start, radius and amount of rays to generate, and generates them with some delta.
     *
     * @param pStart    The starting point
     * @param pCenter   The center point
     * @param direction Direction from start to center points
     * @param normal    Normal to pStart
     * @param radius    Size of radius, usually of light source
     * @param numOfRays The amount of generated rays
     * @return A list of rays, from the pStart to the pCenter, changed by Delta.
     */
    public static List<Ray> raysWithDelta(Point3D pStart, Point3D pCenter, Vector direction, Vector normal, double radius, int numOfRays) {
        Ray centerRay = new Ray(pStart, direction, normal);
        if (radius == 0.0)
            return List.of(centerRay);

        Vector orthogonal = direction.getOrthogonal();
        return findRays(centerRay.getP0(), pCenter, radius, orthogonal, direction.crossProduct(orthogonal).normalize(), numOfRays);

    }
}
