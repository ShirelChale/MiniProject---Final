package geometries;
import primitives.*;

/**
 * A Tube Object.
 * Inherits from <i>RadialGeometry</i>.
 * 
 * A 3D geometry which defined by its axis ray and a radius.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Tube extends RadialGeometry{

	/*** Attributes: ***/
	protected Ray _axisRay;
	
	/*** Constructors: ***/
	
	 /**
	 * A <i>Tube</i> constructor - for a double number and a ray. 
	 *
	 * @param r - the tube radius.
	 * @param ray - the tube axis ray.
	 */
	public Tube(double r, Ray ray) {
		super(r);
		this._axisRay=new Ray(ray);
	}
	
	/*** Getters: ***/
	public Ray get_axisRay() {
		return _axisRay;
	}
	
	
	/*** Methods: ***/
	
	/**
	 * Calculate the normal vector to a point on the tube (for now, the normal will be null value).
	 * 
	 * @param p - a point on the tube.
	 * @return the normal vector to point on the tube.
	 */
	public Vector getNormal(Point3D p) {
		return null;
	}
		
	/**
	 * Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the tube's radius and the tube's axis ray.
	 */
	@Override
    public String toString() {
		return "Tube's radius: " + this._radius + "Tube's axis ray: " + this._axisRay.toString();
	}
}
