package elements;

import primitives.*;

public class Camera {

    Point3D p0;
    Vector vUp;
    Vector vTo;
    Vector vRight;
    double width;
    double height;
    double distance;

    public Camera(Point3D p0, Vector vUp, Vector vTo) {

        if(vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("Only vUp orthogonal to vTo accepted!");

        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        this.vRight = vUp.crossProduct(vTo).normalized();

    }

    /***
     * make a Ray from the p0 and intersect the
     * center of a pixel
     * @param nX number of pixels in X axis
     * @param nY number of pixels in X axis
     * @param j place form center pixel in x axis
     * @param i place form center pixel in y axis
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }



    public Camera setViewPlaneSize(double width, double height){
        if(Util.alignZero(width) <= 0 || Util.alignZero(height) <=0)
            throw new IllegalArgumentException("ViewPlane size must be positive!");
        this.width =width;
        this.height = height;
        return this;
    }

    public Camera setDistance(double distance){
        if(distance<=0)
            throw new IllegalArgumentException("ViewPlane distance must be positive!");
        this.distance = distance;
        return this;
    }




    public Point3D getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public Camera setVpSize(int i, int i1) {return null;}
}
