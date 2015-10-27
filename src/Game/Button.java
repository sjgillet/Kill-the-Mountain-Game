package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;

public class Button {
	int xpos;
	int ypos;
	int ID=-1;
	int buttonWidth = 190;
	int buttonHeight=40;
	String name;
	public Button(int x, int y, int id){
		xpos = x;
		ypos = y;
		ID = id;
		if(id==0){
			name = "Attack";
		}
		else if(id == 1){
			name = "Run";
		}
		else if(id == 2){
			name = "Item";
		}
		else if(id == 3){
			name = "Possess";
		}

	}
	public boolean mouseOnButton(){
		if(MouseInfo.getPointerInfo().getLocation().x>=xpos&&MouseInfo.getPointerInfo().getLocation().x<=xpos+buttonWidth){
			if(MouseInfo.getPointerInfo().getLocation().y>=ypos&&MouseInfo.getPointerInfo().getLocation().y<=ypos+buttonHeight){
				return true;
			}
		}
		return false;
	}
	public void pushButton(){
		if(GamePanel.messages.size()==0){//no messages
			if(this.ID==0&&AppletUI.client.players.get(0).battling){//attack
				AppletUI.client.players.get(0).forms.get(0).attacks.get(0).use(AppletUI.client.players.get(0).forms.get(0), AppletUI.client.players.get(0).currentTarget);
			}
			else if(this.ID==1&&AppletUI.client.players.get(0).battling){//run
				if(GamePanel.randomNumber(1, 2)==1){
					AppletUI.client.players.get(0).battling=false;
					AppletUI.client.players.get(0).currentTarget=null;
					GamePanel.messages.add("You escaped!");
				}
				else if(AppletUI.client.players.get(0).forms.get(AppletUI.client.players.get(0).forms.size()-1).level>AppletUI.client.players.get(0).currentTarget.level){
					AppletUI.client.players.get(0).battling=false;
					AppletUI.client.players.get(0).currentTarget=null;
					GamePanel.messages.add("You escaped!");
				}
				else{
					GamePanel.messages.add("Failed to escape!");
				}
			}
			else if(this.ID==2){

			}
			else if(this.ID==3&&AppletUI.client.players.get(0).battling){//possess

				AppletUI.client.players.get(0).possessCurrentTarget();

			}
		}
	}
	public void Draw(Graphics g){
		if(GamePanel.messages.size()==0){//no messages
			if(mouseOnButton()){
				g.drawImage(GamePanel.apri[0][1],xpos,ypos,buttonWidth,buttonHeight,null);
			}
			else{
				g.drawImage(GamePanel.apri[0][0],xpos,ypos,buttonWidth,buttonHeight,null);
			}
			Font font = new Font("Iwona Heavy",Font.BOLD,18);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(name, xpos+60, ypos+25);
		}
	}
}
