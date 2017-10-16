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

		Vector3f standardScale 			= new Vector3f		(1, 1, 1);
		Quaternionf standardRotation 	= new Quaternionf	(0, 0, 0, 1);

		TexturedModel dragonModel = new TexturedModel(OBJFileLoader.loadOBJ("dragon", loader),	new ModelTexture(loader.loadTexture("white")));
		
		TerrainTexture bcTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(bcTexture,rTexture, gTexture,bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));
		

		Player 	player 	= 	new Player(dragonModel, new Vector3f(0, 0, 			 0), standardRotation, standardScale); 	
		Camera 	camera 	= 	new Camera(				new Vector3f(0, -150, 			 0), standardRotation, standardScale); 				
		Light 	light 	= 	new Light(				new Vector3f(0, 2000000, 	20), new Vector3f(1, 1, 1)); 						
		Terrain terrain =   new Terrain(0,0, loader, texturePack, blendMap);
		MasterRenderer renderer = new MasterRenderer(); 
		//camera.getTransform().SetRot(new Quaternionf(0,0.50f,0,1));
		player.getTransform().SetRot(new Quaternionf(0,0.0f,0,1));

		while (DisplayManager.notClose() && !Input.GetKey(Input.KEY_Q)) {

			

			Input.update();
			
			player.update();
			camera.update();
			//camera.setTransform(player.getTransform());
			//camera.getTransform().move(camera.getTransform().GetForwardAxis(), 20);
			//player.getTransform().Rotate(player.getTransform().GetUpAxis(), 0.005f);
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.render(light, camera);

			//System.out.print(camera.getTransform().Vec3ToString(player.getTransform().GetPos())+"   ");
			System.out.println(player.getTransform().Vec3ToString(player.getTransform().getForward()));
			DisplayManager.updateDisplay();
			// renderer.checkError();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
