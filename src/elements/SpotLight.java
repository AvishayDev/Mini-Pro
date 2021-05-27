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

    /***
     * constructor for the light, reset parameters
     * @param light color of the light
     * @param point the position of the light
     * @param directionLight the direction of the light
     * @param narrowLight the narrow of the light (bigger then 1!)
     */
    public SpotLight(Color light, Point3D point, Vector directionLight, int narrowLight) {
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
        double dotProDir = Util.alignZero(direction.dotProduct(getL(point)));
        if (dotProDir <= 0) return Color.BLACK; // behind the spot
        if (narrow != 1) dotProDir = Math.pow(dotProDir, narrow); // if there is narrow at all
        return super.getIntensity(point).scale(dotProDir);
        //if angle <=0 the color is scaled by 0 so return black(0,0,0) => less calculations
    }

}
