package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  A Geometries object. 
 *  Implements <i>Intersectable</i> class
 *  
 *  Defined by an list if geometries.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class Geometries implements Intersectable{

	/*** Attributes: ***/
	private List<Intersectable> listOfGeometries;

	
	/*** Constructors: ***/

	/**
	 * A <i>Geometries</i> default constructor - for multiple intersection. 
	 */
	public Geometries() {
		this.listOfGeometries = new ArrayList<Intersectable>();
	}

	/**
	 * A <i>Geometries</i> constructor - for multiple intersection. 
	 *
	 * @param geometries - a list of Geometries for the <i>listOfGeometries</i>.
	 */
	public Geometries(Intersectable... geometries) {
		this.listOfGeometries = new ArrayList<>();
		int n=geometries.length;
		for(int i =0; i<n; i++)
			listOfGeometries.add(geometries[i]);
	}

	

	/*** Methods: ***/

	/**
	 * Adds multiple Geometries to the <i>listOfGeometries</i>. 
	 *
	 * @param geometries - a list of Geometries for the <i>listOfGeometries</i>.
	 */
	public void add(Intersectable... geometries) {
		int n=geometries.length;
		for(int i =0; i<n; i++)
			listOfGeometries.add(geometries[i]);
	}

	/**
	 * Finds geometries intersection by sending a ray to the geometry. 
	 *
	 * @param ray - the ray that been sent to the Geometries.
	 * @return a list of intersection of <i>GeoPoint</i>.
	 */
	public List<GeoPoint> findIntersections(Ray ray){
		
		List<GeoPoint> result = new ArrayList<GeoPoint>();
		for(int i =0; i < listOfGeometries.size(); i++) {
			List<GeoPoint> ourPoints = listOfGeometries.get(i).findIntersections(ray);
			if(ourPoints != null)
				result.addAll(listOfGeometries.get(i).findIntersections(ray));
		}
		if(!result.isEmpty())
			return result;
		else
			return null;
	}

	
	/**
	 * Checks if the <i>obj</i> object is equal to different possibilities.
	 * 
	 * @param obj is the object which we want to compare.
	 * @return a boolean answer to the object's equality.
	 */
	/*
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometries other = (Geometries) obj;
		if (listOfGeometries == null) {
			if (other.listOfGeometries != null)
				return false;
		} else if (!listOfGeometries.equals(other.listOfGeometries))
			return false;
		return true;
	}
	
*/	
	
}













