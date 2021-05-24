package geometries;

import primitives.*;

/**
 * An interface that extends the interface Intersectable, and adds another method that returns the normal
 */
public abstract class Geometry implements Intersectable {

    /**
     * The color that is being returned from the 3D model object. Default color is black.
     */
    protected Color emission = Color.BLACK;

    /*
    Point3D minPoint;
    Point3D maxPoint;

    private void findMinMax();
    public boolean isIntersect(Ray ray) {

        A = min point
        B = max point
        O = p0 of ray
        D = dir of ray

        1) find t's for may intersect by the equations:
        tAx = (Ax - Ox) / Dx
        tAy = (Ay - Oy) / Dy
        tBx = (Bx - Ox) / Dx
        tBy = (By - Oy) / Dy

        for each t do:
        if t >= 0
            2) calc the point with the ray.getPoint(t)
            3) if point Sustained this, so take it:
                Ax < Px < Bx
                Ay < Py < By
                Az < Pz < Bz
                return true;

        - if all pass:
            return false;
    }

    */

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
