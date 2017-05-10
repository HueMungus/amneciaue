package game;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class GameLevel extends Level {
	ArrayList<MoveBlock> moveblocks;
	ArrayList<StaticBlock> staticblocks;
	MoveBlock focus;
	public Vec2 Gravity = new Vec2(0.0f,15.0f);
	ArrayList<StaticBlock> staticBlocks;
	public boolean verticalGravity = true;
	
//	public Image coolImage = Engine.importImage("60.jpg");
	
	/**
	 * This boolean is used to prevent Gravity changes whilst a block is in motion.
	 * But otherwise can be used whenever physics for this particular level should be stopped
	 */
	public boolean physicsOn;
	
	public GameLevel(MoveBlock[] moveblocks, StaticBlock[] staticblocks) {
		for (MoveBlock mblock : moveblocks) {
			add(mblock);
			this.moveblocks.add(mblock);
		}
		for (StaticBlock sblock : staticblocks) {
			add(sblock);
			this.staticBlocks.add(sblock);
		}
		physicsOn = false;
	}
	
//	public GameLevel(ArrayList<Block> parts, ArrayList<MoveBlock> moveblocks, ArrayList<StaticBlock> staticblocks) {
//		this.pparts = parts;
//		this.moveblocks = moveblocks;
//		this.staticBlocks = staticblocks;
//		physicsOn = false;
//	}
	
	public GameLevel(String filename, boolean sameDirectory) {
		Load(filename, sameDirectory);
		physicsOn = false;
	}
	
	public void Load(String filename, boolean sameDirectory) {
		File file;
		staticBlocks = new ArrayList<StaticBlock>();
		moveblocks = new ArrayList<MoveBlock>();
		Vec2 pos = new Vec2();
		Vec2 vel = new Vec2();
		if (sameDirectory) {
			file = new File(System.getProperty("user.dir") + "/src/"+ filename);
			System.out.println(System.getProperty("user.dir") + "/src/" + " with " + filename);
		} else {
			file = new File(filename);
			System.out.println(filename);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			if (reader != null) {
				System.out.println("Reader is not null");
			} else {
				System.out.println("Reader is null");
			}
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split(" ");
				if (words[0] != null) {
					switch (words[0].toLowerCase()) {
					case "static":
						if (words.length > 2) {
							pos.x = Integer.parseInt(words[1]);
							pos.y = Integer.parseInt(words[2]);
						} else {
							pos.x = pos.y = 0;
						}
						StaticBlock bob = new StaticBlock((int) pos.x,(int) pos.y);
						add(bob);
						staticBlocks.add(bob);
						System.out.println("Made a staticblock with position: " + pos.x + ", " + pos.y);
						break;
					case "move":
						//					Set the position vector if specified in the file
						if (words.length > 2) {
							pos.x = Integer.parseInt(words[1]);
							pos.y = Integer.parseInt(words[2]);
						} else {
							pos.x = pos.y = 0;
						}
						//					set the velocity vector if specified in the file
						if (words.length > 4) {
							vel.x = (float) Double.parseDouble(words[3]);
							vel.y = (float) Double.parseDouble(words[4]);
						} else {
							vel.x = vel.y = 0.0f;
						}
						MoveBlock steve = new MoveBlock(pos, vel);
						add(steve);
						moveblocks.add(steve);
						System.out.println("Made a moveblock with position: " + pos.x + ", " + pos.y + " and velocity " + vel.x + ", " + vel.y);
						break;
					case "goal" :
						if (words.length > 2) {
							pos.x = Integer.parseInt(words[1]);
							pos.y = Integer.parseInt(words[2]);
						} else {
							pos.x = pos.y = 0;
						}
						Block john = new Block(pos, Engine.GColor);
						addv(john);
						System.out.println("Made a goal block with position: " + pos.toString());
					default:
						System.out.println("Unknown block type: " + words[0] + "\"");
						break;
					}
				}
			}
			reader.close();
			focus = moveblocks.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("Exception caught in Load Method with Load(" + filename + ", " + sameDirectory + ") ID = ");
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Loads up the parts, moveblocks and staticblocks arrays stored in this GameLevel,
	 * Flag percentPositions determines whether the positions are absolute, or a percentage of screen space
	 * @param percentPositions
	 * @param filename
	 *//*
	public void Load(boolean percentPositions, String filename) {
		File file;
		Vec2 pos = new Vec2();
		Vec2 vel = new Vec2();
		file = new File(System.getProperty("user.dir") + "/src/"+ filename);
		System.out.println(System.getProperty("user.dir") + "/src/" + " with " + filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split(" ");
				if (words[0] != null) {
					switch (words[0].toLowerCase()) {
					case "staticblock":
						if (words[1] != null && words[2] != null) {
							pos.x = Integer.parseInt(words[1]);
							pos.y = Integer.parseInt(words[2]);
						} else {
							pos.x = pos.y = 0;
						}
						StaticBlock bob = new StaticBlock(pos);
						parts.add(bob);
						staticblocks.add(bob);
						break;
					case "moveblock":
						//					Set the position vector if specified in the file
						if (words[1] != null && words[2] != null) {
							pos.x = Integer.parseInt(words[1]);
							pos.y = Integer.parseInt(words[2]);
						} else {
							pos.x = pos.y = 0;
						}
						//					set the velocity vector if specified in the file
						if (words[3] != null && words[4] != null) {
							vel.x = (float) Double.parseDouble(words[3]);
							vel.y = (float) Double.parseDouble(words[4]);
						} else {
							vel.x = vel.y = 0.0f;
						}
						MoveBlock steve = new MoveBlock(pos, vel);
						parts.add(steve);
						moveblocks.add(steve);

						break;
					default:
						System.out.println("Unknown block type: " + words[0] + "\"");
						break;
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Exception caught in Load Method with Load(" + filename + ")");
		}
	}*/
	
	public void stop() {
		physicsOn = false;
		focus.v.x = 0;
		focus.v.y = 0;
	}
	
	public void add(MoveBlock moveblock) {
		super.add(moveblock);
		this.moveblocks.add(moveblock);
	}
	
	public void add(StaticBlock staticblock) {
		super.add(staticblock);
		this.staticBlocks.add(staticblock);
	}

	@Override
	public void Left() {
		
	}

	@Override
	public void AltLeft() {
		this.Gravity.x = -15.0f;
		this.Gravity.y = 0;
		this.verticalGravity = false;
		System.out.println("Gravity: " + Gravity.toString());
	}

	@Override
	public void Right() {
		
	}

	@Override
	public void AltRight() {
		this.Gravity.x = 15.0f;
		this.Gravity.y = 0;
		this.verticalGravity = false;
		System.out.println("Gravity: " + Gravity.toString());
	}
	
	@Override
	public void AltUp() {
		this.Gravity.x = 0;
		this.Gravity.y = -15.0f;
		this.verticalGravity = true;
		System.out.println("Gravity: " + Gravity.toString());
	}
	
	@Override
	public void AltDown() {
		this.Gravity.x = 0;
		this.Gravity.y = 15.0f;
		this.verticalGravity = true;
		System.out.println("Gravity: " + Gravity.toString());
	}
	
}
