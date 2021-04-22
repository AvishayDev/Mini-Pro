package elements;

import primitives.Color;
import primitives.Vector;

public class SpotLight extends PointLight{

    //     (I0*max(0,dir.dotProduct(l))
    // IL =  -------------------------
    //         Kc+Ki*d +Kq*(d^2)

    Vector direction;

    @Override
    public Color getIntensity() {
        return null;
    }
}
