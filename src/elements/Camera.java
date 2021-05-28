package elements;

import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import unittests.renderer.BlackBoard;

import javax.naming.NoInitialContextException;
import java.awt.*;
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
    // This Point3D will store the center of the view plane
    private Point3D pCenter;
    // This variable stores the focal distance, which is the distance between p0 to the focal board.
    private double focalDistance;
    // todo note
    private double apertureHeight;
    // todo note
    private double apertureWidth;

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
        this.pCenter = p0.add(vTo.scale(distance));

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

        // Ratio (pixel width & height)
        double rY = this.height / (double) nY;
        double rX = this.width / (double) nX;

        // Pixel[i,j] center

        double yI = -(i - ((nY - 1) / 2d)) * rY;
        double xJ = (j - ((nX - 1) / 2d)) * rX;

        if (!Util.isZero(xJ))
            //use pC instead of pIJ
            pCenter = pCenter.add(vRight.scale(xJ));
        if (!Util.isZero(yI))
            pCenter = pCenter.add(vUp.scale(yI));

        return pCenter;
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

    public Point3D findFocalPoint(Ray ray) {
        double t = focalDistance / (ray.getDir().dotProduct(vTo));
        return ray.getPoint(t);
    }

    public List<Ray> constructDOFRays(int nX, int nY, int j, int i, int numOfRays) {
        // First step - create the ray to center of the pixel
        Ray centerRay = constructRay(nX, nY, j, i);
        // Second step - calculate focal point
        Point3D focalPoint = findFocalPoint(centerRay);
        // Third step - Create points on the aperture
        List<Point3D> points = BlackBoard.FindPoints(p0, apertureHeight, apertureWidth, vUp, vRight, numOfRays);
        // Create rays from points to the focal point.
        return BlackBoard.raysFromPointToPoints(focalPoint,points, true);
    }

    /*public Ray constructRays(int nX, int nY, int j, int i){
        List<Ray> rays = new LinkedList<>();
        rays.add(constructRay(nX,nY, j, i));

        return BlackBoard.raysFromPointToPoints()
    }*/

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
     * This method receives a double and inserts it as the distance between the camera and the view plane
     * @param distance The distance between the camera and the view plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setDistance(double distance) {
        if (distance <= 0)
            throw new IllegalArgumentException("View Plane distance must be positive!");
        this.distance = distance;
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
     * Builder pattern
     * this function will change the position, direction and the angle of the camera
     * @param newPositionPoint the new position of the camera (send p0 for no change)
     * @param newDirectionPoint the point to look at
     * @return this object
     */
    public Camera changeDirection(Point3D newPositionPoint, Point3D newDirectionPoint) {
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

        this.pCenter = p0.add(vTo.scale(distance));

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

        this.pCenter = p0.add(vTo.scale(distance));

        return this;
    }

    /***
     * Help function
     * This function change the angle of vRight & vUp along axisDir direction
     *  - positive angle is Counterclockwise change -
     * @param cosAngle the cos(angle) value
     * @param sinAngle the sin(angle) value
     * @param axisDir the vector to axis
     * @return this object
     */
    private void angleChange(Vector axisDir, double cosAngle, double sinAngle) {

        // we want to rotate vRight and vUp by the angle sent.
        // we use this formula to do it:
        // Vfinal = V * cos(angle) + (K x V) * sin(angle) + K * (K dot V) * (1 - cos(angle))
        // K = the axis Vector
        // V = vRight || vUp

        boolean cosZero = Util.isZero(cosAngle);
        boolean sinZero = Util.isZero(sinAngle);
        double kdotV = Util.alignZero(axisDir.dotProduct(vRight));

        //calculations for vRight
        Vector vFinal;
        if (cosZero) {
            //if cos == 0 => sin != 0
            // + (K x V) * sin(angle)
            try {
                vFinal = axisDir.crossProduct(vRight).scale(sinAngle);
            } catch (IllegalArgumentException e) {
                //if catch its mean the axisDir and vRight is the same
                // so we need to add the Zero Vector so dont do nothing
                vFinal = axisDir;
            }
            // + K * (K dot V) * (1 - cos(angle)) => cos(angle) == 0 => (1 - cos(angle)) == 1
            if (kdotV != 0)
                vFinal = vFinal.add(axisDir.scale(kdotV));

        } else {
            // V * cos(angle)
            vFinal = vRight.scale(cosAngle);


            //sin(angle) is always != 0
            // + (K x V) * sin(angle)
            try {
                vFinal = vFinal.add(axisDir.crossProduct(vRight).scale(sinAngle));
            } catch (IllegalArgumentException e) {
                //if catch its mean the axisDir and vRight is the same
                // so we need to add the Zero Vector so dont do nothing
                vFinal = axisDir;
            }

            // + K * (K dot V) * (1 - cos(angle))

            if (kdotV != 0 && !Util.isZero(1 - cosAngle))
                vFinal = vFinal.add(axisDir.scale(kdotV).scale(1 - cosAngle));

        }

        vRight = vFinal.normalize();


        //calculations for vUp

        kdotV = Util.alignZero(axisDir.dotProduct(vUp));
        Vector vFinal1;
        if (cosZero) {
            //if cos == 0 => sin != 0
            // + (K x V) * sin(angle)
            try {
                vFinal1 = axisDir.crossProduct(vUp).scale(sinAngle);
            } catch (IllegalArgumentException e) {
                //if catch its mean the axisDir and vRight is the same
                // so we need to add the Zero Vector so dont do nothing
                vFinal1 = axisDir;
            }

            // + K * (K dot V) * (1 - cos(angle)) => cos(angle) == 0 => (1 - cos(angle)) == 1
            if (kdotV != 0)
                vFinal1 = vFinal1.add(axisDir.scale(kdotV));


        } else {
            // V * cos(angle)
            vFinal1 = vUp.scale(cosAngle);

            //if sin == 0 => cos != 0
            // + (K x V) * sin(angle)
            try {
                vFinal1 = vFinal1.add(axisDir.crossProduct(vUp).scale(sinAngle));
            } catch (IllegalArgumentException e) {
                //if catch its mean the axisDir and vRight is the same
                // so we need to add the Zero Vector so dont do nothing
                vFinal1 = axisDir;
            }

            // + K * (K dot V) * (1 - cos(angle))

            if (kdotV != 0 && !Util.isZero(1 - cosAngle))
                vFinal1 = vFinal1.add(axisDir.scale(kdotV).scale(1 - cosAngle));

        }

        vUp = vFinal1.normalize();

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
