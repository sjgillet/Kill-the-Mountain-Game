package Game;

import java.awt.Graphics;

public class Attack {
	String name;
	int type;
	int dmg;
	int heal;
	int energyCost;
	String msg;
	final int fire = 0;
	final int water = 1;
	final int anima = 2;
	final int shadow = 3;
	final int earth = 4;
	final int light = 5;
	final int time = 6;
	public Attack(String ID){
		name = ID;
		if(name == "Bite"){
			type = anima;
			dmg = 5;
			energyCost = 3;
		}
		else if(name == "Charge"){
			type = anima;
			dmg = 8;
			energyCost = 15;
		}
		else if(name == "Peck"){
			type = light;
			dmg = 6;
			energyCost = 4;
		}
		else if(name == "Punch"){
			type = anima;
			dmg = 10;
			energyCost = 10;
		}
		else if(name == "Lash"){
			type = anima;
			dmg = 8;
			energyCost = 5;
		}
		else if(name == "Rain"){
			type = water;
			dmg = 2;
			energyCost = 1;
		}
		else if(name == "Ignite"){
			type = anima;
			dmg = 2;
			energyCost = 1;
		}
		else if(name == "Scare"){
			type = shadow;
			dmg = 8;
			energyCost = 15;
		}
		else if(name == "Enlighten"){
			type = light;
			dmg = 5;
			energyCost = 3;
		}
		else if(name == "Restore"){
			type = time;
			dmg = -1;
			msg = "Time was reverted!";
			energyCost = 20;
		}
	}
	public void use(Creature user, Creature target){
		if(user.energy>=energyCost){
			//heal the user for the ammount the attack heals for
			user.restoreHP(heal);
			//determine if the user's chance to hit
			int hitChance = (int)((double)(100*((double)user.accuracy/(double)target.avoid)));
			//if the user hit
			if(GamePanel.randomNumber(1, 100)<hitChance){//hit
				target.takeDmg(user, this, this.dmg, this.type);
				
			}
			else{
				GamePanel.messages.add("Your"+user.name+"'s attack missed!");
			}
			
		}
	}
	public void Draw(Graphics g){
		
	}
}
