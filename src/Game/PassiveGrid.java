package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class PassiveGrid {
	PassiveNode[][] grid = new PassiveNode[10][10];
	boolean visible = false;

	public PassiveGrid(){
		//populate the grid
		for(int i = 0; i<grid.length;i++){
			for(int j = 0; j<grid[0].length;j++){
				int roll = GamePanel.randomNumber(0, 9);
				if(roll==0){
					grid[i][j]=new PassiveNode("10% increased damage",roll,i*50,j*50);
				}
				else if(roll==1){
					grid[i][j]=new PassiveNode("10% increased defense",roll,i*50,j*50);
				}
				else if(roll==2){
					grid[i][j]=new PassiveNode("+1 life returned on hit",roll,i*50,j*50);
				}
				else if(roll==3){
					grid[i][j]=new PassiveNode("2% increased experience gain",roll,i*50,j*50);
				}
				else if(roll==4){
					grid[i][j]=new PassiveNode("10% increased life",roll,i*50,j*50);
				}
				else if(roll==5){
					grid[i][j]=new PassiveNode("20% increased critical strike chance",roll,i*50,j*50);
				}
				else if(roll==6){
					grid[i][j]=new PassiveNode("1% increased life for each point of laziness this creature possesses",roll,i*50,j*50);
				}
				else if(roll==7){
					grid[i][j]=new PassiveNode("10% increased chance to dodge",roll,i*50,j*50);
				}
				else if(roll==8){
					grid[i][j]=new PassiveNode("15% increased damage",roll,i*50,j*50);
				}
				else if(roll==9){
					grid[i][j]=new PassiveNode("5% increased defense",roll,i*50,j*50);
				}
			}
		}
		//determine the creature's starting position in the passive grid
		grid[GamePanel.randomNumber(0, 9)][GamePanel.randomNumber(0, 9)].takePassive();
	}
	public void Draw(Graphics g){//draw the passive grid
		if(visible){
			for(int i = 0; i<grid.length;i++){
				for(int j = 0; j<grid[0].length;j++){
					grid[i][j].Draw(g);

				}
			}
			for(int i = 0; i<grid.length;i++){
				for(int j = 0; j<grid[0].length;j++){
					if(grid[i][j].mouseOverThis()==true){
						g.drawImage(GamePanel.descriptionPanel,grid[i][j].xpos+50,grid[i][j].ypos,530,50,null);
						Font font = new Font("Iwona Heavy",Font.BOLD,16);
						g.setFont(font);
						g.setColor(Color.WHITE);
						g.drawString(grid[i][j].name, grid[i][j].xpos+55, grid[i][j].ypos+30);
					}
				}
			}
			for(int i = 0; i<grid.length;i++){
				for(int j = 0; j<grid[0].length;j++){
					if(grid[i][j].taken){
						PassiveNode north = null;
						PassiveNode south = null;
						PassiveNode east = null;
						PassiveNode west = null;
						if(i>0){
							north = grid[i-1][j];
						}
						else if(j>0){
							west = grid[i][j-1];
						}
						else if(i<9){
							south = grid[i+1][j];
						}
						else if(j<9){
							east = grid[i][j+1];
						}
						if(north!=null){
							if(north.taken==false){
								//draw highlight
								g.drawImage(GamePanel.passiveEdgeHighlights[0][0],i*50,j*50,50,50,null);
							}
						}
						else{
							g.drawImage(GamePanel.passiveEdgeHighlights[0][0],i*50,j*50,50,50,null);
						}
						if(east!=null){
							if(east.taken==false){
								//draw highlight
								g.drawImage(GamePanel.passiveEdgeHighlights[1][0],i*50,j*50,50,50,null);
							}
						}
						else{
							g.drawImage(GamePanel.passiveEdgeHighlights[1][0],i*50,j*50,50,50,null);
						}
						if(south!=null){
							if(south.taken==false){
								//draw highlight
								g.drawImage(GamePanel.passiveEdgeHighlights[2][0],i*50,j*50,50,50,null);
							}
						}
						else{
							g.drawImage(GamePanel.passiveEdgeHighlights[2][0],i*50,j*50,50,50,null);
						}
						if(west!=null){
							if(west.taken==false){
								//draw highlight
								g.drawImage(GamePanel.passiveEdgeHighlights[3][0],i*50,j*50,50,50,null);
							}
						}
						else{
							g.drawImage(GamePanel.passiveEdgeHighlights[3][0],i*50,j*50,50,50,null);
						}
					}
				}
			}
		}
	}
}
