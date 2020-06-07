package unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

class GeometriesTests {
	@Test
	void testGeometries() {
		
		// ============ Equivalence Partitions Tests ==============

		Sphere s = new Sphere(3d, new Point3D(1,0,0));
		Plane p = new Plane(new Point3D(1,0,0), new Point3D(5,0,0), new Point3D(1,5,0));
		Triangle t = new Triangle(new Point3D(0,1,0),new Point3D(0,5,0),new Point3D(5,1,0));
		
		// TC01: Some geometries intersected (but not all) (1 points)
		Geometries collectionTC01 = new Geometries(s,t,p);
		List<Point3D> resultTC01 = collectionTC01.findIntersections(new Ray(new Point3D(0.5,1,-1), new Vector(0,0,1)));
		
		assertEquals("All geometries intersected.", 2, resultTC01.size());

		// =============== Boundary Values Tests ==================


		//TC11: Checking intersection points for 0 Geometries. (0 point)
		Geometries collectionTC11 = new Geometries();

		List<Point3D> resultTC11 = collectionTC11.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(3, 3, 3)));

		assertEquals("ERROR: There are intersection points for 0 Geometries.", null, resultTC11);

		//TC12: Checking intersections for a ray which not intersect any geometry. (0 point)
		Geometries collectionTC12 = new Geometries(t, s, p);
		
		List<Point3D> resultTC12 = collectionTC12.findIntersections(new Ray(new Point3D(10,10,10), new Vector(0,1,0)));
		
		assertEquals("ERROR: There are intersection points but the ray intersect no geometry.", null, resultTC12);

		//TC13: Only one geometry is intersected. (1 point)
		Geometries collectionTC13 = new Geometries(s,t,p);
		List<Point3D> resultTC13 = collectionTC13.findIntersections(new Ray(new Point3D(0,0,1), new Vector(0,0,1)));
		
		assertEquals("ERROR: There are intersection points but the ray intersect no geometry.", 1, resultTC13.size());

		//TC14 All geometries intersected.
		Geometries collectionTC14 = new Geometries(s,t,p);
		List<Point3D> resultTC14 = collectionTC14.findIntersections(new Ray(new Point3D(1,2,-1), new Vector(0,0,1)));
		
		assertEquals("All geometries intersected.", 3, resultTC14.size());	
	}

}
















