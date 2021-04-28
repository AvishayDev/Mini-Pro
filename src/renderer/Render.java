package renderer;

import elements.*;
import primitives.*;
import scene.*;

import java.util.MissingResourceException;

public class Render {

    private Scene scene = null;
    private Camera camera = null;
    private RayTracerBase rayTracer = null;
    private ImageWriter imageWriter = null;


    public void renderImage(){

        if(scene == null || camera == null|| rayTracer== null|| imageWriter== null)
            throw new MissingResourceException("A","B","C");
        throw new UnsupportedOperationException();

    }

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

    public void writeToImage(){
        if(imageWriter ==null)
            throw new MissingResourceException("A","B","C");

        imageWriter.writeToImage();
    }


    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
}
