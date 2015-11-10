package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Item {

	String name = "";
	String description;
	boolean onGround;
	double damage;
	double speed;
	int mana;
	int health;
	int armor;
	int magicResist;
	int xID;
	int yID;
	BufferedImage itemArtwork = null;
	
	public double getDamage()
	{
		return damage;
	}
	public void setDamage(double dmg)
	{
		damage = dmg;
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
	
	public void drop()
	{
		onGround = true;
	}
	
	public Item(String name, String description, double damage, double speed)
	{
		this.name = name;
		this.description = description;
		this.damage = damage;
		this.speed = speed;
		this.armor = 0;
		this.magicResist = 0;
	}
	
	public Item(String name, String description, int armor, int magicResist)
	{
		this.name = name;
		this.description = description;
		this.armor = armor;
		this.magicResist = magicResist;
		this.damage = 0;
		this.speed = 0;
	}
	
	public Item(String name, String type, int stat)
	{
		this.name = name;
		this.onGround = false;
		this.type = type;
		if(type.equals("Weapon"))
		{
			setDamage(stat);
			setArmor(0);
		}
		else 
			{
				setArmor(stat);
				setDamage(0);
			}
	}
	
	//item types
	String type;

	public Item(String name, boolean onGround, String type) {

		this.name = name;
		this.onGround = onGround;
		this.type = type;
		
		if (name.equals("sword")){
			itemArtwork = GamePanel.sword;
		}
		
		else if (name.equals("test2")){
			itemArtwork = GamePanel.monsterImage;
		}

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
