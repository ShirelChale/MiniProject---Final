/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * 
 * Unit tests for geometries.Tube class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class TubeTests {

    // ============ Equivalence Partitions Tests ==============

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {

		// TC01: A 3D point isn't on tube.
		
		// Creating a tube:
		Tube t = new Tube(3, new Ray(new Point3D(0,0,0), new Vector(2,1,1)));
		
		// Creating a point to check (the point isn't on tube):
		Point3D point = new Point3D(2,2,2);
		
		// Now,we'll place everything in the tube equation.
		// For convenience:
		double xPart = (point.getX() - t.get_axisRay().get_POO().getX());
		double yPart = (point.getY() - t.get_axisRay().get_POO().getY());
		
		double result = (xPart * xPart + yPart * yPart);
		
		if (result > t.get_radius() * t.get_radius())
			fail("The point isn't on tube");
		
		// TC02: Wrong normal.
		
		Vector v = new Vector(-0.5773502691896263,0.5773502691896254,0.5773502691896254);
		v.normalize();
		 assertEquals("ERROR: Wrong normal", t.getNormal(point), v);
	}

}

















