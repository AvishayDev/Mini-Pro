package renderer;

import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import geometries.Intersectable;
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
            //we want to calc the same calculations for a bin of rays
            Vector l = lightSource.getL(intersection.point);
            if (!(lightSource instanceof DirectionalLight) && numOfRaysSoftShadows != 0) {
                Vector orthogonal;
                List<Point3D> point3DS;
                //if (lightSource instanceof PointLight) {
                orthogonal = l.getOrthogonal().normalize();
                //create the points on the light
                point3DS = BlackBoard.FindPointsCircle(((PointLight) lightSource).getPosition(), ((PointLight) lightSource).getRadius(), orthogonal, l.crossProduct(orthogonal).normalize(), numOfRaysSoftShadows);
                //}else{
                //    orthogonal = ((SpotLight) lightSource).getDirection().getOrthogonal().normalize();
                //create the points on the light
                //    point3DS = BlackBoard.FindPointsCircle(((PointLight) lightSource).getPosition(), ((PointLight) lightSource).getRadius(), orthogonal, ((SpotLight) lightSource).getDirection().crossProduct(orthogonal).normalize(), numOfRaysSoftShadows);

                //}

                //create bin of rays from the light to the intersection
                List<Ray> rays = BlackBoard.raysFromPointToPoints(intersection.point, point3DS, true);

                //the calculation of the transparency
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                for (Ray ray : rays) {
                    double nl = alignZero(n.dotProduct(ray.getDir()));
                    if (nl * nv > 0) { // sign(nl) == sing(nv)
                        double ktr = transparency(lightSource, ray.getDir(), n, intersection);
                        if (ktr * k > MIN_CALC_COLOR_K) {
                            color = color.add(lightIntensity.scale(ktr * (calcDiffusive(material.kD, nl) //
                                    + calcSpecular(material.kS, l, n, v, material.nShininess))));
                        }
                    }
                }
                //do average of the values
                return color.reduce(rays.size());
            } else {
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
        }
        return color;
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
            if (numOfRaysGlossySurface == 0)
                color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
            else
                color = calcGlobalEffect(constructReflectedRays(gp, v, n), level, material.kR, kkr);

        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            if (numOfRaysDiffuseGlass == 0)
                color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
            else
                color = color.add(calcGlobalEffect(constructRefractedRays(gp, v, n), level, material.kT, kkt));

        return color;
    }

    private List<Ray> constructReflectedRays(Intersectable.GeoPoint gp, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(v.dotProduct(n) * 2));
        Ray centerRay = new Ray(gp.point, r, n);
        Vector orthogonal = r.getOrthogonal().normalize();
        List<Point3D> point3DS = BlackBoard.FindPointsCircle(gp.point.add(r, GlossyDiffusiveDistance), gp.getRadiusGS(), orthogonal, orthogonal.crossProduct(r).normalize(), numOfRaysGlossySurface);
        return BlackBoard.raysFromPointToPoints(centerRay.getP0(), point3DS, false);
    }

    private List<Ray> constructRefractedRays(Intersectable.GeoPoint gp, Vector v, Vector n) {
        Ray centerRay = new Ray(gp.point, v, n);
        Vector orthogonal = v.getOrthogonal().normalize();
        List<Point3D> point3DS = BlackBoard.FindPointsCircle(gp.point.add(v, GlossyDiffusiveDistance), gp.getRadiusDG(), orthogonal, orthogonal.crossProduct(v).normalize(), numOfRaysDiffuseGlass);
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
    protected Color calcGlobalEffect(List<Ray> rays, int level, double kx, double kkx) {
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