package primitives;

/**
 * A Point3D Object.
 * 
 * A Point3D object is a basic object in geometry. 
 * A point with 3 coordinates.
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public class Point3D {
	
	/*** Attributes: ***/
	
	public static Point3D zero = new Point3D(0,0,0);

	protected Coordinate coordinate_x;
	protected Coordinate coordinate_y;
	protected Coordinate coordinate_z;

	
	/*** Constructors: ***/
	
	/**
	 * A <i>Point3D</i> constructor - for 3 coordinates. 
	 * 
	 * @param coordinate_x - the x coordinate of the 3D point.
	 * @param coordinate_y - the y coordinate of the 3D point.
	 * @param coordinate_z - the z coordinate of the 3D point.
	 */
	public Point3D(Coordinate coordinate_x, Coordinate coordinate_y, Coordinate coordinate_z) {
		this.coordinate_x = coordinate_x;
		this.coordinate_y = coordinate_y;
		this.coordinate_z = coordinate_z;
	}
	
	/**
	 * A <i>Point3D</i> constructor - for 3 double numbers. 
	 * 
	 * @param num1 - the 1st number of the 3D point.
	 * @param num2 - the 2nd number of the 3D point.
     * @param num3 - the 3rd number of the 3D point.
     */
	public Point3D(double num1, double num2, double num3) {
		this.coordinate_x = new Coordinate(num1);
		this.coordinate_y = new Coordinate(num2);
		this.coordinate_z = new Coordinate(num3);
	}
	
	/**
	 * A <i>Point3D</i> copy constructor - for a  3D point. 
	 * 
	 * @param p - a 3D point.
	 */
	public Point3D(Point3D p) {
		this.coordinate_x = p.getX();
		this.coordinate_y = p.getY();
		this.coordinate_z = p.coordinate_z;
	}

	
	/*** Getters: ***/
	public Coordinate getX() {
		return coordinate_x;
	}

	public Coordinate getY() {
		return coordinate_y;
	}

	public Coordinate getZ() {
		return coordinate_z;
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
		Point3D other = (Point3D) obj;
		if (coordinate_x == null) {
			if (other.coordinate_x != null)
				return false;
		} else if (!coordinate_x.equals(other.coordinate_x))
			return false;
		if (coordinate_y == null) {
			if (other.coordinate_y != null)
				return false;
		} else if (!coordinate_y.equals(other.coordinate_y))
			return false;
		if (coordinate_z == null) {
			if (other.coordinate_z != null)
				return false;
		} else if (!coordinate_z.equals(other.coordinate_z))
			return false;
		return true;
	}
	
	/**
	 * Prints the object in a concise and convenient way.
	 * 
	 * @return a string of the 3D point's coordinates.
	 */
	@Override
    public String toString() {
		
    	return "{"+"X = "+
        this.coordinate_x.toString()+","+ "Y = "+
        this.coordinate_y.toString()+","+ "Z = "+
        this.coordinate_z.toString()+","+ "}";
    }

	/**
	 * Subtracts the current 3D point from the received 3D point.
	 * 
	 * @param p2 - the second 3D point from whom we want to subtract the current 3D point.
	 * @return the new vector created by the subtracted 3D points.
	 */
	public Vector subtract(Point3D p2) {
		Coordinate _x = new Coordinate((this.getX().get() - p2.getX().get()));
		Coordinate _y = new Coordinate((this.getY().get() - p2.getY().get()));
		Coordinate _z = new Coordinate((this.getZ().get() - p2.getZ().get()));
		Point3D resultHead = new Point3D(_x, _y, _z);
		return new Vector(resultHead);
	}

	/**
	 * Adds the current 3D point to the received 3D point.
	 * 
	 * @param p2 - the second 3D point to whom we want to add the current 3D point.
	 * @return the result 3D point of the added 3D points.
	 */
	public Point3D add(Vector v2) {
		Coordinate _x = new Coordinate((v2._head.getX().get() + this.getX().get()));
		Coordinate _y = new Coordinate((v2._head.getY().get() + this.getY().get()));
		Coordinate _z = new Coordinate((v2._head.getZ().get() + this.getZ().get()));
		Point3D resultHead = new Point3D(_x, _y, _z);
		return resultHead;
	}
	
	/**
	 * Calculates the current 3D point distance squared.
	 * 
	 * @return the current 3D point's distance squared.
	 */
	public double distanceSquared(Point3D p) {
		return ((this.getX().get() - p.getX().get()) * (this.getX().get() - p.getX().get()) + 
				(this.getY().get() - p.getY().get()) * (this.getY().get() - p.getY().get()) +
				(this.getZ().get() - p.getZ().get()) * (this.getZ().get() - p.getZ().get()));
	}
	
	/**
	 * Calculates the current 3D point distance (using <i>distanceSquared()</i>).
	 * 
	 * @return the current 3D point's distance.
	 */
	public double distance(Point3D p) {
		return Math.sqrt(this.distanceSquared(p));
	}
}
