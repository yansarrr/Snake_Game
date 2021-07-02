import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Implement panel and add panel inside the frame
public class Setup extends JPanel implements KeyListener, ActionListener{

	private int[] snakeXlength = new int[750];
	private int[] snakeYlength = new int[750];
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	private ImageIcon rightmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	private ImageIcon leftmouth;
	
	private int lengthOfSnake = 3;
	
	//Need to add a timer class to control the speed of the snake in the panel
	//This fires an action event once per second
	private Timer timer;
	private int delay = 100;
	private ImageIcon snakeimage;
	
	
	//random position for enemy
	private int[] enemyXposition = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int[] enemyYposition = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	private ImageIcon enemyimage;
	private Random random = new Random();
	//generate random position for enemy
	private int Xpos = random.nextInt(34);
	private int Ypos = random.nextInt(23);
	
	//score
	private int score = 0;
	
	private int moves = 0;
	
	private ImageIcon titleImage;




	public Setup() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	
	
	
	//Use the built-in Java Graphics class to draw everything in the panel
	public void paint (Graphics g) {
		//When game starts, set moves to 0, which shows the snake's default position
		if (moves == 0) {
			 snakeXlength[2] = 50;
			 snakeXlength[1] = 75;
			 snakeXlength[0] = 100;
			 
			 snakeYlength[2] = 100;
			 snakeYlength[1] = 100;
			 snakeYlength[0] = 100;
			 
		}
		
		//draw title image border
		g.setColor(Color.PINK);
		g.drawRect(24, 10, 851, 55);
		
		//draw title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw setup border
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		
		//draw background for setup
		g.setColor(Color.DARK_GRAY);
		g.fillRect(25, 75, 850, 575);
		
		//draw score 
		g.setColor(Color.orange);
		g.setFont(new Font("arial", Font.PLAIN, 19));
		g.drawString("Score: " + score, 780, 30);
		
		//draw length of snake
		g.setColor(Color.orange);
		g.setFont(new Font("arial", Font.PLAIN, 19));
		g.drawString("Length: " + lengthOfSnake, 780, 55);
		
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
		
		
		for (int a = 0; a < lengthOfSnake; a++) {
			//The 0 index is the head of the snake, so draw it’s right side orientation when right key is pressed
			if (a == 0 && right) {
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			
			if (a == 0 && left) {
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			
			if (a == 0 && down) {
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			
			if (a == 0 && up) {
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
			//If a is not 0, the head has been drawn and so the body now needs to be drawn
			if (a != 0) {
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
			}
		}
		
		//draw enemy
		enemyimage = new ImageIcon("enemy.png");
		enemyimage.paintIcon(this, g, enemyXposition[Xpos], enemyYposition[Ypos]);

		//increment snake length if snake head [0] touches the enemy's random position
		//afterwards, generate enemy's random position again
		if(enemyXposition[Xpos] == snakeXlength[0] && enemyYposition[Ypos] == snakeYlength[0]) {
			lengthOfSnake++;
			score++;
			Xpos = random.nextInt(34);
			Ypos = random.nextInt(23);
		}
		
		//There's a collision if snake's head has the same X and Y coordinates as it's body
		for (int b = 1; b < lengthOfSnake; b++) {
			if(snakeXlength[b] == snakeXlength[0] && snakeYlength[b] == snakeYlength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD, 40));
				g.drawString("Game over", 300, 300);
			}
		}
		
		g.dispose();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		timer.start();
		//We have an array, inside the first index the head is stored, all other indices contain the body of snake.
		//So how can the body follow the head?
		//Use a loop, shift the position of head to the next index, then the body will follow, then the end of the snake body is shifted 25 pixels to make up for the forward movement

		if (right) {
			for (int r = lengthOfSnake - 1; r >= 0; r--) {
				snakeYlength[r+1] = snakeYlength[r];
			}
			for (int r = lengthOfSnake; r >= 0; r--) {
				if (r == 0) {
					snakeXlength[r] = snakeXlength[r] + 25;
				} else {
					snakeXlength[r] = snakeXlength[r-1];
				}
				//check if snake touches the right border, if so snake comes out from the left 
				if(snakeXlength[r] > 850) {
					snakeXlength[r] = 25;
				}		
			}
			repaint();
		}
		
		
		if (left) {
			for (int r = lengthOfSnake - 1; r >= 0; r--) {
				snakeYlength[r+1] = snakeYlength[r];
			}
			for (int r = lengthOfSnake; r >= 0; r--) {
				if (r == 0) {
					snakeXlength[r] = snakeXlength[r] - 25;
				} else {
					snakeXlength[r] = snakeXlength[r-1];
				}
				//check if snake touches the left border, if so snake comes out from the right 
				if(snakeXlength[r] < 25) {
					snakeXlength[r] = 850;
				}		
			}
			repaint();
		}
		
		
		if (up) {
			for (int r = lengthOfSnake - 1; r >= 0; r--) {
				snakeXlength[r+1] = snakeXlength[r];
			}
			for (int r = lengthOfSnake; r >= 0; r--) {
				if (r == 0) {
					snakeYlength[r] = snakeYlength[r] - 25;
				} else {
					snakeYlength[r] = snakeYlength[r-1];
				}
				//check if snake touches the top border, if so snake comes out from the bottom 
				if(snakeYlength[r] < 75) {
					snakeYlength[r] = 625;
				}		
			}
			repaint();
		}
		
		
		if (down) {
			for (int r = lengthOfSnake - 1; r >= 0; r--) {
				snakeXlength[r+1] = snakeXlength[r];
			}
			for (int r = lengthOfSnake; r >= 0; r--) {
				if (r == 0) {
					snakeYlength[r] = snakeYlength[r] + 25;
				} else {
					snakeYlength[r] = snakeYlength[r-1];
				}
				//check if snake touches the bottom border, if so snake comes out from the top border 
				if(snakeYlength[r] > 625) {
					snakeYlength[r] = 75;
				}		
			}
			repaint();
		}
	}
	




	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//Increment moves so the default position of snake will disappear
			moves++;
			right = true;
			if (!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}
			down = false;
			up = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			left = true;
			if (!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}
			down = false;
			up = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			up = true;
			if (!down) {
				up = true;
			} else {
				up = false;
				down = true;
			}
			left = false;
			right = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			down = true;
			if (!up) {
				down = true;
			} else {
				down = false;
				up = true;
			}
			left = false;
			right = false;
		}
	}





	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}