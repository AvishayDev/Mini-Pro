package unittests.IntegrationTests;

import org.junit.Test;
import elements.*;
import primitives.*;
import geometries.*;

import javax.naming.NoInitialContextException;

import static org.junit.Assert.*;

/**
 * Testing Camera Class
 *
 * @author Dan
 */

public class IntegrationTests {
    /**
     * Test method for
     * {@link elements.Camera#cameraRaysIntersect(int, int, Intersectable)}.
     */
    @Test
    public void cameraRaysIntersect() {

        Camera camera = new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere = new Sphere(new Point3D(0,0,-3),1);

        //TC0-1: First sphere test case Sphere (r=1)
        assertEquals("TC0-1: First sphere test case Sphere r=1", 2,
                camera.cameraRaysIntersect(3,3,sphere).size());

        //TC0-2: Second sphere test case (r=2.5)
        sphere = new Sphere(new Point3D(0,0,-2.5d),2.5d);
        camera.replaceCameraPosition(new Point3D(0,0,0.5));
        assertEquals("TC0-2: Second sphere test case (r=2.5)", 18,
                camera.cameraRaysIntersect(3,3,sphere).size());

        //TC0-3: Third sphere test case (r=2)
        sphere = new Sphere(new Point3D(0,0,-2d),2d);
        assertEquals("TC0-3: Third sphere test case (r=2)", 10,
                camera.cameraRaysIntersect(3,3,sphere).size());

        //TC0-4: Fourth sphere test case (r=4)
        sphere = new Sphere(new Point3D(0,0,-1),4);
        assertEquals("TC0-4: Fourth sphere test case (r=4)", 9,
                camera.cameraRaysIntersect(3,3,sphere).size());

        //TC0-5: Fifth sphere test case (r=0.5)
        sphere = new Sphere(new Point3D(0,0,1),0.5d);
        assertEquals("TC0-5: Fifth sphere test case (r=0.5)", 0,
                camera.cameraRaysIntersect(3,3,sphere).size());

        //TC0-6: First Plane test case
        Plane plane = new Plane(new Point3D(0,0,-2),new Vector(0,0,-1));
        assertEquals("TC0-6: First Plane test case", 9,
                camera.cameraRaysIntersect(3,3,plane).size());

        //TC0-7: Second Plane test case
        plane = new Plane(new Point3D(0,0,-2),new Vector(0,0.5d,-1));
        assertEquals("TC0-7: Second Plane test case", 9,
                camera.cameraRaysIntersect(3,3,plane).size());

        //TC0-8: Third Plane test case
        plane = new Plane(new Point3D(0,0,-3),new Vector(0,(1d/0.7071067811865475),(-1d/0.7071067811865475)));
        assertEquals("TC0-8: Third Plane test case", 6,
                camera.cameraRaysIntersect(3,3,plane).size());

        //TC0-9: First Triangle test case
        Triangle triangle = new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2), new Point3D(-1,-1,-2));
        assertEquals("TC0-9: First Triangle test case", 1,
                camera.cameraRaysIntersect(3,3,triangle).size());

        //TC0-10: Second Triangle test case
        triangle = new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2), new Point3D(-1,-1,-2));
        assertEquals("TC0-10: Second Triangle test case", 2,
                camera.cameraRaysIntersect(3,3,triangle).size());

    }
}
