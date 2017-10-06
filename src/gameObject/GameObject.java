package gameObject;
import org.joml.*;
//import math.Quaternion;
//import math.Vector3f;

public class GameObject {
	protected Transform transform;

	public GameObject(Vector3f position, Quaternionf rotation, Vector3f scale) {
		transform = new Transform();
		transform.SetPos(position);
		transform.SetRot(rotation);
		transform.SetScale(scale);
	}

	public Transform getTransform() {
		return transform;
	}

	public void printoutDirection() {
		System.out.print("P: " + transform.GetPos().toString());
	}

}
