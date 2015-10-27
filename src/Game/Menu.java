package Game;

import java.util.ArrayList;

public class Menu {
	
	ArrayList<MenuButton> pauseMain = new ArrayList<MenuButton>();
	
	
	public Menu() {
		
	}
	
	//main
	String save = "Save";
	String load = "Load";
	String options = "Options";
	String backToPause = "Back to Pause Main";
	
	//
	
	
	public enum pauseMenu{
		
		MAIN,LOAD,PAUSE_MAIN,INVENTORY,PICK_ITEM,OPTIONS,DISPLAY_STATS,SOUND
		
	}
	
	public enum combatMenu{
		
		COMBAT_MAIN,
		ITEMS,EQUIPMENT_INV,EQUIP_ITEM, 
		INVENTORY,USE_INVENTORY,
		SKILLS,USE_SKILL,
		PICK_TARGET
		
	}
	
	public void menuState(String current) {
		
		switch(current.toLowerCase())
		{
		case "main":
			System.out.println("in Main");
			//save
			//load
			//options
			//back
			break;
			
		/*case "Load":
			//load checkpoint
			//load last save
			break;
			
		case PAUSE_MAIN:
			//game
			//Inventory
			//Player
			break;
			
		case INVENTORY:
			//load inventory into arraylist or display inventory
			break;
			
		case PICK_ITEM:
			//equip
			//use
			//cancel
			break;
			
		case OPTIONS:
			//Gamespeed
			//fullscreen
			//difficulty
			//SOUND
			break;
			
		case DISPLAY_STATS:
			//Display Stats
			//display skills
			break;
			
		case SOUND:
			//Music on/off
			//sound fx on/off
			break;
			*/
		}
		
	}
	
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
	
}
