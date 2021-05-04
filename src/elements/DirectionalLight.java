package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{


    //IL = I0

    Vector direction;

    /**
     *
     * calc the Il of point in the scene
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        return null;
    }

    @Override
    public Vector getL(Point3D point) {
        return null;
    }
}
