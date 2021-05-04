package elements;

import primitives.*;

public interface LightSource {


    /**
     *
     * calc the Il of point in the scene
     * @param point point to light
     * @return the color of the point
     */
    Color getIntensity(Point3D point);

    Vector getL(Point3D point);
}
