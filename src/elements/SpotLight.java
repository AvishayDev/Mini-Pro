package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * this class represent light from point in the scene and
 * light for i direction with narrow light effect.
 */
public class SpotLight extends PointLight {


    /**
     * the direction of the light
     */
    private Vector direction;

    /***
     * constructor for the light, reset parameters
     * @param light color of the light
     * @param directionLight the direction of the light
     * @param point the position of the light
     * @param KcValue the kC value
     * @param KlValue the kL value
     * @param KqValue the kQ value
     */
    public SpotLight(Color light,Vector directionLight, Point3D point, double KcValue,double KlValue,double KqValue){
        super(light,point,KcValue,KlValue,KqValue);
        direction = directionLight.normalized();
    }

    /**
     * calc the IL of point in the scene
     * //     (I0*max(0,dir.dotProduct(l))
     * // IL =  -------------------------
     * //         Kc+Ki*d +Kq*(d^2)
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        double distanceSquared = position.distanceSquared(point);
        double Alpha = direction.dotProduct(getL(point));
        //if angle <=0 the color is scaled by 0 so return black(0,0,0) => less calculations
        return Alpha > 0 ? intensity.scale(Alpha*Alpha*Alpha).reduce(kC + (kL * Math.sqrt(distanceSquared)) + (2 * kQ * distanceSquared)) : Color.BLACK;
    }

}
