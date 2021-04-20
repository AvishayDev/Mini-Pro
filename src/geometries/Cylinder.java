package geometries;
import primitives.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;


/***
 * Represents Cylinder with vector ,3D point and height.
 */
public class Cylinder extends Tube {
    double height;

    /***
     * Create Cylinder from Ray radius and height
     * @param axisRay Axis ray of the Cylinder
     * @param radius radius of the Cylinder
     * @param height height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (Util.isZero(height) || height < 0)
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
        Vector vec1;
        try {
            vec1 = point.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e) {//if catch it means the point on the center
            return axisRay.getDir();
        }
        double t = Math.abs(axisRay.getDir().dotProduct(vec1));
        /**
         * if t equals to 0 or in the length of height its means the point on the bases
         */
        if (Util.isZero(t) || Util.isZero(t - height)) {
            //the point in the base or in the side of the base
            return axisRay.getDir();
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

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        List<Point3D> points = super.findIntersections(ray);

        //p1 = axisRay.getP0
        //p2 = same as p1 but in upper base

        boolean nullCheck= true;
        Point3D p1 = axisRay.getP0();
        Point3D p2 = p1.add(axisRay.getDir().scale(height));
        Vector v = ray.getDir();
        Vector va = axisRay.getDir();
        Point3D p = ray.getP0();

        if(points != null) {
            nullCheck =false;
            Point3D point;
            for (int i = 0; i < points.size(); ) {
                point = points.get(i);
                //"point" is a point on the cylinder body so it cant be equal to p1 or p2
                //because they placed in the bases so, no worry about Zero vector
                if (va.dotProduct(point.subtract(p1)) <= 0 || va.dotProduct(point.subtract(p2)) >= 0) {
                    // its mean the point outside the cyliner or in body and base intersection
                    points.remove(point);
                } else {
                    i++;
                }
            }
        }

        // (Va,p) + (Va,-p1) = -(Va,v)*t1 ,
        // (Va,p) + (Va,-p2) = -(Va,v)*t2 => equations for bases intersections
        // calc -(Va,v)
        double dotProV = -1*va.dotProduct(v);
        if(Util.isZero(dotProV))
            //its mean we dosent have t so ray dosent intersect bases!
            return nullCheck || points.isEmpty() ? null : points;

        // calc (Va,p)
        double dotProP = va.dotProduct(p);

        //calc t1,t2
        double t1 = Util.alignNumber((dotProP + va.dotProduct(p1.scale(-1)))/dotProV);
        double t2 = Util.alignNumber((dotProP + va.dotProduct(p2.scale(-1)))/dotProV);

        //calc points place
        Point3D q;
        if(nullCheck){
            //its mean there is no body intersections so the ray is
            //or parallel or dont cross. if parallel so may cross bases if inside
            //so check without takeing body and base intersections
            points = new LinkedList<Point3D>();
            if(t1 > 0){
                q = ray.getPoint(t1);
                if(q.distanceSquared(p1) < (radius * radius))
                    points.add(q);
            }

            if(t2 > 0){
                q = ray.getPoint(t2);
                if(q.distanceSquared(p2) < (radius * radius))
                    points.add(q);
            }
        }else{
            //its mean there is initialization for points
            //so check may the ray cross body and base intersections
            if(t1 > 0){
                q = ray.getPoint(t1);
                if(q.distanceSquared(p1) <= (radius * radius))
                    points.add(q);
            }

            if(t2 > 0){
                q = ray.getPoint(t2);
                if(q.distanceSquared(p2) <= (radius * radius))
                    points.add(q);
            }
        }





        return points.isEmpty() ? null : points;

    }

    /***
     *
     * @return The height of the Cylinder
     */
    public double getHeight() {
        return height;
    }
}
