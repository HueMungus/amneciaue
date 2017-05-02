package game;

import java.util.ArrayList;

public class Engine extends GameEngine {
//	 In future if multi-threaded, this will be the renderer, another class will be the physics engine
//	 As such update schedule will be 
//	Physics - not done
//	Render - working on it
//	Remove needed (from toRemove array) - not done
//	Add needed (from toAdd array) - not done
//	
//	Checklist: (other than above)
//	Constructor - not done
//	Set up screen clearing - not done - Current Project!
//	Getting keyboard/mouse input - not done
//	Debugging 'Checkpoints' - not done
//	
//	Levels - not done
//	
//	
//	
	
	// Goes through each array at the end of update loop
	ArrayList<Block> toAdd = new ArrayList<Block>();
	ArrayList<Block> toRemove = new ArrayList<Block>();
	ArrayList<Block> currentBlocks = new ArrayList<Block>();
	
	int windowWidth, windowHeight;
	
	public Engine(int windowWidth, int windowHeight) {
		
		
		
		
	}
	
	public static void main(String[] args) {
		Engine steve = new Engine(500, 500);
	}
	
	@Override
	public void update(double dt) {
//		Physics here, not in paintComponent()
		

	}
	
	public void Render() {
//		Rendering
//		Flush screen
		mGraphics.cl
		
		for (Block block : currentBlocks) {
			mGraphics.setColor(block.color);
			mGraphics.fillRect(block.x(), block.y(), block.width(), block.height());
		}
	}

	@Override
	public void paintComponent() {

	}

}
