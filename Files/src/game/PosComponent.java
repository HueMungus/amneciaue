package game;

import javax.swing.JComponent;

public class PosComponent extends Vec2 {
	public JComponent content;
	
	public PosComponent(JComponent content, Vec2 pos) {
		this.content = content;
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public PosComponent(JComponent content) {
		this.content = content;
		this.x = 0;
		this.y = 0;
	}
	
	public PosComponent(JComponent content, float x, float y) {
		this.content = content;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets this PosComponent's position using percentage,
	 * Values can be between 0 and 1, 1 being the right/down-most position
	 * it can be, 0 being the left/up-most position it can be
	 * @param x
	 * @param y
	 */
	public void setPos(float x, float y) {
		if (x <= 1.0f && x >= 0.0f && y <= 1.0f && y >= 0.0f) {
			this.x = x;
			this.y = y;
		} else {
			this.x = 1.0f;
			this.y = 1.0f;
		}
	}
}
