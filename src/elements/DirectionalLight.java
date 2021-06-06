package elements;

import primitives.*;

import java.util.List;

/**
 * The class DirectionalLight is an extension of the class Light and implements the class LightSource
 * Its field are the color intensity, which is a field from class Light and a vector that represent the direction of the light
 * It has one constructor and implements the methods of LightSource.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction of the light
     */
    private Vector direction;

    /**
     * The opposite direction of the light. It's helpful to store it since it's being used very often.
     */
    private Vector oppositeDirection;

    /***
     * constructor for the DirectionLight. reset the parameters
     * @param light the color of the light
     * @param directionLight the direction of the light, normalized vector
     */
    public DirectionalLight(Color light, Vector directionLight) {
        super(light);
        direction = directionLight.normalized();
        oppositeDirection = direction.scale(-1);
    }

    /**
     * calc the IL of point in the scene
     *
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        return intensity;
    }

    /***
     * calc the vector L of the light
     * @param point the point to light
     * @return the vector L
     */
    @Override
    public Vector getL(Point3D point) {
        return direction;
    }

    /**
     * Returns the distance from any point to a DirectionalLight, which is infinity.
     *
     * @param point the point to find distance from
     * @return Double.POSITIVE_INFINITY since this is a spot light
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * This method receives a point, a normal from this point and and number and returns a list of rays
     * from the point to the area of the position of the light.
     *
     * @param point  Intersection point
     * @param n      The normal vector to the point
     * @param amount The amount of rays you want to be returned
     * @return A list of rays from the intersection point, changed by delta, to the area of the Light
     */
    @Override
    public List<Ray> getTargetRays(Point3D point, Vector n, int amount) {
        return List.of(new Ray(point, oppositeDirection, n));
    }
}
