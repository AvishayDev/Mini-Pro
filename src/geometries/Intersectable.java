package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This interface includes one method that finds the intersections between the current object a ray
 */
public interface Intersectable {

    // note, also, is this not supposed to be in a different file?
    public static class GeoPoint {
        // 3D model object.
        public Geometry geometry;
        // 3D Point object.
        public Point3D point;

        /***
         * Simple constructor, receives geometry and a point 3D and assigns them into the object's field.
         * @param geometry The 3D model object.
         * @param point The Point3D object.
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /***
         * Override of the equals method, receives some object and checks if it's from the same type (GeoPoint) and not null,
         * checks if geometry's field class are equal and if the points fields are equal
         * @param o The object we're comparing against
         * @return True in case the objects are indeed equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;
            if (geometry.getClass() != geoPoint.geometry.getClass()) return false;
            //if (!geometry.equals(geoPoint.geometry)) return false;
            return point.equals(geoPoint.point);
        }
    }

    /**
     * This method receives a ray and returns a list of all the intersections points. In case there are none, null will be returned.
     * It uses the new method of findGeoIntersections for all geometries now.
     * @param ray The ray which we find the intersections to the object
     * @return A list of the intersection points in form of Point3D. In case there are no intersections, null will be returned
     */
    default List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * This method receives a ray and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none, null will be returned.
     * @param ray The ray which we find the intersections to the object.
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
    List<GeoPoint> findGeoIntersections(Ray ray);
}
