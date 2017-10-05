package entities;

//import org.lwjgl.util.vector.Vector3f;

import Models.TexturedModel;
import engineTester.Transform;
import toolBox.Quaternion;
import toolBox.Vector3f;

public class Entity extends GameObject
{
	private TexturedModel model;
	
	protected Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	
	

	
	public Entity(TexturedModel model, Vector3f position, Quaternion rotation, Vector3f scale)
	{
		super(position, rotation,scale);
		this.model = model;
		
	}
	
	public void increasePosition(Vector3f change)
	{
		transform.GetPos().Add(change);
	}
	
	public void increaseRotation(Quaternion change)
	{
		transform.GetRot().Add(change);
	}
	
	

	public TexturedModel getModel()
	{
		return model;
	}

	public void setModel(TexturedModel model)
	{
		this.model = model;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getRotX()
	{
		return rotX;
	}

	public void setRotX(float rotX)
	{
		this.rotX = rotX;
	}

	public float getRotY()
	{
		return rotY;
	}

	public void setRotY(float rotY)
	{
		this.rotY = rotY;
	}

	public float getRotZ()
	{
		return rotZ;
	}

	public void setRotZ(float rotZ)
	{
		this.rotZ = rotZ;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}
}
