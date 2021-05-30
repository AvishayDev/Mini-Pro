package unittests.IntegrationTests;

import org.junit.Test;
import elements.*;
import primitives.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing Camera Class
 *
 * @author Dan
 */

public class IntegrationTests {

    /***
     * This method receives the amount of pixels in the view plane's X axis and Y axis, and a geometry, and returns a list of intersection points from
     * rays out of the camera, through the view plane that hits the geometry object
     * It will throw exception in case width or height of the camera are not initialized properly.
     * @param nX The amount of pixels in X axis of the view plane
     * @param nY The amount of pixels in Y axis of the view plane
     * @param geometry The geometry that's being pictured in the camera
     * @return A list of all the intersection points from the camera to the geometry.
     */
    private int cameraRaysIntersect(Camera camera, int nX, int nY, Intersectable geometry) {
        List<Point3D> returnList = new LinkedList<>();
        List<Point3D> points;

        Ray rayCheck;

        for (int i = 0; i < nX; i++)
            for (int j = 0; j < nY; j++) {
                rayCheck = camera.constructRay(nX, nY, j, i);
                points = geometry.findIntersections(rayCheck);
                if (points != null)
                    returnList.addAll(points);
            }

        return returnList.size();
    }

    /**
     * Test method for Camera rays intersect points with a sphere. Uses the private method "cameraRaysIntersect" from above.
     */
    @Test
    public void cameraRaysIntersectSphere() {

        Camera camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);

        //TC0-1: First sphere test case Sphere (r=1)
        Sphere sphere = new Sphere(new Point3D(0, 0, -3), 1);
        assertEquals("TC0-1: First sphere test case Sphere r=1", 2,
                cameraRaysIntersect(camera, 3, 3, sphere));

        //TC0-2: Second sphere test case (r=2.5)
        sphere = new Sphere(new Point3D(0, 0, -2.5d), 2.5d);
        camera.changeDirection(new Point3D(0, 0, 0.5), camera.getVTo().getHead());
        assertEquals("TC0-2: Second sphere test case (r=2.5)", 18,
                cameraRaysIntersect(camera, 3, 3, sphere));

        //TC0-3: Third sphere test case (r=2)
        sphere = new Sphere(new Point3D(0, 0, -2d), 2d);
        assertEquals("TC0-3: Third sphere test case (r=2)", 10,
                cameraRaysIntersect(camera, 3, 3, sphere));

        //TC0-4: Fourth sphere test case (r=4)
        sphere = new Sphere(new Point3D(0, 0, -1), 4);
        assertEquals("TC0-4: Fourth sphere test case (r=4)", 9,
                cameraRaysIntersect(camera, 3, 3, sphere));

        //TC0-5: Fifth sphere test case (r=0.5)
        sphere = new Sphere(new Point3D(0, 0, 1), 0.5d);
        assertEquals("TC0-5: Fifth sphere test case (r=0.5)", 0,
                cameraRaysIntersect(camera, 3, 3, sphere));
    }

    /**
     * Test method for Camera rays intersect points with a plane. Uses the private method "cameraRaysIntersect" from above.
     */
    @Test
    public void cameraRaysIntersectPlane() {

        Camera camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);

        //TC0-6: First Plane test case
        Plane plane = new Plane(new Point3D(0, 0, -2), new Vector(0, 0, -1));
        assertEquals("TC0-6: First Plane test case", 9,
                cameraRaysIntersect(camera, 3, 3, plane));

        //TC0-7: Second Plane test case
        plane = new Plane(new Point3D(0, 0, -2), new Vector(0, 0.5d, -1));
        assertEquals("TC0-7: Second Plane test case", 9,
                cameraRaysIntersect(camera, 3, 3, plane));

        //TC0-8: Third Plane test case
        plane = new Plane(new Point3D(0, 0, -3), new Vector(0, (1d / 0.7071067811865475), (-1d / 0.7071067811865475)));
        assertEquals("TC0-8: Third Plane test case", 6,
                cameraRaysIntersect(camera, 3, 3, plane));
    }

    /**
     * Test method for Camera rays intersect points with a triangle. Uses the private method "cameraRaysIntersect" from above.
     */
    @Test
    public void cameraRaysIntersectTriangle() {

        Camera camera = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setViewPlaneDistance(1).setViewPlaneSize(3, 3);

        //TC0-9: First Triangle test case
        Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
        assertEquals("TC0-9: First Triangle test case", 1,
                cameraRaysIntersect(camera, 3, 3, triangle));

        //TC0-10: Second Triangle test case
        triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2));
        assertEquals("TC0-10: Second Triangle test case", 2,
                cameraRaysIntersect(camera, 3, 3, triangle));

    }
}
