package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


/***
 * Represents Triangle with Plane and three 3D points.
 */
public class Triangle extends Polygon{


    /***
     * Triangle constructor based on vertices list. The list have 3 points
     * and more can't be added. The polygon must be convex.
     * @param point1 First point of the Triangle
     * @param point2 second point of the Triangle
     * @param point3 Third point of the Triangle
     */
    public Triangle(Point3D point1, Point3D point2, Point3D point3){
        super(point1,point2,point3);
    }

    /***
     * To string function
     * @return Returns information about the triangle's vertices and its plane
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
       //if all the dot-prodoct(v,N) are +/- the ray in the Paramida
        //else the ray dont cross the Triangle


        /***
         * 1) חישוב וקטורים
         * 2) חישוב נורמלים
         * 3) חישוב סימני dot-prodoct
         * 4) אם קיבלנו 0 באחד הסימנים אז אנחנו על הצלע או בקודקוד
         *  אם קיבלנו 2 אפסים אז זה נמצא על שני צלעות אז זה בעצם על הקודקוד המשותף שלהם
         *  צריך לבדוק - יכול להיות 0 גם על המשכי הצלעות!
         */
        return null;
    }
}
