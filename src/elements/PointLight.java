package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

// note
public class PointLight extends Light implements LightSource {

    // note
    Point3D position;
    // note
    double kC;
    // note
    double kL;
    // note
    double kQ;

    //               I0
    // IL =  ---------------------
    //       Kc + kl*d +Kq*(d^2)


    /**
     * calc the Il of point in the scene
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        return null;
    }

    // note
    @Override
    public Vector getL(Point3D point) {
        return null;
    }
}
