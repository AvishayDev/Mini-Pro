package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/***
 * Scene class is built in a Passive Data Structure (aka POD)
 * It contains the name of the scene, the 3D model object in context, the color of the background, the Ambient Light and a list of
 * all the light sources
 */
public class Scene {

    /**
     * The name of the scene.
     */
    public String name;
    /**
     * The Geometry object in context of the scene.
     */
    public Geometries geometries = new Geometries();

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
    public List<LightSource> lights = new LinkedList<>();


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
     *
     * @param geometries list of geometries to reset
     * @return The current scene after setting the object.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /***
     * This method receives a list of LightSource objects, and adds them all into the field lights.
     * @param lightSources  A list of LightSource objects.
     * @return This Scene, with the updated values.
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

    /***
     * This is a getter method for the Intensity field of the ambientLight field of this object.
     * @return The Intensity value, in a form of Color.
     */
    public Color ambientGetIntensity() {
        return ambientLight.getIntensity();
    }
}

