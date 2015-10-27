package Game;

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
		xPosition = x;
		yPosition = y;

	}

	public void isPushed() {

		//from pause main
		if (text.equals("Game")){
			
			GamePanel.menu.menuState("Main");
			
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

		g.drawImage(GamePanel.playerImage,(int)xPosition,(int)yPosition,buttonWidth,buttonHeight,null);

	}

}
