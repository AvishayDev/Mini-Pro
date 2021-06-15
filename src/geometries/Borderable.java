package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * this class represent the option of border-able for the geometries
 */
public abstract class Borderable {


    // min point coordinates
    public double minX;
    public double minY;
    public double minZ;

    // max point coordinates
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * find the minimum and the maximum of the geometry border
     */
    public abstract void findMinMax();

    /**
     * this function calculate if the ray trace the border of the geometry
     *
     * @param ray the crosses ray
     * @return true for intersection, false for not intersection
     */
    protected boolean intersectBorder(Ray ray) {

        Point3D origin = ray.getP0();
        double originX = origin.getX();
        double originY = origin.getY();
        double originZ = origin.getZ();
        Vector dir = ray.getDir();
        double dirX = dir.getX();
        double dirY = dir.getY();
        double dirZ = dir.getZ();

        // Initially will receive the values of tMinX and tMaxX
        double tMin;
        double tMax;

        if (Util.alignZero(dirX) >= 0) {
            tMin = (minX - originX) / dirX;
            tMax = (maxX - originX) / dirX;
        } else {
            tMin = (maxX - originX) / dirX;
            tMax = (minX - originX) / dirX;
        }

        double tMinY;
        double tMaxY;
        if (Util.alignZero(dirY) >= 0) {
            tMinY = (minY - originY) / dirY;
            tMaxY = (maxY - originY) / dirY;
        } else {
            tMinY = (maxY - originY) / dirY;
            tMaxY = (minY - originY) / dirY;
        }

        // If either the max value of Y is smaller than overall min value, or min value of Y is bigger than the overall
        // max, we can already return false.
        // Otherwise we'll update the overall min and max values and perform the same check on the Z values.
        if ((tMin > tMaxY) || (tMinY > tMax))
            return false;

        if (tMinY > tMin)
            tMin = tMinY;

        if (tMaxY < tMax)
            tMax = tMaxY;

        double tMinZ;
        double tMaxZ;

        if (Util.alignZero(dirZ) >= 0) {
            tMinZ = (minZ - originZ) / dirZ;
            tMaxZ = (maxZ - originZ) / dirZ;
        } else {
            tMinZ = (maxZ - originZ) / dirZ;
            tMaxZ = (minZ - originZ) / dirZ;
        }

        // If either the max value of Z is smaller than overall min value, or min value of Z is bigger than the overall
        // max, we can already return false. Otherwise we can return true since no other coordinate checks are needed.
        return (!(tMin > tMaxZ)) && (!(tMinZ > tMax));
    }

}
