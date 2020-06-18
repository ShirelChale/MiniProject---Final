package renderer;

import primitives.*;
import elements.*;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import scene.*;
import static primitives.Util.*;

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
		//AmbientLight Al = _scene.get_ambientLight();
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
					_imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
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
	 * Function <i>calcColor</i> - calculate the color to write to a pixel.
	 * 
	 * @param p - a <i>GeoPoint</i> object.
	 * @return a color for the object point's pixel to write.
	 */
	private Color calcColor(GeoPoint p)
	{
		Color result = this._scene.get_ambientLight().get_intensity();
		result = result.add(p.geometry.get_emmission());
		List<LightSource> lights = _scene.get_Lights();

		Vector v = p.point.subtract(_scene.get_camera().getPosition()).normalize();
		Vector n = p.geometry.getNormal(p.point);

		Material m = p.geometry.get_material();
		int nShininess = m.get_nShininess();
		double kd = m.get_KD();
		double ks = m.get_KS();

		if(_scene.get_Lights() != null)
			for(LightSource lg :lights) {
				Vector l = lg.getL(p.point).normalize();
				double Nl = alignZero(n.dotProduct(l));
				double Nv = alignZero(n.dotProduct(v));

				if(sign(Nl) == sign(Nv))
				{
					Color Ip = lg.getIntensity(p.point);
					result = result.add(
							calcDiffusive(kd, Nl, Ip),
							calcSecular(ks, l, n, v, Nl, nShininess, Ip));
				}
			}

		return result;
	}
	
	
	/**
	 * Function <i>calcColor</i> - Checks if a value is positive.
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
	 * @param l - light source.
	 * @param n - the normal vector to the point on the geometry.
	 * @param v - the vector from the camera's view point to the point on the geometry.
	 * @param nl - the product value of a light's ray which hit the point on the geometry and the normal vector to that point. 
	 * @param nShininess - the geometry's material shininess value. 
	 * @param ip - the intensity color of the point on the geometry.
	 * @return the secular color.
	 */
	private Color calcSecular(double ks, Vector l, Vector n, Vector v, double nl, int nShininess, Color ip) {
		double p = nShininess;
		
		Vector r = l.add(n.scale(-2*nl));
		double minusVR = -alignZero(r.dotProduct(v));
		if(minusVR <= 0)
			return Color.BLACK;
		return ip.scale(ks * Math.pow(minusVR, p));
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
	 * Function <i>getClosestPoint</i> - calculate the closest point for finding the wanted color.
	 * 
	 * @param intersectionPointsList - a list of 3D points which among we want to find the closest point.
	 * @return a <i>GeoPoint</i> object of the geometry's closest point to the camera.
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionPointsList)
	{
		Point3D p0 = this._scene.get_camera().getPosition();
		GeoPoint cp = null;

		double minDistance = Double.MAX_VALUE;
		double currentDistance = 0;

		for(GeoPoint p :intersectionPointsList)
		{
			currentDistance = p0.distance(p.point);

			if(currentDistance < minDistance)
			{
				minDistance = currentDistance;
				cp = p;
			}
		}

		return cp;
	}
}


















