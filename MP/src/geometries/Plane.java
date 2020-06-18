package geometries;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;

import primitives.*;

/**
 * A Plane Object.
 * Inherits from <i>Geometry</i>.
 * 
 * A Plane is a flat, two-dimensional surface.
 * It's defined by a 3D point and a normal vector to that point.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Plane extends Geometry{

	/*** Attributes: ***/
	Point3D _p;
	Vector _normal;


	/*** Constructors: ***/

	/**
	 * A <i>Plane</i> constructor - for 3 3D points. 
	 * 
	 * @param p1 - the 1st 3D point on the plane.
	 * @param p2 - the 2nd 3D point on the plane.
	 * @param p3 - the 3rd 3D point on the plane.
	 */
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		this._p = new Point3D(p1);
		// Normal calculation:
		Vector a = new Vector(p1.subtract(p2));
		Vector b = new Vector(p1.subtract(p3));
		this._normal = a.crossProduct(b);
		_normal.normalize();
	}
	


	/**
	 * A <i>Plane</i> constructor - for a color of the plane, and 3 3D points. 
	 * 
	 * @param color - a plane's color.
	 * @param p1 - the 1st 3D point on the plane.
	 * @param p2 - the 2nd 3D point on the plane.
	 * @param p3 - the 3rd 3D point on the plane.
	 */
	public Plane(Color color, Point3D p1, Point3D p2, Point3D p3) {
		this._p = new Point3D(p1);
		// Normal calculation:
		Vector a = new Vector(p1.subtract(p2));
		Vector b = new Vector(p1.subtract(p3));
		this._normal = a.crossProduct(b);
		_normal.normalize();
		this._emmission = color;
	}


	/**
	 * A <i>Plane</i> constructor - for a 3D point and a normal vector. 
	 * 
	 * @param p - a 3D point.
	 * @param d - a normal vector.
	 */
	public Plane(Point3D p, Vector normalToPlane) {
		this._p=new Point3D(p);
		this._normal= new Vector(normalToPlane);
	}


	/**
	 * A <i>Plane</i> constructor - for a color of the plane, a 3D point and a normal vector. 
	 * 
	 * @param color - a plane's color.
	 * @param p - a 3D point.
	 * @param d - a normal vector.
	 */
	public Plane(Color color, Point3D p, Vector normalToPlane) {
		this._p=new Point3D(p);
		this._normal= new Vector(normalToPlane);
		this._emmission = color;
	}



	/*** Getters: ***/
	public Point3D get_p() {
		return _p;
	}

	public Vector getNormal() {
		return _normal;
	}


	/*** Methods: ***/

	/**
	 * Calculate the normal vector to 3D point on the plane.
	 * 
	 * @param p - a 3D point on the plane.
	 * @return the normal vector to the plane.
	 */
	public Vector getNormal(Point3D p) {
		return _normal;
	}

	/**
	 * Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the Point on the plane and the normal to plane.
	 */
	@Override
	public String toString() {
		return "Point on Plane: "+ _p.toString()+" Normal to Plane: "+ _normal.toString();
	}

	/**
	 * Adds multiple Geometries to the <i>listOfGeometries</i>. 
	 *
	 * @param geometries - a list of Geometries for the <i>listOfGeometries</i>.
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray){

		double t, help, mechane;
		if(this._p!=ray.get_POO()) {
			mechane = this._normal.dotProduct(ray.get_diraction());
			if(isZero(mechane))
				return null;
			else
			{
				help = this._normal.dotProduct(this._p.subtract(ray.get_POO()))/mechane;
				t = alignZero(help);
				if(t>0) {
					// The 3DPoint list result:
					List<GeoPoint> resultList = new ArrayList<GeoPoint>();

					GeoPoint p = new GeoPoint(this, ray.getPoint(t));
					resultList.add(p);

					return resultList;
				} 
			}
		}
		return null;
	}
}















