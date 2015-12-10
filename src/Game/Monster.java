package Game;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
/**
 * Defines the monsters of the game
 * 
 * @author Matthew Finzel
 *
 */
public class Monster extends Entity{

	Point destination = new Point(0,0);
	double angleInRadians;
	double angleInDegrees;
	double baseMovementSpeed = 1;
	double movementSpeed = 1;
	double baseVisionWidth = 150;
	double baseViewDistance = 300;
	double visionWidth = 150;
	double viewDistance = 300;
	Rectangle collisionBox;
	public static BufferedImage monsterImage;
	Point anchor;
	Entity target;
	public Monster(int xpos, int ypos, String monsterType) {
		this.xpos = xpos;
		this.ypos = ypos;
		anchor = new Point(xpos,ypos);
		destination = new Point(xpos,ypos);
		if (monsterType.equals("monster1")){
			monsterImage = FileIO.loadImage("/Textures/Player.png");
		}
		collisionBox = new Rectangle(xpos,ypos,monsterImage.getWidth(),monsterImage.getHeight());
		changeDirection();
	}
	/*
	 * move towards destination
	 */
	public void moveTowardsDestination(){
		double desiredAngle = getAngleToDestination();

		if(atDestination()==false){

			//find angle between current position and destination
			angleInDegrees = getAngleToDestination();
			angleInRadians = Math.toRadians(angleInDegrees);
			//move towards destination
			setPosition(xpos+(movementSpeed*Math.cos(angleInRadians)),ypos+(movementSpeed*Math.sin(angleInRadians)));

		}

	}
	//pick a random destination and move towards it
	public void changeDirection(){
		if(target!=null){
			destination = new Point((int)target.xpos,(int)target.ypos);
		}
		else{
			//pick a random direction
			double angle = GamePanel.randomNumber(1,360);
			angleInRadians = Math.toRadians(angle);
			//pick a random point that is within 200 pixels of the anchor
			int distance = GamePanel.randomNumber(0,200);
			destination = new Point((int)(double)(anchor.x+(distance*Math.cos(angleInRadians))),(int)(double)(anchor.y+(distance*Math.sin(angleInRadians))));

		}
	}

	public void setPosition(double x, double y){
		Rectangle collisionBoxAtNewXPosition = new Rectangle((int)x,(int)ypos,32,32);
		Rectangle collisionBoxAtNewYPosition = new Rectangle((int)xpos,(int)y,32,32);
		boolean collidedWithSomethingX = false;
		boolean collidedWithSomethingY = false;
		for(int x1 = (int)(xpos/32)-((int)movementSpeed+1);x1<(int)(xpos/32)+(int)movementSpeed+1;x1++){
			for(int y1 = (int)(ypos/32)-((int)movementSpeed+1);y1<(int)(ypos/32)+(int)movementSpeed+1;y1++){
				if(x1>=0&&x1<GamePanel.levels.get(GamePanel.currentLevel).width&&y1>=0&&y1<GamePanel.levels.get(GamePanel.currentLevel).height){
					Tile temp = GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1];
					for(int i = 0; i<temp.collisionBoxes.size();i++){
						if(collisionBoxAtNewXPosition.intersects(temp.collisionBoxes.get(i))&&temp.collisionType>=1){
							//GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1].flagged=true;
							collidedWithSomethingX = true;

							if(temp.collisionType==2){
								temp.currentHealth-=1;
							}

						}

						//updates collisionBox
						Rectangle playerBox = new Rectangle((int)GamePanel.player.xpos, (int)GamePanel.player.ypos,32,32);

						//initiate battle if collides with player
						if (collisionBoxAtNewXPosition.intersects(playerBox)){

							if (!GamePanel.inBattle){
							GamePanel.inBattle = true;
							GamePanel.bat = new Battle(1.0);
							//change menu state
							GamePanel.menu.currentMenu = GamePanel.menu.combatMain;

							}
						}



					}
				}
			}
		}
		for(int x1 = (int)(xpos/32)-((int)movementSpeed+1);x1<(int)(xpos/32)+(int)movementSpeed+1;x1++){
			for(int y1 = (int)(ypos/32)-((int)movementSpeed+1);y1<(int)(ypos/32)+(int)movementSpeed+1;y1++){
				if(x1>=0&&x1<GamePanel.levels.get(GamePanel.currentLevel).width&&y1>=0&&y1<GamePanel.levels.get(GamePanel.currentLevel).height){
					Tile temp = GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1];
					for(int i = 0; i<temp.collisionBoxes.size();i++){
						if(collisionBoxAtNewYPosition.intersects(temp.collisionBoxes.get(i))&&temp.collisionType>=1){
							//GamePanel.levels.get(GamePanel.currentLevel).tileMap[x1][y1].flagged=true;
							collidedWithSomethingY = true;

							if(temp.collisionType==2){
								temp.currentHealth-=1;
							}

						}

						//updates collision box
						Rectangle playerBox = new Rectangle((int)GamePanel.player.xpos, (int)GamePanel.player.ypos,32,32);

						//initiate battle if collides with player
						if (collisionBoxAtNewYPosition.intersects(playerBox)){

							if (!GamePanel.inBattle){
							GamePanel.inBattle = true;
							GamePanel.bat = new Battle(1.0);
							//change menu state
							GamePanel.menu.currentMenu = GamePanel.menu.combatMain;
							}

						}



					}
				}
			}
		}
		//if player is going to collide with the tile at their future position

		if(collidedWithSomethingX==false){	
			if(!collidedWithSomethingY||true){
				xpos = x;//xpos+(movementSpeed*Math.cos(angleInRadians));
			}
			else{
				xpos+=movementSpeed;
			}
			collisionBox.x=(int)xpos;
		}
		if(collidedWithSomethingY==false){
			if(!collidedWithSomethingX||true){
				ypos = y;//ypos+(movementSpeed*Math.sin(angleInRadians));
			}
			else{
				ypos+=movementSpeed;
			}
			collisionBox.y=(int)ypos;
		}

		if(collidedWithSomethingX||collidedWithSomethingY){
			changeDirection();
		}


	}

	/*
	 * check if the player is already at their destination
	 * 
	 * @return true if player is at their destination, false if not
	 */
	public boolean atDestination(){
		if(Math.abs(xpos-destination.x)<=movementSpeed&&Math.abs(ypos-destination.y)<=movementSpeed){
			setPosition(destination.x,destination.y);
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
		moveTowardsDestination();
		if(xpos==destination.x&&ypos==destination.y){
			changeDirection();
		}
	}
	/*
	 * Draws the monster's vision cone to the screen
	 * 
	 * @param g - The Graphics2D object to use for drawing
	 */
	public void drawVisionCone(Graphics2D g){
		int [] xpositions = new int[3];
		int [] ypositions = new int[3];
		double angleA = Math.toRadians(angleInDegrees+90);
		double angleB = Math.toRadians(angleInDegrees-90);
		double x = (((ApplicationUI.windowWidth/2)-16)+(xpos+(viewDistance*Math.cos(angleInRadians))-(int)GamePanel.player.xpos));
		double y = (((ApplicationUI.windowHeight/2)-16)+(ypos+(viewDistance*Math.sin(angleInRadians))-(int)GamePanel.player.ypos));	
		double newX = 0;
		double newY = 0;

		Point firstVertex = new Point(((ApplicationUI.windowWidth/2))+(int)(xpos)-(int)GamePanel.player.xpos,((ApplicationUI.windowHeight/2))+(int)ypos-(int)GamePanel.player.ypos);
		xpositions[0]=firstVertex.x;
		ypositions[0]=firstVertex.y;

		newX = x+(Math.cos(angleA)*visionWidth)-(Math.cos(angleA)*(visionWidth/2));
		newY = y+(Math.sin(angleA)*visionWidth)-(Math.sin(angleA)*(visionWidth/2));

		Point secondVertex = new Point((int)newX,(int)newY);
		xpositions[1]=secondVertex.x;
		ypositions[1]=secondVertex.y;

		newX = x+(Math.cos(angleB)*visionWidth)-(Math.cos(angleB)*(visionWidth/2));
		newY = y+(Math.sin(angleB)*visionWidth)-(Math.sin(angleB)*(visionWidth/2));

		Point thirdVertex = new Point((int)newX,(int)newY);
		xpositions[2]=thirdVertex.x;
		ypositions[2]=thirdVertex.y;


		Polygon visionCone = new Polygon(xpositions,ypositions,3);
		g.setColor(new Color(255,0,0,25));
		g.fillPolygon(visionCone);
		if(visionCone.intersects(GamePanel.player.collisionBox)){
			target = GamePanel.player;
			changeDirection();
			movementSpeed = baseMovementSpeed*3;
			if(viewDistance<baseViewDistance*2){
				viewDistance +=2;
				visionWidth +=2;
			}
		}
		else{
			if(viewDistance>baseViewDistance){
				viewDistance-=1;
				visionWidth-=1;
			}
			target = null;
			movementSpeed = baseMovementSpeed;
		}
	}
	/*
	 * Draws the monster to the screen
	 * 
	 * @param g - The Graphics2D object to use for drawing
	 */
	public void Draw(Graphics2D g){
		int x = (int)(double)((((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos));
		int y = (int)(double)((((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos));
		g.drawImage(GamePanel.monsterImage,x,y,32,32,null);	
		drawVisionCone(g);

	}

}
