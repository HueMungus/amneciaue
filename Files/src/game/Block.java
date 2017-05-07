package game;

import java.awt.Color;
import java.awt.Image;

public class Block {
	public boolean hasImage = false;
	public boolean uniqueCoords = false;
	public float uniqueWidth, uniqueHeight;
	public Image image;
	Color color = Engine.BColor;
	public static float bWidth, bHeight;
	
	Vec2 pos; // dim = dimension, width and height are stored here
	
	public void onHit() {
		
	}
	
	public Block() { this(0,0); }
	
	public Block(int x, int y) {	
		this.pos = new Vec2(x, y);
	}
	
	public Block(int x, int y, Image image) {
		this(x,y);
		if (image != null) {
			this.hasImage = true;
			this.image = image;
		} else {
			System.out.println("block made with null image");
		}
	}
	
	public Block(float x, float y, Image image) { this((int) x,(int) y,image); }
	public Block(Image image) {	this(0,0,image); }
	public Block(float x, float y) { this((int) x,(int) y); }
	
	public int x() {
		return (int) pos.x;
	}
	
	public int y() {
		return (int) pos.y;
	}
	
	public int width() {
		if (uniqueCoords) {
			return (int) uniqueWidth;
		}
		return (int) bWidth;
	}
	
	public int height() {
		if (uniqueCoords) {
			return (int) uniqueHeight;
		}
		return (int) bHeight;
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
