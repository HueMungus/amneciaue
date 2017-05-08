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
	GameLevel gLevel;
	GameLevel testLevel = new GameLevel("testlevel", true);
	Level level;
	Level menu;
	Level othermenu;
	boolean physicsOn, renderOn, removeOn, addOn; //Booleans that determine whether a 'system' should be used or not
	static int frameWidth = 600, frameHeight = 600;
	
	JFrame mFrame;
	JPanel mPanel;
	Graphics2D Graphics;
	
	Image menuImage, otherMenuImage;

	int windowWidth, windowHeight;

	@SuppressWarnings("serial")
	public Engine() {
//		Import menu.jpg
		try {
			menuImage = ImageIO.read(new File(System.getProperty("user.dir") + "/src/" + "menu.jpg"));
		} catch (IOException e) {
			System.out.println("bad image read:");
			System.out.println(System.getProperty("user.dir") + "/src/" + "menu.jpg");
		}
//		Import othermenu.jpg
		try {
			otherMenuImage = ImageIO.read(new File(System.getProperty("user.dir") + "/src/" + "othermenu.jpg"));
		} catch (IOException e) {
			System.out.println("bad image read:");
			System.out.println(System.getProperty("user.dir") + "/src/" + "othermenu.jpg");
		}
		mFrame = new JFrame();
		mPanel = new JPanel();
		mFrame.setTitle("cool game");
		mFrame.setSize(frameWidth, frameHeight);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setLocationRelativeTo(null);
		menu = new Level() {
			@Override
			public void Left() {
				this.Right();
			}
			@Override
			public void Right() {
				gLevel = testLevel;
				level = testLevel;
				physicsOn = true;
			}
			@Override
			public void AltLeft() {
				level = othermenu;
			}
			@Override
			public void AltRight() {
				level = othermenu;
			}
		};
		menu.add(new Block(menuImage, 600, 600));
		othermenu = new Level() {
			@Override
			public void Left() {  }
			@Override
			public void Right() {  }
			@Override
			public void AltLeft() {
				level = menu;
			}
			@Override
			public void AltRight() {
				level = menu;
			}
		};
		othermenu.add(new Block(otherMenuImage, 600, 600));
		level = menu;
//					Panel Key binds
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK, true), "alt left");
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK, true), "alt right");
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");
		mPanel.getActionMap().put("alt left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.AltLeft();
				System.out.println("AltLeft");
				
			}

		});
		mPanel.getActionMap().put("left", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.Left();
				System.out.println("Left");
			}

		});
		mPanel.getActionMap().put("alt right", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.AltRight();
				System.out.println("AltRight");
			}

		});
		mPanel.getActionMap().put("right", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.Right();
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
//		System.out.println("Updating...");
		if (physicsOn) {
//			System.out.println("Physics-ing...");
			Collisions(); // Collisions only work on GameLevels, but GameLevel test is done in this method
		}
		
		/*if (removeOn) {
			
		}
		if (addOn) {
			
		}*/
		
		if (renderOn) {
//			System.out.println("Rendering...");
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
		for (Block block : level.parts) {
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
		if (level.getClass().equals(GameLevel.class)) {
//			Physics
		}
	}

	@Override
	public void paintComponent() {

	}


}
