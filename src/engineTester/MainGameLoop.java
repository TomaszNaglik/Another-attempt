package engineTester;


import java.util.ArrayList;
import java.util.List;

//import org.lwjgl.util.vector.Vector3f;

import Models.RawModel;
import Models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.*;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolBox.Quaternion;
import toolBox.Vector3f;

public class MainGameLoop
{
	public static void main(String[] args)
	{
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		/*int numOfTerrains = 10;
		int numOfPlants = 4000;*/
		Vector3f standardScale = new Vector3f(1,1,1);
		Quaternion standardRotation = new Quaternion(0,0,0,1);
		
		/*TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));
		
		*/
		
		
		TexturedModel dragonModel = new TexturedModel(OBJFileLoader.loadOBJ("dragon",loader), new ModelTexture(loader.loadTexture("white")));
		dragonModel.print();
		System.out.println("");
		/*TexturedModel treeModel = new TexturedModel(OBJFileLoader.loadOBJ("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel fernModel = new TexturedModel(OBJFileLoader.loadOBJ("fern", loader), new ModelTexture(loader.loadTexture("fern")));
		TexturedModel grassModel = new TexturedModel(OBJFileLoader.loadOBJ("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		
		fernModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLighting(true);
		
		List<Entity> trees = new ArrayList<Entity>();
		
		for(int i=0; i<numOfPlants ; i++)
		{
			//Entity tree = new Entity(treeModel, new Vector3f(((float)Math.random()*1600)-800,0,((float)Math.random()*800)-800),0,0,0,10);
			Vector3f newLocation = new Vector3f(((float)Math.random()*numOfTerrains*Terrain.getSize())-numOfTerrains*Terrain.getSize()/2,0,
					 ((float)Math.random()*numOfTerrains*Terrain.getSize())-numOfTerrains*Terrain.getSize()/2);
			Entity tree = new Entity(treeModel, newLocation, new Quaternion(0,0,0,1),standardScale);
			trees.add(tree);
		}
		
		List<Entity> plants = new ArrayList<Entity>();
		for(int i=0; i<numOfPlants ; i++)
		{
			Vector3f newLocation = new Vector3f(((float)Math.random()*numOfTerrains*Terrain.getSize())-numOfTerrains*Terrain.getSize()/2,0,
												 ((float)Math.random()*numOfTerrains*Terrain.getSize())-numOfTerrains*Terrain.getSize()/2);
			Entity fern = new Entity(fernModel, newLocation, new Quaternion(0,0,0,1),standardScale);
			fern.setScale(2.0f);
			plants.add(fern);
		}
		for(int i=0; i<numOfPlants ; i++)
		{
			Vector3f newLocation = new Vector3f(((float)Math.random()*numOfTerrains*Terrain.getSize())-numOfTerrains*Terrain.getSize()/2,0,
												 ((float)Math.random()*numOfTerrains*Terrain.getSize())-numOfTerrains*Terrain.getSize()/2);
			Entity grass = new Entity(grassModel, newLocation, new Quaternion(0,0,0,1),standardScale);
			grass.setScale(2.0f);
			plants.add(grass);
		}
		List<Terrain> terrains = new ArrayList<Terrain>();
		for (int i=0;i<numOfTerrains;i++)
			for(int j =0; j<numOfTerrains;j++)
			{
				Terrain terrain = new Terrain(i-5,j-5,loader, texturePack,blendMap);
				terrains.add(terrain);
			}
	*/
		
		Player player = new Player(dragonModel, new Vector3f(0,0,-20),standardRotation,standardScale); 	// problem could be here
		Camera camera = new Camera(new Vector3f(0,0,0),standardRotation,standardScale); 					// problem could be here
		
		Light light = new Light(new Vector3f(0,2000000,20), new Vector3f(1,1,1));								// problem could be here
		player.printoutDirection();
		camera.printoutDirection();
		light.printoutDirection();
		System.out.println("");
		
		MasterRenderer renderer = new MasterRenderer();															// problem could be here
		
		while(DisplayManager.notClose() && !Input.GetKey(Input.KEY_Q))
		{
			//player.increaseRotation(0, 0.5f, 0);
			//entity.increasePosition(0, 0, -0.1f);
			renderer.processEntity(player);
			/*for(Entity tree:trees) {
				renderer.processEntity(tree);
			}
			for(Entity plant:plants) {
				renderer.processEntity(plant);
			}
			for(Terrain terrain:terrains)
			{
				renderer.processTerrain(terrain);
			}*/
			
			Input.update();
			camera.update();
			player.update();
			
			
			
			
			renderer.render(light, camera);
			//System.out.println(light.getTransform().GetPos().toString());
			//player.printoutDirection();
			//camera.printoutDirection();
			DisplayManager.updateDisplay();
			//renderer.checkError();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
