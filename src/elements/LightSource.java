package elements;

import primitives.*;

/***
 * LightSource is an interface that includes 2 methods.
 * The first method is "getIntensity" which calculates the Il of point in the scene, receives a Point3D and returns the Color
 * The second method is "getL" which calculates the Vector L of the light, receives a Point3D and returns the Vector.
 */
public interface LightSource {


    /**
     * calc the Il of point in the scene
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
}
