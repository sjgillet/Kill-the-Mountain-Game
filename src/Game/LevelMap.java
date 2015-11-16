package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LevelMap {
	int xpos = 0;
	int ypos = 0;
	int pixelWidthPerTile = 8;
	double zoom = 1.0;
	int borderWidth = 2;
	public BufferedImage mapImage;
	public LevelMap(Tile[][] map){
		mapImage = new BufferedImage( (map.length*pixelWidthPerTile),(map[0].length*pixelWidthPerTile), BufferedImage.TYPE_INT_ARGB);
		Graphics g = mapImage.getGraphics();
		//g.setColor(Color.yellow);
		//g.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
		for(int x = 0; x<map.length;x++){
			for(int y = 0; y<map[0].length;y++){
				map[x][y].Draw((Graphics2D)g, (double)pixelWidthPerTile/32.0,true);
				//g.drawImage(GamePanel.tiles[map[x][y].artX][map[x][y].artY], (x*pixelWidthPerTile), (y*pixelWidthPerTile),pixelWidthPerTile,pixelWidthPerTile, null);
			}
		}
		for(int x = 0; x<map.length;x++){
			for(int y = 0; y<map[0].length;y++){
				map[x][y].DrawVegetation((Graphics2D)g, (double)pixelWidthPerTile/32.0,true);
				//g.drawImage(GamePanel.tiles[map[x][y].artX][map[x][y].artY], (x*pixelWidthPerTile), (y*pixelWidthPerTile),pixelWidthPerTile,pixelWidthPerTile, null);
			}
		}
		g.dispose();
	}
	public void Draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, ApplicationUI.windowWidth, ApplicationUI.windowHeight);
		g.drawImage(mapImage, (int)(double)(xpos*zoom)+borderWidth, (int)(double)(ypos*zoom)+borderWidth,(int)(double)(mapImage.getWidth()*zoom), (int)(double)(mapImage.getHeight()*zoom), null);
		//draw the cross-hairs at the player's position
		g.setColor(new Color(255,0,220));
		g.drawLine((int)(double)((xpos+(int)((GamePanel.player.xpos/32)*pixelWidthPerTile))*zoom), (int)(double)(ypos*zoom), (int)(double)((xpos+(int)((GamePanel.player.xpos/32)*pixelWidthPerTile))*zoom), (int)(double)((ypos+mapImage.getHeight())*zoom));//vertical line
		g.drawLine((int)(double)(xpos*zoom), (int)(double)((ypos+(int)((GamePanel.player.ypos/32)*pixelWidthPerTile))*zoom), (int)(double)((xpos+mapImage.getWidth())*zoom), (int)(double)((ypos+(int)((GamePanel.player.ypos/32)*pixelWidthPerTile))*zoom));//horizontal line
	}
}
