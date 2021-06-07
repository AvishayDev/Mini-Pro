package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/***
 * LightSource is an interface that includes 2 methods.
 * The first method is "getIntensity" which calculates the Il of point in the scene, receives a Point3D and returns the Color
 * The second method is "getL" which calculates the Vector L of the light, receives a Point3D and returns the Vector.
 */
public interface LightSource {

    /**
     * calc the Il of point in the scene
     *
     * @param point point to light
     * @return the color of the point
     */
    Color getIntensity(Point3D point);

    /***
     * calc the vector L of the light
     * @param point the point to light
     * @return the vector L
     */
    Vector getL(Point3D point);

    /***
     * this function calculate the distance between the light source
     * and the point gets
     * @param point the point to find distance from
     * @return the final distance
     */
    double getDistance(Point3D point);

    /**
     * This method receives a point, a normal from this point and and number and returns a list of rays
     * from the point to the area of the position of the light.
     *
     * @param point  Intersection point
     * @param n      The normal vector to the point
     * @param amount The amount of rays you want to be returned
     * @return A list of rays from the intersection point, changed by delta, to the area of the Light
     */
    List<Ray> getTargetRays(Point3D point, Vector n, int amount);
}
