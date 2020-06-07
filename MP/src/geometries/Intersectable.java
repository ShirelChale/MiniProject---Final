package geometries;

import primitives.*;
import java.util.List;

public interface Intersectable {

	 /**
	 * Finds geometries intersection by sending a ray to the geometry. 
	 *
	 * @param ray - the ray that been sent to the Geometries.
	 */
	public List<Point3D> findIntersections(Ray ray);
}
