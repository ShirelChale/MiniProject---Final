/**
 * 
 */
package unittests;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * 
 * Unit tests for geometries.Triangle class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class Triangletests {

    // ============ Equivalence Partitions Tests ==============
	
	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {

		// TC01: A 3D point isn't on triangle.
		
		// Creating a triangle:
		Point3D A = new Point3D(0,0,0);
		Point3D B = new Point3D(0,2,0);
		Point3D C = new Point3D(2,0,0);
		
		Triangle t = new Triangle(A,B,C);
		
		// Creating a point to check (the point isn't on sphere):
		Point3D point = new Point3D(1,0,0);
		
		// First, we will check if point is even on the triangle.
		// For convenience:
		double right = (A.getX() - point.getX()) * (B.getY() - point.getY()) * (C.getZ() - point.getZ()) +
				(B.getX() - point.getX()) * (C.getY() - point.getY()) * (A.getZ() - point.getZ()) +
				(C.getX() - point.getX()) * (A.getY() - point.getY()) * (B.getZ() - point.getZ());
		
		double left = (A.getX() - point.getX()) * (C.getY() - point.getY()) * (B.getZ() - point.getZ()) +
				(B.getX() - point.getX()) * (A.getY() - point.getY()) * (C.getZ() - point.getZ()) +
				(C.getX() - point.getX()) * (B.getY() - point.getY()) * (A.getZ() - point.getZ());
		
		if (right != left) {
			//double distance = (B.getX() - A.getX()) * (C.getY() - A.getY()) - 
					//(C.getX() - A.getX()) * (B.getY() - A.getY());
			
			//if (distance == 0)
				//out.println("ERROR: Not a triangle");
			
			//double a = (point.getX() - A.getX()) * (C.getY() - A.getY()) -
					//(C.getX() - A.getX()) * (B.getY() - A.getY());
			
			//double b = (B.getX() - A.getX()) * (point.getY() - A.getY()) -
					//(point.getX() - A.getX()) * (B.getY() - A.getY());
		
		//if((0 > a/distance || a/distance > 1) && (0 > a/distance || b/distance > 1))
			//fail("The point isn't on triangle");
			
			fail("The point isn't on triangle");
		}
		
		//else fail("The point isn't on triangle");
		
		// TC02: Wrong normal.

		Vector v = new Vector(0,0,-4);
		v.normalize();
		if (!t.getNormal(A).equals(v))
			fail("ERROR: Wrong normal");
	}
}
















