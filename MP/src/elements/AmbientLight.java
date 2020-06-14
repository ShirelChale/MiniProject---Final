package elements;

import primitives.*;

public class AmbientLight {

	private final Color _iA;
	
	public AmbientLight(Color color, double KA) {
		this._iA = color.scale(KA);
	}

	public Color get_intensity() {
		return _iA;
	}
}
