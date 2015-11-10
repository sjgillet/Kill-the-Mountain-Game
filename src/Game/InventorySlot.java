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


		//holding an item, somthing in the slot
		if (GamePanel.player.inventory.currentHeldItem!=null&&item!=null) {
			//temporarily keep track of the item you're holding on to
			Item oldItem = item;
			//item = new Item (GamePanel.player.inventory.currentHeldItem.name,GamePanel.player.inventory.currentHeldItem.onGround, GamePanel.player.inventory.currentHeldItem.type);
			item = GamePanel.player.inventory.currentHeldItem;
			GamePanel.player.inventory.currentHeldItem = oldItem;
			holdingItem = false;
		}
		//holding item , nothing in slot (placing into empty slot)
		else if (GamePanel.player.inventory.currentHeldItem!=null&&item==null){
			holdingItem = false;
			item = GamePanel.player.inventory.currentHeldItem;
			GamePanel.player.inventory.currentHeldItem = null;
		}
		//not holding item, something in the slot
		else if (GamePanel.player.inventory.currentHeldItem==null&&item!=null) {
			GamePanel.player.inventory.currentHeldItem = new Item(item.name, item.onGround, item.type);
			item = null;
			holdingItem = true;
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

		if (item!=null){

			g.drawImage(item.itemArtwork,(int)xPosition,(int)yPosition,50,50,null);

		}


	}

}
