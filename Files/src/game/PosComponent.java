package game;

import javax.swing.JComponent;

public class PosComponent extends Vec2 {
	public JComponent content;
	
	public PosComponent(JComponent content, Vec2 pos) {
		this.content = content;
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public PosComponent(JComponent content, int x, int y) {
		this.content = content;
		this.x = x;
		this.y = y;
	}
	
	public PosComponent(JComponent content, float x, float y) {
		this.content = content;
		this.x = x;
		this.y = y;
	}
	
}
