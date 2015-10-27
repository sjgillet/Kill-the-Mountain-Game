package Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 7734877696044080629L;
	public static BufferedImage [][] tiles = Images.cut("/Tiles/overlappedTiles.png", 32, 32);
	public static BufferedImage [][] fog = Images.cut("/Textures/Fog.png", 200, 200);
	public static BufferedImage darkness = Images.load("/Textures/Darkness.png");
	public static BufferedImage [][] spiritTiles = Images.cut("/Tiles/overlappedTiles2.png", 32, 32);
	public static BufferedImage [][] playersprites = Images.cut("/Creatures/Wizard.png", 32, 32);
	public static BufferedImage [][] playerSpritSprites = Images.cut("/Creatures/Wizard2.png", 32, 32);
	public static BufferedImage [][] barBackgrounds = Images.cut("/Textures/BarBackgrounds.png", 300, 20);
	public static BufferedImage [][] passiveNodes = Images.cut("/Textures/PassiveNodes.png", 50, 50);
	public static BufferedImage [][] passiveEdgeHighlights = Images.cut("/Textures/PassiveEdgeHighlights.png", 50, 50);
	public static BufferedImage [][] forestGrub = Images.cut("/Creatures/ForestGrub.png", 100, 100);
	public static BufferedImage [][] fireFly = Images.cut("/Creatures/FireFly.png", 100, 100);
	public static BufferedImage [][] monkey = Images.cut("/Creatures/LongArm.png", 100, 100);
	public static BufferedImage [][] dragon = Images.cut("/Creatures/dragon.png", 100, 100);
	public static BufferedImage [][] pig = Images.cut("/Creatures/Pig.png", 100, 100);
	public static BufferedImage [][] bird = Images.cut("/Creatures/SunnyTheBird.png", 100, 100);
	public static BufferedImage [][] grassTextures = Images.cut("/Tiles/Grass.png", 32, 64);
	public static BufferedImage [][] spiritGrassTextures = Images.cut("/Tiles/Grass2.png", 32, 64);
	public static BufferedImage [][] stones = Images.cut("/Creatures/CaptureStone.png", 100, 100);
	public static BufferedImage [][] leaves = Images.cut("/Tiles/leafParticleAnim.png", 40, 40);
	public static BufferedImage [][] spiritLeaves = Images.cut("/Tiles/leafParticleAnim2.png", 40, 40);
	public static BufferedImage [][] goldLeaves = Images.cut("/Tiles/goldLeafParticleAnim.png", 40, 40);
	public static BufferedImage apri[][] = Images.cut("/Textures/APRI.png",190,40);
	public static BufferedImage tilesTooltip = Images.load("/Tiles/overlappedTiles.png");
	public static BufferedImage namePlate = Images.load("/Textures/NamePlate.png");
	public static BufferedImage spiritFade = Images.load("/Textures/SpiritFade.png");
	public static BufferedImage battleBackground = Images.load("/Textures/BattleBackground.png");
	public static BufferedImage[][] cursorImg = Images.cut("/Textures/Cursor.png", 36, 36);
	public static BufferedImage[][] bars = Images.cut("/Textures/Bars.png", 3, 30);
	public static BufferedImage descriptionPanel = Images.load("/Tiles/DescriptionPanel.png");
	public static BufferedImage button = Images.load("/Tiles/DropDownButton.png");
	public static BufferedImage battleFade = Images.load("/Textures/BattleFade.png");
	public static BufferedImage wizardBattler = Images.load("/Creatures/WizardBattler.png");
	public static Point battlePos;
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	public static ArrayList<ConfirmationBox> confirmationBoxes = new ArrayList<ConfirmationBox>();
	public static Animation cursor = new Animation(cursorImg, 2, 500, 36, 0, (Controller.cursorPos.x*32)-2, (Controller.cursorPos.y*32)-2, true, 0, 0);
	public static int currentRoom = 0;
	public static Tooltip tooltip = new Tooltip(1600,0);
	public static boolean editMode = false;
	public static boolean spiritWorldOn = true;
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	public static ArrayList<String> messages = new ArrayList<String>();
	public static ArrayList<FogCloud> clouds = new ArrayList<FogCloud>();
	public GamePanel(){
		this.setDoubleBuffered(true);
		rooms.add(new Room("Teyl Forest",121,72));//outdoor area's need to be atleast 91 wide and 48 tall (61,36 for full screen)
		rooms.add(new Room("Forest Cabin",20,10));
		rooms.add(new Room("Forbidden Cave",50,30));
		//AppletUI.client.players.add(new Player(0,0));
		Controller.loadLevel(GamePanel.rooms.get(GamePanel.currentRoom).name);
		for(int i = 0; i<20; i++){
			GamePanel.clouds.add(new FogCloud(GamePanel.randomNumber(0, 2420),GamePanel.randomNumber(0, 1080)));
		}
	}
	static int randomNumber(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw(g);
	}

	public void Draw(Graphics g){
		rooms.get(currentRoom).Draw(g);
		if(editMode){
			if((tooltip.cursorPos.x)>=7&&(tooltip.cursorPos.x)<=8&&(tooltip.cursorPos.y)>=4&&(tooltip.cursorPos.y)<=6){//tree is selected
				cursor.frameWidth=64;
				cursor.frameHeight=96;
				cursor.Draw(g);
			}
			else{
				cursor.frameWidth=32;
				cursor.frameHeight=32;
				cursor.Draw(g);
			}
			tooltip.Draw(g);
		}
		else{

		}
		if(confirmationBoxes.size()>0){
			confirmationBoxes.get(confirmationBoxes.size()-1).Draw(g);
		}
		
		for(int i = 0; i<1;i++){
			
			if(AppletUI.client.players.get(i).battling==true&&AppletUI.client.players.get(i).currentTarget!=null&&AppletUI.client.players.get(i).currentTarget.Sprites!=null){
				g.drawImage(battleFade,0,0,AppletUI.windowWidth,AppletUI.windowHeight,null);
				//opponent
				g.drawImage(battleBackground,battlePos.x,battlePos.y,500,400,null);
				g.drawImage(namePlate,battlePos.x,battlePos.y,400,80,null);
				g.drawImage(AppletUI.client.players.get(i).currentTarget.Sprites[0][AppletUI.client.players.get(i).currentTarget.color],battlePos.x+400,battlePos.y,100,100,null);
				//draw opponent's health bar
				g.drawImage(barBackgrounds[0][0],battlePos.x+90,battlePos.y+5,300,20,null);
				for(int j = 0;j<100;j++){
					if(j<=(AppletUI.client.players.get(i).currentTarget.currentHealth/AppletUI.client.players.get(i).currentTarget.health)*100){
						int percentHP = (int)(((double)((double)AppletUI.client.players.get(i).currentTarget.currentHealth/(double)AppletUI.client.players.get(i).currentTarget.health))*100.0);
						if(percentHP>60){
							g.drawImage(bars[0][0],battlePos.x+90+(j*3),battlePos.y+5,3,20,null);
						}
						else if(percentHP<=60&&percentHP>=20){
							g.drawImage(bars[1][0],battlePos.x+90+(j*3),battlePos.y+5,3,20,null);
						}
						else{
							g.drawImage(bars[2][0],battlePos.x+90+(j*3),battlePos.y+5,3,20,null);
						}
					}
				}
				//draw the opponent's energy bar
				g.drawImage(barBackgrounds[0][1],battlePos.x+90,battlePos.y+30,300,20,null);
				for(int j = 0;j<100;j++){
					if(AppletUI.client.players.get(i).currentTarget.currentEnergy>0){
						if(j<=(AppletUI.client.players.get(i).currentTarget.currentEnergy/AppletUI.client.players.get(i).currentTarget.energy)*100){

							g.drawImage(bars[5][0],battlePos.x+190+(j*3),battlePos.y+30,3,20,null);

						}
					}
				}
				//player
				g.drawImage(namePlate,battlePos.x,battlePos.y+300,500,100,null);
				g.drawImage(namePlate,battlePos.x+100,battlePos.y+220,400,80,null);
				//if the player has a form
				if(AppletUI.client.players.get(i).forms.size()>0){
					g.drawImage(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).Sprites[1][AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).color],battlePos.x+20,battlePos.y+200,100,100,null);
				}
				else{
					g.drawImage(wizardBattler,battlePos.x+20,battlePos.y+200,100,100,null);
				}
				//draw player's creature's health bar
				g.drawImage(barBackgrounds[0][0],battlePos.x+190,battlePos.y+225,300,20,null);
				for(int j = 0;j<100;j++){
					if(AppletUI.client.players.get(i).forms.size()>0){
						if(j<=(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentHealth/AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).health)*100){
							//int percentHP = (int)((double)(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentHealth/(double)AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).health)*100);
							int percentHP = (int)(((double)((double)AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentHealth/(double)AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).health))*100.0);
							if(percentHP>60){
								g.drawImage(bars[0][0],battlePos.x+190+(j*3),battlePos.y+225,3,20,null);
							}
							else if(percentHP<=60&&percentHP>=20){
								g.drawImage(bars[1][0],battlePos.x+190+(j*3),battlePos.y+225,3,20,null);
							}
							else{
								g.drawImage(bars[2][0],battlePos.x+190+(j*3),battlePos.y+225,3,20,null);
							}
						}
						else{
							int percentHP = (int)(((double)((double)AppletUI.client.players.get(i).currentHealth/(double)AppletUI.client.players.get(i).health))*100.0);
							if(percentHP>60){
								g.drawImage(bars[0][0],battlePos.x+190+(j*3),battlePos.y+225,3,20,null);
							}
							else if(percentHP<=60&&percentHP>=20){
								g.drawImage(bars[1][0],battlePos.x+190+(j*3),battlePos.y+225,3,20,null);
							}
							else{
								g.drawImage(bars[2][0],battlePos.x+190+(j*3),battlePos.y+225,3,20,null);
							}
						}
					}
				}
				if(AppletUI.client.players.get(i).forms.size()>0){
					//draw the player's creature's energy bar
					g.drawImage(barBackgrounds[0][1],battlePos.x+190,battlePos.y+250,300,20,null);
					for(int j = 0;j<100;j++){
						if(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentEnergy>0){
							if(j<=(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentEnergy/AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).energy)*100){

								g.drawImage(bars[5][0],battlePos.x+190+(j*3),battlePos.y+250,3,20,null);

							}
						}
					}
					//draw the player's creature's experience bar
					g.drawImage(barBackgrounds[0][2],battlePos.x+190,battlePos.y+275,300,20,null);
					for(int j = 0;j<100;j++){
						if(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentXP>0){
							if(j<=((double)((double)(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).currentXP)/(double)(AppletUI.client.players.get(i).forms.get(AppletUI.client.players.get(0).currentForm).experienceToLevel)))*100){

								g.drawImage(bars[4][0],battlePos.x+190+(j*3),battlePos.y+275,3,20,null);

							}
						}
					}
				}
				//draw the attack, possess, run, and item buttons
				for(int k = 0; k<buttons.size();k++){
					buttons.get(k).Draw(g);
				}
			}
		}
		//draw fog
		if(GamePanel.spiritWorldOn){
			//g.drawImage(spiritFade,0,0,1920,1080,null);
		}
		for(int i = 0;i<clouds.size();i++){
			clouds.get(i).Draw(g);
			if(clouds.get(i).xpos+clouds.get(i).width<0){
				clouds.remove(i);
				if(GamePanel.spiritWorldOn)
					clouds.add(new FogCloud(2420,GamePanel.randomNumber(0, 1080)));
			}
		}

		if(messages.size()>0){
			Font font = new Font("Iwona Heavy",Font.BOLD,14);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawImage(namePlate,battlePos.x,battlePos.y+300,500,100,null);
			g.drawString(messages.get(messages.size()-1), battlePos.x+5, battlePos.y+335);
		}
		if(AppletUI.client.players.get(0).forms.size()>0){
			if(AppletUI.client.players.get(0).forms.get(AppletUI.client.players.get(0).currentForm).talents.visible){
				AppletUI.client.players.get(0).forms.get(AppletUI.client.players.get(0).currentForm).talents.Draw(g);
			}
		}
		Font font = new Font("Iwona Heavy",Font.BOLD,16);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Current FPS: "+AppletUI.currentFPS, 1800, 40);
	}
}
