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
import terrains.Planet;
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
		

		//Player 	player 	= 	new Player(dragonModel, new Vector3f(0, 0, 			0), standardRotation, standardScale); 	
		//Camera camera = new Camera(player);
		Camera 	camera 	= 	new Camera(				new Vector3f(0, 00, 	1050), standardRotation, standardScale); 				
		Light 	light 	= 	new Light(				new Vector3f(0, 2000000, 	0), new Vector3f(1, 1, 1)); 						
		Terrain terrain =   new Terrain(0,0,0, loader, texturePack, blendMap, "hp");
		
		List<Planet> planet = new ArrayList<Planet>();
		
		Planet planet0 = new Planet(0,0,0, loader, texturePack, blendMap, "planet",0); planet.add(planet0);
		/*Planet planet1 = new Planet(0,0,0, loader, texturePack, blendMap, "planet",1); planet.add(planet1);
		Planet planet2 = new Planet(0,0,0, loader, texturePack, blendMap, "planet",2); planet.add(planet2);
		Planet planet3 = new Planet(0,0,0, loader, texturePack, blendMap, "planet",3); planet.add(planet3);
		Planet planet4 = new Planet(0,0,0, loader, texturePack, blendMap, "planet",4); planet.add(planet4);
		Planet planet5 = new Planet(0,0,0, loader, texturePack, blendMap, "planet",5); planet.add(planet5);*/
		
		
		
		MasterRenderer renderer = new MasterRenderer(); 
		camera.getTransform().SetRot(new Quaternionf(0,0.0f,0,1));
		//player.getTransform().SetRot(new Quaternionf(0,0.0f,0,1));
		

		while (DisplayManager.notClose() && !Input.GetKey(Input.KEY_Q)) {

			

			Input.update();
			
			//player.update();
			camera.update();
			light.getTransform().SetPos(camera.getTransform().GetPos());
			//light.getTransform().Move(light.getTransform().getForward().mul(-1), 1000);
			for(Planet p:planet)
				p.update();
			//camera.setTransform(player.getTransform());
			//camera.getTransform().move(camera.getTransform().GetForwardAxis(), 20);
			//player.getTransform().Rotate(player.getTransform().GetUpAxis(), 0.005f);
			
			//renderer.processEntity(player);
			//renderer.processTerrain(terrain);
			for(Planet p:planet)
				renderer.processPlanet(p);
			/*renderer.processPlanet(planet0);
			renderer.processPlanet(planet1);
			renderer.processPlanet(planet2);
			renderer.processPlanet(planet3);
			renderer.processPlanet(planet4);
			renderer.processPlanet(planet5);*/
			renderer.render(light, camera);

//			System.out.print(player.getTransform().Vec3ToString(player.getTransform().GetPos())+"   ");
//			System.out.println("  Forward: "+camera.getTransform().Vec3ToString(player.getTransform().getForward()));
//			System.out.print("  Right: "+camera.getTransform().Vec3ToString(camera.getTransform().getRight()));
//			System.out.println("  Up: "+camera.getTransform().Vec3ToString(camera.getTransform().getUp()));
//			System.out.print("Dragon pos: "+player.getTransform().Vec3ToString(player.getTransform().GetPos()));
//			System.out.println("Terrain pos: "+terrain.getTransform().Vec3ToString(terrain.getTransform().GetPos()));
			DisplayManager.updateDisplay();
			// renderer.checkError();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
