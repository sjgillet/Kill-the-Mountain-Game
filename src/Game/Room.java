package Game;

import java.awt.Rectangle;
import java.util.ArrayList;
/**
 * Stores information about a room in the dungeon
 */
public class Room {
	Rectangle area;
	//ArrayList<ArrayList<Room>> pathsToThisFromStart = new ArrayList<ArrayList<Room>>();
	ArrayList<Room> shortestPathToThisFromStart = null;
	ArrayList<Hallway> hallways = new ArrayList<Hallway>();
	public Room(int x, int y, int w, int h){
		area = new Rectangle(x,y,w,h);
	}
}
