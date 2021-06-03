package renderer;

import elements.*;
import primitives.*;

import java.util.List;
import java.util.MissingResourceException;

/***
 * This class includes the fields Scene, Camera, RayTracerBase and ImageWriter.
 * It contains setters and methods for rendering.
 */
public class Render {

    /**
     * The camera view of the scene
     */
    private Camera camera = null;

    /**
     * The ray-tracer calculator
     */
    private RayTracerBase rayTracer = null;

    /**
     * The image creator
     */
    private ImageWriter imageWriter = null;

    /**
     * This is the amount of rays, in case anti aliasing is on.
     */
    private int numOfRaysAntiAliasing = 0;

    /**
     * This is the amount of rays, in case depth of field is on.
     */
    private int numOfRaysDepthOfField = 0;

    /***
     * This method get all the rays from the camera to each pixel, for each ray receives a color from the RayTracerBasic
     * then draws the checked pixel with the color we received.
     * @throws MissingResourceException in case the any of the fields is null.
     */
    public void renderImage() {
        if (camera == null || rayTracer == null || imageWriter == null)
            throw new MissingResourceException("A", "B", "C");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        Color pixelColor;

        if (numOfRaysDepthOfField != 0) {
            List<Ray> raysTrace;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    raysTrace = camera.constructDOFRays(nX, nY, j, i, numOfRaysDepthOfField);
                    pixelColor = rayTracer.traceRays(raysTrace);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } else if (numOfRaysAntiAliasing != 0) {
            List<Ray> raysTrace;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    raysTrace = camera.constructAntiARays(nX, nY, j, i, numOfRaysAntiAliasing);
                    pixelColor = rayTracer.traceRays(raysTrace);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } else {// if(rayTracer instanceof RayTracerAdvanced){
            Ray rayTrace;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    rayTrace = camera.constructRay(nX, nY, j, i);
                    pixelColor = rayTracer.traceRay(rayTrace);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        }
    }

    /***
     * This method receives an interval of distance and a color to draw lines on the image, mostly used to testing purposes.
     * @param interval The amount of pixels between each line in the grid.
     * @param color The color of the grid's line.
     * @throws MissingResourceException in case the imageWriter is null.
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("A", "B", "C");
        if (interval < 0)
            throw new IllegalArgumentException("Please don't choose negative interval!");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i += interval) {
            for (int j = 0; j < nX; j++) {
                imageWriter.writePixel(j, i, color);
            }
        }

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j += interval) {
                imageWriter.writePixel(j, i, color);
            }
        }
    }

    /***
     * This method is being used to call the writeToImage method of the field imageWriter.
     * @throws MissingResourceException in case the imageWriter is null.
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("A", "B", "C");

        imageWriter.writeToImage();
    }

    /**
     * Setter for the Camera field of this Render.
     *
     * @param camera The updated camera.
     * @return This Render, with the updated values.
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Setter for the RayTracer field of this Render.
     *
     * @param rayTracer The updated rayTracer
     * @return This Render, with the updated values.
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Setter for the ImageWriter field of this Render.
     *
     * @param imageWriter The updated imageWriter.
     * @return This Render, with the updated values.
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if DOF is on.
     *
     * @param numOfRays The amount of rays you want to go through the focal point.
     * @return This Render, with the updated values.
     */
    public Render setNumOfRaysDOF(int numOfRays) {
        this.numOfRaysDepthOfField = numOfRays;
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if DOF is on.
     *
     * @param numOfRays The amount of rays you want to go through the focal point.
     * @return This Render, with the updated values.
     */
    public Render setNumOfRaysAA(int numOfRays) {
        this.numOfRaysAntiAliasing = numOfRays;
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if Soft Shadows is on.
     *
     * @param numOfRays The amount of rays you want to go through the focal point.
     * @return This Render, with the updated values.
     */
    public Render setNumOfRaysSS(int numOfRays) {
        if (!(this.rayTracer instanceof RayTracerAdvanced))
            throw new MissingResourceException("Please Use RayTracerAdvanced for soft shadows!", "RayTracerAdvanced", "10");
        ((RayTracerAdvanced) this.rayTracer).setNumOfRaysSoftShadows(numOfRays);
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if Glossy Surface is on.
     *
     * @param numOfRays The amount of rays you want to go through the focal point.
     * @return This Render, with the updated values.
     */
    public Render setNumOfRaysGS(int numOfRays) {
        if (!(this.rayTracer instanceof RayTracerAdvanced))
            throw new MissingResourceException("Please Use RayTracerAdvanced for Glossy Surface!", "RayTracerAdvanced", "10");
        ((RayTracerAdvanced) this.rayTracer).setNumOfRaysGlossySurface(numOfRays);
        return this;
    }

    /**
     * Setter for the numOfRays field of this Render. It's relevant only if Diffuse Glass is on.
     *
     * @param numOfRays The amount of rays you want to go through the focal point.
     * @return This Render, with the updated values.
     */
    public Render setNumOfRaysDG(int numOfRays) {
        if (!(this.rayTracer instanceof RayTracerAdvanced))
            throw new MissingResourceException("Please Use RayTracerAdvanced for Diffuse Glass!", "RayTracerAdvanced", "10");
        ((RayTracerAdvanced) this.rayTracer).setNumOfRaysDiffuseGlass(numOfRays);
        return this;
    }


}
