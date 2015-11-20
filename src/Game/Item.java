package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Item {


	//UI variables
	String name = "";		//name of the item that is displayed in a tooltip
	String description;		//description of the item that is displayed in a tooltip
	String type;			//category of item; helmet, torso, arms, legs, weapon, or consumable

	//Combat Stats
	double damage;		//damage multiplier of a weapon item; influences attack damage
	double speed;		//speed multiplier of a weapon item; influences order of attacks in battle
	int armor;			//armor rating of an armor item; affects physical damage resistance
	int magicResist;	//magic resistence granted by an armor item; affects magic resist

	int healAmt = 0;	//amount that an item restores hp
	int restAmt = 0;	//amount that an item restores sp
	boolean statusRestore = false;  //whether an item will alleviate an ailment

	//GUI variables

	int xPosition;
	int yPosition;

	BufferedImage itemArtwork = null;




	public Item(String name, String description, double damage, double speed)//weapon
	{
		this.type="weapon";
		this.name = name;
		this.description = description;
		this.type = type;
		this.damage = damage;
		this.speed = speed;
		this.armor = 0;
		this.magicResist = 0;
	}

	public Item(String name, String description, String type, int armor, int magicResist)//armor
	{
		this.name = name;
		this.description = description;
		this.type = type;
		this.armor = armor;
		this.magicResist = magicResist;
		this.damage = 0;
		this.speed = 0;
	}

	public Item (String name, String description, int heal, int rest, boolean restore)//consumable
	{
		this.type="consumable";
		this.name = name;
		this.description = description;
		this.type = type;
		this.healAmt = heal;
		this.restAmt = rest;
		this.statusRestore = restore;
	}
//	public Item(String name, String type) {
//
//		this.name = name;
//		this.type = type;
//
//		if (name.equals("sword")){
//			itemArtwork = GamePanel.sword;
//		}
//
//		else if (name.equals("test2")){
//			itemArtwork = GamePanel.monsterImage;
//		}
//		else if (name.equals("Weak Healing Potion")){
//
//		}
//
//	}

	public String getName()
	{
		return name;
	}

	public double getDamage()
	{
		return damage;
	}
	public void setDamage(double dmg)
	{
		damage = dmg;
	}
	public double getSpeed()
	{
		return speed;
	}
	public void setSpeed(double newSpeed)
	{
		this.speed = newSpeed;
	}
	public int getArmor()
	{
		return armor;
	}
	public void setArmor(int newArm)
	{
		armor = newArm;
	}
	public int getMagicResist()
	{
		return this.magicResist;
	}
	public void setMagicResist(int mre)
	{
		magicResist = mre;
	}

	//item types


	

	public void draw(Graphics2D g) {


		System.out.println("Drawing item on tile " );
		g.drawImage(itemArtwork,(int)((ApplicationUI.windowWidth/2)-16)+xPosition+6-(int)GamePanel.player.xpos,(int)((ApplicationUI.windowHeight/2)-16)+yPosition+6-(int)GamePanel.player.ypos,20,20,null);


	}

	/*
	 * Draw method for the info box when hovering over an inventorySlot
	 * 
	 * @param Graphics2D g
	 * @param Item i, the item that will have it's stats/name displayed
	 * 
	 * @return N/A
	 */
	public void drawItemToolTip(Graphics2D g, Item item) {

		if (item!=null){

			g.setColor(Color.white);
			g.fillRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
			g.setColor(Color.black);
			g.drawRect(Controller.mousePosition.x-75, Controller.mousePosition.y-60, 75, 50);
			g.drawString(item.name, Controller.mousePosition.x-70, Controller.mousePosition.y-45);
			g.drawString(item.type, Controller.mousePosition.x-70, Controller.mousePosition.y-35);

		}
	}

}
