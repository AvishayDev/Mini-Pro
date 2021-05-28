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
     * the camera view of the scene
     */
    private Camera camera = null;
    /**
     * the ray-tracer calculator
     */
    private RayTracerBase rayTracer = null;
    /**
     * the image creator
     */
    private ImageWriter imageWriter = null;

    /**
     * This boolean will decide whether do we use depthOfField or not.
     */
    private boolean depthOfField = false;
    /**
     * This is the amount of rays, in case depth of field is on.
     */
    private int numOfRays =0;

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

        if(!depthOfField)
        {
            Ray rayTrace;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    rayTrace = camera.constructRay(nX, nY, j, i);
                    pixelColor = rayTracer.traceRay(rayTrace);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        }
        else
        {
            List<Ray> raysTrace;
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    raysTrace = camera.constructDOFRays(nX, nY, j, i,numOfRays);
                    pixelColor = rayTracer.traceRays(raysTrace);
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

    /***
     * Setter for the Camera field of this Render.
     * @return This Render.
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /***
     * Setter for the RayTracer field of this Render.
     * @return This Render.
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /***
     * Setter for the ImageWriter field of this Render.
     * @return This Render.
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public void setDepthOfField(boolean depthOfField) {
        this.depthOfField = depthOfField;
    }

    public void setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
    }
}
