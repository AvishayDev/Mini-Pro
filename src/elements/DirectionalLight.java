package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

// note
public class DirectionalLight extends Light implements LightSource {


    //IL = I0
    // note
    private Vector direction;

    public DirectionalLight(Color light, Vector directionLight){
        super(light);
        direction = directionLight.normalized();
    }
    /**
     * calc the IL of point in the scene
     * @param point point to light
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point3D point) {
        return intensity;
    }

    // note
    @Override
    public Vector getL(Point3D point) {
        return direction;
    }
}
