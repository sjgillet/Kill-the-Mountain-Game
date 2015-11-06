package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Inventory {

	//2d array of slots
	InventorySlot[][] main;
	InventorySlot[] equipped;
	InventorySlot head;
	InventorySlot torso;
	InventorySlot legs;
	InventorySlot arms;
	InventorySlot feet;
	InventorySlot hands;
	int xPosition;
	int yPosition;
	Item currentHeldItem = new Item("holdingNothing", false, "hi");

	public Inventory(int x, int y) {

		xPosition = x;
		yPosition = y;

		equipped = new InventorySlot[4];

		for (int i = 0; i < equipped.length; i++) {
			
			equipped[i] = new InventorySlot(i*50, 250, new Item("test", false, "type"));
		}

		main = new InventorySlot[10][3];
		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++) {
				main[i][j] = new InventorySlot(i*50,j*50,new Item("test", false, "type"));
			}
		}

		head = new InventorySlot(0, 0, new Item("sword", false, "physical"));


	}

	/*
	 * Loops through the arrays of inventory slots and displays them
	 * 
	 * @param Graphics2D g
	 * @return N/A
	 */
	public void drawInventory(Graphics2D g) {
		
		Font font = new Font("Iwona Heavy",Font.BOLD,12);
		g.setFont(font);
		g.setColor(Color.black);

		for (int i = 0; i<equipped.length; i++) {
			equipped[i].xPosition = (ApplicationUI.windowWidth/2) - ((50*main.length)/2) + (i*50);
			equipped[i].yPosition = (ApplicationUI.windowHeight/2) - 135;
			equipped[i].drawInventorySlot(g);
		}

		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++){
				main[i][j].xPosition = (ApplicationUI.windowWidth/2) - ((50*main.length)/2) + (i*50); 
				main[i][j].yPosition = (ApplicationUI.windowHeight/2) - ((50*main[i].length)/2) + (j*50);
				main[i][j].drawInventorySlot(g);
			}
		}

		head.xPosition = (ApplicationUI.windowWidth/2) + ((50*main.length)/2) + 50;
		head.yPosition = main[9][0].yPosition;
		head.drawInventorySlot(g);
		
		if (head.isOver()) {
			GamePanel.player.inventory.head.item.drawItemToolTip(g, head.item);
		}
		
		//loop through inventory to check if item stats will be displayed
		for (int i = 0; i<GamePanel.player.inventory.main.length; i++) {
			for (int j = 0; j<GamePanel.player.inventory.main[i].length; j++){
				if (GamePanel.player.inventory.main[i][j].isOver()) {
					GamePanel.player.inventory.main[i][j].item.drawItemToolTip(g, main[i][j].item);
				}
			}
		}

		//loop through equipped items to check if item stats will be displayed
		for (int i = 0; i<GamePanel.player.inventory.equipped.length; i++) {
			if (GamePanel.player.inventory.equipped[i].isOver()){
				GamePanel.player.inventory.equipped[i].item.drawItemToolTip(g, equipped[i].item);
			}
		}

		
	}

}

