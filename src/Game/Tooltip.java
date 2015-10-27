package Game;

import java.awt.Graphics;
import java.awt.Point;

public class Tooltip {
	int xpos;
	int ypos;
	double scale = .5;
	Point cursorPos;
	public Tooltip(int x, int y){
		xpos = x;
		ypos = y;
		cursorPos=new Point(0,0);
	}
	public void moveCursorRight(){
		cursorPos.x+=1;
	}
	public void moveCursorLeft(){
		cursorPos.x-=1;
	}
	public void moveCursorUp(){
		cursorPos.y-=1;
	}
	public void moveCursorDown(){
		cursorPos.y+=1;
	}
	public void Draw(Graphics g){
		g.drawImage(GamePanel.tilesTooltip,xpos,ypos,(int)(GamePanel.tilesTooltip.getWidth()*scale),(int)(GamePanel.tilesTooltip.getHeight()*scale),null);
		g.drawImage(GamePanel.cursorImg[0][0],xpos+(cursorPos.x*(int)(32*scale)),ypos+(cursorPos.y*(int)(32*scale)),(int)(32*scale),(int)(32*scale),null);
	}
}
