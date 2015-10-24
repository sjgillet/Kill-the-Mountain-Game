package Game;


import java.awt.Point;

public class Door {
	int xposA;
	int xposB;
	Point spawnPointA;
	Point spawnPointB;
	int yposA;
	int yposB;
	
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
	public Door(int x, int y, Level destination, int destX, int destY){
		xposA = x;
		yposA = y;
		levelB = destination;
		spawnPointB = new Point(xposB,yposB);
	}
	public void enterDoor(){
		GamePanel.setCurrentLevel(levelB);
	}
}
