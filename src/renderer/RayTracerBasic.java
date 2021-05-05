package renderer;

import static geometries.Intersectable.GeoPoint;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * this class extends RayTracerBase abstract class
 * and have more functions for Specific calculations
 */
public class RayTracerBasic extends RayTracerBase {


    /**
     * constructor that calls super constructor
     *
     * @param scene the scene for the image
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /***
     *
     * implements the function 'traceRay', calc the color of
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


    // note
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientGetIntensity()
                .add(intersection.geometry.getEmission())
                .add(calcLocalEffects(intersection, ray));

    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal();
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getkD(), ks = material.getkS();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }


    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double dotProCalc = l.dotProduct(n);
        dotProCalc = dotProCalc < 0 ? dotProCalc * -1 : dotProCalc;

        return lightIntensity.scale(kd).scale(dotProCalc);
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(-2*l.dotProduct(n)));
        return lightIntensity.scale(ks).scale(Math.pow(Util.max(0,v.dotProduct(r)*-1),nShininess));

    }

}
