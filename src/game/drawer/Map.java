package game.drawer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

import game.display.Game;
import game.entities.Alien;
import game.entities.Bonus;
import game.entities.Missile;
import game.entities.Spaceship;
import game.transitions.Level;


public class Map extends JPanel implements ActionListener {

	private final int SPACESHIP_X = 220;
	private final int SPACESHIP_Y = 430;
	private final Timer timer_map;

	private final Image background;
	private Spaceship spaceship;
	private boolean inGame;
	private Level level = Level.EASY;
	private List<Alien> aliens;
	private Timer timer_aliens;
	private List<Bonus> bonus;
	private Timer timer_bonus;
	
	public Map() {

		addKeyListener(new KeyListerner());

		setFocusable(true);
		setDoubleBuffered(true);
		ImageIcon image = new ImageIcon("images/space.jpg");
		this.background = image.getImage();
		inGame = true;
		spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
		
		timer_map = new Timer(Game.getDelay(), this);
		timer_map.start();
		startAliens();
		newBonus();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.background, 0, 0, null);
		if (inGame) {
			draw(g);
		}
		else{
			drawGameOver(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void draw(Graphics g) {
		// Draw spaceship
		g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);

		// Draw Bullet
		List<Missile> missile = spaceship.getMissile();
		for (int i = 0; i < missile.size(); i++) {
			Missile m = (Missile) missile.get(i);
			g.drawImage(m.getImage(), m.getX(), m.getY(), this);
		}

		// Draw Alien
		for (int i = 0; i < aliens.size(); i++) {
			Alien a = aliens.get(i);
			g.drawImage(a.getImage(), a.getX(), a.getY(), this);
		}
		// Draw Bonus
		for (int i = 0; i < bonus.size(); i++) {
			Bonus b = bonus.get(i);
			g.drawImage(b.getImage(), b.getX(), b.getY(), this);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("HEALTH: " + spaceship.getHealth(), 5, 15);
		g.drawString(("SCORE: " + spaceship.getScore()), 5, 470);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkCollide();
		updateSpaceship();
		updateMissile();
		updateAlien();
		changeLevel();
		updateBonus();
		repaint();
	}

	private void drawMissionAccomplished(Graphics g) {
		String message = "MISSION ACCOMPLISHED";
		String message_score = "SCORE: ";
		//String message_Restart = "Restart Press enter.";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
		g.drawString(message_score + spaceship.getScore(), (((Game.getWidth() - metric.stringWidth(message_score)) / 2) - 20), (Game.getHeight() / 2) + 40);
		//g.drawString(message_Restart, ((Game.getWidth() - metric.stringWidth(message_Restart)) /2), Game.getHeight() - 60);
		updateAlien();
		updateMissile();
		updateBonus();
	}

	private void drawGameOver(Graphics g) {
		String message = "MISSION ACCOMPLISHED";
		String message_score = "SCORE: ";
		//String message_Restart = "Restart Press enter.";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
		g.drawString(message_score + spaceship.getScore(), (((Game.getWidth() - metric.stringWidth(message_score)) / 2) - 20), (Game.getHeight() / 2) + 40);
		//g.drawString(message_Restart, ((Game.getWidth() - metric.stringWidth(message_Restart)) /2), Game.getHeight() - 60);
		updateAlien();
		updateMissile();
		updateBonus();
	}
	public void newSpaceship() {
		this.spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
	}
	public void startAliens() {
		aliens = new ArrayList<Alien>();
		timer_aliens = new Timer(level.getAlienTime(), new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateAliens();
			}
		});
		timer_aliens.start();
	}
	private void newBonus() {
		bonus = new ArrayList<>();
		timer_bonus = new Timer(level.getBonusTime(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateBonus();
			}
		});
		timer_bonus.start();
	}

	public void nextLevel() {
		Optional<Level> next = level.next();
		level = next.get();
		timer_aliens.setDelay(level.getAlienTime());
		}
	public void changeLevel(){
		if(level == Level.EASY && spaceship.getScore() >= 5000 && spaceship.getScore() < 10000){
			nextLevel();
		}
		if(level == Level.MEDIUM && spaceship.getScore() >= 10000 && spaceship.getScore() < 20000){
			nextLevel();
		}
		if(level == Level.HARD && spaceship.getScore() >= 20000){
		}
			
	}
	public void restartScore(){
		spaceship.setScore(0);
		level = Level.EASY;
		
	}
	public void restartHealth(){
		spaceship.setLife(3);
	}
	public void checkCollide() {
		Rectangle spaceshipForm = spaceship.getBounds();
		Rectangle missileForm;
		Rectangle alienForm;
		Rectangle bonusForm;

		for (int i = 0; i < aliens.size(); i++) {
			Alien tempAlien = aliens.get(i);
			alienForm = tempAlien.getBounds();
			if (spaceshipForm.intersects(alienForm)) {
				if (spaceship.getHealth() == 1) {
					inGame = false;
				} else {
					spaceship.setLife(spaceship.getHealth() - 1);
					tempAlien.setVisible(false);
				}
			}
		}

		List<Missile> missile = spaceship.getMissile();
		for (int i = 0; i < missile.size(); i++) {
			Missile tempMissile = missile.get(i);
			missileForm = tempMissile.getBounds();
			
			for (int j = 0; j < aliens.size(); j++) {
				Alien tempAlien = aliens.get(j);
				alienForm = tempAlien.getBounds();
			
				if (missileForm.intersects(alienForm)) {					
					spaceship.setScore(spaceship.getScore() + 100);
					tempAlien.setVisible(false);
					tempAlien.alienExplosion();
					tempMissile.setVisible(false);
				}
			}
			for (int k = 0; k < bonus.size(); k++){
				Bonus tempBonus = bonus.get(k);
				bonusForm = tempBonus.getBounds();
				
				if(missileForm.intersects(bonusForm)){
					spaceship.setScore(spaceship.getScore() + 50);
					tempBonus.setVisible(false);
					tempMissile.setVisible(false);
				}
			}
				
				
		}
	}
	
	public void generateAliens() {
		Random randomic = new Random();

		for (int i = 0; i < level.getAlienCount(); i++) {
			int x = Math.abs(randomic.nextInt(Game.getWidth()));
			int y = -500 + Math.abs(randomic.nextInt(Game.getHeight()));
			aliens.add(new Alien(x, y, level.getAlienImage()));			
		}
	}
	
	private void generateBonus() {
		Random randomic = new Random();
		for (int i = 0; i < level.getBonusCount(); i++) {
			int x = Math.abs(randomic.nextInt(Game.getWidth()));
			int y = -500 + Math.abs(randomic.nextInt(Game.getHeight()));
			bonus.add(new Bonus(x, y, "images/bonus.png"));
		}
	}

	private void updateSpaceship() {
		spaceship.moveSpaceship();
	}

	private void updateMissile() {
		List<Missile> missile = spaceship.getMissile();
		for (int i = 0; i < missile.size(); i++) {
			Missile m = (Missile) missile.get(i);
			if (m.isVisible()) {
				m.missileMoviment();
			} else {
				missile.remove(i);
			}
		}
	}

	private void updateAlien() {
		for (int i = 0; i < aliens.size(); i++) {
			Alien a = (Alien) aliens.get(i);
			if (a.isVisible()) {
				a.movementAlien();
			} else {
				aliens.remove(i);
			}
			if (a.explosion) {
				a.setVisible(true);
			}
		}
	}
	private void updateBonus(){
		for(int i = 0; i < bonus.size(); i++){
			Bonus b = (Bonus) bonus.get(i);
			if(b.isVisible()){
				b.bonusMoviment();
			} else {
				bonus.remove(i);
			
			}
		}
	}
	  private class KeyListerner extends KeyAdapter {
	        
	        @Override
	        public void keyPressed(KeyEvent e) {
	            spaceship.keyPressed(e);
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            spaceship.keyReleased(e);
	        }

	        
	    }
	}