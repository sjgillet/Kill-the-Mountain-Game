package Game;

import java.util.ArrayList;

public class CombatEntity {
	
	/* Character's combat class. Player only.
	 * Affects character progression, 
	 * statistics, and specific game conditions/effects*/
	
		
	/*ABILITY STATS*/
	protected int hp;		//maxiumum hit points; measures the character's health
	protected int sp;		//maximum skill points; measures the character's exhaustion
	protected int str;		//strength; basis for physical damage
	protected int acc;		//accuracy; basis for attack hit chance
	protected int mag;		//magic; basis for magical damage
	protected int intel;	//intelligence; basis for skill/spell hit chance
	protected int eva;		//evasion; basis for avoiding attacks/skills; determines turn order
	protected int lck;		//luck; affects nearly everything in your favor
	protected int arm;		//armor; basis for reducing physical damage
	protected int mre;		//magic resistance; basis for reducing magic damage
	
	protected int currHP;	//current hit points; character dies at 0hp;
	protected int currSP;	//current skill points; must have enough sp to use a skill
	protected boolean isDead;	//Flag for death. If monsters die they award xp
								//if player dies, reload save or exit game
	
	/*COMBAT STATISTICS*/
	//damage variables
	protected int physDamage;
	protected int magDamage;
	
	//damage reduction multipliers.
	protected double physDR;  //physical damage resistance; based on armor
	protected double magDR;   //magical damage resistance; based on MRe
	protected int totalArmor; //total armor; includes all armor items and natural
	protected int totalMRE;	//total magic resist; includes all items and natural
	
	/*Encounter Stats*/
	protected int statTotal;
	
	
	protected ArrayList<Skill> skills = new ArrayList<Skill>();
	
	public int getHP()
	{
		return this.hp;
	}
	public void setHP(int newHP)
	{
		this.hp = newHP;
	}
	public int getSP()
	{
		return this.sp;
	}
	public void setSP(int newSP)
	{
		this.sp = newSP;
	}
	public int getStrength()
	{
		return this.str;
	}
	public void setStrength(int newStrength)
	{
		this.str = newStrength;
	}
	public int getAcc()
	{
		return this.acc;
	}
	public void setAcc(int newAcc)
	{
		this.acc = newAcc;
	}
	public int getMagic()
	{
		return this.mag;
	}
	public void setMagic(int newMagic)
	{
		this.mag = newMagic;
	}
	public int getIntel()
	{
		return this.intel;
	}
	public void setIntel(int newIntel)
	{
		this.intel = newIntel;
	}
	public int getEvasion()
	{
		return this.eva;
	}
	public void setEvasion(int newEvasion)
	{
		this.eva = newEvasion;
	}
	public int getLuck()
	{
		return this.lck;
	}
	public void setLuck(int newLuck)
	{
		this.lck = newLuck;
	}
	public int getArmor()
	{
		return this.arm;
	}
	public void setArmor(int newArmor)
	{
		this.arm = newArmor;
	}
	public int getMagicRes()
	{
		return this.mre;
	}
	public void setMagicRes(int newMagicResist)
	{
		this.mre = newMagicResist;
	}
	public double getPDR()
	{
		return this.physDR;
	}
	public double getMDR()
	{
		return this.magDR;
	}
	public int getPDmg()
	{
		return this.physDamage;
	}
	public int getMDmg()
	{
		return this.magDamage;
	}
	
	public int getStatTotal()
	{
		return (this.acc + this.arm + this.eva + this.intel 
				+ this.lck + this.mag + this.mre + this.str);
	}
	
	public int getCurrHP()
	{
		return this.currHP;
	}
	public void setCurrHP(int newHP)
	{
		this.currHP = newHP;
	}
	
		
	//Heals an entity by the given amount, up to max HP
	public void heal(int healAmt)
	{
		this.currHP += healAmt;
		if(this.currHP > this.hp)
			currHP = hp;
	}
	public void applyDamage(int damage)
	{
		System.out.println("Do Nothing");
//		this.currHP -= damage;
//		if(this.currHP <=0)
//			this.isDead = true;
//		
	}
	
	
	public int getCurrSP()
	{
		return this.currSP;
	}
	public void setCurrSP(int newSP)
	{
		this.currSP = newSP;
	}
	public void spendSP(int cost)
	{
		this.currSP -= cost;
		if(currSP < 0)
			currSP = 0;
	}
	public void restSP(int restAmt)
	{
		currSP += restAmt;
		if(currSP > sp)
			currSP = sp;
	}
	
		
	/**
	 * Update combat statistics based on ability stats
	 */
	public void updateStats()
	{
		physDamage = str + (1/4)*acc;	System.out.println("Damage: " + physDamage);
		magDamage = mag + (1/4)*intel;
		physDR = 100/(100 + arm);		System.out.println("DR: " + physDR);
		magDR = 100/(100 + mre);
	}	
	
	public CombatEntity(){
		
	}
	
	
	
}
