// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Rectangle;

public class GraphicsPanel extends JPanel implements KeyListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	private Background background1;			// The background object will display a picture in the background.
	private Background background2;	
	// There has to be two background objects for scrolling.

	private Sprite sprite;					// create a Sprite object
	private ArrayList<Grill> grillList = new ArrayList<Grill>();
	private ArrayList<Carrot> carrotList = new ArrayList<Carrot>();
	private ArrayList<Haybale> haybaleList = new ArrayList<Haybale>();
	private ArrayList<Heart> heartList = new ArrayList<Heart>();
	private double tickCount;
	private int boxRowSpawn;
	private int carrotRowSpawn;
	private int haybaleRowSpawn;
	private int score;
	private int powerUpRandom;
	private int speedModifier;
	private boolean slowDown;
	private int slowDownVariable;
	private int gameState;
	private int startSelection;
	private int exitSelection;
	private int scoreMultiplier;


	//constructors
	public GraphicsPanel(){
		background1 = new Background();	
		background2 = new Background(background1.getImage().getIconWidth());
		slowDownVariable = 0;
		scoreMultiplier = 1000;
		gameState = 1;
		startSelection = 1;
		exitSelection = 1;
		for (int i = 0; i < 3; i++) {
			heartList.add(new Heart((68*i),0,"images/objects/heart.png", 9));
		}
		tickCount = 0;
		score = 0;
		speedModifier = 1;
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.

		sprite = new Sprite(-350, 250);			
		// The Sprite constuctor has two parameter - - the x coordinate and y coordinate

		setPreferredSize(new Dimension(background1.getImage().getIconWidth(),
				background2.getImage().getIconHeight()));  
		// This line of code sets the dimension of the panel equal to the dimensions
		// of the background image.

		timer = new Timer(5, new ClockListener(this));   // This object will call the ClockListener's
		// action performed method every 5 milliseconds once the 
		// timer is started. You can change how frequently this
		// method is called by changing the first parameter.
		timer.start();
		this.setFocusable(true);					     // for keylistener
		this.addKeyListener(this);
	}

	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint(). You'll want to draw each of your objects.
	//				This is the only place that you can draw objects. 
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	
	//method: paintComponent
	//parameters: Graphics g
	//return type: void
	//description: This method draws everything in this game from the titlescreen to the hearts to the sprite itself.
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		switch (gameState) {
		case 1:
			Titlescreen startScreen1 = new Titlescreen("images/background/StartScreen1 (1).png", 2);
			startScreen1.draw(g2,this);
			break;
		case 2:
			background1.draw(this, g);
			background2.draw(this, g);
			sprite.draw(g2, this);
			for (Grill b: grillList) {
				b.draw(g2, this);
			}

			for (Carrot f: carrotList) {
				f.draw(g2, this);
			}

			for (Haybale c: haybaleList) {
				c.draw(g2, this);
			}

			for (Heart q: heartList) {
				q.draw(g2, this);
			}

			g2.setColor(Color.GREEN);
			g2.setFont(new Font("Arial", Font.BOLD, 30));
			g2.drawString("Score: " + score,600,30);
			if (tickCount % 5850 >= 0 && tickCount % 5850 <= 300 && tickCount > 300) {
				g2.setColor(Color.RED);
				g2.setFont(new Font("Arial", Font.BOLD, 30));
				g2.drawString("Speed increasing!",300,300);
				g2.setFont(new Font("Arial", Font.BOLD, 10));
				g2.drawString("celeritas", 300, 350);
			}
			if (slowDown == true) {
				g2.setColor(Color.RED);
				g2.setFont(new Font("Arial", Font.BOLD, 30));
				g2.drawString("Slowing down!", 300, 300);
				slowDownVariable++;
				if (slowDownVariable == 100) {
					slowDown = false;
					slowDownVariable = 0;
				}
			}
			break;
		case 3:
			Titlescreen exitScreen1 = new Titlescreen("images/background/cowabungaasEndScreen.png", 2);
			exitScreen1.draw(g2,this);
			break;
		}
	}

	// method:clock
	// description: This method is called by the clocklistener every 5 milliseconds.  You should update the coordinates
	//				of one of your characters in this method so that it moves as time changes.  After you update the
	//				coordinates you should repaint the panel. 
	
	
	//method: clock
	//parameters: none
	//return type: void
	//description: This method is called and runs every 5 milliseconds. This method randomizes and adds grills, haybales, 
    // 	           and carrots to each of their respective Arraylists. It also figures out when the sprite runs into these 
    //             objects along with when any of these items go off the screen so that the game can delete them to save memory.
	//             On top of all of that it also repaints the screen and counts once every five milliseconds.
	public void clock(){
		if (gameState == 2) {
			boxRowSpawn = (int) (Math.random()*3)+1;
			haybaleRowSpawn = (int) (Math.random()*3)+1;
			carrotRowSpawn = (int) (Math.random()*3)+1;
			if (tickCount % (300/speedModifier) == 0) {
				switch(boxRowSpawn) {
				case 1:
					grillList.add(new Grill(1500, 330, "images/objects/Grill for Project-02 (1).png",2)); //1500, 340
					break;
				case 2:
					grillList.add(new Grill(1500, 420, "images/objects/Grill for Project-02 (1).png", 2)); //1500, 430
					break;
				case 3:
					grillList.add(new Grill(1500, 250, "images/objects/Grill for Project-02 (1).png", 2)); //1500, 255
					break;
				}
				switch(carrotRowSpawn) {
				case 1:
					carrotList.add(new Carrot(1350,340, "images/objects/Carrot (2) (1) (1)-01 copy.png", 6));
					break;
				case 2:
					carrotList.add(new Carrot(1350,430, "images/objects/Carrot (2) (1) (1)-01 copy.png", 6));
					break;
				case 3:
					carrotList.add(new Carrot(1350,250, "images/objects/Carrot (2) (1) (1)-01 copy.png", 6));
					break;
				}
				if (tickCount % 6000 == 0) {
					speedModifier++;
					switch(haybaleRowSpawn) {
					case 1:
						haybaleList.add(new Haybale(1675,330, "images/objects/Hay Bale for Project-01.png", 50));
						break;
					case 2:
						haybaleList.add(new Haybale(1675,420, "images/objects/Hay Bale for Project-01.png", 50));
						break;
					case 3:
						haybaleList.add(new Haybale(1675,245, "images/objects/Hay Bale for Project-01.png", 50));
						break;
					}
				}
			}
			// You can move any of your objects by calling their move methods.
			sprite.move(this);
			background1.move(speedModifier);
			background2.move(speedModifier);
			for (int i = grillList.size()-1; i >= 0; i--) {
				grillList.get(i).move(speedModifier);
				if (grillList.get(i).getX() < -100) {
					grillList.remove(i); //Change for to a normal for loop.
				}
			}

			for (int i = carrotList.size()-1; i >= 0; i--) {
				carrotList.get(i).move(speedModifier);
				if (carrotList.get(i).getX() < -50) {
					carrotList.remove(i); //Change for to a normal for loop.
				}
			}

			for (int i = haybaleList.size()-1; i >= 0; i--) {
				haybaleList.get(i).move(speedModifier);
				if (haybaleList.get(i).getX() < -100) {
					haybaleList.remove(i); //Change for to a normal for loop.
				}
			}




			// You can also check to see if two objects intersect like this. In this case if the sprite collides with the
			// item, the item will get smaller. 
			for(int i = grillList.size()-1; i >= 0; i--) {
				if(sprite.collision(grillList.get(i)) && sprite.getY() < grillList.get(i).getY()) {
					grillList.remove(i);
					heartList.remove(heartList.size()-1);
					playSound("src/sounds/splat.WAV");
					if (heartList.size() == 0) {
						gameState = 3;
						sprite.setCoords(-350,250);
						for (int x = haybaleList.size()-1; x >= 0; x--) 
							haybaleList.remove(x);
						for (int x = carrotList.size()-1; x >= 0; x--) 
							carrotList.remove(x);
						for (int x = grillList.size()-1; x >= 0; x--) 
							grillList.remove(x);
						for (int x = 0; x < 3; x++) 
							heartList.add(new Heart((68*x),0,"images/objects/heart.png", 9));
						speedModifier = 2;
						score = 0;
						slowDownVariable = 1;
						tickCount = 0;
					}
				}
			}

			for(int i = carrotList.size()-1; i >= 0; i--) {
				if(sprite.collision(carrotList.get(i)) && sprite.getY() < carrotList.get(i).getY()) {
					score += scoreMultiplier;
					carrotList.remove(i);
				}
			}

			for(int i = haybaleList.size()-1; i >= 0; i--) {
				if(sprite.collision(haybaleList.get(i)) && sprite.getY() < haybaleList.get(i).getY()) {
					haybaleList.remove(i);
					playSound("src/sounds/coin.WAV");
					powerUpRandom = (int) (Math.random() * 2) + 1;
					switch (powerUpRandom) {
					case 1:
						heartList.add(new Heart((68*heartList.size()),0,"images/objects/heart.png", 9));
						break;
					case 2:
						speedModifier--;
						slowDown = true;
						break;
//					case 3:
//						scoreMultiplierTimer = (int) (tickCount + 5000);
//						while(scoreMultiplierTimer > tickCount) {
//							scoreMultiplier = 2000;
//						}
//						scoreMultiplier = 1000;
//						break;
						
					}

				}
			}
			tickCount++;
		}
		this.repaint();
	}

	// method: keyPressed()
	// description: This method is called when a key is pressed. You can determine which key is pressed using the 
	//				KeyEvent object.  For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if
	//				the left key was pressed.
	// parameters: KeyEvent e
	@Override
	
	//method: keyPressed
	//parameters: KeyEvent e
	//return type: void
	//description: This method is called when any key is pressed and runs specific lines of code based on which key is pressed.
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			sprite.moveUp();
			if (gameState == 1)
				if (startSelection == 1)
					startSelection++;
				else
					startSelection--;
			if (gameState == 3)
				if (exitSelection == 1)
					exitSelection++;
				else
					exitSelection--;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			sprite.moveDown();
			if (gameState == 1)
				if (startSelection == 2) 
					startSelection--;
				else
					startSelection++;
			if (gameState == 3)
				if (exitSelection == 2)
					exitSelection--;
				else
					exitSelection++;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (gameState == 3) 
				gameState = 2;
			
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (gameState == 1)
				gameState = 2;
		}
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			heartList.add(new Heart((68*heartList.size()),0,"images/objects/heart.png", 9));
		}
	}

	// This function will play the sound "fileName".
	
	//method: playSound
	//parameters: String fileName
	//return type: void
	//description: This method plays the sound "fileName" which is the parameter of this method.
	public static void playSound(String fileName) {
		try {
			File url = new File(fileName);
			Clip clip = AudioSystem.getClip();

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.start();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// method: keyTyped()
	// description: This method is called when a key is pressed and released. It basically combines the keyPressed and
	//              keyReleased functions.  You can determine which key is typed using the KeyEvent object.  
	//				For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if the left key was typed.
	//				You probably don't want to do much in this method, but instead want to implement the keyPresses and keyReleased methods.
	// parameters: KeyEvent e
	@Override
	public void keyTyped(KeyEvent e) {

	}

	// method: keyReleased()
	// description: This method is called when a key is released. You can determine which key is released using the 
	//				KeyEvent object.  For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if
	//				the left key was pressed.
	// parameters: KeyEvent e
	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() ==  KeyEvent.VK_UP || e.getKeyCode() ==  KeyEvent.VK_DOWN)
			sprite.stop_Vertical();

	}

}
