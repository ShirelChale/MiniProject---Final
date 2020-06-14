package primitives;

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
	//private double x;
	//private double y;
	//private double z; 
	
	//private double x1;
	//private double y1;
	//private double z1; 
	
	//private Point3D _POO=new Point3D(x, y, z);
	//private Vector _direction = new Vector(x1, y1, z1);
	
	private Point3D _POO;
	private Vector _direction;

	
	/*** Constructors: ***/
	
	/**
	 * A <i>Ray</i> constructor - for a 3D point and a vector. 
	 * Checks if the direction vector is normalized.
	 * 
	 * @param p - a 3D point for the ray's starting point.
	 * @param d - the direction of the ray.
	 */
	public Ray(Point3D p, Vector d) {
		this._POO = p;
		this._direction=d;
		this._direction.normalize();
	}
	
	/**
	 * A <i>Ray</i> copy constructor - for a ray. 
	 * 
	 * @param r - a ray.
	 */
	public Ray(Ray r) {
		this._POO = r._POO;
		this._direction = r._direction;
		this._direction.normalize();
		
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
	 * Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the Ray's starting point and the Ray's direction.
	 */
	@Override
    public String toString() {
    	return "Ray{" + "_POO" + this._POO + "_direction" + this._direction + "}";
    }
    
    public Point3D getPoint(double t) 
    {
    	return this._POO.add(this._direction.scale(t));
    }
}
    
    
    
    
    
