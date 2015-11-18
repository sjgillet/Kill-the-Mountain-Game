package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 496501089018037548L;
	public static BufferedImage[][] tiles = FileIO.loadSpriteSheet("/Textures/overlappedTiles.png", 32, 32);
	public static BufferedImage colorGradients = FileIO.loadImage("/Textures/OverworldGradients.png");
	public static BufferedImage[][] overlayTiles = FileIO.loadSpriteSheet("/Textures/TileOverlays.png", 32, 32);
	public static BufferedImage sword = FileIO.loadImage("/Textures/Sword.png");
	public static BufferedImage titleScreen = FileIO.loadImage("/Textures/TitleScreen.png");
	

	public static BufferedImage playerImage = FileIO.loadImage("/Textures/Player.png");
	public static BufferedImage monsterImage = FileIO.loadImage("/Textures/Monster.png");
	public static BufferedImage inventorySlotImage = FileIO.loadImage("/Textures/InventorySlot.png");
	static BufferedImage[][] tilesRankedByElevation = new BufferedImage[50][14];
	public static ArrayList<Level> levels = new ArrayList<Level>();
	public static MenuButton button;
	public static Menu menu = new Menu();
	public static boolean atTitleScreen = true;
	public static boolean inInventory = false;
	public static boolean paused = false;
	public static int currentLevel = 0;
	public static Player player = new Player(200,200);
	public static boolean showMap = false;
	public static boolean godmode = false;
	public static boolean loading = false;

	public static boolean inBattle = false;
	public static Battle bat = new Battle();
	public static ArrayList<String> loadingMessages = new ArrayList<String>();
	public static MessageBox dialog = new MessageBox();
	static Random random;
	static JFrame jframe;
	public GamePanel(JFrame frame){
		jframe = frame;
		random = new Random();
		player.playerCombatant = new PlayerCombatant(player.race.BEAR, player.cls.TANK);
		player.playerCombatant.updateStats();
	}
	public static int randomNumber(int min, int max){
		if(min>max){
			int temp = min;
			min = max;
			max = temp;
		}
		if(max==min){
			return max;
		}
		int randNum = random.nextInt((max-min)+1) + min;
		return randNum;

	}
	public static void createLevel(){
		atTitleScreen = false;
		menu.currentMenu = menu.pauseMain;
		loading = true;
		drawLoadingMessage("Generating overworld...",true);
		//button = new MenuButton(60,40,"",ApplicationUI.windowWidth - 60 - 30, 30);
		Level testLevel = new Level("Test");
		levels.add(testLevel);
		//create the map
		drawLoadingMessage("Generating the dungeon...",true);
		currentLevel++;
		Level dungeon = new Level("Dungeon");
		levels.add(dungeon);

		//loop through all levels
		for(int i = 0; i<levels.size();i++){
			for(int j = 0;  j<levels.get(i).houseLevels.size();j++){
				currentLevel++;
				levels.add(levels.get(i).houseLevels.get(j));
				
			}
		}
//		//forest
//		currentLevel++;
//		Level forest = new Level("Forest");
//		levels.add(forest);
//		System.out.println("Aligning Tiles...");
//		forest.updateTileMapArt();
//		System.out.println("Creating map of the forest...");
//		forest.map = new LevelMap(forest.tileMap);
		currentLevel=0;
		System.out.println("Finished!");
		loading = false;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw((Graphics2D)g);
	}
	public static void drawLoadingMessage(String msg, boolean clearLoadingMessages){
		loading = true;
		if(clearLoadingMessages){
			loadingMessages.clear();
		}
		loadingMessages.add(msg);
		jframe.paint(jframe.getGraphics());		
	}
	public static void setCurrentLevel(Level lvl){
		for(int i = 0; i<levels.size();i++){
			if(levels.get(i).name.equals(lvl.name)){
				System.out.println("Set level!");
				currentLevel = i;
				return;
			}
		}
		System.out.println("failed to set level, lvl name was: "+lvl.name);
	}
	public void Draw(Graphics2D g){
		
		if (atTitleScreen&&!loading){
			g.drawImage(titleScreen,0,0,ApplicationUI.windowWidth,ApplicationUI.windowHeight,null);
			menu.drawMenu(g, menu.title);
		}
		else if(loading){
			g.setColor(new Color(0,0,0));
			g.drawRect(0, 0, ApplicationUI.windowWidth, ApplicationUI.windowHeight);
			Font font = new Font("Iwona Heavy",Font.BOLD,18);
			g.setFont(font);
			g.setColor(Color.WHITE);
			for(int i = 0; i<loadingMessages.size();i++){
				g.drawString(loadingMessages.get(i), 5, ((ApplicationUI.windowHeight/2)-(i*10))+(i*30));
			}
		}
		else{
			levels.get(currentLevel).Draw(g);
			//button.Draw(g);
			if(showMap){
				levels.get(currentLevel).map.Draw(g);
			}
			else{
				int widthInTiles = 80;
				int widthOfMiniMap = 320;
				int tileSize = widthOfMiniMap/widthInTiles;
				LevelMap temp = levels.get(currentLevel).map;
				if(temp!=null){
					int w = widthInTiles*temp.pixelWidthPerTile;//width of the minimap
					int h = widthInTiles*temp.pixelWidthPerTile;//height of the minimap
					int x = (int)((player.xpos/32)*temp.pixelWidthPerTile)-((widthInTiles*temp.pixelWidthPerTile)/2);
					int y = (int)((player.ypos/32)*temp.pixelWidthPerTile)-((widthInTiles*temp.pixelWidthPerTile)/2);

					int drawX=0;
					int drawY=0;
					if(x<0){			
						w = w+x;
						drawX = widthOfMiniMap-(w/(temp.pixelWidthPerTile/tileSize));
						x=0;
					}
					if(y<0){
						h = h+y;
						drawY = widthOfMiniMap-(h/(temp.pixelWidthPerTile/tileSize));
						y=0;
					}
					if(x+w>levels.get(currentLevel).width*temp.pixelWidthPerTile){
						w = (levels.get(currentLevel).width*temp.pixelWidthPerTile)-x;
					}
					if(y+h>levels.get(currentLevel).height*temp.pixelWidthPerTile){
						h = (levels.get(currentLevel).width*temp.pixelWidthPerTile)-y;
					}
					g.setColor(Color.yellow);
					g.fillRect(0, 0, widthOfMiniMap+2, widthOfMiniMap+2);
					g.setColor(Color.black);
					g.fillRect(1, 1, widthOfMiniMap, widthOfMiniMap);
					if(w>0&&h>0&&w<temp.mapImage.getWidth()&&h<temp.mapImage.getHeight()){
						BufferedImage tempImg = temp.mapImage.getSubimage(x, y, w, h);
						g.drawImage(tempImg,1+drawX,1+drawY,w/(temp.pixelWidthPerTile/tileSize),h/(temp.pixelWidthPerTile/tileSize),null);
					}
					g.setColor(Color.red);
					g.drawLine(0, widthOfMiniMap/2, widthOfMiniMap, widthOfMiniMap/2);//horizontal
					g.drawLine(widthOfMiniMap/2, 0, widthOfMiniMap/2, widthOfMiniMap);//vertical
				}
			}
			if(godmode){
				Font font = new Font("Iwona Heavy",Font.BOLD,18);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString("Movement Speed: "+player.movementSpeed,5,ApplicationUI.windowHeight-110);
				g.drawString("Godmode: "+godmode,5,ApplicationUI.windowHeight-80);
				g.drawString("Seed: "+levels.get(currentLevel).seed,5,ApplicationUI.windowHeight-50);
				g.drawString("WeatherID = " + levels.get(currentLevel).weatherID, ApplicationUI.windowWidth - 180,  ApplicationUI.windowHeight - 50);
			}

			//display for being in battle
			if(inBattle)
			{
				Font font = new Font("Iwona Heavy",Font.PLAIN, 20);
				g.setFont(font);
				g.setColor(Color.WHITE);
//				g.drawString(bat.getPlayer().getName(), 5, ApplicationUI.windowHeight - 170);
//				g.drawString(bat.getEnemies().get(0).getName(), 150, ApplicationUI.windowHeight - 170);
				PlayerCombatant plr = bat.getPlayer();
				Enemy e = bat.getEnemies().get(0);
				g.drawString(plr.getName() + " HP: " + plr.getCurrHP() + " | DMG: " + plr.getPDmg()
						+ "VS! " 
						+ e.getName() + " HP: " + e.getCurrHP() + " | DMG: " + e.getPDmg(),
						5, ApplicationUI.windowHeight - 140);
				
			}

			//			g.setColor(Color.yellow);
			//			g.fillRect(0, 0, (50*32)+2, (14*32)+2);
			//			for(int i = 0; i<50; i++){
			//				for(int j = 0; j<14;j++){
			//					g.drawImage(levels.get(currentLevel).tilesRankedByElevation[i][j],(i*32)+1,(j*32)+1,null);
			//				}
			//			}
		
			if (paused){ 
				menu.drawMenu(g, menu.currentMenu);
			}
			
			if (inInventory){
				player.inventory.drawInventory(g);
				
			}
			dialog.Draw(g);
			
		}
	}
}
