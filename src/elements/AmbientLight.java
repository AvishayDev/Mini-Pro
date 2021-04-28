package elements;

import primitives.Color;

public class AmbientLight extends Light{



    public AmbientLight(Color intensity,double kA){
        this.intensity = intensity.scale(kA);
    }

    @Override
    public Color getIntensity() {
        return intensity;
    }
}
