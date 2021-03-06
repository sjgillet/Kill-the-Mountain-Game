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
	public ArrayList<Item> armors;
	public ArrayList<Item> consumables;
	public ArrayList<Item> allItems;

	//Item lists containing beginner-tier items. ex. leather armor
	public ArrayList<Item> weaponsT1; 
	public ArrayList<Item> armorsT1;
	public ArrayList<Item> consumablesT1;
	
	//Item lists containing mid-level items. ex. Steel armors, weapons
	public ArrayList<Item> weaponsT2; 
	public ArrayList<Item> armorsT2;
	public ArrayList<Item> consumablesT2;
	
	//Item lists containing top-tier items. ex. Mithral weapons, armors
	public ArrayList<Item> weaponsT3; 
	public ArrayList<Item> armorsT3;
	public ArrayList<Item> consumablesT3;


	public Inventory(int x, int y) {

		xPosition = x;
		yPosition = y;

		equipped = new InventorySlot[4];
		

		for (int i = 0; i < equipped.length; i++) {

			equipped[i] = new InventorySlot(i*50, 250, null, null);
		}

		main = new InventorySlot[10][3];
		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++) {
				main[i][j] = new InventorySlot(i*50,j*50,null,null);
			}
		}

		head = new InventorySlot(0, 0, null, "helmet");
		torso = new InventorySlot(0, 0, null, "torso");
		arms = new InventorySlot(0, 0, null, "arms");
		legs = new InventorySlot(0, 0, null, "legs");


		compileItems();		
	}

	/*
	 * Puts item that was clicked on in the game into the overworld, if space available
	 * 
	 * @param Item to be picked up
	 * @return boolean true if space was available and item was successfully picked up
	 */
	public boolean pickUpItem(Item item) {

		boolean hasEmptySlot = false;

		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++) {
				if (main[i][j].item==null){
					main[i][j].item = item;
					hasEmptySlot = true;
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Drops an item from the inventory and places it where the player is standing
	 */
	public void dropFromInventory() {

		if (GamePanel.levels.size()>GamePanel.currentLevel){

			//Item temp = new Item("test2", true, "test2");
			
			int x = (int)(GamePanel.player.xpos/32);
			int y = (int)(GamePanel.player.ypos/32);

			if (x>=0&&y>=0&&x<GamePanel.levels.get(GamePanel.currentLevel).width&&y<GamePanel.levels.get(GamePanel.currentLevel).height){
				currentHeldItem.xPosition = x*32;
				currentHeldItem.yPosition = y*32;
				GamePanel.levels.get(GamePanel.currentLevel).tileMap[x][y].itemsOnThisTile.add(currentHeldItem);
				
				currentHeldItem = null;
			}
		}
	}

	private void compileItems()
	{
		weaponsT1 = new ArrayList<Item>();
		armorsT1 = new ArrayList<Item>();
		consumablesT1 = new ArrayList<Item>();
		weaponsT2 = new ArrayList<Item>();
		armorsT2 = new ArrayList<Item>();
		consumablesT2 = new ArrayList<Item>();
		weaponsT3 = new ArrayList<Item>();
		armorsT3 = new ArrayList<Item>();
		consumablesT3 = new ArrayList<Item>();
		
		weapons = new ArrayList<Item>();
		armors = new ArrayList<Item>();
		consumables = new ArrayList<Item>();
		allItems = new ArrayList<Item>();
		
		Item longsword = new Item("Longsword", 
				"A plain but well-crafted blade of steel. Stick 'em with the pointy end.", 1.10d, 1.00d);
		
		Item plateMail = new Item("Plate Mail", 
				"Heavy, durable, and only mildy uncomfortable armor fashioned from plates of steel.",
				"torso", 50, 25);
		Item steelHelm = new Item("Steel Helm",
				"Round and durable helmet that causes terrible hat hair.",
				"helmet", 25, 10);
		Item steelGnt = new Item("Steel Gauntlets",
				"Plates of steel sewn into a pair of thick leather gloves.",
				"arms", 15, 10);
		Item steelBoots = new Item("Steel Boots",
				"Thick leather boots with plates of steel placed over the shins. Try and stub your toe now!",
				"legs", 10,5);
		
		Item potHeal10 = new Item("Potion of Healing 10",
				"A vial of a thin red liquid that gleams in the light.", 10, 0, false);
		Item potHeal50 = new Item("Potion of Healing 50",
				"A large jug of a familiar red potion.", 50, 0, false);
		Item antidote = new Item("Antidote",
				"A vial of a thin red liquid that gleams in the light.", 0, 0, true);
		/* Compile all items into one list for referencing for loot drops
		 * Organized by quality into tiers for ease of access*/
		allItems = new ArrayList<Item>();
		//begin tier 1 items
		weaponsT1.add(longsword);
		weaponsT2.add(longsword);
		weaponsT3.add(longsword);
		armorsT1.add(longsword);
		armorsT2.add(longsword);
		armorsT3.add(longsword);
		consumablesT1.add(longsword);
		consumablesT2.add(longsword);
		consumablesT3.add(longsword);
		//end tier 1 items
		
		//begin tier 2 items
		
		//end tier 2 items
		
		//begin tier 3 items
		
		//end tier 3 items
		
		equipped[0].item = longsword;
		
		
	}
	public Item randomItem(String type){
		Item tempItem = null;
		if(type.equals("consumable")){
			int tier = 0;
			int roll = GamePanel.randomNumber(1, 100);
			ArrayList<Item> consumables1 = new ArrayList<Item>();
			if(roll>=95){//5%
				consumables1 = consumablesT3;
			}
			else if(roll<95&&roll>=80){//15%
				consumables1 = consumablesT2;
			}
			else{//80%
				consumables1 = consumablesT1;
			}
			if(consumables.size()>0){
				tempItem=consumables.get(GamePanel.randomNumber(0, consumables1.size()-1));
			}
		}
		else if(type.equals("weapon")){
			int tier = 0;
			int roll = GamePanel.randomNumber(1, 100);
			ArrayList<Item> damageItems = new ArrayList<Item>();
			if(roll>=95){//5%
				damageItems = consumablesT3;
			}
			else if(roll<95&&roll>=80){//15%
				damageItems = consumablesT2;
			}
			else{//80%
				damageItems = consumablesT1;
			}	
			if(damageItems.size()>0){
				tempItem=damageItems.get(GamePanel.randomNumber(0, damageItems.size()-1));
			}
		}
		else if(type.equals("armor")){
			int tier = 0;
			int roll = GamePanel.randomNumber(1, 100);
			ArrayList<Item> armors1 = new ArrayList<Item>();
			if(roll>=95){//5%
				armors1 = consumablesT3;
			}
			else if(roll<95&&roll>=80){//15%
				armors1 = consumablesT2;
			}
			else{//80%
				armors1 = consumablesT1;
			}	
			if(armors1.size()>0){
				tempItem=armors1.get(GamePanel.randomNumber(0, armors1.size()-1));
			}
		}
		return weaponsT1.get(0);//will cause null pointer exceptions in the future!(needs to return a clone of the item instead of
		//returning that item in specific
	}
	/**
	 * Retrieves an item from the item lists with the given name
	 * @param name 	Name of the item to be retrieved
	 * @return		Item queried, or null if not found
	 */
	public Item getItem(String name)
	{
//		for(Item i : weapons)
//			if(i.getName().equals(name))
//				return i;
//		for(Item i : armors)
//			if(i.getName().equals(name))
//				return i;
//		for(Item i : consumables)
//			if(i.getName().equals(name))
//				return i;
		for(Item i : allItems)
			if(i.getName().equals(name))
				return i;	
		return null;
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

		//equipped inventory placement
		for (int i = 0; i<equipped.length; i++) {
			equipped[i].xPosition = (ApplicationUI.windowWidth/2) - ((50*main.length)/2) + (i*50);
			equipped[i].yPosition = (ApplicationUI.windowHeight/2) - 135;
			equipped[i].drawInventorySlot(g);
		}

		//main inventory placement
		for (int i = 0; i<main.length; i++) {
			for (int j = 0; j<main[i].length; j++){
				main[i][j].xPosition = (ApplicationUI.windowWidth/2) - ((50*main.length)/2) + (i*50); 
				main[i][j].yPosition = (ApplicationUI.windowHeight/2) - ((50*main[i].length)/2) + (j*50);
				main[i][j].drawInventorySlot(g);
			}
		}

		//helmet/head inventory slot placement
		head.xPosition = (ApplicationUI.windowWidth/2) + ((50*main.length)/2) + 25;
		head.yPosition = main[9][0].yPosition;
		head.drawInventorySlot(g);

		if (head.isOver()&&GamePanel.player.inventory.head.item!=null) {
			GamePanel.player.inventory.head.item.drawItemToolTip(g, head.item);
		}

		//torso inventory slot placement
		torso.xPosition = (ApplicationUI.windowWidth/2) + ((50*main.length)/2) + 25;
		torso.yPosition = main[9][0].yPosition + 50;
		torso.drawInventorySlot(g);

		//torso tooltip
		if (torso.isOver()&&GamePanel.player.inventory.torso.item!=null) {
			GamePanel.player.inventory.torso.item.drawItemToolTip(g, torso.item);
		}

		//arms inventory slot placement
		arms.xPosition = (ApplicationUI.windowWidth/2) + ((50*main.length)/2) + 75;
		arms.yPosition = main[9][1].yPosition;
		arms.drawInventorySlot(g);

		//arms tooltip
		if (arms.isOver()&&GamePanel.player.inventory.arms.item!=null) {
			GamePanel.player.inventory.arms.item.drawItemToolTip(g, arms.item);
		}

		//legs inventory slot placement
		legs.xPosition = (ApplicationUI.windowWidth/2) + ((50*main.length)/2) + 25;
		legs.yPosition = main[9][2].yPosition;
		legs.drawInventorySlot(g);

		//legs tooltip
		if (legs.isOver()&&GamePanel.player.inventory.legs.item!=null) {
			GamePanel.player.inventory.legs.item.drawItemToolTip(g, legs.item);
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

		//if holding an item, draw the item next to the cursor
		if (currentHeldItem!=null) {
			g.drawImage(currentHeldItem.itemArtwork,(int)Controller.mousePosition.x,(int)Controller.mousePosition.y,30,30,null);
		}
	}

}
