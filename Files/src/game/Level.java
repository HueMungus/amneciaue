package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Level {
	ArrayList<PosComponent> parts;
	
	
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
	
	
	/**
	 * Loads contents of the file specified into this Level
	 * Equivalent to calling the 
	 * Level(String filename, boolean sameDirectory) constructor
	 * @param filename
	 * @param sameDirectory
	 */
	public void Load(String filename, boolean sameDirectory) {
		File file;
		if (sameDirectory) {
			file = new File(filename);
			System.out.println(filename);
		} else {
			file = new File(System.getProperty("user.dir") + filename);
			System.out.println(System.getProperty("user.dir") + " with " + filename);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split(" ");
				switch (words[0].toLowerCase()) {
				case "staticblock" :
					
					break;
				case "moveblock" :
					
					break;
				default:
					System.out.println("Unknown block type: " + words[0] + "\"");
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
