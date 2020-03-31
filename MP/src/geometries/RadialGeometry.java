package geometries;

/**
 * An abstract class for geometries which had radius.
 * Implements <i>Geometry</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public abstract class RadialGeometry {
	
	/*** Attributes: ***/
	protected double _radius;
	
	
	/*** Constructors: ***/
	
	/**
	 * A <i>RadialGeometry</i> constructor - for a double number. 
	 * 
	 * @param r - the radius of the geometrie.
	 */
	public RadialGeometry(double r) {
		this._radius=r;
	}
	
	/**
	 * A <i>RadialGeometry</i> copy constructor - for a RadialGeometry object. 
	 * 
	 * @param g - a RadialGeometry object.
	 */
	public RadialGeometry(RadialGeometry g) {
		this._radius = g._radius;
	}
	
	
	/*** Getters: ***/
	public double get_radius() {
		return _radius;
	}
	
}