package Game;

import java.awt.Graphics;
import java.util.ArrayList;

public class Room {
	String name = "start";
	int width = 0;
	int height = 0;
	int minLevel = 1;
	int maxLevel = 3;
	Tile[][] tiles;
	public Room(String Name, int Width, int Height){
		name = Name;
		width = Width;
		height = Height;
		//initialize the tile array
		tiles = new Tile[width][height];
		fillRoomWithTile(6,0);
	}
	public void fillRoomWithTile(int x,int y){
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				tiles[i][j]=new Tile(x,y,i*32,j*32);
			}
		}
	}
	public void Draw(Graphics g){

		ArrayList <Grass>grassTiles=new ArrayList<Grass>();
		double occupiedGrassCount =0;
		//draw background tiles
		for(int i = 0; i<width;i++){
			for(int j = 0;j<height;j++){
				tiles[i][j].Draw(g);
				if(tiles[i][j].grass!=null){
					tiles[i][j].grass.drawRear(g);
					grassTiles.add(tiles[i][j].grass);
					if(tiles[i][j].grass.occupied){
						occupiedGrassCount++;
					}
				}


			}
		}
		for(int i = 0; i<width;i++){
			for(int j = 0;j<height;j++){
				for(int k = 0; k<AppletUI.client.players.size();k++){
					if(AppletUI.client.players.get(k).xpos==i&&AppletUI.client.players.get(k).ypos==j){
						AppletUI.client.players.get(k).Draw(g);
					}
				}
			}
		}
		for(int i = 0; i<width;i++){
			for(int j = 0;j<height;j++){
				if(tiles[i][j].grass!=null){
					tiles[i][j].grass.drawFront(g);
				}
			}
		}

		//10% of grass tiles should be occupied
		if(occupiedGrassCount/grassTiles.size()<.1&&AppletUI.client.players.get(0).battling==false){
			int temp = GamePanel.randomNumber(0, grassTiles.size()-1);
			grassTiles.get(temp).occupied=true;
		}
		if(this.name=="Forbidden Cave"){
			if(!GamePanel.editMode)
				g.drawImage(GamePanel.darkness,0,0,AppletUI.windowWidth,AppletUI.windowHeight,null);
		}
	}
}
