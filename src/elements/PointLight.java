package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

// note
public class PointLight extends Light implements LightSource {

    // note
    protected Point3D position;
    // note
    protected double kC;
    // note
    protected double kL;
    // note
    protected double kQ;

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
        double distanceSquared = position.distanceSquared(point);
        return intensity.reduce(kC+(kL*Math.sqrt(distanceSquared))+(kQ*distanceSquared));
    }

    // note
    @Override
    public Vector getL(Point3D point) {
        return point.subtract(position);
    }
}
