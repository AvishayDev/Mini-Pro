package elements;

import primitives.*;

/**
 * this interface represent all light source that can be in the scene
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
}
