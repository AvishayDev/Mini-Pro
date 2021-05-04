package geometries;

import primitives.*;

/**
 * An interface that extends the interface Intersectable, and adds another method that returns the normal
 */
public abstract class Geometry implements Intersectable {

    protected Color emission = Color.BLACK;
    Material material;

    /**
     * A method that receives a point, and returns the normal from the current object to this point
     *
     * @param point The point in which we calculate its normal
     * @return The normal from the object to the received point
     */
    public abstract Vector getNormal(Point3D point) ;

    public Color getEmission(){
        return emission;
    }

    public Material getMaterial(){
        return material;
    }

    public Geometry setEmission(Color color){
        emission = color;
        return this;
    }

    public Geometry setMaterial(Material material){
        material = material;
        return this;
    }
}
