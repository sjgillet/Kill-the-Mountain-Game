package Game;

import java.awt.Rectangle;
/**
 * An object which represents a hallway in the dungeon
 */
public class Hallway {
	Rectangle area;
	Room start;
	Room end;
	public Hallway(int x, int  y, int w, int h){//creates a hallway with the specified x, y, width, and height
		area = new Rectangle(x,y,w,h);
	}
}
