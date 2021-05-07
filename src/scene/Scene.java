package scene;

import geometries.*;
import primitives.*;
import elements.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/***
 * Scene class is built in a Passive Data Structure (aka POD)
 * It contains the name of the scene, the 3D model object in context, the color of the background, the Ambient Light and a list of
 * all the light sources
 */
public class Scene {

    /**
     *  The name of the scene.
     */
    public String name;
    /**
     * The Geometry object in context of the scene.
     */
    public Geometries geometries = new Geometries();;
    /**
     * The color of the background. Default value is black.
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light object. Default value is black.
     */
    public AmbientLight ambientLight = new AmbientLight();
    /**
     * A list of all the light sources.
     */
    public List<LightSource> lights = new LinkedList<LightSource>();

    /***
     * A simple constructor, receives name only. It also initializing the geometries object with to be empty 3D model rather than null
     * All the other values shall be initialized or set with their setter methods
     * @param name The name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Setter for the lights field of the scene. Builder design pattern.
     * @param geometries list of geometries to reset
     * @return The current scene after setting the object.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Setter for the lights field of the scene. Builder design pattern.
     * @param lightSources list of lights to reset
     * @return The current scene after setting the object.
     */
    public Scene setLights(List<LightSource> lightSources) {
        lights = lightSources;
        return this;
    }

    /***
     * Setter for the Color background field of the scene. Builder design pattern.
     * @return The current scene after setting the object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /***
     * Setter for the Ambient Light field of the scene. Builder design pattern.
     * @return The current scene after setting the object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }


    /**
     * getter for AmbientLight intensity
     * @return the intensity of AmbientLight
     */
    public Color ambientGetIntensity() {
        return ambientLight.getIntensity();
    }
}

