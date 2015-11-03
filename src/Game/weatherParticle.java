package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class weatherParticle {
	double xpos;
	double ypos;
	double oldX;
	double oldY;
	int startY;
	int windDirection;
	int initialElevation;
	int id = -1;
	double speed = 36.0;
	Point destination;
	double splashWidth = 0;
	/*
	 * A single particle for weather
	 * 
	 * @param x - the x position the particle should end up at
	 * @param y - the y position the particle should end up at
	 * @param ID - the type of particle to display
	 */
	public weatherParticle(int x, int y, int ID){
		windDirection = (int)GamePanel.levels.get(GamePanel.currentLevel).windDirection;
		initialElevation = ApplicationUI.windowHeight;
		xpos = x;//+windDirection;
		ypos = y-initialElevation;
		startY = y;

		id = ID;
	}
	public void update(){
		if(ypos<startY){
			if(id==0){//rain
				oldX = xpos;
				oldY = ypos;
				//move towards destination
				xpos = xpos+0;//(windDirection*(double)((double)windDirection/(double)initialElevation));
				ypos = ypos+ speed;
			}
		}
		else{
			if(splashWidth<2)
				splashWidth = 2;

		}
	}
	public void Draw(Graphics2D g){
		if(id==0){
			if(splashWidth==0){
				g.setColor(new Color(32,35,251,150));
				g.drawLine(((ApplicationUI.windowWidth/2)-16)+(int)oldX-(int)(GamePanel.player.xpos), ((ApplicationUI.windowHeight/2)-16)+(int)oldY-(int)(GamePanel.player.ypos), ((ApplicationUI.windowWidth/2)-16)+(int)xpos-(int)(GamePanel.player.xpos), ((ApplicationUI.windowHeight/2)-16)+(int)ypos-(int)(GamePanel.player.ypos));
			}
			else{
				if(splashWidth<6){
					g.setColor(new Color(72,75,251,150));
					splashWidth+=.2;
					g.drawRect((int)(double)(((ApplicationUI.windowWidth/2)-16)+(int)oldX-(int)(GamePanel.player.xpos)-(splashWidth/2)), (int)(double)(((ApplicationUI.windowHeight/2)-16)+(int)ypos-(int)(GamePanel.player.ypos)-(splashWidth/2)), (int)splashWidth, (int)splashWidth);
				}
				else{
					GamePanel.levels.get(GamePanel.currentLevel).weather.remove(this);
				}
			}
		}
	}
}
