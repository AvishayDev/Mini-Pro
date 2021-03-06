package renderer;

import elements.BlackBoard;
import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.function.Function;

import static geometries.Intersectable.GeoPoint;

/**
 * This class extends RayTracerBasic class
 * and have support for Soft Shadows, Glossy Surface and Diffuse Glass
 */
public class RayTracerAdvanced extends RayTracerBasic {

    /**
     * This is the amount of rays, in case Soft Shadow  is on.
     */
    private int numOfRaysSoftShadows = 0;

    /**
     * This is the amount of rays, in case Glossy Surface  is on.
     */
    private int numOfRaysGlossySurface = 0;

    /**
     * This is the amount of rays, in case Diffuse Glass  is on.
     */
    private int numOfRaysDiffuseGlass = 0;

    /**
     * This is the diffusive distance, relevant only if diffusive glass is on.
     */
    private static final int GlossyDiffusiveDistance = 100;

    /**
     * Constructor that calls super constructor
     *
     * @param scene the scene for the image
     */
    public RayTracerAdvanced(Scene scene) {
        super(scene);
    }

    /***
     * This is a private function that is being used by the function calcLocalEffects.
     * It receives a light Source, the L vector of light, the normal vector and the GeoPoint of the intersection
     * and returns a double value of the transparency
     * @param light         A LightSource from the current scene
     * @param geoPoint      The intersected GeoPoint
     * @param material  intersection material
     * @param nv normal * view direction
     * @param n normal at intersection
     * @param l from light to intersection direction
     * @return A double value of the transparency.
     */
    @Override
    protected double transparency(LightSource light, GeoPoint geoPoint, Material material, double nv, Vector n, Vector l) {
        double lightDistance = light.getDistance(geoPoint.point);
        List<Ray> shadowRays = light.getTargetRays(geoPoint.point, n, numOfRaysSoftShadows);
        double finalKTr = 0;

        for (var shadowRay : shadowRays) {
            Vector lightDir = shadowRay.getDir(); // opposite to l actually
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay, lightDistance);
            double ktr = nv * n.dotProduct(lightDir) >= 0 ? material.kT : 1.0;
            if (ktr > MIN_CALC_COLOR_K && intersections != null) {
                for (GeoPoint gp : intersections)
                    ktr *= gp.geometry.getMaterial().kT;
            }
            finalKTr += ktr;
        }

        return finalKTr / shadowRays.size();
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
    @Override
    protected Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.getNormal();
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        double nv = n.dotProduct(v);
        if (kkr > MIN_CALC_COLOR_K)
            if (numOfRaysGlossySurface == 0)
                color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
            else
                color = calcGlobalEffect(constructReflectedRays(gp.point, v, n, material.radiusGS), level, material.kR, kkr, (vector -> vector.dotProduct(n) * nv < 0));

        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            if (numOfRaysDiffuseGlass == 0)
                color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
            else
                color = color.add(calcGlobalEffect(constructRefractedRays(gp.point, v, n, material.radiusDG), level, material.kT, kkt, (vector -> vector.dotProduct(n) * nv >= 0)));

        return color;
    }

    /**
     * This method receives an intersection GeoPoint, the intersecting vector and the normal vector of gp
     * It returns a list of rays that represents the reflected rays.
     *
     * @param point The closest intersection point
     * @param v     The vector of the intersecting ray
     * @param n     The normal of gp
     * @return A list of the reflected rays
     */
    private List<Ray> constructReflectedRays(Point3D point, Vector v, Vector n, double radiusGS) {
        Vector r = v.subtract(n.scale(v.dotProduct(n) * 2));
        return BlackBoard.raysWithDelta(point, point.add(r, GlossyDiffusiveDistance), r, n, radiusGS, numOfRaysGlossySurface);
    }

    /**
     * This method receives an intersection GeoPoint, the intersecting vector and the normal vector of gp
     * It returns a list of rays that represents the refracted rays.
     *
     * @param point The closest intersection point
     * @param v     The vector of the intersecting ray
     * @param n     The normal of gp
     * @return A list of the refracted rays
     */
    private List<Ray> constructRefractedRays(Point3D point, Vector v, Vector n, double radiusDG) {
        return BlackBoard.raysWithDelta(point, point.add(v, GlossyDiffusiveDistance), v, n, radiusDG, numOfRaysDiffuseGlass);
    }

    /***
     * This is a private function that is being used by the functions calcColor and calcGlobalEffect.
     * It receives a ray, int value of level and double values of kx and kkx and returns the calculated color.
     * @param rays   The new ray for reflection/refraction
     * @param level The level of depp in the recursion
     * @param kx    kR or kT value (reflection or refraction)
     * @param kkx   The recursive value K multiply by kR or kT
     * @return The calculated Color.
     */
    private Color calcGlobalEffect(List<Ray> rays, int level, double kx, double kkx, Function<Vector, Boolean> dotProCheck) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            if (dotProCheck.apply(ray.getDir())) {
                GeoPoint gp = findClosestIntersection(ray);
                color = color.add((gp == null ? scene.background : calcColor(gp, ray.getDir(), level - 1, kkx)).scale(kx));

            }
        }
        return color.reduce(rays.size());
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if Soft Shadows is on.
     *
     * @param numOfRaysSoftShadows The amount of rays you want to go through the focal point.
     * @return This RayTracerAdvanced, with the updated values.
     */
    public RayTracerAdvanced setNumOfRaysSoftShadows(int numOfRaysSoftShadows) {
        this.numOfRaysSoftShadows = numOfRaysSoftShadows;
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if Glossy Surface is on.
     *
     * @param numOfRaysGlossySurface The amount of rays you want to go through the focal point.
     * @return This RayTracerAdvanced, with the updated values.
     */
    public RayTracerAdvanced setNumOfRaysGlossySurface(int numOfRaysGlossySurface) {
        this.numOfRaysGlossySurface = numOfRaysGlossySurface;
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if Diffuse Glass is on.
     *
     * @param numOfRaysDiffuseGlass The amount of rays you want to go through the focal point.
     * @return This RayTracerAdvanced, with the updated values.
     */
    public RayTracerAdvanced setNumOfRaysDiffuseGlass(int numOfRaysDiffuseGlass) {
        this.numOfRaysDiffuseGlass = numOfRaysDiffuseGlass;
        return this;
    }

}