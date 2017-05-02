package game;

import java.awt.Color;

public class Block {
	Color color;
	
	Vec2 min, dim; // dim = dimension, width and height are stored here
	
	public void onHit() {
		
	}
	
	public Block() {
		this.min = new Vec2(0,0);
		this.dim = new Vec2(0,0);
	}
	
	public Block(int x, int y, int width, int height) {
		
		this.min = new Vec2(x, y);
		this.dim = new Vec2(x + width, y + height);
	}
	
	public Block(float x, float y, float width, float height) {
		this.min = new Vec2(x, y);
		this.dim = new Vec2(x + width, y + height);
	}
	
	public int x() {
		return (int) min.x;
	}
	
	public int y() {
		return (int) min.y;
	}
	
	public int width() {
		return (int) dim.x;
	}
	
	public int height() {
		return (int) dim.y;
	}
	
	/**
	 * Converts this Block into a MoveBlock
	 * uses simple cast
	 * @return
	 */
	public static MoveBlock newMove() {
		return new MoveBlock();
	}
	
	/**
	 * Converts this Block into a StaticBlock
	 * uses simple cast
	 * @return
	 */
	public static StaticBlock newStatic() {
		return new StaticBlock();
	}
}
