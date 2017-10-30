package entities;
import org.joml.*;
//import org.lwjgl.util.vector.Quaternion;

import input.Input;
//import math.Quaternion;
//import math.Vector3f;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class Player extends Entity {

	private float movementSpeed = 20f;
	private float rotationSpeed = 2f;
	private float upwardsSpeed = 0;
	private static float GRAVITY = 500;
	private static float JUMP_POWER = 250;
	private Vector3f movementDirection;
	private Vector3f rotation;
	


	public Player(TexturedModel model, Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(model, position, rotation, scale);

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

	
}
