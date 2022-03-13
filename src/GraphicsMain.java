// Class: GraphicsMain
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class contains the main method for this project. You shouldn't modify this class.
//              This class must be selected when you run your project.
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javazoom.jl.player.Player;


public class GraphicsMain extends JFrame{
	//method: main
	//parameters: String[] args
	//return type: void
	//description: This is the main method of this video game project. It runs the game along with the music.
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						Player player = new Player(getClass().getResource("sounds/Videogame.mp3").openStream());
						player.play();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		GraphicsMain window = new GraphicsMain();
		JPanel p = new JPanel();


		p.add(new GraphicsPanel());  
		window.setTitle("Cowabungus Sus Moo 2k Bungo 22 v6.9 New Wave Computers Main");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(p);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

}
