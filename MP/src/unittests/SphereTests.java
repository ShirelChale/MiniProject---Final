/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 *  
 * Unit tests for geometries.Sphere class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class SphereTests {

    // ============ Equivalence Partitions Tests ==============

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {
		
		// TC01: A 3D point isn't on sphere.
		
		// Creating a sphere:
		Sphere s = new Sphere(3, new Point3D(0,0,0));
		
		// Creating a point to check (the point isn't on sphere):
		Point3D point = new Point3D(1,1,1);	
		
		// For convenience:
		double xPart = (point.getX() - s.get_center().getX());
		double yPart = (point.getY() - s.get_center().getY());
		double zPart = (point.getZ() - s.get_center().getZ());
		
		// Placing everything in the Sphere equation:
		double result = (xPart * xPart + yPart * yPart + zPart * zPart);
		
		if (result > s.get_radius()*s.get_radius())
			fail("The point isn't on sphere");
		
		// TC02: Wrong normal.
		
		Vector v = new Vector(3,3,3);
		v.normalize();
		if (!s.getNormal(point).equals(v))	
			fail("ERROR: Wrong normal");
	}
}













