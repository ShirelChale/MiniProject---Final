package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

class CylinderTests {

	@Test
	void testGetNormal() {
		/**
		 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
		 */

		Cylinder c = new Cylinder(1, new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 0)), 1);
		System.out.println(c.getNormal(new Point3D(1, 1, 1)));
	}
}

