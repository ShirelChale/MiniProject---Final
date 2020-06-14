package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

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
	 */
	public List<Point3D> findIntersections(Ray ray){
		
		List<Point3D> result = new ArrayList<Point3D>();
		for(int i =0; i < listOfGeometries.size(); i++) {
			List<Point3D> ourPoints = listOfGeometries.get(i).findIntersections(ray);
			if(ourPoints != null)
				result.addAll(listOfGeometries.get(i).findIntersections(ray));
		}
		if(!result.isEmpty())
			return result;
		else
			return null;
	}
}













