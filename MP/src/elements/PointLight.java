package elements;

import primitives.Color;
import primitives.*;

public class PointLight extends Light implements LightSource{

	/*** Attributes: ***/
	protected Point3D _position;
	protected double _kC, _kL, _kQ;
	
	
	public PointLight(Color i, Point3D p, double c, double l, double q)
	{
		super(i);
		this._position = p;
		this._kC = c;
		this._kL = l;
		this._kQ = q;
	}
	
	
	/*** Getters: ***/

	public Color getIntensity(Point3D p) {
		double denominator = this._kC + this._kL * this.getL(p).length() + this._kQ * (this.getL(p).lengthSquared());
		return this._intensity.reduce(denominator);
	}
	
	public Vector getL(Point3D p) {
		if(p.equals(_position))
			return null;
		return new Vector(p.subtract(this._position)).normalize();

	}
}
