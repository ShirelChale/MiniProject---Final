package primitives;

/**
 * A Vector Object.
 * 
 * A Vector object is a basic object in geometry with size and direction. 
 * It's defined by its end point (its starting point is the origin).
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Vector {

	/*** Attributes: ***/
	protected Point3D _head;


	/*** Constructors: ***/

	/**
	 * A <i>Vector</i> constructor - for 3 coordinates. 
	 * 
	 * @param coordinate_x - the x coordinate of the vector.
	 * @param coordinate_y - the y coordinate of the vector.
	 * @param coordinate_z - the z coordinate of the vector.
	 * @exception IllegalArgumentException("Using vector zero is illegal.") - if the vector equals to vector zero, throws that it's illegal.
	 */
	public Vector(Coordinate coordinate_x, Coordinate coordinate_y, Coordinate coordinate_z) {
		Point3D help = new Point3D(coordinate_x, coordinate_y, coordinate_z);
		if (help.equals(Point3D.zero))
			throw new IllegalArgumentException("Using vector zero is illegal.");
		this._head=help;
	}

	
	/**
	 * A <i>Vector</i> constructor - for the end point. 
	 * 
	 * @param _head - the 3D end point of the vector.
	 * @exception IllegalArgumentException("Using vector zero is illegal.") - if the vector equals to vector zero, throws that it's illegal.
	 */
	public Vector(Point3D p) {
		
		if (p.equals(Point3D.zero))
			throw new IllegalArgumentException("Using vector zero is illegal.");

		this._head = new Point3D(p);
	}

	
	/**
	 * A <i>Vector</i> constructor - for 3 double numbers. 
	 * 
	 * @param num1 - the 1st number of the vector.
	 * @param num2 - the 2nd number of the vector.
	 * @param num3 - the 3rd number of the vector.
	 * @exception IllegalArgumentException("Using vector zero is illegal.") - if the vector equals to vector zero, throws that it's illegal.
	 */
	public Vector(double num1, double num2, double num3) {
		Point3D help=new Point3D(num1, num2, num3);

		if (help.equals(Point3D.zero))
			throw new IllegalArgumentException("Using vector zero is illegal.");
		this._head=new Point3D(help);
	}


	/**
	 * A <i>Vector</i> copy constructor - for a vector. 
	 * 
	 * @param v - a vector.
	 */
	public Vector(Vector v ) {
		this._head = new Point3D(v._head);
	}


	/*** Getters: ***/
	public Point3D get_head() {
		return _head;
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
		Vector other = (Vector) obj;
		if (_head == null) {
			if (other._head != null)
				return false;
		} else if (!_head.equals(other._head))
			return false;
		return true;
	} 

	/**
	 * Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the vector's head.
	 */
	@Override
	public String toString() {
		return "Head = "+this._head.toString();
	}

	/**
	 * Subtracts the current vector from the received vector.
	 * 
	 * @param v2 - the second vector from whom we want to subtract the current vector.
	 * @return the result vector of the subtracted vectors.
	 */
	public Vector subtract(Vector v2) {
		Vector result = new Vector(_head.subtract(v2._head));
		return result;
	}

	/**
	 * Adds the current vector to the received vector.
	 * 
	 * @param v2 - the second vector to whom we want to add the current vector.
	 * @return the result vector of the added vectors.
	 */
	public Vector add(Vector v2) {
		Point3D p = new Point3D (_head.add(v2));
		Vector result = new Vector(p);
		return result;
	}

	/**
	 * A scalar multiplication between the current vector and the received vector.
	 * 
	 * @param num - the scalar number with whom we want to multiply the current vector.
	 * @return the result vector of the multiply vectors.
	 */
	public Vector scale(double num) {
		Point3D result = new Point3D(_head);
		result.coordinate_x = new Coordinate(result.getX().get() * num);
		result.coordinate_y = new Coordinate(result.getY().get() * num);
		result.coordinate_z = new Coordinate(result.getZ().get() * num);
		return new Vector(result);
	}

	/**
	 * A dot-product between the current vector and the received vector.
	 * 
	 * @param v2 - the second vector to whom we want to dot-product the current vector.
	 * @return the result double number of the two dot-product vectors.
	 */
	public double dotProduct(Vector v2) {
		double result = ((this._head.coordinate_x._coord * v2._head.coordinate_x._coord) +
				(this._head.coordinate_y._coord * v2._head.coordinate_y._coord) +
				(this._head.coordinate_z._coord * v2._head.coordinate_z._coord));
		return result;
	}

	/**
	 * A cross-product between the current vector and the received vector
	 * 
	 * @param v - the vector with whom we want to cross-product the current vector.
	 * @return the result vector of the cross-product vector.
	 */
	public Vector crossProduct(Vector v){
		Vector result= new Vector(new Coordinate((this._head.getY().get() * v._head.getZ().get()) - (this._head.getZ().get() * v._head.getY().get())),
				new Coordinate((this._head.getZ().get() * v._head.getX().get()) - (this._head.getX().get() * v._head.getZ().get())),
				new Coordinate((this._head.getX().get() * v._head.getY().get())-(this._head.getY().get() * v._head.getX().get())));
		return result;
	} 

	/**
	 * Calculates the current vector length squared.
	 * 
	 * @return the current vector's length squared.
	 */
	public double lengthSquared() {
		double lengthS = (this._head.getX().get() * this._head.getX().get() +
				this._head.getY().get() * this._head.getY().get() +
				this._head.getZ().get() * this._head.getZ().get() 
				);
		return lengthS;
	}

	/**
	 * Calculates the current vector's length (using <i>lengthSquared()</i>).
	 * 
	 * @return the current vector's length.
	 */
	public double length() {
		double length = Math.sqrt(this.lengthSquared());
		return length;
	} 

	/**
	 * Normalizes the current vector.
	 * 
	 * @return the current vector (as normalized).
	 */
	public Vector normalize() {
		double l=this.length();
		this._head.coordinate_x = new Coordinate(this._head.getX().get() /l);
		this._head.coordinate_y = new Coordinate(this._head.getY().get() /l);
		this._head.coordinate_z = new Coordinate(this._head.getZ().get() /l);
		return this;
		/*
		_head = scale(1 / length())._head;
		return this;*/
	}

	/**
	 * Creates a new normalized vector on the same direction as the current vector.
	 * 
	 * @return the new normalized vector.
	 */
	public Vector normalized() {
		Vector normalVector = new Vector(this);
		normalVector.normalize();
		return normalVector;			
	}
}




































