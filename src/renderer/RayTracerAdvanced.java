package renderer;

import elements.LightSource;
import geometries.Intersectable;

import static geometries.Intersectable.*;

import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerAdvanced extends RayTracerBasic {

    private int numOfRaysSoftShadows = 0;

    private int numOfRaysGlossySurface = 0;

    private int numOfRaysDiffuseGlass = 0;

    private static final int GlossyDiffusiveDistance = 100;


    /**
     * Constructor that calls super constructor
     *
     * @param scene the scene for the image
     */
    public RayTracerAdvanced(Scene scene) {
        super(scene);
    }


    /**
     * Calculates the final color of point in geometry with all the light effects
     *
     * @param intersection the geometry with the point needs to be colorized
     * @param v            the ray direction that cross the geometry
     * @return the final color after adding light effects
     */
    @Override
    protected Color calcLocalEffects(Intersectable.GeoPoint intersection, Vector v, double k) {
        Vector n = intersection.getNormal();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            double ktr = transparency(lightSource, intersection, material, nv, n, l);
            if (ktr * k > MIN_CALC_COLOR_K) {
                Color lightIntensity = lightSource.getIntensity(intersection.point) //
                        .scale(ktr * (calcDiffusive(material.kD, nl) //
                                + calcSpecular(material.kS, l, n, v, material.nShininess)));
                color = color.add(lightIntensity);
            }
        }
        return color;
    }

    /***
     * This is a private function that is being used by the function calcLocalEffects.
     * It receives a light Source, the L vector of light, the normal vector and the GeoPoint of the intersection
     * and returns a double value of the transparency
     * @param light         A LightSource from the current scene
     * @param geoPoint      The intersected GeoPoint
     * @param material  intesection material
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
    protected Color calcGlobalEffects(Intersectable.GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.getNormal();
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            if (numOfRaysGlossySurface == 0 || material.radiusGS == 0)
                color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
            else
                color = calcGlobalEffect(constructReflectedRays(gp, v, n), level, material.kR, kkr);

        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            if (numOfRaysDiffuseGlass == 0 || material.radiusDG == 0)
                color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
            else
                color = color.add(calcGlobalEffect(constructRefractedRays(gp, v, n), level, material.kT, kkt));

        return color;
    }

    private List<Ray> constructReflectedRays(Intersectable.GeoPoint gp, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(v.dotProduct(n) * 2));
        Ray centerRay = new Ray(gp.point, r, n);
        Vector orthogonal = r.getOrthogonal();
        List<Point3D> point3DS = BlackBoard.findPoints(gp.point.add(r, GlossyDiffusiveDistance), gp.geometry.getMaterial().radiusGS, orthogonal, orthogonal.crossProduct(r).normalize(), numOfRaysGlossySurface);
        return BlackBoard.raysFromPointToPoints(centerRay.getP0(), point3DS, false);
    }

    private List<Ray> constructRefractedRays(Intersectable.GeoPoint gp, Vector v, Vector n) {
        Ray centerRay = new Ray(gp.point, v, n);
        Vector orthogonal = v.getOrthogonal();
        List<Point3D> point3DS = BlackBoard.findPoints(gp.point.add(v, GlossyDiffusiveDistance), gp.geometry.getMaterial().radiusDG, orthogonal, orthogonal.crossProduct(v).normalize(), numOfRaysDiffuseGlass);
        return BlackBoard.raysFromPointToPoints(centerRay.getP0(), point3DS, false);
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
    private Color calcGlobalEffect(List<Ray> rays, int level, double kx, double kkx) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            Intersectable.GeoPoint gp = findClosestIntersection(ray);
            color = color.add((gp == null ? scene.background : calcColor(gp, ray.getDir(), level - 1, kkx)).scale(kx));
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