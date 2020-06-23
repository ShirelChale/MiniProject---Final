package geometries;
import primitives.*;

/**
 * An abstract class for kinds of geometries. 
 * Defined by its color and its material.
 * 
 * Implements <i>Intersectable</i> class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public abstract class Geometry implements Intersectable {
	
	/*** Attributes: ***/
	protected Color _emmission;
	protected Material _material;
	
	
	
	/*** Constructors: ***/
	
	/**
	 * A <i>Geometry</i> constructor - for a color and the geometry's material.
	 * 
	 * @param m - geometry's material.
	 * @param color - color of <i>_emmission</i>.
	 */
	public Geometry(Color color, Material m)
	{
		this._emmission = new Color(color);
		this._material = new Material(m);
	}
	
	
	/**
	 * A <i>Geometry</i> constructor - for a color.
	 * @param color - color of <i>_emmission</i>.
	 */
	public Geometry(Color color)
	{
		this(color, new Material (0d, 0d, 0));
	}
	
	/**
	 * A <i>Geometry</i> default constructor - sets <i>_emmission</i> color is black.
	 */
	public Geometry()
	{
		this(Color.BLACK, new Material (0d, 0d, 0));
	}
	
	
	
	/*** Getters: ***/
	
	public Color get_emmission() {
		return _emmission;
	}
	
	public Material get_material() {
		return _material;
	}



	/*** Methods: ***/

	/**
	 * function <i>getNormal</i> - Calculate the normal vector to a point on the geometry.
	 * 
	 * @param p - a point on the cylinder.
	 */
	public abstract Vector getNormal(Point3D p);
}
