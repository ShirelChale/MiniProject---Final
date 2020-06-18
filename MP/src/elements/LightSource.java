package elements;
import primitives.*;
import primitives.Color;

/**
 *  A Light source object.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public interface LightSource {
	
	/*** Getters: ***/

	public Color getIntensity(Point3D p);
	public Vector getL(Point3D p);
}
