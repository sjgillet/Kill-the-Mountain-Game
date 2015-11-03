package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class InventorySlot {

	Item item;
	int xPosition;
	int yPosition;
	int width = 50;
	int height = 50;


	public InventorySlot(int x, int y, Item i) {

		xPosition = x;
		yPosition = y;
		item = i;

	}


	public boolean isOver() {
		if (((Controller.mousePosition.x >= this.xPosition) && 
				(Controller.mousePosition.x<=this.xPosition + this.width) &&
				(Controller.mousePosition.y>=this.yPosition) && 
				(Controller.mousePosition.y<=this.yPosition+this.height)))
		{
			return true;
		}
		return false;
	}

	public void drawInventorySlot(Graphics2D g) {

		g.drawImage(GamePanel.inventorySlotImage,(int)xPosition,(int)yPosition,50,50,null);

	}

}
