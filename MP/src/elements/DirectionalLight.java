package elements;

import primitives.*;

public class DirectionalLight extends Light implements LightSource{
	


	/*** Attributes: ***/
	private Vector _diraction;
	
	
	
	public DirectionalLight(Color i, Vector v)
	{
		super(i);
		this._diraction = v;
	}
	

	/*** Getters: ***/

	public Color getIntensity(Point3D p)
	{
		return this._intensity;
	}
	
	public Vector getL(Point3D p) {
		return this._diraction.normalize();
	}

}
