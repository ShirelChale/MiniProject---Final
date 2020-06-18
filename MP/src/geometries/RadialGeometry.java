package geometries;

import java.util.List;


import primitives.Color;
import primitives.*;
import static primitives.Util.*;

/**
 * An abstract class for geometries which had radius.
 * Defined by its radius.
 * 
 * Inherits from <i>Geometry</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public abstract class RadialGeometry extends Geometry{

	/*** Attributes: ***/
	protected double _radius;


	/*** Constructors: ***/

	/**
	 * A <i>RadialGeometry</i> constructor - for a double number. 
	 * 
	 * @param r - the radius of the geometry.
	 * @param color - the color of the geometry.
	 * @param m - the material of the geometry.
	 */
	public RadialGeometry(double r, Color color, Material m) {
		super(color, m);
		setRadius(r);
	}

	/**
	 * A <i>RadialGeometry</i> constructor - for a double number and a radial geometry color. 
	 * 
	 * @param r - the radius of the geometry.
	 * @param color - the color of the radial geometry.
	 */
	public RadialGeometry(double r, Color color) {
		super(color);
		setRadius(r);
	}

	/**
	 * A <i>RadialGeometry</i> constructor - for a double number. 
	 * 
	 * @param r - the radius of the geometry.
	 */
	public RadialGeometry(double r) {
		setRadius(r);
	}

	/**
	 * A <i>RadialGeometry</i> copy constructor - for a <i>RadialGeometry</i> object. 
	 * 
	 * @param g - a RadialGeometry object.
	 */
	public RadialGeometry(RadialGeometry g) {
		this._radius = g._radius;
	}

	/**
	 * A <i>Vector</i> constructor - for 3 coordinates. 
	 * 
	 * @param r - a radius.
	 * @exception IllegalArgumentException("radius "+r+" is not valid") - if r <=0.
	 */
	private void setRadius(double r)
	{
		if(isZero(r) || r<0.0)
			throw new IllegalArgumentException("radius "+r+" is not valid");
		this._radius = r;
	}

	/*** Getters: ***/
	public double get_radius() {
		return _radius;
	}

	/**
	 * Function <i>findIntersections</i> - finds geometries intersection by sending a ray to the geometry. 
	 * 
	 * @param ray - ray which sent to the radial geometry.
	 * @return null value.
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray){
		return null;
	}
}