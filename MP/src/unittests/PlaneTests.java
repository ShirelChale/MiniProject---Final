/**
 *
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
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
		double result = n.get_head().getX().get() * point.getX().get() + 
				n.get_head().getY().get() * point.getY().get() + 
				n.get_head().getZ().get() * point.getZ().get();

		if(result != 0)
			fail("ERROR: The point isn't on Plane");

		// TC02: Wrong normal.

		Vector v = new Vector(25,0,0);
		v.normalize();
		if (!p.getNormal().equals(v))
			fail("ERROR: Wrong normal");
	}

	/**
	 * Test method for {@link geometries.p#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {

		// Creating a plane:
		Plane plane = new Plane(new Point3D(1,0,0), new Point3D(5,0,0), new Point3D(1,5,0));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray intersects the plane (1 points)
		Point3D p1 = new Point3D(1,1,0);

		List<GeoPoint> listTC1 = plane.findIntersections(new Ray(new Point3D(1, 1, -1), new Vector(0, 0, 1)));
		
		List<Point3D> resultTC1 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC1)
			resultTC1.add(p.point);
			
		assertEquals("Wrong number of points", 1, resultTC1.size());
		assertEquals("Ray intersects the plane", List.of(p1), resultTC1);

		// TC02: Ray does not intersect the plane (0 points)
		List<GeoPoint> resultTC2 = plane.findIntersections(new Ray(new Point3D(1, 1, -1), new Vector(0, 0, -1)));

		assertEquals("Wrong number of points", null, resultTC2);



		// =============== Boundary Values Tests ==================


		// **** Group: Ray is parallel to the plane


		// TC11: Ray included in the plane (0 points)
		List<GeoPoint> resultTC11 = plane.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0)));

		assertEquals("Wrong number of points", null, resultTC11);

		
		// TC12: Ray not included in the plane (0 points)
		List<GeoPoint> resultTC12 = plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 0, 0)));

		assertEquals("Wrong number of points", null, resultTC12);


		
		// **** Group: Ray is orthogonal to the plane


		// TC13: P0 before the plane (1 points)

		List<GeoPoint> listTC13 = plane.findIntersections(new Ray(new Point3D(1, 1, -1), new Vector(0, 0, 1)));
		
		List<Point3D> resultTC13 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC13)
			resultTC13.add(p.point);
			
		assertEquals("Wrong number of points", 1, resultTC13.size());
		assertEquals("P0 before the plane", List.of(p1), resultTC13);
		

		// TC14: P0 in the plane (0 points)
		List<GeoPoint> resultTC14 = plane.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC14);

		
		// TC15: P0 after the plane (0 points)
		List<GeoPoint> resultTC15 = plane.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 0, 1)));

		assertEquals("Wrong number of points", null, resultTC15);

		

		// **** Group: Ray is neither orthogonal nor parallel to the plane

		// TC16: Ray's begins at the plane (0 points)
		List<GeoPoint> resultTC16 = plane.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(2, 5, 1)));

		assertEquals("Wrong number of points", null, resultTC16);
		

		// TC17: Ray's begins in the same point which appears as reference point in the plane (0 points)
		List<GeoPoint> resultTC17 = plane.findIntersections(new Ray(new Point3D(5, 0, 0), new Vector(2, 5, 1)));

		assertEquals("Wrong number of points", null, resultTC17);

	}
}























