package unittests.elements;

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
 *
 */
public class CameraTests {

    /**
     * Test method for
     * {@link elements.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void constructRay() throws NoInitialContextException {

        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals("Bad ray", new Ray(new Vector(-2, -2, 10),Point3D.ZERO),
                camera.setViewPlaneSize(6, 6).constructRay(3, 3, 0, 0));

        // TC02: 4X4 Corner (0,0)
        assertEquals("Bad ray", new Ray( new Vector(-3, -3, 10),Point3D.ZERO),
                camera.setViewPlaneSize(8, 8).constructRay(4, 4, 0, 0));

        // TC03: 4X4 Side (0,1)
        assertEquals("Bad ray", new Ray( new Vector(-1, -3, 10),Point3D.ZERO),
                camera.setViewPlaneSize(8, 8).constructRay(4, 4, 1, 0));

        // TC04: 4X4 Inside (1,1)
        assertEquals("Bad ray", new Ray( new Vector(-1, -1, 10),Point3D.ZERO),
                camera.setViewPlaneSize(8, 8).constructRay(4, 4, 1, 1));

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals("Bad ray", new Ray( new Vector(0, 0, 10),Point3D.ZERO),
                camera.setViewPlaneSize(6, 6).constructRay(3, 3, 1, 1));

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals("Bad ray", new Ray( new Vector(0, -2, 10),Point3D.ZERO),
                camera.setViewPlaneSize(6, 6).constructRay(3, 3, 1, 0));

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals("Bad ray", new Ray(new Vector(-2, 0, 10),Point3D.ZERO),
                camera.setViewPlaneSize(6, 6).constructRay(3, 3, 0, 1));
    }

    @Test
    public void cameraRaysIntersect() throws NoInitialContextException {
        Camera camera = new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        Sphere sphere = new Sphere(new Point3D(0,0,-3),1);

        //TC0-1: First sphere test case Sphere (r=1)
        assertEquals("TC0-1: First sphere test case Sphere r=1", 2,
                camera.cameraRaysIntersect(3,3,sphere).size());

        //TC0-2: Second sphere test case (r=2.5)
        sphere = new Sphere(new Point3D(0,0,-2.5d),2.5d);
        camera.replaceCamera(new Point3D(0,0,0.5));
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
        plane = new Plane(new Point3D(0,0,-2),new Vector(0,0,-1));
        assertEquals("TC0-7: Second Plane test case", 9,
                camera.cameraRaysIntersect(3,3,plane).size());

        //TC0-8: Third Plane test case

        assertEquals("TC0-8: Third Plane test case", 6,
                camera.cameraRaysIntersect(3,3,plane).size());

        //TC0-9: First Triangle test case

        assertEquals("TC0-9: First Triangle test case", 1,
                camera.cameraRaysIntersect(3,3,triangle).size());

        //TC0-10: Second Triangle test case

        assertEquals("TC0-10: Second Triangle test case", 2,
                camera.cameraRaysIntersect(3,3,triangle).size());

    }
}