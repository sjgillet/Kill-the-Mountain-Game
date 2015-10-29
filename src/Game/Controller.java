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
		//menu stuff here
		for (int i = 0; i < GamePanel.menu.currentMenu.size(); i++) {
			if (GamePanel.menu.currentMenu.get(i).isOver()){
				GamePanel.menu.currentMenu.get(i).isPushed();
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
			if(e.getKeyCode()==KeyEvent.VK_UP){
				GamePanel.levels.get(GamePanel.currentLevel).seed++;
				GamePanel.levels.get(GamePanel.currentLevel).generateMap();
				GamePanel.levels.get(GamePanel.currentLevel).updateTileMapArt();
				GamePanel.levels.get(GamePanel.currentLevel).map = new LevelMap(GamePanel.levels.get(GamePanel.currentLevel).tileMap);
				GamePanel.player.destination.x=(int)GamePanel.player.xpos;
				GamePanel.player.destination.y=(int)GamePanel.player.ypos;
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				GamePanel.levels.get(GamePanel.currentLevel).seed--;
				GamePanel.levels.get(GamePanel.currentLevel).generateMap();
				GamePanel.levels.get(GamePanel.currentLevel).updateTileMapArt();
				GamePanel.levels.get(GamePanel.currentLevel).map = new LevelMap(GamePanel.levels.get(GamePanel.currentLevel).tileMap);
				GamePanel.player.destination.x=(int)GamePanel.player.xpos;
				GamePanel.player.destination.y=(int)GamePanel.player.ypos;
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_P){//
			if(GamePanel.paused){
				GamePanel.paused=false;
			}
			else{
				GamePanel.paused=true;
			}
		}//
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
			if(mousePressed){
				if(GamePanel.showMap==false){
					GamePanel.player.destination.x=(int)GamePanel.player.xpos+mousePosition.x-((ApplicationUI.windowWidth/2)-16);
					GamePanel.player.destination.y=(int)GamePanel.player.ypos+mousePosition.y-((ApplicationUI.windowHeight/2)-16);
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
