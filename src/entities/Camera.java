package entities;
import org.joml.*;
import org.joml.Math;

import gameObject.GameObject;
import input.Input;
//import math.Quaternion;
//import math.Vector2f;
//import math.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class Camera extends GameObject {

	private static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	private Vector3f position = new Vector3f(0, 55, 0);
	private Vector3f target = new Vector3f(1, 5, 0);
	private Vector3f direction = new Vector3f(0, 0, 0);

	private Vector3f right = new Vector3f(0, 0, 0);
	private Vector3f up = new Vector3f(0, 1, 0);
	private float pitch;
	private float yaw;
	private float roll;

	private float cameraSpeed = 5.0f;
	private float rotationSpeed = 1.0f;

	private boolean locked = false;
	private float sensitivity = 15.0f;

	public Camera(Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(position, rotation, scale);

	}

	public void update()

	{

		if (Input.GetKey(Input.KEY_K)) {
			MasterRenderer.setDensity(MasterRenderer.getDensity() + 0.00005f);
		}
		if (Input.GetKey(Input.KEY_L)) {
			MasterRenderer.setDensity(MasterRenderer.getDensity() - 0.00005f);
		}
		Vector2f centerPosition = new Vector2f(DisplayManager.getWidth() / 2, DisplayManager.getHeigth() / 2);

		if (Input.GetKey(Input.KEY_UP)) {
			transform.SetPos(transform.GetPos().add(new Vector3f(1, 0, 0)));
		}
		if (Input.GetKey(Input.KEY_DOWN)) {
			transform.SetPos(transform.GetPos().add(new Vector3f(-1, 0, 0)));
		}
		if (Input.GetKey(Input.KEY_LEFT)) {
			transform.SetPos(transform.GetPos().add(new Vector3f(0, 0, 1)));
		}
		if (Input.GetKey(Input.KEY_RIGHT)) {
			transform.SetPos(transform.GetPos().add(new Vector3f(0, 0, -1)));
		}

		
		if (Input.GetMouse(0)) {
			Input.SetCursor(false);
			Input.SetMousePosition(centerPosition);
			this.locked = true;
		} else if (Input.GetMouse(1)) {
			Input.SetCursor(true);
			this.locked = false;
		}

		if (locked) {
			Vector2f deltaPos = new Vector2f(0, 0);
			deltaPos = centerPosition.sub(Input.GetMousePosition());

			boolean rotY = deltaPos.x != 0;
			boolean rotX = deltaPos.y != 0;

			if (rotY)
				getTransform().Rotate(Y_AXIS, (float) Math.toRadians(deltaPos.x * sensitivity));
			if (rotX)
				getTransform().Rotate(getTransform().GetRightAxis(),(float) Math.toRadians(deltaPos.y * sensitivity *1));

			if (rotY || rotX)
				Input.SetMousePosition(centerPosition);

			
		}
		

	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public Vector3f getDirection() {
		return direction;
	}

	

	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}

	public Vector3f getRight() {
		return right;
	}

	public void setRight(Vector3f right) {
		this.right = right;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}

	@Override
	public void printoutDirection() {
		System.out.print("C: " + transform.GetPos().toString() + "R : " + transform.GetRot().toString());// +" Direction
																											// :
																											// "+direction.toString()+
																											// " Pitch:
																											// "+pitch+"
																											// Yaw:
																											// "+yaw+"
																											// Roll:
																											// "+roll);
	}

}
