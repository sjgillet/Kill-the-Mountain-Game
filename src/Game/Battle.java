package Game;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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

	private int escapesAttempted = 0;

	private boolean playerTurn;
	private ArrayList<CombatEntity> turnOrder;
	private int turnOrderIndex;

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

		/* Dialogue message to start battle	 */
		GamePanel.dialog.addMessage("BATTLE START!");
		String listOpponents = "";
		for(int i = 0; i < enemies.size(); i++)
		{
			if(i == enemies.size() - 1 && enemies.size() != 1)
				listOpponents += " and ";
			listOpponents += "a " + enemies.get(i).getName() + ", ";
		}
		GamePanel.dialog.addMessage(player.getName() + "VS! "  + listOpponents);
		GamePanel.dialog.addMessage("FIGHT!");

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

		//dialog message on battle start
		GamePanel.dialog.addMessage("BATTLE START!");
		String listOpponents = "";
		for(int i = 0; i < enemies.size(); i++)
		{
			if(i == enemies.size() - 1 && enemies.size() != 1)
				listOpponents += " and ";
			listOpponents += "a " + enemies.get(i).getName() + ", ";
		}
		GamePanel.dialog.addMessage(player.getName() + " VS! "  + listOpponents);
		GamePanel.dialog.addMessage("FIGHT!");

		setTurnOrder();

	}

	/**
	 * Initializes the player, level, and monsters objects, nothing else. 
	 * Do not input code to manipulate these objects
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

	public void setTurnOrder()
	{
		int i = 0;
		turnOrder = new ArrayList<CombatEntity>();
		ArrayList<Enemy> en = enemies;

		//add player and enemies to turn order list
		for(Enemy e : enemies)
			turnOrder.add(e);
		turnOrder.add(player);

		//sort turn order by speed
		for(i = 0; i < turnOrder.size() - 1; i++)
		{
			boolean sorted = true;
			if(turnOrder.get(i).getSpeed() < turnOrder.get(i+1).getSpeed())
			{
				System.out.println("Swap");
				CombatEntity temp = turnOrder.get(i+1);
				turnOrder.set(i+1, turnOrder.get(i));
				turnOrder.set(i, temp);
				sorted = false;
			}
			if(!sorted) i = -1;
		}
		System.out.print("Turn Order: "); 
		for(i = 0; i < turnOrder.size(); i++) 
			System.out.print(turnOrder.get(i).getName() + ": " + turnOrder.get(i).getSpeed() + ", ");
		changeTurn(0);
	}

	/** 
	 * Changes the acting combat entity to the next in the turn order
	 * At the end of each round, reassess turn order.
	 */
	public void changeTurn()
	{
		System.out.println("combatants: " + turnOrder.size() + "\tturnIndex: "  + turnOrderIndex);
		if(turnOrderIndex >= turnOrder.size() - 1)
		{
			setTurnOrder();
			changeTurn(0);
		}
		else changeTurn(turnOrderIndex + 1);
	}
	/**
	 * To specified spot in the turn order. 
	 * If it's now the player's turn, wait for user input.
	 * If it's now an enemy's turn, the enemy will take an action
	 * based on its situation
	 * @param orderIndex	entity whose turn it is. 
	 */
	public void changeTurn(int orderIndex)
	{
		System.out.println("New Order Index: " + orderIndex);
		turnOrderIndex = orderIndex;
		if(turnOrder.get(orderIndex) != player)
		{
			playerTurn = false;
			//get enemy, pass to take action
			for(Enemy e : enemies)
				if(e == turnOrder.get(orderIndex))
					takeAction(e);
		}
		else playerTurn = true;
	}

	/**
	 * Ends the battle event, whether victory, defeat, or running
	 * Awards XP and loot to the player
	 * If the player had died, lose XP for current level.
	 */
	public void battleEnd()
	{
		if(player.isDead())
		{
			player.setXP(0);
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

	public void takeAction(Enemy e)
	{
		if(!e.isDead())
		{
			//determine action by weight of decision
			double attackWt = 100;						//monster attacks normally
			double skillWt = 10*e.getCurrSP();			//monster makes special attack. Less likely for 'tired' opponents
			double runWt = 0;							//monster runs. More likely for injured opponents
			if(e.getCurrHP() < e.getHP()/2)
				runWt = (int)( (double)((e.getHP() - e.getCurrHP())/e.getHP()) *100);	
			double totalWt = attackWt + skillWt + runWt;
			double attackCh = (double)(attackWt/totalWt);
			double skillCh = (double)(skillWt/totalWt + attackCh);
			double runCh = (double)(runWt/totalWt + skillCh);
			System.out.println("Run: " + runCh + " Skill: " + skillCh + " Attack: " + attackCh);

			double pick = rand.nextDouble();
			pick = (attackCh + skillCh)/2;
			if(pick > skillCh)
				attemptRun(e);
			else if(pick > attackCh)
			{
				double normStr = e.getStrength();
				double normAcc = e.getAcc();
				e.setStrength((int)(double)(normStr*1.5d));
				e.setAcc((int)(double)(normAcc*0.8));
				e.updateStats();
				GamePanel.dialog.addMessage("The " + e.getName() + " attacks with a surge of energy! " + e.getStrength());
				attack(e,player);
				e.setStrength((int)normStr);
				e.setAcc((int)normAcc);				
			}
			else 
				attack(e,player);
		}
		changeTurn();
	}

	//Attack Normally
	public void attack(CombatEntity attacker, CombatEntity target)
	{
		
		double accuracy = (attacker.getAcc() + rand.nextInt(attacker.getLuck()))*(attacker.getSpeed()/attacker.getEvasion());
		double evade = target.getEvasion() + rand.nextInt(target.getLuck());
		System.out.println(attacker.getAcc() + " " + target.getEvasion() + " " + accuracy + " " + evade + " " + attacker.getSpeed()/attacker.getAcc());
		double hitChance = 0.5 + (accuracy-evade)/50.0;
		if(hitChance > 1.0)
			hitChance = 1.0;
		else if (hitChance < 0.35)
			hitChance = 0.35;		
		if(rand.nextDouble() < hitChance)
		{
			System.out.println("Strength = " + attacker.getStrength());
			int aAttack = attacker.getPDmg();
			double tDR = target.getPDR();
			int attackDamage = (int)(aAttack * (1 - tDR));
			target.applyDamage(attackDamage);

			GamePanel.dialog.addMessage(attacker.getName() + " dealt "
					+ attackDamage + " to " + target.getName() + "!");
			System.out.println(attacker.getName() + " dealt "
					+ attackDamage + " to " + target.getName() + "!");
			if(target.getCurrHP() == 0)
			{
				GamePanel.dialog.addMessage(target.getName() + " died!");
				//battle end?
			}
		}
		else 
		{
			GamePanel.dialog.addMessage(attacker.getName() + " missed!");
			//			if(GamePanel.dialog.currentMessage<GamePanel.dialog.messages.size()-1){
			//				GamePanel.dialog.currentMessage+=1;
			//				GamePanel.dialog.currentIndex=0;
			//			}
			System.out.println(attacker.getName() + " missed!");
		}
		if(attacker == player)
			changeTurn();
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

		PlayerCombatant plr = getPlayer();
		ArrayList<Enemy> enemies = getEnemies();
		Enemy e = enemies.get(0);
		int sceneWidth = ApplicationUI.windowWidth/2;
		int sceneHeight = ApplicationUI.windowHeight/2;
		int sceneX = (ApplicationUI.windowWidth/2)-(sceneWidth/2);
		int sceneY = (ApplicationUI.windowHeight/2)-(sceneHeight/2);
		//draw the background for the scene

		g.setColor(Color.green);
		g.fillRect(sceneX-1, sceneY-1, sceneWidth+2, sceneHeight+2);
		g.setColor(Color.lightGray);
		g.fillRect(sceneX, sceneY, sceneWidth, sceneHeight);
		
		//draw the player
		g.drawImage(plr.battleArt,sceneX+sceneWidth-(sceneWidth/3),sceneY+sceneHeight-(sceneHeight/2),sceneWidth/3,sceneHeight/2,null);
		//draw the enemies
		for(int i = 0; i<enemies.size();i++){
			Enemy currentEnemy = enemies.get(i);
			int yDifference = sceneWidth/(enemies.size()+1);
			g.drawImage(currentEnemy.battleArt, sceneX, sceneY+(yDifference*i), sceneWidth/3, sceneHeight/2, null);
		}
		
		Font font = new Font("Iwona Heavy",Font.PLAIN, 20);
		g.setFont(font);
		g.setColor(Color.BLACK);
		

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
		for(int i = 0; i<enemies.size();i++){
			DrawEnemyNamePlate(g, 0, i*100, enemies.get(i));
		}
		g.setColor(Color.white);
		//				g.drawString(bat.getPlayer().getName(), 5, ApplicationUI.windowHeight - 170);
		//				g.drawString(bat.getEnemies().get(0).getName(), 150, ApplicationUI.windowHeight - 170);
		g.drawString(plr.getName() + " HP: " + plr.getCurrHP() + " | DMG: " + plr.getPDmg()
		+ "VS! " 
		+ e.getName() + " HP: " + e.getCurrHP() + " | DMG: " + e.getPDmg(),
		5, ApplicationUI.windowHeight - 140);
	}
	public void attemptRun()
	{
		GamePanel.dialog.addMessage("NOPE!");
		int playerSpeed = player.getSpeed();
		int enemiesSpeed = 0;
		int enemiesAlive = 0;
		int enemiesQuicker = 0;
		int enemiesSlower = 0;

		escapesAttempted++;
		for(Enemy e : enemies)
		{
			if(!e.isDead())
			{
				enemiesSpeed += e.getSpeed();
				if(e.getSpeed() > playerSpeed)
					enemiesQuicker++;
				else 
					enemiesSlower++;
				enemiesAlive++;
			}				
		}

		//double runChance = playerSpeed/((0.9 + 0.1*enemiesAlive)*(enemiesSpeed/enemiesAlive));
		double runChance = rand.nextDouble() - (0.1*enemiesQuicker) + (0.05*enemiesSlower) + (0.1* escapesAttempted);
		//SUCCESSFUL ESCAPE
		if(runChance > 0.5)
		{
			GamePanel.dialog.addMessage("You got away!");
			battleEnd();
		}
		//FAILED ESCAPE, NEXT TURN;
		else
		{
			GamePanel.dialog.addMessage("You tripped on a rock and fell! They're still there!");
			changeTurn();
		}

	}

	public void attemptRun(Enemy e)
	{
		GamePanel.dialog.addMessage(e.getName() + " is making a break for it...");
		int enemySpeed = e.getSpeed();
		double speedDifference = (e.getSpeed()/player.getSpeed()) - 1.0;
		speedDifference *= speedDifference;
		if(speedDifference > 0.3) 	speedDifference = 0.3;
		if(speedDifference < -0.3) 	speedDifference = -0.3;
		double runChance = 0.5 + speedDifference;
		//enemy successfully escaped
		if(rand.nextDouble() < runChance)
		{
			GamePanel.dialog.addMessage(e.getName() + " got away!");
			//remove from active participation, award xp
			player.awardXP(e.getXP()/2);
			e.run();

		}
		else GamePanel.dialog.addMessage("Oh, no you don't! " + e.getName() + " didn't get away.");
	}

}
