package Game;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Battle {

	private PlayerCombatant player = new PlayerCombatant();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy> availableMonsters = new ArrayList<Enemy>();
	private MonsterList monsters;
	private Level lvl;
	private int statTotal;					//total of the player's stats for setting the stats of enemies
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
	
	//Attack Normally
	public void Attack(CombatEntity attacker, CombatEntity target)
	{
		int aAttack = attacker.getPDmg();
		double tDR = target.getPDR();
		target.applyDamage((int)(attacker.getPDmg() * (1 - target.getPDR())));
	}
	
	
	//Run Away
	
	public void DrawEnemyNamePlate(Graphics2D g, int x, int y, Enemy enemy){
		Font font = new Font("Iwona Heavy",Font.PLAIN, 20);
		g.setFont(font);
		g.setColor(Color.BLACK);
		
		//draw player's nameplate
		int namePlateWidth = 250;
		int namePlateHeight = 85;
		//draw background
		g.setColor(Color.lightGray);
		g.fillRect(x, y, namePlateWidth, namePlateHeight);
		//draw outline
		g.setColor(Color.green);
		g.drawRect(x, y, namePlateWidth, namePlateHeight);
		//draw player's name
		g.setColor(Color.black);
		g.drawString(enemy.getName(), x+20, y+20);
		
		
		int hpBarLength = enemy.hp*2;
		if(hpBarLength>200){
			hpBarLength=200;
		}
		int spBarLength = enemy.sp*4;
		if(spBarLength>200){
			spBarLength=200;
		}
		
		//draw player's health bar
		g.setColor(new Color(255,0,0,200));
		g.fillRect(x+20, y+30, hpBarLength, 30);//background of bar
		g.setColor(new Color(0,193,22));
		g.fillRect(x+20, y+30, (int)(double)((double)hpBarLength*((double)enemy.currHP/(double)enemy.hp)), 30);
		
		//draw player's sp bar
		g.setColor(new Color(50,50,50,200));
		g.fillRect(x+20, y+65, spBarLength, 15);//background of bar
		g.setColor(Color.cyan);
		g.fillRect(x+20, y+65, (int)(double)((double)spBarLength*((double)enemy.currSP/(double)enemy.sp)), 15);
	}
	
	public void Draw(Graphics2D g){
		int sceneWidth = 600;
		int sceneHeight = 400;
		
		g.setColor(Color.green);
		g.fillRect((ApplicationUI.windowWidth/2)-(sceneWidth/2)-1, (ApplicationUI.windowHeight/2)-(sceneHeight/2)-1, sceneWidth+2, sceneHeight+2);
		g.setColor(Color.lightGray);
		g.fillRect((ApplicationUI.windowWidth/2)-(sceneWidth/2), (ApplicationUI.windowHeight/2)-(sceneHeight/2), sceneWidth, sceneHeight);
		Font font = new Font("Iwona Heavy",Font.PLAIN, 20);
		g.setFont(font);
		g.setColor(Color.BLACK);
		PlayerCombatant plr = getPlayer();
		Enemy e = getEnemies().get(0);

		//draw player's nameplate
		int namePlateWidth = 250;
		int namePlateHeight = 85;
		//draw background
		g.setColor(Color.lightGray);
		g.fillRect(ApplicationUI.windowWidth-namePlateWidth, 0, namePlateWidth, namePlateHeight);
		//draw outline
		g.setColor(Color.green);
		g.drawRect(ApplicationUI.windowWidth-namePlateWidth, 0, namePlateWidth, namePlateHeight);
		//draw player's name
		g.setColor(Color.black);
		g.drawString(plr.getName(), (ApplicationUI.windowWidth-namePlateWidth)+20, 20);


		int hpBarLength = plr.hp*2;
		if(hpBarLength>200){
			hpBarLength=200;
		}
		int spBarLength = plr.sp*4;
		if(spBarLength>200){
			spBarLength=200;
		}

		//draw player's health bar
		g.setColor(new Color(255,0,0,200));
		g.fillRect((ApplicationUI.windowWidth-namePlateWidth)+20, 30, hpBarLength, 30);//background of bar
		g.setColor(new Color(0,193,22));
		g.fillRect((ApplicationUI.windowWidth-namePlateWidth)+20, 30, (int)(double)((double)hpBarLength*((double)plr.currHP/(double)plr.hp)), 30);

		//draw player's sp bar
		g.setColor(new Color(50,50,50,200));
		g.fillRect((ApplicationUI.windowWidth-namePlateWidth)+20, 65, spBarLength, 15);//background of bar
		g.setColor(Color.cyan);
		g.fillRect((ApplicationUI.windowWidth-namePlateWidth)+20, 65, (int)(double)((double)spBarLength*((double)plr.currSP/(double)plr.sp)), 15);

		//draw enemy nameplates
		for(int i = 0; i<getEnemies().size();i++){
			DrawEnemyNamePlate(g, 0, i*100, getEnemies().get(i));
		}
		g.setColor(Color.white);
		//				g.drawString(bat.getPlayer().getName(), 5, ApplicationUI.windowHeight - 170);
		//				g.drawString(bat.getEnemies().get(0).getName(), 150, ApplicationUI.windowHeight - 170);
		g.drawString(plr.getName() + " HP: " + plr.getCurrHP() + " | DMG: " + plr.getPDmg()
		+ "VS! " 
		+ e.getName() + " HP: " + e.getCurrHP() + " | DMG: " + e.getPDmg(),
		5, ApplicationUI.windowHeight - 140);
	}
	
	
	
	
	
	
	

}
