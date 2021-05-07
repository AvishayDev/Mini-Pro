package elements;

import primitives.Color;

/***
 * AmbientLight is a extends class of the abstract class Light
 * It contains a constructor and default constructor
 */
public class AmbientLight extends Light {

    /***
     * Constructor for Ambient Light object. It receives a Color and a power (kA), then initialize the intensity value with the
     * received color scaled by the received power
     * @param intensity The color of the ambient light
     * @param kA The power of the received color
     */
    public AmbientLight(Color intensity, double kA) {
        super(intensity.scale(kA));
    }

    /***
     * Default constructor, only initializing the constructor of "Light" With black.
     */
    public AmbientLight(){
        super(Color.BLACK);
    }
}
