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
	boolean holdingItem = false;
	

	public InventorySlot(int x, int y, Item i) {

		xPosition = x;
		yPosition = y;
		item = i;

	}

	
	/*
	 * Called from the controller when the mouse is clicked
	 * Depending on which InventorySlot the mouse is over when clicked, 
	 * determines if an item will be picked up to be moved, or placed, or placed
	 * and replaced with an item in another slot
	 * 
	 * @param N/A
	 * 
	 * @return N/A
	 */
	public void isPushed() {

		// if you're holding an item and click a slot, replace held item with item in slot
		if (holdingItem) {
			//temporarily keep track of the item you're holding on to
			item = GamePanel.player.inventory.currentHeldItem;
			GamePanel.player.inventory.currentHeldItem = null;
			holdingItem = false;
		}
		else {
			holdingItem = true;
			GamePanel.player.inventory.currentHeldItem = item;
		}


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

		if (item.name.equals("sword")) {
			g.drawImage(GamePanel.sword,(int)xPosition,(int)yPosition,50,50,null);
		}

		if (holdingItem) {
			g.drawImage(GamePanel.sword,(int)Controller.mousePosition.x,(int)Controller.mousePosition.y,30,30,null);
		}




	}

}
