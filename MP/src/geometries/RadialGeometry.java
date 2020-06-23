package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * An abstract class for geometries which had radius.
 * Defined by its radius.
 * 
 * Inherits from <i>Geometry</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public abstract class RadialGeometry extends Geometry{

	/*** Attributes: ***/
	protected double _radius;


	/*** Constructors: ***/

	/**
	 * A <i>RadialGeometry</i> constructor - for a double number. 
	 * 
	 * @param color - the color of the geometry.
	 * @param m - the material of the geometry.
	 * @param r - the radius of the geometry.
	 */
	public RadialGeometry(Color color, Material m, double r) {
		super(color, m);
		_radius = r;
	}

	/**
	 * A <i>RadialGeometry</i> constructor - for a double number. 
	 * 
	 * @param r - the radius of the geometry.
	 */
	public RadialGeometry(double r) {
        this(Color.BLACK, new Material(0, 0, 0), r);
	}

	/**
	 * A <i>RadialGeometry</i> copy constructor - for a <i>RadialGeometry</i> object. 
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
	
    @Override
    public String toString() {
        return "RadialGeometry{" +
                "radius=" + _radius +
                '}';
    }
}