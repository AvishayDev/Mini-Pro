package elements;

import primitives.*;

import java.util.List;

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

    /**
     * todo write notes
     */
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

    /**
     * This method receives a point, a normal from this point and and number and returns a list of rays
     * from the point to the area of the position of the light.
     *
     * @param point  Intersection point
     * @param n      The normal vector to the point
     * @param amount The amount of rays you want to be returned
     * @return A list of rays from the intersection point, changed by delta, to the area of the Spot Light
     */
    @Override
    public List<Ray> getTargetRays(Point3D point, Vector n, int amount) {
        Ray ray = new Ray(point, position.subtract(point), n);
        if (radius == 0.0)
            return List.of(ray);
        Vector orthogonal = direction.getOrthogonal();
        return BlackBoard.raysFromPointToPoints(ray.getP0(), //
                BlackBoard.findPoints(position, radius, orthogonal, direction.crossProduct(orthogonal).normalize(), amount), //
                false);
    }
}
