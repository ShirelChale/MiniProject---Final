package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

class TriangleTest {

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {
		
	    // ============ Equivalence Partitions Tests ==============

		// TC01: A 3D point isn't on triangle.
		
		// Creating a triangle:
		Point3D A = new Point3D(0,0,0);
		Point3D B = new Point3D(0,2,0);
		Point3D C = new Point3D(2,0,0);
		
		Triangle t = new Triangle(A,B,C);
		
		// Creating a point to check (the point isn't on t):
		Point3D point = new Point3D(1,0,0);
		
		// First, we will check if point is even on the triangle.
		// For convenience:
		double right = (A.getX() - point.getX()) * (B.getY() - point.getY()) * (C.getZ() - point.getZ()) +
				(B.getX() - point.getX()) * (C.getY() - point.getY()) * (A.getZ() - point.getZ()) +
				(C.getX() - point.getX()) * (A.getY() - point.getY()) * (B.getZ() - point.getZ());
		
		double left = (A.getX() - point.getX()) * (C.getY() - point.getY()) * (B.getZ() - point.getZ()) +
				(B.getX() - point.getX()) * (A.getY() - point.getY()) * (C.getZ() - point.getZ()) +
				(C.getX() - point.getX()) * (B.getY() - point.getY()) * (A.getZ() - point.getZ());
		
		if (right != left)
			fail("The point isn't on triangle");
		
		// TC02: Wrong normal.

		Vector v = new Vector(0,0,-4);
		v.normalize();
		if (!t.getNormal(A).equals(v))
			fail("ERROR: Wrong normal");
	}
	
	/**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
    	
		Point3D A = new Point3D(0,1,0);
		Point3D B = new Point3D(0,5,0);
		Point3D C = new Point3D(5,1,0);
		
		Triangle triangle = new Triangle(A,B,C);

		
        // ============ Equivalence Partitions Tests ==============

		
        // TC01: Point inside the triangle (1 points)
		Point3D p1 = new Point3D(1,2,0);

		List<Point3D> resultTC01 = triangle.findIntersections(new Ray(new Point3D(1, 2, -1), new Vector(0, 0, 1)));
        
		assertEquals("Wrong number of points", 1, resultTC01.size());
		assertEquals("Point inside the triangle", List.of(p1), resultTC01);
		
		
        // TC02: Point outside against edge of the triangle (0 point)
		List<Point3D> resultTC02 = triangle.findIntersections(new Ray(new Point3D(0.5, 0.5, -1), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC02);
        
        
        // TC03: Point outside against vertex of the triangle (0 points)
		List<Point3D> resultTC03 = triangle.findIntersections(new Ray(new Point3D(-0.5, 0.5, -1), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC03);

        
        // =============== Boundary Values Tests ==================

      
		// **** Group: begins "before" the plane
		  
		 
        // TC11: Point on edge (0 points)
		List<Point3D> resultTC11 = triangle.findIntersections(new Ray(new Point3D(3, 1, -1), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC11);
         
        
        // TC12: Point in vertex (0 points)
		List<Point3D> resultTC12 = triangle.findIntersections(new Ray(new Point3D(0, 1, -1), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC12);
        
        
        // TC13: Point on edge's continuation (0 points)
		List<Point3D> resultTC13 = triangle.findIntersections(new Ray(new Point3D(0, 7, -1), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC13);

    }

}