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

        // v = ray.getDir()
        //va = axisRay.detDir()
        // p = ray.getP0()
        // pa = axisRay.getP0()

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
            vecA = axisRay.getDir().scale(ray.getDir().dotProduct(axisRay.getDir()));
            //calc the final equation
            Flag =true;
            vecA = ray.getDir().subtract(vecA);

        } catch (IllegalArgumentException e) {
            //
            if(Flag){
                //if true its mean the Exception came from the final calc
                //  so the ray and the axisRay is parallel
                return null;
            }
            // if not, so the exception came from calc (v,va)va
            // so we take only the vector of the ray
            vecA = ray.getDir();
        }
        A = Util.alignNumber(vecA.lengthSquared()); //vecA^2

        // ------ Calc of C --------

        Flag = false;
        boolean Flag2 = false;
        Point3D pointC;
        try{ //calc the equation C = ( DeltaP - (DeltaP,va)va )^2 - r^2
            //calc DeltaP
            deltaP = ray.getP0().subtract(axisRay.getP0());
            // calc (DeltaP,va)va
            Flag = true;
            vecC = axisRay.getDir().scale(deltaP.dotProduct(axisRay.getDir()));
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
                // if true its mean the Exception came from DeltaP
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
        try {
            B = Util.alignNumber(vecA.dotProduct(pointC.subtract(Point3D.ZERO)) * 2);
        }catch (IllegalArgumentException e){
            //if catch so pointC is (0,0,0)
            //its mean B = 0
            B = 0;
        }

        //calc Determinante
        double determinate = Util.alignNumber((B*B)- (4d*A*C));

        if(Util.alignZero(determinate)<0){
            //its mean no intersections so
            return null;
        }
        determinate =Math.sqrt(determinate);
        if (Util.alignZero(determinate)>0){
            //its mean two intersections
            return List.of(ray.getPoint((-B+determinate)/(2*A)),ray.getPoint((-B-determinate)/(2*A)));
        }
        //if pass all its mean only one intersection
        return List.of(ray.getPoint(-B/2*A));

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
