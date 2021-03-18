package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.lang.reflect.Array;
import java.util.*;

public class Geometries implements Intersectable{

    List<Intersectable> geometries;

    public Geometries(){
        geometries =new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries){
        this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries.clone()));
    }

    public void add(Intersectable... geometries){
        this.geometries.addAll(Arrays.asList(geometries.clone()));
    }
    
    public List<Point3D> findIntersections(Ray ray) {
        if(geometries == null)
            return null;
    }
}
