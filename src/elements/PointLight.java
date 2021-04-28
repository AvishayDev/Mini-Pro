package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource{

    Point3D position;
    double kC;
    double kL;
    double kQ;

    //               I0
    // IL =  ---------------------
    //       Kc + kl*d +Kq*(d^2)



    @Override
    public Color getIntensity() {
        return null;
    }

    @Override
    public Color getIntensity(Point3D point) {
        return null;
    }

    @Override
    public Vector getL(Point3D point) {
        return null;
    }
}
