package Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Creature {
	int xpos;
	int ypos;
	int creatureID;
	int attack;
	int defense;
	int accuracy;
	int health;
	double currentHealth;
	int crit;
	int avoid;
	int laziness;
	int speed;
	int level;
	int willPower;
	int energy = 100;
	int currentEnergy;
	int currentXP = 0;
	int experienceToLevel;
	int type;
	int color = 0;
	
	double attackGainPerLevel;
	double defenseGainPerLevel;
	double accuracyGainPerLevel;
	double healthGainPerLevel;
	double critGainPerLevel;
	double avoidGainPerLevel;
	double speedGainPerLevel;
	double lazinessGainPerLevel;
	
	final int fire = 0;
	final int water = 1;
	final int anima = 2;
	final int shadow = 3;
	final int earth = 4;
	final int light = 5;
	final int time = 6;
	ArrayList<Attack> attacks = new ArrayList<Attack>();
	Button formSelect;
	PassiveGrid talents = new PassiveGrid();
	BufferedImage [][]Sprites;
	String name = "";
	public Creature(int lvl, int ID, int clr){
		color = clr;
		creatureID = ID;
		level = lvl;
		if(creatureID==1){
			attackGainPerLevel=1.5;
			defenseGainPerLevel=1;
			accuracyGainPerLevel=1.5;
			healthGainPerLevel=3;
			critGainPerLevel=1.5;
			avoidGainPerLevel=1.5;
			speedGainPerLevel=1.5;
			lazinessGainPerLevel=1.5;
			
			this.name="Forest Sprite";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+20;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=100;
		}
		else if(creatureID==2){
			attackGainPerLevel=1.2;
			defenseGainPerLevel=1;
			accuracyGainPerLevel=1.4;
			healthGainPerLevel=1.5;
			critGainPerLevel=1.3;
			avoidGainPerLevel=1.2;
			speedGainPerLevel=1.1;
			lazinessGainPerLevel=2.5;
			
			this.name="Forest Grub";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+15;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=1;
			this.Sprites=GamePanel.forestGrub;
			attacks.add(new Attack("Bite"));
		}
		else if(creatureID==3){
			attackGainPerLevel=1.5;
			defenseGainPerLevel=2;
			accuracyGainPerLevel=1.5;
			healthGainPerLevel=2.5;
			critGainPerLevel=1.2;
			avoidGainPerLevel=1.2;
			speedGainPerLevel=1.3;
			lazinessGainPerLevel=1.0;
			
			this.name="Giant Firefly";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+30;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=3;
			this.Sprites=GamePanel.fireFly;
			attacks.add(new Attack("Bite"));
		}
		else if(creatureID==4){
			attackGainPerLevel=2;
			defenseGainPerLevel=1.2;
			accuracyGainPerLevel=1.2;
			healthGainPerLevel=1.2;
			critGainPerLevel=1.5;
			avoidGainPerLevel=1.5;
			speedGainPerLevel=1.5;
			lazinessGainPerLevel=.4;
			
			this.name= "Armonkey";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+22;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=6;
			this.Sprites=GamePanel.monkey;
			attacks.add(new Attack("Punch"));
		}
		else if(creatureID==5){
			attackGainPerLevel=1.7;
			defenseGainPerLevel=1.3;
			accuracyGainPerLevel=1;
			healthGainPerLevel=2;
			critGainPerLevel=1.2;
			avoidGainPerLevel=.5;
			speedGainPerLevel=1.5;
			lazinessGainPerLevel=1.5;
			
			this.name= "GnuDragon";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+24;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=20;
			this.Sprites=GamePanel.dragon;
			attacks.add(new Attack("Bite"));
		}
		else if(creatureID==6){
			attackGainPerLevel=1.8;
			defenseGainPerLevel=1.5;
			accuracyGainPerLevel=2;
			healthGainPerLevel=3;
			critGainPerLevel=1.2;
			avoidGainPerLevel=1.5;
			speedGainPerLevel=1.5;
			lazinessGainPerLevel=2.0;
			
			this.name= "Piggy";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+4;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=20;
			this.Sprites=GamePanel.pig;
			attacks.add(new Attack("Charge"));
		}
		else if(creatureID==7){
			attackGainPerLevel=1.8;
			defenseGainPerLevel=1.3;
			accuracyGainPerLevel=3;
			healthGainPerLevel=1.5;
			critGainPerLevel=2.5;
			avoidGainPerLevel=1.5;
			speedGainPerLevel=1.5;
			lazinessGainPerLevel=.5;
			
			this.name= "Birderp";
			this.attack=(int)(level*attackGainPerLevel);
			this.defense=(int)(level*defenseGainPerLevel);
			this.accuracy=(int)(level*accuracyGainPerLevel);
			this.health=(int)(level*healthGainPerLevel)+14;
			this.currentHealth=health;
			this.crit=(int)(level*critGainPerLevel);
			this.avoid=(int)(level*avoidGainPerLevel);
			this.speed=(int)(level*speedGainPerLevel);
			this.laziness=(int)(level*lazinessGainPerLevel);
			this.willPower=20;
			this.Sprites=GamePanel.bird;
			attacks.add(new Attack("Peck"));
		}
		experienceToLevel = (attack+defense+accuracy+health+crit+avoid+speed+laziness)/8;
	}
	public void restoreHP(int amt){
		currentHealth+=amt;
		if(currentHealth>health){
			currentHealth = health;
		}
	}
	public void takeDmg(Creature attacker, Attack atk, int amt, int dmgType){
		int dmgTaken = ((int)(amt*effectiveness(dmgType,type)))-defense;
		int rng = (int)((double)dmgTaken/15.0);
		if(rng==0){
			rng = 1;
		}
		dmgTaken = dmgTaken+GamePanel.randomNumber(-rng, rng);
		if(dmgTaken<0){
			dmgTaken = GamePanel.randomNumber(0, 1);
		}
		currentHealth-=dmgTaken;
		if(currentHealth<=0){
			attacker.gainXP(this);
			for(int i = 0; i<AppletUI.client.players.size();i++){
				AppletUI.client.players.get(i).battling=false;
				AppletUI.client.players.get(i).currentTarget=null;
			}
		}
		if(dmgTaken>0){
		GamePanel.messages.add(atk.name+" dealt "+dmgTaken+" damage to "+name+"!");
		}
		else{
			GamePanel.messages.add(attacker.name+"'s "+atk.name+" did no damage.");
		}
	}
	public void gainXP(Creature opponent){
		//gain experience equal to the opponents average stats
		int totalStats = opponent.attack+opponent.defense+opponent.accuracy+opponent.avoid+opponent.laziness+opponent.speed+opponent.health+opponent.crit;
		int xpGain = totalStats/8;
		this.currentXP+=xpGain;
		if(this.currentXP>=this.experienceToLevel){
			levelUp();
			experienceToLevel = ((attack+defense+accuracy+avoid+laziness+speed+health+crit)/8)*10;
		}
	}
	public void levelUp(){
		
		
		int oldAttack = attack;
		attack=(int)(level*(1+attackGainPerLevel));
		GamePanel.messages.add("Attack: "+oldAttack+" -> "+attack);
		
		int oldDefense = defense;
		defense = (int)(level*(1+defenseGainPerLevel));
		GamePanel.messages.add("Defense: "+oldDefense+" -> "+defense);
		
		int oldAccuracy = accuracy;
		accuracy = (int)(level*(1+accuracyGainPerLevel));
		GamePanel.messages.add("Accuracy: "+oldAccuracy+" -> "+accuracy);
		
		int oldHealth = health;
		health=(int)(level*(1+healthGainPerLevel));
		GamePanel.messages.add("Health: "+oldHealth+" -> "+health);
		
		int oldCrit = crit;
		crit = (int)(level*(1+critGainPerLevel));
		GamePanel.messages.add("Crit: "+oldCrit+" -> "+crit);
		
		int oldAvoid = avoid;
		avoid = (int)(level*(1+avoidGainPerLevel));
		GamePanel.messages.add("Avoid: "+oldAvoid+" -> "+avoid);
		
		int oldSpeed = speed;
		speed = (int)(level*(1+speedGainPerLevel));
		GamePanel.messages.add("Speed: "+oldSpeed+" -> "+speed);
		
		int oldLaziness = laziness;
		laziness = (int)(laziness*(1+lazinessGainPerLevel));
		
		GamePanel.messages.add("Level up!");
	}
	public double effectiveness(int atkType, int targetType){
		//fire hit fire
		if(atkType == 0&&targetType == 0)
		return 1.0;
		//fire hit water
		if(atkType == 0&&targetType == 1)
		return .5;
		//fire hit anima
		if(atkType == 0&&targetType == 2)
		return 1.5;
		//fire hit shadow
		if(atkType == 0&&targetType == 3)
		return 1.0;
		//fire hit earth
		if(atkType == 0&&targetType == 4)
		return 1.0;
		//fire hit light
		if(atkType == 0&&targetType == 5)
		return 1.0;
		//fire hit time
		if(atkType == 0&&targetType == 6)
		return 1.0;
		//water hit fire
		if(atkType == 1&&targetType == 0)
		return 1.0;
		//water hit water
		if(atkType == 1&&targetType == 1)
		return 1.0;
		//water hit anima
		if(atkType == 1&&targetType == 2)
		return 1.0;
		//water hit shadow
		if(atkType == 1&&targetType == 3)
		return 1.0;
		//water hit earth
		if(atkType == 1&&targetType == 4)
		return 1.0;
		//water hit light
		if(atkType == 1&&targetType == 5)
		return 1.0;
		//water hit time
		if(atkType == 1&&targetType == 6)
		return 1.0;
		//anima hit fire
		if(atkType == 2&&targetType == 0)
		return 1.0;
		//anima hit water
		if(atkType == 2&&targetType == 1)
		return 1.0;
		//anima hit anima
		if(atkType == 2&&targetType == 2)
		return 1.0;
		//anima hit shadow
		if(atkType == 2&&targetType == 3)
		return 1.0;
		//anima hit earth
		if(atkType == 2&&targetType == 4)
		return 1.0;
		//anima hit light
		if(atkType == 2&&targetType == 5)
		return 1.0;
		//anima hit time
		if(atkType == 2&&targetType == 6)
		return 1.0;
		//shadow hit fire
		if(atkType == 3&&targetType == 0)
		return 1.0;
		//shadow hit water
		if(atkType == 3&&targetType == 1)
		return 1.0;
		//shadow hit anima
		if(atkType == 3&&targetType == 2)
		return 1.0;
		//shadow hit shadow
		if(atkType == 3&&targetType == 3)
		return 1.0;
		//shadow hit earth
		if(atkType == 3&&targetType == 4)
		return 1.0;
		//shadow hit light
		if(atkType == 3&&targetType == 5)
		return 1.0;
		//shadow hit time
		if(atkType == 3&&targetType == 6)
		return 1.0;
		//earth hit fire
		if(atkType == 4&&targetType == 0)
		return 1.0;
		//earth hit water
		if(atkType == 4&&targetType == 1)
		return 1.0;
		//earth hit anima
		if(atkType == 4&&targetType == 2)
		return 1.0;
		//earth hit shadow
		if(atkType == 4&&targetType == 3)
		return 1.0;
		//earth hit earth
		if(atkType == 4&&targetType == 4)
		return 1.0;
		//earth hit light
		if(atkType == 4&&targetType == 5)
		return 1.0;
		//earth hit time
		if(atkType == 4&&targetType == 6)
		return 1.0;
		//light hit fire
		if(atkType == 5&&targetType == 0)
		return 1.0;
		//light hit water
		if(atkType == 5&&targetType == 1)
		return 1.0;
		//light hit anima
		if(atkType == 5&&targetType == 2)
		return 1.0;
		//light hit shadow
		if(atkType == 5&&targetType == 3)
		return 1.0;
		//light hit earth
		if(atkType == 5&&targetType == 4)
		return 1.0;
		//light hit light
		if(atkType == 5&&targetType == 5)
		return 1.0;
		//light hit time
		if(atkType == 5&&targetType == 6)
		return 1.0;
		//time hit fire
		if(atkType == 6&&targetType == 0)
		return 1.0;
		//time hit water
		if(atkType == 6&&targetType == 1)
		return 1.0;
		//time hit anima
		if(atkType == 6&&targetType == 2)
		return 1.0;
		//time hit shadow
		if(atkType == 6&&targetType == 3)
		return 1.0;
		//time hit earth
		if(atkType == 6&&targetType == 4)
		return 1.0;
		//time hit light
		if(atkType == 6&&targetType == 5)
		return 1.0;
		//time hit time
		if(atkType == 6&&targetType == 6)
		return 1.0;
		//no conditions satisfied
		return 1.0;
	}
}
