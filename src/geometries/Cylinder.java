package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


/***
 * Represents Cylinder with vector ,3D point and height.
 */
public class Cylinder extends Tube {

    /**
     * Height is the distance between the bases of the Cylinder
     */
    private double height;

    /***
     * Create Cylinder from Ray radius and height
     * @param axisRay Axis ray of the Cylinder
     * @param radius radius of the Cylinder
     * @param height height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (Util.alignZero(height) <= 0)
            throw new IllegalArgumentException("Please Don't Choose height zero\n");
        this.height = height;
    }

    /***
     * Create Cylinder from vector, 3D point, radius and height
     * From the vector and the 3D point it make Ray and use it
     * @param vec Vector for the Ray
     * @param point 3D point for the Ray
     * @param radius Radius of the Cylinder
     * @param height Height of the Cylinder
     */
    public Cylinder(Vector vec, Point3D point, double radius, double height) {
        super(vec, point, radius);
        if (Util.alignZero(height) <= 0)
            throw new IllegalArgumentException("Please Don't Choose height zero\n");
        this.height = height;
    }

    /***
     * This function returns the normal of the sphere? Null for now
     * @param point A point3D object
     * @return The normal vector
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector axisDir = axisRay.getDir();

        Vector vec1;
        try {
            vec1 = point.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e) {//if catch it means the point on the center
            return axisDir;
        }
        double t = Math.abs(axisDir.dotProduct(vec1));

        //if t equals to 0 or in the length of height its means the point on the bases

        if (Util.isZero(t) || Util.isZero(t - height)) {
            //the point in the base or in the side of the base
            return axisDir;
        } else
            //the point on the side of the Cylinder
            return super.getNormal(point);
    }

    /***
     * To string function
     * @return Information about the Cylinder's axis ray value and the radius value
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
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
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray, double maxDistance) {


        List<GeoPoint> points = super.findGeoIntersectionsParticular(ray, maxDistance);

        //p1 = axisRay.getP0
        //p2 = same as p1 but in upper base

        boolean nullCheck = true;
        Point3D p1 = axisRay.getP0();
        Vector va = axisRay.getDir();
        Point3D p2 = p1.add(va.scale(height));
        Vector v = ray.getDir();
        Point3D p = ray.getP0();
        if (points != null) {
            nullCheck = false;
            int i = 0;
            GeoPoint geoPoint = points.get(i);

            //points.get(i).geometry = this;
            //"point" is a point on the cylinder body so it cant be equal to p1 or p2
            //because they placed in the bases so, no worry about Zero vector
            if (va.dotProduct(geoPoint.point.subtract(p1)) <= 0 || va.dotProduct(geoPoint.point.subtract(p2)) >= 0) {
                // its mean the point outside the cylinder or in body and base intersection
                points.remove(i);
            } else {
                i++;
            }

            if (points.size() > i) {
                geoPoint = points.get(i);
                if (va.dotProduct(geoPoint.point.subtract(p1)) <= 0 || va.dotProduct(geoPoint.point.subtract(p2)) >= 0) {
                    // its mean the point outside the cylinder or in body and base intersection
                    points.remove(i);
                } else {
                    i++;
                }
            }
        }


        // (Va,p) + (Va,-p1) = -(Va,v)*t1 ,
        // (Va,p) + (Va,-p2) = -(Va,v)*t2 => equations for bases intersections
        // calc -(Va,v)
        double dotProV = -va.dotProduct(v);
        if (Util.isZero(dotProV))
            //its mean we doesn't have t so ray doesn't intersect bases!
            return nullCheck || points.isEmpty() ? null : points;

        // calc (Va,p)
        double dotProP = va.dotProduct(p);

        //calc t1,t2
        double t1 = (dotProP + va.dotProduct(p1.scale(-1))) / dotProV;
        double t2 = (dotProP + va.dotProduct(p2.scale(-1))) / dotProV;

        //calc points place
        double radiusSquare = radius * radius;
        Point3D q;
        if (nullCheck) {
            //its mean there is no body intersections so the ray is
            //or parallel or don't cross. if parallel so may cross bases if inside
            //so check without taking body and base intersections
            if (Util.alignZero(t1) > 0 && Util.alignZero(t1 - maxDistance) <= 0) {
                q = ray.getPoint(t1);
                if (q.distanceSquared(p1) < radiusSquare)
                    points = new LinkedList<>(List.of(new GeoPoint(this, q)));
            }

            if (Util.alignZero(t2) > 0 && Util.alignZero(t2 - maxDistance) <= 0) {
                q = ray.getPoint(t2);
                if (q.distanceSquared(p2) < radiusSquare)
                    if (points == null)
                        points = new LinkedList<>(List.of(new GeoPoint(this, q)));
                    else
                        points.add(new GeoPoint(this, q));
            }
        } else {
            //its mean there is initialization for points
            //so check may the ray cross body and base intersections
            if (Util.alignZero(t1) > 0 && Util.alignZero(t1 - maxDistance) <= 0) {
                q = ray.getPoint(t1);
                if (q.distanceSquared(p1) <= radiusSquare)
                    points.add(new GeoPoint(this, q));
            }

            if (Util.alignZero(t2) > 0 && Util.alignZero(t2 - maxDistance) <= 0) {
                q = ray.getPoint(t2);
                if (q.distanceSquared(p2) <= radiusSquare)
                    points.add(new GeoPoint(this, q));
            }
        }

        if (points == null || points.isEmpty())
            return null;
        return points;
    }

    /**
     * find the minimum and the maximum of the geometry border
     */
    @Override
    public void findMinMaxParticular() {

        Point3D p0 = axisRay.getP0();

        minX = p0.getX() - radius;
        minY = p0.getY() - radius;
        minZ = p0.getZ() - radius;

        p0 = axisRay.getPoint(height);

        maxX = p0.getX() + radius;
        maxY = p0.getY() + radius;
        maxZ = p0.getZ() + radius;

    }

    /**
     * this function calculate if the ray trace the border of the geometry
     * We override this method to the original implementation so it won't use the override of Tube instead.
     *
     * @param ray the optional cross ray
     * @return true for intersection, false for not intersection
     */
    @Override
    protected boolean intersectBorder(Ray ray) {

        Point3D origin = ray.getP0();
        double originX = origin.getX();
        double originY = origin.getY();
        double originZ = origin.getZ();
        Vector dir = ray.getDir();
        double dirX = dir.getX();
        double dirY = dir.getY();
        double dirZ = dir.getZ();

        // Initially will receive the values of tMinX and tMaxX
        double tMin;
        double tMax;

        if (Util.alignZero(dirX) >= 0) {
            tMin = (minX - originX) / dirX;
            tMax = (maxX - originX) / dirX;
        } else {
            tMin = (maxX - originX) / dirX;
            tMax = (minX - originX) / dirX;
        }

        double tMinY;
        double tMaxY;
        if (Util.alignZero(dirY) >= 0) {
            tMinY = (minY - originY) / dirY;
            tMaxY = (maxY - originY) / dirY;
        } else {
            tMinY = (maxY - originY) / dirY;
            tMaxY = (minY - originY) / dirY;
        }

        // If either the max value of Y is smaller than overall min value, or min value of Y is bigger than the overall
        // max, we can already return false.
        // Otherwise we'll update the overall min and max values and perform the same check on the Z values.
        if ((tMin > tMaxY) || (tMinY > tMax))
            return false;

        if (tMinY > tMin)
            tMin = tMinY;

        if (tMaxY < tMax)
            tMax = tMaxY;

        double tMinZ;
        double tMaxZ;

        if (Util.alignZero(dirZ) >= 0) {
            tMinZ = (minZ - originZ) / dirZ;
            tMaxZ = (maxZ - originZ) / dirZ;
        } else {
            tMinZ = (maxZ - originZ) / dirZ;
            tMaxZ = (minZ - originZ) / dirZ;
        }

        // If either the max value of Z is smaller than overall min value, or min value of Z is bigger than the overall
        // max, we can already return false. Otherwise we can return true since no other coordinate checks are needed.
        return (!(tMin > tMaxZ)) && (!(tMinZ > tMax));
    }


    /***
     * This function returns the height of the cylinder
     * @return The height of the Cylinder
     */
    public double getHeight() {
        return height;
    }

}
