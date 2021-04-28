package unittests.renderer;

import org.junit.Test;
import renderer.ImageWriter;
import primitives.*;

import static org.junit.Assert.*;

public class ImageWriterTest {

    @Test
    public void writeToImage() {




    }

    @Test
    public void imageTest(){

        int nX = 800;
        int nY = 500;
        ImageWriter imageWriter =new ImageWriter("test3",nX,nY);
        Color blue = new Color(0,0,139);
        Color orangeRed = new Color(255,69,0);

        for(int i = 0; i<nY;i++){
            for(int j = 0; j<nX;j++){
                imageWriter.writePixel(j,i,blue);
            }
        }


        for(int i = 0; i<nY;i+=50){
            for(int j = 0; j<nX;j++){
                imageWriter.writePixel(j,i,orangeRed);
            }
        }

        for(int i = 0; i<nY;i++){
            for(int j = 0; j<nX;j+=50){
                imageWriter.writePixel(j,i,orangeRed);
            }
        }

        imageWriter.writeToImage();





    }

    @Test
    public void writePixel() {
    }
}