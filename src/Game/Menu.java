package Game;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Menu {

	//Create ArrayLists for different menu states
	ArrayList<MenuButton> title = new ArrayList<MenuButton>();
	ArrayList<MenuButton> main = new ArrayList<MenuButton>();
	ArrayList<MenuButton> pauseMain = new ArrayList<MenuButton>();
	ArrayList<MenuButton> options = new ArrayList<MenuButton>();
	ArrayList<MenuButton> load = new ArrayList<MenuButton>();
	ArrayList<MenuButton> currentMenu = title;

	//combat menus
	ArrayList<MenuButton> combatMain = new ArrayList<MenuButton>();
	
	ArrayList<MenuButton> pickTarget = new ArrayList<MenuButton>();
 
	int menuxPos;
	int menuyPos;
	int buttonWidth = 250;
	int buttonHeight = 40;

	/*
	 * Add Menu Buttons to each menu state
	 */
	public Menu() {
		title.add(new MenuButton(buttonWidth, buttonHeight,"New Game"));
		title.add(new MenuButton(buttonWidth, buttonHeight,"Load Game"));
		title.add(new MenuButton(buttonWidth, buttonHeight,"Exit"));

		pauseMain.add(new MenuButton(buttonWidth, buttonHeight, "Game"));
		pauseMain.add(new MenuButton(buttonWidth, buttonHeight, "Inventory"));
		pauseMain.add(new MenuButton(buttonWidth, buttonHeight, "Display Stats"));

		main.add(new MenuButton(buttonWidth, buttonHeight, "Back")); //0
		main.add(new MenuButton(buttonWidth, buttonHeight, "Save"));
		main.add(new MenuButton(buttonWidth, buttonHeight, "Load"));
		main.add(new MenuButton(buttonWidth, buttonHeight, "Options"));

		options.add(new MenuButton(buttonWidth, buttonHeight, "Back to Save/Load"));
		options.add(new MenuButton(buttonWidth, buttonHeight, "GameSpeed"));
		options.add(new MenuButton(buttonWidth, buttonHeight, "FullScreen"));
		options.add(new MenuButton(buttonWidth, buttonHeight, "Difficulty"));
		options.add(new MenuButton(buttonWidth, buttonHeight, "Sound"));

		load.add(new MenuButton(buttonWidth, buttonHeight, "Back to Save/Load"));
		load.add(new MenuButton(buttonWidth, buttonHeight, "Load Last Checkpoint"));
		load.add(new MenuButton(buttonWidth, buttonHeight, "Load Last Save"));

		//combat menus
		combatMain.add(new MenuButton(buttonWidth, buttonHeight, "Attack"));
		combatMain.add(new MenuButton(buttonWidth, buttonHeight, "Skills"));
		combatMain.add(new MenuButton(buttonWidth, buttonHeight, "Items"));
		combatMain.add(new MenuButton(buttonWidth, buttonHeight, "Run"));
		
		pickTarget.add(new MenuButton(buttonWidth, buttonHeight, "Enemy 1"));
		pickTarget.add(new MenuButton(buttonWidth, buttonHeight, "Enemy 2"));
		pickTarget.add(new MenuButton(buttonWidth, buttonHeight, "Enemy 3"));
		pickTarget.add(new MenuButton(buttonWidth, buttonHeight, "nvm"));
		
	}

	/*
	 * Switches the menu state so the game will display the correct
	 * set of buttons at the right times
	 * 
	 * @param String, name of menu state to change to
	 * 
	 */
	public void menuState(String current) {

		switch(current.toLowerCase())
		{
		case "main":
			currentMenu = main;
			//save
			//load
			//options
			break;

		case "load":
			currentMenu = load;
			//load checkpoint
			//load last save
			break;

		case "pausemain":
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
			currentMenu = options;
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


	public void combatState(String current) {

		switch(current.toLowerCase())
		{
		case "combat main":
			currentMenu = combatMain;
			//attack
			//items
			//skills
			//run
			break;

		case "items":
			//Equip
			//Use
			break;

		case "equipment inv":
			//Equipment inventory
			break;

		case "equip item":
			//equip
			//cancel
			break;

		case "inventory":
			//arraylist of inventory
			break;

		case "use inventory":
			//use
			//cancel
			break;

		case "skills":
			//skill menu select
			break;

		case "use skill":
			//use
			//back
			break;

		case "pick target":
			//names of targets
			currentMenu = pickTarget;
			
			break;

		}

	}



	/*
	 * Draw current menu state
	 * 
	 * @param Graphics2D g
	 * @param ArrayList of buttons (menu) to draw
	 */
	public void drawMenu(Graphics2D g, ArrayList<MenuButton> a){

		if (!GamePanel.inBattle){
			menuxPos = (ApplicationUI.windowWidth/2 - (buttonWidth/2));
			menuyPos = (ApplicationUI.windowHeight/2 - ((buttonHeight*a.size())/2));
		}
		else {
			menuxPos = (ApplicationUI.windowWidth - buttonWidth);
			menuyPos = (ApplicationUI.windowHeight/2 - ((buttonHeight*a.size())/2));
		}

		for (int i = 0; i<a.size(); i++) {
			a.get(i).xPosition=menuxPos;
			a.get(i).yPosition = menuyPos+(i*buttonHeight);
			a.get(i).Draw(g);
		}

	}

}
