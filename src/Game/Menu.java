package Game;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Menu {



	ArrayList<MenuButton> main = new ArrayList<MenuButton>();
	ArrayList<MenuButton> currentMenu = main;
	ArrayList<MenuButton> pauseMain = new ArrayList<MenuButton>();

	int menuxPos;
	int menuyPos;
	int buttonWidth = 250;
	int buttonHeight = 40;
	
	//main
	String save = "Save";
	String load = "Load";
	String options = "Options";
	String backToPause = "Back to Pause Main";

	//pause main
	String game = "Game";
	String inventory = "Inventory";
	String displaystats = "DisplayStats";

	public Menu() {
		main.add(new MenuButton(buttonWidth, buttonHeight, backToPause, 50, 50)); //0
		main.add(new MenuButton(buttonWidth, buttonHeight, save, 90, 50));
		main.add(new MenuButton(buttonWidth, buttonHeight, load, 50, 90));
		main.add(new MenuButton(buttonWidth, buttonHeight, options, 90, 90));
		pauseMain.add(new MenuButton(buttonWidth, buttonHeight, game , 45, 10));

	}



	public void menuState(String current) {

		switch(current.toLowerCase())
		{
		case "main":
			currentMenu = main;
			//save
			//load
			//options
			if (main.get(0).isOver()&&Controller.mousePressed) {
				main.get(0).isPushed();
				System.out.println("got here");
			}
			break;

		case "Load":
			//load checkpoint
			//load last save
			break;

		case "pausemain":
			System.out.println("in pause main");
			currentMenu = pauseMain;
			//game
			//Inventory
			//displaystats
			break;

		case "inventory":
			//load inventory into arraylist or display inventory
			break;

		case "pickitem":
			//equip
			//use
			//cancel
			break;

		case "options":
			//Gamespeed
			//fullscreen
			//difficulty
			//SOUND
			break;

		case "displaystats":
			//Display Stats
			//display skills
			break;

		case "sound":
			//Music on/off
			//sound fx on/off
			break;
		}

	}

	/*
	public void combatState(combatMenu current) {

		switch(current)
		{
		case COMBAT_MAIN:
			//attack
			//items
			//skills
			//run
			break;

		case ITEMS:
			//Equip
			//Use
			break;

		case EQUIPMENT_INV:
			//Equipment inventory
			break;

		case EQUIP_ITEM:
			//equip
			//cancel
			break;

		case INVENTORY:
			//arraylist of inventory
			break;

		case USE_INVENTORY:
			//use
			//cancel
			break;

		case SKILLS:
			//skill menu select
			break;

		case USE_SKILL:
			//use
			//back
			break;

		case PICK_TARGET:
			//names of targets
			break;

		}

	}

	 */
	public void drawMenu(Graphics2D g, ArrayList<MenuButton> a){

		menuxPos = (ApplicationUI.windowWidth/2 - (buttonWidth/2));
		menuyPos = (ApplicationUI.windowHeight/2 - ((buttonHeight*a.size())/2));
		
		for (int i = 0; i<a.size(); i++) {
			a.get(i).xPosition=menuxPos;
			a.get(i).yPosition = menuyPos+(i*buttonHeight);
			a.get(i).Draw(g);
		}

	}

}
