package terrains;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import gameObject.GameObject;
import models.RawModel;
import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Planet extends GameObject {
	
	private final int RESOLUTION = 100;
	private final int SIDE = 100;
	private final int RADIUS = 100;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private RawModel model;
	

	public Planet(int gridX, int gridY, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap) {
		super(new Vector3f(gridX, gridY, gridZ), new Quaternionf(0, 0, 0, 1), new Vector3f(1, 1, 1));
		this.texturePack = texturePack;
		this.blendMap = blendMap;
	
		this.model = generatePlanet(loader, heightMap);
	}

	

	private RawModel generatePlanet(Loader loader, String heightMap) {

		int vertexCountPerSide = RESOLUTION*RESOLUTION ;
		
		float [] vertices = new float[vertexCountPerSide*3];
		float [] normals = new float[vertexCountPerSide*3];
		float [] textureCoords = new float[vertexCountPerSide*2];
		int[] indices = new int[6*(RESOLUTION-1)*(RESOLUTION-1)];
		
		int vertexPointer = 0;
		for (int i = 0; i < RESOLUTION; i++) {
			for (int j = 0; j < RESOLUTION; j++) {
				
				Vector3f vertex = new Vector3f();
				
				vertex.x = ((float)j/(float)RESOLUTION)*SIDE-(float)SIDE/2f;
				vertex.y = (float)SIDE/2;
				vertex.z = ((float)i/(float)RESOLUTION)*SIDE-(float)SIDE/2f;
				
				vertex = projectToSphere(vertex, this.transform.GetPos(), RADIUS);
				
				vertices[vertexPointer * 3] = vertex.x;
				vertices[vertexPointer * 3 + 1] = vertex.y;
				vertices[vertexPointer * 3 + 2] = vertex.z;
				Vector3f normal = new Vector3f(0,1,0);//calculateNormal(j,i, image);
				
				/*vertices[vertexPointer * 3] = (float) j / ((float) RESOLUTION - 1.15855f) * SIDE + (0 * SIDE);
				vertices[vertexPointer * 3 + 1] = 0;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) RESOLUTION - 1.15855f) * SIDE + (0 * SIDE);*/
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float) j / ((float) RESOLUTION - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) RESOLUTION - 1);
				vertexPointer++;
			}
		}
		
		int pointer = 0;
		for (int gz = 0; gz < RESOLUTION - 1; gz++) {
			for (int gx = 0; gx < RESOLUTION - 1; gx++) {
				int topLeft = (gz * RESOLUTION) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * RESOLUTION) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVao(vertices, textureCoords, normals, indices);
		
		
	}



	private Vector3f projectToSphere(Vector3f vertex, Vector3f center, int radius) {
		Vector3f newVertex = new Vector3f(vertex);
		newVertex.sub(center);
		newVertex.normalize();
		newVertex.mul(radius);
		return newVertex;
		
		
		
	}


	public RawModel getModel() {
		return model;
	}

	public void setModel(RawModel model) {
		this.model = model;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}
}

	

