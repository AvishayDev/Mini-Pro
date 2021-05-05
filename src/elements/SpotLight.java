package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

// note
public class SpotLight extends PointLight {

    //     (I0*max(0,dir.dotProduct(l))
    // IL =  -------------------------
    //         Kc+Ki*d +Kq*(d^2)

    // note
    private Vector direction;

    public SpotLight(Color light,Vector directionLight, Point3D point, double KcValue,double KlValue,double KqValue){
        super(light,point,KcValue,KlValue,KqValue);
        direction = directionLight;
    }

    /**
     * calc the IL of point in the scene
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
