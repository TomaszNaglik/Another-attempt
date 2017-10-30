package math;



import java.nio.FloatBuffer;

//import org.lwjgl.util.vector.Matrix4f;
//import org.lwjgl.util.vector.Vector3f;
//import org.lwjgl.util.vector.Vector4f;
import org.joml.*;
import org.joml.Math;
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
	
	public static Matrix4f initCamera(Vector3f forward, Vector3f up)
	{
		Vector3f f = new Vector3f(forward);
		f.normalize();
		
		Vector3f r = new Vector3f(up);
		r.normalize();
		r=r.cross(f);
		
		Vector3f u = new Vector3f(f);
		u.cross(r);
		Matrix4f m = new Matrix4f();
		
		
		m.set(r.x, r.y, r.z, 0, u.x, u.y, u.z, 0, f.x, f.y, f.z, 0, 0, 0, 0, 1);
		
		return m;
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f cameraRotation = initCamera(camera.getTransform().getForward(),camera.getTransform().getUp());
		Matrix4f cameraTranslation = new Matrix4f().translate(-camera.getTransform().GetPos().x,-camera.getTransform().GetPos().y,-camera.getTransform().GetPos().z);
		
		return cameraRotation.mul(cameraTranslation);
		/*Quaternionf rotation = new Quaternionf(camera.getTransform().GetRot());
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
		*/
		
	}

	public static Matrix4f createPerspectiveMatrix(float fov, float aspectRatio, float zNear, float zFar) {
		return new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
	}
	
	public static FloatBuffer CreateFlippedBuffer(Matrix4f matrix) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		return matrix.get(buffer);
	}
	
	public static Quaternionf rotateAroundAxis(Vector3f d, float angle) {
		d.normalize();
		float a = (float) Math.toRadians(angle);
		float sin = (float) Math.sin(a/2);
		float cos = (float) Math.cos(a/2);
		return new Quaternionf( sin*d.x,sin*d.y,sin*d.z, cos);
	}
}
