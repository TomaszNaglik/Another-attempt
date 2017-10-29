package math;



import java.nio.FloatBuffer;

//import org.lwjgl.util.vector.Matrix4f;
//import org.lwjgl.util.vector.Vector3f;
//import org.lwjgl.util.vector.Vector4f;
import org.joml.*;
import org.lwjgl.BufferUtils;

import entities.Camera;
import gameObject.Transform;


public class Maths
{
	public static Matrix4f createTransformationMatrix(Transform transform)
	{
		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.scale(transform.GetScale());
		transformationMatrix.rotate(transform.GetRot());
		transformationMatrix.translate(transform.GetPos());
		
		transformationMatrix.invert();

		return transformationMatrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		
		Matrix4f viewMatrix = new Matrix4f();
		Quaternionf rotation = new Quaternionf(camera.getTransform().GetRot());
		Vector3f position = new Vector3f(camera.getTransform().GetPos());
		
		//rotation.mul(new Quaternionf(0,0,1,0));
		//position.mul(-1);
		
		Vector3f up = new Vector3f(camera.getTransform().getUp());
		Vector3f right = new Vector3f(camera.getTransform().getRight());
		Vector3f forward = new Vector3f(camera.getTransform().getForward());
		Vector3f pos = new Vector3f(camera.getTransform().GetPos());
		
		
		viewMatrix.rotate(rotation);
		viewMatrix.translate(position);
		viewMatrix.invert();
		//viewMatrix.lookAt(pos, pos.add(forward), up);
		//viewMatrix.
		//System.out.println(viewMatrix.toString());
		//camera.getTransform().GetPos().mul(-1);
		return viewMatrix;
		
		
	}

	public static Matrix4f createPerspectiveMatrix(float fov, float aspectRatio, float zNear, float zFar) {
		return new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
	}
	
	public static FloatBuffer CreateFlippedBuffer(Matrix4f matrix) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		return matrix.get(buffer);
	}
}
