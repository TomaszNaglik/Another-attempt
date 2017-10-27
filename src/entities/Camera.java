package entities;
import org.joml.*;
import org.joml.Math;

import gameObject.GameObject;
import gameObject.Transform;
import input.Input;
//import math.Quaternion;
//import math.Vector2f;
//import math.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class Camera extends GameObject {

	private float cameraSpeed = 5.0f;
	private float rotationSpeed = 1.0f;

	private boolean locked = false;
	private float sensitivity = 1.1f;

	public Camera(Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(position, rotation, scale);

	}
	
	
	
	public void keyBoardUpdate() {
//		if (Input.GetKey(Input.KEY_K)) {
//			MasterRenderer.setDensity(MasterRenderer.getDensity() + 0.00005f);
//		}
//		if (Input.GetKey(Input.KEY_L)) {
//			MasterRenderer.setDensity(MasterRenderer.getDensity() - 0.00005f);
//		}
		if (Input.GetKey(Input.KEY_UP)) {
			transform.move(transform.getForward(), cameraSpeed);	
		}
		if (Input.GetKey(Input.KEY_DOWN)) {
			transform.move(transform.getForward(), -cameraSpeed);	
		}
		if (Input.GetKey(Input.KEY_LEFT)) {
			transform.move(transform.getRight(), -cameraSpeed);
		}
		if (Input.GetKey(Input.KEY_RIGHT)) {
			transform.move(transform.getRight(), cameraSpeed);
		}
		if (Input.GetKey(Input.KEY_N)) {
			transform.Rotate(new Vector3f(1,0,0),   (float) Math.toRadians(rotationSpeed * sensitivity));
			
		}
		if (Input.GetKey(Input.KEY_M)) {
			transform.Rotate(new Vector3f(1,0,0),   (float) Math.toRadians(-rotationSpeed * sensitivity));	
			
		}
		if (Input.GetKey(Input.KEY_J)) {
			transform.Rotate(new Vector3f(0,1,0),   (float) Math.toRadians(rotationSpeed * sensitivity));	
			
		}
		if (Input.GetKey(Input.KEY_K)) {
			transform.Rotate(new Vector3f(0,1,0),   (float) Math.toRadians(-rotationSpeed * sensitivity));	
			
		}
		if (Input.GetKey(Input.KEY_I)) {
			transform.Rotate(new Vector3f(0,0,1),   (float) Math.toRadians(rotationSpeed * sensitivity));	
		}
		if (Input.GetKey(Input.KEY_O)) {
			transform.Rotate(new Vector3f(0,0,1),   (float) Math.toRadians(-rotationSpeed * sensitivity));	
		}
	}
	public void mouseUpdate() {
		Vector2f centerPosition = new Vector2f(DisplayManager.getWidth() / 2, DisplayManager.getHeigth() / 2);
		
		if (Input.GetMouse(0)) {
			Input.SetCursor(false);
			Input.SetMousePosition(centerPosition);
			this.locked = true;
		} else if (Input.GetMouse(1)) {
			Input.SetCursor(true);
			this.locked = false;
		}

		if (locked) {
			
			Vector2f mousePos = Input.GetMousePosition();
			Vector2f deltaPos = mousePos.sub(centerPosition);
			//deltaPos.sub(mousePos);
			
			boolean rotY = deltaPos.x != 0;
			boolean rotX = deltaPos.y != 0;
			//System.out.println(deltaPos.x);
			if (rotY)
				transform.Rotate(transform.calcAndReturnUpAxis(),   (float) Math.toRadians(deltaPos.x * sensitivity));
			if (rotX)
				transform.Rotate(transform.calcAndReturnForwardAxis(),(float) Math.toRadians(deltaPos.y * sensitivity *-1));

			if (rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
	}

	public void update()

	{
		keyBoardUpdate();
		mouseUpdate();
	}

	
	@Override
	public void printoutDirection() {
		System.out.print("C: " + transform.GetPos().toString() + "R : " + transform.GetRot().toString());
	}

}
