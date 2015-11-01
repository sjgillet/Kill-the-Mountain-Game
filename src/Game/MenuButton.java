package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class MenuButton {

	int xPosition;
	int yPosition;
	boolean clicked = false;
	String text = "";
	int buttonWidth;
	int buttonHeight;

	public MenuButton(int buttonWidth, int buttonHeight, String text, int x, int y) {

		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.text = text;
		System.out.println(text);
		xPosition = x;
		yPosition = y;

	}

	public void isPushed() {

		//from pause main
		if (this.text.equals("Game")){
		
		GamePanel.menu.menuState("Main");
		
		} else if(this.text.equals("Back to Pause Main")){
			
		GamePanel.menu.menuState("PauseMain");
			
		}

	}

	public boolean isOver() {
		if (((Controller.mousePosition.x >= this.xPosition) && 
				(Controller.mousePosition.x<=this.xPosition + this.buttonWidth) &&
				(Controller.mousePosition.y>=this.yPosition) && 
				(Controller.mousePosition.y<=this.yPosition+this.buttonHeight)))
		{
			return true;
		}
		return false;
	}

	public void Draw(Graphics2D g){

		//g.drawImage(GamePanel.playerImage,(int)xPosition,(int)yPosition,buttonWidth,buttonHeight,null);
		g.setColor(new Color(200,200,200));
		g.fillRect(xPosition, yPosition, buttonWidth, buttonHeight);
		g.setColor(Color.white);
		g.drawRect(xPosition, yPosition, buttonWidth, buttonHeight);
		Font font = new Font("Iwona Heavy",Font.BOLD,18);
		g.setFont(font);
		FontMetrics m = g.getFontMetrics(font);
		g.setColor(Color.black);
		g.drawString(text, xPosition+(buttonWidth/2) - (m.stringWidth(text)/2), yPosition + 27);
		
	}

}
