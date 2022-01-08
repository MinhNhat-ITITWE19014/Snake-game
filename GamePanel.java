package Snake;

import java.awt.Component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 40; 
	static final int BODY_SIZE = 30; 
	static final int BONUS_SIZE = 40;
	static final int APPLE_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 150;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 4;
	int applesEaten = 0;
	int bonusEaten;
	int boomEaten;
	int bonus;
	int boomX;
	int boomY;
	int bonusX;
	int bonusY;
	int appleX;
	int appleY;
	int score = 0;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApple();
		if( (score != 0) &&  (score % 5 == 0)) {
			newBonus();
	}
		
		if (score >= 10) {
		newBoom();	
}
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	
	public void draw(Graphics g) {
		if (score >= 10) {
			g.setColor(new Color(131,68,26));
			g.fillOval(boomX, boomY, UNIT_SIZE, UNIT_SIZE);}
		
		if( (score != 0) &&  (score % 5 == 0)) {
			g.setColor(Color.yellow);
			g.fillOval(bonusX, bonusY, UNIT_SIZE, UNIT_SIZE);}
		
		if(running) {
					
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, APPLE_SIZE, APPLE_SIZE);
			
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], BODY_SIZE, BODY_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0));
					
					g.fillRect(x[i], y[i], BODY_SIZE, BODY_SIZE);
				}
				
			
			g.setColor(new Color(6,238,255));
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+score))/2, g.getFont().getSize());
			}
		}
		else {
			gameOver(g);
		}
		if((x[0] == boomX) && (y[0] == boomY)) {
			gameOver(g);
			}
		}
	
	public void newApple(){
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}

	public void newBonus(){
        bonusX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	    bonusY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void newBoom(){
        boomX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	    boomY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move(){
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			score++;
			if (score >= 10) {
			newBoom();
			}
			if( (score != 0) &&  (score % 5 == 0)) {
				newBonus();
		}
			
			else {
				newApple();
			}
		}
		}
	
	public void checkBonus() {
		if((x[0] == bonusX) && (y[0] == bonusY)) {
			bonusEaten++;
			score=score+2; 
			newApple();
			
			if (score >= 10) {
			newBoom();	
	}
	}
	}
	
	public void checkCollisions() {
	
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		if((x[0] == boomX) && (y[0] == boomY)) {
			running = false;
		}
		
		if(x[0] < 0) {
			running = false;
		}
	
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
	
		if(y[0] < 0) {
			running = false;
		}
	
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
	
		
		if(!running) {
			timer.stop();
		}
		}
	}
	public void gameOver(Graphics g) {
	
		g.setColor(new Color(6,238,255));
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+score, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+score))/2, g.getFont().getSize());
		
		g.setColor(new Color(6,238,255));
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkBonus();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}