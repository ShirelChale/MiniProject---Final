package geometries;
import primitives.*;

/**
 * A Sphere Object.
 * Inherits from <i>RadialGeometry</i>.
 * 
 * A 3D geometry which defined by its center 3D point and a radius.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Sphere extends RadialGeometry{

	/*** Attributes: ***/
	private Point3D _center;
	
	/*** Constructors: ***/
	
	 /**
	 * A <i>Sphere</i> constructor - for a double number and a 3D point. 
	 *
	 * @param r - the sphere radius.
	 * @param p - the sphere center point.
	 */
	public Sphere(double r, Point3D p) {
		super(r);
		this._center=new Point3D(p);
	}
	
	/*public Sphere(Sphere s) {
		super(s._radius);
		this._center = new Point3D(s._center);
	}*/
	
	
	/*** Getters: ***/
	public Point3D get_center() {
		return _center;
	}
	
	
	/*** Methods: ***/
	
	/**
	 * Calculate the normal vector to the center point of the sphere.
	 * 
	 * @param p - the sphere center point.
	 * @return the normal vector to the center point.
	 */
	public Vector getNormal(Point3D p) {
		Vector n = new Vector((p.subtract(_center)));
		return n.normalize();
	}
		
	/**
	 * Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the sphere's radius and the sphere's center point.
	 */
	@Override
    public String toString() {
		return "Sphere's radius: " + this._radius + "Sphere's center: " + this._center.toString();
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
