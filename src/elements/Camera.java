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

    Point3D p0;
    Vector vUp;
    Vector vTo;
    Vector vRight;
    double width;
    double height;
    double distance;

    /***
     * Constructor for Camera, that receives initial starting point, VectorTo (Z axis), VectorUp (Y axis)
     * VectorRight (X axis) will be calculated inside the constructor
     * @param p0 Initial starting point of the camera
     * @param vTo Vector of emulated Z axis
     * @param vUp Vector of emulated Y axis
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {

        if(vUp.dotProduct(vTo) != 0)
            throw new IllegalArgumentException("Only vUp orthogonal to vTo accepted!");

        this.p0 = p0;
        this.vUp = vUp.normalized();
        this.vTo = vTo.normalized();
        this.vRight = vUp.crossProduct(vTo).normalized(); // Vector of emulated X axis

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
    public Ray constructRay(int nX, int nY, int j, int i) throws NoInitialContextException {
        if(width == 0 || height == 0)
                throw new NoInitialContextException("ViewPlane size must be positive!");

        // Image center
        Point3D pC = this.p0.add(vTo.scale(distance));

        // Ratio (pixel width & height)
        double rY = this.height/(double) nY;
        double rX = this.width/(double) nX;

        // Pixel[i,j] center

        double yI = -(i-((nY-1)/2d))*rY;
        double xJ = (j-((nX-1)/2d))*rX;

        if(!Util.isZero(xJ))
            //use pC instead of pIJ
            pC = pC.add(vRight.scale(-xJ)); // Need another check later, different from presentation
        if(!Util.isZero(yI))
            pC = pC.add(vUp.scale(yI));

        return new Ray(pC.subtract(p0),p0);
    }


    /***
     * This method receives 2 doubles and inserts them as the camera's width and height values, as long as they're valid (bigger than 0).
     * @param width The width of the View Plane
     * @param height The height of the View Plane
     * @return This camera, with the updated values. Not a clone.
     */
    public Camera setViewPlaneSize(double width, double height){
        if(Util.alignZero(width) <= 0 || Util.alignZero(height) <=0)
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
    public Camera setDistance(double distance){
        if(distance<=0)
            throw new IllegalArgumentException("ViewPlane distance must be positive!");
        this.distance = distance;
        return this;
    }

    /***
     * This method receives the amount of pixels in the view plane's X axis and Y axis, and a geometry, and returns a list of intersection points from
     * rays out of the camera, through the view plane that hits the geometry object
     * It will throw exception in case width or height of the camera are not initialized properly.
     * @param nX The amount of pixels in X axis of the view plane
     * @param nY The amount of pixels in Y axis of the view plane
     * @param geometry The geometry that's being pictured in the camera
     * @return A list of all the intersection points from the camera to the geometry
     */
    public List<Point3D> cameraRaysIntersect(int nX, int nY,Intersectable geometry) throws NoInitialContextException {
        List<Point3D> returnList = new LinkedList<Point3D>();
        List<Point3D> points;

        Ray rayCheck;

        for(int i = 0;i<nX;i++)
            for(int j = 0;j<nY;j++){
                rayCheck = constructRay(nX,nY,j,i);
                points = geometry.findIntersections(rayCheck);
                if(points != null)
                    returnList.addAll(points);
            }

        return returnList;
    }


    public Camera changeAngle(double angle){


        //for calc vRight new position
        double coeff[][] = {{vRight.getX(),vRight.getY(),vRight.getZ(),Math.cos((angle*Math.PI)/180)},
                {vUp.getX(),vUp.getY(),vUp.getZ(),Math.cos(((90-angle)*Math.PI)/180)},
                {vTo.getX(),vTo.getY(),vTo.getZ(),0}};

        Util.findSolution(coeff);

        //for calc vUp new position
        coeff = new double[][]{{vUp.getX(),vUp.getY(),vUp.getZ(),Math.cos((angle*Math.PI)/180)},
                {vRight.getX(),vRight.getY(),vRight.getZ(),Math.cos(((90+angle)*Math.PI)/180)},
                {vTo.getX(),vTo.getY(),vTo.getZ(),0}};

        Util.findSolution(coeff);

        //no changes for vTo


        return this;
    }

    public void replaceCameraPosition(Point3D point) {
        p0 = point;
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
