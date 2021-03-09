package primitives;

import java.util.Objects;

/***
 * Represents Ray with vector and 3D point.
 */
public class Ray {
    Point3D p0;
    Vector dir;

    /***
     * Make's a Ray using vector and 3D point
     * @param vec vector for the ray
     * @param point point for the ray
     */
    public Ray(Vector vec, Point3D point){
        dir = vec.normalized();
        p0 = new Point3D(point.x, point.y, point.z);
    }

    /***
     * Equals function, which returns whether the current object and the received objects are equal
     * @param o The received object
     * @return True if the objects are equal, and false if they aren't.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /***
     * Getter for the Point3D field of the ray
     * @return The p0 variable
     */
    public Point3D getP0() {
        return p0;
    }

    /***
     * Getter for the Vector feild of the ray
     * @return The dir variable
     */
    public Vector getDir() {
        return dir;
    }

    /***
     * To string function
     * @return Information about the Point3D's coordinates
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
