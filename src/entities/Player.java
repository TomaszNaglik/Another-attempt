package entities;

//import org.lwjgl.util.vector.Vector2f;
//import org.lwjgl.util.vector.Vector3f;

import Models.TexturedModel;
import engineTester.Input;
import engineTester.Transform;
import renderEngine.DisplayManager;
import renderEngine.MasterRenderer;
import toolBox.Quaternion;
import toolBox.Vector3f;

public class Player extends Entity {
	
	private float movementSpeed = 1f;
	private float rotationSpeed = 1f;
	
	private float pitch;
	private float yaw;
	private float roll;
	
	private Vector3f target = new Vector3f(1,3,0);
	private Vector3f direction = new Vector3f(0,0,0);
	
	private Vector3f right = new Vector3f(0,0,0);
	private Vector3f up = new Vector3f(0,1,0);
	
	
	//private Transform transform;
	

	public Player(TexturedModel model, Vector3f position, Quaternion rotation, Vector3f scale) {
		super(model, position, rotation, scale);
		
	}
	
	public void update() {
		
		if(Input.GetKey(Input.KEY_W)) {
			transform.SetPos(transform.GetPos().Add(new Vector3f(1,0,0)));
		}
		if(Input.GetKey(Input.KEY_S)) {
			transform.SetPos(transform.GetPos().Add(new Vector3f(-1,0,0)));
		}
		if(Input.GetKey(Input.KEY_A)) {
			transform.SetPos(transform.GetPos().Add(new Vector3f(0,0,-1)));
		}
		if(Input.GetKey(Input.KEY_D)) {
			transform.SetPos(transform.GetPos().Add(new Vector3f(0,0,1)));
		}
		
		
		
		/*updateVectors();
		
		if(Input.GetKey(Input.KEY_W)){
			this.increasePosition(direction.x*movementSpeed, direction.y*movementSpeed, direction.z*movementSpeed);
		}
		if(Input.GetKey(Input.KEY_S)){
			this.increasePosition(-direction.x*movementSpeed, -direction.y*movementSpeed, -direction.z*movementSpeed);
		}
		if(Input.GetKey(Input.KEY_A)){
			this.increasePosition(-right.x*movementSpeed, -right.y*movementSpeed, -right.z*movementSpeed);
		}
		if(Input.GetKey(Input.KEY_D)){
			this.increasePosition(right.x*movementSpeed, right.y*movementSpeed, right.z*movementSpeed);
		}
		
		
		
		
		
		if(yaw >= 180) yaw = -180;
		if(yaw < -180) yaw = 180;
		if(pitch >= 180) pitch = -180;
		if(pitch < -180) pitch = 180;
		if(roll >= 180) roll = -180;
		if(roll < -180) roll = 180;
		if(position.getY()<3) position.setY(3.0f);
		
		*/
		
	}
	
	private void updateVectors()
	{/*
		double beta =  Math.toRadians(yaw);
		double alpha = Math.toRadians(pitch);
		
		float x = (float)(Math.cos(alpha)*Math.cos(beta));
		float z = (float)(Math.sin(alpha)*Math.cos(beta));
		float y = (float) Math.sin(beta);
		
		target.setX(y);
		target.setY(z);
		target.setZ(x);
		
		
		Vector3f.sub (position, target, direction);
		Vector3f.cross(up, direction, right);
		Vector3f.cross(direction, right, up);
		
		direction.normalise();
		right.normalise();
		up.normalise();
		target.normalise();*/
	}
	
	

	

}
