import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class BallPit1 extends GameEngine {
	
	//Main function that takes care of some Object Oriented stuff
	public static void main(String args[]) {
		createGame(new BallPit1());
	}

    //Draws a stick man from top,left (x,y) with width and height
    public void drawStickman( double x, double y, double width, double height) {
    	//Head
		drawCircle(x+width/2.0, y+width/2.0, width/2.0);
		//Body
		drawLine(   x+width/2.0, y+width,         x+width/2.0, y+height-width);
		//Arms
		drawLine(   x,           y+5.0*width/4.0, x+width,     y+5.0*width/4.0);
		//Legs
		drawLine(   x+width/2.0, y+height-width,  x,           y+height);
		drawLine(   x+width/2.0, y+height-width,  x+width,     y+height);
	}

	//Variables used to represent my stickman
	//Position (upper left corner)
	double stickmanPositionX = 50;
	double stickmanPositionY = 50;

	//The velocity the stickman is moving
	double stickmanVelocityX = 0;
	double stickmanVelocityY = 0;

	//How big he is (width and height)
	double stickmanSizeX = 30;
	double stickmanSizeY = 90;

	//Variables to represent the ball
	//Position of the ball (centre)
	int N = 1000;
	double[] ballPositionX = new double[N];
	double[] ballPositionY = new double[N];

	//Velocity of the ball
	double[] ballVelocityX = new double[N];
	double[] ballVelocityY = new double[N];

	//Radius of the ball
	double radius = 7;

	//Controls how 'springy' the balls are
	double k = 15;

	//Variable to Control gravity
	double gravity = 980;

	public void init() {
		setWindowSize(500,500);
		for(int i = 0; i < N; i++) {
			ballPositionX[i] = 20 + rand(460);
			ballPositionY[i] = 20 + rand(460);
		}

		for(int i = 0; i < N; i++) {
			ballVelocityX[i] = 0;
			ballVelocityY[i] = 0;
		}
	}



	//Keeps track of how many frames have been drawn
	int numberOfFrames = 0;

	//Updates the display
	public void update(double dt) {
		//Update the StickMan
		//Make him accelerate towards the ground
		stickmanVelocityY += gravity   * dt;
		//Move him
		stickmanPositionX += stickmanVelocityX * dt;
		stickmanPositionY += stickmanVelocityY * dt;

		//If he's collided with the left wall
		if(stickmanPositionX < 0) {
			//Make sure he can't move through it
			stickmanPositionX = 0;
		}
		//If he's collided with the right wall
		if(stickmanPositionX + stickmanSizeX > 500) {
			//Make sure he can't move through it
			stickmanPositionX = 500 - stickmanSizeX;
		}

		//If he's collided with the roof
		if(stickmanPositionY < 0) {
			//Make sure he can't move through it
			stickmanPositionY = 0;
		}
		//If he's collided with the floor
		if(stickmanPositionY + stickmanSizeY > 500) {
			//Make sure he can't move through it
			stickmanPositionY = 500 - stickmanSizeY;
		}

		//Update the Ball
		for(int i = 0; i < N; i++) {
			//Make it accelerate towards the ground
			ballVelocityY[i] += gravity * dt;

			//Move it
			ballPositionX[i] += ballVelocityX[i] * dt;
			ballPositionY[i] += ballVelocityY[i] * dt;

			//If the ball has collided with the left wall
			//And it's moving left
			if(ballPositionX[i] < radius && ballVelocityX[i] < 0) {
				//Adjust the position so it bounced at the correct time
				double distance = radius-ballPositionX[i];
				ballPositionX[i] = radius + distance;
				//And reverse the velocity (with reduced speed)
				ballVelocityX[i] = -ballVelocityX[i] * 0.70;
			}
			
			//If the ball has collided with the right wall
			//And it's moving right
			if(ballPositionX[i] > 500-radius && ballVelocityX[i] > 0) {
				//Adjust the position so it bounced at the correct time
				double distance = ballPositionX[i] - (500-radius);
				ballPositionX[i] = (500-radius) - distance;
				//And reverse the velocity (with reduced speed)
				ballVelocityX[i] = -ballVelocityX[i] * 0.70;
			}

			//If the ball has collided with the top wall
			//And it's moving up
			if(ballPositionY[i] < radius && ballVelocityY[i] < 0) {
				//Adjust the position so it bounced at the correct time
				double distance = radius-ballPositionY[i];
				ballPositionY[i] = radius + distance;
				//And reverse the velocity (with reduced speed)
				ballVelocityY[i] = -ballVelocityY[i] * 0.70;
			}

			//If the ball has collided with the floor
			if(ballPositionY[i] > 500-radius && ballVelocityY[i] > 0) {
				//work out the extra overlap
				double distance = ballPositionY[i] - (500-radius);

				//And from that the extra time
				double time = distance / ballVelocityY[i];
				//Adjust the velocity to remove extra acceleration
				ballVelocityY[i] -= gravity * time;

				//Adjust the position
				ballPositionY[i] = (500-radius) - ballVelocityY[i]*time;
				//And reverse the velocity (with reduced speed)
				ballVelocityY[i] = -ballVelocityY[i] * 0.70;
			}
		}

		//Go through the ball array
		for(int i = 0; i < N; i++) {
			//Go through every other ball
			for(int j = i+1; j < N; j++) {
				//Calculate the distance between them
				double dx = ballPositionX[j] - ballPositionX[i];
				double dy = ballPositionY[j] - ballPositionY[i];
				double d = Math.sqrt(dx*dx + dy*dy);
				
				//If the balls are less than 2 radii away from each other.
				if(d < (radius)*2) {
					//Get a unit vector
					dx = dx/d;
					dy = dy/d;

					//Calculate how much they overlap
					double displacement = ((radius)*2) - d;

					//'Push' the balls away from each other
					ballVelocityX[i] -= k * dx * displacement;
					ballVelocityY[i] -= k * dy * displacement;

					ballVelocityX[j] -= k * -dx * displacement;
					ballVelocityY[j] -= k * -dy * displacement;

					//Apply some dampening to stop them getting too energetic
					ballVelocityX[i] *= 0.9;
					ballVelocityY[i] *= 0.9;

					ballVelocityX[j] *= 0.9;
					ballVelocityY[j] *= 0.9;
				}
			}
		}

		//Go through the Ball array
		for(int i = 0; i < N; i++) {
			
			//If they collide vertically
			if(ballPositionY[i]+radius > stickmanPositionY && ballPositionY[i]-radius < stickmanPositionY+stickmanSizeY) {

				//Find the centre X position of the stickman.
				double centreX = stickmanPositionX + stickmanSizeX/2.0;
				double centreY;

				//Work out a suitable point to use for a collision with the stickman
				if(ballPositionY[i] < stickmanPositionY+stickmanSizeX/2.0) {
					centreY = stickmanPositionY+stickmanSizeX/2.0;
				} else if(ballPositionY[i] > stickmanPositionY+stickmanSizeY-stickmanSizeX/2.0) {
					centreY = stickmanPositionY+stickmanSizeY-stickmanSizeX/2.0;
				} else {
					centreY = ballPositionY[i];
				}

				//Calculate distance between them
				double dx = centreX - ballPositionX[i];
				double dy = centreY - ballPositionY[i];
				double d = Math.sqrt(dx*dx + dy*dy);

				//If they collide horizontally
				if(d < radius+stickmanSizeX/2.0) {
					//Calculate how much they overlap
					double displacement = (radius+stickmanSizeX/2.0) - d;

					//'Push' the stickman away (0%)
					stickmanVelocityX -=  k * -dx * displacement * 0.0;
					stickmanVelocityY -=  k * -dy * displacement * 0.0;

					//'Push' the ball away
					ballVelocityX[i] -=  k * dx * displacement;
					ballVelocityY[i] -=  k * dy * displacement;

					//Apply some dampening to stop it getting too energetic
					ballVelocityX[i] *= 0.9;
					ballVelocityY[i] *= 0.9;
				}
			}
		}
	}

	//This gets called any time the Operating System
	//tells the program to paint itself
	public void paintComponent() {
		changeBackgroundColor(white);
		clearBackground(500, 500);

		//Draw Stickman
		changeColor(black);
		drawStickman(stickmanPositionX, stickmanPositionY, stickmanSizeX, stickmanSizeY);

		for(int i = 0; i < N; i++) {
			//Draw Ball
			changeColor(black);
			drawSolidCircle(ballPositionX[i], ballPositionY[i], radius);
		}
	}

	//Called whenever a key is pressed
    public void keyPressed(KeyEvent e) {
    	//If right arrow key
    	if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
    		//Make the stickman move right
    		stickmanVelocityX = 250;
    	}
    	//If right arrow key
    	if(e.getKeyCode() == KeyEvent.VK_LEFT) {
    		//Make the stickman move left
    		stickmanVelocityX = -250;
    	}
    	//If space bar
    	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
    		//And the stickman is touching the ground
    		if(stickmanPositionY + stickmanSizeY == 500) {
    			//Make him move up
    			stickmanVelocityY = -800;
    		}
    	}
    }

    //Called whenever a key is released
    public void keyReleased(KeyEvent e) {
    	//If right arrow key
    	if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
    		//Stop the stickman moving right
    		stickmanVelocityX = 0;
    	}
    	//If left arrow key
    	if(e.getKeyCode() == KeyEvent.VK_LEFT) {
    		//Stop the stickman moving left
    		stickmanVelocityX = 0;
    	}
    }
    
    //Called whenever a key is pressed and immediately released
    public void keyTyped(KeyEvent e) {

    }
}