package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * An interface that extends the interface Intersectable, and adds another method that returns the normal
 */
public abstract class Geometry implements Intersectable {

    /**
     * The color that is being returned from the 3D model object. Default color is black.
     */
    protected Color emission = Color.BLACK;



    public double[] minPoint = new double[3];
    public double[] maxPoint = new double[3];

    protected abstract void findMinMax();



    /**
     * The material type of the 3D model object.
     */
    private Material material = new Material();

    /**
     * A method that receives a point, and returns the normal from the current object to this point
     *
     * @param point The point in which we calculate its normal
     * @return The normal from the object to the received point
     */
    public abstract Vector getNormal(Point3D point);

    /***
     * Getter for the Color emission field of the Geometry
     * @return The emission
     */
    public Color getEmission() {
        return emission;
    }

    /***
     * Getter for the Material field of the Geometry
     * @return The Material value
     */
    public Material getMaterial() {
        return material;
    }

    /***
     * Setter for the Color emission field of the Geometry
     * @return This object, the geometry
     */
    public Geometry setEmission(Color color) {
        emission = color;
        return this;
    }

    /***
     * Setter for the Material field of the Geometry
     * @return This object, the geometry
     */
    public Geometry setMaterial(Material material1) {
        material = material1;
        return this;
    }

}
