package elements;

import primitives.*;


/**
 *  An Ambient Light object.
 *  
 *  Inherits from <i>Light</i>.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class AmbientLight extends Light{

	/*** A constructor: ***/

	/**
	 * An <i>AmbientLight</i> constructor - for a color and a double. 
	 */
	public AmbientLight(Color color, double KA) {
		super(color.scale(KA));
	}
}
