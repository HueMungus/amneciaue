package game;

import java.awt.Color;

public class MoveBlock extends Block {
	Vec2 v;
	
	/**
	 * Makes MoveBlock with position:
	 * @param x
	 * and:
	 * @param y
	 * and velocity:
	 * @param vx
	 * with:
	 * @param vy
	 * 
	 */
	public MoveBlock(int x, int y, int vx, int vy) {
		this.pos = new Vec2(x,y);
		this.v = new Vec2(vx,vy);
		this.color = Engine.MBColor;
	}
	
	/**
	 * Makes a MoveBlock with position:
	 * @param pos
	 * and with velocity:
	 * @param v
	 */
	public MoveBlock(Vec2 pos, Vec2 v) { this((int)pos.x, (int)pos.y, (int)v.x, (int)v.y); }
	
	/**
	 * Makes a no-velocity MoveBlock with position:
	 * @param pos
	 */
	public MoveBlock(Vec2 pos) { this((int)pos.x, (int)pos.y, 0,0); }
	
	/**
	 * Makes a no-velocity MoveBlock with position:
	 * @param x
	 * with:
	 * @param y
	 */
	public MoveBlock(int x, int y) { this(x,y,0,0); }
	
	public MoveBlock() { this(0,0,0,0); }
	
	@Override
	public void onHit() {
		
	}
	
}
