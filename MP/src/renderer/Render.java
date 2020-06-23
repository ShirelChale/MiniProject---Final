package renderer;

import primitives.*;
import elements.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import scene.*;
import static primitives.Util.*;

import static primitives.Util.alignZero;

import java.lang.Math;
import java.util.List;

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

	private Scene _scene;
	private ImageWriter _imageWriter;



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
	 * Function <i>renderImage</i> - creates an image of a scene.
	 */
	public void renderImage()
	{
		Camera cam = _scene.get_camera();
		java.awt.Color Background = _scene.get_background().getColor();
		Geometries geometries = _scene.get_geometries();
		double distance = _scene.get_distance();

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
					_imageWriter.writePixel(j, i, calcColor(closestPoint, r).getColor());
				}
			}
		
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
	private Color calcColor(GeoPoint geopoint, Ray inRay) 
	{
        Color resultColor = calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
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
	private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) 
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
					double ktr = transparency(lightSource, l, n, geoPoint);
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
				result = result.add(this.calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
		}

		if(kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, pointGeo, inRay);
			GeoPoint refractedPoint = this.findCLosestIntersection(refractedRay);

			if(refractedPoint != null)
				result = result.add(this.calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
		}

		return result;
	}


	/**
	 * Function <i>transparency</i> - calculates the transparency number of the color.
	 * 
	 * @param lightSource - the light source.
	 * @param l - 
	 * @param n - the normal vector to the point on the geometry.
	 * @param geoPoint - TODO
	 * @return the transparency number of the color.
	 */
	private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
		Vector lightDirection = l.scale(-1);
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

		List<GeoPoint> intersection = _scene.get_geometries().findIntersections(lightRay);

		if(intersection == null)
			return 1d;

		double lightDistance = lightSource.getDistance(geoPoint.point);
		double ktr = 1d;

		for (GeoPoint gp: intersection)
			if(alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
			{
				ktr *= gp.geometry.get_material().get_KT();

				if(ktr < MIN_CALC_COLOR_K)
					return 0d;
			}
		return ktr;
	}


	/**
	 * Function <i>sign</i> - Checks if a value is positive.
	 * 
	 * @param val - a value.
	 * @return true if it's positive.
	 */
	private boolean sign(double val)
	{
		return (val > 0d);
	}


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




