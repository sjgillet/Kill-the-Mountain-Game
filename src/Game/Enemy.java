package Game;

import java.util.ArrayList;

public class Enemy extends CombatEntity{
	
	/*A means of balancing an enemy's stats according to the player's 
	* total, assigning each stat a weight to keep its stats similar
	* through progression
	* Key: ACC, ARM, EVA, HP, INT, LCK, MAG, MRE, STR
	*/
	private double[] statWeights;
	private int statTotal;
	private String name;
	private String description;
	private double challenge;
	
	public void setName(String newName)
	{
		name = newName;
	}
	public void setDescription(String newDescript)
	{
		description = newDescript;
	}
	public double getChallenge()
	{
		return this.challenge;
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
	
	public Enemy(String newName, String descript /*Texture*/, double challenge ){
		this.setName(newName);
		this.setDescription(descript);
		
	}
	
	public void Initialize()
	{
		this.setAcc((int)(statTotal * statWeights[0]));				//ACC
		this.setArmor((int)(statTotal * statWeights[1]));			//ARM
		this.setEvasion((int)(statTotal * statWeights[2]));			//EVA
		this.setHP((int)(statTotal * statWeights[3]));				//HP
		this.setIntelligence((int)(statTotal * statWeights[4]));	//INT
		this.setLuck((int)(statTotal * statWeights[5]));			//LCK
		this.setMagic((int)(statTotal * statWeights[6]));			//MAG
		this.setMagicResist((int)(statTotal * statWeights[7]));		//MRE
		this.setStrength((int)(statTotal * statWeights[8]));		//STR
		this.setSP((int)(statTotal * statWeights[9]));				//SP
	}
	
	
	
}
