package elements;

import geometries.Intersectable;
import primitives.*;

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
     *
     * @param point
     * @param n
     * @param amount
     * @return
     */
    List<Ray> getTargetRays(Point3D point, Vector n, int amount);
}
