package Game;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class Controller implements KeyListener,MouseListener,MouseMotionListener,MouseWheelListener,DragSourceListener{
	private JPanel gamePanel;
	static boolean mousePressed = false;
	static Point mousePosition = new Point(0,0);
	static Point oldMousePosition = new Point(0,0);
	boolean overSlot = false;

	public void setGamePanel(JPanel panelRef) {
		gamePanel = panelRef;
		gamePanel.addKeyListener(this);
		gamePanel.addMouseListener(this);
		gamePanel.addMouseMotionListener(this);
		gamePanel.addMouseWheelListener(this);
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()<0){//mouse wheel moved up (zoom in)
			if(GamePanel.levels.get(GamePanel.currentLevel).map.zoom<1){
				GamePanel.levels.get(GamePanel.currentLevel).map.zoom+=.125;
			}
		}
		else{//mouse wheel moved down(zoom out)
			if(GamePanel.levels.get(GamePanel.currentLevel).map.zoom>.125){
				GamePanel.levels.get(GamePanel.currentLevel).map.zoom-=.125;
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		mousePosition.x = (int) e.getPoint().getX();
		mousePosition.y = (int) e.getPoint().getY();

	}

	public void mouseMoved(MouseEvent e) {
		mousePosition.x = (int) e.getPoint().getX();
		mousePosition.y = (int) e.getPoint().getY();
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mousePressed = true;

		mousePosition.x = (int) e.getPoint().getX();
		mousePosition.y = (int) e.getPoint().getY();


		if(e.getButton()==MouseEvent.BUTTON3&&GamePanel.showMap&&GamePanel.godmode){
			LevelMap map = GamePanel.levels.get(GamePanel.currentLevel).map;
			int x = (int)(double)(((mousePosition.x-(map.xpos*map.zoom))*(map.pixelWidthPerTile/map.zoom)/2));
			int y = (int)(double)(((mousePosition.y-(map.ypos*map.zoom))*(map.pixelWidthPerTile/map.zoom)/2));
			GamePanel.player.setPosition(x,y,2);
			GamePanel.player.destination = new Point(x,y);
		}

		// right clicking over a tile with an item over it will pick it up
		if(e.getButton()==MouseEvent.BUTTON3&&!GamePanel.showMap&&!GamePanel.paused&&!GamePanel.inInventory){

			int x = 0;
			int y = 0;

			if (GamePanel.levels.size()>GamePanel.currentLevel){

				//determines tile location based on player position and window width and height
				x = (mousePosition.x + (int)(GamePanel.player.xpos+16) - (ApplicationUI.windowWidth/2))/32;
				y = (mousePosition.y + (int)(GamePanel.player.ypos+16) - (ApplicationUI.windowHeight/2))/32;


				if (x>=0&&y>=0&&x<GamePanel.levels.get(GamePanel.currentLevel).width&&y<GamePanel.levels.get(GamePanel.currentLevel).height){

					//if this tile item arraylist isnt empty
					if (GamePanel.levels.get(GamePanel.currentLevel).tileMap[x][y].itemsOnThisTile.size()>0){

						//get first item of the arraylist
						Item temp = GamePanel.levels.get(GamePanel.currentLevel).tileMap[x][y].itemsOnThisTile.get(0);

						//call method in inventory to pick up item
						if (GamePanel.player.inventory.pickUpItem(temp)){
							GamePanel.levels.get(GamePanel.currentLevel).tileMap[x][y].itemsOnThisTile.remove(0);
						}
					}
				}
			}
		}

		//menu stuff here
		for (int i = 0; i < GamePanel.menu.currentMenu.size(); i++) {
			if (GamePanel.menu.currentMenu.get(i).isOver()){
				GamePanel.menu.currentMenu.get(i).isPushed();
			}
		}

		if (GamePanel.inInventory) {

			overSlot = false;

			//head slot
			if (GamePanel.player.inventory.head.isOver()){
				if (GamePanel.player.inventory.currentHeldItem==null||GamePanel.player.inventory.currentHeldItem.type.equals("helmet")){
					overSlot = true;
					GamePanel.player.inventory.head.isPushed();
				}
			}




			//equipped items
			for (int i = 0; i < GamePanel.player.inventory.equipped.length; i++) {
				if (GamePanel.player.inventory.equipped[i].isOver()) {
					GamePanel.player.inventory.equipped[i].isPushed();
					overSlot = true;
				}

			}

			//main inventory items
			for (int i = 0; i<GamePanel.player.inventory.main.length; i++) {
				for (int j = 0; j<GamePanel.player.inventory.main[i].length; j++){
					if (GamePanel.player.inventory.main[i][j].isOver()) {
						GamePanel.player.inventory.main[i][j].isPushed();
						overSlot = true;
					}

				}
			}



			if (GamePanel.player.inventory.torso.isOver()) {
				if (GamePanel.player.inventory.currentHeldItem==null||GamePanel.player.inventory.currentHeldItem.type.equals("torso")){
					GamePanel.player.inventory.torso.isPushed();
					overSlot = true;
				}

			}

			if (GamePanel.player.inventory.arms.isOver()) {
				if (GamePanel.player.inventory.currentHeldItem==null||GamePanel.player.inventory.currentHeldItem.type.equals("arms")){
					GamePanel.player.inventory.arms.isPushed();
					overSlot = true;
				}

			}

			if (GamePanel.player.inventory.legs.isOver()) {
				if (GamePanel.player.inventory.currentHeldItem==null||GamePanel.player.inventory.currentHeldItem.type.equals("legs")){
					GamePanel.player.inventory.legs.isPushed();
					overSlot = true;
				}

			}


			if (!overSlot) {
				GamePanel.player.inventory.dropFromInventory();
			}

		}

	}

	public void mouseReleased(MouseEvent e) {

		mousePressed = false;


	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_M){
			if(GamePanel.showMap){
				GamePanel.showMap=false;
			}
			else{
				GamePanel.showMap=true;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_G){
			if(GamePanel.godmode){
				GamePanel.godmode=false;
			}
			else{
				GamePanel.godmode=true;
			}
		}
		if(GamePanel.godmode){
			if(e.getKeyCode()==KeyEvent.VK_1){
				if(GamePanel.player.movementSpeed>0){
					GamePanel.player.movementSpeed-=.05;
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_2){

				GamePanel.player.movementSpeed+=.05;

			}
			if(e.getKeyCode()==KeyEvent.VK_3){
				if(GamePanel.currentLevel>0){
					GamePanel.currentLevel--;
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_4){
				if(GamePanel.currentLevel<GamePanel.levels.size()-1){
					GamePanel.currentLevel++;
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_5){
				GamePanel.dialog.addMessage("Added a message to dialog! Does it work?"+System.currentTimeMillis());

			}
			if(e.getKeyCode()==KeyEvent.VK_UP){

				if(!GamePanel.loading){
					GamePanel.levels.get(GamePanel.currentLevel).seed++;
					GamePanel.levels.get(GamePanel.currentLevel).generateMap();
					GamePanel.levels.get(GamePanel.currentLevel).updateTileMapArt();
					GamePanel.levels.get(GamePanel.currentLevel).map = new LevelMap(GamePanel.levels.get(GamePanel.currentLevel).tileMap);
					GamePanel.player.destination.x=(int)GamePanel.player.xpos;
					GamePanel.player.destination.y=(int)GamePanel.player.ypos;
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				if(!GamePanel.loading){
					GamePanel.levels.get(GamePanel.currentLevel).seed--;
					GamePanel.levels.get(GamePanel.currentLevel).generateMap();
					GamePanel.levels.get(GamePanel.currentLevel).updateTileMapArt();
					GamePanel.levels.get(GamePanel.currentLevel).map = new LevelMap(GamePanel.levels.get(GamePanel.currentLevel).tileMap);
					GamePanel.player.destination.x=(int)GamePanel.player.xpos;
					GamePanel.player.destination.y=(int)GamePanel.player.ypos;
				}

			}
			if(e.getKeyCode()== KeyEvent.VK_W){
				if(GamePanel.levels.get(GamePanel.currentLevel).weatherID < 1)
					GamePanel.levels.get(GamePanel.currentLevel).weatherID++;
				else GamePanel.levels.get(GamePanel.currentLevel).weatherID = -1;


			}
			if(e.getKeyCode() == KeyEvent.VK_H)
			{
				GamePanel.bat.getPlayer().setCurrHP(1000);
			}

			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(0);
		}
		if (e.getKeyCode()==KeyEvent.VK_P){//
			if(GamePanel.paused){
				GamePanel.paused=false;
			}
			else{
				GamePanel.inInventory=false;
				GamePanel.paused=true;
				GamePanel.menu.currentMenu = GamePanel.menu.main;
			}

		}//
		if (e.getKeyCode()==KeyEvent.VK_I) {
			if (GamePanel.inInventory){
				GamePanel.inInventory=false;
			}
			else{
				GamePanel.paused=false; //so you can't be in inventory and paused at the same time
				GamePanel.inInventory=true;
			}
		}


		//debug into a battle
		if(e.getKeyCode() == KeyEvent.VK_B)
		{
			if(GamePanel.inBattle)
			{
				GamePanel.inBattle = false;
			}
			else
			{
				GamePanel.inBattle = true;
				GamePanel.bat = new Battle(1.0);
			}
		}


		if(GamePanel.inBattle)
		{
			//			if(e.getKeyCode() == KeyEvent.VK_E)
			//			{
			//				GamePanel.bat.attack(GamePanel.bat.getEnemies().get(0), GamePanel.bat.getPlayer());
			//			}
			if(e.getKeyCode() == KeyEvent.VK_1)
			{
				GamePanel.bat.attack(GamePanel.bat.getPlayer(),GamePanel.bat.getEnemies().get(0));
			}
			if(e.getKeyCode() == KeyEvent.VK_2)
			{
				GamePanel.bat.attack(GamePanel.bat.getPlayer(),GamePanel.bat.getEnemies().get(1));
			}
			if(e.getKeyCode() == KeyEvent.VK_3)
			{
				GamePanel.bat.attack(GamePanel.bat.getPlayer(),GamePanel.bat.getEnemies().get(2));
			}
			if(e.getKeyCode() == KeyEvent.VK_D)
			{
				GamePanel.bat.getEnemies().get(0).setCurrHP(GamePanel.bat.getEnemies().get(0).getCurrHP() - 2);
			}



		}

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {


	}
	/*
	 * Constantly checks for changes in key state by being called by the game loop in applicationUI
	 */
	public static void checkKeys(){

		//check if mouse was pressed
		if(mousePressed){
			//Point temp = MouseEvent.getPoint();
			if(mousePressed && !GamePanel.inBattle){
				if(GamePanel.showMap==false){
					if (!GamePanel.paused){
						GamePanel.player.destination.x=(int)GamePanel.player.xpos+mousePosition.x-((ApplicationUI.windowWidth/2)-16);
						GamePanel.player.destination.y=(int)GamePanel.player.ypos+mousePosition.y-((ApplicationUI.windowHeight/2)-16);
					}
				}
				else{
					int changeX = oldMousePosition.x-mousePosition.x;
					int changeY = oldMousePosition.y-mousePosition.y;
					GamePanel.levels.get(GamePanel.currentLevel).map.xpos-=changeX/GamePanel.levels.get(GamePanel.currentLevel).map.zoom;
					GamePanel.levels.get(GamePanel.currentLevel).map.ypos-=changeY/GamePanel.levels.get(GamePanel.currentLevel).map.zoom;
				}
			}
			//System.out.println("destination: "+GamePanel.player.destination.x+","+GamePanel.player.destination.y);
		}
		oldMousePosition.x = mousePosition.x;
		oldMousePosition.y = mousePosition.y;
	}
	public void updateAll(){
		if (gamePanel != null)
			gamePanel.getParent().repaint();
	}
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void dragEnter(DragSourceDragEvent e) {
		// TODO Auto-generated method stub

	}
	public void dragExit(DragSourceEvent e) {
		// TODO Auto-generated method stub

	}
	public void dragOver(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}



}
