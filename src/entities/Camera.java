package entities;
import org.joml.*;
import org.joml.Math;
import org.lwjgl.input.Mouse;

import gameObject.GameObject;
import gameObject.Transform;
import input.Input;
import math.Maths;
//import math.Quaternion;
//import math.Vector2f;
//import math.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;

public class Camera extends GameObject {

	private float cameraSpeed = 1500.0f;
	private float rotationSpeed = 5.0f;
	
	
	private Vector3f direction;
	private Vector3f rotation;

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
	//thinmatrix player camera
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
		transform.GetRot().rotateAxis((float) Math.toRadians(180 - theta), transform.getUp());
	}
	
	private float calculateHDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	//
		
	public void keyBoardUpdate() {
	
		
		direction = new Vector3f(0,0,0);
		rotation = new Vector3f(0,0,0);
		if (Input.GetKey(Input.KEY_LEFT)) {
			direction.add(new Vector3f(1,0,0));
		}
		if (Input.GetKey(Input.KEY_RIGHT)) {
			direction.add(new Vector3f(-1,0,0));
		}
		if (Input.GetKey(Input.KEY_UP)) {
			direction.add(new Vector3f(0,0,1));
		}
		if (Input.GetKey(Input.KEY_DOWN)) {
			direction.add(new Vector3f(0,0,-1));
		}
		if (Input.GetKey(Input.KEY_PRIOR)) {
			direction.add(new Vector3f(0,1,0));
		}
		if (Input.GetKey(Input.KEY_NEXT)) {
			direction.add(new Vector3f(0,-1,0));
		}
		if (Input.GetKey(Input.KEY_O)) {
			rotation.add(new Vector3f(1,0,0));
		}
		if (Input.GetKey(Input.KEY_P)) {
			rotation.add(new Vector3f(-1,0,0));
		}
		if (Input.GetKey(Input.KEY_K)) {
			rotation.add(new Vector3f(0,1,0));
		}
		if (Input.GetKey(Input.KEY_L)) {
			rotation.add(new Vector3f(0,-1,0));
		}
		if (Input.GetKey(Input.KEY_M)) {
			rotation.add(new Vector3f(0,0,1));
		}
		if (Input.GetKey(Input.KEY_N)) {
			rotation.add(new Vector3f(0,0,-1));
		}
		
		transform.calcForwardAxis();
		transform.calcRightAxis();
		transform.calcUpAxis();

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
				transform.Rotate(transform.getUp(),   (float) Math.toRadians(deltaPos.x * sensitivity));
			if (rotX)
				transform.Rotate(transform.getForward(),(float) Math.toRadians(deltaPos.y * sensitivity *-1));

			if (rotY || rotX)
				Input.SetMousePosition(centerPosition);
		}
	}

	public void update()

	{
		
		//mouseUpdate();
		//playerCameraUpdate();
		//playerCameraPosition();
		transformUpdate();
	}

	private void rotate(Vector3f axis, float angle, float direction){
		transform.Rotate(axis.mul(direction), angle);
		//transform.SetRot(Maths.rotateAroundAxis(axis.mul(direction), angle));
		//transform.GetRot().add(Maths.rotateAroundAxis(axis.mul(direction), angle)).normalize();
		//transform.GetRot().rotateAxis(angle, axis.x*direction, axis.y*direction, axis.z*direction, transform.GetRot());
		//transform.SetRot(Maths.rotateAroundAxis(axis.mul(direction), angle).mul(transform.GetRot().conjugate()));
	}
	
	private void transformUpdate() {
		keyBoardUpdate();
		float delta = DisplayManager.getFrameTimeSeconds();
		
		if(rotation.x != 0) 
			rotate(new Vector3f(1,0,0),rotationSpeed*delta,rotation.x);
		if(rotation.y != 0) 
			rotate(new Vector3f(0,1,0),rotationSpeed*delta,rotation.y);
		if(rotation.z != 0) 
			rotate(new Vector3f(0,0,1),rotationSpeed*delta,rotation.z);
		

		
		if(!direction.equals(new Vector3f(0,0,0))) {
		transform.Move(direction, cameraSpeed*delta);
		}
		
	}
	@Override
	public void printoutDirection() {
		System.out.print("C: " + transform.GetPos().toString() + "R : " + transform.GetRot().toString());
	}

}
