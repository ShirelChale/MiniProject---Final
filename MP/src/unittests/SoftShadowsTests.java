package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class SoftShadowsTests {

	@Test
	public void OurSceneTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(new Color(218,28,76));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

		scene.addGeometries( 
				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0.3, 0.5),
						new Point3D(30, -20, 60),  //t11
						new Point3D(35, 10, -60),   //t12
						new Point3D(-16, 50, 0)), //t13
				new Triangle(new Color(java.awt.Color.YELLOW),new Material(0, 0, 0, 0.3, 0.5),
						new Point3D(-55, 10, 60),//t21
						new Point3D(-45, -10, -20),   //t22
						new Point3D(26, 57, 0)), //t23
				new Triangle(new Color(java.awt.Color.BLUE),new Material(0, 0, 0, 0, 0.55),
						new Point3D(0, 0, 400),   //t31
						new Point3D(90, 80, -110),  //t32
						new Point3D(-90, 80, -110)),
	
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.07, 0.2, 30, 0.7, 0), 7,
						new Point3D(0, 10, -10)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.07, 0.2, 30, 0.7, 0), 10,
						new Point3D(20, 0, -30)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.07, 0.2, 30, 0.7, 0), 17,
						new Point3D(-28, -10, -30)),

				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
						30, new Point3D(0, 0, 200))); 


		scene.addLights(
				new DirectionalLight(new Color(500, 300, 0),new Vector(0, 0, 1)),
				new SpotLight(new Color(400, 240, 0), //
						new Point3D(-80, 20, 0), new Vector(0.5, -0.5, 3), 1, 1E-5, 1.5E-7),
				new SpotLight(new Color(400, 240, 0), //
						new Point3D(30, 0, -100), new Vector(2, 0, 3), 1, 1E-5, 1.5E-7));

		ImageWriter imageWriter = new ImageWriter("Mini Project 1", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
}
