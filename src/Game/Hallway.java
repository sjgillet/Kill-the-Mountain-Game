package Game;

import java.awt.Rectangle;

public class Hallway {
	Rectangle area;
	Room start;
	Room end;
	public Hallway(int x, int  y, int w, int h){
		area = new Rectangle(x,y,w,h);
	}
}
