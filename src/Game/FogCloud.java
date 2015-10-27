package Game;

import java.awt.Graphics;

public class FogCloud {
	int xpos;
	int ypos;
	int speed = -1;
	int width;
	int height;
	int growthRate;
	int type;
	public FogCloud(int x,int y){
		xpos = x;
		ypos = y;
		growthRate = GamePanel.randomNumber(-1, 1);
		type = GamePanel.randomNumber(0, 4);
		width = GamePanel.randomNumber(200, 500);
		height = GamePanel.randomNumber(200, 500);
	}
	public void Draw(Graphics g){
		xpos+=speed;
		ypos+=GamePanel.randomNumber(0, speed);
		if(GamePanel.randomNumber(1, 6)==1){
			width+=growthRate;
		}
		if(GamePanel.randomNumber(1, 6)==1){
			height+=growthRate;
		}
		if(width == 0||height==0){
			growthRate = 0;
		}
		g.drawImage(GamePanel.fog[type][0],xpos-(width/2),ypos-(height/2),width,height,null);
	}
}
