package geometries;

import primitives.Point3D;
import primitives.Ray;
import geometries.*;
import primitives.Util;
import primitives.Vector;

import java.lang.reflect.Array;
import java.util.*;

/***
 * Represents list of geometries from all the types we have
 */
public class Geometries implements Intersectable {

    /**
     * List that have all the geometries
     */
    private List<Intersectable> geometries = new LinkedList<>();

    /***
     * create EMPTY list of geometries
     */
    public Geometries() {
    }

    /***
     * create List of geometries from all the geometries
     * it get
     * @param geometries all the geometries
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /***
     * adding number of geometries to the list
     * @param geometries the adding geometries
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    /**
     * This method receives a ray and his max distance and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none or pass the max distance, null will be returned.
     *
     * @param ray         The ray which we find the intersections to the object.
     * @param maxDistance the maximum distance for the ray to go
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> saveList;
        List<GeoPoint> returnList = null;
        for (Intersectable g : geometries) {
            //if (isIntersect((Geometry) g, ray)) {
            saveList = g.findGeoIntersections(ray, maxDistance);
            if (saveList != null)
                if (returnList == null)
                    returnList = new LinkedList<>(saveList);
                else
                    returnList.addAll(saveList);
            //}
        }
        return returnList;
    }

    /*
        private boolean isIntersect(Geometry geo, Ray ray) {

            //A = minPoint;
            //B = max point
            //O = p0 of ray
            Point3D oPoint = ray.getP0();
            //D = dir of ray
            Vector rayDir = ray.getDir();

            //1) find t's for may intersect by the equations:
            double t = Double.POSITIVE_INFINITY;
            double optional;

            // tAx = (Ax - Ox) / Dx
            optional = Util.alignZero((geo.minPoint[0] - oPoint.getX()) / rayDir.getX());
            if (optional >= 0 && optional < t)
                t = optional;

            // tAy = (Ay - Oy) / Dy
            optional = Util.alignZero((geo.minPoint[1] - oPoint.getY()) / rayDir.getY());
            if (optional >= 0 && optional < t)
                t = optional;

            // tAz = (Az - Oz) / Dz
            optional = Util.alignZero((geo.minPoint[2] - oPoint.getZ()) / rayDir.getZ());
            if (optional >= 0 && optional < t)
                t = optional;

            Point3D optionalPoint;
            if (t != 0)
                optionalPoint = ray.getPoint(t);
            else
                optionalPoint = oPoint;
            if (geo.minPoint[0] <= optionalPoint.getX() && optionalPoint.getX() <= geo.maxPoint[0] &&
                    geo.minPoint[1] <= optionalPoint.getY() && optionalPoint.getY() <= geo.maxPoint[1] &&
                    geo.minPoint[2] <= optionalPoint.getZ() && optionalPoint.getZ() <= geo.maxPoint[2])
                return true;

            t = Double.POSITIVE_INFINITY;
            // tBx = (Bx - Ox) / Dx
            optional = Util.alignZero((geo.maxPoint[0] - oPoint.getX()) / rayDir.getX());
            if (optional >= 0 && optional < t)
                t = optional;

            // tBy = (By - Oy) / Dy
            optional = Util.alignZero((geo.maxPoint[1] - oPoint.getY()) / rayDir.getY());
            if (optional >= 0 && optional < t)
                t = optional;

            // tBz = (Bz - Oz) / Dz
            optional = Util.alignZero((geo.maxPoint[2] - oPoint.getZ()) / rayDir.getZ());
            if (optional >= 0 && optional < t)
                t = optional;


            if (t != 0)
                optionalPoint = ray.getPoint(t);
            else
                optionalPoint = oPoint;
            if (geo.minPoint[0] <= optionalPoint.getX() && optionalPoint.getX() <= geo.maxPoint[0] &&
                    geo.minPoint[1] <= optionalPoint.getY() && optionalPoint.getY() <= geo.maxPoint[1] &&
                    geo.minPoint[2] <= optionalPoint.getZ() && optionalPoint.getZ() <= geo.maxPoint[2])
                return true;

            //if pass all its mean there is no intersections
            //so dont calc
            return false;
        }


    private boolean isIntersect(Geometry geo, Ray ray) {
        //A = minPoint;
        //B = max point
        //O = p0 of ray
        Point3D oPoint = ray.getP0();
        //D = dir of ray
        Vector rayDir = ray.getDir();

        //1) find t's for may intersect by the equations:

        // tAx = (Ax - Ox) / Dx
        double tx = Util.alignZero((geo.minPoint[0] - oPoint.getX()) / rayDir.getX());

        // tBy = (By - Oy) / Dy
        double ty = Util.alignZero((geo.maxPoint[1] - oPoint.getY()) / rayDir.getY());

        if (tx >= ty && ty != Double.POSITIVE_INFINITY && tx != Double.POSITIVE_INFINITY)
            return false;

        // tAy = (Ay - Oy) / Dy
        ty = Util.alignZero((geo.minPoint[1] - oPoint.getY()) / rayDir.getY());

        // tBx = (Bx - Ox) / Dx
        tx = Util.alignZero((geo.maxPoint[0] - oPoint.getX()) / rayDir.getX());

        if (ty >= tx && ty != Double.POSITIVE_INFINITY && tx != Double.POSITIVE_INFINITY)
            return false;

        return true;
    }

     */
    /*
    private boolean isIntersect(Geometry geo, Ray ray) {

        double[] xt = CheckAxis(transRay.origin.x, transRay.direction.x);
        double[] yt = CheckAxis(transRay.origin.y, transRay.direction.y);
        double[] zt = CheckAxis(transRay.origin.z, transRay.direction.z);

        double tMin = Math.Max(Math.Max(xt[0], yt[0]), zt[0]);
        double tMax = Math.Min(Math.Min(xt[1], yt[1]), zt[1]);

        List<Intersection> xs = new List<Intersection>();

        if (tMin > tMax)
        {
            return xs;
        }

        xs.Add(new Intersection(this, tMin));
        xs.Add(new Intersection(this, tMax));
        return xs;
    }

    private double[] CheckAxis(double origin, double direction)
    {

        double[] t = new double[2];

        double tMinNumerator = (-1 - origin);
        double tMaxNumerator = (1 - origin);

        if(direction < 0)
            direction = -direction;
        //Infinities might pop here due to division by zero
        if (direction >= 0.001)
        {
            t[0] = tMinNumerator / direction;
            t[1] = tMaxNumerator / direction;
        }
        else
        {
            t[0] = tMinNumerator * Double.POSITIVE_INFINITY;
            t[1] = tMaxNumerator * Double.POSITIVE_INFINITY;
        }

        if(t[0] > t[1])
        {
            double temp = t[0];
            t[0] = t[1];
            t[1] = temp;
        }

        return t;
    }

     */
}

