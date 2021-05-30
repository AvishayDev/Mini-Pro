package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import renderer.BlackBoard;

import java.util.List;

/***
 * Camera is an object that has initial starting point, p0, and a virtual coordinate system of x,y,z
 * It also contains the height and width of the View Plane, and the distance from the camera to it
 */
public class Camera {

    // ----------- camera variables -------------

    // p0 is a Point3D value, it shows the exact location of the camera, it is the head point of all the rays coming out of the camera.
    private Point3D p0;
    // vUp is a Vector value, the emulated Y axis of the camera.
    private Vector vUp;
    // vTo is a Vector value, the emulated Z axis of the camera.
    private Vector vTo;
    // vRight is a Vector value, the emulated  X axis of the camera.
    private Vector vRight;


    // ----------- viewPlane variables -------------
    // width is a double value that indicates the width of the view plane
    private double width;
    // height is a double value that indicates the height of the view plane
    private double height;
    // This Point3D will store the center of the view plane
    private Point3D viewPlaneCenter;
    // This variable stores the focal distance, which is the distance between p0 to the focal board.
    private double viewPlaneDistance;

    // ----------- focalPlane variables -------------
    // This variable stores the focal distance, which is completely virtual
    private double focalDistance;

    // ----------- aperture variables -------------
    // This variable stores the height of the camera's aperture
    private double apertureHeight;
    // This variable stores the height of the camera's aperture
    private double apertureWidth;
    // This variable stores the center point of the aperture. It will usually just be the center of the camera.
    private Point3D apertureCenter;

    /***
     * Constructor for Camera, that receives initial starting point, VectorTo (Z axis), VectorUp (Y axis)
     * VectorRight (X axis) will be calculated inside the constructor
     * @param p0 Initial starting point of the camera
     * @param vTo Vector of emulated Z axis
     * @param vUp Vector of emulated Y axis
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp, double viewPlaneDistance) {
        if (vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("Only vUp orthogonal to vTo accepted!");

        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        this.vRight = vTo.crossProduct(vUp).normalize(); // Vector of emulated X axis
        this.viewPlaneCenter = this.p0.add(vTo, viewPlaneDistance);
        apertureCenter = p0;
    }

    /**
     * Find the Point3D value on the View Plane, given the amount of pixels in it and the number of pixels
     * it is from the center.
     *
     * @param nX number of pixels in X axis
     * @param nY number of pixels in Y axis
     * @param j  place form center pixel in X axis
     * @param i  place form center pixel in Y axis
     * @return The Point3D of the Center of the pixel.
     */
    private Point3D findCenterPixel(int nX, int nY, int j, int i) {

        Point3D pixelCenter = viewPlaneCenter;
        //Point3D pixelCenter = p0.add(vTo, viewPlaneDistance);

        // Ratio (pixel width & height)
        double rY = this.height / (double) nY;
        double rX = this.width / (double) nX;

        // Pixel[i,j] center
        double yI = -(i - ((nY - 1) / 2d)) * rY;
        double xJ = (j - ((nX - 1) / 2d)) * rX;

        if (!Util.isZero(xJ))
            //use pC instead of pIJ
            pixelCenter = pixelCenter.add(vRight.scale(xJ));
        if (!Util.isZero(yI))
            pixelCenter = pixelCenter.add(vUp.scale(yI));

        return pixelCenter;
    }

    /***
     * make a Ray from the p0 and intersect the
     * center of a pixel
     * @param nX    number of pixels in X axis
     * @param nY    number of pixels in Y axis
     * @param j     place form center pixel in X axis
     * @param i     place form center pixel in Y axis
     * @return ray from p0 to center of pixel place
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // The center of the pixel that we received
        Point3D pC = findCenterPixel(nX, nY, j, i);

        // A ray from the camera position to the center of the received pixel.
        return new Ray(pC.subtract(p0), p0);
    }

    /**
     * This method receives a ray and calculates the point it intersects the virtual focal plane
     * @param ray   A ray, usually sent from the view plane
     * @return  The intersection point between the ray and the virtual focal point.
     */
    public Point3D findFocalPoint(Ray ray) {
        double t = focalDistance / (ray.getDir().dotProduct(vTo));
        return ray.getPoint(t);
    }

    /**
     * This method receives the amount of pixel and the pixel we want to send a ray through, and the amount of different
     * rays to send through the area of it to the focal point. It will return a list of rays that go through this pixel.
     * @param nX        number of pixels in X axis
     * @param nY        number of pixels in Y axis
     * @param j         place form center pixel in X axis
     * @param i         place form center pixel in Y axis
     * @param numOfRays The amount of rays we want to go through the focal point from the area of the received pixel.
     * @return  A list of rays that go from the received pixel area to the focal point.
     */
    public List<Ray> constructDOFRays(int nX, int nY, int j, int i, int numOfRays) {
        // First step - create the ray to center of the pixel
        Ray centerRay = constructRay(nX, nY, j, i);
        // Second step - calculate focal point
        Point3D focalPoint = findFocalPoint(centerRay);
        // Third step - Create points on the aperture
        List<Point3D> points = BlackBoard.FindPoints(apertureCenter, apertureHeight, apertureWidth, vUp, vRight, numOfRays);
        // Fourth step - Create rays from points to the focal point.
        return BlackBoard.raysFromPointToPoints(focalPoint, points, true);
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
     * This method receives 2 doubles and inserts them as the camera's width and height values, as long as they're valid (bigger than 0).
     * @param width The width of the View Plane
     * @param height The height of the View Plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setApertureSize(double width, double height) {
        if (Util.alignZero(width) <= 0 || Util.alignZero(height) <= 0)
            throw new IllegalArgumentException("ViewPlane size must be positive!");

        this.apertureWidth = width;
        this.apertureHeight = height;
        return this;
    }

    /***
     * This method receives a double and inserts it as the distance between the camera and the Focal plane
     * @param distance The distance between the camera and the Focal plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setFocalDistance(double distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("Focal Plane distance must be positive!");
        this.focalDistance = distance;
        return this;
    }

    /***
     * This method receives a double and inserts it as the distance between the camera and the view plane
     * @param distance The distance between the camera and the view plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setAperturePoint(double distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("View Plane distance must be positive!");
        apertureCenter = p0.add(vTo, distance);
        return this;
    }

    /***
     * Builder pattern
     * this function will change the position, direction and the angle of the camera
     * @param newPositionPoint the new position of the camera (send p0 for no change)
     * @param newDirectionPoint the point to look at
     * @return this object
     */
    public Camera changeDirection(Point3D newPositionPoint, Point3D newDirectionPoint) {

        Point3D oldP0 =p0;
        //replace camera position
        p0 = newPositionPoint;

        // 1) we calc the direction
        vTo = newDirectionPoint.subtract(p0).normalize();
        // 2) we calc the axisDir
        try {
            vRight = vTo.crossProduct(Vector.Y).normalize();
            vUp = vRight.crossProduct(vTo).normalize();
        } catch (IllegalArgumentException e) {
            vUp = Vector.Z;
            vRight = vTo.crossProduct(vUp).normalize();
        }

        //calc the previous distance
        double distances = oldP0.distance(viewPlaneCenter);
        this.viewPlaneCenter = p0.add(vTo, distances);

        //calc the previous distance
        distances = oldP0.distance(apertureCenter);
        if (!Util.isZero(distances))
            //check for zero distance for setting aperture point on p0
            this.apertureCenter = p0.add(vTo, distances);

        return this;
    }

    /***
     * Builder pattern
     * This function change the angle of vRight & vUp along vTo direction
     * the angle change Counterclockwise by the vTo axis!
     * @param angle the angle to change by DEGREES!
     * @return this object
     */
    public Camera rotate(double angle) {
        //change from degrees
        double radians = Math.toRadians(angle);

        // we want to rotate vRight and vUp by the angle sent.
        // we use this formula to do it:
        // Final Vector = V * cos(angle) + (K x V) * sin(angle) + K * (K dot V) * (1 - cos(angle))
        // K = vTo
        // V = vRight || vUp

        double cosAngle = Math.cos(radians);
        double sinAngle = Math.sin(radians);

        if (Util.isZero(sinAngle))
            vUp = vUp.scale(cosAngle).normalize();
        else if (Util.isZero(cosAngle))
            vUp = vRight.scale(sinAngle).normalize();
        else
            vUp = vUp.scale(cosAngle).add(vRight.scale(sinAngle)).normalize();

        vRight = vTo.crossProduct(vUp).normalize();

        //this.viewPlaneCenter = p0.add(vTo.scale(distance));

        return this;
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

    public Camera setViewPlaneDistance(double viewPlaneDistance) {

        if (viewPlaneDistance <= 0)
            throw new IllegalArgumentException("View Plane distance must be positive!");
        this.viewPlaneDistance = viewPlaneDistance;
        return this;
    }
}
