package elements;

import primitives.*;

public class SpotLight extends PointLight{
	
	/*** Attributes: ***/
	private Vector _direction;
	private double _concenteration;
	
	
	
	/*** A constructor: ***/

	/**
	 * A <i>SpotLight</i> constructor - for a color, a direction vector and 3 doubles. 
	 * 
	 * @param
	 * @param c - the 1st 3D point on the triangle.
	 * @param l - the 2nd 3D point on the triangle.
	 * @param q - the 3rd 3D point on the triangle.
	 */
	public SpotLight(Color color, Point3D p, Vector v, double c, double l, double q, double concenteration){
		super(color, p, c, l, q);
		this._direction = new Vector(v).normalize();
		this._concenteration = concenteration;
	}
	
	
	/**
	 * A <i>SpotLight</i> constructor - for a color, a direction vector and 3 doubles. 
	 * 
	 * @param
	 * @param c - the 1st 3D point on the triangle.
	 * @param l - the 2nd 3D point on the triangle.
	 * @param q - the 3rd 3D point on the triangle.
	 */
	public SpotLight(Color color, Point3D p, Vector v, double c, double l, double q){
		this(color, p, v, c, l, q, 1);
	}
	

	
	public Color getIntensity(Point3D p) {
		//double denominator = this._kC + this._kL * this.getL(p).length() + this._kQ * (this.getL(p).lengthSquared());
		//return this._intensity.scale(this._diraction.normalize().dotProduct(this.getL(p))).reduce(denominator);

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





















