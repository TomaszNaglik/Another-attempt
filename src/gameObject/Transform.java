package gameObject;

import org.joml.*;

import math.Maths;
//import org.lwjgl.util.vector.Vector3f;

//import math.*;

public class Transform {

	private Vector3f m_pos;
	private Quaternionf m_rot;
	private Vector3f m_scale;

	private Vector3f m_oldPos;
	private Quaternionf m_oldRot;
	private Vector3f m_oldScale;
	
	private Vector3f right;
	private Vector3f up;
	private Vector3f forward;

	private Transform m_parent;
	private Matrix4f m_parentMatrix;

	public Transform() {
		m_pos = new Vector3f(0, 0, 0);
		m_rot = new Quaternionf(0, 0, 0, 1);
		m_scale = new Vector3f(1, 1, 1);
		right = new Vector3f(1, 0, 0);
		up = new Vector3f(0, 1, 0);
		forward = new Vector3f(0, 0, 1);

		m_parentMatrix = new Matrix4f();
	}

	public void Rotate(Vector3f axis, float angle) {

		m_rot.rotateAxis(angle, axis);
	}

	public boolean HasChanged() {
		if (m_parent != null && m_parent.HasChanged())
			return true;

		if (!m_pos.equals(m_oldPos))
			return true;

		if (!m_rot.equals(m_oldRot))
			return true;

		if (!m_scale.equals(m_oldScale))
			return true;

		return false;
	}

	public Matrix4f GetTransformation() {
		return Maths.createTransformationMatrix(this);
	}

	private Matrix4f GetParentMatrix() {
		if (m_parent != null && m_parent.HasChanged())
			m_parentMatrix = m_parent.GetTransformation();

		return m_parentMatrix;
	}

	public void SetParent(Transform parent) {
		this.m_parent = parent;
	}

	/*public Vector3f GetTransformedPos() {
		return GetParentMatrix().Transform(m_pos);
	}*/

	/*public Quaternion GetTransformedRot() {
		Quaternion parentRotation = new Quaternion(0, 0, 0, 1);

		if (m_parent != null)
			parentRotation = m_parent.GetTransformedRot();

		return parentRotation.Mul(m_rot);
	}*/

	public Vector3f GetPos() {
		return m_pos;
	}

	public void SetPos(Vector3f pos) {
		this.m_pos = pos;
	}

	public Quaternionf GetRot() {
		return m_rot;
	}

	public void SetRot(Quaternionf rotation) {
		this.m_rot = rotation;
	}

	public Vector3f GetScale() {
		return m_scale;
	}

	public void SetScale(Vector3f scale) {
		this.m_scale = scale;
	}

	public void SetScale(float scale) {
		this.m_scale = new Vector3f(scale, scale, scale);

	}

	public Vector3f GetRightAxis() {
		m_rot.positiveX(right);
		return right;
	}
	public Vector3f GetUpAxis() {
		m_rot.positiveY(up);
		return up;
	}
	public Vector3f GetForwardAxis() {
	m_rot.positiveY(forward);
	return forward;
}

}
