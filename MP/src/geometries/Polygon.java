package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate system.
 * Inherits from <i>Geometry</i>.
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 * 
 */
public class Polygon extends Geometry {

	/*** Attributes: ***/

	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> _vertices;

	/**
	 * Associated plane in which the polygon lays
	 */
	public Plane _plane;


	/*** A constructor: ***/

	/**
	 * A <i>Plane</i> constructor - for a list of vertices. 
	 * The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices - list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex></li>
	 *                                  </ul>
	 */
	public Polygon(Color c, Material m, Point3D... vertices) {
		super(c, m);
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		_vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		_plane = new Plane(vertices[0], vertices[1], vertices[2]);
		
		if (vertices.length == 3) return; // no need for more tests for a Triangle

		Vector n = _plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
	}

	
	/**
	 * A <i>Plane</i> constructor - for a color and a list of vertices. 
	 * The list must be ordered by edge path.
	 * 
	 * @param emmission - the polygon's color.
	 * @param vertices - list of vertices according to their order by edge path.
	 */
    public Polygon(Color c ,Point3D... vertices) {
        this(c, new Material(0, 0, 0), vertices);
    }


	/**
	 * A <i>Plane</i> constructor - for a material, a color and a list of vertices. 
	 * The list must be ordered by edge path.
	 * 
	 * @param m - the polygon's material.
	 * @param emmission - the polygon's color.
	 * @param vertices - list of vertices according to their order by edge path.
	 */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, new Material(0, 0, 0), vertices);
    }


    

	/*** A getter: ***/

	public void set_vertices(List<Point3D> _vertices) {
		this._vertices = _vertices;
	}



	/*** Methods: ***/

	/**
	 * Function <i>findIntersections</i> - finds geometries intersection by sending a ray to the geometry. 
	 *
	 * @param ray - the ray that been sent to the Geometries.
	 * @return a list of intersection of <i>GeoPoint</i>.
	 */
	@Override
	public List<GeoPoint> findIntersections(Ray ray){
		return null;
	}


	/**
	 * Function <i>getNormal</i> - Calculate the normal vector to the center point of the polygon.
	 * 
	 * @param p - a 3D point on the polygon.
	 * @return the normal vector to the polygon.
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return _plane.getNormal();
	}
}












