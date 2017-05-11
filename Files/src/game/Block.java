package game;

import java.awt.Color;
import java.awt.Image;

public class Block {
	public boolean hasImage = false;
	public boolean uniqueCoords = false;
	public float uniqueWidth, uniqueHeight;
	public Image image;
	Color color = Engine.BColor;
	public static int ratio = 10;
	public static float bWidth = Engine.frameWidth / ratio, bHeight = Engine.frameHeight / ratio;
	
	Vec2 pos; // dim = dimension, width and height are stored here
	
	public void onHit() {
		
	}
	
	public Block() { this(0,0); }
	
	public Block(int x, int y) {	
		this.pos = new Vec2(x, y);
	}
	
	public Block(int x, int y, Color color) {
		this(x,y);
		this.color = color;
	}
	
	public Block(Vec2 pos, Color color) {
		this((int)pos.x,(int)pos.y,color);
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
	
	public Block(int x, int y, int width, int height) {
		this(x,y);
		this.uniqueCoords = true;
		this.uniqueWidth = width;
		this.uniqueHeight = height;
	}
	
	public Block(int x, int y, int width, int height, Image image) {
		this(x,y,width,height);
		if (image != null) {
			this.hasImage = true;
			this.image = image;
		} else {
			System.out.println("block made with null image");
		}
	}
	
	public Block(Image image, int width, int height) { this(0,0,width,height,image); }
	public Block(Image image) {	this(0,0,image); }
	
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
	
	public static class Static extends Block {
		public int hitCounter = 0;

		public Static(int x, int y) { 
			this.pos = new Vec2(x,y);
			this.color = Engine.SBColor;
		}
		
		public Static(Vec2 pos) { this((int)pos.x, (int)pos.y); }
		
		public Static() { this(0,0); }
		
	}
	
	public static class Move extends Block {
		Vec2 v;
		
		public Move(int x, int y, int vx, int vy) {
			this.pos = new Vec2(x,y);
			this.v = new Vec2(vx,vy);
			this.color = Engine.MBColor;
		}
		
		public Move(Vec2 pos, Vec2 v) { this((int)pos.x, (int)pos.y, (int)v.x, (int)v.y); }
		
		public Move(Vec2 pos, Vec2 v, Image image) { super((int) pos.x,(int) pos.y, image); this.v = v; }
		
		public Move(Vec2 pos) { this((int)pos.x, (int)pos.y, 0,0); }
		
		public Move(int x, int y) { this(x,y,0,0); }
		
		public Move() { this(0,0,0,0); }
		
		@Override
		public void onHit() {
			
		}
		
	}
}
