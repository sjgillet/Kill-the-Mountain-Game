package Game;

import java.util.Random;
import java.util.ArrayList;

public class Battle {

	private PlayerCombatant player = new PlayerCombatant();
	private Enemy targetEnemy;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy> availableMonsters = new ArrayList<Enemy>();
	private MonsterList monsters;
	private Level lvl;
	private int statTotal;					//total of the player's stats for setting the stats of enemies
	private ArrayList<Item> lootDrop;
	private Random rand = new Random();

	public ArrayList<Enemy> getEnemies()
	{
		return this.enemies;
	}
	public PlayerCombatant getPlayer()
	{
		return this.player;
	}
	public void updateStatTotal(int stats)
	{
		statTotal = stats;
	}




	//	public void addMonster(String name)
	//	{
	//		enemies.add(monsters.getMonster(name));
	//	}





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
		initializeBattle();
		getAvailableMonsters();

		/* Choose opponents */
		while(challengeRating > monsters.getLowestCR(availableMonsters))
			enemies.add(getOpponent(challengeRating)); 

		/* Initialize opponents */
		for(Enemy e : enemies)
		{
			e.Initialize();
		}	
	}

	/**
	 * Generates the battle data for fighting a given boss.
	 * @param boss	The boss of a dungeon for the player 
	 * to fight. 
	 */
	public Battle(Enemy boss)
	{
		initializeBattle();
		boss.Initialize();
		enemies.add(boss);		
	}	

	/**
	 * Generates a battle for fighting a specific opponent
	 * for debugging purposes
	 * @param name		Name of the monster to fight
	 */
	public Battle(String name)
	{
		initializeBattle();
		Enemy e = monsters.getMonster(name);
		e.Initialize();
		enemies.add(e);
	}

	/*
	 * TODO: Implement attacks/skills/etc.
	 * TODO: After attack, check if target is dead. If dead, award XP
	 */


	private void initializeBattle()
	{
		player = GamePanel.player.playerCombatant;
		lvl = GamePanel.levels.get(GamePanel.currentLevel);
		monsters = lvl.monstersList;
	}
	private void getAvailableMonsters()
	{
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
	}


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

	public void end()
	{
		if(player.isDead())
		{

		}
		for(Enemy e : enemies)
		{
			if(e.isDead()){
				player.awardXP(e.getXP());
				Item loot = getLoot(e);
				System.out.println("Got a " + loot.getName() + "!");
			}
		}
	}

	public Item getLoot(Enemy e)
	{
		for(int i = 0; i < e.lootRates.length; i ++)
		{
			double l = rand.nextDouble();
			if(e.lootRates[i] <= l)
				lootDrop.add(e.lootList.get(i));
			else return null;
		}		
		return null;
	}


	//Attack Normally
	public void attack(CombatEntity attacker, CombatEntity target)
	{
		int aAttack = attacker.getPDmg();
		double tDR = target.getPDR();
		target.applyDamage((int)(aAttack * (1 - tDR)));
		if(target.getCurrHP() == 0)
		{
			target.kill();
			end();
		}
	}


	public void attemptRun()
	{
		boolean success;
		int playerSpeed = player.getAcc();
		int enemiesSpeed = 0;
		int enemiesAlive = 0;
		for(Enemy e : enemies)
		{
			if(!e.isDead())
			{
				enemiesSpeed += e.getAcc();
				enemiesAlive++;
			}				
		}
		double runChance = playerSpeed/((0.9 + 0.1*enemiesAlive)*(enemiesSpeed/enemiesAlive));
		runChance = rand.nextDouble() - (0.1*enemiesAlive);
		if(runChance > 0.5)
			success = true;

	}


}
