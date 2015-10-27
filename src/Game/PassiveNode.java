package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;

public class PassiveNode {
	int xpos;
	int ypos;
	boolean taken = false;
	String name = "";
	int id=-1;
	public PassiveNode(String Name, int ID, int x, int y){
		name = Name;
		id=ID;
		xpos = x;
		ypos = y;
	}
	public void takePassive(){//gain the effects of the passive
		taken = true;
		if(name=="10% increased damage"){
			
		}
		else if(name=="10% increased defense"){
			
		}
	}
	public void dropPassive(){//lose the effects of the passive
		taken = false;
	}
	public boolean mouseOverThis(){
		if(MouseInfo.getPointerInfo().getLocation().x>=xpos&&MouseInfo.getPointerInfo().getLocation().x<=xpos+50){
			if(MouseInfo.getPointerInfo().getLocation().y>=ypos&&MouseInfo.getPointerInfo().getLocation().y<=ypos+50){
				return true;
			}
		}
		return false;
	}
	public void Draw(Graphics g){
		if(taken ==false){
			g.drawImage(GamePanel.passiveNodes[id][1],xpos,ypos,50,50,null);
		}
		else{
			g.drawImage(GamePanel.passiveNodes[id][0],xpos,ypos,50,50,null);
		}
		if(mouseOverThis()){
			
		}
	}
}
