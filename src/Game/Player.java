package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player {
	int xpos;
	int ypos;
	int direction = 0;

	int currentForm = 0;
	int walkAnimFrames=16;
	int walkingSpeed = 512/walkAnimFrames;

	int currentHealth;
	int health;
	int avoid;
	int defense;
	int willpower = 1;

	boolean moveLeft = false;
	boolean moveRight = false;
	boolean moveDown = false;
	boolean moveUp = false;

	boolean animationIsActive = false;
	boolean battling = false;
	ArrayList<Creature> forms = new ArrayList<Creature>();
	BufferedImage[][] artwork;
	Creature currentTarget;
	Animation walkAnim;
	public Player(int x, int y){
		xpos = x;
		ypos = y;
		artwork = GamePanel.playersprites;
		int color = 0;
		int roll = GamePanel.randomNumber(1, 2000);
		if(roll==2000){// 1/2000
			color = 2;
		}
		else if(roll <=5){// 1/400
			color = 1;
		}
		else{
			color = 0;
		}
		//forms.add(new Creature(3,GamePanel.randomNumber(2, 3),color));
	}
	//attempt to take over the mind of the current target
	public void possessCurrentTarget(){
		if(currentTarget!=null){
			if(GamePanel.randomNumber(0, currentTarget.willPower)==0){
				forms.add(currentTarget);
				battling = false;
				GamePanel.messages.add("You have gained control of a "+currentTarget.name+"!");
				currentForm=forms.size()-1;
				//leave the spirit world
				if(GamePanel.spiritWorldOn){
					GamePanel.spiritWorldOn=false;
					for(int i = 0; i<GamePanel.clouds.size();i++){
						GamePanel.clouds.get(i).growthRate=-1;
					}
				}
			}
			else{
				ArrayList<String> msgs = new ArrayList<String>();
				msgs.add("The "+ currentTarget.name+" resisted your attempt to control it's mind!");
				msgs.add(currentTarget.name+"'s mind struggled out of your grasp!");
				msgs.add(currentTarget.name+" was not affected by your attempt to control it!");
				GamePanel.messages.add(msgs.get(GamePanel.randomNumber(0, msgs.size()-1)));
			}
		}
	}
	//show the player's available forms so that they can interact with their different forms
	public void showForms(){

	}
	public void moveNorth(){
		System.out.println("moving north");
		//determine what tile the player is currently on and what tile is above them
		Tile current = GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos];
		Tile northernTile = null;
		if(ypos-1>=0){
			northernTile= GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos-1];
		}
		if(northernTile!=null&&!AppletUI.client.players.get(0).battling){
			if(current.exitCollisionType[0]==1&&northernTile.enterCollisionType[0]==1&&walkAnim==null||GamePanel.editMode){
				walkAnim = new Animation(GamePanel.playersprites, walkAnimFrames, walkingSpeed, 32, 3, 944,514, false, 0, 0);
				if(GamePanel.editMode)
					ypos-=1;
				direction = 3;
				//				if(GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass!=null)
				//					GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass.enterGrass();

				if(GamePanel.spiritWorldOn){
					walkAnim.motionBlur=true;
				}
			}
			else if(current.exitCollisionType[0]==1&&northernTile.enterCollisionType[0]==2){
				System.out.println("Entering door");
				//ypos-=1;
				northernTile.door.enterDoor();
			}
		}
		//direction = 3;
	}
	public void moveEast(){
		//determine what tile the player is currently on and what tile is above them
		Tile current = GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos];
		Tile EasternTile = null;
		if(xpos+1<GamePanel.rooms.get(GamePanel.currentRoom).width){
			EasternTile= GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos+1][ypos];
		}
		if(EasternTile!=null&&!AppletUI.client.players.get(0).battling){
			if(current.exitCollisionType[1]==1&&EasternTile.enterCollisionType[1]==1&&walkAnim==null||GamePanel.editMode){
				walkAnim = new Animation(GamePanel.playersprites, walkAnimFrames, walkingSpeed, 32, 1, 944,514, false, 0, 0);
				if(GamePanel.editMode)
					xpos+=1;
				direction = 1;
				//				if(GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass!=null)
				//					GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass.enterGrass();

				if(GamePanel.spiritWorldOn){
					walkAnim.motionBlur=true;
				}
			}
		}
		//direction = 1;
	}
	public void moveSouth(){
		//determine what tile the player is currently on and what tile is above them
		Tile current = GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos];
		Tile southernTile = null;
		if(ypos+1<GamePanel.rooms.get(GamePanel.currentRoom).height){
			southernTile= GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos+1];
		}
		if(southernTile!=null&&!AppletUI.client.players.get(0).battling){
			if(current.exitCollisionType[2]==1&&southernTile.enterCollisionType[2]==1&&walkAnim==null||GamePanel.editMode){
				walkAnim = new Animation(GamePanel.playersprites, walkAnimFrames, walkingSpeed, 32, 0, 944,514, false, 0, 0);
				if(GamePanel.editMode)
					ypos+=1;
				direction = 0;


				if(GamePanel.spiritWorldOn){
					walkAnim.motionBlur=true;
				}
			}
			else if(current.exitCollisionType[0]==1&&southernTile.enterCollisionType[2]==2){
				System.out.println("Entering door");
				//ypos-=1;
				southernTile.door.enterDoor();
			}
		}
		//direction = 0;
	}
	public void moveWest(){
		//determine what tile the player is currently on and what tile is above them
		Tile current = GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos];
		Tile westernTile = null;
		if(xpos-1>=0){
			westernTile= GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos-1][ypos];
		}
		if(westernTile!=null&&!AppletUI.client.players.get(0).battling){
			if(current.exitCollisionType[3]==1&&westernTile.enterCollisionType[3]==1&&walkAnim==null||GamePanel.editMode){
				walkAnim = new Animation(GamePanel.playersprites, walkAnimFrames, walkingSpeed, 32, 2, 944, 514, false, 0, 0);
				if(GamePanel.editMode)
					xpos-=1;
				direction = 2;
				//				if(GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass!=null)
				//					GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass.enterGrass();

				if(GamePanel.spiritWorldOn){
					walkAnim.motionBlur=true;
				}
			}
		}
		//direction = 2;
	}
	public void enterBattle(int monsterID, int color){
		int max = GamePanel.rooms.get(GamePanel.currentRoom).maxLevel;
		int min = GamePanel.rooms.get(GamePanel.currentRoom).minLevel;
		currentTarget = new Creature(GamePanel.randomNumber(min,max),monsterID,color);
		battling = true;
		GamePanel.buttons.add(new Button(GamePanel.battlePos.x+5,GamePanel.battlePos.y+305,0));
		GamePanel.buttons.add(new Button(GamePanel.battlePos.x+5,GamePanel.battlePos.y+355,1));
		GamePanel.buttons.add(new Button(GamePanel.battlePos.x+305,GamePanel.battlePos.y+305,2));
		GamePanel.buttons.add(new Button(GamePanel.battlePos.x+305,GamePanel.battlePos.y+355,3));
	}
	public void Draw(Graphics g){
		if(!GamePanel.editMode){
			//			xpos = Controller.cursorPos.x*32;
			//			ypos = Controller.cursorPos.y*32;
			int x = xpos*32;
			int y = ypos*32;
			if(walkAnim!=null){
				//System.out.println("draw anim at: "+walkAnim.xpos+","+walkAnim.ypos);
				if(GamePanel.spiritWorldOn){
					walkAnim.spriteSheet=GamePanel.playerSpritSprites[direction];
				}
				else{
					walkAnim.spriteSheet=GamePanel.playersprites[direction];
				}
				walkAnim.Draw(g);
				if(walkAnim.framesDrawn>=walkAnim.getFrameCount()-1){

					if(direction==2){
						xpos-=1;
					}
					else if(direction==1){
						xpos+=1;
					}
					else if(direction==3){
						ypos-=1;
					}
					else if(direction==0){
						ypos+=1;
					}
					walkAnim=null;
					if(GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass!=null)
						GamePanel.rooms.get(GamePanel.currentRoom).tiles[xpos][ypos].grass.enterGrass();
				}

			}
			else {
				if(GamePanel.spiritWorldOn==false){
					g.drawImage(artwork[direction][0],944,514,32,32,null);
				}
				else{
					g.drawImage(GamePanel.playerSpritSprites[direction][0],944,514,32,32,null);
				}
				//System.out.println("draw static sprite");
			}

		}

	}
}
