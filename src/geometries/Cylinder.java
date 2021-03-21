package geometries;
import primitives.*;

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
        return null;
    }

    /***
     *
     * @return The height of the Cylinder
     */
    public double getHeight() {
        return height;
    }
}
