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
        //make sure vUp orthogonal to vTo
        //make vRight
        //all vectors normalized
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
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        return null;
    }

    public Camera setViewPlaneSize(double width, double height){

        return this;
    }

    public Camera setDistance(double distance){

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
}
