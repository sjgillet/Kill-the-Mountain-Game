package Game;


import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Player extends Entity{

	PlayerCombatant.playerRace race; PlayerCombatant.combatClass cls;
	Inventory inventory = new Inventory(0,0);
	PlayerCombatant playerCombatant = null;


	Point destination = new Point(0,0);
	double angleInRadians;
	double angleInDegrees;
	double movementSpeed = 6;
	Rectangle collisionBox;
	int updatesInQue = 0;

	public Item[] getEquipment()
	{
		Item[] items = new Item[5];
		for(int i = 0; i < inventory.equipped.length; i++)
			if(inventory.equipped[i] != null)
				items[i] = inventory.equipped[i].item;
		return items;
	}

	public Player(int x, int y){
		this.xpos = x;
		this.ypos = y;
		collisionBox = new Rectangle(x,y,32,32);
	}
	public void moveTowardsDestination(double speed){

		if(!atDestination()){
			//find angle between current position and destination
			angleInDegrees = getAngleToDestination();
			angleInRadians = Math.toRadians(angleInDegrees);
			//move towards destination
			setPosition(xpos+(speed*Math.cos(angleInRadians)),ypos+(movementSpeed*Math.sin(angleInRadians)),speed);


		}
	}
	public void setPosition(double x, double y, double speed){
		boolean enteredADoor = false;
		Rectangle collisionBoxAtNewXPosition = new Rectangle((int)x,(int)ypos,32,32);
		Rectangle collisionBoxAtNewYPosition = new Rectangle((int)xpos,(int)y,32,32);
		boolean collidedWithSomethingX = false;
		boolean collidedWithSomethingY = false;
		for(int x1 = (int)(xpos/32)-((int)speed+1);x1<(int)(xpos/32)+(int)speed+1;x1++){
			for(int y1 = (int)(ypos/32)-((int)speed+1);y1<(int)(ypos/32)+(int)speed+1;y1++){
				if(x1>=0&&x1<GamePanel.levels.get(GamePanel.currentLevel).width&&y1>=0&&y1<GamePanel.levels.get(GamePanel.currentLevel).height){
					if(GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1].elevation>=GamePanel.levels.get(GamePanel.currentLevel).waterlevel){
						Tile temp = GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1];
						for(int i = 0; i<temp.collisionBoxes.size();i++){
							if(collisionBoxAtNewXPosition.intersects(temp.collisionBoxes.get(i))&&temp.collisionType>=1){
								temp.flagged=true;
								//GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1].flagged=true;
								collidedWithSomethingX = true;
								if(temp.collisionType==2){
									temp.currentHealth-=1;
								}
								else if(temp.collisionType==3){
									enteredADoor=true;
									collidedWithSomethingX = false;
									temp.door.enterDoor();
								}
							}
						}
					}
				}
				else{
					//collidedWithSomethingX = true;
				}
			}
		}
		for(int x1 = (int)(xpos/32)-((int)speed+1);x1<(int)(xpos/32)+(int)speed+1;x1++){
			for(int y1 = (int)(ypos/32)-((int)speed+1);y1<(int)(ypos/32)+(int)speed+1;y1++){
				if(x1>=0&&x1<GamePanel.levels.get(GamePanel.currentLevel).width&&y1>=0&&y1<GamePanel.levels.get(GamePanel.currentLevel).height){
					if(GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1].elevation>=GamePanel.levels.get(GamePanel.currentLevel).waterlevel){
						Tile temp = GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1];
						for(int i = 0; i<temp.collisionBoxes.size();i++){
							if(collisionBoxAtNewYPosition.intersects(temp.collisionBoxes.get(i))&&temp.collisionType>=1){
								//GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1].flagged=true;
								collidedWithSomethingY = true;
								if(temp.collisionType==2){
									temp.currentHealth-=1;
								}
								else if(temp.collisionType==3){
									enteredADoor=true;
									collidedWithSomethingY = false;
									temp.door.enterDoor();
								}
							}
						}
					}
				}
				else{
					//collidedWithSomethingY=true;
				}
			}
		}
		if(!enteredADoor){
			//if player is going to collide with the tile at their future position
			if((collidedWithSomethingX==false||(GamePanel.godmode))&&((!GamePanel.paused)&&(!GamePanel.inInventory))){
				xpos = x;
				collisionBox.x=(int)(double)((((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos));

			}
			else{
				if(speed>1){
					//moveTowardsDestination(1);
				}
			}
			if((collidedWithSomethingY==false||(GamePanel.godmode))&&((!GamePanel.paused)&&(!GamePanel.inInventory))){
				ypos = y;
				collisionBox.y=(int)(double)((((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos));

			}
			else{
				if(speed>1){
					//moveTowardsDestination(1);
				}
			}
		}
	}
	/*
	 * check if the player is already at their destination
	 * 
	 * @return true if player is at their destination, false if not
	 */
	public boolean atDestination(){
		if(Math.abs(xpos-destination.x)<=movementSpeed&&Math.abs(ypos-destination.y)<=movementSpeed){
			setPosition(destination.x,destination.y,movementSpeed);
			return true;
		}
		return false;
	}
	/*
	 * get angle between the player and the destination
	 * 
	 * @return the angle to move in
	 */
	public double getAngleToDestination() {
		double angle = (float) Math.toDegrees(Math.atan2((double)destination.y - ypos, (double)destination.x - xpos));
		if(angle < 0){
			angle += 360;
		}
		return angle;
	}

	public void update(){

		if(!GamePanel.levels.get(GamePanel.currentLevel).drawingLevel||true){
			moveTowardsDestination(movementSpeed);
		}
		else{
			//updatesInQue++;

		}

	}

	public void Draw(Graphics2D g){
		g.drawImage(GamePanel.playerImage,(ApplicationUI.windowWidth/2)-16,(ApplicationUI.windowHeight/2)-16,32,32,null);

	}
}
