//class:Titlescreen.java
//written by: Ryan Schaeffer
//date: Jan 23, 2022
//description: This is the class for the beginning titlescreen and is used to draw it.

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class Titlescreen {

	protected ImageIcon image;
	private int x_coordinate;
	private int y_coordinate;
	
	//constructors
	public Titlescreen(String imageString, int imageScale) {
		x_coordinate = 0;
		y_coordinate = -50;
		ClassLoader cldr = this.getClass().getClassLoader();	// These lines of code load the picture.
		String imagePath = imageString;							
		URL imageURL = cldr.getResource(imagePath);				

		image = new ImageIcon(imageURL);

		Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / imageScale, 
				image.getIconHeight() / imageScale, Image.SCALE_SMOOTH);

		image = new ImageIcon(scaled);		
	}
	
	
	//method: draw
	//parameters: Graphics g, Component c
	//return type: void
	//description: This method draws the titlescreen image at specific coordinates.
	public void draw(Graphics g, Component c) {
		image.paintIcon(c, g, x_coordinate, y_coordinate);
	}
}
