package renderer;

import primitives.*;
import elements.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import scene.*;
import static primitives.Util.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  A rendering class for creating a scene.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class Render {

	/*** Attributes: ***/
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	private int _threads = 1;
	private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean _print = false; // printing progress percentage

	private Scene _scene;
	private ImageWriter _imageWriter;

	//public static double average = 0;
	//public static double numSamples = 1;


	/**
	 * Pixel is an internal helper class whose objects are associated with a Render object that
	 * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
	 * its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each thread.
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long _maxRows = 0;
		private long _maxCols = 0;
		private long _pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long _counter = 0;
		private int _percents = 0;
		private long _nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) 
		{
			_maxRows = maxRows;
			_maxCols = maxCols;
			_pixels = maxRows * maxCols;
			_nextCounter = _pixels / 100;

			if (Render.this._print) 

				System.out.printf("\r %02d%%", _percents);
		}


		/**
		 *  Default constructor for secondary Pixel objects
		 */
		public Pixel() {}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
		 * critical section for all the threads, and main Pixel object data is the shared data of this critical
		 * section.<br/>
		 * The function provides next pixel number each call.
		 * @param target target secondary Pixel object to copy the row/column of the next pixel 
		 * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
		 * finished, any other value - the progress percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++_counter;
			if (col < _maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			++row;
			if (row < _maxRows) {
				col = 0;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * @param target target secondary Pixel object to copy the row/column of the next pixel 
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percents = nextP(target);
			if (percents > 0)
				if (Render.this._print) System.out.printf("\r %02d%%", percents);
			if (percents >= 0)
				return true;
			if (Render.this._print) System.out.printf("\r %02d%%", 100);
			return false;
		}
	}



	/*** Constructors: ***/

	/**
	 * A <i>ImageWriter</i> constructor - for an imageWriter and a scene. 
	 *
	 * @param image - an image on which we're working on.
	 * @param s - a scene which we're want to write to an image.
	 */
	public Render(ImageWriter image, Scene s)
	{
		this._imageWriter = image;
		this._scene = s;
	}



	/*** Methods: ***/


	/**
	 * Set multithreading <br>
	 * - if the parameter is 0 - number of coress less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
		if (threads != 0)
			_threads = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			if (cores <= 2)
				_threads = 1;
			else
				_threads = cores;
		}
		return this;
	}


	/**
	 * Function <i>renderImage</i> - creates an image of a scene.
	 */
	public void renderImage()
	{
		Camera cam = _scene.get_camera();
		java.awt.Color Background = _scene.get_background().getColor();
		Geometries geometries = _scene.get_geometries();
		double distance = _scene.get_distance();
		//double numSamples = 1;
		double numSamples = _scene.get_sumples();

		int Ny = _imageWriter.getNy(); // Amount of pixels by Height.
		int Nx = _imageWriter.getNx(); // Amount of pixels by Width.
		double width = _imageWriter.getWidth(); // Height
		double height = _imageWriter.getHeight(); // Width

		for(int i = 0; i < Ny; i++)
			for(int j = 0; j < Nx; j++)
			{
				Ray r = cam.constructRayThroughPixel(Nx, Ny, j, i, distance, width, height);
				List<GeoPoint> intersectionPointsList = geometries.findIntersections(r);

				if(intersectionPointsList == null)
					_imageWriter.writePixel(j, i, Background);
				else
				{
					GeoPoint closestPoint = getClosestPoint(intersectionPointsList);
					//_imageWriter.writePixel(j, i, calcColor(closestPoint, r).getColor());
					_imageWriter.writePixel(j, i, calcColor(closestPoint, r, numSamples).getColor());

				}
			}
	}


	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render setDebugPrint() {
		_print = true;
		return this;
	}


	/**
	 * Function <i>printGrid</i> - creates a grid over an image in a received color.
	 * 
	 * @param interval - number of pixels which help us to select the pixel for the grid color.
	 * @param color - the color for the grid.
	 */
	public void printGrid(int interval, java.awt.Color color)
	{
		int Nx = _imageWriter.getNx();
		int Ny = _imageWriter.getNy();

		for(int i = 0; i < Ny; i++)
			for(int j = 0; j < Nx; j++) {
				if(i % interval == 0 || j % interval == 0)
					_imageWriter.writePixel(j, i, color);
			}
	}


	/**
	 * Function <i>writeToImage</i> - writes to an image.
	 */
	public void writeToImage()
	{
		_imageWriter.writeToImage();
	}


	/**
	 * Function <i>getClosestPoint</i> - calculate the closest point for finding the wanted color.
	 * 
	 * @param intersectionPointsList - a list of 3D points which among we want to find the closest point.
	 * @return a <i>GeoPoint</i> object of the geometry's closest point to the camera.
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionPointsList)
	{
		Point3D p0 = this._scene.get_camera().getPosition();
		GeoPoint closestPoint = null;

		double minDistance = Double.MAX_VALUE;
		double currentDistance = 0;

		for(GeoPoint p :intersectionPointsList)
		{
			currentDistance = p0.distance(p.point);

			if(currentDistance < minDistance)
			{
				minDistance = currentDistance;
				closestPoint = p;
			}
		}

		return closestPoint;
	}


	/**
	 * Function <i>calcColor</i> - calculates the color to write to a pixel.
	 * 
	 * @param geoPoint - a <i>GeoPoint</i> object.
	 * @param inRay - TODO
	 * @return a color for the object point's pixel to write.
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay, double numSamples)
	//private Color calcColor(GeoPoint geopoint, Ray inRay) 
	{
		//Color resultColor = calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
		Color resultColor = calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0, numSamples);
		resultColor = resultColor.add(_scene.get_ambientLight().get_intensity());

		return resultColor;
	}


	/**
	 * Function <i>calcColor</i> - help function for calculating the color to write to a pixel.
	 * 
	 * @param geoPoint - a <i>GeoPoint</i> object.
	 * @param inRay - 
	 * @param level - recursion level.
	 * @param k - TODO
	 * @return a color for the object point's pixel to write.
	 */
	//private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) 
	private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k, double numSamples) 

	{
		if(level == 0 || level == 1 || k < MIN_CALC_COLOR_K)
			return Color.BLACK;

		Color result = geoPoint.geometry.get_emmission();
		Point3D pointGeo = geoPoint.point;

		Vector v = pointGeo.subtract(this._scene.get_camera().getPosition()).normalize();
		Vector n = geoPoint.geometry.getNormal(pointGeo);

		Material material = geoPoint.geometry.get_material();
		double nShininess = material.get_nShininess();
		double kd = material.get_KD();
		double ks = material.get_KS();
		double kr = geoPoint.geometry.get_material().get_KR();
		double kt = geoPoint.geometry.get_material().get_KT();
		double kkr = k * kr;
		double kkt = k * kt;

		List<LightSource> lightSources = this._scene.get_Lights();

		if(lightSources != null)
			for(LightSource lightSource :lightSources) {
				Vector l = lightSource.getL(pointGeo);
				double Nl = alignZero(n.dotProduct(l));
				double Nv = alignZero(n.dotProduct(v));

				if(Nl * Nv > 0) {
					//double ktr = transparency(lightSource, l, n, geoPoint);
					double ktr = transparency(lightSource, l, n, geoPoint, numSamples);
					//numSamples = 1;
					//average = 0;
					if(ktr * k > MIN_CALC_COLOR_K) {
						Color Ip = lightSource.getIntensity(pointGeo).scale(ktr);
						result = result.add(
								calcDiffusive(kd, Nl, Ip),
								calcSpecular(ks, l, n, v, Nl, nShininess, Ip));	
					}
				}
			}

		if(kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, pointGeo, inRay);
			GeoPoint reflectedPoint = this.findCLosestIntersection(reflectedRay);

			if(reflectedPoint != null)
				//result = result.add(this.calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
				result = result.add(this.calcColor(reflectedPoint, reflectedRay, level-1, kkr, numSamples).scale(kr));

		}

		if(kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, pointGeo, inRay);
			GeoPoint refractedPoint = this.findCLosestIntersection(refractedRay);

			if(refractedPoint != null)
				//result = result.add(this.calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
				result = result.add(this.calcColor(refractedPoint, refractedRay, level-1, kkt, numSamples).scale(kt));

		}

		return result;
	}


	/**
	 * Function <i>transparency</i> - Calculates the transparency number of the color.
	 * 
	 * @param lightSource - the light source.
	 * @param l - 
	 * @param n - the normal vector to the point on the geometry.
	 * @param geoPoint - TODO
	 * @return the transparency number of the color.
	 *//*
	 * Acceleration with threads:
	private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {

		List<Double> ColorList = new ArrayList<Double>();
		double numSamples = 81;
		double average = 0;
		Point3D source = geoPoint.getPoint();
		//int d = 1;

		final int nX = 9; // Amount of pixels by width for our new light area.
		final int nY = 9; // Amount of pixels by height for our new light area.
		final double height = 0.9; // Height of light area.
		final double width = 0.9;  // Width of light area.
		final double dist = 50; // The distance between a point to the light area.

		Vector lightDirection = l.scale(-1); // Flips the direction
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

		ColorList.add(calcShade(lightSource, source, lightRay));
		//average += ColorList[0];

		Vector head = lightDirection.scale(dist);

		double zVal = head.get_head().getZ().get();


		final Pixel thePixel = new Pixel(nY, nX);

		Thread[] threads = new Thread[_threads];
		for (int i = _threads - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel)) {
					double xVal = head.get_head().getX().get() + findPixelShift(nX, pixel.col, dist, width);
					double yVal = head.get_head().getY().get() + findPixelShift(nY, pixel.row, dist, height);

					Point3D pixelPoint = new Point3D(xVal, -yVal, zVal);
					Vector pixelVector = new Vector(pixelPoint.subtract(source));
					Ray pixelRay = new Ray(source, pixelVector, n);

					ColorList.add(calcShade(lightSource, source, pixelRay));
				}
			});
			if(!ColorList.isEmpty())
				for(Double num : ColorList)
					average += num;
			//d++;
		}

		// Start threads
		for (Thread thread : threads) thread.start();

		// Wait for all threads to finish
		for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
		if (_print) System.out.printf("\r100%%\n");

		average = average/numSamples;

		return average;
	}*/

	
	private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint, double numSamples) {

		//double ColorList[] = new double[1280];
		double average = 0;
		Point3D source = geoPoint.getPoint();
		//int d = 0;
		double depth = 0;
		double half = 0.5;

		Vector lightDirection = l.scale(-1); // Flips the direction
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
		double zVal = lightDirection.get_head().getZ().get();

		double temp = calcShade(lightSource, source, lightRay);
		average += temp;
		if(temp == 0) {
			depth++;
			numSamples += 4;
			trans(lightSource, lightDirection, n, source, half, zVal, depth, temp, numSamples, average);
		}		

		average = average/numSamples;

		return average;
	}

	private void trans(LightSource lightSource, Vector middleRay, Vector n, Point3D middle, 
			double half, double zVal, double depth, double temp, double numSamples, double average)
	{
		if (depth == 5)
			return;

		createNewRay(lightSource, middleRay, n, middle, half, half, zVal, depth, half, temp, numSamples, average);
		createNewRay(lightSource, middleRay, n, middle, half, -half, zVal, depth, half, temp, numSamples, average);
		createNewRay(lightSource, middleRay, n, middle, -half, half, zVal, depth, half, temp, numSamples, average);
		createNewRay(lightSource, middleRay, n, middle, -half, -half, zVal, depth, half, temp, numSamples, average);	
	}

	private void createNewRay(LightSource lightSource, Vector middleRay, Vector n, Point3D middle, 
			double numX, double numY, double zVal, double depth, double half, double temp, double numSamples, double average)
	{
		double xVal = middleRay.get_head().getX().get() + numX;
		double yVal = middleRay.get_head().getY().get() + numY;

		Vector sumpleDirection = new Vector(xVal, yVal, zVal);

		Ray sumpleLightRay = new Ray(middle, sumpleDirection, n);

		temp = calcShade(lightSource, middle, sumpleLightRay);
		average += temp;
		if(temp == 0) {
			depth++;
			numSamples += 4;
			trans(lightSource, middleRay, n, middle, half/2, zVal, depth, temp, numSamples, average);
		}
	}

/*
	// ***Mini Project 1:***
	private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint, double numSamples) {

		double ColorList[] = new double[(int) numSamples];
		double average = 0;
		Point3D source = geoPoint.getPoint();

		Vector lightDirection = l.scale(-1); // Flips the direction
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

		ColorList[0] = calcShade(lightSource, source, lightRay);
		average += ColorList[0];

		double zVal = lightDirection.get_head().getZ().get();

		for (int d = 1; d < numSamples; d++)
		{
			double randX = rand();
			double randY = rand();

			double xVal = lightDirection.get_head().getX().get() + randX;
			double yVal = lightDirection.get_head().getY().get() + randY;

			Vector randDirection = new Vector(xVal, yVal, zVal);

			Ray randLightRay = new Ray(source, randDirection, n);

			ColorList[d] = calcShade(lightSource, source, randLightRay);

			average += ColorList[d];
		}

		average = average/numSamples;

		return average;
	}*/


	private double findPixelShift(int totalPixels, int pixelNum, double distance, double wORh) {

		// View plane resolution:
		double resolution = wORh / totalPixels;

		// Pixel center calculation:
		double pixelCenter = (pixelNum - totalPixels / 2d) * resolution + resolution / 2d;

		return pixelCenter;

	}


	/**
	 * Function <i>rand</i> - Calculates a random number.
	 * 
	 * @param radius - the boundary of the circle.
	 * @return a random number - positive or negative.
	 */
	private double rand() {
		double rand = Math.random();
		double result = rand / 80;

		result *= getRandomSign();

		return result;
	}


	/**
	 * Function <i>getRandomSign</i> - Calculates a random sign.
	 * 
	 * @return positive or negative 1.
	 */
	private int getRandomSign()
	{
		Random rand = new Random();
		if(rand.nextBoolean())
			return -1;
		else
			return 1;
	}


	/**
	 * Function <i>unshaded</i> - Calculates a point's shaded value (1 = no shading, 0 = shading).
	 * 
	 * @param lightSource - the light source.
	 * @param source - the point which will be checked.
	 * @param lightRay - ray from a point on light source to the point.
	 * @return point's shaded value (1 or 0).
	 */
	private double calcShade(LightSource lightSource, Point3D source, Ray lightRay) {

		List<GeoPoint> intersection = _scene.get_geometries().findIntersections(lightRay);

		if(intersection == null)
			return 1d;

		double lightDistance = lightSource.getDistance(source);
		double ktr = 1d;

		for (GeoPoint gp: intersection)
			if(alignZero(gp.point.distance(source) - lightDistance) <= 0)
			{
				ktr *= gp.geometry.get_material().get_KT();

				if(ktr < MIN_CALC_COLOR_K)
					return 0d;
			}
		return ktr;
	}

	/*
	/**
	 * Function <i>sign</i> - Checks if a value is positive.
	 * 
	 * @param val - a value.
	 * @return true if it's positive.
	 *//*
	private boolean sign(double val)
	{
		return (val > 0d);
	}
	  */

	/**
	 * Function <i>calcSpecular</i> - calculate the secular color.
	 * 
	 * @param ks - the material's 2nd attenuation factor.
	 * @param l - the vector from the light source to a point.
	 * @param n - the normal vector to the point on the geometry.
	 * @param v - the vector from the camera's view point to the point on the geometry.
	 * @param nl - the product value of a light's ray which hit the point on the geometry and the normal vector to that point. 
	 * @param nShininess - the geometry's material shininess value. 
	 * @param ip - the intensity color of the point on the geometry.
	 * @return the secular color.
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, double nl, double nShininess, Color ip) {
		double p = nShininess;

		if(isZero(nl))
			throw new IllegalArgumentException("nl can't be zero for scaling the normal vector.");

		Vector r = l.add(n.scale(-2 * nl)).normalize();
		double VR = -alignZero(r.dotProduct(v));

		if(VR <= 0)
			return Color.BLACK;

		return ip.scale(ks * Math.pow(VR, p));
	}


	/**
	 * Function <i>calcDiffusive</i> - calculate the diffusive color.
	 * 
	 * @param ks - the material's 2nd attenuation factor.
	 * @param nl - the product value of a light's ray which hit the point on the geometry and the normal vector to that point. 
	 * @param ip - the intensity color of the point on the geometry.
	 * @return the diffusive color.
	 */
	private Color calcDiffusive(double kd, double nl, Color ip) {
		if (nl < 0)
			nl = -nl;
		return ip.scale(nl * kd);
	}


	/**
	 * Function <i>unshaded</i> - Checks if geometry isn't shaded.
	 * 
	 * @param l - the vector from the light source to a point.
	 * @param n - the normal to the geometry.
	 * @param gp - a geometry.
	 * @return true if the geometry isn't shaded.
	 *//*
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) 
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Point3D pointGeo = geopoint.point;

		Ray lightRay = new Ray(pointGeo, lightDirection, n);

		List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);

		if (intersections == null) 
			return true;

		double lightDistance = light.getDistance(pointGeo);

		for (GeoPoint gp : intersections) {
			double temp = gp.point.distance(pointGeo) - lightDistance;
			if (alignZero(temp) <= 0 && gp.geometry.get_material().get_KT() == 0)
				return false;
		}

		return true;
	}*/


	/**
	 * Function <i>constructReflectedRay</i> - Builds a reflection ray.
	 * 
	 * @param n - the normal to the geometry.
	 * @param point - a point on the geometry.
	 * @param inRay - a ray to the geometry
	 * @return a reflection ray.
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay)
	{
		Vector v = inRay.get_diraction();
		double vn = v.dotProduct(n);

		if(vn == 0) 
			return null;

		Vector r = v.subtract(n.scale(2 * vn));
		return new Ray(point, r, n);
	}


	/**
	 * Function <i>constructRefractedRay</i> - Builds a refraction ray.
	 * 
	 * @param n - the normal to the geometry.
	 * @param point - a point on the geometry.
	 * @param inRay - a ray to the geometry. 
	 * @return a refraction ray.
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay)
	{
		return new Ray(point, inRay.get_diraction(), n);
	}


	/**
	 * Function <i>findCLosestIntersection</i> - calculate the closest point for finding the wanted color.
	 * 
	 * @param ray - the ray which intersects the scene.
	 * @return a <i>GeoPoint</i> object of the geometry's closest point to the camera.
	 */
	private GeoPoint findCLosestIntersection(Ray ray)
	{
		if (ray == null) 
			return null;

		Point3D ray_p0 = ray.get_POO();
		GeoPoint closestPoint = null;
		double closestDistance = Double.MAX_VALUE;

		List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray);

		if(intersections == null)
			return null;

		for(GeoPoint p : intersections)
		{
			double currentDistance = ray_p0.distance(p.point);

			if(currentDistance < closestDistance)
			{
				closestDistance = currentDistance;
				closestPoint = p;
			}
		}

		return closestPoint;
	}
}




