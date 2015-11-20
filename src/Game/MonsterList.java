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
	
	
	private final Enemy golem = new Enemy("Golem", "A big rock with legs.", 1.0, 50);
	private final Enemy goblin = new Enemy("Goblin", "Ugly little thing. Doesn't look that threatening.", 0.3, 10);
	
	public MonsterList()
	{	
		this.allMonsters = new ArrayList<Enemy>();
		this.weights = new ArrayList<Double[]>();
		
		//golem


		System.out.println("Generating Monster List");
		golem.setWeights(0.1, 0.3, 0.05, 0.5, 0.05, 0.075, 0.0, 0.2, 0.225, 0.1);
		golem.setXP(50);
		golem.setArt(GamePanel.golem);
		allMonsters.add(golem);
		
		//goblin
		goblin.setWeights(0.15, 0.08, 0.12, 0.2, 0.075, 0.125, 0.0, 0.05, 0.05, 0.05);
		goblin.setXP(10);
		goblin.lootList.add(GamePanel.player.inventory.getItem("Rusty Spear"));
		goblin.lootList.add(GamePanel.player.inventory.getItem("Potion of Healing 10"));
		goblin.lootRates = new double[]{0.50, 0.75};
		goblin.setArt(GamePanel.goblin);
		allMonsters.add(goblin);
		
		//kobold
		
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
