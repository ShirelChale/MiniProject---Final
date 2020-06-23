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

	/*** Methods: ***/

	/**
	 * *Function for implementation:*
	 * 
	 * Function <i>getIntensity</i> - Calculate the intensity of a point on a geometry.
	 * 
	 * @param p - a point on a geometry.
	 * @return the intensity color at p.
	 */
	Color getIntensity(Point3D p);

	/**
	 * *Function for implementation:*
	 * 
	 * Function <i>getL</i> - Calculate the vector from the light source to a point.
	 * 
	 * @param p - a point on a geometry.
	 * @return the vector from the light source to a point.
	 */
	Vector getL(Point3D p);

	/**
	 * *Function for implementation:*
	 * 
	 * Function <i>getDistance</i> - Calculate the vector from the light source to a point.
	 * 
	 * @param point - a point on a geometry.
	 * @return distance from the light source to a point.
	 */
	double getDistance(Point3D point);
}
