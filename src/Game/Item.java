package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Item {

	String name = "";
	boolean onGround;
	int attack;
	double speed;
	int mana;
	int health;
	int armor;
	int xID;
	int yID;
	BufferedImage itemArtwork = null;
	
	//item types
	String type;

	public Item(String name, boolean onGround, String type) {

		this.name = name;
		this.onGround = onGround;
		this.type = type;
		
		if (name.equals("sword")){
			itemArtwork = GamePanel.sword;
		}
		
		else if (name.equals("test2")){
			itemArtwork = GamePanel.monsterImage;
		}

	}

	/*
	 * Draw method for the info box when hovering over an inventorySlot
	 * 
	 * @param Graphics2D g
	 * @param Item i, the item that will have it's stats/name displayed
	 * 
	 * @return N/A
	 */
	public void drawItemToolTip(Graphics2D g, Item item) {

		if (item!=null){

			g.setColor(Color.white);
			g.fillRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
			g.setColor(Color.black);
			g.drawRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
			g.drawString(item.name, Controller.mousePosition.x-70, Controller.mousePosition.y-45);
			g.drawString(item.type, Controller.mousePosition.x-70, Controller.mousePosition.y-35);

		}
	}

}
