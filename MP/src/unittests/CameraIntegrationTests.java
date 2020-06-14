package unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.Camera;
import geometries.*;
import primitives.*;

/**
 *  
 * Unit tests for integration of shapes with elements.Camera class.
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
class CameraIntegrationTests {

	Camera camera1 = new Camera(Point3D.zero, new Vector(0,0,1), new Vector(0,-1,0));
	Camera camera2 = new Camera(new Point3D(0,0,-0.5), new Vector(0,0,1), new Vector(0,-1,0));
	Camera camera3 = new Camera(new Point3D(0,0,-1), new Vector(0,0,1), new Vector(0,-1,0));
	
	/**
	 * Test method for integration of sphere by 
	 * {@link elements.Camera#constructRayThroughPixel(integer, integer, integer, integer, double, double, double)}.
	 */
	@Test
	void constructRayThroughPixelWithSphere() {

		// TC1: Camera and view plane are outside sphere. Radius = 1 (2 points).
		Sphere sphere1 = new Sphere(1d, new Point3D(0, 0, 3));
		
		List<Point3D> resultTC1 = new ArrayList<Point3D>();

		int count1 = 0;
		int Nx = 3;
		int Ny = 3;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC1 = sphere1.findIntersections(camera1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC1 != null)
					count1 += resultTC1.size();
			}
		}

		assertEquals("Wrong number of points", 2, count1);

	
		// TC2: Camera is outside sphere but sphere contains the view plane. Radius = 2.5 (18 points).
		Sphere sphere2 = new Sphere(2.5d, new Point3D(0, 0, 2.5));
		
		List<Point3D> resultTC2 = new ArrayList<Point3D>();

		int count2 = 0;
		
		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC2 = sphere2.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC2 != null)
					count2 += resultTC2.size();
			}
		}
		
		assertEquals("Wrong number of points", 18, count2);


		// TC3: Camera is outside sphere, but 5/9 of view plane's pixels are inside sphere. Radius = 2 (10 points).
		Sphere sphere3 = new Sphere(2d, new Point3D(0, 0, 2));
		
		List<Point3D> resultTC3 = new ArrayList<Point3D>();

		int count3 = 0;
		
		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC3 = sphere3.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC3 != null)
					count3 += resultTC3.size();
			}
		}
		
		assertEquals("Wrong number of points", 10, count3);


		// TC4: Sphere contains the view plane and the camera. Radius = 4 (9 points).
		Sphere sphere4 = new Sphere(4d, new Point3D(0, 0, 0));
		
		List<Point3D> resultTC4 = new ArrayList<Point3D>();

		int count4 = 0;
		
		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC4 = sphere4.findIntersections(camera2.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC4 != null)
					count4 += resultTC4.size();
			}
		}
		
		assertEquals("Wrong number of points", 9, count4);


		// TC5: Sphere is behind view plane. Radius = 0.5 (0 points).
		Sphere sphere5 = new Sphere(0.5d, new Point3D(0, 0, -3));
		
		List<Point3D> resultTC5 = new ArrayList<Point3D>();

		int count5 = 0;
		
		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC5 = sphere5.findIntersections(camera1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC5 != null)
					count5 += resultTC5.size();
			}
		}
		
		assertEquals("Wrong number of points", 0, count5);
	}

	
	/**
	 * Test method for integration of plane by 
	 * {@link elements.Camera#constructRayThroughPixel(integer, integer, integer, integer, double, double, double)}.
	 */
	
	@Test
	void constructRayThroughPixelWithPlane() {

		// TC1: Plane is in front view plane and its z value is 0 (9 points).
		Plane plane1 = new Plane(new Point3D(0,0,5), new Vector(0,0,1));
		
		List<Point3D> resultTC1 = new ArrayList<Point3D>();

		int count1 = 0;
		int Nx = 3;
		int Ny = 3;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC1 = plane1.findIntersections(camera3.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC1 != null)
					count1 += resultTC1.size();
			}
		}

		assertEquals("Wrong number of points", 9, count1);


		// TC2: Plane is diagonal to view plane (9 points).
		Plane plane2 = new Plane(new Point3D(0,0,5), new Vector(0,-1,2));

		List<Point3D> resultTC2 = new ArrayList<Point3D>();

		int count2 = 0;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC2 = plane2.findIntersections(camera3.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC2 != null)
					count2 += resultTC2.size();
			}
		}

		assertEquals("Wrong number of points", 9, count2);


		// TC3: Plane is parallel to only 6 lower pixels of the view plane (6 points).
		Plane plane3 = new Plane(new Point3D(0,0,5), new Vector(0,-1,1));
		
		List<Point3D> resultTC3 = new ArrayList<Point3D>();

		int count3 = 0;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC3 = plane3.findIntersections(camera3.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC3 != null)
					count3 += resultTC3.size();
			}
		}

		assertEquals("Wrong number of points", 6, count3);
		
		
		//TC4: Beyond plane (0 points).
		Plane plane4 = new Plane(new Point3D(0, 0, -5), new Vector(0, 0, 1));
		
		List<Point3D> resultTC4 = new ArrayList<Point3D>();

		int count4 = 0;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC4 = plane4.findIntersections(camera1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC4 != null)
					count4 += resultTC4.size();
			}
		}

		assertEquals("Wrong number of points", 0, count4);
	}
	
	
	/**
	 * Test method for integration of triangle by 
	 * {@link elements.Camera#constructRayThroughPixel(integer, integer, integer, integer, double, double, double)}.
	 */
	
	@Test
	void constructRayThroughPixelWithTriangle() {

		// TC1: Only middle pixel's ray of view plane intersects the triangle (1 point).
		Triangle triangle1 = new Triangle(new Point3D(0, -0.5, 1), new Point3D(-0.5, 0.5, 1), new Point3D(0.5, 0.5, 1));
		
		List<Point3D> resultTC1 = new ArrayList<Point3D>();

		int count1 = 0;
		int Nx = 3;
		int Ny = 3;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC1 = triangle1.findIntersections(camera3.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC1 != null)
					count1 += resultTC1.size();
			}
		}

		assertEquals("Wrong number of points", 1, count1);


		// TC2: Only middle-upper and center pixels intersects the triangle (2 points).
		Triangle triangle2 = new Triangle(new Point3D(1, 1, 2), new Point3D(-1, 1, 2), new Point3D(0, -20, 2));

		List<Point3D> resultTC2 = new ArrayList<Point3D>();

		int count2 = 0;

		for(int i = 0; i < Ny; ++i)
		{
			for(int j = 0; j < Nx; ++j) {
				resultTC2 = triangle2.findIntersections(camera1.constructRayThroughPixel(Nx, Ny, j, i, 1, 3, 3));
				if(resultTC2 != null)
					count2 += resultTC2.size();
			}
		}
		
		assertEquals("Wrong number of points", 2, count2);
	}
}












