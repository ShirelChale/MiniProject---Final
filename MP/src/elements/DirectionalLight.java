package elements;

import primitives.*;

/**
 *  An Directional Light object.
 *  Defined by its direction.
 *  
 *  Implements <i>LightSource</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class DirectionalLight extends Light implements LightSource{

	/*** Attributes: ***/
	private Vector _diraction;
	
	
	/*** A constructor: ***/

	/**
	 * An <i>DirectionalLight</i> constructor - for a color and a Vector. 
	 * 
	 * @param i - light's color.
	 * @param v - light's directional vector.
	 */
	public DirectionalLight(Color i, Vector v)
	{
		super(i);
		this._diraction = v;
	}
	

	/*** Methods: ***/
	
	/**
	 * Function <i>getIntensity</i> - Calculate the intensity of a point on a geometry.
	 * 
	 * @param p - a point on the cylinder.
	 * @return the intensity color at p.
	 */
	public Color getIntensity(Point3D p)
	{
		return this._intensity;
	}
	
	
	/**
	 * Function <i>getL</i> - Calculate the vector from the light source to a point.
	 * 
	 * @param p - a point on a geometry.
	 * @return the vector from the light source to a point.
	 */
	public Vector getL(Point3D p) {
		return this._diraction.normalize();
	}

}
