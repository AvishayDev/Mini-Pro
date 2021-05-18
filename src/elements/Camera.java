package elements;

import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import javax.naming.NoInitialContextException;
import java.util.LinkedList;
import java.util.List;

/***
 * Camera is an object that has initial starting point, p0, and a virtual coordinate system of x,y,z
 * It also contains the height and width of the View Plane, and the distance from the camera to it
 */
public class Camera {

    // p0 is a Point3D value, it shows the exact location of the camera, it is the head point of all the rays coming out of the camera.
    private Point3D p0;
    // vUp is a Vector value, the emulated Y axis of the camera.
    private Vector vUp;
    // vTo is a Vector value, the emulated Z axis of the camera.
    private Vector vTo;
    // vRight is a Vector value, the emulated  X axis of the camera.
    private Vector vRight;
    // width is a double value that indicates the width of the view plane
    private double width;
    // height is a double value that indicates the height of the view plane
    private double height;
    // distance is a double value the indicates how far the p0 is from the center of the view plane
    private double distance;

    /***
     * Constructor for Camera, that receives initial starting point, VectorTo (Z axis), VectorUp (Y axis)
     * VectorRight (X axis) will be calculated inside the constructor
     * @param p0 Initial starting point of the camera
     * @param vTo Vector of emulated Z axis
     * @param vUp Vector of emulated Y axis
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {

        if (vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("Only vUp orthogonal to vTo accepted!");

        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        this.vRight = vTo.crossProduct(vUp).normalize(); // Vector of emulated X axis

    }

    /***
     * make a Ray from the p0 and intersect the
     * center of a pixel
     * @param nX number of pixels in X axis
     * @param nY number of pixels in X axis
     * @param j place form center pixel in x axis
     * @param i place form center pixel in y axis
     * @return ray from p0 to center of pixel place
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // Image center
        Point3D pC = p0.add(vTo.scale(distance));

        // Ratio (pixel width & height)
        double rY = this.height / (double) nY;
        double rX = this.width / (double) nX;

        // Pixel[i,j] center

        double yI = -(i - ((nY - 1) / 2d)) * rY;
        double xJ = (j - ((nX - 1) / 2d)) * rX;

        if (!Util.isZero(xJ))
            //use pC instead of pIJ
            pC = pC.add(vRight.scale(xJ)); // Need another check later, different from presentation
        if (!Util.isZero(yI))
            pC = pC.add(vUp.scale(yI));

        return new Ray(pC.subtract(p0), p0);
    }

    /***
     * This method receives 2 doubles and inserts them as the camera's width and height values, as long as they're valid (bigger than 0).
     * @param width The width of the View Plane
     * @param height The height of the View Plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setViewPlaneSize(double width, double height) {
        if (Util.alignZero(width) <= 0 || Util.alignZero(height) <= 0)
            throw new IllegalArgumentException("ViewPlane size must be positive!");

        this.width = width;
        this.height = height;
        return this;
    }

    /***
     * This method receives a double and inserts it as the distance between the camera and the view plane
     * @param distance The distance between the camera and the view plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setDistance(double distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("ViewPlane distance must be positive!");
        this.distance = distance;
        return this;
    }

    /***
     * this function change the angle of XY plane(positive == clockwise)
     * and change the camera to look at the point sent
     * Builder pattern
     * @param point the point to look at
     * @param angle the angle to change
     * @return this object
     */
    public Camera directionChange(Point3D point, double angle) {

        /*
         * for the point change, every time ZERO one coordinate
         * and calc the angle to change in each plane (XY,YZ,ZX)
         * and move the vectors (vRight & vUp) to the new angle
         * from their places
         * */


        return this;
    }


    /***
     * This function change the angle of XY plane with Builder pattern
     * positive angle is clockwise change
     * @param angle the angle to change
     * @return this object
     */
    public Camera directionChange(double angle) {

        // we want to rotate vRight and vUp by the angle sent.
        // we use this formula to do it:
        // Vfinal = V * cos(angle) + (K x V) * sin(angle) + K * (K dot V) * (1 - cos(angle))
        // K = vTo


        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        // V * cos(angle)
        Vector Vfinal = vRight.scale(cosAngle);
        // + (K x V) * sin(angle)
        Vfinal = Vfinal.add(vTo.crossProduct(vRight).scale(sinAngle));
        // + K * (K dot V) * (1 - cos(angle)) => K dot V is always 0
        //Vfinal = Vfinal.add(vTo.scale(vTo.dotProduct(vRight)*(1-cosAngle)));

        vRight = Vfinal;

        // V * cos(angle)
        Vector Vfinal2 = vUp.scale(cosAngle);
        // + (K x V) * sin(angle)
        Vfinal2 = Vfinal2.add(vTo.crossProduct(vUp).scale(sinAngle));
        // + K * (K dot V) * (1 - cos(angle)) => K dot V is always 0
        //Vfinal2 = Vfinal2.add(vTo.scale(vTo.dotProduct(vUp)*(1-cosAngle)));

        vUp = Vfinal2;


/*
        double cosAlpha = Math.cos((angle * Math.PI) / 180);
        //for calc vRight new position
        double coeff[][] = {{vRight.getX(), vRight.getY(), vRight.getZ(), cosAlpha},
                {vUp.getX(), vUp.getY(), vUp.getZ(), Math.cos(((90 - angle) * Math.PI) / 180)},
                {vTo.getX(), vTo.getY(), vTo.getZ(), 0}};

        vRight = Util.findSolution(coeff);

        //for calc vUp new position
        coeff = new double[][]{{vUp.getX(), vUp.getY(), vUp.getZ(), cosAlpha},
                {vRight.getX(), vRight.getY(), vRight.getZ(), 0},
                {vTo.getX(), vTo.getY(), vTo.getZ(), 0}};

        vUp = Util.findSolution(coeff);

        //no changes for vTo
*/
        return this;
    }

    /***
     * This method changes the location point of the Camera
     * @param point A Point3D which will be the the new location of the camera
     */
    public void replaceCameraPosition(Point3D point) {
        p0 = point;
    }

    /***
     * Getter for the Point3D p0 field of the camera
     * @return The Point3D p0
     */
    public Point3D getP0() {
        return p0;
    }

    /***
     * Getter for the Vector vUp field of the camera
     * @return The Vector vUp
     */
    public Vector getVUp() {
        return vUp;
    }

    /***
     * Getter for the Vector vTo field of the camera
     * @return The Vector vTo
     */
    public Vector getVTo() {
        return vTo;
    }

    /***
     * Getter for the Vector vRight field of the camera
     * @return The Vector vRight
     */
    public Vector getVRight() {
        return vRight;
    }
}
