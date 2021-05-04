package elements;

import primitives.*;

// note
public interface LightSource {


    /**
     * calc the Il of point in the scene
     * @param point point to light
     * @return the color of the point
     */
    Color getIntensity(Point3D point);

    // note
    Vector getL(Point3D point);
}
