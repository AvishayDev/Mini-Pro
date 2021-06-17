package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/***
 * Represents list of geometries from all the types we have
 */
public class Geometries extends Intersectable {


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
    protected List<GeoPoint> findGeoIntersectionsParticular(Ray ray, double maxDistance) {
        List<GeoPoint> saveList;
        List<GeoPoint> returnList = null;
        for (Intersectable g : geometries) {
            saveList = g.findGeoIntersections(ray, maxDistance);
            if (saveList != null)
                if (returnList == null)
                    returnList = new LinkedList<>(saveList);
                else
                    returnList.addAll(saveList);
        }
        return returnList;
    }

    /**
     * find the minimum and the maximum of the geometry border
     */
    @Override
    public void findMinMaxParticular() {

        // mix point coordinates
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        minZ = Double.POSITIVE_INFINITY;

        // max point coordinates
        maxX = Double.NEGATIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        maxZ = Double.NEGATIVE_INFINITY;

        for (Intersectable g : geometries) {
            g.findMinMax();

            //calc min
            if (g.minX < minX)
                minX = g.minX;
            if (g.minY < minY)
                minY = g.minY;
            if (g.minZ < minZ)
                minZ = g.minZ;

            //calc max
            if (g.maxX > maxX)
                maxX = g.maxX;
            if (g.maxY > maxY)
                maxY = g.maxY;
            if (g.maxZ > maxZ)
                maxZ = g.maxZ;
        }

    }

}

