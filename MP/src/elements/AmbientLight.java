package elements;

import primitives.*;


/**
 *  A rendering class for creating a scene.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class AmbientLight {

	/*** Attributes: ***/
	private final Color _iA;
	
	
	/*** A constructor: ***/

	/**
	 * An <i>AmbientLight</i> constructor - for a color and a double. 
	 */
	public AmbientLight(Color color, double KA) {
		this._iA = color.scale(KA);
	}
	
	
	/*** A getter: ***/

	public Color get_intensity() {
		return _iA;
	}
}
