package geometries;
import primitives.*;

/**
 * An interface for kinds of geometries.
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */

public interface Geometry extends Intersectable {
	public Vector getNormal(Point3D p);
}
