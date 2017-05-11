package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class GameLevel extends Level {
	ArrayList<Block.Move> moveblocks;
	ArrayList<Block.Static> staticblocks;
	Block.Move focus;
	public static float GravityConstant = 9.81f;
	public Vec2 Gravity = new Vec2(0.0f, GravityConstant);
	ArrayList<Block.Static> staticBlocks;
	public boolean verticalGravity = true;
	
//	public Image coolImage = Engine.importImage("60.jpg");
	
	/**
	 * This boolean is used to prevent Gravity changes whilst a block is in motion.
	 * But otherwise can be used whenever physics for this particular level should be stopped
	 */
	public boolean physicsOn;
	
	public GameLevel(Block.Move[] moveblocks, Block.Static[] staticblocks) {
		for (Block.Move mblock : moveblocks) {
			add(mblock);
			this.moveblocks.add(mblock);
		}
		for (Block.Static sblock : staticblocks) {
			add(sblock);
			this.staticBlocks.add(sblock);
		}
		physicsOn = false;
	}
	
//	public GameLevel(ArrayList<Block> parts, ArrayList<Block.MoveBlock> moveblocks, ArrayList<Block.StaticBlock> staticblocks) {
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
		staticBlocks = new ArrayList<Block.Static>();
		moveblocks = new ArrayList<Block.Move>();
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
			System.out.println("Reader is not null");
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
						Block.Static bob = new Block.Static(pos);
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
						Block.Move steve = new Block.Move(pos, vel);
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
						Block john = new Block(pos, Engine.OColor);
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
	
	
	public void stop() {
		physicsOn = false;
		focus.v.x = 0;
		focus.v.y = 0;
	}
	
	public void add(Block.Move moveblock) {
		super.add(moveblock);
		this.moveblocks.add(moveblock);
	}
	
	public void add(Block.Static staticblock) {
		super.add(staticblock);
		this.staticBlocks.add(staticblock);
	}

	@Override
	public void Left() {
		
	}

	@Override
	public void AltLeft() {
		if (!physicsOn) {
			this.Gravity.x = -1 * GravityConstant;
			this.Gravity.y = 0;
			this.verticalGravity = false;
			System.out.println("Gravity: " + Gravity.toString());
		}
	}

	@Override
	public void Right() {
		
	}

	@Override
	public void AltRight() {
		if (!physicsOn) {
			this.Gravity.x = GravityConstant;
			this.Gravity.y = 0;
			this.verticalGravity = false;
			System.out.println("Gravity: " + Gravity.toString());
		}
	}
	
	@Override
	public void AltUp() {
		if (!physicsOn) {
			this.Gravity.x = 0;
			this.Gravity.y = -1 * GravityConstant;
			this.verticalGravity = true;
			System.out.println("Gravity: " + Gravity.toString());
		}
	}
	
	@Override
	public void AltDown() {
		if (!physicsOn) {
			this.Gravity.x = 0;
			this.Gravity.y = GravityConstant;
			this.verticalGravity = true;
			System.out.println("Gravity: " + Gravity.toString());
		}
	}
	
}
