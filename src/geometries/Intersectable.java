package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This interface includes one method that finds the intersections between the current object a ray
 */
public interface Intersectable {


    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        public GeoPoint(Geometry geometry,Point3D point){
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;
            if(geometry.getClass() != geoPoint.geometry.getClass()) return false;
            //if (!geometry.equals(geoPoint.geometry)) return false;
            return point.equals(geoPoint.point);
        }

    }

    /**
     * This method receives a ray and returns a list of all the intersections points. In case there are none, null will be returned
     *
     * @param ray The ray which we find the intersections to the object
     * @return A list of the intersection points in form of Point3D. In case there are no intersections, null will be returned
     */
    default List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList .stream()
                        .map(gp -> gp.point)
                        .collect(Collectors.toList());
    }

    List<GeoPoint> findGeoIntersections(Ray ray);
}
