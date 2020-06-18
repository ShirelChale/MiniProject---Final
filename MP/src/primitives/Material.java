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


	public Material(double _KD, double _KS, int _nShininess) {
		//super();
		this._KD = _KD;
		this._KS = _KS;
		this._nShininess = _nShininess;
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
