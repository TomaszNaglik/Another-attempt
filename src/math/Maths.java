package math;



//import org.lwjgl.util.vector.Matrix4f;
//import org.lwjgl.util.vector.Vector3f;
//import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import gameObject.Transform;

public class Maths
{
	public static Matrix4f createTransformationMatrix(Transform transform)
	{
		return transform.GetTransformation();
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		
		Matrix4f viewMatrix = new Matrix4f();
		Matrix4f cameraRotation = camera.getTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
		Vector3f cameraPos = camera.getTransform().GetTransformedPos().Mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().InitTranslation(cameraPos);

		return viewMatrix.Mul(cameraRotation.Mul(cameraTranslation));
		
		
	}

	public static Matrix4f createPerspectiveMatrix(float fov, float aspectRatio, float zNear, float zFar) {
		return new Matrix4f().InitPerspective(fov, aspectRatio, zNear, zFar);
	}
}
