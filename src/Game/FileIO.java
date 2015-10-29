package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileIO {
	/*
	 * Loads an image from an image file
	 * 
	 * @param imageName - the file path to the image
	 * 
	 * @return the image loaded
	 */
	public static BufferedImage loadImage(String imageName){
		BufferedImage image;
		try
		{
			image = ImageIO.read(Images.class.getResourceAsStream(imageName));
			BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = img.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = img;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return image;
	}
	public static BufferedImage combineImages(BufferedImage[][] imgs){
		BufferedImage result = new BufferedImage( (imgs.length)*imgs[0][0].getWidth(),(imgs[0].length)*imgs[0][0].getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		for(int x = 0; x<imgs.length;x++){
			for(int y = 0; y<imgs[0].length;y++){
				g.drawImage(imgs[x][y], x*imgs[0][0].getWidth(), y*imgs[0][0].getHeight(), imgs[0][0].getWidth(),imgs[0][0].getHeight(),null);
			}
		}
		g.dispose();
		return result;
	}
	public static BufferedImage colorImage(BufferedImage img, Color color){
		BufferedImage result = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x<img.getWidth();x++){
			for(int y = 0; y<img.getHeight();y++){
				Color colorAtThisPoint = new Color(img.getRGB(x, y),true);
				//pixel is not transparent
				if(colorAtThisPoint.getAlpha()>0){
					//System.out.println("a pixel was not transparent");
					//pixel is some shade of grey
					if(colorAtThisPoint.getRed()==colorAtThisPoint.getBlue()&&colorAtThisPoint.getRed()==colorAtThisPoint.getGreen()){
						//System.out.println("pixel was a shade of grey");
						int red = color.getRed()-(255-colorAtThisPoint.getRed());
						int green = color.getGreen()-(255-colorAtThisPoint.getGreen());
						int blue = color.getBlue()-(255-colorAtThisPoint.getBlue());
						if(red<0){
							red=0;
						}
						if(green<0){
							green=0;
						}
						if(blue<0){
							blue=0;
						}
						result.setRGB(x, y, new Color(red,green,blue,255).getRGB());
						
					}
					else{
						result.setRGB(x, y, colorAtThisPoint.getRGB());
					}
				}
				
			}
		}
		return result;
	}
	/*
	 * Loads a 2d array of images from an image file
	 * 
	 * @param imageName - the file path to the base image
	 * @param sliceWidth - the width of each subimage to be taken from the main image
	 * @param sliceHeight - the hieght of each subimage to be taken from the main image
	 * 
	 * @return A 2d array of bufferedImages
	 */
	public static BufferedImage[][] loadSpriteSheet(String imageName, int sliceWidth, int sliceHeight){
		//System.out.println("Starting image cut for "+imageName);
				long temp = System.currentTimeMillis();
				BufferedImage sheet;
				try
				{
					sheet = ImageIO.read(Images.class.getResourceAsStream(imageName));
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return null;
				}

				int w = sheet.getWidth();
				int h = sheet.getHeight();

				int xSlices = w/sliceWidth;
				int ySlices = h/sliceHeight;

				BufferedImage[][] images = new BufferedImage[xSlices][ySlices];
				for (int x=0; x<xSlices; x++)
					for (int y=0; y<ySlices; y++)
					{
						BufferedImage img = new BufferedImage(sliceWidth, sliceHeight, BufferedImage.TYPE_INT_ARGB);
						Graphics g = img.getGraphics();
						g.drawImage(sheet, -x*sliceWidth, -y*sliceHeight, null);
						g.dispose();
						images[x][y] = img;
					}

				//System.out.println("Done cutting image for "+imageName+". Total time spent on cut = "+(System.currentTimeMillis()-temp));
				return images;
	}
}
