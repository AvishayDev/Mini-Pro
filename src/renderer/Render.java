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
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     */
    private class Pixel {
        private long maxRows = 0; // Ny
        private long maxCols = 0; // Nx
        private long pixels = 0; // Total number of pixels: Nx*Ny
        public volatile int row = 0; // Last processed row
        public volatile int col = -1; // Last processed column
        private long counter = 0; // Total number of pixels processed
        private int percents = 0; // Percent of pixels processed
        private long nextCounter = 0; // Next amount of processed pixels for percent progress
        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            maxRows = maxRows;maxCols = maxCols; pixels = maxRows * maxCols;
            nextCounter = _pixels / 100;
            if (Render.this.print) System.out.printf("\r %02d%%", _percents);
        }
        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {}
        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (print && percents > 0) System.out.printf("\r %02d%%", percents);
            if (percents >= 0) return true;
            if (print) System.out.printf("\r %02d%%", 100);
            return false;
        }
        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col; ++counter;
            if (col < maxCols) {
                target.row = this.row; target.col = this.col;
                if (print && counter == nextCounter) {
                    ++percents; nextCounter = pixels * (percents + 1) / 100; return percents;
                }
                return 0;
            }
            ++row;
            if (row < maxRows) {
                col = 0;
                if (print && counter == nextCounter) {
                    ++percents; nextCounter = pixels * (percents + 1) / 100; return percents;
                }
                return 0;
            }
            return -1;
        }
}
