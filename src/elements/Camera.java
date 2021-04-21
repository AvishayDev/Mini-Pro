package elements;

import geometries.Intersectable;
import org.junit.runners.model.InitializationError;
import primitives.*;

import javax.naming.NoInitialContextException;
import java.util.LinkedList;
import java.util.List;


public class Camera {

    Point3D p0;
    Vector vUp;
    Vector vTo;
    Vector vRight;
    double width;
    double height;
    double distance;

    public Camera(Point3D p0, Vector vTo, Vector vUp) {

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
            pC = pC.add(vRight.scale(-xJ));
        if(!Util.isZero(yI))
            pC = pC.add(vUp.scale(yI));

        return new Ray(pC.subtract(p0),p0);
    }



    public Camera setViewPlaneSize(double width, double height){
        if(Util.alignZero(width) <= 0 || Util.alignZero(height) <=0)
            throw new IllegalArgumentException("ViewPlane size must be positive!");
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setDistance(double distance){
        if(distance<=0)
            throw new IllegalArgumentException("ViewPlane distance must be positive!");
        this.distance = distance;
        return this;
    }

    /***
     *
     * @param nX
     * @param nY
     * @param geometry
     * @return
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


    public void replaceCamera(Point3D point) {
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
