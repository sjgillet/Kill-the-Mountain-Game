package Game;

import java.util.Random;

public class PlayerCombatant extends CombatEntity{
	public enum playerRace
	{
		HUMAN, DWARF, ELF, MUNCHKIN, BEAR
	}
	private playerRace race; 
	
	public enum combatClass
	{
		FIGHTER, TANK, 
		ROGUE,
		WIZARD, SORCERER,
		PEASANT
	}
	
	public combatClass cls;
	private short level;
	private int xp;
	
	public playerRace getRace()
	{
		return race;
	}
	
	/**
	 * Applies a given amount of damage to the player's hp
	 * If hp reaches 0 after this damage is applied, 
	 * the player dies.
	 * @param damage
	 */
	public void applyDamage(int damage)
	{
		this.currHP -= damage;
		if(currHP <= 0)
			this.kill();
			
	}
	public void kill()
	{
		this.isDead = true;
		//TODO: Reload last save or Exit
	}
	
	
	public short getLevel()
	{
		return this.level;
	}
	public void setLevel(short newLevel)
	{
		this.level = newLevel;
	}
	public int getXP()
	{
		return this.xp;
	}
	public void awardXP(int award)
	{
		this.xp += award;
		if(xp < 100)
			this.levelUp();
		int temp = xp - 100;
		xp = 0;
		if(temp > 0)
			awardXP(temp);
	}
	public void setXP(int newTotal)
	{
		this.xp = newTotal;
	}
	
	public PlayerCombatant()
	{
		acc = 10;
		arm = 0;
		eva = 10;
		hp = 10;
		sp = 10;
		mag = 10;
		intel = 10;
		str = 10;
		lck = 10;
		mre = 10;
		
		currHP = hp;
		currSP = sp;
		updateStats();		
	}
	
	public PlayerCombatant(playerRace startingRace, combatClass startingClass)
	{
		race = startingRace;
		cls = startingClass;
		
		//Initialize player stats based on player race
		switch(race)
		{
		case HUMAN:	//average statistics
			hp  = 10;
			sp = 0;
			str = 10;
			acc = 10;
			eva = 10;
			mag = 10;
			intel = 10;
			mre = 10;
			arm = 0;
			lck = 10;			
			break;
			
		case DWARF:	//high natural resistances and hp, but clumsy
			hp  = 15;
			sp = 0;
			str = 12;
			acc = 7;
			eva = 7;
			mag = 10;
			intel = 10;
			arm = 10;
			mre = 8;
			lck = 8;
			break;
			
		case ELF:	//high magic, low natural resistances
			hp  = 8;
			sp = 10;
			str = 6;
			acc = 12;
			eva = 8;
			mag = 12;
			intel = 14;
			arm = 0;
			mre = 8;
			lck = 8;			
			break;
			
		case MUNCHKIN: //high accuracy/evasion, low hp/resistance
			hp  = 10;
			sp = 0;
			str = 7;
			acc = 14;
			eva = 14;
			mag = 10;
			intel = 12;
			arm = 0;
			mre = 6;
			lck = 15;			
			break;
			
		case BEAR:	//very high damage, but clumsy and little magic
			hp  = 20;
			sp = 0;
			str = 20;
			acc = 6;
			eva = 6;
			mag = 0;
			intel = 7;
			arm = 5;
			mre = 6;
			lck = 8;			
			break;			
		}		
		//Increase starting stats based on chosen starting class
		switch(cls)
		{
		case FIGHTER:	
			hp += 0;
			sp += 0;
			str += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			intel += 0;
			arm += 0;
			mre += 0;
			lck += 0;			
			break;	
				
		case TANK:
			hp += 0;
			sp += 0;
			str += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			intel += 0;
			arm += 0;
			mre += 0;
			lck += 0;			
			break;
			
		case ROGUE:	
			hp += 0;
			sp += 0;
			str += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			intel += 0;
			arm += 0;
			mre += 0;
			lck += 0;				
			break;
			
		case WIZARD:	
			hp += 0;
			sp += 0;
			str += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			intel += 0;
			arm += 0;
			mre += 0;
			lck += 0;			
			break;
		
		case SORCERER:		
			hp += 0;
			sp += 0;
			str += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			intel += 0;
			arm += 0;
			mre += 0;
			lck += 0;			
			break;
		
		case PEASANT:		
			hp += 0;
			sp += 0;
			str += 0;
			acc += 0;
			eva += 0;
			mag += 0;
			intel += 0;
			arm += 0;
			mre += 0;
			lck += 0;	//peasants have high luck					
			break;
		}		
		currHP = hp;
		currSP = sp;
		updateStats();		
	}
	
	public void levelUp()
	{		
		//TODO: messages to display how much your stats increased?
		Random rand = new Random();
		int temp;
		switch(cls)
		{
		/* Template for leveling characters based on class */
		case FIGHTER:
			temp = rand.nextInt(8);	setHP(getHP() + temp);
			System.out.println("HP +" + temp + "!");
			heal(temp);
			
			temp = rand.nextInt(2);	setSP(getSP() + temp);
			System.out.println("SP +" + temp + "!");
			restSP(temp);
			
			temp = rand.nextInt(6);	setStrength(getStrength() + temp);	//Increase STR
			System.out.println("STR +" + temp + "!");
			temp = rand.nextInt(3);	setAcc(getAcc() + temp);			//Increase ACC
			System.out.println("ACC +" + temp + "!");
			temp = rand.nextInt(3);	setEvasion(getEvasion() + temp);	//Increase EVA
			System.out.println("EVA +" + temp + "!");
			temp = 0;				setMagic(getMagic() + temp);		//Increase MAG
			System.out.println("MAG +" + temp + "!...");
			temp = rand.nextInt(1);	setIntel(getIntel() + temp);		//Increase INT
			System.out.println("INT +" + temp + "!");
			temp = rand.nextInt(2);	setMagicRes(getMagicRes() + temp);	//Increase MAG
			System.out.println("MRE +" + temp + "!");
			temp = rand.nextInt(3);	setLuck(getLuck() + temp);			//Increase LCK
			System.out.println("LCK +" + temp + "!");
			
//			setHP(getHP() + 			0);
//			setSP(getSP() + 			0);
//			setStrength(getStrength() + 0);
//			setAcc(getAcc() + 			0);
//			setEvasion(getEvasion() + 	0);
//			setMagic(getMagic() + 		0);
//			setIntel(getIntel() + 		0);
//			setMagicRes(getMagicRes() + 0);
//			setLuck(getLuck() + 		0);
			
			break;
		
		case TANK:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
		
		case ROGUE:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case WIZARD:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case SORCERER:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case PEASANT:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
		}
		
		switch(race)
		{
		case HUMAN:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case DWARF:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case ELF:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case MUNCHKIN:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
			
		case BEAR:
			setHP(getHP() + 			0);
			setSP(getSP() + 			0);
			setStrength(getStrength() + 0);
			setAcc(getAcc() + 			0);
			setEvasion(getEvasion() + 	0);
			setMagic(getMagic() + 		0);
			setIntel(getIntel() + 		0);
			setMagicRes(getMagicRes() + 0);
			setLuck(getLuck() + 		0);
			break;
		}
		updateStats();
		
	}
	
}
