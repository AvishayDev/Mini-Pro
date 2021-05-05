package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
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
        double distanceSquared = position.distanceSquared(point);
        Vector l = getL(point);
        return intensity.scale(Util.max(0,direction.dotProduct(l))).reduce(kC+(kL*Math.sqrt(distanceSquared))+(kQ*distanceSquared));
    }

}
