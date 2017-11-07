package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joml.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
//import org.lwjgl.util.vector.Matrix4f;
//import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import math.Maths;
//import math.Matrix4f;
//import math.Vector3f;
import models.TexturedModel;
import shaders.PlanetShader;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Planet;
import terrains.Terrain;

public class MasterRenderer {

	private static final float FOV = 120;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 100000;

	private static final float RED =  135.0f/255.0f;
	private static final float GREEN =  206.0f/255.0f;
	private static final float BLUE =  250.0f/255.0f;

	private static float density = 0.0000f;
	private static float gradient = 5.0f;

	private Matrix4f projectionMatrix;
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;// = new EntityRenderer(shader);

	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private PlanetRenderer planetRenderer;
	private PlanetShader planetShader = new PlanetShader();

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private List<Planet> planetChunks = new ArrayList<Planet>();
	
	public MasterRenderer() {
		createProjectionMatrix();
		this.renderer = new EntityRenderer(shader, projectionMatrix);
		this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		this.planetRenderer = new PlanetRenderer(planetShader, projectionMatrix);
		enableCulling();

	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);

	}

	public void render(Light sun, Camera camera) {
		prepare();

		shader.start();
		shader.loadSkyColour(new Vector3f(RED, GREEN, BLUE));
		shader.loadFogVariables(density, gradient);
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();

		terrainShader.start();
		terrainShader.loadSkyColour(new Vector3f(RED, GREEN, BLUE));
		terrainShader.loadFogVariables(density, gradient);
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		planetShader.start();
		planetShader.loadSkyColour(new Vector3f(RED, GREEN, BLUE));
		planetShader.loadFogVariables(density, gradient);
		planetShader.loadLight(sun);
		planetShader.loadViewMatrix(camera);
		planetRenderer.render(planetChunks);
		planetShader.stop();
		
		planetChunks.clear();
		entities.clear();
		terrains.clear();
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	public void processPlanet(Planet chunk) {
		planetChunks.add(chunk);
	}

	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);

		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		projectionMatrix = Maths.createPerspectiveMatrix(FOV, aspectRatio, NEAR_PLANE, FAR_PLANE);

	}

	public static float getDensity() {
		return density;
	}

	public static void setDensity(float density) {
		MasterRenderer.density = density;
	}

	public static float getGradient() {
		return gradient;
	}

	public static void setGradient(float gradient) {
		MasterRenderer.gradient = gradient;
	}

	public void checkError() {
		// TODO Auto-generated method stub
		if (GL11.glGetError() == GL11.GL_NO_ERROR)
			System.out.println("NO ERROR");
		else
			System.out.println("Error found");
	}

}
