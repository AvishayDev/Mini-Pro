package geometries;

import primitives.Point3D;
import primitives.Ray;

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
     * This method receives a ray and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none, null will be returned.
     *
     * @param ray The ray which we find the intersections to the object.
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> saveList;
        List<GeoPoint> returnList = null;
        for (Intersectable g : geometries) {
            //if (g.isIntersect){
            saveList = g.findGeoIntersections(ray);
            if (saveList != null)
                if (returnList == null)
                    returnList = new LinkedList<>(saveList);
                else
                    returnList.addAll(saveList);
            //}
        }
        return returnList;
    }
}
