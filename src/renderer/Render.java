package renderer;

import elements.*;
import primitives.*;
import scene.*;

import java.util.MissingResourceException;

/***
 * This class includes the fields Scene, Camera, RayTracerBase and ImageWriter.
 * It contains setters and methods for rendering.
 */
public class Render {

    //Note
    private Scene scene = null;
    private Camera camera = null;
    private RayTracerBase rayTracer = null;
    private ImageWriter imageWriter = null;


    /***
     * This method get all the rays from the camera to each pixel, for each ray receives a color from the RayTracerBasic
     * then draws the checked pixel with the color we received.
     * @throws MissingResourceException in case the any of the fields is null.
     */
    public void renderImage(){

        if(scene == null || camera == null|| rayTracer== null|| imageWriter== null)
            throw new MissingResourceException("A","B","C");
        throw new UnsupportedOperationException();

    }

    /***
     * This method receives an interval of distance and a color to draw lines on the image, mostly used to testing purposes.
     * @param interval The amount of pixels between each line in the grid.
     * @param color The color of the grid's line.
     * @throws MissingResourceException in case the imageWriter is null.
     */
    public void printGrid(int interval, Color color){
        if(imageWriter == null)
            throw new MissingResourceException("A","B","C");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for(int i = 0; i<nY;i+=interval){
            for(int j = 0; j<nX;j++){
                imageWriter.writePixel(j,i,color);
            }
        }

        for(int i = 0; i<nY;i++){
            for(int j = 0; j<nX;j+=interval){
                imageWriter.writePixel(j,i,color);
            }
        }

    }

    /***
     * This method is being used to call the writeToImage method of the field imageWriter.
     * @throws MissingResourceException in case the imageWriter is null.
     */
    public void writeToImage(){
        if(imageWriter ==null)
            throw new MissingResourceException("A","B","C");

        imageWriter.writeToImage();
    }

    /***
     * Setter for the Scene field of this Render.
     * @return This Render.
     */
    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
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
}
