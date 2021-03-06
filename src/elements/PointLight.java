package elements;

import primitives.*;

import java.util.List;

/**
 * The class PointLight is an extension of the class Light and implements the class LightSource.
 * It includes fields of the position Point3D, a double for the kC value, another one for kL value and another one for kQ value
 * along with intensity color field from Light class.
 * It has one constructor and implements the methods of LightSource.
 */
public class PointLight extends Light implements LightSource {

    /**
     * The radius of the PointLight
     */
    protected double radius = 0.0;

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
     */
    public PointLight(Color light, Point3D point) {
        super(light);
        position = point;
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

    /**
     * This method calculates the distance between the point light and received point and returns it
     *
     * @param point the point to find distance from
     * @return The distance from point light to the received point
     */
    @Override
    public double getDistance(Point3D point) {
        return position.distance(point);
    }

    /**
     * This method receives a point, a normal from this point and and number and returns a list of rays
     * from the point to the area of the position of the light.
     *
     * @param point  Intersection point
     * @param n      The normal vector to the point
     * @param amount The amount of rays you want to be returned
     * @return A list of rays from the intersection point, changed by delta, to the area of the point Light
     */
    @Override
    public List<Ray> getTargetRays(Point3D point, Vector n, int amount) {
        return BlackBoard.raysWithDelta(point, position, position.subtract(point).normalize(), n, radius, amount);
    }

    /***
     * reset the value of kC in builderType
     * @param Kc the kC value
     * @return this object, with the updated values.
     */
    public PointLight setKc(double Kc) {
        kC = Kc;
        return this;
    }

    /***
     * reset the value of kL in builderType
     * @param Kl the kL value
     * @return this object, with the updated values.
     */
    public PointLight setKl(double Kl) {
        kL = Kl;
        return this;
    }

    /***
     * reset the value of kQ in builderType
     * @param Kq the kQ value
     * @return this object, with the updated values.
     */
    public PointLight setKq(double Kq) {
        kQ = Kq;
        return this;
    }

    /**
     * Setter for the radius value in this point light
     *
     * @param radius The new value of the radius
     * @return This object, with the updated values.
     */
    public PointLight setRadius(double radius) {
        this.radius = radius;
        return this;
    }
}
