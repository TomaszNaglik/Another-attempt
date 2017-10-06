package entities;
import org.joml.*;
import gameObject.GameObject;
//import math.Quaternion;
//import math.Vector3f;

//import org.lwjgl.util.vector.Vector3f;

public class Light extends GameObject {
	private Vector3f position;
	private Vector3f colour;

	public Light(Vector3f position, Vector3f colour) {
		super(position, new Quaternionf(0, 0, 0, 1), new Vector3f(1, 1, 1));
		this.position = position;
		this.colour = colour;
	}

	public Vector3f getPosition() {
		return transform.GetPos();
	}

	public void setPosition(Vector3f position) {
		this.transform.SetPos(position);
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

}
