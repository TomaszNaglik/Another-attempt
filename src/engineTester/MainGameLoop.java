package engineTester;

import java.util.ArrayList;
import java.util.List;
import org.joml.*;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import input.Input;
//import math.Quaternion;
//import math.Vector3f;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.*;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		Vector3f standardScale = new Vector3f(1, 1, 1);
		Quaternionf standardRotation = new Quaternionf(0, 0, 0, 1);

		TexturedModel dragonModel = new TexturedModel(OBJFileLoader.loadOBJ("dragon", loader),
				new ModelTexture(loader.loadTexture("white")));
		dragonModel.print();
		System.out.println("");

		Player player = new Player(dragonModel, new Vector3f(0, 0, -20), standardRotation, standardScale); 	// problem could be here
		Camera camera = new Camera(new Vector3f(0, 0, 0), standardRotation, standardScale); 				// problem could be here

		Light light = new Light(new Vector3f(0, 2000000, 20), new Vector3f(1, 1, 1)); 						// problem could be here
		player.printoutDirection();
		camera.printoutDirection();
		light.printoutDirection();
		System.out.println("");

		MasterRenderer renderer = new MasterRenderer(); // problem could be here

		while (DisplayManager.notClose() && !Input.GetKey(Input.KEY_Q)) {

			

			Input.update();
			camera.update();
			player.update();

			
			renderer.processEntity(player);
			renderer.render(light, camera);

			DisplayManager.updateDisplay();
			// renderer.checkError();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
