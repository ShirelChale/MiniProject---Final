package geometries;
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
	public Cylinder(double r, Ray ray, double h) {
		super(r, ray);
		this._height=h;
	}
	
	/*** A getter: ***/
	public double get_height() {
		return _height;
	}
	
	
	/*** Methods: ***/
	
	/**
	 * function <i>getNormal</i> - Calculate the normal vector to a point on the cylinder (for now, the normal will be null value).
	 * 
	 * @param p - a point on the cylinder.
	 * @return the normal vector to point on the cylinder.
	 */
	public Vector getNormal(Point3D p) {
		return null;
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
