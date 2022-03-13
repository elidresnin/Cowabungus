// Class: Background
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This is an abstract class that provides partial implementation for a Background. You can't
// 				create an instance of this class.
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class Background {

	protected ImageIcon image;
	protected int scale;
	protected int x;
	protected int moveCount;

	
	//constructors
	public Background(int x) {
		moveCount = 0;
		ClassLoader cldr = this.getClass().getClassLoader();	// These five lines of code load the background picture.
		String imagePath = "images/background/Oltman, Daniel-1-03.png";	// Change this line if you want to use a different 
		URL imageURL = cldr.getResource(imagePath);				// background image.  The image should be saved in the
		scale = 9;

		image = new ImageIcon(imageURL);

		Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / scale, 
				image.getIconHeight() / scale, Image.SCALE_SMOOTH);

		image = new ImageIcon(scaled);


		this.x = x;
	}

	public Background() {
		this(0);
	}

	//method: draw
	//parameters: Component c, Graphics g
	//return type: void
	//description: This method draws the backgrounds at the beginning of the game.
	public void draw(Component c, Graphics g) {
		image.paintIcon(c, g, x, 0);
	}

	//method: getHeight
	//parameters: none
	//return type: int
	//description: This method returns the height of the background.
	public int getHeight() {
		return image.getIconHeight();
	}

	//method: getWidth
	//parameters: none
	//return type: int
	//description: This method returns the width of the background.
	public int getWidth() {
		return image.getIconWidth();
	}

	//method: getX
	//parameters: none
	//return type: int
	//description: This method returns the x value of the background.
	public int getX() {
		return x;
	}

	//method: move
	//parameters: double speedModifier
	//return type: void
	//description: This method moves the background at adjusted speeds based on the value of speedModifier.
	public void move(double speedModifier) {
		if(getX()<=image.getIconWidth()) {
			x -= speedModifier;	
			moveCount++;
		}
		if (x < 0-image.getIconWidth()) {
			reset();
		}

	}

	//method: reset
	//parameters: none
	//return type: void
	//description: This method resets the x value of the background so that it loops seamlessly.
	public void reset() {
		x = image.getIconWidth();
	}

	//method: getImage
	//parameters: none
	//return type: ImageIcon
	//description: This method simply returns the image being used for the background.
	public ImageIcon getImage() {
		return image;
	}

}
