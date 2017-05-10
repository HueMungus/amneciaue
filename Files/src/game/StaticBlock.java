package game;

public class StaticBlock extends Block {

	public StaticBlock(int x, int y) { 
		this.pos = new Vec2(x,y);
		this.color = Engine.SBColor;
	}
	
	public StaticBlock(Vec2 pos) { this((int)pos.x, (int)pos.y); }
	
	public StaticBlock() { this(0,0); }
	
}
