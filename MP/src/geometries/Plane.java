package geometries;
import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;

import primitives.*;

/**
 * A Plane Object.
 * Implements <i>Geometry</i>.
 * 
 * A Plane is a flat, two-dimensional surface.
 * It's defined by a 3D point and a normal vector to that point.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Plane implements Geometry{
	
	/*** Attributes: ***/
	protected Point3D _p;
	protected Vector _normal;
	
	
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
	 * A <i>Plane</i> constructor - for a 3D point and a normal vector. 
	 * 
	 * @param p - a 3D point.
	 * @param d - a normal vector.
	 */
	public Plane(Point3D p, Vector normalToPlane) {
		this._p=new Point3D(p);
		this._normal= new Vector(normalToPlane);
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
	public List<Point3D> findIntersections(Ray ray){
		
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
					List<Point3D> resultList = new ArrayList<Point3D>();

					Point3D p = ray.getPoint(t);
					resultList.add(p);

					return resultList;
				} 
			}
		}
		return null;
	}
}















