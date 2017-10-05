package entities;

import engineTester.Transform;
import toolBox.Quaternion;
import toolBox.Vector3f;

public class GameObject {
	protected Transform transform;
	
	public GameObject(Vector3f position, Quaternion rotation, Vector3f scale) {
		transform = new Transform();
		transform.SetPos(position);
		transform.SetRot(rotation);
		transform.SetScale(scale);
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public void printoutDirection()
	{
		System.out.print("P: "+transform.GetPos().toString());
	}
	
}
