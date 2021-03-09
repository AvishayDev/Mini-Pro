package primitives;

/***
 * Represents Vector with one single Point3D
 * and have basic functions on the vector.
 */
public class Vector {
    Point3D head;

    /***
     * This constructor gets 3 coordinates and returns a vector that include those values
     * @param x Coordinate x
     * @param y Coordinate y
     * @param z Coordinate z
     */
    public Vector(Coordinate x,Coordinate y,Coordinate z){
        Point3D point=new Point3D(x,y,z);
        if(point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Please Don't Choose The 0 Vector!");
        head=point;
    }

    /***
     * This constructor gets 3 doubles and returns a vector that include those values
     * @param x Value x
     * @param y Value y
     * @param z Value z
     */
    public Vector(double x,double y,double z){
        Point3D point=new Point3D(x,y,z);
        if(point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Please Don't Choose The 0 Vector!");
        head=point;
    }

    /***
     * This constructor gets a Point3D object and sets it as the Vector's head
     * @param point The vector's head
     */
    public Vector(Point3D point) {
        head=new Point3D(point.x, point.y, point.z);
    }

    /***
     * This function sums up between the current vector and the received vector
     * @param vec The vector which we want to add to the current vector
     * @return  The sum of the two vectors
     */
    public Vector add(Vector vec){
        return new Vector(this.head.x.coord+vec.head.x.coord,
                        this.head.y.coord+vec.head.y.coord,
                    this.head.z.coord+vec.head.z.coord);
    }

    /***
     * This function subtracts between the current vector and the received vector
     * @param vec The vector which we want to subtract to the current vector
     * @return The subtraction of the 2 vectors
     */
    public Vector subtract(Vector vec){
        return new Vector(this.head.x.coord-vec.head.x.coord,
                this.head.y.coord-vec.head.y.coord,
                this.head.z.coord-vec.head.z.coord);
    }

    /***
     * This function returns a scaled version of the current vector
     * @param Num The amount of scale we will increase/decrease the vector
     * @return The scaled vector
     */
    public Vector scale(double Num){
        return new Vector(this.head.x.coord*Num,
                this.head.y.coord*Num,
                this.head.z.coord*Num);
    }

    /***
     * This function performs a cross product between the current vector and a received vector
     * @param vec The received vector
     * @return The result of the cross product action.
     */
    public Vector crossProduct(Vector vec){
        return new Vector((this.head.y.coord*vec.head.z.coord)-(this.head.z.coord*vec.head.y.coord),
                (this.head.z.coord*vec.head.x.coord)-(this.head.x.coord*vec.head.z.coord),
                (this.head.x.coord*vec.head.y.coord)-(this.head.y.coord*vec.head.x.coord));
    }

    /***
     * This function performs a dot product between the current vector and a received vector
     * @param vec The received vector
     * @return The result of the dot product action.
     */
    public double dotProduct(Vector vec){
       return (this.head.x.coord*vec.head.x.coord)+
               (this.head.y.coord*vec.head.y.coord)+
               (this.head.z.coord*vec.head.z.coord);
    }

    /***
     * This function returns the length squared of the current vector
     * @return The length squared value, type of double
     */
    public double lengthSquared(){
        return head.distanceSquared(Point3D.ZERO);
    }

    /***
     * This function returns the length of the current vector
     * @return The length value, type of double
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /***
     * This function normalize the current vector
     * @return The current vector, but now normalized
     */
    public Vector normalize(){
      this.head = scale(1/length()).head;
      return this;
    }

    /***
     * This function returns a normalized version the current vector
     * @return The normalized version the current vector
     */
    public Vector normalized(){
        Vector vec = new Vector(this.head);
        return vec.normalize();
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
        Vector other = (Vector)obj;
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
}
