package Game;

import java.util.Random;
import java.util.ArrayList;

public class Battle {
	private PlayerCombatant player = new PlayerCombatant();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy> availableMonsters = new ArrayList<Enemy>();
	private MonsterList monsters;
	private Level lvl;
	private Random rand = new Random();

	public ArrayList<Enemy> getEnemies()
	{
		return this.enemies;
	}
	public PlayerCombatant getPlayer()
	{
		return this.player;
	}
	public void addMonster(String name)
	{
		enemies.add(monsters.getMonster(name));
	}
	
	
	
	public Battle()
	{
		
	}
	
	/**
	 * Creates a random encounter with monsters to fight.
	 * Opponents are random in number, and the encounter is 
	 * random in difficulty. The monsters fought are randomly
	 * chosen, but are based on the player's level and 
	 * stat total.
	 * @param challengeRating 	A randomly chosen challenge for
	 * an encounter; determines what monsters to fight and how
	 * many.
	 */
	public Battle(double challengeRating)
	{
		lvl = GamePanel.levels.get(GamePanel.currentLevel);
		monsters = lvl.monsters;
		
		/* Collect list of available opponents
		 * based on location and player level */		
		
		if(lvl.name.equals("overworld"))	//gets a list of monsters that only spawn in the overworld
		{
			availableMonsters = monsters.getOverworldMonsters();
		}
		
		
		else if(lvl.name.equals("dungeon"))	//gets a list of dungeon monsters based on player level
		{
			availableMonsters = monsters.getLowLevelMonsters(); 

			if(player.getLevel() >= 10)
			{
				for(Enemy e : monsters.getMidLevelMonsters())
					availableMonsters.add(e);
			}
			if(player.getLevel() >= 20)
			{
				for(Enemy e : monsters.getHighLevelMonsters())
					availableMonsters.add(e);				
			}			
		}
		else	//safety net case. Remove if possible
		{
			availableMonsters = new ArrayList<Enemy>();
			availableMonsters.add(monsters.getOverworldMonsters().get(0));
		}

		/* Choose opponents*/
		while(challengeRating > monsters.getLowestCR(availableMonsters))
			enemies.add(getOpponent(challengeRating)); 
	}
	
	/**
	 * Generates the battle data for fighting a given boss.
	 * @param boss	The boss of a dungeon for the player 
	 * to fight. 
	 */
	public Battle(Enemy boss)
	{
		lvl = GamePanel.levels.get(GamePanel.currentLevel);
		monsters = lvl.monsters;
		enemies.add(boss);
	}	
	
	/**
	 * Generates a battle for fighting a specific opponent
	 * for debugging purposes
	 * @param name		Name of the monster to fight
	 */
	public Battle(String name)
	{
		lvl = GamePanel.levels.get(GamePanel.currentLevel);
		monsters = lvl.monsters;
		enemies.add(monsters.getMonster(name));
	}
	
	/*
	 * TODO: Implement attacks/skills/etc.
	 * TODO: After attack, check if target is dead. If dead, award XP
	 */
		

	/**
	 * Retrieve a random monster whose challenge is less than or equal to a given
	 * double-precision value
	 * @param challengeRating	Determines the highest difficulty creature to be fought
	 * @return	The Enemy to be added to the group of monsters the player will fight
	 */
	private Enemy getOpponent(double challengeRating)
	{
		Enemy opponent;
		
		//retrieve a random monster from possible opponents
		while(true)
		{
			opponent = availableMonsters.get(rand.nextInt(monsters.getAllMonsters().size() - 1));
			if(opponent.getChallenge() >= challengeRating)
			{
				opponent.Initialize();
				challengeRating -= opponent.getChallenge();
				return opponent;
			}
		}
	}
	
}
