package primitives;

import java.util.Objects;

/***
 * Represents Ray with vector and 3D point.
 */
public class Ray {
    private final Point3D p0;
    private final Vector dir;

    /***
     * Make's a Ray using vector and 3D point
     * @param vec vector for the ray
     * @param point point for the ray
     */
    public Ray(Vector vec, Point3D point){
        dir = vec.normalized();
        p0 = point;
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
     * calculate the place of point from p0 by t scale
     * @param t the scale to v
     * @return the point multiply by t from p0 in the angle of dir
     */
    public Point3D getPoint(double t){

        return p0.add(dir.scale(t));
    }

    /***
     * calculate the t for the point on the ray
     * @param point the point on the ray
     * @return
     */
    public double getT(Point3D point){
        return point.subtract(p0).length();
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
