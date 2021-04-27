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
    List<Intersectable> geometries;

    /***
     * create EMPTY list of geometries
     */
    public Geometries() {
        geometries = new LinkedList<Intersectable>();
    }

    /***
     * create List of geometries from all the geometries
     * it get
     * @param geometries all the geometries
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = List.of(geometries);
    }

    /***
     * adding number of geometries to the list
     * @param geometries the adding geometries
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries.clone()));
    }

    /***
     * calculate all the points that touch all the geometries
     * in the list
     * @param ray The ray which we find the intersections to the object
     * @return List of all the points
     */
    public List<Point3D> findIntersections(Ray ray) {

        if (geometries.isEmpty())
            //if geometries is empty so no intersection => null
            return null;

        List<Point3D> saveList;
        List<Point3D> returnList = new LinkedList<Point3D>();
        for (Intersectable g : geometries) {
            saveList = g.findIntersections(ray);
            if (saveList != null)
                returnList.addAll(saveList);
        }


        if (returnList.isEmpty())
            return null;
        returnList.sort(Comparator.comparingDouble(ray::getT));
        return returnList;

    }
}
