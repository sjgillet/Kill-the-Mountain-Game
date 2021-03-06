package Game;

import java.util.ArrayList;
import java.util.Map;

public class MonsterList {
	
	private ArrayList<Enemy> allMonsters;		//a list of all monsters
	private ArrayList<Enemy> overworldMonsters;	//easy monsters to fight in the overworld only
	private ArrayList<Enemy> lowLevelMonsters;	//monsters to fight in the first dungeon and/or overworld
	private ArrayList<Enemy> midLevelMonsters;	//monsters to fight in 2nd dungeon and onward
	private ArrayList<Enemy> highLevelMonsters;	//monsters to fight near at at the end of the game
	private ArrayList<Double[]> weights;		//REMOVE?
	private Inventory inv= GamePanel.player.inventory;
	
	public ArrayList<Enemy> getAllMonsters()
	{
		return allMonsters;
	}
	public ArrayList<Enemy> getOverworldMonsters()
	{
		return overworldMonsters;
	}	
	public ArrayList<Enemy> getLowLevelMonsters()
	{
		return lowLevelMonsters;
	}
	public ArrayList<Enemy> getMidLevelMonsters()
	{
		return midLevelMonsters;
	}
	public ArrayList<Enemy> getHighLevelMonsters()
	{
		return highLevelMonsters;
	}
	
	
	private Enemy golem = new Enemy("Golem", "A big rock with legs.", GamePanel.golem, 1.0, 50);
	private Enemy goblin = new Enemy("Goblin", "Ugly little thing. Doesn't look that threatening.", GamePanel.goblin, 0.3, 10);
	private Enemy kobold = new Enemy("Kobold", "It looks like a lizard-person, pretty strange if you ask me. Still, looks like out to get you.", GamePanel.monsterImage, 0.6, 25);
	
	public MonsterList()
	{	
		System.out.println("Generating Monster List");
		
		this.allMonsters = new ArrayList<Enemy>();
		this.overworldMonsters = new ArrayList<Enemy>();
		this.lowLevelMonsters = new ArrayList<Enemy>();
		this.midLevelMonsters = new ArrayList<Enemy>();
		this.highLevelMonsters = new ArrayList<Enemy>();
		this.weights = new ArrayList<Double[]>();
		
		Inventory inv = GamePanel.player.inventory;
		
		//golem
		System.out.println("Generating Monster List");
		golem.setWeights(0.1, 0.3, 0.05, 0.7, 0.05, 0.075, 0.0, 0.2, 0.225, 0.1);
		//REVAMP LOOT LIST FOR NEW ITEM ROLLS
		golem.lootList.add(inv.getItem("Plate Mail"));
		golem.lootRates = new double[]{0.8};
		allMonsters.add(golem);
		overworldMonsters.add(golem);
		midLevelMonsters.add(golem);
		
		//goblin
		goblin.setWeights(0.15, 0.08, 0.12, 0.3, 0.075, 0.125, 0.0, 0.05, 0.05, 0.05);
		//REVAMP LOOT LIST FOR NEW ITEM ROLLS
		goblin.lootList.add(inv.getItem("Rusty Spear"));
		goblin.lootList.add(inv.getItem("Potion of Healing 10"));
		goblin.lootRates = new double[]{0.50, 0.75};
		allMonsters.add(goblin);
		lowLevelMonsters.add(goblin);
		overworldMonsters.add(goblin);
		
		//kobold
		kobold.setWeights(0.125, 0.1, 0.13, 0.5, 0.05, 0.075, 0.0, 0.18, 0.11, 0.07);
		//REVAMP LOOT LIST FOR NEW ITEM ROLLS
		kobold.lootList.add(inv.getItem("Rusty Spear"));
		kobold.lootList.add(inv.getItem("Spear"));
		kobold.lootList.add(inv.getItem("Potion of Healing 10"));
		kobold.lootList.add(inv.getItem("Potion of Healing 30"));
		kobold.lootList.add(inv.getItem("Leather Vest"));
		kobold.lootList.add(inv.getItem("Leather Greaves"));
		kobold.lootList.add(inv.getItem("Pun-Pun's Staff of the Gods"));
		kobold.lootRates = new double[]{0.60, 0.65, 0.80, 0.86, 0.9, 0.95, 0.99, 1.00};
		allMonsters.add(kobold);
		overworldMonsters.add(kobold);
		lowLevelMonsters.add(kobold);
		
		
		//orc
		
	}
	
	/**
	 * Returns the lowest challenge rating of all enemies in 
	 * a given list of monsters.	
	 * @return	The challenge rating of the easiest challenge 
	 * monster
	 */
	public double getLowestCR(ArrayList<Enemy> enemies)
	{
		double min = enemies.get(0).getChallenge();
		for(Enemy e : enemies)		
			if(e.getChallenge() < min)
				min = e.getChallenge();
		
		return min;
	}
	
	/**
	 * Returns a monster with the given name. Searches through
	 * the list of all monsters to return the correct monster
	 * @param name	Name of the monster to search for
	 * @return		Returns the requested monster if found.
	 * 				Returns null if it is not found.
	 */
	public Enemy getMonster(String name)
	{
		System.out.println("Searching for " + name);
		for(Enemy e : allMonsters)
			if(e.getName().equals(name))
				{
					System.out.println(name + " found!");
					return e;
					
				}
		System.out.println(name + " not found.");
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
