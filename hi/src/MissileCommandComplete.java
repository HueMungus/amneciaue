import java.awt.*;
import java.awt.event.*;

// Background music:
// Music: Sci-Fi - Bensound.com
// License: Creative Commons - Attribution - No Derivative Works
// http://creativecommons.org/licenses/by-nd/3.0/legalcode

public class MissileCommandComplete extends GameEngine {
	// Main Function
	public static void main(String args[]) {
		// Warning: Only call createGame in this function
		// Create a new MissileCommandComplete
		createGame(new MissileCommandComplete());
	}

	//-------------------------------------------------------
	// Game
	//-------------------------------------------------------
	boolean gameOver;
	AudioClip backgroundMusic;
	Image backgroundImage;

	//-------------------------------------------------------
	// Missile
	//-------------------------------------------------------
	int maxMissiles;
	double missileX[],  missileY[];
	double missileSX[], missileSY[];
	double missileVX[], missileVY[];
	double missileAngle[];

	Image missileImage;

	// Initialise Missile
	public void initMissile() {
		// Set Maximum number of missiles
		maxMissiles = 3;

		// Allocate Arrays
		missileX = new double[maxMissiles];
		missileY = new double[maxMissiles];

		missileSX = new double[maxMissiles];
		missileSY = new double[maxMissiles];

		missileVX = new double[maxMissiles];
		missileVY = new double[maxMissiles];
		missileAngle = new double[maxMissiles];

		// Load Image
		missileImage = loadImage("Images/missile.png");

		// Create Random Missiles
		for(int i = 0; i < maxMissiles; i++) {
			// New Missile
			newMissile(i);
		}
	}

	// New Missile
	public void newMissile(int i) {
		// Random Start Position
		missileSX[i] = rand(500);
		missileSY[i] = 0;

		// Set Missile Position
		missileX[i] = missileSX[i];
		missileY[i] = missileSY[i];

		// Random Target Position
		double tx = rand(500);
		double ty = 500;

		// Calculate velocity
		missileVX[i] = tx - missileSX[i];
		missileVY[i] = ty - missileSY[i];

		// Calculate angle
		missileAngle[i] = atan2(missileVX[i], -missileVY[i]) - 90;

		// Rescale velocity
		double l = length(missileVX[i], missileVY[i]);
		missileVX[i] *= 10 / l;
		missileVY[i] *= 10 / l;
	}

	// Update the Missile
	public void updateMissile(double dt) {
		// Update all Missiles
		for(int i = 0; i < maxMissiles; i++) {
			// Move Missile down
			missileX[i] += missileVX[i]*dt;
			missileY[i] += missileVY[i]*dt;

			// Check if missile has reached the bottom of the screen
			if(missileY[i] >= 500) {
				// Then the game is over
				gameOver = true;
			}
		}
	}

	// Draw the missile
	public void drawMissile() {
		// Draw all Missiles
		for(int i = 0; i < maxMissiles; i++) {
			// Draw the missile & trail
			changeColor(red);
			drawLine(missileSX[i], missileSY[i], missileX[i], missileY[i]);

			// Save Transform
			saveCurrentTransform();

			// Translate to Missile Position
			translate(missileX[i], missileY[i]);

			// Rotate by Missile Angle
			rotate(missileAngle[i]);

			// Draw Missile Image
			drawImage(missileImage, -15, -6, 30, 12);

			// Reset Transform
			restoreLastTransform();
		}
	}
	//-------------------------------------------------------

	//-------------------------------------------------------
	// Shell
	//-------------------------------------------------------
	double shellX, shellY;
	double shellVX, shellVY;
	double shellEndX, shellEndY;
	double shellAngle;
	boolean shellActive, shellExploding;
	double shellRadius;

	AudioClip shellExplosion;
	Image shellImage;

	double explosionTime;
	int explosionFrame;
	Image explosionSheet;
	Image[] explosionImages;


	// Initialise Shell
	public void initShell() {
		// Load Audio file
		shellExplosion = loadAudio("Audio/explosion.wav");

		// Load Image
		shellImage = loadImage("Images/shell.png");

		// Load Explosion Sprite Sheet
		explosionSheet = loadImage("Images/explosion.png");
        explosionImages = new Image[40];

        // Extract Individual Images
        for(int i = 0; i < 40; i++) {
            explosionImages[i] = subImage(explosionSheet, i*128, 0, 128, 128);
        }
	}

	// Fire Shell
	public void fireShell(double x, double y) {
		// If the shell is already active
		if(shellActive || shellExploding) {
			return;
		}
		// Start shell at center bottom
		shellX = 250;
		shellY = 500;

		// Set end position
		shellEndX = x;
		shellEndY = y;

		// Calculate velocity
		shellVX = shellEndX - shellX;
		shellVY = shellEndY - shellY;

		shellAngle = atan2(shellVX, -shellVY) - 90;

		// Rescale velocity
		double l = length(shellVX, shellVY);
		shellVX = shellVX * 100 / l;
		shellVY = shellVY * 100 / l;

		// Set shell active
		shellActive = true;
		shellExploding = false;
	}

	// Update the shell
	public void updateShell(double dt) {
		if(shellActive) {
			// Move shell
			shellX += shellVX * dt;
			shellY += shellVY * dt;

			// Check if shell has moved past its target
			if((shellEndX-shellX)*shellVX < 0 || (shellEndY-shellY)*shellVY < 0) {
				// Make shell explode
				shellActive = false;
				explosionTime = 0.0;
				explosionFrame = 0;
				shellRadius = 30;
				shellExploding = true;
				playAudio(shellExplosion);
			}
		} else if(shellExploding) {
			// Add to Time
			explosionTime += dt;

			// If explosion time reaches 1.0, stop explosion
			if(explosionTime >= 1.0) {
				shellExploding = false;
			}

			// Calculate Radius
			shellRadius = 30 * (1.0 - explosionTime);

			// Calculate frame from time
			explosionFrame = (int)(explosionTime * 39);

			// Check if explosion destroys each missile
			for(int i = 0; i < maxMissiles; i++) {
				// Check distance to missile
				if(distance(shellX, shellY, missileX[i], missileY[i]) < shellRadius) {
					// Reset the missile
					newMissile(i);
				}
			}
		}
	}

	// Draw the Shell
	public void drawShell() {
		if(shellActive) {
			// Draw the shell
			
			// Save Transform
			saveCurrentTransform();

			// Translate to Missile Position
			translate(shellX, shellY);

			// Rotate by Missile Angle
			rotate(shellAngle);

			// Draw Missile Image
			drawImage(shellImage, -4, -2, 8, 4);

			// Reset Transform
			restoreLastTransform();
		} else if(shellExploding) {
			// Draw the explosion
			
			// Save Transform
			saveCurrentTransform();

			// Translate to Missile Position
			translate(shellX, shellY);

			// Rotate by Missile Angle
			rotate(shellAngle);

			// Draw Missile Image
			drawImage(explosionImages[explosionFrame], -25, -25, 50, 50);

			// Reset Transform
			restoreLastTransform();
		}
	}
	//-------------------------------------------------------

	//-------------------------------------------------------
	// Cannon
	//-------------------------------------------------------
	double cannonX, cannonY;
	double cannonW, cannonH;
	double barrelX, barrelY;

	// Initialise Cannon
	public void initCannon() {
		// Set Position
		cannonX = 250;
		cannonY = 490;

		// Set Size
		cannonW = 20;
		cannonH = 20;

		// Set Barrel
		barrelX = 250;
		barrelY = 470;
	}

	// Draw Cannon
	public void drawCannon() {
		changeColor(white);

		// Draw Square
		drawSolidRectangle(cannonX-cannonW/2, cannonY-cannonH/2, cannonW, cannonH);

		// Draw Barrel
		drawLine(cannonX, cannonY, barrelX, barrelY, 5);
	}

	//-------------------------------------------------------

	// Initialise the game
	public void init() {
		// Initialise Window Size
		setWindowSize(500, 500);

		// Load Background Image
		backgroundImage = loadImage("Images/city-background.png");

		gameOver = false;

		// Initialise the Shell
		initShell();

		// Initialise the Missile
		initMissile();

		// Initialise the Cannon
		initCannon();

		// Load and Play Background Music
		// Background music:
		// Music: Sci-Fi - Bensound.com
		// License: Creative commons - Attribution - No Derivative Works
		// http://creativecommons.org/licenses/by-nd/3.0/legalcode
		backgroundMusic = loadAudio("Audio/bensound-scifi.wav");
		startAudioLoop(backgroundMusic);
	}

	// Updates the game
	public void update(double dt) {
		// Don't update if the game is over
		if(gameOver) {return;}
		dt=dt*10;

		// Update Missile
		updateMissile(dt);

		// Update Shell
		updateShell(dt);
	}

	// This gets called any time the Operating System
	// tells the program to paint itself
	public void paintComponent() {
		// Clear the background to black
		drawImage(backgroundImage, 0, 0, 500, 500);

		// Draw the Missile
		drawMissile();

		// Draw the Shell
		drawShell();

		// Draw the Cannon
		drawCannon();

		// Display game over message
		if(gameOver) {
			changeColor(red);
			drawText(150, 150, "Game Over!");
		}
	}

	// MousePressed Event Handler
	public void mousePressed(MouseEvent e) {
		// If use pressed left button
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Fire a shell
			fireShell(e.getX(), e.getY());
		}
	}

	// MouseMoved Event Handler
	public void mouseMoved(MouseEvent e) {
		// Set Barrel Position
		double bx = e.getX() - cannonX;
		double by = e.getY() - cannonY;

		double l = length(bx, by);

		barrelX = cannonX + bx * 20 / l;
		barrelY = cannonY + by * 20 / l;
	}
}
