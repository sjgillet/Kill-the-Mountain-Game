package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class MessageBox {
	ArrayList<String> messages = new ArrayList<String>();
	int currentMessage = 0;
	int currentIndex = 0;
	int x, y, width, height;
	int frames=0;
	int framesPerUpdate=3;
	Font font = new Font("Iwona Heavy",Font.BOLD,18);
	public MessageBox(){
		x = 2;
		y = 2;
		width = 700;
		height = 200;
	}
	public void addMessage(String msg){
		messages.add(msg);
		//		if(messages.size()>50){
		//			messages.remove(0);
		//		}
	}

	public void Draw(Graphics2D g){
		y = ApplicationUI.windowHeight-height;
		x = (ApplicationUI.windowWidth/2)-(width/2);
		currentMessage = messages.size()-1;
		if(messages.size()>0){
			if(currentIndex<messages.get(currentMessage).length()){
				if(frames>=framesPerUpdate||messages.get(currentMessage).charAt(currentIndex)==' '){

					currentIndex++;
					frames = 0;
				}
			}
		}
		g.setColor(new Color(150,0,0));
		g.fillRect(x-2, y-2, width+4, height+4);
		g.setColor(new Color(200,200,200));
		g.fillRect(x, y, width, height);

		g.setColor(Color.black);
		g.setFont(font);
		FontMetrics m = g.getFontMetrics(font);

		Rectangle clipArea = new Rectangle(x,y,width,height);
		//g.setClip(clipArea);
		//draw the previous 2 messages
		for(int i = messages.size()-5; i<messages.size()-1;i++){
			if(i>=0){
				g.drawString(messages.get(i), x+50, y+height-170+((i-(messages.size()-5))*30));
			}
		}
		//draw all the characters up to index
		if(messages.size()>0){
			g.drawChars(messages.get(currentMessage).toCharArray(), 0, currentIndex, x+50, y+height-50);
		}
		//g.setClip(new Rectangle(0,0,ApplicationUI.windowWidth,ApplicationUI.windowHeight));
		frames++;
	}

}
