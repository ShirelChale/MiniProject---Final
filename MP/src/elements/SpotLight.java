package elements;

import primitives.*;

/**
 *  An Spot Light object.
 *  Defined by its position, its attenuation speed and 3 attenuation factors.
 *  
 *  Inherits from <i>PointLight</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class SpotLight extends PointLight{
	
	/*** Attributes: ***/
	private Vector _direction;
	private double _concenteration;
	
	
	
	/*** A constructor: ***/

	/**
	 * A <i>SpotLight</i> constructor - for a color, a 3D point, a vector, a double and 3 doubles. 
	 * 
	 * @param color - light's color.
	 * @param p - light's position.
	 * @param v - light's directional vector.
	 * @param c - the 1st attenuation factor.
	 * @param l - the 2nd attenuation factor.
	 * @param q - the 3rd attenuation factor.
	 * @param concenteration - the attenuation speed of the light.
	 */
	public SpotLight(Color color, Point3D p, Vector v, double c, double l, double q, double concenteration){
		super(color, p, c, l, q);
		this._direction = new Vector(v).normalize();
		this._concenteration = concenteration;
	}
	
	
	/**
	 * A <i>SpotLight</i> constructor - for a color, a 3D point, a vector and 3 doubles. 
	 * 
	 * @param color - light's color.
	 * @param p - light's position.
	 * @param v - light's directional vector.
	 * @param c - the 1st attenuation factor.
	 * @param l - the 2nd attenuation factor.
	 * @param q - the 3rd attenuation factor.
	 */
	public SpotLight(Color color, Point3D p, Vector v, double c, double l, double q){
		this(color, p, v, c, l, q, 1);
	}
	

	/*** Methods: ***/
	
	/**
	 * Function <i>getIntensity</i> - Calculate the intensity of a point on a geometry.
	 * 
	 * @param p - a point on a geometry.
	 * @return the intensity color at p.
	 */
	public Color getIntensity(Point3D p) {

		double projection = this._direction.dotProduct(this.getL(p).normalize());
		
		if(Util.isZero(projection))
			return Color.BLACK;
		
		double factor = Math.max(0, projection);
		Color pointLightIntencity = super.getIntensity(p);
		
		if(this._concenteration !=1)
			factor = Math.pow(factor, this._concenteration);
			
		
		return pointLightIntencity.scale(factor);
	}
	
}





















