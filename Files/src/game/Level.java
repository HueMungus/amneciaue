package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Level {
	ArrayList<Block> parts;
	
	public boolean inMotion;
	
	
	/**
	 * Generates a level from a file
	 * Flag sameDirectory determines whether the file is in the same directory
	 * as the program using it.
	 * @param filename
	 * @param sameDirectory
	 */
	public Level(String filename, boolean sameDirectory) {
		
		
		
		
		
	}
	
	
	/**
	 * Generates a blank Level, must have a call to Load() to use
	 */
	public Level() {
		
	}
	
	public void add(Block block) {
		this.parts.add(block);
	}
	
	
	/**
	 * Loads contents of the file specified into this Level
	 * Equivalent to calling the 
	 * Level(String filename, boolean sameDirectory) constructor
	 * @param filename
	 * @param sameDirectory
	 */
	
	public void Left() {
	}
	
	public void AltLeft() {
	}
	
	public void Right() {
	}
	
	public void AltRight() {
	}
	
}
