package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class GameLevel extends Level {
	ArrayList<MoveBlock> moveblocks;
	ArrayList<StaticBlock> staticblocks;
	Block focus;
	
	public GameLevel(Block[] parts, MoveBlock[] moveblocks, StaticBlock[] staticblocks) {
		for (Block part : parts) {
			this.parts.add(part);
		}
		for (MoveBlock mblock : moveblocks) {
			this.moveblocks.add(mblock);
		}
		for (StaticBlock sblock : staticblocks) {
			this.staticblocks.add(sblock);
		}
		inMotion = false;
	}
	
	public GameLevel(ArrayList<Block> parts, ArrayList<MoveBlock> moveblocks, ArrayList<StaticBlock> staticblocks) {
		this.parts = parts;
		this.moveblocks = moveblocks;
		this.staticblocks = staticblocks;
		inMotion = false;
	}
	
	public GameLevel(String filename, boolean sameDirectory) {
		Load(filename, sameDirectory);
		inMotion = false;
	}
	
	public void Load(String filename, boolean sameDirectory) {
		File file;
		Vec2 pos = new Vec2();
		Vec2 vel = new Vec2();
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
			System.out.println("Exception caught in Load Method with Load(" + filename + ", " + sameDirectory + ")");
		}
	}
	
	public void add(MoveBlock moveblock) {
		this.parts.add(moveblock);
		this.moveblocks.add(moveblock);
	}
	
	public void add(StaticBlock staticblock) {
		this.parts.add(staticblock);
		this.staticblocks.add(staticblock);
	}

	@Override
	public void Left() {
		
	}

	@Override
	public void AltLeft() {
//		Rotate gravity 90 degrees ( pi/2 ) clockwise
		
	}

	@Override
	public void Right() {
		
	}

	@Override
	public void AltRight() {
//		Rotate gravity 90 ( pi/2 ) anti-clockwise or -90 ( -pi/2 ) clockwise
		
	}
	
}
