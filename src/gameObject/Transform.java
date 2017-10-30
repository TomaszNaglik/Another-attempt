package gameObject;

import org.joml.*;
import org.joml.Math;

import math.Maths;


public class Transform {

	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;

	private Vector3f right;
	private Vector3f up;
	private Vector3f forward;

	

	public Transform() {
		position = new Vector3f(0, 0, 0);
		rotation = new Quaternionf(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);
		
		right = new Vector3f();
		up = new Vector3f();
		forward = new Vector3f();
		
		calcAxis();

	}
	
	public void Move(Vector3f direction, float amount) {
		Vector3f newDirection = new Vector3f(direction);
		newDirection.normalize();
		newDirection.mul(amount);
		this.position.add(newDirection);
		
	}
	


	public void Rotate(Vector3f axis, float angle) {
		this.rotation.rotateAxis(angle, axis);
		
	}
	
	public void calcAxis()
	{
		calcForwardAxis();
		calcRightAxis();
		calcUpAxis();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Matrix4f GetTransformation() {
		return Maths.createTransformationMatrix(this);
	}

	public Vector3f GetPos() {
		return position;
	}

	public void SetPos(Vector3f pos) {
		this.position = pos;
	}

	public Quaternionf GetRot() {
		return rotation;
	}

	public void SetRot(Quaternionf rotation) {
		this.rotation = rotation;
	}

	public Vector3f GetScale() {
		return scale;
	}

	public void SetScale(Vector3f scale) {
		this.scale = scale;
	}

	public void SetScale(float scale) {
		SetScale( new Vector3f(scale, scale, scale));

	}

	public void calcRightAxis() {
		right = calcOwnRight();
	}
	

	public void calcUpAxis() {
		up = calcOwnUp();
	}
	public void calcForwardAxis() {
		//rotation.normalizedPositiveX(forward);
		forward = calcOwnForward();
	}
	public Vector3f getRight() {
		return right;
	}
	public Vector3f getUp() {
		return up;
	}

	public Vector3f getForward() {
		return forward;
	}



	
	
	public String Vec3ToString(Vector3f v) {
		float x = (float)v.x;
		float y = (float)v.y;
		float z = (float)v.z;
		return String.valueOf(x)+" "+ String.valueOf(y) +" "+ String.valueOf(z);
	}
	
	private Vector3f calcOwnForward() {
		Vector3f r = new Vector3f();
		r.x = 2 * (rotation.x*rotation.z + rotation.w*rotation.y);
		r.y = 2 * (rotation.y*rotation.z - rotation.w*rotation.x);
		r.z = 1 - 2 * (rotation.x*rotation.x + rotation.y*rotation.y);
		
		return r;
	}
	
	private Vector3f calcOwnRight() {
		Vector3f r = new Vector3f();
		r.x = 1 - 2 * (rotation.y*rotation.y + rotation.z*rotation.z);
		r.y = 2 * (rotation.x*rotation.y + rotation.w*rotation.z);
		r.z = 2 * (rotation.x*rotation.z - rotation.w*rotation.y);
		return r.mul(-1);
	}

	private Vector3f calcOwnUp() {
		Vector3f r = new Vector3f();
		r.x = 2 * (rotation.x*rotation.y - rotation.w*rotation.z);
		r.y = 1 - 2 * (rotation.x*rotation.x + rotation.z*rotation.z);
		r.z = 2 * (rotation.y*rotation.z + rotation.w*rotation.x);
		
		return r;
	}



	

}
