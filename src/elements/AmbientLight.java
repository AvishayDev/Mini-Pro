package elements;

import primitives.Color;

/***
 * AmbientLight is a extends class of the abstract class Light
 * It contains a constructor, and an implementation of getIntensity
 */
public class AmbientLight extends Light{



    public AmbientLight(Color intensity,double kA){
        this.intensity = intensity.scale(kA);
    }


}
