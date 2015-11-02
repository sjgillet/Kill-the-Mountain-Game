package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 496501089018037548L;
	public static BufferedImage[][] tiles = FileIO.loadSpriteSheet("/Textures/overlappedTiles.png", 32, 32);
	public static BufferedImage colorGradients = FileIO.loadImage("/Textures/OverworldGradients.png");
	public static BufferedImage[][] overlayTiles = FileIO.loadSpriteSheet("/Textures/TileOverlays.png", 32, 32);
	public static BufferedImage playerImage = FileIO.loadImage("/Textures/Player.png");
	public static BufferedImage monsterImage = FileIO.loadImage("/Textures/Monster.png");
	public static ArrayList<Level> levels = new ArrayList<Level>();
	public static MenuButton button;
	public static Menu menu = new Menu();
	public static boolean paused = false;
	public static int currentLevel = 0;
	public static Player player = new Player(200,200);
	public static boolean showMap = false;
	public static boolean godmode = false;
	public static boolean loading = false;
	public static ArrayList<String> loadingMessages = new ArrayList<String>();
	static Random random;
	public GamePanel(){
		random = new Random();
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
		loading = true;
		loadingMessages.add("Generating overworld...");
		button = new MenuButton(60,40,"",ApplicationUI.windowWidth - 60 - 30, 30);
		Level testLevel = new Level("Test");
		levels.add(testLevel);
		loadingMessages.clear();
		loadingMessages.add("Aligning tiles...");
		testLevel.updateTileMapArt();
		loadingMessages.clear();
		loadingMessages.add("Creating world map...");
		//create the map
		testLevel.map = new LevelMap(testLevel.tileMap);
		loadingMessages.clear();
		loadingMessages.add("Generating the dungeon...");
		System.out.println("Generating the dungeon...");
		currentLevel++;
		Level dungeon = new Level("Dungeon");
		levels.add(dungeon);
		System.out.println("Aligning Tiles...");
		dungeon.updateTileMapArt();
		System.out.println("Creating map of the dungeon...");
		dungeon.map = new LevelMap(dungeon.tileMap);
		currentLevel--;
		System.out.println("Finished!");
		loading = false;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Draw((Graphics2D)g);
	}
	public static void setCurrentLevel(Level lvl){
		for(int i = 0; i<levels.size();i++){
			if(levels.get(i)==lvl){
				currentLevel = i;
			}
		}
	}
	public void Draw(Graphics2D g){
		if(loading){
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
					if(w>0&&h>0){
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
			
		}
	}
}
