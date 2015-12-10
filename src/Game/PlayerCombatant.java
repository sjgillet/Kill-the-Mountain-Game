package Game;

import java.awt.image.BufferedImage;
import java.util.Random;

public class PlayerCombatant extends CombatEntity{
	Random rand = new Random();
	BufferedImage battleArt;
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
	private short level = 0;
	private int xp; 
	private String name = "Dovahabear";

	public playerRace getRace()
	{
		return race;
	}
	public String getRaceString(boolean upper)
	{
		String str = this.getRace().toString();
		System.out.println(str);
		if(str.equals("HUMAN"))
			if(upper)
				return "Human";
			else return "human";
		else if(str.equals("DWARF"))
			if(upper)
				return "Dwarf";
			else return "dwarf";
		else if(str.equals("ELF"))
			if(upper)
				return "Elf";
			else return "elf";
		else if(str.equals("MUNCHKIN"))
			if(upper)
				return "Munchkin";
			else return "munchkin";
		else if(str.equals("BEAR"))
			if(upper)
				return "Bear";
			else return "bear";
		else return "";
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

		{
			currHP = 0;
			this.kill();
		}


	}
	public void kill()
	{
		this.isDead = true;
		//TODO: Reload last save or Exit
	}

	public int getStatTotal()
	{
		return (str + acc + mag + intel + eva + lck + arm + mre);
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
		if(xp >= 100)
		{
			this.levelUp();
			int temp = xp - 100;
			xp = 0;
			if(temp > 0)
				awardXP(temp);
		}
		System.out.println("XP: " + xp);
	}
	public void setXP(int newTotal)
	{
		this.xp = newTotal;
	}
	public String getName()
	{
		return this.name;
	}
	public void setName(String newName)
	{
		this.name = newName;
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
		//updateStats();		
	}

	public PlayerCombatant(playerRace startingRace, combatClass startingClass)
	{
		race = startingRace;
		cls = startingClass;

		//Initialize player stats based on player race
		switch(race)
		{
		case HUMAN:	//average statistics
			battleArt = GamePanel.human;
			hp  = 10;
			sp  = 10;
			str = 10;
			acc = 10;
			eva = 10;
			mag = 10;
			intel = 10;
			mre = 10;
			arm = 10;
			lck = 10;			
			break;

		case DWARF:	//high natural resistances and hp, but clumsy
			battleArt = GamePanel.dwarf;
			hp  = 15;
			sp  = 10;
			str = 12;
			acc = 7;
			eva = 7;
			mag = 10;
			intel = 10;
			arm = 12;
			mre = 8;
			lck = 8;
			break;

		case ELF:	//high magic, low natural resistances
			battleArt = GamePanel.elf;
			hp  = 8;
			sp  = 12;
			str = 6;
			acc = 12;
			eva = 8;
			mag = 12;
			intel = 14;
			arm = 6;
			mre = 8;
			lck = 8;			
			break;

		case MUNCHKIN: //high accuracy/evasion, low hp/resistance
			battleArt = GamePanel.munchkin;
			hp  = 10;
			sp  = 10;
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
			battleArt = GamePanel.bear;
			hp  = 20;
			sp = 5;
			str = 20;
			acc = 6;
			eva = 6;
			mag = 0;
			intel = 7;
			arm = 13;
			mre = 6;
			lck = 7;			
			break;			
		}
		levelUp();

		currHP = hp;
		currSP = sp;
		//updateStats();		
	}

	public void levelUp()
	{		
		this.level++;
		int temp;
		switch(cls)
		{
		/* Template for leveling characters based on class */
		case FIGHTER:
			temp = rand.nextInt(10);	setHP(getHP() + temp);			//Increase HP and heal
			GamePanel.dialog.addMessage("HP + " + temp + "!");
			heal(temp);

			temp = rand.nextInt(3);	setSP(getSP() + temp);				//Increase SP and rest
			GamePanel.dialog.addMessage("SP + " + temp + "!");
			restSP(temp);

			temp = rand.nextInt(6);	setStrength(getStrength() + temp);	//Increase STR
			GamePanel.dialog.addMessage("STR + " + temp + "!");

			temp = rand.nextInt(3);	setAcc(getAcc() + temp);			//Increase ACC
			GamePanel.dialog.addMessage("ACC + " + temp + "!");

			temp = rand.nextInt(3);	setEvasion(getEvasion() + temp);	//Increase EVA
			GamePanel.dialog.addMessage("EVA + " + temp + "!");

			temp = rand.nextInt(1);	setMagic(getMagic() + temp);		//Increase MAG
			GamePanel.dialog.addMessage("MAG + " + temp + "!");

			temp = rand.nextInt(1);	setIntel(getIntel() + temp);		//Increase INT
			GamePanel.dialog.addMessage("INT + " + temp + "!");

			temp = rand.nextInt(3); setArmor(getArmor() + temp);		//Increase ARM
			GamePanel.dialog.addMessage("ARM + " + temp + "!");

			temp = rand.nextInt(2);	setMagicRes(getMagicRes() + temp);	//Increase MRE
			GamePanel.dialog.addMessage("MRE + " + temp + "!");

			temp = rand.nextInt(3);	setLuck(getLuck() + temp);			//Increase LCK
			GamePanel.dialog.addMessage("LCK + " + temp + "!");

			break;

		case TANK:
			temp = rand.nextInt(12);	setHP(getHP() + temp);			//Increase HP and heal
			GamePanel.dialog.addMessage("HP + " + temp + "!");
			heal(temp);

			temp = rand.nextInt(2);	setSP(getSP() + temp);				//Increase SP and rest
			GamePanel.dialog.addMessage("SP + " + temp + "!");
			restSP(temp);

			temp = rand.nextInt(6);	setStrength(getStrength() + temp);	//Increase STR
			GamePanel.dialog.addMessage("STR + " + temp + "!");

			temp = rand.nextInt(2);	setAcc(getAcc() + temp);			//Increase ACC
			GamePanel.dialog.addMessage("ACC + " + temp + "!");

			temp = rand.nextInt(2);	setEvasion(getEvasion() + temp);	//Increase EVA
			GamePanel.dialog.addMessage("EVA + " + temp + "!");

			temp = rand.nextInt(1);	setMagic(getMagic() + temp);		//Increase MAG
			GamePanel.dialog.addMessage("MAG + " + temp + "!");

			temp = rand.nextInt(1);	setIntel(getIntel() + temp);		//Increase INT
			GamePanel.dialog.addMessage("INT + " + temp + "!");

			temp = rand.nextInt(6); setArmor(getArmor() + temp);		//Increase ARM
			GamePanel.dialog.addMessage("ARM + " + temp + "!");

			temp = rand.nextInt(4);	setMagicRes(getMagicRes() + temp);	//Increase MRE
			GamePanel.dialog.addMessage("MRE + " + temp + "!");

			temp = rand.nextInt(2);	setLuck(getLuck() + temp);			//Increase LCK
			GamePanel.dialog.addMessage("LCK + " + temp + "!");

			break;

		case ROGUE:
			temp = rand.nextInt(8);	setHP(getHP() + temp);				//Increase HP and heal
			GamePanel.dialog.addMessage("HP + " + temp + "!");
			heal(temp);

			temp = rand.nextInt(6);	setSP(getSP() + temp);				//Increase SP and rest
			GamePanel.dialog.addMessage("SP + " + temp + "!");
			restSP(temp);

			temp = rand.nextInt(3);	setStrength(getStrength() + temp);	//Increase STR
			GamePanel.dialog.addMessage("STR + " + temp + "!");

			temp = rand.nextInt(6);	setAcc(getAcc() + temp);			//Increase ACC
			GamePanel.dialog.addMessage("ACC + " + temp + "!");

			temp = rand.nextInt(6);	setEvasion(getEvasion() + temp);	//Increase EVA
			GamePanel.dialog.addMessage("EVA + " + temp + "!");

			temp = rand.nextInt(1);	setMagic(getMagic() + temp);		//Increase MAG
			GamePanel.dialog.addMessage("MAG + " + temp + "!");

			temp = rand.nextInt(2);	setIntel(getIntel() + temp);		//Increase INT
			GamePanel.dialog.addMessage("INT + " + temp + "!");

			temp = rand.nextInt(3); setArmor(getArmor() + temp);		//Increase ARM
			GamePanel.dialog.addMessage("ARM + " + temp + "!");

			temp = rand.nextInt(3);	setMagicRes(getMagicRes() + temp);	//Increase MRE
			GamePanel.dialog.addMessage("MRE + " + temp + "!");

			temp = rand.nextInt(3);	setLuck(getLuck() + temp);			//Increase LCK
			GamePanel.dialog.addMessage("LCK + " + temp + "!");
			break;

		case WIZARD:
			temp = rand.nextInt(6);	setHP(getHP() + temp);				//Increase HP and heal
			GamePanel.dialog.addMessage("HP + " + temp + "!");
			heal(temp);

			temp = rand.nextInt(8);	setSP(getSP() + temp);				//Increase SP and rest
			GamePanel.dialog.addMessage("SP + " + temp + "!");
			restSP(temp);

			temp = rand.nextInt(1);	setStrength(getStrength() + temp);	//Increase STR
			GamePanel.dialog.addMessage("STR + " + temp + "!");

			temp = rand.nextInt(2);	setAcc(getAcc() + temp);			//Increase ACC
			GamePanel.dialog.addMessage("ACC + " + temp + "!");

			temp = rand.nextInt(2);	setEvasion(getEvasion() + temp);	//Increase EVA
			GamePanel.dialog.addMessage("EVA + " + temp + "!");

			temp = rand.nextInt(4);	setMagic(getMagic() + temp);		//Increase MAG
			GamePanel.dialog.addMessage("MAG + " + temp + "!");

			temp = rand.nextInt(6);	setIntel(getIntel() + temp);		//Increase INT
			GamePanel.dialog.addMessage("INT + " + temp + "!");

			temp = rand.nextInt(1); setArmor(getArmor() + temp);		//Increase ARM
			GamePanel.dialog.addMessage("ARM + " + temp + "!");

			temp = rand.nextInt(4);	setMagicRes(getMagicRes() + temp);	//Increase MRE
			GamePanel.dialog.addMessage("MRE + " + temp + "!");

			temp = rand.nextInt(3);	setLuck(getLuck() + temp);			//Increase LCK
			GamePanel.dialog.addMessage("LCK + " + temp + "!");
			break;

		case SORCERER:
			temp = rand.nextInt(8);	setHP(getHP() + temp);				//Increase HP and heal
			GamePanel.dialog.addMessage("HP + " + temp + "!");
			heal(temp);

			temp = rand.nextInt(6);	setSP(getSP() + temp);				//Increase SP and rest
			GamePanel.dialog.addMessage("SP + " + temp + "!");
			restSP(temp);

			temp = rand.nextInt(1);	setStrength(getStrength() + temp);	//Increase STR
			GamePanel.dialog.addMessage("STR + " + temp + "!");

			temp = rand.nextInt(2);	setAcc(getAcc() + temp);			//Increase ACC
			GamePanel.dialog.addMessage("ACC + " + temp + "!");

			temp = rand.nextInt(2);	setEvasion(getEvasion() + temp);	//Increase EVA
			GamePanel.dialog.addMessage("EVA + " + temp + "!");

			temp = rand.nextInt(6);	setMagic(getMagic() + temp);		//Increase MAG
			GamePanel.dialog.addMessage("MAG + " + temp + "!");

			temp = rand.nextInt(4);	setIntel(getIntel() + temp);		//Increase INT
			GamePanel.dialog.addMessage("INT + " + temp + "!");

			temp = rand.nextInt(2); setArmor(getArmor() + temp);		//Increase ARM
			GamePanel.dialog.addMessage("ARM + " + temp + "!");

			temp = rand.nextInt(4);	setMagicRes(getMagicRes() + temp);	//Increase MRE
			GamePanel.dialog.addMessage("MRE + " + temp + "!");

			temp = rand.nextInt(2);	setLuck(getLuck() + temp);			//Increase LCK
			GamePanel.dialog.addMessage("LCK + " + temp + "!");
			break;

		case PEASANT:
			temp = rand.nextInt(6);	setHP(getHP() + temp);			//Increase HP and heal
			GamePanel.dialog.addMessage("HP + " + temp + "!");
			heal(temp);

			temp = rand.nextInt(4);	setSP(getSP() + temp);				//Increase SP and rest
			GamePanel.dialog.addMessage("SP + " + temp + "!");
			restSP(temp);

			temp = rand.nextInt(4);	setStrength(getStrength() + temp);	//Increase STR
			GamePanel.dialog.addMessage("STR + " + temp + "!");

			temp = rand.nextInt(4);	setAcc(getAcc() + temp);			//Increase ACC
			GamePanel.dialog.addMessage("ACC + " + temp + "!");

			temp = rand.nextInt(4);	setEvasion(getEvasion() + temp);	//Increase EVA
			GamePanel.dialog.addMessage("EVA + " + temp + "!");

			temp = rand.nextInt(2);	setMagic(getMagic() + temp);		//Increase MAG
			GamePanel.dialog.addMessage("MAG + " + temp + "!");

			temp = rand.nextInt(2);	setIntel(getIntel() + temp);		//Increase INT
			GamePanel.dialog.addMessage("INT + " + temp + "!");

			temp = rand.nextInt(3); setArmor(getArmor() + temp);		//Increase ARM
			GamePanel.dialog.addMessage("ARM + " + temp + "!");

			temp = rand.nextInt(3);	setMagicRes(getMagicRes() + temp);	//Increase MAG
			GamePanel.dialog.addMessage("MRE + " + temp + "!");

			temp = rand.nextInt(6);	setLuck(getLuck() + temp);			//Increase LCK
			GamePanel.dialog.addMessage("LCK + " + temp + "!");

		}

		switch(race)
		{

		case HUMAN:
			if(getLevel() % 4 == 0)
			{
				setHP(getHP() + 			1);
				setSP(getSP() + 			1);
				setStrength(getStrength() + 1);
				setAcc(getAcc() + 			1);
				setEvasion(getEvasion() + 	1);
				setMagic(getMagic() + 		1);
				setIntel(getIntel() + 		1);
				setArmor(getArmor() + 		1);
				setMagicRes(getMagicRes() + 1);
				setLuck(getLuck() + 		1);
				GamePanel.dialog.addMessage("Your human "
						+ "ancestry makes you more versitile! "
						+ "\nAll stats increased by 1!");
			}
			break;

		case DWARF:
			if(getLevel() % 5 == 0)
			{
				setHP(getHP() + 			3);
				setStrength(getStrength() + 2);
				setIntel(getIntel() + 		2);
				setArmor(getArmor() + 		2);
				GamePanel.dialog.addMessage("Your dwarven "
						+ "ancestry makes you more powerful and durable! "
						+ "\nHP, STR, INT, and ARM increased!");
			}
			break;

		case ELF:
			if(getLevel() % 5 == 0)
			{
				setSP(getSP() + 			2);
				setAcc(getAcc() + 			3);
				setEvasion(getEvasion() + 	2);
				setIntel(getIntel() + 		2);
				GamePanel.dialog.addMessage("Your elven "
						+ "ancestry makes you more agile and cunning! "
						+ "\nSP, ACC, EVA, and INT increased!");
			}
			break;

		case MUNCHKIN:
			if(getLevel() % 5 == 0)
			{
				setEvasion(getEvasion() + 	2);
				setIntel(getIntel() + 		2);
				setMagicRes(getMagicRes() + 2);
				setLuck(getLuck() + 		3);
				GamePanel.dialog.addMessage("Your munchkin "
						+ "ancestry makes you smaller and harder to hit! "
						+ "\nEVA, INT, MRE, and LCK increased!");
			}
			break;

		case BEAR:
			if(getLevel() % 5 == 0)
			{
				setHP(getHP() + 			3);
				setStrength(getStrength() + 2);
				setArmor(getArmor() +		2);
				setMagicRes(getMagicRes() + 2);
				GamePanel.dialog.addMessage("Your bear... "
						+ "ancestry makes you bigger and stronger! "
						+ "\nHP, STR, ARM, and MRE increased!");
			}
			break;
		}
		System.out.println("HP: " + getHP() + "\tSP: " + getSP() + "\tSTR: " + getStrength() + "\tACC: "
				+ getAcc() + "\tEVA: " + getEvasion() + "\tARM: " + getArmor() + "\tMAG: " + getMagic() + "\tINT: " + getIntel()
				+ "\tMRE: " + getMagicRes() + "\tLCK: " + getLuck());
		updateStats();		
	}


	public void updateStats()
	{
		Inventory inv = GamePanel.player.inventory;
		double wpnDmg = 1.0;
		double wpnSpd = 1.0;

		if (inv.weapon!=null)
			wpnDmg = inv.weapon.item.getDamage();
		physDamage = (int)(Math.floor(str + (1/4)*acc)*wpnDmg);	System.out.println(this.getName() + "'s Damage: " + physDamage);

		magDamage = mag + (1/4)*intel;

		int totalArmor = getArmor();
		int totalMR = getMagicRes();
		if(inv.head.item != null)
		{
			totalArmor += inv.head.item.getArmor();
			totalMR += inv.head.item.getMagicResist();
		}
		if(inv.torso.item != null)
		{
			totalArmor += inv.torso.item.getArmor();
			totalMR += inv.torso.item.getMagicResist();
		}
		if(inv.legs.item != null)
		{	
			totalArmor += inv.legs.item.getArmor();
			totalMR += inv.legs.item.getMagicResist();
		}
		if(inv.arms.item != null)
		{
			totalArmor += inv.arms.item.getArmor();
			totalMR += inv.arms.item.getArmor();
		}		
		physDR = 100/(100 + totalArmor);						System.out.println(this.getName() + "'s DR: " + physDR);
		magDR = 100/(100 + totalMR);

		if(inv.weapon!=null)
			wpnSpd = inv.weapon.item.getSpeed();
		speed = (int)(this.getEvasion()*wpnSpd);				System.out.println(this.getName() + "'s Speed: " + speed);
	}	
}
