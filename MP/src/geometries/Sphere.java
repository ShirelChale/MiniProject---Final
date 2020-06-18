package geometries;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;

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

	/**
	 * A <i>Sphere</i> constructor - for a double number and a 3D point. 
	 *
	 * @param r - the sphere radius.
	 * @param p - the sphere center point.
	 */
	public Sphere(Material m, double r, Point3D p) {
		this(r,p);
		this._material = m;
	}

	
	/**
	 * A <i>Sphere</i> constructor - for a double number and a 3D point. 
	 *
	 * @param r - the sphere radius.
	 * @param p - the sphere center point.
	 */
	public Sphere(Color color, Material m, double r, Point3D p) {
		this(m,r,p);
		this._emmission = color;
	}
	
	

	/*** A getter: ***/
	public Point3D get_center() {
		return _center;
	}


	
	/*** Methods: ***/

	/**
	 * Function <i>getNormal</i> - Calculate the normal vector to the center point of the sphere.
	 * 
	 * @param p - the sphere center point.
	 * @return the normal vector to the center point.
	 */
	public Vector getNormal(Point3D p) {
		Vector n = new Vector((p.subtract(_center)));
		return n.normalize();
	}


	/**
	 * Function <i>toString</i> - Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the sphere's radius and the sphere's center point.
	 */
	@Override
	public String toString() {
		return "Sphere's radius: " + this._radius + "Sphere's center: " + this._center.toString();
	}


	/**
	 * Function <i>findIntersections</i> - finds geometries intersection by sending a ray to the sphere. 
	 *
	 * @param ray - the ray that been sent to the sphere.
	 * @return a list of intersection of <i>GeoPoint</i>.
	 */
	public List<GeoPoint> findIntersections(Ray ray){

		Vector L;

		if(!_center.equals(ray.get_POO())) 
			L = _center.subtract(ray.get_POO());
		else
			L = new Vector(_center);

		double tm = ray.get_diraction().dotProduct(L);

		double help = L.length();
		double d = Math.sqrt(help * help - tm * tm);

		if(d>this._radius)
			return null;

		double th = Math.sqrt(this._radius * this._radius - d * d);

		double t1,t2;
		t1 = tm-th;
		t2 = tm+th;

		GeoPoint p1,p2;

		t1 = alignZero(t1);
		t2 = alignZero(t2);

		if(t1 > 0 || t2 > 0) {
			// The 3DPoint list result:
			List<GeoPoint> resultList = new ArrayList<GeoPoint>();	

			if(t1 > 0) {
				p1 = new GeoPoint(this, ray.getPoint(t1));
				resultList.add(p1);
			}

			if(t2 > 0) {
				p2 = new GeoPoint(this, ray.getPoint(t2));
				resultList.add(p2);
			}
			return resultList;
		}
		return null;
	}
}




























