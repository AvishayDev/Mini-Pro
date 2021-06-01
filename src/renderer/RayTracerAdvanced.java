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

    //private int numOfRaysDepthOfField = 0;

    //private int numOfRaysAntiAliasing = 0;

    /**
     * Constructor that calls super constructor
     *
     * @param scene the scene for the image
     */
    public RayTracerAdvanced(Scene scene) {
        super(scene);
    }

    public RayTracerAdvanced setNumOfRaysSoftShadows(int numOfRaysSoftShadows) {
        this.numOfRaysSoftShadows = numOfRaysSoftShadows;
        return this;
    }

    /**
     * Calculates the final color of point in geometry with all the light effects
     *
     * @param intersection the geometry with the point needs to be colorized
     * @param v            the ray direction that cross the geometry
     * @return the final color after adding light effects
     *
     @Override protected Color calcLocalEffects(Intersectable.GeoPoint intersection, Vector v, double k) {
     Vector n = intersection.getNormal();
     double nv = alignZero(n.dotProduct(v));
     if (nv == 0) return Color.BLACK;
     Material material = intersection.geometry.getMaterial();
     Color color = Color.BLACK;
     for (LightSource lightSource : scene.lights) {
     //we want to calc the same calculations for a bin of rays
     Vector l = lightSource.getL(intersection.point);
     if (!(lightSource instanceof DirectionalLight)) {
     Vector orthogonalL = l.getOrthogonal().normalize();
     //create the points on the light
     List<Point3D> point3DS = BlackBoard.FindPointsCircle(((PointLight) lightSource).getPosition(), ((PointLight) lightSource).getRadius(), orthogonalL, l.crossProduct(orthogonalL).normalize(), numOfRaysSoftShadows);
     //create bin of rays from the light to the intersection
     List<Ray> rays = BlackBoard.raysFromPointToPoints(intersection.point, point3DS, true);

     for (Ray ray : rays) {
     double nl = alignZero(n.dotProduct(ray.getDir()));
     if (nl * nv > 0) { // sign(nl) == sing(nv)
     double ktr = transparency(lightSource, l, n, intersection, nv);
     if (ktr * k > MIN_CALC_COLOR_K) {
     Color lightIntensity = lightSource.getIntensity(intersection.point) //
     .scale(ktr * (calcDiffusive(material.kD, nl) //
     + calcSpecular(material.kS, l, n, v, material.nShininess)));
     color = color.add(lightIntensity);
     }
     }
     }
     color= color.reduce(rays.size());
     }else {
     double nl = alignZero(n.dotProduct(l));
     if (nl * nv > 0) { // sign(nl) == sing(nv)
     double ktr = transparency(lightSource, l, n, intersection, nv);
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

     */
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
    @Override
    protected double transparency(LightSource light, Vector l, Vector n, Intersectable.GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        // creating the center ray
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move
        if (!(light instanceof DirectionalLight)) {

            Vector orthogonalL = lightDirection.getOrthogonal().normalize();
            List<Point3D> point3DS = BlackBoard.FindPointsCircle(((PointLight) light).getPosition(), ((PointLight) light).getRadius(), orthogonalL, lightDirection.crossProduct(orthogonalL).normalize(), numOfRaysSoftShadows);
            List<Ray> lightRays = BlackBoard.raysFromPointToPoints(geoPoint.point, point3DS,false);

            boolean findKtr = false;
            double sumKtr = 0.0;
            for (int i = 0; i < lightRays.size(); i++) {
                //if (lightRays.get(i).getDir().dotProduct(n) * nv > 0) {
                    double lightDistance = point3DS.get(i).distance(geoPoint);
                    List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRays.get(i), lightDistance);
                    if (intersections == null) {
                        sumKtr += 1.0;
                        continue;
                    }

                    double ktr = 1.0;
                    for (Intersectable.GeoPoint gp : intersections) {
                        ktr *= gp.geometry.getMaterial().kT;
                        if (ktr < MIN_CALC_COLOR_K) {
                            findKtr = true;
                            break;
                        }
                    }
                    if (!findKtr) {
                        sumKtr += ktr;
                    } else
                        findKtr = false;
                //}
            }
            return sumKtr / lightRays.size();

        }

        double lightDistance = light.getDistance(geoPoint.point);
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return 1.0;

        double ktr = 1.0;
        for (Intersectable.GeoPoint gp : intersections) {
            ktr *= gp.geometry.getMaterial().kT;
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }

}