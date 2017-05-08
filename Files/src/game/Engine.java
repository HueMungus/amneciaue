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
	Level menu;
	Level othermenu;
	boolean physicsOn, renderOn, removeOn, addOn; //Booleans that determine whether a 'system' should be used or not
	static int frameWidth = 600, frameHeight = 600;
	
	JFrame mFrame;
	JPanel mPanel;
	Insets insets;
	Graphics2D Graphics;
	
	Image menuImage, otherMenuImage;

	int windowWidth, windowHeight;

	@SuppressWarnings("serial")
	public Engine() {
		menuImage = importImage("menu.jpg");					// Import the potato jpg image
		otherMenuImage = importImage("othermenu.jpg");			// Import the jam jpg image
		
		mFrame = new JFrame(); 									// Initialize the mFrame (which is the window)
		mPanel = new JPanel();									// Initialize the mPanel (which is like the 'canvas' that goes in the window)
		mFrame.setTitle("cool game daddy");						// #Originality
		mFrame.setSize(frameWidth, frameHeight);				// Set the size of the window
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Makes the window close when you click the 'X' or right click on program tray
		mFrame.setLocationRelativeTo(null);						// Positions window in center of screen (null means center of screen)
		menu = new Level() {									// menu is the first Level that is visible on launch, currently a potato picture
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
				currentLevel = othermenu;
			}
			@Override
			public void AltRight() {
				currentLevel = othermenu;
			}
		};
		menu.add(new Block(menuImage, 600, 600));				// Adds a Block object with menuImage (potato) and unique (not percent of screen size) dimensions of 600x600 pixels
		othermenu = new Level() {								// othermenu is the second Level that currently made visible by pressing Alt+Left/Right on the menu Level
			@Override											// It also contains a picture of jam
			public void Left() {  }
			@Override
			public void Right() {  }
			@Override
			public void AltLeft() {
				currentLevel = menu;							// This changes the currentLevel back to menu (potato) when pressing Alt+Left/Right
			}
			@Override
			public void AltRight() {
				currentLevel = menu;							// Same as AltLeft()
			}
		};
		othermenu.add(new Block(otherMenuImage, 600, 600));		// Block that contains otherMenuImage (jam) and unique dimensions of 600x600 pixels
		currentLevel = menu;									// Make currentLevel menu/potato Level
//					Panel Key binds
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK, true), "alt left");		// AltLeft keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");								// Left keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK, true), "alt right");	// AltRight keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");								// Right keybind code
		mPanel.getActionMap().put("alt left", new AbstractAction() {														// AltLeft action code (what the AltLeft keybind does)

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.AltLeft();
				System.out.println("AltLeft");
				
			}

		});
		mPanel.getActionMap().put("left", new AbstractAction() {															// Left action code (what the Left keybind does)

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.Left();
				System.out.println("Left");
			}

		});
		mPanel.getActionMap().put("alt right", new AbstractAction() {														// AltRight action code (what the AltRight keybind does)

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.AltRight();
				System.out.println("AltRight");
			}

		});
		mPanel.getActionMap().put("right", new AbstractAction() {															// Right action code (what the Right keybind does)
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentLevel.Right();
				System.out.println("Right");
			}
			
		});
		mFrame.add(mPanel);									// Adding the Panel that will contain the stuff to the window
		mFrame.setVisible(true);							// You have to make it visible (invisible by default)

		renderOn = true;									// false by default
		loop.start();										// loop is the timer that calls the actionPerformed(ActionEvent e) every 50 milliseconds
		Graphics = (Graphics2D) mFrame.getGraphics();		// Used to render shit

	}
	
	@Override
	public void setupWindow(int width, int height) { }		// Overriden with nothing to prevent GameEngine code from being executed at all
	
	public Image importImage(String filename) {				// Condenses image import code into a smaller method
		Image bob = null;
		try {
			bob = ImageIO.read(new File(System.getProperty("user.dir") + "/src/" + filename));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error importing image \"" + filename + "\"");
		}
		return bob;
	}

	public static void main(String[] args) {				// Everything still needs a main method
		Engine steve = new Engine();
		steve.getTime();									// This serves no purpose except getting rid of the 'steve not used locally' warning
	}

	@Override
	public void update(double dt) {	}						// Overriden from GameEngine, this method is abstract so must be overriden
	
	@Override
	public void actionPerformed(ActionEvent e) {			// Main loop
//		System.out.println("Updating...");
		if (physicsOn) {
//			System.out.println("Physics-ing...");
			Collisions();
		}
		
		if (removeOn) {
			
		}
		if (addOn) {
			
		}
		
		if (renderOn) {
//			System.out.println("Rendering...");
			Render();
		}
	}

	public void Render() {
		//		Rendering
		//		Flush screen
		Graphics.clearRect(0, 0, mFrame.getWidth(), mFrame.getHeight());

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
