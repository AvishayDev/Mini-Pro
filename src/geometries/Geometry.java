package geometries;
import primitives.*;

/**
 * An interface that extends the interface Intersectable, and adds another method that returns the normal
 */
public interface Geometry extends Intersectable {

    Color emission = null;
    Material meterial = null;
    
    /**
     * A method that recives a point, and returns the normal from the current object to this point
     * @param point The point in which we calculate its normal
     * @return The normal from the object to the received point
     */
    Vector getNormal(Point3D point);
}
