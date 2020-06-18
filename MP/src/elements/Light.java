package elements;

import primitives.Color;


/**
 *  A Light interface.
 *  
 *  Defined by an intensity color.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public abstract class Light {

	/*** Attributes: ***/
	protected Color _intensity;
	
	
	/*** A constructor: ***/

	/**
	 * An <i>Light</i> constructor - for a color. 
	 */
	public Light(Color i)
	{
		this._intensity = i;
	}

	
	/*** A getter: ***/
	public Color get_intensity() {
		return _intensity;
	}

	
}
