package elements;

import primitives.*;
import static primitives.Util.alignZero;


/**
 *  An Point Light object.
 *  Defined by its position and 3 attenuation factors.
 *  
 *  Implements <i>LightSource</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class PointLight extends Light implements LightSource{

	/*** Attributes: ***/
	protected Point3D _position;
	protected double _kC, _kL, _kQ;
	
	
	
	/*** A constructor: ***/

	/**
	 * An <i>PointLight</i> constructor - for a color, 3D point and 3 doubles. 
	 * 
	 * @param color - light's color.
	 * @param p - light's position.
	 * @param c - the 1st attenuation factor.
	 * @param l - the 2nd attenuation factor.
	 * @param q - the 3rd attenuation factor.
	 */
	public PointLight(Color i, Point3D p, double c, double l, double q)
	{
		super(i);
		this._position = p;
		this._kC = c;
		this._kL = l;
		this._kQ = q;
	}
	
    public PointLight(Color intensity, Point3D position) {
        this(intensity, position, 1d, 0d, 0d);
    }
	
	
	
	/*** Methods: ***/
	
	/**
	 * Function <i>getIntensity</i> - Calculate the intensity of a point on a geometry.
	 * 
	 * @param p - a point on a geometry.
	 * @return the intensity color at p.
	 */
	public Color getIntensity(Point3D p) {
		double denominator = this._kC + this._kL * p.distance(_position) + this._kQ * (p.distanceSquared(_position));
		return this._intensity.reduce(denominator);
	}
	
	/**
	 * Function <i>getL</i> - Calculate the vector from the light source to a point.
	 * 
	 * @param p - a point on a geometry.
	 * @return the vector from the light source to a point.
	 */
	public Vector getL(Point3D p) {
		if(p.equals(_position))
			return null;
		return new Vector(p.subtract(this._position)).normalize();

	}
	
	/**
	 * Function <i>getDistance</i> - Calculate the vector from the light source to a point.
	 * 
	 * @param point - a point on a geometry.
	 * @return ditance from the light source to a point.
	 */
	public double getDistance(Point3D point)
	{
		return alignZero(this._position.distance(point));
	}
}
