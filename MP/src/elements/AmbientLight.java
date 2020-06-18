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
	 * 	 
	 * @param i - light's color.
	 * @param v - a attenuation factor.
	 */
	public AmbientLight(Color color, double KA) {
		super(color.scale(KA));
	}
}
