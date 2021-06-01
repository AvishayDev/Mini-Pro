package primitives;

/***
 * Represents Vector with one single Point3D
 * and have basic functions on the vector.
 */
public class Vector {

    /**
     * the head of the vector
     */
    Point3D head;

    /**
     * X-axis unit vector
     */
    public static final Vector X = new Vector(1, 0, 0);

    /**
     * Y-axis unit vector
     */
    public static final Vector Y = new Vector(0, 1, 0);

    /**
     * Z-axis unit vector
     */
    public static final Vector Z = new Vector(0, 0, 1);

    /***
     * This constructor gets 3 coordinates and returns a vector that include those values
     * @param x Coordinate x
     * @param y Coordinate y
     * @param z Coordinate z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D point = new Point3D(x, y, z);
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Please Don't Choose The 0 Vector!");
        head = point;
    }

    /***
     * This constructor gets 3 doubles and returns a vector that include those values
     * @param x Value x
     * @param y Value y
     * @param z Value z
     */
    public Vector(double x, double y, double z) {
        Point3D point = new Point3D(x, y, z);
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Please Don't Choose The 0 Vector!");
        head = point;
    }

    /***
     * This constructor gets a Point3D object and sets it as the Vector's head
     * @param point The vector's head
     */
    public Vector(Point3D point) {
        if (point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Please Don't Choose The 0 Vector!");
        head = new Point3D(point.x, point.y, point.z);
    }

    public Vector getOrthogonal() {
        if (Util.isZero(head.getY())&& Util.isZero(head.getZ()))
            return new Vector(0,1,0);
        return new Vector(0,-getZ(),getY());
    }

    /***
     * this function take this vector and move it to the
     * point place NOT CHANGE THE VECTOR
     * @param point point to add for the vector
     * @return new vector with the add of the point
     */
    public Vector add(Point3D point) {
        return new Vector(this.head.getX() + point.getX()
                , this.head.getY() + point.getY()
                , this.head.getZ() + point.getZ());
    }


    /***
     * This function sums up between the current vector and the received vector
     * @param vec The vector which we want to add to the current vector
     * @return The sum of the two vectors
     */
    public Vector add(Vector vec) {
        return new Vector(this.head.getX() + vec.head.getX(),
                this.head.getY() + vec.head.getY(),
                this.head.getZ() + vec.head.getZ());
    }

    /***
     * This function subtracts between the current vector and the received vector
     * @param vec The vector which we want to subtract to the current vector
     * @return The subtraction of the 2 vectors
     */
    public Vector subtract(Vector vec) {
        return new Vector(this.head.getX() - vec.head.getX(),
                this.head.getY() - vec.head.getY(),
                this.head.getZ() - vec.head.getZ());
    }

    /***
     * This function returns a scaled version of the current vector
     * @param Num The amount of scale we will increase/decrease the vector
     * @return The scaled vector
     */
    public Vector scale(double Num) {
        return new Vector(this.head.getX() * Num,
                this.head.getY() * Num,
                this.head.getZ() * Num);
    }

    /***
     * This function performs a cross product between the current vector and a received vector
     * @param vec The received vector
     * @return The result of the cross product action.
     */
    public Vector crossProduct(Vector vec) {
        return new Vector((this.head.getY() * vec.head.getZ()) - (this.head.getZ() * vec.head.getY()),
                (this.head.getZ() * vec.head.getX()) - (this.head.getX() * vec.head.getZ()),
                (this.head.getX() * vec.head.getY()) - (this.head.getY() * vec.head.getX()));
    }

    /***
     * This function performs a dot product between the current vector and a received vector
     * @param vec The received vector
     * @return The result of the dot product action.
     */
    public double dotProduct(Vector vec) {
        return (this.head.getX() * vec.head.getX()) +
                (this.head.getY() * vec.head.getY()) +
                (this.head.getZ() * vec.head.getZ());
    }


    /***
     * This function performs a dot product between the current vector and a received point
     * @param point The received vector
     * @return The result of the dot product action.
     */
    public double dotProduct(Point3D point) {
        return (this.head.getX() * point.getX()) +
                (this.head.getY() * point.getY()) +
                (this.head.getZ() * point.getZ());
    }

    /***
     * This function returns the length squared of the current vector
     * @return The length squared value, type of double
     */
    public double lengthSquared() {
        return head.distanceSquared(Point3D.ZERO);
    }

    /***
     * This function returns the length of the current vector
     * @return The length value, type of double
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector divide(Vector vec) {
        return new Vector(getX() / vec.getX(), getY() / vec.getY(), getZ() / vec.getZ());
    }

    /***
     * This function normalize the current vector
     * @return The current vector, but now normalized
     */
    public Vector normalize() {
        double size = length();
        this.head = new Point3D(head.getX() / size, head.getY() / size, head.getZ() / size);
        return this;
    }

    /***
     * This function returns a normalized version the current vector
     * @return The normalized version the current vector
     */
    public Vector normalized() {
        return new Vector(this.head).normalize();
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
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return head.equals(other.head);
    }

    /***
     * To string function
     * @return Information about the vector's head
     */
    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }

    /***
     * Getter for the head field of the vector
     * @return The vector's head
     */
    public Point3D getHead() {
        return head;
    }

    /***
     * This method is a getter for the X value of the head of this Vector.
     * @return the X value of the head of this vector, in form of a double.
     */
    public double getX() {
        return this.head.getX();
    }

    /***
     * This method is a getter for the Y value of the head of this Vector.
     * @return the Y value of the head of this vector, in form of a double.
     */
    public double getY() {
        return this.head.getY();
    }

    /***
     * This method is a getter for the Z value of the head of this Vector.
     * @return the Z value of the head of this vector, in form of a double.
     */
    public double getZ() {
        return this.head.getZ();
    }
}
