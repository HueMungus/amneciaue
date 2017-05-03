package game;

import java.util.ArrayList;

public class DebugString {
	public static String string = "At: ";
	static ArrayList<String> level = new ArrayList<String>();
	static int pos = 0;
	
	public static void add(String location) {
		level.add(string + location);
		++pos;
		string = level.get(pos);
	}
	
	public static void back() {
		if (pos == 0) {
			System.out.println("Attempt at reducing pos below 1");
			return;
		}
		level.remove(pos);
		--pos;
		string = level.get(pos);
	}
}
