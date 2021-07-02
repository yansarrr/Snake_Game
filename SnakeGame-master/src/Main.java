import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		//Create a window for the game using JFrame and set properties such as boundaries, background color, etc.

		JFrame frame = new JFrame();
		Setup setup = new Setup();
		
		frame.setBounds(10, 10, 905, 700);
		frame.setBackground(Color.PINK);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Add the object of setup to the object of JFrame
		frame.add(setup);
		
	}

}
