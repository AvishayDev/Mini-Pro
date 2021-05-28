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
        //findMinMax();
    }

    /***
     * create List of geometries from all the geometries
     * it get
     * @param geometries all the geometries
     */
    public Geometries(Intersectable... geometries) {
        this();
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
            //if (isIntersect((Geometry)g,ray)) {
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
    public boolean isIntersect(Geometry geo, Ray ray) {

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
            tmin = (geo.minPoint[0] - originX) / dirX;
            tmax = (geo.maxPoint[0] - originX) / dirX;
        } else {
            tmin = (geo.maxPoint[0] - originX) / dirX;
            tmax = (geo.minPoint[0] - originX) / dirX;
        }

        double tymin;
        double tymax;
        if (Util.alignZero(dir.getY()) >= 0) {
            tymin = (geo.minPoint[1] - originY) / dirY;
            tymax = (geo.maxPoint[1] - originY) / dirY;
        } else {
            tymin = (geo.maxPoint[1] - originY) / dirY;
            tymax = (geo.minPoint[1] - originY) / dirY;
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
            tzmin = (geo.minPoint[2] - originZ) / dirZ;
            tzmax = (geo.maxPoint[2] - originZ) / dirZ;
        } else {
            tzmin = (geo.maxPoint[2] - originZ) / dirZ;
            tzmax = (geo.minPoint[2] - originZ) / dirZ;
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

