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
 * Unit tests for geometries.Plane class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class PlaneTests {

    // ============ Equivalence Partitions Tests ==============
	
	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		
        // ============ Equivalence Partitions Tests ==============
		
		// TC01: A 3D point isn't on plane.
		
		// Creating a plane:
		Plane p = new Plane(new Point3D(0,0,0), new Point3D(0,5,0), new Point3D(0,0,5));
		
		// Creating a point to check (the point isn't on plane):
		Point3D point = new Point3D(0,1,2);
		
		Vector n = p.getNormal();
		
		// Now, we will check whether <i>point</i> is on the plane by placing it in the plane equation.
		// Afterwards, we'll check if the left side equals the right side.
		double result = n.get_head().getX() * point.getX() + 
				n.get_head().getY() * point.getY() + 
				n.get_head().getZ() * point.getZ();
		
		if(result != 0)
			fail("ERROR: The point isn't on Plane");
		
		// TC02: Wrong normal.
		
		Vector v = new Vector(25,0,0);
		v.normalize();
		if (!p.getNormal().equals(v))
			fail("ERROR: Wrong normal");
		
	}
}
