package geometries;
import static primitives.Util.alignZero;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * A Triangle Object.
 * Inherits from <i>Polygon</i>.
 * 
 * A Triangle is a flat, two-dimensional surface.
 * It's defined by a 3D point on each edge.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Triangle extends Polygon {

	/*** Constructors: ***/

	/**
	 * A <i>Triangle</i> constructor - for 3 3D points. 
	 * 
	 * @param p1 - the 1st 3D point on the triangle.
	 * @param p2 - the 2nd 3D point on the triangle.
	 * @param p3 - the 3rd 3D point on the triangle.
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		super(p1,p2,p3);
	}

	/*** Methods: ***/

	/**
	 * Adds multiple Geometries to the <i>listOfGeometries</i>. 
	 *
	 * @param geometries - a list of Geometries for the <i>listOfGeometries</i>.
	 */
	public List<Point3D> findIntersections(Ray ray){

		if(this._plane.findIntersections(ray) == null)
			return null;

		Point3D point = new Point3D(this._plane.findIntersections(ray).get(0));

		Point3D A = this._vertices.get(0);
		Point3D B = this._vertices.get(1);
		Point3D C = this._vertices.get(2);

		Vector v1 = ray.get_POO().subtract(A); 
		Vector v2 = ray.get_POO().subtract(B); 
		Vector v3 = ray.get_POO().subtract(C); 

		Vector N1 = v1.crossProduct(v2);
		Vector N2 = v2.crossProduct(v3);
		Vector N3 = v3.crossProduct(v1);

		N1.normalize();
		N2.normalize();
		N3.normalize();

		double help1 = alignZero(ray.get_diraction().dotProduct(N1));
		double help2 = alignZero(ray.get_diraction().dotProduct(N2));
		double help3 = alignZero(ray.get_diraction().dotProduct(N3));

		if ((help1 > 0 && help2 > 0 && help3 > 0) || (help1 < 0 && help2 < 0 && help3 < 0))
		{
			// The 3DPoint list result:
			List<Point3D> resultList = new ArrayList<Point3D>();

			resultList.add(point);
			return resultList;
		}

		else
			return null;
	}

}


















