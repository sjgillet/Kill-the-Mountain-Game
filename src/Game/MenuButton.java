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

	public MenuButton(int buttonWidth, int buttonHeight, String text) {

		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.text = text;

	}

	/*
	 * Called from the controller when the mouse is clicked
	 * Depending on which MenuButton the mouse is over when clicked, 
	 * determines what the next menu state will be
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void isPushed() {

		//from pause main
		if (this.text.equals("Game")){

			GamePanel.menu.menuState("Main");

		} else if (this.text.equals("Load")){

			GamePanel.menu.menuState("Load");

		} else if(this.text.equals("Back")){

			GamePanel.menu.menuState("PauseMain");

		} else if (this.text.equals("Back to Save/Load")){

			GamePanel.menu.menuState("Main");
		}
		else if(this.text.equals("Exit")){
			System.exit(0);
		}
		else if (this.text.equals("Options")){

			GamePanel.menu.menuState("Options");

		} 
		
		//title screen
		else if (this.text.equals("New Game")){
			System.out.println("here");
			GamePanel.createLevel();
		}


	}

	/*
	 * How to know if the mouse is currently over a particular MenuButton
	 * 
	 * @param N/A
	 * @return true if the mouse position is within this button, false if not
	 */
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
		//if the mouse is over this button
		if(isOver()){
			g.setColor(new Color(20,200,20));;
			g.drawRect(xPosition, yPosition, buttonWidth, buttonHeight-1);
		}
		Font font = new Font("Iwona Heavy",Font.BOLD,18);
		g.setFont(font);
		FontMetrics m = g.getFontMetrics(font);
		g.setColor(Color.black);
		g.drawString(text, xPosition+(buttonWidth/2) - (m.stringWidth(text)/2), yPosition + 27);
		
	}

}
