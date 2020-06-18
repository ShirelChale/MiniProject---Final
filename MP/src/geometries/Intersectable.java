package geometries;

import primitives.*;
import java.util.List;

/**
 *  A Intersectable geometry interface.
 *  
 *  Defined by a geometry and a 3D point.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public interface Intersectable {

	public static class GeoPoint {
		
		/*** Attributes: ***/
	    public Geometry geometry;
	    public Point3D point;
	    
	    
	    
		/*** Constructors: ***/

		/**
		 * A <i>GeoPoint</i> constructor - for a geometry and 3D point.
		 * 
		 * @param geo - a geometry.
		 * @param p - a 3D point.
		 */
		public GeoPoint(Geometry geo, Point3D p)
		{
			this.geometry = geo;
			this.point = p;
		}
		
		
		
		/*** Methods: ***/
		
		/**
		 * Checks if the <i>obj</i> object is equal to different possibilities.
		 * 
		 * @param obj is the object which we want to compare.
		 * @return a boolean answer to the object's equality.
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GeoPoint other = (GeoPoint) obj;
			if (geometry == null) {
				if (other.geometry != null)
					return false;
			} else if (!geometry.equals(other.geometry))
				return false;
			if (point == null) {
				if (other.point != null)
					return false;
			} else if (!point.equals(other.point))
				return false;
			return true;
		}
		
		
	}


	 /**
	 * Finds geometries intersection by sending a ray to the geometry. 
	 *
	 * @param ray - the ray that been sent to the Geometries.
	 */
	public List<GeoPoint> findIntersections(Ray ray);
	
	public boolean equals(Object obj);
}
