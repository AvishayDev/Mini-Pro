package elements;

import primitives.Color;

/***
 * Light is an abstract class that contains the protected field Color. (Not to be confused with Java.awt color)
 * It also contains an abstract getter method for this field
 */
public abstract class Light {

    // This color field represent the intensity of the light.
    protected Color intensity;

    /***
     * Getter for the Color field of the Light
     * @return The Color variable
     */
    public Color getIntensity() {
        return intensity;
    }
}
