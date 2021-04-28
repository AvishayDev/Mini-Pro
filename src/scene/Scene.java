package scene;

import geometries.*;
import primitives.*;
import elements.*;

import java.util.List;

public class Scene {

    public String name;
    public Geometries geometries;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 0);
    public List<LightSource> lights;


    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }


    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
}

