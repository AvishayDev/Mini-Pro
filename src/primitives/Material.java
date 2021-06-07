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
    /**
     * Refraction of material attenuation factor of light Refraction from the material
     */
    public double kR = 0.0;
    /**
     * Transparency of material attenuation factor of light Transparency from the material
     */
    public double kT = 0.0;

    /**
     * how big the radius of the spread rays will be for Glossy Surface
     */
    public double radiusGS = 0;
    /**
     * how big the radius of the spread rays will be for Diffusive Glass
     */
    public double radiusDG = 0;
    /*
        for real images:
        kS = 8 * kT
        kD + ((kS/8) || kR) + kT <= 1
    */

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
     * This method is a setter for the double field kR
     * @param kR the int value of kR
     * @return This object of material, with the updated values.
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    /***
     * This method is a setter for the double field kT
     * @param kT the int value of kT
     * @return This object of material, with the updated values.
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     * This method is a setter for the field RadiusGS
     *
     * @param radiusGS The double value of radiusGS
     * @return This object of material, with the updated values.
     */
    public Material setRadiusGS(double radiusGS) {
        this.radiusGS = radiusGS;
        return this;
    }

    /**
     * This method is a setter for the field RadiusDG
     *
     * @param radiusDG The double value of RadiusDG
     * @return This object of material, with the updated values.
     */
    public Material setRadiusDG(double radiusDG) {
        this.radiusDG = radiusDG;
        return this;
    }
}
