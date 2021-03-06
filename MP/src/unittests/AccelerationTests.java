package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import elements.*;
import renderer.*;
import scene.*;

class AccelerationTests {

	@Test
	public void OurMultiObjectsSceneTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(20,20,-20), new Vector(-100,-100,100), new Vector(-50,100,50)));
		//scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));

		scene.setDistance(200);
		scene.setBackground(new Color(218,28,76));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

		double x=0;
		double y=0;
		double z=0;
		double xx=x;

		double zz=z;

		scene.addGeometries( 
				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0, 0.4),
						new Point3D(-5,-20,20),  
						new Point3D(5,-20,22),  
						new Point3D(5,5,5) ),
				new Triangle(new Color(java.awt.Color.RED),new Material(0, 0, 0, 0, 0.3),
						new Point3D(-20,-20,5),
						new Point3D(-20,-20,-5), 
						new Point3D(-5,5,-5)));

		/*	scene.addGeometries( 
				new Sphere(new Color(java.awt.Color.BLACK),
						new Material(0.4, 0.2, 30, 0,0),1,
						new Point3D(new Coordinate(-5),new Coordinate(5),new Coordinate(0))));

		 */
		/*	scene.addLights(
				new DirectionalLight(new Color(500, 300, 0),new Vector(0, 0, -1)));
			/*	new SpotLight(new Color(400, 240, 0), //
						new Point3D(-80, 20, 0), new Vector(0.5, -0.5, 3), 1, 1E-5, 1.5E-7),
				new SpotLight(new Color(400, 240, 0), //
						new Point3D(30, 0, -100), new Vector(2, 0, 3), 1, 1E-5, 1.5E-7));
				/*new Triangle(new Color(java.awt.Color.BLUE),new Material(0, 0, 0, 0, 0.5),
						new Point3D(60, 10, -10),
						new Point3D(40, 15, -20), 
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.BLUE),new Material(0, 0, 0, 0, 0.5),
						new Point3D(-60, 10, -10), 
						new Point3D(-40, 15, -20),
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.BLUE),new Material(0, 0, 0, 0, 0.5),
						new Point3D(40, 15, -20),
						new Point3D(20, 17, -30),
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.BLUE),new Material(0, 0, 0, 0, 0.5),
						new Point3D(-40, 15, -20),
						new Point3D(-20, 17, -30),  
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.BLUE),new Material(0, 0, 0, 0, 0.5),
						new Point3D(-20, 17, -30),
						new Point3D(20, 17, -30), 
						new Point3D(0, -7, 0)),

				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0.5, 0),
						new Point3D(70, 0, 0),  
						new Point3D(50, 7, -20),  
						new Point3D(0, -7, 0)), 
				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0.5, 0),
						new Point3D(-70, 0, 0),
						new Point3D(-50, 7, -20), 
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0.5, 0),
						new Point3D(50, 7, -20),
						new Point3D(20, 6, -30),
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0.5, 0),
						new Point3D(-50, 7, -20),
						new Point3D(-20, 6, -30),
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.GREEN),new Material(0, 0, 0, 0.5, 0),
						new Point3D(20, 6, -30),
						new Point3D(-20, 6, -30),  
						new Point3D(0, -7, 0)),
				new Triangle(new Color(java.awt.Color.YELLOW),new Material(0, 0, 0, 0.3, 0),
						new Point3D(30, -20, -60),  
						new Point3D(35, 10, -160),  
						new Point3D(-16, 50, -110))
						/*,

				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.07, 0.2, 30, 0.7, 0), 7,
						new Point3D(0, 10, -10)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.07, 0.2, 30, 0.7, 0), 10,
						new Point3D(20, 0, -30)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.07, 0.2, 30, 0.7, 0), 17,
						new Point3D(-28, -10, -30)),

				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
						30, new Point3D(0, 0, 200))*/ 

		for (int i=0; i < 8; i++){
			for (int j=0; j <= i ; j++){
				for(int k=0; k <= i; k++){
					scene.addGeometries( 
							new Sphere(new Color(java.awt.Color.BLUE),
									new Material(0.7, 0.2, 30, 0.3, 0.5),1,
									new Point3D(new Coordinate(xx),new Coordinate(y),new Coordinate(zz))));									
					if(k != i)
						zz = zz-2;
					else										
						zz = z;							
				}
				if (j!=i)	
					xx = xx + 2;
				else
					xx = x;
			}
			if (i!=9)
			{
				y-=1.8;
				x-=1.8;
				z+=1.8;
				xx=x;
				zz=z;
			}
		}

		scene.addLights(
				//new DirectionalLight(new Color(500, 300, 0),new Vector(-1, -1, 1)),
				new SpotLight(new Color(20, 20, 0), 
						new Point3D(-5,5,0), new Vector(0, -1, 0), 1, 1E-5, 1.5E-7),
				new SpotLight(new Color(400, 240, 0),
						new Point3D(30, 0, 0), new Vector(0, -1, 0), 1, 1E-5, 1.5E-7));

		ImageWriter imageWriter = new ImageWriter("Mini Project 2", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
}
