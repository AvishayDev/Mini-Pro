package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

// note
public class PointLight extends Light implements LightSource {

    // note
    private Point3D position;
    // note
    private double kC;
    // note
    private double kL;
    // note
    private double kQ;

    //               I0
    // IL =  ---------------------
    //       Kc + kl*d +Kq*(d^2)

    public PointLight(Color light, Point3D point, double KcValue,double KlValue,double KqValue){
        super(light);
        position =point;
        kC=KcValue;
        kL = KlValue;
        kQ = KqValue;
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
