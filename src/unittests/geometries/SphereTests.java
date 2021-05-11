package unittests.geometries;

import geometries.Sphere;
import org.junit.Test;
import primitives.Coordinate;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author Avihai & Avishay
 */
public class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sphere = new Sphere(new Point3D(0, 0, 0), 2);
        Point3D point1 = new Point3D(0, 0, 2);
        Vector vec1 = new Vector(0, 0, 1);

        assertEquals("Normal value not valid", vec1, sphere.getNormal(point1));


    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Point3D center = new Point3D(1, 0, 0);
        Sphere sphere = new Sphere(center, 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 1, 0), new Point3D(-1, 0, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Vector(3, 1, 0), new Point3D(-1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p3 = new Point3D(1, 0, 1);
        result = sphere.findIntersections(new Ray(new Vector(0, -0.5, 0.5), new Point3D(1, 0.5, 0.5)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p3), result);

        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(5, 3, 1), new Point3D(1, 2, 2))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        Point3D p4 = new Point3D(1, 0.8, 0.6);
        result = sphere.findIntersections(new Ray(new Vector(0, 1, 2), new Point3D(1, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p4), result);

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, -1, -2), new Point3D(1, 0, -1))));

        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        Point3D p5 = new Point3D(1, 0, -1);
        Point3D p6 = new Point3D(1, 0, 1);
        result = sphere.findIntersections(new Ray(new Vector(0, 0, -3), new Point3D(1, 0, 3)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getZ() > result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p5, p6), result);

        // TC14: Ray starts at sphere and goes inside (1 points)
        Point3D p7 = new Point3D(1, 0, -1);
        result = sphere.findIntersections(new Ray(new Vector(0, 0, -1), new Point3D(1, 0, 1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p7), result);

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Vector(0, 0, -1), new Point3D(1, 0, 0.5)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p7), result);

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Vector(0, 0, -1), center));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(p7), result);

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, 0, 1), new Point3D(1, 0, 1))));

        // TC18: Ray starts after sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, 0, 1), new Point3D(1, 0, 2))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(0, 0, 1))));

        // TC20: Ray starts at the tangent point
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(1, 0, 1))));

        // TC21: Ray starts after the tangent point
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(2, 0, 1))));

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Vector(0, 0, 1), new Point3D(3, 0, 0))));
    }
}