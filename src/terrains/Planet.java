package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import gameObject.GameObject;
import models.RawModel;
import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Planet extends GameObject {
	
	private final int RESOLUTION = 1048;
	private final int SIDE = 100;
	private final int RADIUS = 100;
	
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256*256*256;
	
	
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private RawModel model;
	

	public Planet(int gridX, int gridY, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap, int side) {
		super(new Vector3f(gridX, gridY, gridZ), new Quaternionf(0, 0, 0, 1), new Vector3f(1, 1, 1));
		this.texturePack = texturePack;
		this.blendMap = blendMap;
	
		this.model = generatePlanet(loader, heightMap, side);
	}

	

	private RawModel generatePlanet(Loader loader, String heightMap, int side) {

		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/textures/" + heightMap +".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int vertexCountPerSide = RESOLUTION*RESOLUTION ;
		
		float [] vertices = new float[vertexCountPerSide*3];
		float [] normals = new float[vertexCountPerSide*3];
		float [] textureCoords = new float[vertexCountPerSide*2];
		int[] indices = new int[6*(RESOLUTION-1)*(RESOLUTION-1)];
		
		int vertexPointer = 0;
		for (int i = 0; i < RESOLUTION; i++) {
			for (int j = 0; j < RESOLUTION; j++) {
				
				Vector3f vertex = generateVertex(j,i,side);
				
				
				
				vertex = projectToSphere(vertex, this.transform.GetPos(), RADIUS,i,j,image);
				
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



	private Vector3f generateVertex(int j, int i, int side) {
		
		Vector3f vertex = new Vector3f();
		vertex.x = ((float)j/(float)RESOLUTION)*SIDE-(float)SIDE/2f;
		vertex.y = (float)SIDE/2;
		vertex.z = ((float)i/(float)RESOLUTION)*SIDE-(float)SIDE/2f;
		
		if(side==1)
			vertex.rotateAxis((float)Math.toRadians(90), 0, 0, -1);
		else if(side==2)
			vertex.rotateAxis((float)Math.toRadians(90), 1, 0, 0);
		else if(side==3)
			vertex.rotateAxis((float)Math.toRadians(90), -1, 0, 0);
		else if(side==4)
			vertex.rotateAxis((float)Math.toRadians(180), 0, 0, 1);
		else if(side==5)
			vertex.rotateAxis((float)Math.toRadians(90), 0, 0, 1);
		else if(side==0)
			vertex.rotateAxis((float)Math.toRadians(0), 0, 0, 1);
		
		return vertex;
		
	}



	private Vector3f projectToSphere(Vector3f vertex, Vector3f center, int radius, int x, int z, BufferedImage image) {
		Vector3f newVertex = new Vector3f(vertex);
		float distance = radius + getHeight(x,z,image)/10;
		newVertex.sub(center);
		newVertex.normalize();
		newVertex.mul(distance);
		return newVertex;
		
		
		
	}
	
	private float getHeight(int x, int z, BufferedImage image) {
		if(x<0 || x>=image.getHeight() || z<0 || z>= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR/2f;
		height /= MAX_PIXEL_COLOUR/2f;
		height *= MAX_HEIGHT;
		return height;
		
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

	

