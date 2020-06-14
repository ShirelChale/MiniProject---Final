package scene;

import primitives.*;
import elements.*;
import geometries.*;

/**
 * A Scene Object.
 * 
 * A Vector object is a basic object in geometry with size and direction. 
 * It's defined by its end point (its starting point is the origin).
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class Scene {
	
	/*** Attributes: ***/
	private String _name;
	private Color _background;
	private AmbientLight _ambientLight;
	private Geometries _geometries;
	private Camera _camera;
	private double _distance;
	
	
	/*** Constructors: ***/

	/**
	 * Image Writer constructor accepting image name and View Plane parameters,
	 * @param nameOfScene - name of scene.
	 */
	public Scene(String nameOfScene)
	{
		this._name = nameOfScene;
		this._geometries = new Geometries();
	}


	/*** Getters & Setters: ***/	

	public String get_name() {
		return _name;
	}

	public Color get_background() {
		return _background;
	}

	public void setBackground(Color _background) {
		this._background = _background;
	}

	public AmbientLight get_ambientLight() {
		return _ambientLight;
	}

	public void setAmbientLight(AmbientLight _ambientLight) {
		this._ambientLight = _ambientLight;
	}

	public Geometries get_geometries() {
		return _geometries;
	}

	public Camera get_camera() {
		return _camera;
	}

	public void setCamera(Camera _camera) {
		this._camera = _camera;
	}


	public double get_distance() {
		return _distance;
	}

	public void setDistance(double dis) {
		this._distance = dis;
	}


	public void set_geometries(Geometries _geometries) {
		this._geometries = _geometries;
	}

	
	/*** Methods: ***/

	/**
	 * Function addGeometries - adds the received geometries to <i>_geometries</i> collection.
	 */
	public void addGeometries(Intersectable... geometries)
	{
		if(_geometries == null)
			_geometries = new Geometries(geometries);
		else
			_geometries.add(geometries);
	}
}


























