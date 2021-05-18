package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * The class DirectionalLight is an extension of the class Light and implements the class LightSource
 * Its field are the color intensity, which is a field from class Light and a vector that represent the direction of the light
 * It has one constructor and implements the methods of LightSource.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * the direction of the light
     */
    private Vector direction;

    /***
     * constructor for the DirectionLight. reset the parameters
     * @param light the color of the light
     * @param directionLight the direction of the light, normalized vector
     */
    public DirectionalLight(Color light, Vector directionLight) {
        super(light);
        direction = directionLight.normalized();
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

    @Override
    public double getDistance(Intersectable.GeoPoint geoPoint) {
        return 0;
    }
}
