package game;

// Martin Johnson (Gave dev lecturer) said it was fine having multiple java files

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Engine extends GameEngine implements ActionListener {
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
	//	
	//	Levels - part done
	//	
	//	
	//	
	
	// Goes through each array at the end of update loop
	ArrayList<Block> toAdd = new ArrayList<Block>();
	ArrayList<Block> toRemove = new ArrayList<Block>();
	
	/**
	 * MoveBlock Color
	 */
	public static Color MBColor = Color.ORANGE;
	/**
	 * StaticBlock Color
	 */
	public static Color SBColor = Color.DARK_GRAY;
	/**
	 * Default Block Color
	 */
	public static Color BColor = Color.LIGHT_GRAY;
	Timer loop = new Timer(50, this);
	Level currentLevel;
	boolean physicsOn, renderOn, removeOn, addOn; //Booleans that determine whether a 'system' should be used or not
	static int frameWidth = 600, frameHeight = 600;
	
	JFrame mFrame;
	JPanel mPanel;
	Insets insets;
	Graphics2D Graphics;
	
	Image menuImage;

	int windowWidth, windowHeight;

	@SuppressWarnings("serial")
	public Engine() {
		try {
			menuImage = ImageIO.read(new File(System.getProperty("user.dir") + "/src/" + "menu.jpg"));
		} catch (IOException e) {
			System.out.println("bad image read:");
			System.out.println(System.getProperty("user.dir") + "/src/" + "menu.jpg");
		}
		mFrame = new JFrame();
		mPanel = new JPanel();
		mFrame.setTitle("cool game");
		mFrame.setSize(frameWidth, frameHeight);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setLocationRelativeTo(null);
		currentLevel = new Level() {
			@Override
			public void Left() {
//				Start game code here
			}
			@Override
			public void Right() {
//				Start game code here as well
			}
			@Override
			public void AltLeft() {
//				Options menu (if implemented ever) here
			}
			@Override
			public void AltRight() {
//				Options menu here also
			}
		};
		currentLevel.add(new Block(menuImage, 600, 600));
//					Panel Key binds
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

		renderOn = true;
		loop.start();
		Graphics = (Graphics2D) mFrame.getGraphics();

	}
	
	@Override
	public void setupWindow(int width, int height) { }

	public static void main(String[] args) {
		Engine steve = new Engine();
		steve.getTime();
	}

	@Override
	public void update(double dt) {	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Updating...");
		if (physicsOn) {
			System.out.println("Physics-ing...");
			Collisions();
		}
		
		if (removeOn) {
			
		}
		if (addOn) {
			
		}
		
		if (renderOn) {
			System.out.println("Rendering...");
			Render();
		}
	}

	public void Render() {
		//		Rendering
		//		Flush screen
		Graphics.
		clearRect(0, 0, 
				mFrame.getWidth(), mFrame.getHeight());

		//		Render Blocks
		for (Block block : currentLevel.parts) {
			if (block.hasImage) {
//				If it has image, draw it
				Graphics.drawImage(block.image, block.x(), block.y(), block.width(), block.height(), null);
			} else {
//				Otherwise it should be using a color, so draw a rectangle of that color
				Graphics.setColor(block.color);
				Graphics.fillRect(block.x(), block.y(), block.width(), block.height());
			}
		}
	}
	
	public void Collisions() {
		
	}

	@Override
	public void paintComponent() {

	}


}
