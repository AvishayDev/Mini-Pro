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
    public int nShininess = 0;


    /***
     * Empty constructor. This class is PDS, so use the setters to set values to kD, kS and Shininess instead.
     */
    public Material() {
    }

    /***
     * This method is a setter for the double field kD.
     * @param value the double value of kD.
     * @return This object of material, with the updated values.
     */
    public Material setKd(double value) {
        kD = value;
        return this;
    }

    /***
     * This method is a setter for the double field kS.
     * @param value the double value of kS.
     * @return This object of material, with the updated values.
     */
    public Material setKs(double value) {
        kS = value;
        return this;
    }

    /***
     * This method is a setter for the int field nShininess
     * @param nShininess the int value of nShininess
     * @return This object of material, with the updated values.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /***
     * This method is a getter for the field kD.
     * @return The kD value, in form of a double.
     */
    public double getkD() {
        return kD;
    }

    /***
     * This method is a getter for the field kS.
     * @return The kS value, in form of a double.
     */
    public double getkS() {
        return kS;
    }

    /***
     * This method is a getter for the field nShininess.
     * @return The nShininess value, in form of int.
     */
    public int getnShininess() {
        return nShininess;
    }
}
