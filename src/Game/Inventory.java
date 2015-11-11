package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Inventory {

	//2d array of slots
	InventorySlot[][] main;
	InventorySlot[] equipped;

	InventorySlot weapon;
	InventorySlot head;
	InventorySlot torso;
	InventorySlot legs;
	InventorySlot arms;

	int xPosition;
	int yPosition;
	Item currentHeldItem = null;
	
	public ArrayList<Item> weapons; 


	public Inventory(int x, int y) {

		xPosition = x;
		yPosition = y;

		equipped = new InventorySlot[4];

		for (int i = 0; i < equipped.length; i++) {

			equipped[i] = new InventorySlot(i*50, 250, null);
		}

		main = new InventorySlot[10][3];
		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++) {
				main[i][j] = new InventorySlot(i*50,j*50,null);
			}
		}

		main[1][1].item = new Item("test2", false, "whatever");
		head = new InventorySlot(0, 0, new Item("sword", false, "physical"));


		compileItems();		
	}

	
	private void compileItems()
	{
		weapons = new ArrayList<Item>();
		Item longsword = new Item("Longsword", "Weapon", 10);		weapons.add(longsword);
		Item plateMail = new Item("Plate Mail", "Armor", 50);		weapons.add(plateMail);
		Item steelHelm = new Item("Steel Helm", "Helmet", 25);		weapons.add(steelHelm);
		Item steelGnt = new Item("Steel Gauntlets", "Gloves", 15);	weapons.add(steelGnt);
		Item steelBoots = new Item("SteelBoots", "Boots", 10);		weapons.add(steelBoots);
		Item naturalWeapon = new Item("Natural Weapon", "No modifiers", 1.0, 1.0);
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

		if (head.isOver()&&GamePanel.player.inventory.head.item!=null) {
			GamePanel.player.inventory.head.item.drawItemToolTip(g, head.item);
		}

		//loop through inventory to check if item stats will be displayed
		for (int i = 0; i<GamePanel.player.inventory.main.length; i++) {
			for (int j = 0; j<GamePanel.player.inventory.main[i].length; j++){
				if (GamePanel.player.inventory.main[i][j].item!=null){
					if (GamePanel.player.inventory.main[i][j].isOver()) {
						GamePanel.player.inventory.main[i][j].item.drawItemToolTip(g, main[i][j].item);
					}
				}
			}
		}

		//loop through equipped items to check if item stats will be displayed
		for (int i = 0; i<GamePanel.player.inventory.equipped.length; i++) {
			if (GamePanel.player.inventory.equipped[i].item!=null) {
				if (GamePanel.player.inventory.equipped[i].isOver()){
					GamePanel.player.inventory.equipped[i].item.drawItemToolTip(g, equipped[i].item);
				}
			}
		}

		if (currentHeldItem!=null) {
			g.drawImage(currentHeldItem.itemArtwork,(int)Controller.mousePosition.x,(int)Controller.mousePosition.y,30,30,null);
		}
	}

}

