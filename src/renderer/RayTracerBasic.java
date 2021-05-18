package renderer;

import static geometries.Intersectable.GeoPoint;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * This class extends RayTracerBase abstract class
 * and have more functions for Specific calculations
 */
public class RayTracerBasic extends RayTracerBase {



    private static final double DELTA = 0.1;
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
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /***
     * The function calculate the color of point in geometry
     * @param intersection the GeoPoint of the point and geometry
     * @param ray the ray cross the geometry
     * @return the final color with lights around
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientGetIntensity()
                .add(intersection.geometry.getEmission())
                .add(calcLocalEffects(intersection, ray));

    }

    /**
     * Calculates the final color of point in geometry with all the light effects
     *
     * @param intersection the geometry with the point needs to be colorized
     * @param ray          the ray that cross the geometry
     * @return the final color after adding light effects
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd(), ks = material.getKs();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(lightSource,l,n,intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        Point3D point = geopoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (Util.alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0)
                return false;
        }
        return true;
    }
    /***
     * this function calculate the Diffusive effect on the color by light
     * @param kd the material kD factor
     * @param l the vector from the light position to the point on geometry
     * @param n the normal of the geometry in intersection point
     * @param lightIntensity the power of light came from the light
     * @return the final color of Diffusing
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double dotProCalc = l.dotProduct(n);
        if (Util.alignZero(dotProCalc) < 0)
            dotProCalc = -dotProCalc;

        return lightIntensity.scale(kd * dotProCalc);
    }

    /***
     * this function calculate the Specular effect on the color by light
     * @param ks the kS value from material
     * @param l the vector from the light position to the point on geometry
     * @param n the normal of the geometry in intersection point
     * @param v the vector from the camera
     * @param nShininess the value of Shininess
     * @param lightIntensity the power of light came from the light
     * @return the final color on Specular
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
        double angle = Util.alignZero(-1 * v.dotProduct(r));
        return angle > 0 ? lightIntensity.scale(ks * Math.pow(angle, nShininess)) : Color.BLACK;

    }

}
