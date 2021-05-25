package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents Tube with Ray and radius.
 */

public class Tube extends Geometry {

    /**
     * The axis ray of the tube.
     */
    protected Ray axisRay;
    /**
     * The width of the tube.
     */
    protected double radius;

    /***
     * Make's Tube from Ray and radius
     * @param axisRay Axis of the Tube
     * @param radius radius of the Tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = new Ray(axisRay.getDir(), axisRay.getP0());
        if (Util.isZero(radius) || radius < 0)
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
    public Tube(Vector vec, Point3D point, double radius) {
        this.axisRay = new Ray(vec, point);
        if (Util.isZero(radius) || radius < 0)
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
        if (Util.isZero(t))
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

    /**
     * This method receives a ray and his max distance and returns a list of all the intersections points in objects of GeoPoint.
     * In case there are none or pass the max distance, null will be returned.
     *
     * @param ray         The ray which we find the intersections to the object.
     * @param maxDistance the maximum distance for the ray to go
     * @return A list of the intersection points in form of GeoPoint. In case there are no intersections, null will be returned.
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Vector v = ray.getDir();
        Vector va = axisRay.getDir();
        Point3D p = ray.getP0();
        Point3D pa = axisRay.getP0();

        Vector deltaP = null;
        Vector vecA;
        Vector vecC;
        double a;
        double b;
        double c;
        boolean flag = false;

        // ------ Calc of A --------
        try { //calc the equation A = ( v - (v,va)va )^2

            //calc (v,va)va
            vecA = va.scale(v.dotProduct(va));
            //calc the final equation
            flag = true;
            vecA = v.subtract(vecA);

        } catch (IllegalArgumentException e) {
            //
            if (flag) {
                //if true, its mean the Exception came from the final calc
                //  so the ray and the axisRay is parallel
                return null;
            }
            // if not, so the Exception came from calc (v,va)va
            // so we take only the vector of the ray
            vecA = v;
        }
        a = vecA.lengthSquared(); //vecA^2

        // ------ Calc of C --------

        flag = false;
        boolean flag2 = false;
        Point3D pointC;
        try { //calc the equation C = ( DeltaP - (DeltaP,va)va )^2 - r^2
            //calc DeltaP
            deltaP = p.subtract(pa);
            // calc (DeltaP,va)va
            flag = true;
            vecC = va.scale(deltaP.dotProduct(va));
            // calc the final equation
            flag2 = true;
            pointC = deltaP.subtract(vecC).getHead();

        } catch (IllegalArgumentException e) {
            if (flag2) {
                //if true its mean the Exception came from the final
                //calc so deltaP on parallel axisRay so the final calc is only: -r^2
                pointC = Point3D.ZERO;
            } else if (flag) {
                // if the its mean the Exception came from the calc of
                // (DeltaP,va)va . so we take only deltaP for the calc:
                pointC = deltaP.getHead();
            } else {
                // if true, its mean the Exception came from DeltaP
                // and if (DeltaP == Zero Vector)
                // so (DeltaP,va)va == Zero Vector!
                // so we take the Zero point for the calculations
                pointC = Point3D.ZERO;
            }
        }
        //(pointC)^2 - r^2
        c = pointC.distanceSquared(Point3D.ZERO) - (radius * radius);

        // ------ Calc of B --------

        // calc the equation B = 2*(v - (v,va)va, DeltaP -(DeltaP,va)va)
        b = vecA.dotProduct(pointC) * 2;

        //calc Determinant
        double determinate = Util.alignZero((b * b) - (4d * a * c));

        if (determinate <= 0) {
            //its mean no intersections so
            return null;
        }

        //if pass all its mean two intersections
        determinate = Math.sqrt(determinate);
        double t1 = (-b + determinate) / (2d * a);
        double t2 = (-b - determinate) / (2d * a);
        List<GeoPoint> list = null;

        if (Util.alignZero(t1) > 0 && Util.alignZero(t1 - maxDistance) <= 0)
            //so we want to take t1
            list = new LinkedList<>(List.of(new GeoPoint(this, ray.getPoint(t1))));

        if (Util.alignZero(t2) > 0 && Util.alignZero(t2 - maxDistance) <= 0)
            //so we want to take t2
            if (list == null)
                list = new LinkedList<>(List.of(new GeoPoint(this, ray.getPoint(t2))));
            else
                list.add(new GeoPoint(this, ray.getPoint(t2)));

        if (list == null || list.isEmpty())
            return null;
        return list;
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
}
