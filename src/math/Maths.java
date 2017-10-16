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
		
		transformationMatrix.translate(transform.GetPos());
		transformationMatrix.rotate(transform.GetRot());

		return transformationMatrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.rotate(camera.getTransform().GetRot());
		viewMatrix.translate(camera.getTransform().GetPos());
		//viewMatrix.lookAt(camera.getTransform().GetPos(), camera.getTransform().getUp(),camera.getTransform().getRight().mul(1));
		//viewMatrix.
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
