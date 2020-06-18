package primitives;

/**
 *  A Material object.
 *
 * The material of a geometry.
 * Defined by 2 double numbers and a number percent of the material's Shininess.
 *
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class Material {

	/*** Attributes: ***/
	private double _KD;
	private double _KS;
	private int _nShininess;


	/*** A constructor: ***/
	
	/**
	 * A <i>Material</i> constructor - 2 doubles and an integer.
	 * 
	 * @param KD - the 1st attenuation factor.
	 * @param KS - the 2nd attenuation factor.
	 * @param nShininess - a number that represents the material's Shininess.
	 */
	public Material(double KD, double KS, int nShininess) {
		//super();
		this._KD = KD;
		this._KS = KS;
		this._nShininess = nShininess;
	}


	/*** Getters: ***/
	
	public double get_KD() {
		return _KD;
	}

	public double get_KS() {
		return _KS;
	}

	public int get_nShininess() {
		return _nShininess;
	}


}
