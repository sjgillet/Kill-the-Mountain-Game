package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile {
	int xpos;
	int ypos;
	int artX;
	int artY;
	int overlayArtX=-1;
	int overlayArtY=-1;
	int tileID = 0;
	int elevation;
	int oldElevation;
	int vegetationID=-1;
	Color vegetationColor;
	BufferedImage vegetationImage;
	boolean flagged = false;
	Color flagColor = new Color(200,0,0,100);
	int collisionType = 0;//0 means no collision, 1 means collision
	ArrayList<Rectangle> collisionBoxes = new ArrayList<Rectangle>();

	public Tile(int x, int y, int id, int elev){
		xpos = x*32;
		ypos = y*32;
		elevation = elev;
		oldElevation = elev;
		tileID = id;
		collisionBoxes.add(new Rectangle(xpos,ypos,32,32));
		if(tileID==-1){
			collisionType = 0;
			artX = 4;
			artY = 0;
		}
		if(tileID==0){//grass
			artX = 6;
			artY = 0;
			collisionType = 0;
		}
		if(tileID==1){//water
			artX = 3;
			artY = 0;
			collisionType = 1;
		}

		if(tileID==2){//sand
			artX = 7;
			artY = 0;
			collisionType = 0;
		}
		if(tileID==3){//Plateau wall
			artX=6;
			artY=2;
			collisionType = 1;
		}
		if(tileID==4){//roof facing away from the player
			artX = 1;
			artY = 9;
		}
		if(tileID==5){//roof facing towards the player
			artX = 1;
			artY = 10;
		}
		if(tileID==6){//house wall
			artX = 1;
			artY = 11;
		}
		if(tileID==7){//abyss
			artX = 2;
			artY = 0;
		}

	}


	public int getXpos()
	{
		return this.xpos;
	}
	public int getYpos()
	{
		return this.ypos;
	}
	public void setXpos(int x)
	{
		this.xpos = x;
	}
	public void setYpos(int y)
	{
		this.ypos = y;
	}
	public int getArtX()
	{
		return this.artX;
	}
	public int getArtY()
	{
		return this.artY;
	}
	public void setArtX(int x)
	{
		this.artX = x;
	}
	public void setArtY(int y)
	{
		this.artY = y;
	}
	public boolean isFlagged()
	{
		return this.flagged;
	}
	public void setFlagged(boolean bool)
	{
		this.flagged = bool;
	}


	//	public Tile(int x, int y, int xID, int yID){
	//		xpos = x*32;
	//		ypos = y*32;
	//		artX = xID;
	//		artY = yID;
	//		collisionBox = new Rectangle(xpos,ypos,32,32);
	//		
	//
	//	}
	/*
	 * Updates the artwork of the tile to make it match up with surrounding tiles
	 */
	public void updateArt(){
		Tile northernTile = getNorthTile();
		Tile northEasternTile = getNorthEastTile();
		Tile easternTile = getEastTile();
		Tile southEasternTile = getSouthEastTile();
		Tile southernTile = getSouthTile();
		Tile southWesternTile = getSouthWestTile();
		Tile westernTile = getWestTile();
		Tile northWesternTile = getNorthWestTile();
		if(northernTile!=null&&southernTile!=null&&easternTile!=null&&westernTile!=null&&
				northEasternTile!=null&&southEasternTile!=null&&northWesternTile!=null&&southWesternTile!=null||true){
			if(vegetationID==0){//tree
				BufferedImage[][] imagesToCombine = new BufferedImage[3][3];
				for(int i = 0; i<3;i++){
					for(int j = 0; j<3;j++){
						imagesToCombine[i][j]=GamePanel.overlayTiles[i+2][j];
					}
				}
				vegetationImage = FileIO.combineImages(imagesToCombine);
				vegetationImage = FileIO.colorImage(vegetationImage, vegetationColor);
				collisionType=1;
				collisionBoxes.get(0).width=10;
				collisionBoxes.get(0).height=10;
				collisionBoxes.get(0).x=xpos+11;
				collisionBoxes.get(0).y=ypos+11;
			}
			if(vegetationID==1){//flower
				vegetationImage=GamePanel.overlayTiles[2][3];
				vegetationImage = FileIO.colorImage(vegetationImage, vegetationColor);

			}
			if(tileID==0){
				if(elevation>=GamePanel.levels.get(GamePanel.currentLevel).waterlevel){
					artX = 6;
					artY = 0;
				}
				else{//below sea level
					artX = 11;
					artY = 4;
					overlayArtX = 0;
					overlayArtY = GamePanel.levels.get(GamePanel.currentLevel).waterlevel-elevation;
					if(overlayArtY>4){
						overlayArtY=4;
					}
				}
			}
			if(tileID==3){//plateau wall
				//check if this is the top edge
				if(northernTile!=null&&northernTile.elevation<elevation){	
					//if(southernTile!=null&&southernTile.elevation==elevation){					
					if(westernTile!=null&&westernTile.elevation==elevation){
						if(easternTile!=null&&easternTile.elevation==elevation){
							//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
							//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
							//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
							//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
							artX=9;
							artY=3;
							collisionBoxes.get(0).y+=4;
							collisionBoxes.get(0).height=5;
							//}
							//}
							//}
							//}
						}
					}
					//}
				}
				//check if this is the bottom edge
				//if(northernTile!=null&&northernTile.elevation==elevation){	
				if(southernTile!=null&&southernTile.elevation<elevation){
					if(westernTile!=null&&westernTile.elevation==elevation){
						if(easternTile!=null&&easternTile.elevation==elevation){
							//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
							//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
							//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
							//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
							artX=9;
							artY=5;
							collisionBoxes.get(0).height-=5;
							collisionBoxes.get(0).y+=5;
							//}
							//}
							//}
							//}
						}
					}
				}
				//}
				//check if this is the left edge
				if(northernTile!=null&&northernTile.elevation==elevation){	
					if(southernTile!=null&&southernTile.elevation==elevation){					
						if(westernTile!=null&&westernTile.elevation<elevation){
							//if(easternTile!=null&&easternTile.elevation==elevation){
							//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
							//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
							//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
							//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
							artX=9;
							artY=1;
							collisionBoxes.get(0).width=9;
							//}
							//}
							//}
							//}
							//}
						}
					}
				}
				//check if this is the right edge
				if(northernTile!=null&&northernTile.elevation==elevation){	
					if(southernTile!=null&&southernTile.elevation==elevation){					
						//if(westernTile!=null&&westernTile.elevation==elevation){
						if(easternTile!=null&&easternTile.elevation<elevation){
							//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
							//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
							//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
							//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
							artX=9;
							artY=9;
							collisionBoxes.get(0).width=9;
							collisionBoxes.get(0).x+=21;
							//}
							//}
							//}
							//}
						}
						//}
					}
				}
				//check if this is the top right corner with lower elevation right and above
				if(northernTile!=null&&northernTile.elevation<elevation){	
					if(southernTile!=null&&southernTile.elevation==elevation){					
						if(westernTile!=null&&westernTile.elevation==elevation){
							if(easternTile!=null&&easternTile.elevation<elevation){
								//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
								//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
								//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
								//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
								artX=9;
								artY=8;
								collisionBoxes.get(0).height=27;
								collisionBoxes.get(0).y+=5;
								collisionBoxes.get(0).width=9;
								collisionBoxes.get(0).x=xpos+23;
								collisionBoxes.add(new Rectangle(xpos,ypos+4,32,5));
								//}
								//}
								//}
								//}
							}
						}
					}
				}
				//check if this is the top left corner with lower elevation left and above
				if(northernTile!=null&&northernTile.elevation<elevation){	
					if(southernTile!=null&&southernTile.elevation==elevation){					
						if(westernTile!=null&&westernTile.elevation<elevation){
							if(easternTile!=null&&easternTile.elevation==elevation){
								//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
								//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
								//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
								//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
								artX=9;
								artY=0;
								collisionBoxes.get(0).height=27;
								collisionBoxes.get(0).y+=5;
								collisionBoxes.get(0).width=9;
								collisionBoxes.add(new Rectangle(xpos,ypos+4,32,5));
								//}
								//}
								//}
								//}
							}
						}
					}
				}
				//check if this is the bottom left corner with lower elevation left and below
				if(northernTile!=null&&northernTile.elevation==elevation){	
					if(southernTile!=null&&southernTile.elevation<elevation){					
						if(westernTile!=null&&westernTile.elevation<elevation){
							if(easternTile!=null&&easternTile.elevation==elevation){
								//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
								//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
								//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
								//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
								artX=9;
								artY=2;
								collisionBoxes.get(0).height=27;
								collisionBoxes.get(0).y+=5;
								collisionBoxes.add(new Rectangle(xpos,ypos,9,32));
								//}
								//}
								//}
								//}
							}
						}
					}
				}
				//check if this is the bottom right corner with lower elevation right and below
				if(northernTile!=null&&northernTile.elevation==elevation){	
					if(southernTile!=null&&southernTile.elevation<elevation){					
						if(westernTile!=null&&westernTile.elevation==elevation){
							if(easternTile!=null&&easternTile.elevation<elevation){
								//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
								//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
								//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
								//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
								artX=9;
								artY=10;
								collisionBoxes.get(0).height=27;
								collisionBoxes.get(0).y+=5;
								collisionBoxes.add(new Rectangle(xpos+21,ypos,9,32));
								//}
								//}
								//}
								//}
							}
						}
					}
				}
				//check if this is the inverted top left corner
				if(northernTile!=null&&northernTile.elevation>=elevation){	
					if(southernTile!=null&&southernTile.elevation==elevation){					
						if(westernTile!=null&&westernTile.elevation>=elevation){
							if(easternTile!=null&&easternTile.elevation==elevation){
								if(northWesternTile!=null&&northWesternTile.elevation>=elevation){	
									if(southWesternTile!=null&&southWesternTile.elevation>=elevation){	
										//if(northEasternTile!=null&&northEasternTile.elevation==elevation){	
										if(southEasternTile!=null&&southEasternTile.elevation<elevation){	
											artX=9;
											artY=11;
											collisionBoxes.get(0).height=27;
											collisionBoxes.get(0).width=9;
											collisionBoxes.get(0).y+=5;
											collisionBoxes.get(0).x+=23;
											
											
										}
										//}
									}
								}
							}
						}
					}
				}
				//check if this is the inverted top right corner
				if(northernTile!=null&&northernTile.elevation>=elevation){	
					if(southernTile!=null&&southernTile.elevation==elevation){					
						if(westernTile!=null&&westernTile.elevation==elevation){
							if(easternTile!=null&&easternTile.elevation>=elevation){
								//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
								if(southWesternTile!=null&&southWesternTile.elevation<elevation){	
									if(northEasternTile!=null&&northEasternTile.elevation>=elevation){	
										if(southEasternTile!=null&&southEasternTile.elevation>=elevation){	
											artX=9;
											artY=6;
											collisionBoxes.get(0).height=27;
											collisionBoxes.get(0).width=9;
											collisionBoxes.get(0).y+=5;
										}
									}
								}
								//}
							}
						}
					}
				}
				//check if this is the inverted bottom left corner
				if(northernTile!=null&&northernTile.elevation==elevation){	
					//if(southernTile!=null&&southernTile.elevation==elevation){					
					//if(westernTile!=null&&westernTile.elevation==elevation){
					if(easternTile!=null&&easternTile.elevation==elevation){
						if(northWesternTile!=null&&northWesternTile.elevation>=elevation){	
							//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
							if(northEasternTile!=null&&northEasternTile.elevation<elevation){	
								if(southEasternTile!=null&&southEasternTile.elevation>=elevation){	
									artX=9;
									artY=12;
									collisionBoxes.get(0).width=9;
									collisionBoxes.get(0).height=9;
									collisionBoxes.get(0).x+=23;
								}
							}
							//}
						}
					}
					//}
					//}
				}
				//check if this is the inverted bottom right corner
				if(northernTile!=null&&northernTile.elevation==elevation){	
					if(southernTile!=null&&southernTile.elevation>=elevation){					
						if(westernTile!=null&&westernTile.elevation==elevation){
							if(easternTile!=null&&easternTile.elevation>=elevation){
								if(northWesternTile!=null&&northWesternTile.elevation<elevation){	
									if(southWesternTile!=null&&southWesternTile.elevation>=elevation){	
										if(northEasternTile!=null&&northEasternTile.elevation>=elevation){	
											if(southEasternTile!=null&&southEasternTile.elevation>=elevation){	
												artX=9;
												artY=7;
												collisionBoxes.get(0).width=9;
												collisionBoxes.get(0).height=9;
											}
										}
									}
								}
							}
						}
					}
				}
				//check if this is a lone rock
				if(northernTile!=null&&northernTile.elevation<elevation){	
					if(southernTile!=null&&southernTile.elevation<elevation){					
						if(westernTile!=null&&westernTile.elevation<elevation){
							if(easternTile!=null&&easternTile.elevation<elevation){
								//if(northWesternTile!=null&&northWesternTile.elevation==elevation){	
								//if(southWesternTile!=null&&southWesternTile.elevation==elevation){	
								//if(northEasternTile!=null&&northEasternTile.elevation<elevation){	
								//if(southEasternTile!=null&&southEasternTile.elevation==elevation){	
								artX=9;
								artY=13;
								collisionBoxes.get(0).width=26;
								collisionBoxes.get(0).height=26;
								collisionBoxes.get(0).x+=3;
								collisionBoxes.get(0).y+=3;
								//}
								//}
								//}
								//}
							}
						}
					}
				}
				if(elevation>=30){
					artX = 13;
				}
				else if(elevation>GamePanel.levels.get(GamePanel.currentLevel).waterlevel&&elevation<30){//above sea level and below lava lava level
					artX = 9;
				}
				else if(elevation==GamePanel.levels.get(GamePanel.currentLevel).waterlevel){//at sea level
					artX=10;
					overlayArtX = 1;
					overlayArtY = artY;
				}
				else{//below sea level
					artX=11;
					overlayArtX = 0;
					overlayArtY = GamePanel.levels.get(GamePanel.currentLevel).waterlevel-elevation;
					if(overlayArtY>4){
						overlayArtY=4;
					}
				}
			}
		}
	}
	public Tile getNorthTile(){
		if((ypos/32)-1>0){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)][(ypos/32)-1];
		}
		return null;
	}
	public Tile getNorthEastTile(){
		if((ypos/32)-1>=0&&(xpos/32)+1<GamePanel.levels.get(GamePanel.currentLevel).width){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)+1][(ypos/32)-1];
		}
		return null;
	}
	public Tile getSouthTile(){
		if((ypos/32)+1<GamePanel.levels.get(GamePanel.currentLevel).height){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)][(ypos/32)+1];
		}
		return null;
	}
	public Tile getSouthEastTile(){
		if((ypos/32)+1<GamePanel.levels.get(GamePanel.currentLevel).height&&(xpos/32)+1<GamePanel.levels.get(GamePanel.currentLevel).width){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)+1][(ypos/32)+1];
		}
		return null;
	}
	public Tile getWestTile(){
		if((xpos/32)-1>0){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)-1][(ypos/32)];
		}
		return null;
	}
	public Tile getNorthWestTile(){
		if((ypos/32)-1>=0&&(xpos/32)-1>=0){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)-1][(ypos/32)-1];
		}
		return null;
	}
	public Tile getEastTile(){
		if((xpos/32)+1<GamePanel.levels.get(GamePanel.currentLevel).width){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)+1][(ypos/32)];
		}
		return null;
	}
	public Tile getSouthWestTile(){
		if((ypos/32)+1<GamePanel.levels.get(GamePanel.currentLevel).height&&(xpos/32)-1>=0){
			return GamePanel.levels.get(GamePanel.currentLevel).tileMap[(xpos/32)-1][(ypos/32)+1];
		}
		return null;
	}
	public void Draw(Graphics2D g, double scale, boolean forWorldMap){
		int x=(int)(double)(xpos*scale);
		int y=(int)(double)(ypos*scale);
		int index = elevation;
		if(index>49){
			index = 49;
		}
		if(forWorldMap==false){
			x = (int)(double)((((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos)*scale);
			y = (int)(double)((((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos)*scale);
		}
		//updateArt();
		if(tileID!=3&&tileID!=0){
			g.drawImage(GamePanel.tiles[artX][artY],x,y,(int)(double)(32*scale),(int)(double)(32*scale),null);
		}
		else if(tileID==0){
			g.drawImage(GamePanel.levels.get(GamePanel.currentLevel).tilesRankedByElevation[index][4],x,y,(int)(double)(32*scale),(int)(double)(32*scale),null);
		}
		else{
			g.drawImage(GamePanel.levels.get(GamePanel.currentLevel).tilesRankedByElevation[index][artY],x,y,(int)(double)(32*scale),(int)(double)(32*scale),null);
		}
		//draw the overlay image
		if(overlayArtX!=-1&&overlayArtY!=-1){
			g.drawImage(GamePanel.overlayTiles[overlayArtX][overlayArtY],x,y,(int)(double)(32*scale),(int)(double)(32*scale),null);
		}
		if(vegetationID==1){
			g.drawImage(vegetationImage,x,y,(int)(double)(32*scale),(int)(double)(32*scale),null);
		}
		if(flagged){
			g.setColor(flagColor);
			g.fillRect(x,y, 32, 32);
			//flagged = false;
		}
		//Font font = new Font("Iwona Heavy",Font.BOLD,10);
		//g.setFont(font);
		g.setColor(Color.WHITE);
		//g.drawString(elevation+"", ((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos+10,((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos+10);
		//g.setColor(Color.pink);
		//g.drawString(oldElevation+"", ((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos+10,((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos+25);
	}
	public void DrawVegetation(Graphics2D g, double scale, boolean forWorldMap){
		int x=(int)(double)(xpos*scale);
		int y=(int)(double)(ypos*scale);
		if(forWorldMap==false){
			x = (int)(double)((((ApplicationUI.windowWidth/2)-16)+xpos-(int)GamePanel.player.xpos)*scale);
			y = (int)(double)((((ApplicationUI.windowHeight/2)-16)+ypos-(int)GamePanel.player.ypos)*scale);
		}
		if(vegetationID==0){
			g.drawImage(vegetationImage,x-(int)(double)(32*scale),y-(int)(double)(64*scale),(int)(double)(96*scale),(int)(double)(96*scale),null);
		}
		if(collisionType==1&&false){//show collision boxes
			g.setColor(new Color(255,255,0,100));
			for(int i = 0; i<collisionBoxes.size();i++){
				g.fillRect(x+(collisionBoxes.get(i).x-xpos), y+(collisionBoxes.get(i).y-ypos), collisionBoxes.get(i).width, collisionBoxes.get(i).height);
			}
		}
	}
}
