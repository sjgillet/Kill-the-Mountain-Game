package Game;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Door {
	int xposA;
	int xposB;
	int yposA;
	int yposB;
	Point spawnPointA;
	Point spawnPointB;
	int updatesSinceEntered = 0;
	int id;
	Tile[][] doorTiles = new Tile[3][2];

	Level levelB;
	/**
	 * Used to transition the player between levels
	 * 
	 * @param x - the x position of the door in the level it's in
	 * @param y - the y position of the door in the level it's in
	 * @param destination - the level that will be loaded when this door is entered
	 * @param destX - the x position to put the player at in the level that is loaded
	 * @param destY - the y position to put the player at in the level that is loaded
	 */
	public Door(int x, int y, Level destination, int ID, int x2, int y2){
		xposA = x*32;
		yposA = y*32;
		id=ID;
		levelB = destination;
		spawnPointB = new Point(x2*32,y2*32);		
	}
	/*
	 * Used to check whether something collides with this door, opens the door if true, closes it if false
	 * 
	 * @param rect - the rectangle to check for collisions with this door
	 * 
	 * @return true if the door collides, false if not
	 */
	public boolean collidesWith(Rectangle rect){
		Rectangle collisionBox = new Rectangle(((ApplicationUI.windowWidth/2)-16)+xposA-(int)GamePanel.player.xpos,((ApplicationUI.windowHeight/2)-16)+yposA-(int)GamePanel.player.ypos,96,64);
		if(rect.intersects(collisionBox)){
			toggleDoor(true);
			return true;
		}
		toggleDoor(false);
		return false;
	}
	/*
	 * Toggles whether or not the door is open
	 * 
	 * @param isOpen - a boolean which is true if the door should be set to an open state
	 */
	public void toggleDoor(boolean isOpen){
		if(id==1){//house door
			for(int x = 0; x<3;x++){
				for(int y = 0; y<2; y++){
					if(isOpen){//open the door
						doorTiles[x][y].isOpen = true;
					}
					else{//close the door
						doorTiles[x][y].isOpen = false;
					}
				}
			}
		}
	}
	/*
	 * Moves the player to a new position and changes the current level
	 */
	public void enterDoor(){
		if(updatesSinceEntered>=0){
			System.out.println("spawn point: "+spawnPointB);
			GamePanel.player.setPosition(spawnPointB.x,spawnPointB.y,2);
			GamePanel.player.destination = new Point(spawnPointB.x,spawnPointB.y);



			GamePanel.loading=true;
				GamePanel.setCurrentLevel(levelB);
			GamePanel.loading=false;
			updatesSinceEntered = 0;
		}
		else{
			updatesSinceEntered++;
		}
	}
	
}
