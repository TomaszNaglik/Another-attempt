package entities;
import org.joml.*;
import gameObject.GameObject;
//import math.Quaternion;
//import math.Vector3f;
import models.TexturedModel;

public class Entity extends GameObject {
	private TexturedModel model;

	

	public Entity(TexturedModel model, Vector3f position, Quaternionf rotation, Vector3f scale) {
		super(position, rotation, scale);
		this.model = model;

	}

	
	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	
}
