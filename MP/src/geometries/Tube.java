package geometries;
import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * A Tube Object.
 * Inherits from <i>RadialGeometry</i>.
 * 
 * A 3D geometry which defined by its axis ray and a radius.
 * It's a infinite geometry.
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
	public Tube(double r, Ray ray){
		this(Color.BLACK, new Material(0, 0, 0), r, ray);
	}


	/**
	 * A <i>Tube</i> constructor - for a color, a material, a double number and a Ray.
	 * 
	 * @param c - the tube's color.
	 * @param m - the tube's material.
	 * @param r - the tube radius.
	 * @param ray - the tube axis ray.
	 */
	public Tube(Color c, Material m, double r, Ray ray){
		super(c, m, r);
		_axisRay = ray;
	}



	/**
	 * A <i>Tube</i> constructor - for a color, a double number and a Ray.
	 * 
	 * and puts a default material
	 * @param c - the tube's color.
	 * @param m - the tube's material.
	 * @param r - the tube radius.
	 * @param ray - the tube axis ray.
	 */
	public Tube(Color c ,double r, Ray ray){
		this(c, new Material(0, 0, 0), r, ray);
	}




	/*** Getters: ***/
	public Ray get_axisRay() {
		return _axisRay;
	}


	/*** Methods: ***/

	/**
	 * function <i>getNormal</i> - Calculate the normal vector to a point on the tube.
	 * 
	 * @param p - a point on the tube.
	 * @return the normal vector to point on the tube.
	 */
	public Vector getNormal(Point3D p) 
	{
		// To find tube normal, we need first to find its center (The formula is in Moodle):
		Point3D center;
		double temp;
		temp = _axisRay.get_diraction().dotProduct(p.subtract(_axisRay.get_POO()));
		center = _axisRay.get_POO().add(_axisRay.get_diraction().scale(temp));

		// Now, we will use the same method we used for finding sphere normal:
		Vector n = new Vector((p.subtract(center)));

		return n.normalize();

	}

	/**
	 * function <i>toString</i> - Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the tube's radius and the tube's axis ray.
	 */
	@Override
	public String toString() {
		return "Tube's radius: " + this._radius + "Tube's axis ray: " + this._axisRay.toString();
	}


	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		return null;
	}
}
