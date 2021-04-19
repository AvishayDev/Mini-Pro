package geometries;

import primitives.*;

import java.util.LinkedList;
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

        Vector v = ray.getDir();
        Vector va = axisRay.getDir();
        Point3D p = ray.getP0();
        Point3D pa = axisRay.getP0();

        Vector deltaP = null;
        Vector vecA;
        Vector vecC;
        double A;
        double B;
        double C;
        boolean Flag = false;

        // ------ Calc of A --------
        try{ //calc the equation A = ( v - (v,va)va )^2

            //calc (v,va)va
            vecA = va.scale(v.dotProduct(va));
            //calc the final equation
            Flag =true;
            vecA = v.subtract(vecA);

        } catch (IllegalArgumentException e) {
            //
            if(Flag){
                //if true, its mean the Exception came from the final calc
                //  so the ray and the axisRay is parallel
                return null;
            }
            // if not, so the Exception came from calc (v,va)va
            // so we take only the vector of the ray
            vecA = v;
        }
        A = Util.alignNumber(vecA.lengthSquared()); //vecA^2

        // ------ Calc of C --------

        Flag = false;
        boolean Flag2 = false;
        Point3D pointC;
        try{ //calc the equation C = ( DeltaP - (DeltaP,va)va )^2 - r^2
            //calc DeltaP
            deltaP = p.subtract(pa);
            // calc (DeltaP,va)va
            Flag = true;
            vecC = va.scale(deltaP.dotProduct(va));
            // calc the final equation
            Flag2 = true;
            pointC = deltaP.subtract(vecC).getHead();

        } catch (IllegalArgumentException e) {
            if(Flag2){
                //if true its mean the Exception came from the final
                //calc so deltaP on parallel axisRay so the final calc is only: -r^2
                pointC = Point3D.ZERO;
            }else if(Flag){
                // if the its mean the Exception came from the calc of
                // (DeltaP,va)va . so we take only deltaP for the calc:
                pointC = deltaP.getHead();
            }else {
                // if true, its mean the Exception came from DeltaP
                // and if (DeltaP == Zero Vector)
                // so (DeltaP,va)va == Zero Vector!
                // so we take the Zero point for the calculations
                pointC = Point3D.ZERO;
            }
        }
        //(pointC)^2 - r^2
        C =Util.alignNumber(pointC.distanceSquared(Point3D.ZERO) - (radius*radius));

        // ------ Calc of B --------

        // calc the equation B = 2*(v - (v,va)va, DeltaP -(DeltaP,va)va)
        B = Util.alignNumber(vecA.dotProduct(pointC) * 2);

        //calc Determinante
        double determinate = Util.alignNumber((B*B)- (4d*A*C));

        if(Util.alignZero(determinate)<0){
            //its mean no intersections so
            return null;
        }

        determinate =Math.sqrt(determinate);
        if (Util.alignZero(determinate) == 0){
            //its mean there is only one intersection by
            //tangent point
            return null;
            }

        //if pass all its mean two intersections

        List<Point3D> list = new LinkedList<Point3D>();
        double t1 = (-B+determinate)/(2d*A);
        double t2 = (-B-determinate)/(2d*A);
        if(Util.alignZero(t1) > 0)
            //so we want to take t1
            list.add(ray.getPoint(t1));

        if (Util.alignZero(t2) > 0)
            //so we want to take t2
            list.add(ray.getPoint(t2));

        return list.isEmpty() ? null : list;

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
