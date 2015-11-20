package Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends CombatEntity{
	
	/*A means of balancing an enemy's stats according to the player's 
	* total, assigning each stat a weight to keep its stats similar
	* through progression
	* Key: ACC, ARM, EVA, HP, INT, LCK, MAG, MRE, STR
	*/
	private double[] statWeights;
	private int statTotal;
		
	private String description;
	private double challenge;
	private int xp;
	public ArrayList<Item> lootList;
	public double[] lootRates;
	public BufferedImage battleArt = null;
	
	/**
	 * Applies a given amount of damage to the monster's hp
	 * If hp reaches 0 after this damage is applied, 
	 * the monster dies and xp is awarded to the player
	 * @param damage
	 */
	public void applyDamage(int damage)
	{
		this.currHP -= damage;
		if(currHP <= 0)

		{
			this.currHP = 0;
			this.kill();
		}
			
	}
	public void kill()
	{
		this.isDead = true;
		System.out.println(name + " died!");
		GamePanel.player.playerCombatant.awardXP(this.getXP());

		//TODO: Reload last save or Exit
	}
	public void run()
	{
		this.isDead = true;
		this.currHP = hp;
	}
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String newName)
	{
		name = newName;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String newDescript)
	{
		description = newDescript;
	}
	public void setArt(BufferedImage art)
	{
		this.battleArt = art;
	}
	
	public double getChallenge()
	{
		return this.challenge;
	}
	public void setXP(int newXP)
	{
		xp = newXP;
	}
	public int getXP()
	{
		return xp;
	}
	
	/**
	 * Sets the various weights of an enemy's stats. These weights reflect how high 
	 * a given enemy's stats should be regardless of player progression.
	 * An enemy whose stat weights - excluding HP and SP - total 1.0 reflects
	 * an enemy with stats equal to that of the player character
	 * @param ACC	Accuracy
	 * @param ARM	Armor
	 * @param EVA	Evasion
	 * @param HP	Hit Points; Not counted toward challenge
	 * @param INT	Intelligence
	 * @param LCK	Luck
	 * @param MAG	Magic
	 * @param MRE	Magic Resistance
	 * @param STR	Strength
	 * @param SP	Skill Points; Not counted toward challenge
	 */
	public void setWeights(double ACC, double ARM, double EVA, double HP, double INT,
			double LCK, double MAG, double MRE, double STR, double SP)
	{
		this.statWeights = new double[10];
		statWeights[0] = ACC;
		statWeights[1] = ARM;
		statWeights[2] = EVA;
		statWeights[3] = HP;
		statWeights[4] = INT;
		statWeights[5] = LCK;
		statWeights[6] = MAG;
		statWeights[7] = MRE;
		statWeights[8] = STR;
		statWeights[9] = SP;
	}
	
	public Enemy(String newName, String descript /*Texture*/, double challengeRating, int experiencePoints){
		this.setName(newName);
		this.setDescription(descript);
		this.challenge = challengeRating;
		this.xp = experiencePoints;	
		this.lootList = new ArrayList<Item>();
	}
	
	public void Initialize()
	{
		statTotal = GamePanel.bat.getPlayer().getStatTotal();


		this.setAcc((int)(statTotal * statWeights[0]));			System.out.println(this.getName() + " ACC: " + this.getAcc());		//ACC	
		this.setArmor((int)(statTotal * statWeights[1]));		System.out.println(this.getName() + " ARM: " + this.getArmor());	//ARM
		this.setEvasion((int)(statTotal * statWeights[2]));		System.out.println(this.getName() + " EVA: " + this.getEvasion());	//EVA
		this.setHP((int)(statTotal * statWeights[3]));			System.out.println(this.getName() + " HP: " + this.getHP());		//HP
		this.setIntel((int)(statTotal * statWeights[4]));		System.out.println(this.getName() + " INT: " + this.getIntel());	//INT
		this.setLuck((int)(statTotal * statWeights[5]));		System.out.println(this.getName() + " LCK: " + this.getLuck());		//LCK
		this.setMagic((int)(statTotal * statWeights[6]));		System.out.println(this.getName() + " MAG: " + this.getMagic());	//MAG
		this.setMagicRes((int)(statTotal * statWeights[7]));	System.out.println(this.getName() + " MRE: " + this.getMagicRes());	//MRE
		this.setStrength((int)(statTotal * statWeights[8]));	System.out.println(this.getName() + " STR: " + this.getStrength());	//STR
		this.setSP((int)(statTotal * statWeights[9]));			System.out.println(this.getName() + " SP: " + this.getSP());		//SP
		
		this.setCurrHP(this.getHP());	System.out.println(this.getName() + "CURHP: " + this.getCurrHP());//set hp to max on creation
		this.setCurrSP(this.getSP());	System.out.println(this.getName() + "CURRSP: " + this.getCurrSP());//set sp to max on creation
		this.updateStats();				//update damage and damage resistance and speed
	}
	
	
	
	
	
	
}
