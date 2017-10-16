package entities;
import org.joml.*;
import input.Input;
//import math.Quaternion;
//import math.Vector3f;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class Player extends Entity {

	private float movementSpeed = 1f;
	private float rotationSpeed = 0.01f;

	private float pitch;
	private float yaw;
	private float roll;

	private Vector3f target = new Vector3f(1, 3, 0);
	private Vector3f direction = new Vector3f(0, 0, 0);

	private Vector3f right = new Vector3f(0, 0, 0);
	private Vector3f up = new Vector3f(0, 1, 0);

	// private Transform transform;

	public Player(TexturedModel model, Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(model, position, rotation, scale);

	}

	public void update() {

		if (Input.GetKey(Input.KEY_W)) {
			transform.move(transform.getForward(), movementSpeed);
			//transform.SetPos(transform.GetPos().add(new Vector3f(-1, 0, 0)));
		}
		if (Input.GetKey(Input.KEY_S)) {
			//transform.SetPos(transform.GetPos().add(new Vector3f(1, 0, 0)));
			transform.move(transform.getForward(), -movementSpeed);
		}
		if (Input.GetKey(Input.KEY_A)) {
			//transform.SetPos(transform.GetPos().add(new Vector3f(0, 0, 1)));
			transform.move(transform.getRight(), -movementSpeed);
		}
		if (Input.GetKey(Input.KEY_D)) {
			//transform.SetPos(transform.GetPos().add(new Vector3f(0, 0, -1)));
			transform.move(transform.getRight(), movementSpeed);
		}
		
	
		if (Input.GetKey(Input.KEY_Z)) {
			transform.move(transform.getUp(), movementSpeed);
			//transform.SetPos(transform.GetPos().add(new Vector3f(0, 1, 0)));
		}
		if (Input.GetKey(Input.KEY_C)) {
			transform.move(transform.getUp(), -movementSpeed);
			//transform.SetPos(transform.GetPos().add(new Vector3f(0, -1, 0)));
		}
			
		if (Input.GetKey(Input.KEY_T)) {
			transform.Rotate( new Vector3f(1,0,0), rotationSpeed);
		}
		if (Input.GetKey(Input.KEY_G)) {
			transform.Rotate( new Vector3f(1,0,0), -rotationSpeed);
		}
		if (Input.GetKey(Input.KEY_Y)) {
			transform.Rotate( new Vector3f(0,1,0), rotationSpeed);
		}
		if (Input.GetKey(Input.KEY_H)) {
			transform.Rotate( new Vector3f(0,1,0), -rotationSpeed);
		}
		if (Input.GetKey(Input.KEY_U)) {
			transform.Rotate( new Vector3f(0,0,1), rotationSpeed);
		}
		if (Input.GetKey(Input.KEY_J)) {
			transform.Rotate( new Vector3f(0,0,1), -rotationSpeed);
		}
		if (Input.GetKey(Input.KEY_P)) {
			transform.SetPos(new Vector3f(0,0,0));
		}

		

	}

	
}
