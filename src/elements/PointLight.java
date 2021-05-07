package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * The class PointLight is an extension of the class Light and implements the class LightSource.
 * It includes fields of the position Point3D, a double for the kC value, another one for kL value and another one for kQ value
 * along with intensity color field from Light class.
 * It has one constructor and implements the methods of LightSource.
 */
public class PointLight extends Light implements LightSource {

    /**
     * the position of the light
     */
    protected Point3D position;
    /**
     * the value of light by kC
     */
    protected double kC = 1.0;
    /**
     * the value of light by kL
     */
    protected double kL = 0.0;
    /**
     * the value of light by kQ
     */
    protected double kQ = 0.0;

    /***
     * constructor for this light, reset parameters
     * @param light the color of the light
     * @param point the position in scene
     * @param KcValue the kC value
     * @param KlValue the kL value
     * @param KqValue the kQ value
     */
    public PointLight(Color light, Point3D point, double KcValue, double KlValue, double KqValue) {
        super(light);
        position = point;
        kC = KcValue;
        kL = KlValue;
        kQ = KqValue;
    }

    /**
     * calc the IL of point in the scene
     * <p>
     * //               I0
     * // IL =  ---------------------
     * //       Kc + kl*d +Kq*(d^2)
     *
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        double distanceSquared = position.distanceSquared(point);
        return intensity.reduce(kC + (kL * Math.sqrt(distanceSquared)) + (kQ * distanceSquared));
    }

    /***
     * calc the vector L of the light
     * @param point the point to light
     * @return the vector L
     */
    @Override
    public Vector getL(Point3D point) {
        return point.subtract(position).normalize();
    }
}
