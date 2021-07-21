package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class Cube extends Geometry {


    /***
     * the constructor will reset the minimum and maximum values
     * of the cube
     * @param firstPoint the first point
     * @param secondPoint the second point
     */
    public Cube(Point3D firstPoint, Point3D secondPoint) {
        if (firstPoint.equals(secondPoint))
            throw new IllegalArgumentException("The points don't make cube!!");

        //reset the values for X
        double temp1 = firstPoint.getX();
        double temp2 = secondPoint.getX();
        if (temp1 > temp2) {
            maxX = temp1;
            minX = temp2;
        } else {
            minX = temp1;
            maxX = temp2;
        }

        //reset the values for Y
        temp1 = firstPoint.getY();
        temp2 = secondPoint.getY();
        if (temp1 > temp2) {
            maxY = temp1;
            minY = temp2;
        } else {
            minY = temp1;
            maxY = temp2;
        }

        //reset the values for Z
        temp1 = firstPoint.getZ();
        temp2 = secondPoint.getZ();
        if (temp1 > temp2) {
            maxZ = temp1;
            minZ = temp2;
        } else {
            minZ = temp1;
            maxZ = temp2;
        }
    }

    /***
     * the constructor reset the values of the Cube.
     * the reset done with the values.
     * @param minX the minimum value of X
     * @param minY the minimum value of Y
     * @param minZ the minimum value of Z
     * @param maxX the maximum value of X
     * @param maxY the maximum value of Y
     * @param maxZ the maximum value of Z
     */
    public Cube(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        if (minX == maxX && minY == maxY && minZ == maxZ)
            throw new IllegalArgumentException("The points don't make cube!!");

        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

    }

    @Override
    protected void findMinMaxParticular() {
        minX = minX;
        minY = minY;
        minZ = minZ;

        maxX = maxX;
        maxY = maxY;
        maxZ = maxZ;
    }

    @Override
    protected void move(Vector direction, double t) {

        minX += direction.getX() * t;
        minY += direction.getY() * t;
        minZ += direction.getZ() * t;

        maxX += direction.getX() * t;
        maxY += direction.getY() * t;
        maxZ += direction.getZ() * t;
    }

    /**
     * A method that receives a point, and returns the normal from the current object to this point
     *
     * @param point The point in which we calculate its normal
     * @return The normal from the object to the received point
     */
    @Override
    public Vector getNormal(Point3D point) {

        //check if the point on the X axis
        double valuePoint = point.getX();
        if (Util.isZero(valuePoint - minX) || Util.isZero(valuePoint - maxX))
            return Vector.X;

        //check if the point on the Y axis
        valuePoint = point.getY();
        if (Util.isZero(valuePoint - minY) || Util.isZero(valuePoint - maxY))
            return Vector.Y;

        //if not on X and not on Y so on Z
        return Vector.Z;
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray, double maxDistance) {
        return null;
    }

}
