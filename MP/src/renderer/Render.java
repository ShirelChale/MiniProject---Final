package renderer;

import primitives.*;
import elements.*;
import geometries.*;
import scene.*;

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
	 * A <i>Render</i> constructor - for an imageWriter and a scene. 
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
	 * Function renderImage - creates an image of a scene.
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
				List<Point3D> intersectionPointsList = geometries.findIntersections(r);
				
				if(intersectionPointsList == null)
					_imageWriter.writePixel(j, i, Background);
				else
				{
					Point3D closestPoint = getClosestPoint(intersectionPointsList);
					_imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
				}
			}
	}

	/**
	 * Function printGrid - creates a grid over an image in a received color.
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
	 * Function writeToImage - write to an image.
	 */
	public void writeToImage()
	{
		_imageWriter.writeToImage();
	}

	
	/**
	 * Function calcColor - calculate the color to write to a pixel.
	 * 
	 * @param p - ?
	 */
	private Color calcColor(Point3D p)
	{
		return this._scene.get_ambientLight().get_intensity();
	}
	
	/**
	 * Function getClosestPoint - calculate the closest point for finding the wanted color.
	 * 
	 * @param intersectionPointsList - a list of 3D points which among we want to find the closest point.
	 */
	private Point3D getClosestPoint(List<Point3D> intersectionPointsList)
	{
		Point3D p0 = this._scene.get_camera().getPosition();
		Point3D cp = null;
		
		double minDistance = Double.MAX_VALUE;
		double currentDistance = 0;
		
		for(Point3D p :intersectionPointsList)
		{
			currentDistance = p0.distance(p);
			
			if(currentDistance < minDistance)
			{
				minDistance = currentDistance;
				cp = p;
			}
		}
		
		return cp;
	}
}


















