package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.lang.reflect.Array;
import java.util.*;

/***
 * Represents list of geometries from all the types we have
 */
public class Geometries implements Intersectable{

    /**
     * List that have all the geometries
     */
    List<Intersectable> geometries;

    /***
     * create EMPTY list of geometries
     */
    public Geometries(){
        geometries =new ArrayList<Intersectable>();
    }

    /***
     * create List of geometries from all the geometries
     * it get
     * @param geometries all the geometries
     */
    public Geometries(Intersectable... geometries){
        this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries.clone()));
    }

    /***
     * adding number of geometries to the list
     * @param geometries the adding geometries
     */
    public void add(Intersectable... geometries){
        this.geometries.addAll(Arrays.asList(geometries.clone()));
    }

    /***
     * calculate all the points that touch all the geometries
     * in the list
     * @param ray The ray which we find the intersections to the object
     * @return List of all the points
     */
    public List<Point3D> findIntersections(Ray ray) {
        if(geometries == null)
            return null;
        return new ArrayList<Point3D>();
    }
}
