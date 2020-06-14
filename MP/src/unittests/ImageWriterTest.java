package unittests;

import static org.junit.jupiter.api.Assertions.*;

import primitives.*;
import renderer.*;
import org.junit.jupiter.api.Test;

class ImageWriterTest {

	/**
	 * Test method for{@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	void ImageWriterTests() {
		java.awt.Color Turquoise = new java.awt.Color(118, 255, 209);
		java.awt.Color Pink = new java.awt.Color(239, 124, 176);
		
		ImageWriter image = new ImageWriter("SummerTime", 1600, 800, 800, 500);
		
		int Nx = image.getNx();
		int Ny = image.getNy();
		
		for(int i = 0; i < Ny; i++)
			for(int j = 0; j < Nx; j++) {
				if(i % 50 == 0 || j % 50 == 0)
					image.writePixel(j, i, Pink);
				else
					image.writePixel(j, i, Turquoise);
			}
		
		image.writeToImage();
	}
}
