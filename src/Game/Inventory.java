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

		head = new InventorySlot(0, 0, new Item("head", false, "helmet"));


	}

	//draw method
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
				g.setColor(Color.white);
				g.fillRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
				g.setColor(Color.black);
				g.drawRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
				g.drawString(head.item.name, Controller.mousePosition.x-70, Controller.mousePosition.y-45);
				g.drawString(head.item.type, Controller.mousePosition.x-70, Controller.mousePosition.y-35);
		}

		//loop through inventory to check if item stats will be displayed
		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++){
				if (main[i][j].isOver()) {

					//if (!inventorySlot.type.equals("empty"))
					g.setColor(Color.white);
					g.fillRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
					g.setColor(Color.black);
					g.drawRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
					g.drawString(main[i][j].item.name, Controller.mousePosition.x-70, Controller.mousePosition.y-45);
					g.drawString(main[i][j].item.type, Controller.mousePosition.x-70, Controller.mousePosition.y-35);
				}
			}
		}

		//loop through equipped items to check if item stats will be displayed
		for (int i = 0; i<equipped.length; i++) {
			if (equipped[i].isOver()){
				g.setColor(Color.white);
				g.fillRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
				g.setColor(Color.black);
				g.drawRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
				g.drawString(equipped[i].item.name, Controller.mousePosition.x-70, Controller.mousePosition.y-45);
				g.drawString(equipped[i].item.type, Controller.mousePosition.x-70, Controller.mousePosition.y-35);
			}
		}

	}

}

