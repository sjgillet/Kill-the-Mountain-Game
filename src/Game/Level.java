package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Level {
	Tile[][] tileMap;
	Tile[][] hardMap;
	Tile[] baseTiles;	//for initalizing maps
	int width=30;
	int height=30;
	String name;
	Random levelGenerationRandom;
	Random random;
	int seed;
	int waterlevel;
	//self explanatory

	int minNumberOfMountains;
	int maxNumberOfMountains;
	int mountainMaxHeight;
	int mountainMinHeight;
	int minNumberOfCanyons;
	int maxNumberOfCanyons;
	int canyonMaxDepth;//the maximum depth of canyons in the level
	int canyonMinDepth;//the minimum depth of canyons in the level

	//ratio of size between one layer and the layer before it
	int mountainMaxSteepness=0;
	int mountainMinSteepness=0;
	int canyonMaxSteepness;
	int canyonMinSteepness;
	LevelMap map;


	boolean drawingLevel = false;

	ArrayList<ArrayList<Tile>> lakes = new ArrayList<ArrayList<Tile>>();
	ArrayList<Monster> monsters = new ArrayList<Monster>();
	ArrayList<Room> rooms = new ArrayList<Room>();
	ArrayList<Hallway> hallways = new ArrayList<Hallway>();
	Point townLocation;

	//weather variables
	double windDirection = -100;//X distance from center of screen
	int weatherID = -1;//-1 none, 0 rain, 1 ash,
	Color fogColor = new Color(20,20,80,0);
	double fogAlpha = 0;

	ArrayList<weatherParticle> weather = new ArrayList<weatherParticle>();
	ArrayList<Level> houseLevels = new ArrayList<Level>();

	//list of all monsters in terms of stats. To be referenced for battles
	public static MonsterList monstersList;


	public Level(String Name){
		GamePanel.loading=true;
		//Pre-determined map generation for specified zones or debugging purposes.
		name = Name;
		tileMap = new Tile[width][height];
		if(name.equals("Test")){
			seed = 138;
			width = 500;
			height = 500;


			minNumberOfMountains = ((width+height)/2)/80;
			maxNumberOfMountains = ((width+height)/2)/80;

			mountainMaxHeight = 15;//((width+height)/2)/25;//max elevation possible for mountains
			mountainMinHeight = 15;//((width+height)/2)/50;//minimum elevatoin possible for mountains
			mountainMinSteepness = 5;
			mountainMaxSteepness = 5;

		}

		else if(name.equals("Forest")){
			seed = 137;
			width = 500;
			height = 500;

		}
		else if(name.equals("Dungeon")){
			seed = 130;
			width = 500;
			height = 500;
		}

		//generateDoors();
		//update all the tiles on the map to show their proper artwork
		//updateTileMapArt();
		else if(name.equals("Overworld"))	//player hub; Links to each of the dungeons' starting locations and serves as safe point.
		{
			seed = -1;
			width = 50;
			height = 50;
		}
		else if(name.equals("Boss Room A"))	//zone where player encounters the first boss
		{
			seed = -2;
			width = 20;
			height = 10;
		}
		else if(name.equals("Boss Room B"))	//zone where player encounters the second boss
		{
			seed = -3;
			width = 20;
			height = 10;
		}
		else if(name.equals("Boss Room C")) //zone where player encounters the third boss
		{
			seed = -4;
			width = 15;
			height = 40;
		}
		else if(name.equals("Safe Zone")) 	//randomly occuring safe zone within dungeons where the player character can rest.
		{
			seed = -5;
			width = 40;
			height = 25;
		}
		else if(name.equals("Noob Hut")){
			seed = 333;
			width = 40;
			height = 30;
		}
		else if(name.equals("Volcano Crater")){
			seed = 100;
			width = 300;
			height = 300;
		}
		random = new Random(seed);
		System.out.println("generating map");


		//load the map
		//if(name.equals("Dungeon")||loadLevel()==false){
		System.out.println(name+" did not exist, generating "+name);
		//generate the map because the level did not have an existing file to represent it
		generateMap();
		//}
		System.out.println("adding vegetation");


		addTrees();

		System.out.println("coloring tiles");
		colorTiles();

		System.out.println("Adding monsters... "+rooms.size());
		addMonsters();



		System.out.println("Generating Monsters...");
		monstersList = new MonsterList();
		System.out.println("Monsters Catologued!");

		//GamePanel.loading=false;
		updateTileMapArt();
		map = new LevelMap(tileMap);
		saveLevel(name);
	}
	/*
	 * Adds colors from various gradients to all tiles which are black and white
	 */
	public void colorTiles(){
		for(int i = 0; i<50;i++){
			for(int j = 0; j<14;j++){
				//initialize the tile
				GamePanel.tilesRankedByElevation[i][j] = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
				//color the lower elevation part of the tile
				BufferedImage lowerElevationSection = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
				BufferedImage upperElevationSection = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
				BufferedImage rocks = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
				BufferedImage cracks = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
				Graphics g = lowerElevationSection.getGraphics();

				//lower elevation part of the tile
				g.drawImage(GamePanel.tiles[9][j],0,0,null);
				int index = i-1;
				if(index<0){
					index=0;
				}
				Color clr = new Color(GamePanel.colorGradients.getRGB(index, 2),true);
				lowerElevationSection = FileIO.colorImage(lowerElevationSection, clr);

				//upper elevation part of the tile
				g = upperElevationSection.getGraphics();
				g.drawImage(GamePanel.tiles[10][j],0,0,null);
				clr = new Color(GamePanel.colorGradients.getRGB(i, 2),true);
				upperElevationSection = FileIO.colorImage(upperElevationSection, clr);

				//rocks
				g = rocks.getGraphics();
				g.drawImage(GamePanel.tiles[11][j],0,0,null);
				clr = new Color(GamePanel.colorGradients.getRGB(i, 0),true);
				rocks = FileIO.colorImage(rocks, clr);

				//cracks
				g = cracks.getGraphics();
				g.drawImage(GamePanel.tiles[12][j],0,0,null);
				clr = new Color(GamePanel.colorGradients.getRGB(i, 1),true);
				cracks = FileIO.colorImage(cracks, clr);

				//actual tile image
				g = GamePanel.tilesRankedByElevation[i][j].getGraphics();
				g.drawImage(lowerElevationSection,0,0,null);
				g.drawImage(upperElevationSection,0,0,null);
				g.drawImage(rocks,0,0,null);
				g.drawImage(cracks,0,0,null);
				g.dispose();
			}
		}
	}
	/*
	 * calls the updateArt method on every tile in the tilemap
	 * 
	 * @return none
	 */
	public void updateTileMapArt(){
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				if(tileMap[x][y]!=null){
					tileMap[x][y].updateArt(waterlevel);
				}
			}
		}

	}
	/*
	 * Adds trees to the level
	 * 
	 */
	public void addTrees(){
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height;y++){
				if(name.equalsIgnoreCase("Test")){
					//above sea level and grass with no overlays already on it
					if(tileMap[x][y].elevation>=waterlevel&&tileMap[x][y].tileID==0&&tileMap[x][y].vegetationID==-1&&tileMap[x][y].elevation<waterlevel+20){
						//%chance that a grass tile has something on it
						if(randomNumber(1,10)==1){
							int roll = randomNumber(1,100);
							if(roll<=80){
								tileMap[x][y].vegetationID=0;
								tileMap[x][y].vegetationColor=new Color(21+randomNumber(-10,10),111+randomNumber(-10,10),48+randomNumber(-10,10));
							}
							else{
								tileMap[x][y].vegetationID=1;
								tileMap[x][y].vegetationColor=new Color(GamePanel.colorGradients.getRGB(randomNumber(0,49), 3),true);
							}
						}
					}
				}
				else if(name.equalsIgnoreCase("Dungeon")){
					if(tileMap[x][y].tileID==8){//dungeon floor
						if(randomNumber(1,1000)<=3){//chance to have anything at all
							int roll = randomNumber(1,100);
							if(roll<=20){//puddle
								tileMap[x][y].vegetationID=2;
							}
							else{
								tileMap[x][y].vegetationID=5;
							}


						}
					}

				}
			}
		}
	}
	/*
	 * Adds monsters to the level
	 */
	public void addMonsters(){
		if(name.equalsIgnoreCase("Dungeon")){
			for(int i = 0;i<rooms.size();i++){
				Room currentRoom = rooms.get(i);
				int randX = randomNumber(currentRoom.area.x+1,currentRoom.area.x+currentRoom.area.width-2);
				int randY = randomNumber(currentRoom.area.y+1,currentRoom.area.y+currentRoom.area.height-2);
				monsters.add(new Monster(randX*32, randY*32,"monster1"));
			}
		}
	}

	/*
	 * Fills in the tileMap array
	 */
	public void generateMap(){
		tileMap = new Tile[width][height];
		levelGenerationRandom = new Random(seed);
		//initialize the tile map
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				if(name.equals("Dungeon")){
					tileMap[x][y]=new Tile(x,y,7,10,tileMap);//everything is abyss
				}
				else if(name.equals("Noob Hut")){
					tileMap[x][y]=new Tile(x,y,12,10,tileMap);
				}
				else if(name.equals("Volcano Crater")){
					tileMap[x][y]=new Tile(x,y,0,60,tileMap);
				}
				else{
					tileMap[x][y]=new Tile(x,y,0,1,tileMap);//everything is grass
				}

			}
		}		

		if(seed < 0)
		{
			buildRoom(-1 - seed);	//translate seed to a key for room ID's, build them
			tileMap = hardMap;
		}
		if(name.equals("Dungeon")){

			//ArrayList<Room> rooms = new ArrayList<Room>();
			//ArrayList<Hallway> hallways = new ArrayList<Hallway>();

			//generate the first room of the dungeon at a random position
			Room room = new Room((width/2)+randomNumberForLevelGeneration(-40,40),(height/2)+randomNumberForLevelGeneration(-30,30),40,30);
			rooms.add(room);
			int numberOfRooms = 150;
			int maxTriesPerRoom = 5000;
			int tries = 0;
			Room newRoom = null;
			Hallway hallway = null;
			//for the desired number of additional rooms
			while(rooms.size()<numberOfRooms&&tries<maxTriesPerRoom){
				tries++;
				//pick a random room from the list of rooms
				Room randomRoom = rooms.get(randomNumberForLevelGeneration(0,rooms.size()-1));
				//dimensions of the new room
				int w = randomNumberForLevelGeneration(10,25);
				int h = randomNumberForLevelGeneration(8,15);
				int hallwayWidth = randomNumberForLevelGeneration(4,5);
				int hallwayLength = randomNumberForLevelGeneration(2,20);

				newRoom=null;
				hallway=null;
				//pick a random edge of the room
				int whatSide = randomNumberForLevelGeneration(1,4);
				//top
				if(whatSide==1){
					//pick a random position on the room that's being added to for the hallway to be added at
					hallway = new Hallway(randomNumberForLevelGeneration(randomRoom.area.x,randomRoom.area.x+randomRoom.area.width-hallwayWidth),randomRoom.area.y-hallwayLength,hallwayWidth,hallwayLength);
					//add a random rectangle at the end of the hallway
					newRoom = new Room(randomNumberForLevelGeneration(hallway.area.x+hallway.area.width-w,hallway.area.x),hallway.area.y-h,w,h);
				}
				if(whatSide==2){//bottom
					//pick a random position on the room that's being added to for the hallway to be added at
					hallway = new Hallway(randomNumberForLevelGeneration(randomRoom.area.x,randomRoom.area.x+randomRoom.area.width-hallwayWidth),randomRoom.area.y+randomRoom.area.height,hallwayWidth,hallwayLength);
					//add a random rectangle at the end of the hallway
					newRoom = new Room(randomNumberForLevelGeneration(hallway.area.x+hallway.area.width-w,hallway.area.x),hallway.area.y+hallwayLength,w,h);
				}
				if(whatSide==3){//left
					hallway=new Hallway(randomRoom.area.x-hallwayLength,randomNumberForLevelGeneration(randomRoom.area.y,randomRoom.area.y+randomRoom.area.height-hallwayWidth),hallwayLength,hallwayWidth);
					newRoom=new Room(hallway.area.x-w,randomNumberForLevelGeneration(hallway.area.y+hallwayWidth-h,hallway.area.y),w,h);
					//randomNumber(hallway.y-h+hallway.width,hallway.y)
				}
				if(whatSide==3){//right
					hallway=new Hallway(randomRoom.area.x+randomRoom.area.width,randomNumberForLevelGeneration(randomRoom.area.y,randomRoom.area.y+randomRoom.area.height-hallwayWidth),hallwayLength,hallwayWidth);
					newRoom=new Room(hallway.area.x+hallwayLength,randomNumberForLevelGeneration(hallway.area.y+hallwayWidth-h,hallway.area.y),w,h);
					//randomNumber(hallway.y-h+hallway.width,hallway.y)
				}
				if(hallway!=null&&newRoom!=null){
					boolean collided = false;
					//check if room or hallway collides with an existing room
					for(int i = 0; i<rooms.size();i++){
						if(rooms.get(i).area.intersects(new Rectangle(newRoom.area.x-2,newRoom.area.y-2,newRoom.area.width+4,newRoom.area.height+4))){
							collided = true;
						}
						if(rooms.get(i).area.intersects(new Rectangle(hallway.area.x,hallway.area.y,hallway.area.width,hallway.area.height))){
							collided = true;
						}
					}//check if room collides with an existing hallway
					for(int i = 0; i<hallways.size();i++){
						if(hallways.get(i).area.intersects(new Rectangle(newRoom.area.x-2,newRoom.area.y-2,newRoom.area.width+4,newRoom.area.height+4))){
							collided = true;
						}
					}
					if(!collided){
						if(newRoom.area.x>=0&&newRoom.area.y>=0&&newRoom.area.x+newRoom.area.width<width&&newRoom.area.y+newRoom.area.height<height){
							if(hallway.area.x>=0&&hallway.area.y>=0&&hallway.area.x+hallway.area.width<width&&hallway.area.y+hallway.area.height<height){
								rooms.add(newRoom);
								randomRoom.hallways.add(hallway);
								System.out.println("Room number "+rooms.size()+" has "+randomRoom.hallways.size()+" hallways.");
								hallway.start=randomRoom;
								hallway.end=newRoom;
								newRoom.hallways.add(hallway);
								hallways.add(hallway);
								tries=0;
							}
						}
					}
				} 

			}



			System.out.println("adding "+rooms.size()+" rooms and "+hallways.size()+" hallways...");
			//add room to the tilemap
			for(int i = 0; i<rooms.size();i++){
				Room temp = rooms.get(i);
				for(int x = temp.area.x;x<temp.area.x+temp.area.width;x++){
					for(int y = temp.area.y;y<temp.area.y+temp.area.height;y++){
						tileMap[x][y]=new Tile(x,y,8,9,tileMap);
					}
				}
				for(int j = 0; j<rooms.get(i).hallways.size();j++){
					Rectangle temp1 = rooms.get(i).hallways.get(j).area;
					for(int x = temp1.x;x<temp1.x+temp1.width;x++){
						for(int y = temp1.y;y<temp1.y+temp1.height;y++){
							tileMap[x][y].flagged=true;
						}
					}
				}

			}
			//add hallway to the tilemap
			for(int i = 0; i<hallways.size();i++){


				Hallway temp = hallways.get(i);
				for(int x = temp.area.x;x<temp.area.x+temp.area.width;x++){
					for(int y = temp.area.y;y<temp.area.y+temp.area.height;y++){
						tileMap[x][y]=new Tile(x,y,8,9,tileMap);
					}
				}
				if(temp.area.width<temp.area.height&&temp.area.height>5){//hallway is vertical
					//chance to add a rotten door
					if(randomNumberForLevelGeneration(1,100)<=50){
						//pick a random y
						int randY = randomNumberForLevelGeneration(temp.area.y,temp.area.y+temp.area.height);
						//set all the tiles to be brick at randY inside the hallway
						for(int x = temp.area.x;x<temp.area.x+temp.area.width;x++){
							tileMap[x][randY]=new Tile(x,randY,9,9,tileMap);
						}
						//set one of the tiles to be a floor tile with a door at randY
						int randX = randomNumberForLevelGeneration(temp.area.x+1,temp.area.x+temp.area.width-2);
						tileMap[randX][randY]=new Tile(randX,randY,8,9,tileMap);
						tileMap[randX][randY].vegetationID=4;
						//tileMap[randX][randY].flagged=true;
					}
				}
			}
			//find the longest path from the start
			ArrayList<Room> longestPath = getLongestPath(rooms);
			//loop through this path
			for(int i = 0; i<longestPath.size();i++){
				Room temp = longestPath.get(i);
				//add blood to the room
				int bloodPercentage = ((temp.area.width*temp.area.height)/3)/(longestPath.size()-i);

				for(int j = 0; j<bloodPercentage;j++){
					int randX = randomNumberForLevelGeneration(temp.area.x,temp.area.x+temp.area.width);
					int randY = randomNumberForLevelGeneration(temp.area.y,temp.area.y+temp.area.height);
					int attempts = 0;
					while(tileMap[randX][randY].tileID!=8&&tileMap[randX][randY].vegetationID!=-1&&attempts<100){
						attempts++;
						randX = randomNumberForLevelGeneration(temp.area.x,temp.area.x+temp.area.width);
						randY = randomNumberForLevelGeneration(temp.area.y,temp.area.y+temp.area.height);
					}
					tileMap[randX][randY].vegetationID=5;
				}
				//flagRectangle(temp.area,new Color(0,0,255,50));
			}

			//initialize items in rooms further than 8 rooms away from the start and at dead ends
			for (int i = 0; i<rooms.size(); i++) {

				if (rooms.get(i).shortestPathToThisFromStart.size() >= 10 && rooms.get(i).hallways.size()==1){

					//randomly place an item in the room

					int x = 0;
					int y = 0;

					x = rooms.get(i).area.x + (rooms.get(i).area.width/2);
					y = rooms.get(i).area.y + (rooms.get(i).area.height/2);
					String type = "";
					int roll = randomNumber(1,100);
					if(roll>80){//20%
						type = "weapon";
					}
					else if(roll>60&&roll<=80){//20%
						type = "armor";
					}
					else{//60%
						type = "consumable";
					}
					Item temp = GamePanel.player.inventory.randomItem(type);
					if(temp!=null){
						temp.xPosition = x*32;
						temp.yPosition = y*32;

						tileMap[x][y].itemsOnThisTile.add(temp);
					}

				}

			}


			setElevationEdges(9,9);

		}
		else if(name.equals("Canyon")){
			generatePlateauOrCanyon(9,1000,false,getTilesAtElevation(9));
			setElevationEdges(9,3);
			removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(9);
		}
		else if(name.equals("Noob Hut")){
			for(int x = 0;x<width;x++){
				for(int y = 0; y<height;y++){
					if(x==0||y==0||x==width-1||y==height-1){
						tileMap[x][y]=new Tile(x,y,9,11,tileMap);
					}
				}
			}
		}
		else if(name.equals("Volcano Crater")){
			int startElevation = 60;
			int endElevation = 50;
			for(int i = startElevation; i>endElevation;i--){
				ArrayList<Tile> volcanoTiles = getTilesAtElevation(i);

				int sizeOfThisLayer = (int)(double)((width*height)*(Math.pow(.99, (startElevation-i))));

				GamePanel.drawLoadingMessage("Generating canyons for the mountain... Progress: "+(int)(double)(((double)(startElevation-i)/(double)(startElevation-endElevation))*100.0)+"%",true);

				generatePlateauOrCanyon(i,sizeOfThisLayer,false,volcanoTiles);
				setElevationEdges(i,3);
				//remove all weird outcrops that we don't have textures for
				removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(i);

			}

		}
		else{
			int numberOfMountains = randomNumberForLevelGeneration(minNumberOfMountains,maxNumberOfMountains);
			//create mountains

			int mountainHeight = randomNumberForLevelGeneration(mountainMinHeight,mountainMaxHeight);
			waterlevel = 6;
			double steepness = .6;//randomNumber(mountainMinSteepness,mountainMaxSteepness);
			int mountainBaseSize = (int)(double)(((width+height)/2)*2*mountainHeight/steepness);
			System.out.println("Generating the mountain...");
			generateVolcano();
			System.out.println("Finished Generating the moutain!");
			double percentPerPlateau = ((double)(1.0)/(double)(numberOfMountains*mountainHeight));
			double total = 0;
			for(int f = 1; f<=mountainHeight;f++){
				if(f<waterlevel){
					steepness = .9;
				}
				else{
					steepness = .7;
				}
				ArrayList<Tile> tilesAtDesiredElevation = getTilesAtElevation(f);
				for(int k = 1; k<=numberOfMountains;k++){
					total++;
					//create a plateau at the elevation determined by f
					//generatePlateauOrCanyon(f,mountainBaseSize/(f*steepness),true,tilesAtDesiredElevation);
					generatePlateauOrCanyon(f,(int)((double)(mountainBaseSize*Math.pow(steepness, f))),true,tilesAtDesiredElevation);
					GamePanel.drawLoadingMessage("Adding plateau's to overworld: "+(int)(total/(double)(mountainHeight*numberOfMountains)*100)+"%", true);
				}			
				//System.out.println("creating layer with base size: "+(mountainBaseSize/Math.pow(steepness, f))+", base of mountain is size: "+mountainBaseSize+", f = "+f);
				//create the plateau edges
				setElevationEdges(f+1,3);
				//remove all weird outcrops that we don't have textures for
				removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(f+1);
				//System.out.println("Adding Plateau's: "+(int)(f*numberOfMountains*percentPerPlateau*100)+"%");
			}
			//find the elevation with the most grass tiles
			ArrayList<Tile> biggestArea = new ArrayList<Tile>();
			for(int i = waterlevel; i<50;i++){
				ArrayList<Tile> tilesAtThisElevation = getTilesAtElevation(i);
				if(biggestArea.size()==0||tilesAtThisElevation.size()>biggestArea.size()){
					biggestArea = new ArrayList<Tile>(tilesAtThisElevation);
				}
			}
			generateTown(5,biggestArea);

		}

	}
	public ArrayList<Room> getLongestPath(ArrayList<Room> rooms){
		ArrayList<Room> longestPath = new ArrayList<Room>();

		//get all of the paths to all of the rooms
		for(int i = 0; i<rooms.size();i++){
			ArrayList<Room> path = new ArrayList<Room>();
			getShortestPathToRoom(rooms.get(0),path, rooms.get(i));
		}

		//find which room has the longest path
		for(int i = 0; i<rooms.size();i++){
			//check if the current room has a longer "shortest path" than the current longest path
			if(rooms.get(i).shortestPathToThisFromStart!=null){
				if(rooms.get(i).shortestPathToThisFromStart.size()>longestPath.size()||longestPath.size()==0){
					longestPath = rooms.get(i).shortestPathToThisFromStart;
				}
			}
		}

		return longestPath;
	}
	public void flagRectangle(Rectangle rect, Color clr){
		for(int x = rect.x;x<rect.x+rect.width;x++){
			for(int y = rect.y;y<rect.y+rect.height;y++){
				tileMap[x][y].flagColor=clr;
				tileMap[x][y].flagged=true;
			}
		}
	}
	public void getShortestPathToRoom(Room current, ArrayList<Room> path, Room destination){
		path.add(current);
		if(current.equals(destination)){
			if(destination.shortestPathToThisFromStart==null){
				destination.shortestPathToThisFromStart= new ArrayList<Room>(path);
			}
			else{
				if(destination.shortestPathToThisFromStart.size()>path.size()){
					destination.shortestPathToThisFromStart = new ArrayList<Room>(path);
				}
			}
			path.remove(current);
			return;
		}
		//call this on all of the unvisited rooms connected to this one
		for(int i = 0; i<current.hallways.size();i++){
			Room room = current.hallways.get(i).end;

			if(!path.contains(room)){//current room is not already in the path
				getShortestPathToRoom(room,path,destination);
			}

		}
		path.remove(current);

	}
	/*
	 * Generates a volcano
	 */
	public void generateVolcano(){

		int baseSize = (width*height)/2;
		int volcanoHeight = 49;
		int actualVolcanoHeight = 0;
		for(int i = 1; i<=volcanoHeight;i++){
			ArrayList<Tile> volcanoTiles = getTilesAtElevation(i);
			if(volcanoTiles.size()>300&&i<=48){
				int sizeOfThisLayer = (int)(double)(baseSize*(Math.pow(.9, i)));

				GamePanel.drawLoadingMessage("Generating Plateau's for the mountain... Progress: "+(int)(double)(((double)i/(double)volcanoHeight)*100.0)+"%",true);

				generatePlateauOrCanyon(i,sizeOfThisLayer,true,volcanoTiles);
				setElevationEdges(i+1,3);
				//remove all weird outcrops that we don't have textures for
				removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(i+1);
			}
			else{
				actualVolcanoHeight = i;
				System.out.println("ACTUAL VOLCANO HEIGHT: "+actualVolcanoHeight);
				break;
			}
		}
		//do stuff with the top of the volcano
		ArrayList <Tile>craterTiles = getTilesAtElevation(actualVolcanoHeight);
		ArrayList <Tile>usableCraterTiles = new ArrayList<Tile>();
		System.out.println("size of the top of the volcano: "+craterTiles.size());
		//remove all cliff tiles from this
		for(int i = 0; i<craterTiles.size();i++){
			//(int xpos, int ypos, int id, boolean careAboutElevation, int desiredElevation){
			ArrayList<Tile> adjacentTiles = getTilesAdjacentToPosition(new Point(craterTiles.get(i).xpos/32,craterTiles.get(i).ypos/32),true);

			if(craterTiles.get(i).tileID!=3&&checkForTileWithinDistance(craterTiles.get(i),3,1)==false){
				//craterTiles.get(i).flagged=true;
				//usableCraterTiles.add(craterTiles.get(i));
			}
		}
		System.out.println("Number of tiles a crater can be formed on: "+usableCraterTiles.size());
		//generate a plateau out of just the tiles in the crater
		generatePlateauOrCanyon(actualVolcanoHeight,(int)(double)(usableCraterTiles.size()*.8),false,usableCraterTiles);
		setElevationEdges(actualVolcanoHeight,3);
		removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(actualVolcanoHeight);
	}
	public boolean checkForTileWithinDistance(Tile start, int whatToCheckFor, int distance){
		if(distance>0){
			start.flagColor=new Color(255,0,0,200);
			//start.flagged=true;
			if(start.tileID==whatToCheckFor){
				return true;
			}
			ArrayList<Tile> adjacentTiles = getTilesAdjacentToPosition(new Point(start.xpos/32,start.ypos/32),true);
			for(int i = 0; i<adjacentTiles.size();i++){
				checkForTileWithinDistance(adjacentTiles.get(i),whatToCheckFor,distance-1);
			}
		}
		return false;

	}
	/*
	 * used to generate an area on the map which has a higher or lower elevation that the elevation it is created on
	 * 
	 * @param buildElevation - the elevation to build this terrain feature on
	 * @param size - the number of tiles this feature should be made of (not always correct because
	 * the method skips some tiles if it is necessary to prevent an infinite loop where it can't find
	 * any valid places to add to at the desired elevation
	 * @param isPlateau - true if this feature should be raising elevation compared to build elevation,
	 * false if it should be lowering elevation compated to the build elevation.
	 */
	public void generatePlateauOrCanyon(int buildElevation, int size, boolean isPlateau, ArrayList<Tile> tilesAtThisElevation){
		Color randomColor = new Color(randomNumber(0,255),randomNumber(0,255),randomNumber(0,255));
		int elevationChange = 0;
		int count = 0;
		int newTileID = 0;
		if(isPlateau){
			elevationChange = 1;
		}
		else{
			elevationChange = -1;
		}
		ArrayList<Tile> tilesAdded = new ArrayList<Tile>();

		if(tilesAtThisElevation.size()>0){
			Tile randomTile = tilesAtThisElevation.get(randomNumberForLevelGeneration(0,tilesAtThisElevation.size()-1));

			while(randomTile.tileID!=0&&tilesAtThisElevation.size()>0){//while random tile is not grass
				//remove the random tile
				tilesAtThisElevation.remove(randomTile);
				//if there are still tiles at this elevation that haven't been checked
				if(tilesAtThisElevation.size()>0){
					//set randomTile to equal one of those tiles
					randomTile = tilesAtThisElevation.get(randomNumberForLevelGeneration(0,tilesAtThisElevation.size()-1));

				}
			}

			if(tilesAtThisElevation.size()>0){
				//set the first tile at this elevation
				tileMap[randomTile.xpos/32][randomTile.ypos/32] = new Tile(randomTile.xpos/32,randomTile.ypos/32,newTileID,buildElevation+elevationChange,tileMap);
				tilesAdded.add(randomTile);
				int tries = 0;
				//while we've added less tiles than desired, there is a tile to -
				//build off of, and there are untouched tiles at this elevation
				while(count<size&&tilesAdded.size()>0&&tilesAtThisElevation.size()>0){//for the desired amount of tiles

					//pick a random tile which has already been created for this terrain feature
					Tile randTile = tilesAdded.get(randomNumberForLevelGeneration(0,tilesAdded.size()-1));

					//get all the adjacent grass tiles that are at the same elevation as buildElevation
					ArrayList<Tile> adjacentTiles = tilesAdjacentToPosition(randTile.xpos/32,randTile.ypos/32,0,true,buildElevation);
					boolean addedATile = false;
					//make sure there are adjacent tiles that can be modified
					if(adjacentTiles.size()>0){

						//pick a random one of these tiles
						randTile = adjacentTiles.get(randomNumberForLevelGeneration(0,adjacentTiles.size()-1));					

						//make sure this tile is a grass tile and is at the desired elevation
						if(randTile.tileID==0&&randTile.elevation==buildElevation){

							//set the tile at this position to be a cliff tile
							tileMap[randTile.xpos/32][randTile.ypos/32]=new Tile(randTile.xpos/32,randTile.ypos/32,newTileID,buildElevation+elevationChange,tileMap);
							tileMap[randTile.xpos/32][randTile.ypos/32].oldElevation=buildElevation;
							tilesAdded.add(tileMap[randTile.xpos/32][randTile.ypos/32]);
							tilesAtThisElevation.remove(randTile);
							count++;
							addedATile = true;
							tries = 0;

						}
						else{
							System.out.println("failed to pick a grass tile");
						}
						if(randomNumberForLevelGeneration(1,10)==1){
							count++;
						}

					}
					else{
						tilesAdded.remove(randTile);
						if(tilesAdded.size()==0){
							int index = randomNumberForLevelGeneration(0,tilesAtThisElevation.size()-1);
							randomTile = tilesAtThisElevation.get(index);
							while(randomTile.tileID!=0&&tilesAtThisElevation.size()>0){//while random tile is not grass
								//System.out.println("removed a tile with ID: "+randomTile.tileID);
								tilesAtThisElevation.remove(index);
								if(tilesAtThisElevation.size()>0){
									index = randomNumberForLevelGeneration(0,tilesAtThisElevation.size()-1);
									randomTile = tilesAtThisElevation.get(index);
								}
							}
							if(tilesAtThisElevation.size()>0){
								tileMap[randomTile.xpos/32][randomTile.ypos/32] = new Tile(randomTile.xpos/32,randomTile.ypos/32,0,buildElevation+elevationChange,tileMap);
								//System.out.println("1");
								tilesAdded.add(randomTile);
								tilesAtThisElevation.remove(index);
							}

						}

					}

					if(addedATile==false){
						tries++;
					}
				}

			}
		}

	}
	public void generateTown(int numberOfHouses, ArrayList<Tile> tilesWeCanBuildOn){
		for(int i = 0; i<numberOfHouses;i++){
			//find a valid place for this house
			ArrayList<Tile> possibleLocations = tilesWeCanBuildOn;
			if(possibleLocations.size()>0){
				//check every possible location
				Tile randomTile = possibleLocations.get(randomNumberForLevelGeneration(0,possibleLocations.size()-1));//pick a random tile from the possible locations
				boolean madeAHouse = false;
				while(!madeAHouse){
					madeAHouse = generateHouse("Noob Hut", randomTile.xpos/32,randomTile.ypos/32,tilesWeCanBuildOn.get(0).elevation);
					possibleLocations.remove(randomTile);
					if(possibleLocations.size()>0){
						randomTile = possibleLocations.get(randomNumberForLevelGeneration(0,possibleLocations.size()-1));//pick a random tile from the possible locations
					}
					else{
						return;
					}
				}
			}
		}
	}
	public boolean generateHouse(String id, int xpos, int ypos, int elev){
		int w=0;
		int h=0;
		int roofHeight=0;
		int floors=0;
		if(id.equalsIgnoreCase("Noob Hut")){

			roofHeight = randomNumberForLevelGeneration(1,4)*3;
			floors = randomNumberForLevelGeneration(1,1);
			h = (2*floors)+roofHeight;
			w = randomNumberForLevelGeneration(h,h*2);
		}
		int startX = xpos;
		int startY = ypos;
		int endX = xpos+w;
		int endY = ypos+h;

		//check if there is room for this
		for(int x = startX; x<=endX;x++){
			for(int y = startY; y<=endY;y++){
				if(x<0||y<0||x>=width-1||y>=height-1){
					return false;
				}
				if(tileMap[x][y].elevation!=elev||tileMap[x][y].tileID!=0){
					return false;
				}
			}
		}
		//create top part of the roof
		for(int x = startX; x<=endX;x++){
			for(int y = startY; y<=startY+(roofHeight/3);y++){				
				tileMap[x][y]= new Tile(x,y,4,elev+1,tileMap);
			}
		}
		//create the bottom part of the roof
		for(int x = startX; x<=endX;x++){
			for(int y = startY+(roofHeight/3);y<startY+roofHeight;y++){
				tileMap[x][y]= new Tile(x,y,5,elev+1,tileMap);
				tileMap[x][y].flagged=true;
			}
		}
		//create the walls
		for(int x = startX; x<=endX;x++){
			for(int y = startY+roofHeight;y<endY;y++){
				tileMap[x][y]=new Tile(x,y,6,elev+1,tileMap);
			}
		}

		//add a door
		int doorX = randomNumberForLevelGeneration(startX+1,endX-3);
		Level theInsideOfThisHouse = new Level(id);
		theInsideOfThisHouse.updateTileMapArt();
		theInsideOfThisHouse.map=new LevelMap(theInsideOfThisHouse.tileMap);
		Door door = new Door(doorX, startY+roofHeight, theInsideOfThisHouse, 1, 5, 5);
		houseLevels.add(theInsideOfThisHouse);
		for(int x = doorX; x<doorX+3; x++){
			for(int y = startY+roofHeight;y<endY;y++){
				tileMap[x][y]= new Tile(x,y,10,elev+1,tileMap);
				tileMap[x][y].door = door;
			}
		}
		//add some windows
		for(int i = 0; i<w/4;i++){
			int randX = randomNumberForLevelGeneration(startX+1,endX-1);
			while(tileMap[randX][startY+roofHeight].tileID!=6){
				randX = randomNumberForLevelGeneration(startX+1,endX-1);
			}
			tileMap[randX][startY+roofHeight]=new Tile(randX,startY+roofHeight,11,elev+1,tileMap);
			tileMap[randX][startY+roofHeight+1]=new Tile(randX,startY+roofHeight+1,11,elev+1,tileMap);
		}

		return true;
	}
	/*
	 * Generates some doors in the level so that you can get from one level to the next.
	 * 
	 * @return none.
	 */
	public void generateDoors(){
		if(name.equals("Test")){
			ArrayList<Point> acceptableTilesToOverwrite = new ArrayList<Point>();
			acceptableTilesToOverwrite.add(new Point(6,0));
			int w = 3;
			int h = 3;
			ArrayList<Tile[][]> possibleAreas = findAreasFullOfTileTypes(w,h,acceptableTilesToOverwrite);
			Tile[][] randomlyPickedSpot = possibleAreas.get(randomNumberForLevelGeneration(0,possibleAreas.size()));
		}
	}
	/*
	 * Finds all the areas in the level which consist completely of the specified tile types.
	 * 
	 * @param w - the width of the area to search for.
	 * @param h - the height of the area to search for.
	 * @param tileIDs - An arraylist of all the tiles that are acceptable in the area desired.
	 */
	public ArrayList<Tile[][]> findAreasFullOfTileTypes(int w, int h, ArrayList<Point> tileIDs){
		ArrayList<Tile[][]> areasFound = new ArrayList<Tile[][]>();
		//loop through all the tiles in the level
		for(int x = 0; x<width-w; x++){
			for(int y = 0; y<height-h;y++){
				//check an area where this x,y is the top left corner and width and height are what was passed
				boolean isCorrect = true;
				Tile[][] subArea = new Tile[w][h];
				for(int i = x; i<x+w;i++){
					for(int j = y; j<y+h; j++){
						boolean oneWasRight = false;
						//loop through all the desired tile types
						for(int k = 0; k<tileIDs.size();k++){
							if((tileMap[w][j].artX==tileIDs.get(k).x&&tileMap[w][j].artY==tileIDs.get(k).y)){
								oneWasRight = true;
							}
						}
						subArea[i-w][j-h] = tileMap[w][j];
						if(!oneWasRight){
							isCorrect=false;
						}
					}
				}
				if(isCorrect){
					areasFound.add(subArea);
				}
			}
		}
		return areasFound;
	}
	/*
	 * makes cliff tiles on the edges of a change in elevation
	 * 
	 * @param elev - the elevation to act upon
	 */
	public void setElevationEdges(int elev, int id){
		//loop through all tiles
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				//if this tile is at the specified elevation
				if(tileMap[x][y].elevation==elev){
					//create a list of the tiles that are adjacent to this
					ArrayList<Tile> adjacentTiles = getTilesAdjacentToPosition(new Point(x,y),true);
					//if this tile is at an edge
					//check for any adjacent tiles that are not at the same elevation as this tile
					boolean isEdge = false;
					for(int i = 0; i<adjacentTiles.size();i++){
						if(adjacentTiles.get(i).elevation!=elev){//&&adjacentTiles.get(i).elevation<=tileMap[x][y].elevation
							isEdge = true;
						}
					}
					//if the tile is an edge tile make it be cliff
					if(isEdge){
						boolean wasFlagged = tileMap[x][y].flagged;
						int oldElev = tileMap[x][y].oldElevation;
						tileMap[x][y]= new Tile(x,y,id,elev,tileMap);
						tileMap[x][y].oldElevation=oldElev;
						tileMap[x][y].flagged=wasFlagged;
					}
				}
			}
		}
	}
	/*
	 * Recursively removes any sections of a plateau or canyon which are too small to walk on
	 * 
	 * @param elev- the elevation to perform this method upon
	 */
	public void removeAnySectionsOfCliffThatAreConnectedToCliffButTooSmallToWalkOn(int elev){
		//find all the cliff tiles which have cliff on only one side
		for(int x = 0; x<width;x++){
			for(int y = 0; y<height; y++){
				if(tileMap[x][y].tileID==3&&tileMap[x][y].elevation==elev){
					removeOutcrops(new Point(x,y),elev);
				}
			}
		}
	}
	/*replaces any cliff tile passed (which is connected to only one other cliff tile) with a grass tile
	 * 
	 * @param position - the position of the tile to check
	 * @param elev - the elevation to check for adjacent cliff tiles
	 */
	public void removeOutcrops(Point position, int elev){
		//get the tiles which are adjacent to this one
		ArrayList<Tile> adjacentTiles = getTilesAdjacentToPosition(new Point(position.x,position.y),false);
		//determine how many of the adjacent tiles are cliff
		int count = 0;//the number of adjacent cliff tiles detected
		ArrayList<Tile> cliffTiles = new ArrayList<Tile>();
		for(int i = 0; i<adjacentTiles.size();i++){
			if(adjacentTiles.get(i).tileID==3&&adjacentTiles.get(i).elevation==elev){
				//cliffTiles.add(adjacentTiles.get(i));
				count++;
			}
		}
		Tile thisTile = tileMap[position.x][position.y];
		if(getNorthTile(thisTile)!=null&&getSouthTile(thisTile)!=null&&getWestTile(thisTile)!=null&&getEastTile(thisTile)!=null){
			//check if top and bottom are the same elevation but left and right are not
			if(getNorthTile(thisTile).elevation==thisTile.elevation&&getSouthTile(thisTile).elevation==thisTile.elevation){
				if(getWestTile(thisTile).elevation!=thisTile.elevation&&getEastTile(thisTile).elevation!=thisTile.elevation&&getWestTile(thisTile).elevation==getEastTile(thisTile).elevation){
					if(getNorthTile(thisTile).tileID==3&&getSouthTile(thisTile).tileID==3){
						count = 1;
						cliffTiles.add(getNorthTile(thisTile));
						cliffTiles.add(getSouthTile(thisTile));
					}
				}
			}
			//check if left and right are the same elevation but top and bottom are not
			if(getNorthTile(thisTile).elevation!=thisTile.elevation&&getSouthTile(thisTile).elevation!=thisTile.elevation&&getNorthTile(thisTile).elevation==getSouthTile(thisTile).elevation){
				if(getWestTile(thisTile).elevation==thisTile.elevation&&getEastTile(thisTile).elevation==thisTile.elevation){
					if(getWestTile(thisTile).tileID==3&&getEastTile(thisTile).tileID==3){
						count = 1;
						cliffTiles.add(getWestTile(thisTile));
						cliffTiles.add(getEastTile(thisTile));
					}
				}
			}
		}
		if(position.x!=0&&position.y!=0&&position.x!=width-1&&position.y!=height-1){
			if(count==1){//there is only one cliff tile adjacent to this

				int oldElev = tileMap[position.x][position.y].oldElevation;
				//set this section to be grass
				tileMap[position.x][position.y]=new Tile(position.x,position.y,0,oldElev,tileMap);
				//tileMap[position.x][position.y].flagged=true;
				//call this method on the connected cliff sections
				for(int i = 0; i<cliffTiles.size();i++){
					removeOutcrops(new Point(cliffTiles.get(i).xpos/32,cliffTiles.get(i).ypos/32),elev);
				}
			}
		}
	}
	/*
	 * gets a list of all the tiles at a specified elevation
	 * 
	 * @param elevation - the elevation to check for tiles
	 */
	public ArrayList<Tile> getTilesAtElevation(int elev){
		ArrayList<Tile> tilesFound = new ArrayList<Tile>();
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				if(tileMap[x][y].elevation==elev){
					tilesFound.add(tileMap[x][y]);
				}
			}
		}
		return tilesFound;
	}
	public void saveLevel(String name){
		File dir = new File("src/Saves");
		dir.mkdir();
		File dir2 = new File("src/Saves/"+name);
		dir2.mkdir();
		//create a text file inside the folder to store the level's map
		File lvl = new File((dir2.getAbsolutePath())+"//"+name+"_"+seed+"_map.txt");
		if(!lvl.exists()){
			System.out.println("saiofjosieuriojndisdajfoidsjfoiwjeoijwoidfjoidsjfoiajdfoiajsdoiwoirejfoisdjlkwierojisdjfoiswjdf");
			Writer writer =null;
			try{
				//save the map data for the level
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lvl), "utf-8"));
				for(int x = 0; x<width;x++){
					for(int y = 0;y<height;y++){
						writer.write("("+tileMap[x][y].tileID+","+tileMap[x][y].elevation+")");
					}
					writer.write("\n");
				}
				try{writer.close();}catch(Exception ex){}
				System.out.println("Saved level as: "+name+" to filepath: "+lvl.getAbsolutePath());
			}
			catch(Exception ex){
				System.out.println("Failed to write to (or create) game map file: "+ name);
				ex.printStackTrace();
			}
			finally{
				try{writer.close();}catch(Exception ex){}
			}
		}
	}
	public boolean loadLevel(){
		waterlevel = 6;
		tileMap = new Tile[width][height];
		String path = "/Saves"+File.separator+name+File.separator+name+"_"+seed+"_map.txt";
		InputStream map= (Level.class.getResourceAsStream(path));
		if(new File("src"+path).exists()){
			BufferedReader reader = null;
			try{
				//load the level's tile arrangement
				reader = new BufferedReader(new InputStreamReader(map, "UTF-8"));

				for(int y = 0;y<height;y++){
					String line = reader.readLine();
					for(int x = 0; x<width;x++){
						int start = 0;
						int end = -1;
						String temp = "";
						//System.out.println(line);
						char[] lineChars = line.toCharArray();
						//loop through every character in the line
						for(int k = 0; k<lineChars.length;k++){
							if(lineChars[k]==')'){
								end++;
							}
							if(end==x){
								//find the start of this tile's data
								for(int p = k; p>=0;p--){
									if(lineChars[p]=='('){
										start = p+1;
										break;
									}
								}
								//The string representing this tile
								temp = line.substring(start, k+1);
								//split this into an arrayList of strings (one for each value)
								char[] tileChars = temp.toCharArray();
								//System.out.println("tile characters: \""+temp+"\" start: "+start+", k: "+k);
								ArrayList<String> values = new ArrayList<String>();
								int valueStart = 0;
								int valueEnd = 0;
								for(int p = 0;p<tileChars.length;p++){
									//System.out.print(tileChars[p]);
									if(tileChars[p]==','||tileChars[p]==')'){
										//System.out.println("tileChars[p]: \""+tileChars[p]+"\" p: "+p);
										valueEnd = p;
										//System.out.println(valueStart+","+valueEnd);
										values.add(temp.substring(valueStart,valueEnd));						
										valueStart=p+1;
									}
								}
								//System.out.println("values: "+values);
								tileMap[y][x]=new Tile(y,x,Integer.valueOf(values.get(0)),Integer.valueOf(values.get(1)),tileMap);
								break;
							}
						}

					}
					//System.out.println("");

				}
				try{reader.close();}catch(Exception ex){}

			}
			catch(Exception ex){
				System.out.println("Failed to load game map file: "+ name);
				ex.printStackTrace();
			}
			finally{
				try{reader.close();}catch(Exception ex){}
			}
			return true;
		}
		return false;
	}

	public Tile getNorthTile(Tile tile){
		if((tile.ypos/32)-1>0){
			return tileMap[(tile.xpos/32)][(tile.ypos/32)-1];
		}
		return null;
	}
	public Tile getNorthEastTile(Tile tile){
		if((tile.ypos/32)-1>=0&&(tile.xpos/32)+1<width){
			return tileMap[(tile.xpos/32)+1][(tile.ypos/32)-1];
		}
		return null;
	}
	public Tile getSouthTile(Tile tile){
		if((tile.ypos/32)+1<height){
			return tileMap[(tile.xpos/32)][(tile.ypos/32)+1];
		}
		return null;
	}
	public Tile getSouthEastTile(Tile tile){
		if((tile.ypos/32)+1<height&&(tile.xpos/32)+1<width){
			return tileMap[(tile.xpos/32)+1][(tile.ypos/32)+1];
		}
		return null;
	}
	public Tile getWestTile(Tile tile){
		if((tile.xpos/32)-1>0){
			return tileMap[(tile.xpos/32)-1][(tile.ypos/32)];
		}
		return null;
	}
	public Tile getNorthWestTile(Tile tile){
		if((tile.ypos/32)-1>=0&&(tile.xpos/32)-1>=0){
			return tileMap[(tile.xpos/32)-1][(tile.ypos/32)-1];
		}
		return null;
	}
	public Tile getEastTile(Tile tile){
		if((tile.xpos/32)+1<width){
			return tileMap[(tile.xpos/32)+1][(tile.ypos/32)];
		}
		return null;
	}
	public Tile getSouthWestTile(Tile tile){
		if((tile.ypos/32)+1<height&&(tile.xpos/32)-1>=0){
			return tileMap[(tile.xpos/32)-1][(tile.ypos/32)+1];
		}
		return null;
	}
	/* Get/Set methods*/
	public int getSeed()
	{
		return this.seed;
	}
	public void setSeed(int s)
	{
		this.seed = s;
	}
	public String getLevelName()
	{
		return this.name;
	}
	public void setLevelName(String str)
	{
		this.name = str;
	}
	/*
	 * gets all of the tiles that are adjacent to a tile
	 * 
	 * @param position - the position of the tile to check for adjacency
	 * @param includeCorners - true if corners should be added to the list of adjacent tiles, false if not
	 * 
	 * @return An arraylist of all the tiles adjacent to the specified tile position
	 */
	public ArrayList<Tile> getTilesAdjacentToPosition(Point position,boolean includeCorners){
		ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
		for(int x = position.x-1; x<=position.x+1;x++){
			for(int y = position.y-1; y<=position.y+1;y++){
				if(x>=0&&y>=0&&x<width&&y<height){
					if(includeCorners){
						if(!(x==position.x&&y==position.y)){
							adjacentTiles.add(tileMap[x][y]);
						}
					}
					else{
						//if(!(x==position.x-1&&y==position.y-1)&&!(x==position.x+1&&y==position.y-1)&&!(x==position.x-1&&y==position.y+1)&&!(x==position.x+1&&y==position.y+1)){
						if(x==position.x||y==position.y){	
							if(!(x==position.x&&y==position.y)){
								adjacentTiles.add(tileMap[x][y]);
							}
						}
						//}
					}
				}
			}
		}
		//		//flag all adjacent tiles for testing purposes
		//		for(int i = 0; i<adjacentTiles.size();i++){
		//			adjacentTiles.get(i).flagged=true;
		//		}
		return adjacentTiles;
	}

	/*
	 * generates a very specific room with little to no randomization
	 */
	private void buildRoom(int ID)
	{
		hardMap = new Tile[width][height];
		switch(ID)
		{
		//Overworld
		case 0: 
			//overworld = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,7,2,tileMap); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)			//wall in with stone wall
			{
				hardMap[x][0] = new Tile(x,0,1,0,tileMap);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0,tileMap);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1,tileMap);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1,tileMap);
			}
			break;

			//boss room a
		case 1:
			//bossA = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,6,0,tileMap); //base tile grass
				}
			}
			addTallGrass(hardMap);
			for(int x = 0; x < width; x++)			//wall in with tree cover
			{
				hardMap[x][0] = new Tile(x,0,8,1,tileMap);
				hardMap[x][height - 1] = new Tile(x, height - 1, 8, 1,tileMap);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,8,2,tileMap);
				hardMap[width - 1][y] = new Tile(width - 1,y,8,2,tileMap);
			}

			break;

			//boss room b
		case 2:
			//bossB = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,0,1,tileMap); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)			//wall in with stone wall
			{
				hardMap[x][0] = new Tile(x,0,1,0,tileMap);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0,tileMap);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1,tileMap);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1,tileMap);
			}
			break;

			//boss room c
		case 3:
			//bossC = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,0,1,tileMap); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)
			{
				hardMap[x][0] = new Tile(x,0,1,0,tileMap);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0,tileMap);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1,tileMap);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1,tileMap);
			}
			break;

			//safe zone
		case 4:
			//safeRoom = new Tile[width][height];
			for(int x = 0; x<width;x++){
				for(int y = 0; y<height; y++){
					hardMap[x][y]=new Tile(x,y,0,1,tileMap); //base tile is heavy dirt
				}
			}
			for(int x = 0; x < width; x++)
			{
				hardMap[x][0] = new Tile(x,0,1,0,tileMap);
				hardMap[x][height - 1] = new Tile(x, height - 1, 1, 0,tileMap);
			}
			for(int y = 0; y < height - 1; y++)
			{
				hardMap[0][y] = new Tile(0,y,1,1,tileMap);
				hardMap[width - 1][y] = new Tile(width - 1,y,1,1,tileMap);
			}
			break;		
		}
	}
	/*
	 * adds randomly generated tall grass to the tileMap
	 */
	private Tile[][] addTallGrass(Tile[][] map)
	{
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(map[x][y].getArtX() == 6 && map[x][y].getArtY() == 0)
					if(levelGenerationRandom.nextFloat() < 0.1)
						map[x][y].setArtY(1);
		return map;
	}



	/*
	 * Used to determine how many of the specified tile type is adjacent to a position
	 * 
	 * @param xpos - the x position of the tile to check for adjacent tiles
	 * @param ypos - the y position of the tile to check for adjacent tiles
	 * @param xID - the artX of the tile to be checked for
	 * @param yID - the artY of the tile to be checked for
	 * 
	 * @return The number of tiles of the type specified that are adjacent to this
	 */
	public ArrayList<Tile> tilesAdjacentToPosition(int xpos, int ypos, int id, boolean careAboutElevation, int desiredElevation){
		ArrayList<Tile> adjacentTiles = new ArrayList<Tile>();
		//top
		if(ypos-1>=0){
			if(tileMap[xpos][ypos-1].tileID==id){
				if(careAboutElevation == false||tileMap[xpos][ypos-1].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos][ypos-1]);
				}
			}
		}
		//bottom
		if(ypos+1<height){
			if(tileMap[xpos][ypos+1].tileID==id){
				if(careAboutElevation == false||tileMap[xpos][ypos+1].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos][ypos+1]);
				}
			}
		}
		//left
		if(xpos-1>=0){
			if(tileMap[xpos-1][ypos].tileID==id){
				if(careAboutElevation == false||tileMap[xpos-1][ypos].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos-1][ypos]);
				}
			}
		}
		//right
		if(xpos+1<width){
			if(tileMap[xpos+1][ypos].tileID==id){
				if(careAboutElevation == false||tileMap[xpos+1][ypos].elevation==desiredElevation){
					adjacentTiles.add(tileMap[xpos+1][ypos]);
				}
			}
		}

		return adjacentTiles;
	}
	/*
	 * generates a random number in the specified range
	 * 
	 * @param min - the lowest number possible
	 * @param max - the highest number possible
	 * 
	 * @return a random number in the range
	 */
	public int randomNumber(int min, int max){
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
	/*
	 * ONLY USE FOR GENERATING LEVEL LAYOUT, NOTHING ELSE!
	 * generates a random number in the specified range 
	 * 
	 * @param min - the lowest number possible
	 * @param max - the highest number possible
	 * 
	 * @return a random number in the range
	 */
	public int randomNumberForLevelGeneration(int min, int max){
		if(min>max){
			int temp = min;
			min = max;
			max = temp;
		}
		if(max==min){
			return max;
		}
		int randNum = levelGenerationRandom.nextInt((max-min)+1) + min;
		return randNum;
	}
	/*
	 * updates various objects in the level
	 */
	public void update(){
		GamePanel.player.update();
	}
	/*
	 * Draws the level.
	 * 
	 * @param g - the graphics2D object to use for drawing to the GamePanel.
	 * 
	 * @return none.
	 */
	public void Draw(Graphics2D g){
		int viewDistanceX = ((ApplicationUI.windowWidth/32)/2)+3;
		int viewDistanceY = ((ApplicationUI.windowHeight/32)/2)+3;
		drawingLevel = true;

		ArrayList<Tile> overlayTilesToDraw = new ArrayList<Tile>();
		for(int x = (int)(GamePanel.player.xpos/32)-(viewDistanceX); x<(int)(GamePanel.player.xpos/32)+(viewDistanceX);x++){
			for(int y = (int)(GamePanel.player.ypos/32)-(viewDistanceY); y<(int)(GamePanel.player.ypos/32)+(viewDistanceY);y++){
				if(weatherID != -1)
				{
					if(randomNumber(1,2500/(int)(fogAlpha+1))==1){
						//add weather particle
						int tempX = (x*32)+randomNumber(0,31);
						int tempY = (y*32)+randomNumber(0,31);
						weather.add(new weatherParticle(tempX,tempY,weatherID));
					}
				}
				if(x>=0&&y>=0&&x<width&&y<height){
					tileMap[x][y].Draw(g,1,false);
					if(tileMap[x][y].vegetationID!=-1){
						overlayTilesToDraw.add(tileMap[x][y]);
					}
				}
			}
		}
		//draw monsters
		for(int i = 0; i<monsters.size();i++){
			monsters.get(i).update();
			monsters.get(i).Draw(g);
		}
		drawingLevel = false;
		GamePanel.player.Draw(g);
		drawingLevel = true;
		//draw vegetation
		for(int i = 0; i<overlayTilesToDraw.size();i++){
			overlayTilesToDraw.get(i).DrawVegetation(g, 1.0, false);
		}
		//draw weather
		if(weatherID==0){
			if(fogAlpha<50){
				fogAlpha+=.05;
			}
			fogColor = new Color(20,20,80,(int)fogAlpha);
			g.setColor(fogColor);
			g.fillRect(0, 0, ApplicationUI.windowWidth, ApplicationUI.windowHeight);
		}
		else{
			if(fogColor.getAlpha()>0){
				fogAlpha-=.05;
				if(fogAlpha<0){
					fogAlpha=0;
				}
			}
		}
		for(int i = 0; i<weather.size();i++){
			weather.get(i).update();
			weather.get(i).Draw(g);

		}
		drawingLevel = false;
		//update the player however many times it should have updated but couldn't because of the program 
		//being in the process of drawing.
		for(int i = 0; i<GamePanel.player.updatesInQue;i++){
			GamePanel.player.update();
		}
		GamePanel.player.updatesInQue=0;
		//draw the mini-map

	}
}
