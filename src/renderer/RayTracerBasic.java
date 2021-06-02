package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * This class extends RayTracerBase abstract class
 * and have more functions for Specific calculations
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * the starting point for the k calculations
     */
    private static final double INITIAL_K = 1.0;
    /**
     * stop parameter for the recursive functions - maximum level of deep
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * stop parameter for the recursive functions - minimum level of k effect
     */
    protected static final double MIN_CALC_COLOR_K = 0.001;

    //private int numOfRaysSoftShadows = 0;

    /**
     * Constructor that calls super constructor
     *
     * @param scene the scene for the image
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /***
     * Implements the function 'traceRay', calc the color of
     * ray came from the camera
     * @param ray the ray from the camera throw the scene
     * @return the color of the pixel of the ray
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray.getDir());
    }

    /***
     * This is a private function that is being used by the other calcColor function below here
     * It receives the closest intersection point, a ray, int value of level and double value of k and returns the
     * calculated Color.
     * @param intersection  The closest intersection point
     * @param v             The intersecting ray direction
     * @param level         The level of deep (recursive value)
     * @param k             The k value for calculations (recursive value)
     * @return The calculated Color
     */
    protected Color calcColor(GeoPoint intersection, Vector v, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, v, k));
        return level == 1 ? color : color.add(calcGlobalEffects(intersection, v, level, k));
    }

    /***
     * This is a private function that is being used by the function traceRay.
     * It receives a point and a ray, and returns the calculated color.
     * @param geoPoint  The closest intersection point
     * @param v       The intersecting ray direction
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint geoPoint, Vector v) {
        return calcColor(geoPoint, v, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientGetIntensity());
    }

    /***
     * This is a private function that is being used by the function CalcColor.
     * It receives the closest intersection point, the vector of the ray, the int value of level and the
     * double value of k and returns the calculated color.
     * @param gp    The closest intersection point
     * @param v     The vector of the intersecting ray
     * @param level The level of deep (recursive value)
     * @param k     The k value for calculations (recursive value)
     * @return The calculated color.
     */
    protected Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.getNormal();
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    /***
     * This is a private function that is being used by the function calcGlobalEffects.
     * It receives a point, a vector and a normal from the intersection point and returns a Refracted Ray.
     * @param point The intersection point
     * @param v     The vector of the intersecting ray
     * @param n     The normal of the geometry.
     * @return The refracted ray.
     */
    protected Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /***
     * This is a private function that is being used by the function calcGlobalEffects.
     * It receives a point, a vector and a normal from the intersection point and returns a Reflected Ray.
     * @param point The intersection point
     * @param v     The vector of the intersecting ray
     * @param n     The normal of the geometry.
     * @return The reflected ray.
     */
    protected Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(v.dotProduct(n) * 2));
        return new Ray(point, r, n);
    }

    /***
     * This is a private function that is being used by the functions TraceRay and calcGlobalEffect.
     * It receives a ray and returns the closest intersection GeoPoint of the geometry.
     * @param ray   The intersect rai
     * @return The closest Intersected GeoPoint
     */
    protected GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /***
     * This is a private function that is being used by the functions calcColor and calcGlobalEffect.
     * It receives a ray, int value of level and double values of kx and kkx and returns the calculated color.
     * @param ray   The new ray for reflection/refraction
     * @param level The level of depp in the recursion
     * @param kx    kR or kT value (reflection or refraction)
     * @param kkx   The recursive value K multiply by kR or kT
     * @return The calculated Color.
     */
    protected Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray.getDir(), level - 1, kkx)).scale(kx);
    }

    /**
     * Calculates the final color of point in geometry with all the light effects
     *
     * @param intersection the geometry with the point needs to be colorized
     * @param v            the ray direction that cross the geometry
     * @return the final color after adding light effects
     */
    protected Color calcLocalEffects(GeoPoint intersection, Vector v, double k) {
        Vector n = intersection.getNormal();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point) //
                            .scale(ktr * (calcDiffusive(material.kD, nl) //
                                    + calcSpecular(material.kS, l, n, v, material.nShininess)));
                    color = color.add(lightIntensity);
                }
            }
        }
        return color;
    }

    /***
     * This is a private function that is being used by the function calcLocalEffects.
     * It receives a light Source, the L vector of light, the normal vector and the GeoPoint of the intersection
     * and returns a double value of the transparency
     * @param light         A LightSource from the current scene
     * @param l             The L vector of the LightSource
     * @param n             The normal vector of the geometry
     * @param geoPoint      The intersected GeoPoint
     * @return A double value of the transparency.
     */
    protected double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move

        double lightDistance = light.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return 1.0;

        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            ktr *= gp.geometry.getMaterial().kT;
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }

    /***
     * this function calculate the Diffusive effect on the color by light
     * @param kd the material kD factor
     * @param dotProCalc the vector from the light position to the point on geometry multiplied by
     *                   the normal of the geometry in intersection point
     * @return the final color attenuation factor of Diffusing
     */
    protected double calcDiffusive(double kd, double dotProCalc) {
        if (dotProCalc < 0) dotProCalc = -dotProCalc;
        return kd * dotProCalc;
    }

    /***
     * this function calculate the Specular effect on the color by light
     * @param ks the kS value from material
     * @param l the vector from the light position to the point on geometry
     * @param n the normal of the geometry in intersection point
     * @param v the vector from the camera
     * @param nShininess the value of Shininess
     * @return the final color attenuation factor on Specular
     */
    protected double calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
        double angle = -alignZero(v.dotProduct(r));
        return angle > 0 ? ks * Math.pow(angle, nShininess) : 0.0;
    }


    @Override
    public Color traceRays(List<Ray> rays) {
        Color finalColor = Color.BLACK;
        for (Ray ray : rays) {
            finalColor = finalColor.add(traceRay(ray));
        }
        finalColor = finalColor.reduce(rays.size());
        return finalColor;
    }
}
