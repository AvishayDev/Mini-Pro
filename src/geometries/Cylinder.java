package geometries;
import primitives.*;

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

        boolean basesCheck = false;
        boolean insideCheck = false;
        Point3D p1 = axisRay.getP0();
        Point3D p2 = p1.add(axisRay.getDir().scale(height));
        Vector v = ray.getDir();
        Vector va = axisRay.getDir();
        Point3D p = ray.getP0();

        if(points == null){
            //dont intersect body check for bases
            points = new LinkedList<Point3D>();
            basesCheck =true;
        } else {
            //if there is one or two intersections check everything
            insideCheck =true;
            basesCheck =true;
        }

        if(insideCheck){
            //its mean 1 or 2 body intersections so check if they in the cyliner:
            for(Point3D point : points){
                //"point" is a point on the cylinder body so it cant be equal to p1 or p2
                //because they placed in the bases so, no worry about Zero vector
                if(va.dotProduct(point.subtract(p1))<=0 && va.dotProduct(point.subtract(p2))>=0) {
                    // its mean the point outside the cyliner and maybe in base and body intersection
                    // so remove from list
                    points.remove(point);
                }
            }
        }

        if(basesCheck){
            // (Va,p) + (Va,-p1) = -(Va,v)*t1 ,
            // (Va,p) + (Va,-p2) = -(Va,v)*t2 => equations for bases intersections
            // calc -(Va,v)
            double dotProV = -1*va.dotProduct(v);
            if(Util.isZero(dotProV))
                //its mean we dosent have t so ray dosent intersect bases!
                return points;
            // calc (Va,p)
            double dotProP = 0;
            try{
                dotProP = va.dotProduct(p.subtract(Point3D.ZERO));
            }catch(IllegalArgumentException e){
                //if catch its mean p ==(0,0,0) so dotProP is equals to Zero
                //dont do noting..
            }

            //calc t1,t2
            double t1 = 0;
            double t2 = 0;
            try{
                t1 = Util.alignNumber((dotProP + va.dotProduct(Point3D.ZERO.subtract(p1)))/dotProV);
            }catch(IllegalArgumentException e){
              //if catch its mean p1 == (0,0,0) so calc va.dotProduct(Point3D.ZERO.subtract(p1)) as 0
                t1 = Util.alignNumber((dotProP + 0)/dotProV);
            }

            try{
                t2 = Util.alignNumber((dotProP + va.dotProduct(Point3D.ZERO.subtract(p2)))/dotProV);
            }catch(IllegalArgumentException e){
                //if catch its mean p2 == (0,0,0) so calc va.dotProduct(Point3D.ZERO.subtract(p1)) as 0
                t2 = Util.alignNumber((dotProP + 0)/dotProV);
            }

            //calc points place
            Point3D q;
            try {
                q = ray.getPoint(t1);
                if (t1 > 0 && q.distanceSquared(p1) < (radius * radius))
                    points.add(q);
            }catch(IllegalArgumentException e){
                //if catch so t1==0 dont take the point
            }

            try{
                q = ray.getPoint(t2);
                if (t2 > 0 && q.distanceSquared(p2) < (radius * radius))
                    points.add(q);

            }catch (IllegalArgumentException e){
                //if catch so t2==0 dont take the point
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
