import java.awt.geom.AffineTransform;

public class Transform extends AffineTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void rotate(double theta) {
		/**
		 * Rotates the Transform by theta DEGREES
		 */
		super.rotate(Math.toRadians(theta));
	}
	/**
	 * Rotates the Transform by theta radians
	 */
	public void rotateRad(double theta) {
		super.rotate(theta);
	}

	/**
	 * <p>
	 * Concatenates this transform with a transform that rotates co-ordinates based on the rotation vector
	 * </p>
	 */
	public void rotate(Vec2 a) {
		super.rotate(a.x, a.y);
	}
	
	/**
	 * @param theta
	 * @param anchor
	 */
	public void rotate(double theta, Vec2 anchor) {
		super.rotate(Math.toRadians(theta), anchor.x, anchor.y);
	}

	/**
	 * Concatenates this transform with a Transform rotated by a rotation Vector around an anchor point
	 */
	public void rotate(Vec2 rotation, Vec2 anchor) {
		super.rotate(rotation.x, rotation.y, anchor.x, anchor.y);
	}
	
}
