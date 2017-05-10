package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Level {
	ArrayList<Block> parts = new ArrayList<Block>();
	
	/**
	 * Generates a blank level, this constructor should be deleted but couldn't be screwed
	 * @param filename
	 * @param sameDirectory
	 */
	public Level(String filename, boolean sameDirectory) {
		
	}
	
	
	/**
	 * Generates blank level
	 */
	public Level() {
		
	}
	
	/**
	 * Add a block to this Level's Block array
	 * @param block
	 */
	public void add(Block block) {
		this.parts.add(block);
	}
	
	/**
	 * The code to be called when this Level receives the Left key event. 
	 * Generally called when this Level is being used and the user presses 'Left' key
	 */
	public void Left() {
	}
	
	/**
	 * The code to be called when this Level receives the AltLeft key event. 
	 * Generally called when this Level is being used and the user presses 'Left' key with 'Alt' down
	 */
	public void AltLeft() {
	}
	
	/**
	 * The code to be called when this Level receives the Right key event. 
	 * Generally called when this Level is being used and the user presses 'Right' key
	 */
	public void Right() {
	}
	
	/**
	 * The code to be called when this Level receives the Alt Right key event. 
	 * Generally called when this Level is being used and the user presses 'Right' key with 'Alt' down
	 */
	public void AltRight() {
	}
	
	public void AltUp() {}
	public void AltDown() {}
	
}
