package toolBox;



import engineTester.Transform;

//import org.lwjgl.util.vector.Matrix4f;
//import org.lwjgl.util.vector.Vector3f;
//import org.lwjgl.util.vector.Vector4f;

import entities.Camera;

public class Maths
{
	public static Matrix4f createTransformationMatrix(Transform transform)
	{
		/*
		float x = (float)Math.toRadians(rotation.GetX());
		float y = (float)Math.toRadians(rotation.GetY());
		float z = (float)Math.toRadians(rotation.GetZ());
		Vector3f rotationRadians = new Vector3f(x,y,z);
		
		Matrix4f matrix = new Matrix4f();
		matrix.InitScale(scale).InitTranslation(translation).InitRotation(rotationRadians).InitIdentity();*/
		 
		return transform.GetTransformation();
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		/*float x = (float)Math.toRadians(camera.getTransform().GetRot());
		float y = (float)Math.toRadians(rotation.GetY());
		float z = (float)Math.toRadians(rotation.GetZ());
		Vector3f rotationRadians = new Vector3f(x,y,z);
		
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.InitRotation(camera.get).setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix);
		
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, - cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		//Matrix4f.invert(viewMatrix, viewMatrix);
		//Matrix4f.tr
		*/
		
		Matrix4f viewMatrix = new Matrix4f();
		Matrix4f cameraRotation = camera.getTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
		Vector3f cameraPos = camera.getTransform().GetTransformedPos().Mul(-1);

		Matrix4f cameraTranslation = new Matrix4f().InitTranslation(cameraPos);

		return viewMatrix.Mul(cameraRotation.Mul(cameraTranslation));
		
		
	}

	/*public static Matrix4f createProjectionMatrix(float x_scale, float y_scale, float zFar, float zNear,
			float frustum_length, float fov) {
		float aspectRatio = x_scale / y_scale;
		
		
		Matrix4f projectionMatrix = new Matrix4f().InitPerspective(fov, aspectRatio, zNear, zFar);
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 =-((farPlane + nearPlane) / frustum_length);
		projectionMatrix.m23 =-1;
		projectionMatrix.m32 = -((2*nearPlane*farPlane) / frustum_length);
		projectionMatrix.m33 = 0;
		// TODO Auto-generated method stub
		return projectionMatrix;
	}*/
}
