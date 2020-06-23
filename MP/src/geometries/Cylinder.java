package geometries;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.*;

/**
 * A Cylinder Object.
 * Inherits from <i>Tube</i>.
 * 
 * A 3D geometry which defined by its axis ray, a radius and its height.
 * It's a finite geometry.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Cylinder extends Tube{

	/*** Attributes: ***/
	private double _height;

	/*** Constructors: ***/

	/**
	 * A <i>Cylinder</i> constructor - for 2 double numbers and a ray. 
	 *
	 * @param r - the cylinder's radius.
	 * @param ray - the cylinder's axis ray.
	 * @param h - the cylinder's height.
	 */
	public Cylinder(double radius1, Ray ax1, double height1) {
		this(Color.BLACK, new Material(0, 0, 0), radius1 ,ax1, height1);
	}


	/**
	 * A <i>Cylinder</i> constructor -  for a color, a material, 2 double numbers and a Ray.
	 * 
	 * @param c - the cylinder's color.
	 * @param m - the cylinder's material.
	 * @param r - the cylinder's radius.
	 * @param ray - the cylinder's axis ray.
	 * @param h - the cylinder's height.
	 */
	public Cylinder(Color c, Material m, double r, Ray ray, double h) {
		super(c, m, r, ray);
		_height = h;
	}

	/**
	 * A <i>Cylinder</i> constructor -  for a color, 2 double numbers and a Ray.
	 * 
	 * @param c - the cylinder's color.
	 * @param r - the cylinder's radius.
	 * @param ray - the cylinder's axis ray.
	 * @param h - the cylinder's height.
	 */
	public Cylinder(Color c, double r, Ray ray, double h) {
		this(c, new Material(0, 0, 0), r, ray, h);
	}


	

	/*** A getter: ***/
	public double get_height() {
		return _height;
	}


	/*** Methods: ***/

	/**
	 * function <i>getNormal</i> - Calculate the normal vector to a point on the cylinder.
	 * 
	 * @param p - a point on the cylinder.
	 * @return the normal vector to point on the cylinder.
	 */
	public Vector getNormal(Point3D p) 
	{
		Point3D O = _axisRay.get_POO();
		Vector v = _axisRay.get_diraction();

		double t;
		try
		{
			t = alignZero(p.subtract(O).dotProduct(v));
		}
		catch (IllegalArgumentException e)
		{ 
			return v;
		}
		// Checks if the point is on the cylinder's base:
		if (t == 0 || isZero(_height - t)) 
			return v;

		O = O.add(v.scale(t));
		return p.subtract(O).normalize();
	}


	/**
	 * function <i>toString</i> - Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the cylinder's radius, the cylinder's axis ray and the cylinder's height.
	 */
	@Override
	public String toString() {
		return "Cylinder's radius: " + this._radius + "Cylinder's axis Ray: " + this._axisRay.toString() + "Cylinder's height: " + this._height;
	}
}
