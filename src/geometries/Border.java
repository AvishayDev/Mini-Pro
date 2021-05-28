package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public abstract class Border {

    /*

    public double[] minPoint = new double[3];
    public double[] maxPoint = new double[3];

    public abstract void findMinMax();


    protected boolean isIntersect(Ray ray) {

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
            tmin = (minPoint[0] - originX) / dirX;
            tmax = (maxPoint[0] - originX) / dirX;
        }
        else {
            tmin = (maxPoint[0] - originX) / dirX;
            tmax = (minPoint[0] - originX) / dirX;
        }

        double tymin;
        double tymax;
        if (Util.alignZero(dir.getY()) >= 0) {
            tymin = (minPoint[1] - originY) / dirY;
            tymax = (maxPoint[1] - originY) / dirY;
        }
        else {
            tymin = (maxPoint[1] - originY) / dirY;
            tymax = (minPoint[1] - originY) / dirY;
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
            tzmin = (minPoint[2] - originZ) / dirZ;
            tzmax = (maxPoint[2] - originZ) / dirZ;
        }
        else {
            tzmin = (maxPoint[2] - originZ) / dirZ;
            tzmax = (minPoint[2] - originZ) / dirZ;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;
    }
*/
}
