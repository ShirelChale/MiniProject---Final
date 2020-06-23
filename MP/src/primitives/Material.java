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
	private double _KT;
	private double _KR;
	private int _nShininess;

	

	/*** A constructor: ***/
	
	/**
	 * A <i>Material</i> constructor - for 4 doubles and an integer.
	 * 
	 * @param KD - the 1st attenuation factor.
	 * @param KS - the 2nd attenuation factor.
	 * @param KR - the 1st attenuation factor.
	 * @param KT - the 2nd attenuation factor.
	 * @param nShininess - a number that represents the material's Shininess.
	 */
	public Material(double KD, double KS, int nShininess, double KT, double KR) {
		this._KD = KD;
		this._KS = KS;
		this._KR = KR;
		this._KT = KT;
		this._nShininess = nShininess;
	}
	
	
	/**
	 * A <i>Material</i> constructor - for 2 doubles and an integer.
	 * 
	 * @param KD - the 1st attenuation factor.
	 * @param KS - the 2nd attenuation factor.
	 * @param nShininess - a number that represents the material's Shininess.
	 */
	public Material(double KD, double KS, int nShininess) {
		this(KD, KS, nShininess,0, 0);
	}
	
	
	
	/**
	 * A <i>Material</i> constructor - for a <i>Material</i> object.
	 * 
	 * @param m - a <i>Material</i> object.
	 */
	public Material(Material m) {
		this._KD = m._KD;
		this._KS = m._KS;
		this._KR = m._KR;
		this._KT = m._KT;
		this._nShininess = m._nShininess;
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

	public double get_KT() {
		return _KT;
	}

	public double get_KR() {
		return _KR;
	}
	
}
