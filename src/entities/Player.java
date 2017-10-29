package entities;
import org.joml.*;
import input.Input;
//import math.Quaternion;
//import math.Vector3f;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class Player extends Entity {

	private float movementSpeed = 20f;
	private float rotationSpeed = 10f;
	private float upwardsSpeed = 0;
	private static float GRAVITY = 500;
	private static float JUMP_POWER = 250;
	private Vector3f movementDirection;
	private Vector3f rotation;
	


	public Player(TexturedModel model, Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(model, position, rotation, scale);

	}
	
	public void getInput() {
		
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

	public void update() {
		//upwardsSpeed -= GRAVITY*DisplayManager.getFrameTimeSeconds();
		//if(upwardsSpeed < 0) upwardsSpeed = 0;
		getInput();
		move();
		
	}
	public void move() {
		
		float delta = DisplayManager.getFrameTimeSeconds();
		
		if(rotation.x != 0) 
			transform.Rotate(new Vector3f(1,0,0).mul(rotation), rotationSpeed*delta);
		if(rotation.y != 0) 
			transform.Rotate(new Vector3f(0,1,0).mul(rotation), rotationSpeed*delta);
		if(rotation.z != 0) 
			transform.Rotate(new Vector3f(0,0,1).mul(rotation), rotationSpeed*delta);
		
		if(!movementDirection.equals(new Vector3f(0,0,0))) {
		transform.move(movementDirection, movementSpeed*delta);
		}
		
		/*if (Input.GetKey(Input.KEY_W)) {
			transform.move(transform.getForward(), movementSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_S)) {
			transform.move(transform.getForward(), -movementSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_A)) {
			transform.move(transform.getRight(), -movementSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_D)) {
			transform.move(transform.getRight(), movementSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_Z)) {
			transform.move(transform.getUp(), movementSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_C)) {
			transform.move(transform.getUp(), -movementSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_T)) {
			transform.Rotate( new Vector3f(1,0,0), rotationSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_G)) {
			transform.Rotate( new Vector3f(1,0,0), -rotationSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_Y)) {
			transform.Rotate( new Vector3f(0,1,0), rotationSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_H)) {
			transform.Rotate( new Vector3f(0,1,0), -rotationSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_U)) {
			transform.Rotate( new Vector3f(0,0,1), rotationSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_J)) {
			transform.Rotate( new Vector3f(0,0,1), -rotationSpeed*delta);
		}
		if (Input.GetKey(Input.KEY_SPACE)) {
			upwardsSpeed = JUMP_POWER;
		}
		if(transform.GetPos().y >0 || upwardsSpeed>0) {
			transform.move(new Vector3f(0,1,0), upwardsSpeed*delta);
		}else {
			transform.GetPos().y = 0;
		}*/
	}

	
}
