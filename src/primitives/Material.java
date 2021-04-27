package primitives;

/**
 * This class represents material properties.
 * The class is Passive Data Structure (PDS)
 * and uses Build-like setters for chaining the initialization
 */
public class Material {

    /**
     * Diffusive component attenuation factor of light reflection from the material
     */
    public double kD = 0.0;
    /**
     * Specular component attenuation factor of light reflection from the material
     */
    public double kS = 0.0;
    /**
     * Shininess level of material - affects size and blur of specular effect of light reflection
     */
    public int nShininess = 1;

    public Material setkD(double value) {
        kD = value;
        return this;
    }

    public Material setkS(double value) {
        kS = value;
        return this;
    }
}
