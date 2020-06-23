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
 * Unit tests for geometries.Sphere class
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============

		// TC01: A 3D point isn't on sphere.

		// Creating a sphere:
		Sphere s = new Sphere(3, new Point3D(0,0,0));

		// Creating a point to check (the point isn't on sphere):
		Point3D point = new Point3D(1,1,1);	

		// For convenience:
		double xPart = (point.getX().get() - s.get_center().getX().get());
		double yPart = (point.getY().get() - s.get_center().getY().get());
		double zPart = (point.getZ().get() - s.get_center().getZ().get());

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

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {

		Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));


		// ============ Equivalence Partitions Tests ==============


		// TC01: Ray's line is outside the sphere (0 points)
		assertEquals("Ray's line out of sphere", null,
				sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));


		// TC02: Ray starts before and crosses the sphere (2 points)
		Point3D p1 = new Point3D(0.06515307716504659, 0.35505102572168223, 0);
		Point3D p2 = new Point3D(1.5348469228349528, 0.8449489742783177, 0);

		List<GeoPoint> listT2 = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals("Wrong number of points", 2, listT2.size());


		if(listT2.get(0).point.getX().get() > listT2.get(1).point.getX().get())
			listT2 = List.of(listT2.get(1), listT2.get(0));

		List<Point3D> resultT2 = new ArrayList<Point3D>();

		for(GeoPoint p : listT2)
			resultT2.add(p.point);
		
		assertEquals("Ray crosses sphere", List.of(p1, p2), resultT2);



		
        // TC03: Ray starts inside the sphere (1 point)
        List<GeoPoint> listT3 = sphere.findIntersections(new Ray(new Point3D(0.5, 0.5, 0), new Vector(3, 1, 0)));
        
		List<Point3D> resultT3 = new ArrayList<Point3D>();

		for(GeoPoint p : listT3)
			resultT3.add(p.point);
		
        assertEquals("Wrong number of points", 1, resultT3.size());
        assertEquals("Ray not inside the sphere", List.of(p2), resultT3);


        // TC04: Ray starts after the sphere (0 points)
        List<GeoPoint> listT4 = sphere.findIntersections(new Ray(new Point3D(-1, 5, 0), new Vector(3, 1, 0)));

        assertEquals("Wrong number of points", null, listT4);



        // =============== Boundary Values Tests ==================


        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 points)
        List<GeoPoint> listTC11 = sphere.findIntersections(new Ray(p1, new Vector(3, 1, 0)));
        
		List<Point3D> resultTC11 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC11)
			resultTC11.add(p.point);
		
        assertEquals("Wrong number of points", 1, resultTC11.size());
        assertEquals("Ray inside the sphere", List.of(p2), resultTC11);


        // TC12: Ray starts at sphere and goes outside (0 points)
        List<GeoPoint> resultTC12 = sphere.findIntersections(new Ray(p1, new Vector(-3, -1, 0)));

        assertEquals("Wrong number of points", null, resultTC12);



        // **** Group: Ray's line goes through the center

        // TC13: Ray starts before the sphere (2 points)
        List<GeoPoint> listTC13 = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 2, listTC13.size());

        Point3D p3 = new Point3D(0, 0, 0);
        Point3D p4 = new Point3D(2, 0, 0);

        if(listTC13.get(0).point.getX().get() > listTC13.get(1).point.getX().get())
        	listTC13 = List.of(listTC13.get(1), listTC13.get(0));
        
		List<Point3D> resultTC13 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC13)
			resultTC13.add(p.point);
		
        assertEquals("Ray starts before the sphere", List.of(p3, p4), resultTC13);


        // TC14: Ray starts at sphere and goes inside (1 points)
        Point3D p5 = new Point3D(1, -1, 0);
        Point3D p6 = new Point3D(1, 1, 0);

        List<GeoPoint> listTC14 = sphere.findIntersections(new Ray(p5, new Vector(0, 1, 0)));

		List<Point3D> resultTC14 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC14)
			resultTC14.add(p.point);
        
        assertEquals("Wrong number of points", 1, resultTC14.size());
        assertEquals("Ray starts at sphere and goes inside", List.of(p6), resultTC14);


        // TC15: Ray starts inside (1 points)
        List<GeoPoint> listTC15 = sphere.findIntersections(new Ray(new Point3D(1, -0.5, 0), new Vector(0, 1, 0)));

		List<Point3D> resultTC15 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC15)
			resultTC15.add(p.point);
        
        assertEquals("Wrong number of points", 1, resultTC15.size());
        assertEquals("Ray starts inside", List.of(p6), resultTC15);


        // TC16: Ray starts at the center (1 points)
        Point3D p7 = new Point3D(1.9999999999999998, 0.9999999999999998, 0);
        List<GeoPoint> listTC16 = sphere.findIntersections(new Ray(sphere.get_center(), new Vector(1, 1, 0)));

		List<Point3D> resultTC16 = new ArrayList<Point3D>();

		for(GeoPoint p : listTC16)
			resultTC16.add(p.point);
		
        assertEquals("Wrong number of points", 1, resultTC16.size());
        assertEquals("Ray starts at the center", List.of(p7), resultTC16);


        // TC17: Ray starts at sphere and goes outside (0 points)
        List<GeoPoint> resultTC17 = sphere.findIntersections(new Ray(p5, new Vector(0, -1, 0)));

        assertEquals("Wrong number of points", null, resultTC17);


        // TC18: Ray starts after sphere (0 points)
        List<GeoPoint> resultTC18 = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(-3, 0, 0)));

        assertEquals("Wrong number of points", null, resultTC18);



        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC19: Ray starts before the tangent point
        Point3D p8 = new Point3D(0, 1, 0);

        List<GeoPoint> resultTC19 = sphere.findIntersections(new Ray(p8, new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", null, resultTC19);


        // TC20: Ray starts at the tangent point
        Point3D p9 = new Point3D(1, 1, 0);

        List<GeoPoint> resultTC20 = sphere.findIntersections(new Ray(p9, new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", null, resultTC20);


        // TC21: Ray starts after the tangent point
        Point3D p10 = new Point3D(2, 1, 0);

        List<GeoPoint> resultTC21 = sphere.findIntersections(new Ray(p10, new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", null, resultTC21);



        // **** Group: Special cases

        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        Point3D p11 = new Point3D(1, 2, 0);

        List<GeoPoint> resultTC22 = sphere.findIntersections(new Ray(p11, new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", null, resultTC22);
		 


	}

}













