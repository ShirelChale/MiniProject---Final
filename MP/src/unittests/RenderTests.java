package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * 
 * Unit tests for renderer.Render class.
 * 
 * @author Shirel Chale.
 * @author Riky Francois.
 *
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a jpeg image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(Point3D.zero, new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(new Color(75, 127, 90));
		scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

		scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

		scene.addGeometries(
				new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
				new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
				new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
				new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

		ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50, java.awt.Color.YELLOW);
		render.writeToImage();
	}


	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(Point3D.zero, new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(100);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));

		scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

		scene.addGeometries(
				new Triangle(new Color(java.awt.Color.BLUE),
						new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),      // lower right
				new Triangle(
						new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),    // upper right
				new Triangle(new Color(java.awt.Color.RED),
						new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),    // lower left
				new Triangle(new Color(java.awt.Color.GREEN),
						new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100))); // upper left

		ImageWriter imageWriter = new ImageWriter("color render test", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50, java.awt.Color.WHITE);
		render.writeToImage();
	}

}


/**
 * Test rendering an image
 *//*
public class TeapotTest {
	private final Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0));
	private final Scene scene = new Scene("Test scene").setCamera(camera).setDistance(1000);
	private static final Color color = new Color(200, 0, 0);
	private static final Material mat = new Material().setKd(0.5).setKs(0.5).setShininess(60);

	/**
	 * Produce a scene with a 3D model and render it into a png image
	 *//*
	@Test
	public void teapot() {
		scene.addGeometries(...);
		scene.addLights(...);
		ImageWriter imageWriter = new ImageWriter("teapot", 200, 200, 800, 800);
		Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();
		render.renderImage(); render.printGrid(50, java.awt.Color.YELLOW);
		render.writeToImage();
	}
}*/





















