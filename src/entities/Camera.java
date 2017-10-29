package entities;
import org.joml.*;
import org.joml.Math;
import org.lwjgl.input.Mouse;

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
	
	private Player player;
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	private float pitch = 20;
	
	float rotY = 0;
	
	public Camera(Player player)
	{
		super(new Vector3f(0,0,0), new Quaternionf(0,0,0,1), new Vector3f(1,1,1));
		this.player = player;
		
	}
	public Camera(Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(position, rotation, scale);

	}
	
	private void calculateZoom() {
		float zoomLevel = Input.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	private void calculatePitch() {
		if(Input.GetMouse(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Input.GetMouse(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	private	void playerCameraUpdate() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
	}
	
	private void playerCameraPosition() {
		float hDistance = calculateHDistance();
		float vDistance = calculateVDistance();
		calculateCameraPosition(hDistance, vDistance);
	}
	
	private void calculateCameraPosition(float hDis, float vDis) {
		float theta = rotY + angleAroundPlayer;
		float offsetX = (float) (hDis * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (hDis * Math.cos(Math.toRadians(theta)));
		
		transform.GetPos().x = player.getTransform().GetPos().x - offsetX;
		transform.GetPos().z = player.getTransform().GetPos().z - offsetZ;
		transform.GetPos().y = player.getTransform().GetPos().y + vDis;
		transform.GetRot().rotateAxis((float) Math.toRadians(180 - theta), transform.calcAndReturnUpAxis());
	}
	
	private float calculateHDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
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
		//playerCameraUpdate();
		//playerCameraPosition();
	}

	
	@Override
	public void printoutDirection() {
		System.out.print("C: " + transform.GetPos().toString() + "R : " + transform.GetRot().toString());
	}

}
