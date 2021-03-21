package geometries;

import primitives.*;

import java.util.List;

/***
 * Represents Tube with Ray and radius.
 */

public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    /***
     * Make's Tube from Ray and radius
     * @param axisRay Axis of the Tube
     * @param radius radius of the Tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = new Ray(axisRay.getDir(), axisRay.getP0());
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Please Don't Choose radius zero");
        this.radius = radius;
    }

    /***
     * Make's Tube from vector, 3D point and radius.
     * From the vector and the 3D point it will make Ray
     * and use it.
     * @param vec vector for create Ray
     * @param point 3D point for create Ray
     * @param radius radius of the Tube
     */
    public Tube(Vector vec , Point3D point, double radius){
        this.axisRay = new Ray(vec,point);
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Please Don't Choose radius zero");
        this.radius = radius;
    }

    /***
     * Getter for the Normal vector of the tube? Null for now
     * @param point Point3D object
     * @return Normal vector?
     */
    @Override
    public Vector getNormal(Point3D point) {
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
        if(Util.isZero(t))
            return point.subtract(axisRay.getP0()).normalize();
        Point3D o = axisRay.getP0().add(axisRay.getDir().scale(t));
        return point.subtract(o).normalize();
    }

    /***
     * To string function
     * @return Information about the tube's ray and its radius.
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    /***
     * Getter for the tube's axis ray
     * @return The axisRay variable
     */
    public Ray getAxisRay() {
        return axisRay;
    }
    /***
     * Getter for the tube's radius
     * @return The radius variable
     */
    public double getRadius() {
        return radius;
    }


    //public Point3D getP0(){ return axisRay.getP0(); }
    //public Vector getDir(){ return axisRay.getDir(); }

}
