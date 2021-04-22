package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight implements Light,LightSource{


    //IL = I0

    Vector direction;

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
