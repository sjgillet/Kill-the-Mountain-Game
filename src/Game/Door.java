package Game;

import java.awt.Point;

public class Door {
	int roomA;
	Point roomAPos;//set the position to spawn the player at when they enter room A
	int roomB;
	Point roomBPos;//set the position to spawn the player at when they enter room B
	public Door(int a, int b){
		roomA = a;
		roomB = b;
		roomAPos = new Point (0,0);
		roomBPos = new Point (0,0);
	}
	public void enterDoor(){
		//if the player is currently in room A
		if(GamePanel.currentRoom==roomA){
			//move them to room b
			GamePanel.currentRoom=roomB;
			//set the player to be at the desired position in room b
			AppletUI.client.players.get(0).xpos=roomBPos.x;
			AppletUI.client.players.get(0).ypos=roomBPos.y;
			Controller.loadLevel(GamePanel.rooms.get(GamePanel.currentRoom).name);
		}
		else{//if the player is currently in room b
			//move them to room a
			GamePanel.currentRoom=roomA;
			//set the player to be at the desired position in room a
			AppletUI.client.players.get(0).xpos=roomAPos.x;
			AppletUI.client.players.get(0).ypos=roomAPos.y;
			Controller.loadLevel(GamePanel.rooms.get(GamePanel.currentRoom).name);
		}
	}
}
