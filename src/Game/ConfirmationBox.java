package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;

public class ConfirmationBox {
	int xpos;
	int ypos;
	int id;
	String message;
	public ConfirmationBox(int x, int y, int ID){
		xpos=x;
		ypos=y;
		id=ID;
		if(id==0){
			message = "Are you sure you want to save?";
		}
	}
	public void pressYes(){
		if(mouseOnYes()){
			if(this.id==0){
				Controller.saveLevel(GamePanel.rooms.get(GamePanel.currentRoom).name);
				GamePanel.confirmationBoxes.remove(this);
			}
		}
	}
	public void pressNo(){
		if(mouseOnNo()){
			if(this.id==0){
				GamePanel.confirmationBoxes.remove(this);
			}
		}
	}
	public boolean mouseOnYes(){
		if(MouseInfo.getPointerInfo().getLocation().x>=xpos+50&&MouseInfo.getPointerInfo().getLocation().x<=xpos+130){
			if(MouseInfo.getPointerInfo().getLocation().y>=(ypos+100)&&MouseInfo.getPointerInfo().getLocation().y<=(ypos+130)){
				return true;
			}
		}
		return false;
	}
	public boolean mouseOnNo(){
		if(MouseInfo.getPointerInfo().getLocation().x>=xpos+170&&MouseInfo.getPointerInfo().getLocation().x<=xpos+250){
			if(MouseInfo.getPointerInfo().getLocation().y>=(ypos+100)&&MouseInfo.getPointerInfo().getLocation().y<=(ypos+130)){
				return true;
			}
		}
		return false;
	}
	public void Draw(Graphics g){
		g.drawImage(GamePanel.descriptionPanel,xpos,ypos,300,150,null);
		g.drawImage(GamePanel.button,xpos+50,ypos+100,80,30,null);//yes button
		g.drawImage(GamePanel.button,xpos+170,ypos+100,80,30,null);//no button
		Font font = new Font("Iwona Heavy",Font.BOLD,14);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(message, xpos+5, ypos+50);
		font = new Font("Iwona Heavy",Font.BOLD,18);
		g.setFont(font);
		g.drawString("Yes", xpos+55, ypos+120);
		g.drawString("No", xpos+175, ypos+120);
	}
}
