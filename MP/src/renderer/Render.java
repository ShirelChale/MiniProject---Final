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

	/*** Methods: ***/
	
	/**
	 * Function renderImage - adds the received geometries to <i>_geometries</i> collection.
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
	
	
	public Render(ImageWriter image, Scene s)
	{
		this._imageWriter = image;
		this._scene = s;
	}
	
	
	public void writeToImage()
	{
		_imageWriter.writeToImage();
	}

	
	private Color calcColor(Point3D p)
	{
		return this._scene.get_ambientLight().get_intensity();
	}
	
	
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


















