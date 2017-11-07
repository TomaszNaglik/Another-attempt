package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.Quaternionf;
import org.joml.Vector3f;



import gameObject.GameObject;
import input.Input;
import models.RawModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Planet extends GameObject {
	
	private final int RESOLUTION = 512;
	private final int SIDE = 100000;
	private final int RADIUS = 1000;
	
	
	private static final float MAX_HEIGHT = 250;
	private static final float MAX_PIXEL_COLOUR = 256*256*256;
	
	
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private RawModel model;
	private Vector3f movementDirection;
	private Vector3f rotation;
	private float rotationSpeed = 2f;
	private float movementSpeed = 2000f;
	Vector3f[][] globe;
	

	public Planet(int gridX, int gridY, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap, String heightMap, int side) {
		super(new Vector3f(gridX, gridY, gridZ), new Quaternionf(0, 0, 0, 1), new Vector3f(1, 1, 1));
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.globe = new Vector3f[RESOLUTION][RESOLUTION];
		this.model = generateSphere(loader, heightMap);
		
	}

private RawModel generateSphere(Loader loader, String heightMap) {
		
		float pi = (float) Math.PI;
		//float halfPi = (float) Math.PI/2;
		
		int size = RESOLUTION*RESOLUTION;
		
		float [] vertices = new float[size*3];
		float [] normals = new float[size*3];
		float [] textureCoords = new float[size*2];
		int[] indices = new int[6*(RESOLUTION-1)*(RESOLUTION-1)];
				
		BufferedImage image = getImage(heightMap);
				
		int vertexPointer = 0;
		for(int i=0; i< RESOLUTION; i++) {
			float lat = map(i,0,RESOLUTION-1,-2*pi, 0);
			for(int j=0;j<RESOLUTION; j++) {
				float lon = map(j,0,RESOLUTION-1,-pi, 0);
				
				Vector3f vertex = projectToSphere(generateVertex(lon, lat),this.transform.GetPos(), RADIUS,i,j,image);
				globe[i][j] = vertex;
				
				
				vertices[vertexPointer * 3]   = vertex.x;
				vertices[vertexPointer * 3+1] = vertex.y;
				vertices[vertexPointer * 3+2] = vertex.z;
				
				
				
				textureCoords[vertexPointer * 2] = (float) i / ((float) RESOLUTION - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) j / ((float) RESOLUTION - 1);
				vertexPointer++;
			}
		}
		
		vertexPointer = 0;
		for(int i=0; i< RESOLUTION-1; i++) {
			for(int j=0;j<RESOLUTION-1; j++) {
				if(j==512){
					System.out.println(j+" : "+i);
				}
				Vector3f normal = calcNormal(i,j);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int i=0; i< RESOLUTION-1; i++) {
			for(int j=0;j<RESOLUTION-1; j++) {
				int topLeft = (j * RESOLUTION) + i;
				int topRight = topLeft + 1;
				int bottomLeft = ((j + 1) * RESOLUTION) + i;
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

	

	private BufferedImage getImage(String heightMap) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/textures/" + heightMap +".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return image;
}

	private Vector3f generateVertex(float lon, float lat) {
		
		Vector3f vertex = new Vector3f();
		vertex.x = RADIUS*(float)Math.sin(lon)*(float)Math.cos(lat);
		vertex.y = -RADIUS*(float)Math.cos(lon);
		vertex.z = RADIUS*(float)Math.sin(lon)*(float)Math.sin(lat);
		
		return vertex;
		
	}
	
	private float map(float value, float min, float max, float newMin, float newMax) {
		return ((value-min)/(max-min))*(newMax-newMin)+newMin;
	}

	private Vector3f projectToSphere(Vector3f vertex, Vector3f center, int radius, int x, int z, BufferedImage image) {
		Vector3f newVertex = new Vector3f(vertex);
		float distance = radius + getHeight(x,z,image);
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
	
	private Vector3f calcNormal(int x, int z)
	{
		if ( x<1 || x> RESOLUTION-2 || z<1 || z > RESOLUTION -2) {
			Vector3f newNormal = new Vector3f();
			newNormal = globe[x][z].sub(this.getTransform().GetPos()).normalize();
			return newNormal;
		}
		Vector3f center = globe[x][z];
		Vector3f up = globe[x][z-1];
		Vector3f down = globe[x][z+1];
		Vector3f left = globe[x-1][z];
		Vector3f right = globe[x+1][z];
		Vector3f rightUp = globe[x+1][z-1];
		Vector3f leftDown = globe[x-1][z];
		
		
		Vector3f dUp = new Vector3f(center).sub(up);
		Vector3f dRightUp = new Vector3f(center).sub(rightUp);
		Vector3f dRight = new Vector3f(center).sub(right);
		Vector3f dDown = new Vector3f(center).sub(down);
		Vector3f dLeft = new Vector3f(center).sub(left);
		Vector3f dLeftDown = new Vector3f(center).sub(leftDown);
		
		
		Vector3f normal = new Vector3f(dUp.cross(dRightUp)).
						add(new Vector3f(dRightUp.cross(dRight))).
						add(new Vector3f(dRight.cross(dDown).mul(0.5f))).
						add(new Vector3f(dDown.cross(dLeftDown))).
						add(new Vector3f(dLeftDown.cross(dLeft))).
						add(new Vector3f(dLeft.cross(dUp).mul(0.5f)));
			
		return normal.normalize();	
			
			
		
		
	}
	
	
	
	
	public void keyBoardsUpdate() {
		
		movementDirection = new Vector3f(0,0,0);
		rotation = new Vector3f(0,0,0);
		if (Input.GetKey(Input.KEY_W)) {
			movementDirection.add(new Vector3f(1,0,0));
		}
		if (Input.GetKey(Input.KEY_S)) {
			movementDirection.add(new Vector3f(-1,0,0));
		}
		if (Input.GetKey(Input.KEY_A)) {
			movementDirection.add(new Vector3f(0,0,1));
		}
		if (Input.GetKey(Input.KEY_D)) {
			movementDirection.add(new Vector3f(0,0,-1));
		}
		if (Input.GetKey(Input.KEY_Z)) {
			movementDirection.add(new Vector3f(0,1,0));
		}
		if (Input.GetKey(Input.KEY_C)) {
			movementDirection.add(new Vector3f(0,-1,0));
		}
		if (Input.GetKey(Input.KEY_T)) {
			rotation.add(new Vector3f(1,0,0));
		}
		if (Input.GetKey(Input.KEY_G)) {
			rotation.add(new Vector3f(-1,0,0));
		}
		if (Input.GetKey(Input.KEY_Y)) {
			rotation.add(new Vector3f(0,1,0));
		}
		if (Input.GetKey(Input.KEY_H)) {
			rotation.add(new Vector3f(0,-1,0));
		}
		if (Input.GetKey(Input.KEY_U)) {
			rotation.add(new Vector3f(0,0,1));
		}
		if (Input.GetKey(Input.KEY_J)) {
			rotation.add(new Vector3f(0,0,-1));
		}
	}

	
	public void transformUpdate() {
		
		keyBoardsUpdate();
		float delta = DisplayManager.getFrameTimeSeconds();
		
		if(rotation.x != 0) 
			transform.Rotate(new Vector3f(1,0,0).mul(rotation), rotationSpeed*delta);
		if(rotation.y != 0) 
			transform.Rotate(new Vector3f(0,1,0).mul(rotation), rotationSpeed*delta);
		if(rotation.z != 0) 
			transform.Rotate(new Vector3f(0,0,1).mul(rotation), rotationSpeed*delta);
		transform.calcAxis();
		
		if(!movementDirection.equals(new Vector3f(0,0,0))) {
			if(movementDirection.x != 0) {
				transform.Move(transform.getForward().mul(movementDirection.x), movementSpeed*delta);
			}
			if(movementDirection.y != 0) {
				transform.Move(transform.getUp().mul(movementDirection.y), movementSpeed*delta);
			}
			if(movementDirection.z != 0) {
				transform.Move(transform.getRight().mul(movementDirection.z), movementSpeed*delta);
			}
			
		}
		
		
	}
	
	public void update() {
		
		
		transformUpdate();
		
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

	

