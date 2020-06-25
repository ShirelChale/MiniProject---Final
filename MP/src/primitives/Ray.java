package primitives;

import static primitives.Util.isZero;

/**
 * A Ray Object.
 * 
 * A Ray object is a basic object in geometry.
 * A set of points on the line which are on one side relative to a given point on the line which called the ray's starting point.
 * It's defined by a 3D point and a direction (unit circle).
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Ray {

	/*** Attributes: ***/
	private static final double DELTA = 0.1;

	private Point3D _POO;
	private Vector _direction;


	/*** Constructors: ***/

	/**
	 * A <i>Ray</i> constructor - for a 3D point and a vector. 
	 * 
	 * @param p - a 3D point for the ray's starting point.
	 * @param d - the direction of the ray.
	 */
	public Ray(Point3D p, Vector d) {
		this._POO =new Point3D( p);
		this._direction = new Vector(d);
		this._direction.normalize();
	}
	

	/**
	 * A <i>Ray</i> constructor - for a 3D point and 2 vectors. 
	 * 
	 * @param p - a 3D point for the ray's starting point.
	 * @param d - the direction of the ray.
	 * @param n - a normal.
	 */
	public Ray(Point3D p, Vector d, Vector n) {	
		_direction = new Vector(d).normalized();

		double nv = n.dotProduct(d);
		Vector normalDelta = n.scale((nv > 0 ? DELTA : -DELTA));
		_POO = p.add(normalDelta);
	}


	/**
	 * A <i>Ray</i> copy constructor - for a ray. 
	 * 
	 * @param r - a ray.
	 */
	public Ray(Ray r) {
		this._POO = r._POO;
		this._direction = r._direction;
		//this._direction.normalize();

	}


	/*** Getters: ***/
	public Point3D get_POO() {
		return _POO;
	}

	public Vector get_diraction() {
		return _direction;
	}


	/*** Methods: ***/

	/**
	 * Checks if the <i>obj</i> object is equal to different possibilities.
	 * 
	 * @param obj is the object which we want to compare.
	 * @return a boolean answer to the object's equality.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ray other = (Ray) obj;
		if (_POO == null) {
			if (other._POO != null)
				return false;
		} else if (!_POO.equals(other._POO))
			return false;
		if (_direction == null) {
			if (other._direction != null)
				return false;
		} else if (!_direction.equals(other._direction))
			return false;
		return true;
	}

	/**
	 * Function <i>toString</i> - Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the Ray's starting point and the Ray's direction.
	 */
	@Override
	public String toString() {
		return "Ray{" + "_POO" + this._POO + "_direction" + this._direction + "}";
	}


	/**
	 * Function <i>getPoint</i> - Creates a new 3D point using the received length.
	 * 
	 * @param t - the length of the new point we'll create.
	 * @return a new 3D Point.
	 */
	public Point3D getPoint(double t) 
	{
		if (isZero(t))
			return _POO;
		
		Vector v = _direction.scale(t);
		return new Point3D(_POO.add(v));   
	}
}





