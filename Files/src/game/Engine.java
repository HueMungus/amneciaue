package game;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Engine extends GameEngine {
	//	 In future if multi-threaded, this will be the renderer, another class will be the physics engine
	//	 As such update schedule will be 
	//	Physics - not done
	//	Render - working on it
	//	Remove needed (from toRemove array) - not done
	//	Add needed (from toAdd array) - not done
	//	
	//	Checklist: (other than above)
	//	Constructor - in progress
	//	Set up screen clearing - done
	//	Getting keyboard/mouse input - in progress
	//	Debugging 'Checkpoints' - not done
	//	
	//	Levels - not done
	//	
	//	
	//	
	
	public static String programPosition = "At: ";
	
	// Goes through each array at the end of update loop
	ArrayList<Block> toAdd = new ArrayList<Block>();
	ArrayList<Block> toRemove = new ArrayList<Block>();
	ArrayList<Block> currentBlocks;
	
	Level currentLevel;

	JFrame mFrame;
	JPanel mPanel;
	Insets insets;

	int windowWidth, windowHeight;

	public Engine(int windowWidth, int windowHeight) {
		mFrame = new JFrame();
		mPanel = new JPanel();
		mFrame.setTitle("cool game");
		mFrame.setSize(windowWidth, windowHeight);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setLocationRelativeTo(null);
		currentLevel = new Level();
		//			Panel Keybinds
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK, true), "alt left");
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK, true), "alt right");
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");
		mPanel.getActionMap().put("alt left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.AltLeft();
				System.out.println("AltLeft");
				
			}

		});
		mPanel.getActionMap().put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.Left();
				System.out.println("Left");
			}

		});
		mPanel.getActionMap().put("alt right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.AltRight();
				System.out.println("AltRight");
			}

		});
		mPanel.getActionMap().put("right", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.Right();
				System.out.println("Right");
			}
			
		});
		
		
		
		mFrame.add(mPanel);
		mFrame.setVisible(true);

	}

	public static void main(String[] args) {
		Engine steve = new Engine(500, 500);
	}

	@Override
	public void update(double dt) {
		//		Physics here, not in paintComponent() (duh)


	}

	public void Render() {
		//		Rendering
		//		Flush screen
		mGraphics.clearRect(0, 0, mFrame.getWidth(), mFrame.getHeight());

		//		Render Blocks
		for (Block block : currentBlocks) {
			mGraphics.setColor(block.color);
			mGraphics.fillRect(block.x(), block.y(), block.width(), block.height());
		}
	}

	@Override
	public void paintComponent() {

	}

}
