package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * The class "SpotLight" extends the class "PointLight".
 * Its fields are a Vector that represent the direction of the light, along with the fields of "PointLight" class.
 * It has one constructor and one method of "getIntensity".
 */
public class SpotLight extends PointLight {


    /**
     * the direction of the light
     */
    private Vector direction;

    private final int narrow;

    /***
     * constructor for the light, reset parameters
     * @param light color of the light
     * @param directionLight the direction of the light
     * @param point the position of the light
     * @param narrowLight the narrow of the light (bigger then 1!)
     */
    public SpotLight(Color light, Vector directionLight, Point3D point, int narrowLight) {
        super(light, point);
        narrow = narrowLight;
        direction = directionLight.normalized();
    }

    /**
     * calc the IL of point in the scene
     * //     (I0*max(0,dir.dotProduct(l))
     * // IL =  -------------------------
     * //         Kc+Ki*d +Kq*(d^2)
     *
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        Color intensityReturn = super.getIntensity(point);
        double dotProDir = Util.alignZero(direction.dotProduct(getL(point)));
        if (narrow != 1)
            return dotProDir > 0 ? intensityReturn.scale(Math.pow(dotProDir, narrow)) : Color.BLACK;
        else
            return dotProDir > 0 ? intensityReturn.scale(dotProDir) : Color.BLACK;
            //if angle <=0 the color is scaled by 0 so return black(0,0,0) => less calculations

    }

}
