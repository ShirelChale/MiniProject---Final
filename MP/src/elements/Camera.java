package elements;

import static primitives.Util.*;

import primitives.*;

/**
 * A Camera Object.
 * 
 * An optical instrument used to record images.
 * Defined by the camera's view point (its position) and 3 vectors for determining its view plane, 
 * through which rays are sent to create the image.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class Camera{

	/*** Attributes: ***/
	private Point3D _p0;
	private Vector _vUp, _vRight, _vTo;
	
	
	/*** Constructors: ***/

	/**
	 * A <i>Camera</i> constructor - for a 3D point and 2 vectors. 
	 *
	 * @param p - the camera camera's view point.
	 * @param up - a vector oriented upwards.
	 * @param to - a vector oriented towards the view plane.
	 * 
	 * @exception IllegalArgumentException("Vectors aren't orthogonal.") - if the cross product of _vUp and _vTo isn't zero,
	 * that is, they are not orthogonal, throws exception.
	 */
	public Camera(Point3D p, Vector to, Vector up){
		this._p0 = p;
		double Check = up.dotProduct(to);
		
		if (Check == 0) {
			this._vUp = up;
			this._vUp.normalize();
			this._vTo = to;
			this._vTo.normalize();
			
			this._vRight = this._vTo.crossProduct(_vUp);
			this._vRight.normalize();
		}
		
		else
			throw new IllegalArgumentException("Vectors aren't orthogonals.");
	}
	
	
	/*** Getters: ***/
	public Point3D getPosition() {
		return _p0;
	}

	public Vector get_vUp() {
		return _vUp;
	}

	public Vector get_vRight() {
		return _vRight;
	}

	public Vector get_vTo() {
		return _vTo;
	}

	
	/*** Methods: ***/

	/**
	 * Calculate a ray for a view plane pixel.
	 * 
	 * @param nX - number of pixels in a view plane's row.
	 * @param nY - number of pixels in a view plane's column.
	 * @param j - Index for pixel representation in row j.
	 * @param i - Index for pixel representation in column i.
	 * @param screenDistance - the distance of the camera's position from the view plane.
	 * @param screenWidth - the view plane width.
	 * @param screenHeight - the view plane height.
	 * 
	 * @return the ray for the (j,i) pixel.
	 */
	public Ray constructRayThroughPixel (int nX, int nY,
            int j, int i, double screenDistance,
            double screenWidth, double screenHeight)
	{
		
		if(isZero(screenDistance))
			throw new IllegalArgumentException("Distance can't be 0.");
		
		// View plane's center point:
		Point3D pC = _p0.add(_vTo.scale(screenDistance));
		
		// View plane resolution:
		double rX = screenWidth / nX;
		double rY = screenHeight / nY;
		
		// Pixel center calculation:
		double xJ = (j - nX / 2d) * rX + rX / 2d;
		double yI = (i - nY / 2d) * rY + rY / 2d;
		
		// Declaration of where in the pixel the ray will go through:
		Point3D pIJ = pC;
		
		// Finding the center of pIJ.
		if(!isZero(xJ))
			pIJ = pIJ.add(_vRight.scale(xJ));
		
		if(!isZero(yI))
			pIJ = pIJ.add(_vUp.scale(-yI));
		
		// Ray's direction vector:
		Vector Vij = pIJ.subtract(_p0);
			
		// Return the result's ray:
		return new Ray(_p0, Vij);
	}

}


















