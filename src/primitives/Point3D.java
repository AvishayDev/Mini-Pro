package primitives;

import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
import java.awt.*;

/***
 * Represents 3D point with 3 Coordinates,
 * and have basic functions on the point.
 */
public class Point3D {

    /**
     * the x coordinate of point n the scene
     */
    final Coordinate x;
    /**
     * the y coordinate of point n the scene
     */
    final Coordinate y;
    /**
     * the z coordinate of point n the scene
     */
    final Coordinate z;

    /***
     * This point is the center of our coordinate system
     */
    final public static Point3D ZERO = new Point3D(0, 0, 0);

    /***
     * Make's Point3D object with 3 Coordinates
     * @param x First Coordinate
     * @param y Second Coordinate
     * @param z Third Coordinate
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = new Coordinate(x.coord);
        this.y = new Coordinate(y.coord);
        this.z = new Coordinate(z.coord);
    }

    /***
     * Make's Point3D object with 3 Coordinates values
     * @param x First Coordinate values
     * @param y Second Coordinate values
     * @param z Third Coordinate values
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /***
     * Add vector to this point and return it.
     * (with no changes in the point)
     * @param vec Vector for adding to the point
     * @return The final point
     */
    public Point3D add(Vector vec) {
        return new Point3D(this.x.coord + vec.head.x.coord, this.y.coord + vec.head.y.coord, this.z.coord + vec.head.z.coord);
    }

    public Point3D add(Point3D point) {
        return new Point3D(this.x.coord + point.x.coord, this.y.coord + point.y.coord, this.z.coord + point.z.coord);
    }
    /***
     * Add vector to this point and returns the calculated point.
     * (with no changes in the point)
     * @param vec Vector for adding to the point
     * @return The final point
     */
    public Point3D add(Vector vec,double t) {
        return new Point3D(this.x.coord + (vec.getX() *t), this.y.coord + (vec.getY()*t), this.z.coord + (vec.getZ()*t));
    }

    /***
     * Make's vector from the 1st point to the 2sd point
     * @param point 1st point
     * @return The final vector
     */
    public Vector subtract(Point3D point) {
        return new Vector(this.x.coord - point.x.coord, this.y.coord - point.y.coord, this.z.coord - point.z.coord);
    }

    /***
     * Calculate the distance squared between this point and the
     * get point
     * @param point The point which we calculate the distance from
     * @return distance squared between the 2 points
     */
    public double distanceSquared(Point3D point) {
        return ((this.x.coord - point.x.coord) * (this.x.coord - point.x.coord)) +
                ((this.y.coord - point.y.coord) * (this.y.coord - point.y.coord)) +
                ((this.z.coord - point.z.coord) * (this.z.coord - point.z.coord));

    }

    /***
     * Calculate the distance squared between this point and the
     * get point
     * @param point The point which we calculate the distance from
     * @return distance squared between the 2 points
     */
    public double distanceSquared(GeoPoint point) {
        return ((this.x.coord - point.point.getX()) * (this.x.coord - point.point.getX())) +
                ((this.y.coord - point.point.getY()) * (this.y.coord - point.point.getY())) +
                ((this.z.coord - point.point.getZ()) * (this.z.coord - point.point.getZ()));

    }

    /***
     * Calculate the distance between this point and the
     * get point
     * @param point The point which we calculate the distance from
     * @return distance between the 2 points
     */
    public double distance(Point3D point) {
        return Math.sqrt(distanceSquared(point));
    }

    /***
     * Calculate the distance between this point and the
     * get point
     * @param point The point which we calculate the distance from
     * @return distance between the 2 points
     */
    public double distance(GeoPoint point) {
        return Math.sqrt(distanceSquared(point));
    }

    /***
     * This function returns a scaled version of the current point
     * @param t The amount of scale we will increase/decrease the vector
     * @return The scaled point
     */
    public Point3D scale(double t) {
        return new Point3D(this.getX() * t, this.getY() * t, this.getZ() * t);
    }

    /***
     * Equals function, which returns whether the current object and the received objects are equal
     * @param obj The received object
     * @return True if the objects are equal, and false if they aren't.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
    }

    /***
     * To string function
     * @return Information about the Point3D's coordinates
     */
    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /***
     * Getter for the x coordinate field of the point3D
     * @return The x coordinate
     */
    public double getX() {
        return x.coord;
    }

    /***
     * Getter for the y coordinate field of the point3D
     * @return The y coordinate
     */
    public double getY() {
        return y.coord;
    }

    /***
     * Getter for the z coordinate field of the point3D
     * @return The z coordinate
     */
    public double getZ() {
        return z.coord;
    }

}
