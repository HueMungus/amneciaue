package game;

public class StaticBlock extends Block {
	Vec2 pos;
	
	public StaticBlock(Vec2 pos) { this.pos = pos; }
	
	public StaticBlock(int x, int y) { this.pos = new Vec2(x,y); }
	
	public StaticBlock() { this.pos = new Vec2(); }
	
}
