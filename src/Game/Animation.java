package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JPanel;

public class Animation{
	BufferedImage[]spriteSheet;

	int frameCount=0;
	int xpos =0;
	int ypos =0;
	int framesDrawn = 0;
	int xmove;
	int ymove;
	int moveRate;
	int delay = 0;
	private int currentFrame = 0;
	int frameWidth = 0;
	int frameHeight = 0;
	private long timeForNextFrame=System.currentTimeMillis();
	private long LastFrameTime = System.currentTimeMillis();
	boolean motionBlur = false;

	boolean loopAnim = false;
	public Animation(BufferedImage[][] sprites, int numFrames, int frameDelay,int CutWidth, int spriteIndex, int x, int y, boolean loop, int xIncreasePerFrame, int yIncreasePerFrame){
		xmove = xIncreasePerFrame;
		ymove = yIncreasePerFrame;
		moveRate = frameDelay;
		spriteSheet=sprites[spriteIndex];
		timeForNextFrame=System.currentTimeMillis()+frameDelay;
		frameWidth=CutWidth;
		frameHeight = CutWidth;
		frameCount=numFrames;
		delay=frameDelay;
		xpos=x;
		ypos=y;
		loopAnim=loop;
		//LastFrameTime=System.currentTimeMillis();
	}

	public void update(){
		//if it is time for the next frame
		if(this.timeForNextFrame <= System.currentTimeMillis()){
			this.currentFrame++;
			ypos=ypos+xmove;
			xpos=xpos+ymove;

			//animation is a loop
			if((loopAnim&&currentFrame==this.frameCount-1)||currentFrame>=spriteSheet.length-1){
				this.currentFrame=0;
			}

			framesDrawn++;

			timeForNextFrame = LastFrameTime + delay;
		}
	}
	public void setCalledBy(Object caller, int frame){

	}

	public int getCurrentFrame(){
		return this.currentFrame;
	}
	public int getFrameCount(){
		return this.frameCount;
	}
	public void Draw(Graphics g){

		this.update();
		//System.out.println("FrameCount="+this.frameCount);

		//if(this.LastFrameTime+delay >= System.currentTimeMillis()){
		if(this.currentFrame<this.frameCount||loopAnim){
			if(motionBlur){
				
				if(currentFrame>1){
					//g.drawImage(spriteSheet[currentFrame-2], xpos-(ymove*2), ypos-(xmove*2), frameWidth, frameHeight, null);
				}
				if(currentFrame>0){
					//g.drawImage(spriteSheet[currentFrame-1], xpos-ymove, ypos-xmove, frameWidth, frameHeight, null);
				}
			}

			g.drawImage(spriteSheet[currentFrame], xpos, ypos, frameWidth, frameHeight, null);

			this.LastFrameTime=System.currentTimeMillis();
		}
		else{

		}
		//}

	}
}
