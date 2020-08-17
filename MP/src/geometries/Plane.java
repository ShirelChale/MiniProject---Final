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
	protected Point3D _p;
	protected Vector _normal;


	/*** Constructors: ***/

	/**
	 * A <i>Plane</i> constructor - for a color of the plane, its material and 3 3D points. 
	 * 
	 * @param color - a plane's color.
	 * @param m - the plane's material.
	 * @param p1 - the 1st 3D point on the plane.
	 * @param p2 - the 2nd 3D point on the plane.
	 * @param p3 - the 3rd 3D point on the plane.
	 */
    public Plane(Color c, Material m, Point3D p1, Point3D p2, Point3D p3) {
        super(c, m);
        _p = p1;
        Vector v1 = new Vector(p1.subtract(p2));
        Vector v2 = new Vector(p2.subtract(p3));
        _normal = v1.crossProduct(v2).normalize();
    }
	
	
	/**
	 * A <i>Plane</i> constructor - for 3 3D points. 
	 * 
	 * @param p1 - the 1st 3D point on the plane.
	 * @param p2 - the 2nd 3D point on the plane.
	 * @param p3 - the 3rd 3D point on the plane.
	 */
	public Plane(Point3D p1, Point3D p2, Point3D p3) {
		this(Color.BLACK, new Material(0, 0, 0), p1, p2, p3);
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
		this(color, new Material(0, 0, 0), p1, p2, p3);
	}


	/**
	 * A <i>Plane</i> constructor - for a 3D point and a normal vector. 
	 * 
	 * @param p - a 3D point.
	 * @param d - a normal vector.
	 */
	public Plane(Point3D p, Vector normalToPlane) {
		this(Color.BLACK, new Material(0, 0, 0), p, normalToPlane);
	}

	/**
	 * A <i>Plane</i> constructor - for a color of the plane, its material, a 3D point and a normal vector. 
	 * 
	 * @param color - a plane's color.
	 * @param m - the plane's material.
	 * @param p - a 3D point.
	 * @param d - a normal vector.
	 */
    public Plane(Color c, Material m, Point3D a, Vector n) {
        super(c, m);
        _p = a;
        _normal = n.normalized();
    }


	/**
	 * A <i>Plane</i> constructor - for a color of the plane, a 3D point and a normal vector. 
	 * 
	 * @param color - a plane's color.
	 * @param p - a 3D point.
	 * @param d - a normal vector.
	 */
	public Plane(Color color, Point3D p, Vector normalToPlane) {
		this(color, new Material(0, 0, 0), p, normalToPlane);
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
	 * Function <i>toString</i> - Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the Point on the plane and the normal to plane.
	 */
	@Override
	public String toString() {
		return "Point on Plane: "+ _p.toString()+" Normal to Plane: "+ _normal.toString();
	}

	/**
	 * Function <i>findIntersections</i> - finds geometries intersection by sending a ray to the sphere. 
	 *
	 * @param ray - the ray that been sent to the plane.
	 * @return a list of intersection of <i>GeoPoint</i>.
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















