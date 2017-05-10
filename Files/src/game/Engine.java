package game;

// Martin Johnson (Gave dev lecturer) said it was fine having multiple java files

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Engine extends GameEngine implements ActionListener, MouseMotionListener {
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
//	Color stuff
	public static Color MBColor = Color.BLUE;
	public static Color SBColor = Color.GRAY;
	public static Color BColor = Color.PINK;
	public static Color FColor = Color.RED;
//	Level stuff
	GameLevel gLevel;
	GameLevel testLevel = new GameLevel("testlevel.txt", true);
	Level level;
	Level menu;
	Level othermenu;
//	Render/Physics stuff
	boolean physicsOn, renderOn, removeOn, addOn; //Booleans that determine whether a 'system' should be used or not
	long prevdt = System.currentTimeMillis(), dt = 0;
	Timer loop = new Timer(50, this);
//	Window stuff
	static int frameWidth = 600, frameHeight = 600;
	JFrame mFrame;
	JPanel mPanel;
	Graphics2D Graphics;
//	Image stuff
	Image menuImage, otherMenuImage;

	@SuppressWarnings("serial")
	public Engine() {
		menuImage = importImage("menu.jpg");					// Import the potato jpg image
		otherMenuImage = importImage("othermenu.jpg");			// Import the jam jpg image
		
		mFrame = new JFrame(); 									// Initialize the mFrame (which is the window)
		mPanel = new JPanel();									// Initialize the mPanel (which is like the 'canvas' that goes in the window)
		mFrame.setTitle("PewDiePieSimulator");						// #Originality
		mFrame.setSize(frameWidth, frameHeight);				// Set the size of the window
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Makes the window close when you click the 'X' or right click on program tray
		mFrame.setLocationRelativeTo(null);						// Positions window in center of screen (null means center of screen)
		menu = new Level() {									// menu is the first Level that is visible on launch, currently a potato picture
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
		menu.add(new Block(menuImage, 600, 600));				// Adds a Block object with menuImage (potato) and unique (not percent of screen size) dimensions of 600x600 pixels
		othermenu = new Level() {								// othermenu is the second Level that currently made visible by pressing Alt+Left/Right on the menu Level
			@Override											// It also contains a picture of jam
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
		othermenu.add(new Block(otherMenuImage, 600, 600));		// Block that contains otherMenuImage (jam) and unique dimensions of 600x600 pixels
		level = menu;											// Make current level menu/potato Level
//					Panel Key binds
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK, true), "alt left");		// AltLeft keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left");								// Left keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK, true), "alt right");	// AltRight keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right");								// Right keybind code
		mPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "space");
		mPanel.getActionMap().put("alt left", new AbstractAction() {														// AltLeft action code (what the AltLeft keybind does)

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.AltLeft();
				System.out.println("AltLeft");
				
			}

		});
		mPanel.getActionMap().put("left", new AbstractAction() {															// Left action code (what the Left keybind does)

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.Left();
				System.out.println("Left");
			}

		});
		mPanel.getActionMap().put("alt right", new AbstractAction() {														// AltRight action code (what the AltRight keybind does)

			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.AltRight();
				System.out.println("AltRight");
			}

		});
		mPanel.getActionMap().put("right", new AbstractAction() {															// Right action code (what the Right keybind does)
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				level.Right();
				System.out.println("Right");
			}
			
		});
		mPanel.getActionMap().put("space", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Space");
				if (gLevel != null) {
					System.out.println("Physics: on, focus at: " + gLevel.focus.pos.toString());
					gLevel.physicsOn = true;
				}
			}
		});
//		mPanel.setDoubleBuffered(true);
		mFrame.add(mPanel);									// Adding the Panel that will contain the stuff to the window
		mFrame.setVisible(true);							// You have to make it visible (invisible by default)

		renderOn = true;									// false by default
		loop.start();										// loop is the timer that calls the actionPerformed(ActionEvent e) every 50 milliseconds
		Graphics = (Graphics2D) mFrame.getGraphics();		// Used to render shit
		Graphics.setBackground(Color.black);
		
		for (Block bob : testLevel.parts) {
			System.out.println("Block with " +bob.x() + ", " + bob.y() + " with dimensions " + bob.width() + ", " + bob.height());
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {			// Main loop
		long temptime = System.currentTimeMillis();
		dt = temptime - prevdt;
		prevdt = temptime;
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
		Graphics.clearRect(0, 0, mFrame.getWidth(), mFrame.getHeight());

		//		Render Blocks
		for (Block block : level.parts) {
			if (block.hasImage) {
//				If it has an image, draw it, I'm assuming if hasImage is true, its Image won't be null
				Graphics.drawImage(block.image, block.x(), block.y(), block.width(), block.height(), null);
			} else {
//				Otherwise it should be using a color, so draw a rectangle of that color
				Graphics.setColor(block.color);
//				System.out.println("Rendering a block with color: " + block.color.toString());
				Graphics.fillRect(block.x(), block.y(), block.width(), block.height());
//				System.out.println("Using coords " + block.x() + ", " + block.y() + " with dimensions " + block.width() + ", " + block.height());
			}
		}
		if (gLevel != null) {
			Graphics.setColor(FColor);
			Graphics.fillRect(gLevel.focus.x(), gLevel.focus.y(), gLevel.focus.width(), gLevel.focus.height());
		}
	}
	
	public void Collisions() {
		// Checking if the current level of type Level is the same object as the current level of type GameLevel (yeah its annoying)
		if (level.getClass().equals(GameLevel.class)) {
//			Physics
//			First check if the current level's physicsOn boolean is true, otherwise do nothing (inMotion will be set to true when a block is supposed to fall)
			if (gLevel.physicsOn) {
//				First move the focused block by the gravity vector multiplied by dt
				gLevel.focus.pos = gLevel.focus.pos.add(gLevel.Gravity.mult(((float) dt) / 1000));
//				Second check other objects
				Vec2 penetration;
				for (Block A : gLevel.parts) {
//					Will be null if no collision
					if ((penetration = FocusCollidesWith(A)) != null) {
						System.out.println("Resolving Collision...");
//						Resolve
						if (penetration.x > 0) {
//							If penetration is on the X
							if (A.pos.x > gLevel.focus.pos.x) {
//								If A is further on the X than B (focus)
								System.out.println("Checkpoint B2: Subtracting penetration vector on the X");
								gLevel.focus.pos = gLevel.focus.pos.minus(penetration);
								System.out.println("Focus at: " + gLevel.focus.pos.toString());
							} else {
//								If B (focus) is further on the X than A
								System.out.println("Checkpoint A1: Adding penetration vector on the X");
								gLevel.focus.pos = gLevel.focus.pos.add(penetration);
								System.out.println("Focus at: " + gLevel.focus.pos.toString());
							}
						} else {
//							If penetration is on the Y
							if (A.pos.y > gLevel.focus.pos.y) {
//								If A is further on the y than B (focus)
								System.out.println("Checkpoint D4: Subtracting penetration vector on the Y");
								gLevel.focus.pos = gLevel.focus.pos.minus(penetration);
								System.out.println("Focus at: " + gLevel.focus.pos.toString());
							} else {
//								If B (focus) is further on the y than A
								System.out.println("Checkpoint C3: Adding penetration vector on the Y");
								gLevel.focus.pos = gLevel.focus.pos.add(penetration);
								System.out.println("Focus at: " + gLevel.focus.pos.toString());
							}
						}
						return;
					}
				}
				
//				Lastly check world bounds
//				Only check vertical (top & bottom) world bounds if verticalGravity is true, otherwise check horizontal (left & right) world bounds
				if (gLevel.verticalGravity) {
//					if the focused block's y position is less than 0
//					turn physics off, set its y position to 0, and return
					if (gLevel.focus.pos.y < 0) {
						gLevel.physicsOn = false;
						gLevel.focus.pos.y = 0;
						return;
					}
//					if the focused block's x position + its width is greater than the window's size
//					turn physics off, set its position to the window's width - focused block's width, and return
					if (gLevel.focus.pos.y + gLevel.focus.height() > mFrame.getHeight()) {
						gLevel.physicsOn = false;
						gLevel.focus.pos.y = mFrame.getHeight() - gLevel.focus.height();
						return;
					}
				} else {
//					if the focused block's x position is less than 0
//					turn physics off, set its position to 0, and return
					if (gLevel.focus.pos.x < 0) {
						gLevel.physicsOn = false;
						gLevel.focus.pos.x = 0;
						return;
					}
//					if the focused block's x position + its width is greater than the window's size
//					turn physics off, set its position to the window's width - focused block's width, and return
					if (gLevel.focus.pos.x + gLevel.focus.width() > mFrame.getWidth()) {
						gLevel.physicsOn = false;
						gLevel.focus.pos.x = mFrame.getWidth() - gLevel.focus.width();
					}
					
				}
			}
		}
	}
	
//	Used to test if one block is inside another
//	Returns null if they didn't, otherwise returns movement vector that can be used to move the block out
	public Vec2 FocusCollidesWith( Block A ) {
		Block B = gLevel.focus;
		if (A == B) {
			return null;
		}
		Vec2 Amax = new Vec2(A.pos.x + A.width(), A.pos.y + A.height());
		Vec2 Bmax = new Vec2(B.pos.x + B.width(), B.pos.y + B.height());
		System.out.println(Amax.toString() + " and " + Bmax.toString());
		
		// Vector from A to B
		Vec2 n = B.pos.minus(A.pos);
		
		// Calculate half extents along x axis for each object
		float a_extent = (Amax.x - A.pos.x) / 2;
		float b_extent = (Bmax.x - B.pos.x) / 2;

		// Calculate overlap on x axis
		float x_overlap = a_extent + b_extent - Math.abs( n.x ); 

		// SAT test on  axis
		if (x_overlap > 0)
		{
			// Calculate overlap on y axis
			float y_overlap = a_extent + b_extent - Math.abs( n.y );

			// SAT test on y axis
			if(y_overlap > 0)
			{
				// Find out which axis is axis of least penetration
				if(x_overlap > y_overlap)
				{
					// Point towards B knowing that n points from A to B
					if(!(n.x < 0)) {
						return new Vec2(0, y_overlap);
					}
				}
				else
				{
					// Point toward B knowing that n points from A to B
					if(!(n.y < 0))
					return new Vec2(x_overlap, 0);
				}
			}
		}
		return null;
	}

	@Override
	public void setupWindow(int width, int height) { }		// Overriden with nothing to prevent GameEngine code from being executed at all
	
	public static Image importImage(String filename) {				// Condenses image import code into a smaller method
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
	public void paintComponent() {  } //Overriden from GameEngine, method is abstract so must be overriden
	
}

//public Vec2 FocusCollidesWith( Block A ) {
//	Block B = gLevel.focus;
//	if (A == B) {
//		return null;
//	}
//	Vec2 Amax = new Vec2(A.pos.x + A.width(), A.pos.y + A.height());
//	Vec2 Bmax = new Vec2(B.pos.x + B.width(), B.pos.y + B.height());
//	System.out.println(Amax.toString() + " and " + Bmax.toString());
//	
//	// Vector from A to B
//	Vec2 n = B.pos.minus(A.pos);
//	
//	// Calculate half extents along x axis for each object
//	float a_extent = (Amax.x - A.pos.x) / 2;
//	float b_extent = (Bmax.x - B.pos.x) / 2;
//
//	// Calculate overlap on x axis
//	float x_overlap = a_extent + b_extent - Math.abs( n.x ); 
//
//	// SAT test on  axis
//	if (x_overlap > 0)
//	{
//		// Calculate overlap on y axis
//		float y_overlap = a_extent + b_extent - Math.abs( n.y );
//
//		// SAT test on y axis
//		if(y_overlap > 0)
//		{
//			// Find out which axis is axis of least penetration
//			if(x_overlap > y_overlap)
//			{
//				// Point towards B knowing that n points from A to B
//				if(!(n.x < 0)) {
//					return new Vec2(x_overlap, 0);
//				}
//			}
//			else
//			{
//				// Point toward B knowing that n points from A to B
//				if(!(n.y < 0))
//				return new Vec2(0, y_overlap);
//			}
//		}
//	}
//	return null;
//}

//public void Collisions() {
//	// Checking if the current level of type Level is the same object as the current level of type GameLevel (yeah its annoying)
//	if (level.getClass().equals(GameLevel.class)) {
////		Physics
////		First check if the current level's physicsOn boolean is true, otherwise do nothing (inMotion will be set to true when a block is supposed to fall)
//		if (gLevel.physicsOn) {
////			First move the focused block by the gravity vector multiplied by dt
//			gLevel.focus.pos = gLevel.focus.pos.add(gLevel.Gravity.mult(((float) dt) / 1000));
////			Second check other objects
//			Vec2 penetration;
//			for (Block A : gLevel.parts) {
////				Will be null if no collision
//				if ((penetration = FocusCollidesWith(A)) != null) {
//					System.out.println("Resolving Collision...");
////					Resolve
//					if (penetration.x > 0) {
////						If penetration is on the X
//						if (A.pos.x > gLevel.focus.pos.x) {
////							If A is further on the X than B (focus)
//							System.out.println("Checkpoint A1: Adding penetration vector on the X");
//							gLevel.focus.pos = gLevel.focus.pos.add(penetration);
//							System.out.println("Focus at: " + gLevel.focus.pos.toString());
//						} else {
////							If B (focus) is further on the X than A
//							System.out.println("Checkpoint B2: Subtracting penetration vector on the X");
//							gLevel.focus.pos = gLevel.focus.pos.minus(penetration);
//							System.out.println("Focus at: " + gLevel.focus.pos.toString());
//						}
//					} else {
////						If penetration is on the Y
//						if (A.pos.y > gLevel.focus.pos.y) {
////							If A is further on the y than B (focus)
//							System.out.println("Checkpoint C3: Adding penetration vector on the Y");
//							gLevel.focus.pos = gLevel.focus.pos.add(penetration);
//							System.out.println("Focus at: " + gLevel.focus.pos.toString());
//						} else {
////							If B (focus) is further on the y than A
//							System.out.println("Checkpoint D4: Subtracting penetration vector on the Y");
//							gLevel.focus.pos = gLevel.focus.pos.minus(penetration);
//							System.out.println("Focus at: " + gLevel.focus.pos.toString());
//						}
//					}
//					return;
//				}
//			}
//			
////			Lastly check world bounds
////			Only check vertical (top & bottom) world bounds if verticalGravity is true, otherwise check horizontal (left & right) world bounds
//			if (gLevel.verticalGravity) {
////				if the focused block's y position is less than 0
////				turn physics off, set its y position to 0, and return
//				if (gLevel.focus.pos.y < 0) {
//					gLevel.physicsOn = false;
//					gLevel.focus.pos.y = 0;
//					return;
//				}
////				if the focused block's x position + its width is greater than the window's size
////				turn physics off, set its position to the window's width - focused block's width, and return
//				if (gLevel.focus.pos.y + gLevel.focus.height() > mFrame.getHeight()) {
//					gLevel.physicsOn = false;
//					gLevel.focus.pos.y = mFrame.getHeight() - gLevel.focus.height();
//					return;
//				}
//			} else {
////				if the focused block's x position is less than 0
////				turn physics off, set its position to 0, and return
//				if (gLevel.focus.pos.x < 0) {
//					gLevel.physicsOn = false;
//					gLevel.focus.pos.x = 0;
//					return;
//				}
////				if the focused block's x position + its width is greater than the window's size
////				turn physics off, set its position to the window's width - focused block's width, and return
//				if (gLevel.focus.pos.x + gLevel.focus.width() > mFrame.getWidth()) {
//					gLevel.physicsOn = false;
//					gLevel.focus.pos.x = mFrame.getWidth() - gLevel.focus.width();
//				}
//				
//			}
//		}
//	}
//}