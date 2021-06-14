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

        double tmin;
        double tmax;

        if (Util.alignZero(dir.getX()) >= 0) {
            tmin = (minX - originX) / dirX;
            tmax = (maxX - originX) / dirX;
        } else {
            tmin = (maxX - originX) / dirX;
            tmax = (minX - originX) / dirX;
        }

        double tymin;
        double tymax;
        if (Util.alignZero(dir.getY()) >= 0) {
            tymin = (minY - originY) / dirY;
            tymax = (maxY - originY) / dirY;
        } else {
            tymin = (maxY - originY) / dirY;
            tymax = (minY - originY) / dirY;
        }


        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin;
        double tzmax;

        if (Util.alignZero(dir.getZ()) >= 0) {
            tzmin = (minZ - originZ) / dirZ;
            tzmax = (maxZ - originZ) / dirZ;
        } else {
            tzmin = (maxZ - originZ) / dirZ;
            tzmax = (minZ - originZ) / dirZ;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;
    }

}
