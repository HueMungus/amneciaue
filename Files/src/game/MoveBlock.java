package game;

public class MoveBlock extends Block {
	Vec2 pos;
	Vec2 v;
	
	
	/**
	 * Makes a MoveBlock with position:
	 * @param pos
	 * and with velocity:
	 * @param v
	 */
	public MoveBlock(Vec2 pos, Vec2 v) {
		this.pos = pos;
		this.v = v;
	}
	
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
	}
	
	/**
	 * Makes a no-velocity MoveBlock with position:
	 * @param pos
	 */
	public MoveBlock(Vec2 pos) {
		this.pos = pos;
		this.v = new Vec2();
	}
	
	/**
	 * Makes a no-velocity MoveBlock with position:
	 * @param x
	 * with:
	 * @param y
	 */
	public MoveBlock(int x, int y) {
		this.pos = new Vec2(x,y);
		this.v = new Vec2();
	}
	
	public MoveBlock() { this.pos = new Vec2(); this.v = new Vec2(); }
	
	@Override
	public void onHit() {
		
	}
	
}
