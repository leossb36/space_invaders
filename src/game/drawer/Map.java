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
import game.entities.Benefits;
import game.entities.Life;
import game.entities.Laser;
import game.entities.Spaceship;
import game.transitions.Level;

public class Map extends JPanel implements ActionListener {

	private final int SPACESHIP_X = Game.getWidth() / 2;
	private final int SPACESHIP_Y = Game.getHeight() - Game.getHeight() / 5;
	
	private Image background;
	private boolean inGame;

	private static final String SPACEBACKGROUND = "images/space.jpg";
	private static final String LIFESPACESHIP = "images/life.png";

	private Spaceship spaceship;
	private Life lifeSpaceship;
	private Alien aliensList;
	private Laser laserList;
	private Benefits benefitsList;

	private Level level = Level.EASY;
	
	private Timer timer_aliens;
	private Timer timer_bonus;
	private Timer timer_map;
	
	public Map() {		
		setUp();
		initInstances();

	}
	
	private void setUp() {
		addKeyListener(new KeyListerner());
		setFocusable(true);
		setDoubleBuffered(true);
		ImageIcon image = new ImageIcon(SPACEBACKGROUND);
		this.background = image.getImage();
		inGame = true;
	}
	
	private void initInstances() {
		spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
		lifeSpaceship = new Life(0, 0, LIFESPACESHIP);

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
		} else {
			drawGameOver(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void draw(Graphics gameDraw) {
		// Draw spaceship
		gameDraw.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);

		// Draw Bullet
		List<Laser> missile = spaceship.getMissile();
		for (int i = 0; i < missile.size(); i++) {
			Laser m = (Laser) missile.get(i);
			gameDraw.drawImage(m.getImage(), m.getX(), m.getY(), this);
		}

		// Draw Alien
		for (Alien alien: aliensList.getAliens()) {
			gameDraw.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
		}
		// Draw Bonus
		for (int i = 0; i < bonus.size(); i++) {
			Benefits b = bonus.get(i);
			gameDraw.drawImage(b.getImage(), b.getX(), b.getY(), this);
		}

		gameDraw.setColor(Color.WHITE);
		gameDraw.drawString("HEALTH: ", 5, 15);
		for (int i = 1; i <= lifeSpaceship.getLifeSpaceship(); i++) {
			gameDraw.drawImage(lifeSpaceship.getImage(), i * 22 - 17, 20, this);
		}

		gameDraw.drawString(("SCORE: " + spaceship.getScore()), 5, 470);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		handleCollisions();
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
		// String message_Restart = "Restart Press enter.";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
		g.drawString(message_score + spaceship.getScore(),
				(((Game.getWidth() - metric.stringWidth(message_score)) / 2) - 20), (Game.getHeight() / 2) + 40);
		// g.drawString(message_Restart, ((Game.getWidth() -
		// metric.stringWidth(message_Restart)) /2), Game.getHeight() - 60);
		updateAlien();
		updateMissile();
		updateBonus();
	}

	private void drawGameOver(Graphics g) {
		String message = "MISSION ACCOMPLISHED";
		String message_score = "SCORE: ";
		// String message_Restart = "Restart Press enter.";
		Font font = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metric = getFontMetrics(font);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
		g.drawString(message_score + spaceship.getScore(),
				(((Game.getWidth() - metric.stringWidth(message_score)) / 2) - 20), (Game.getHeight() / 2) + 40);
		// g.drawString(message_Restart, ((Game.getWidth() -
		// metric.stringWidth(message_Restart)) /2), Game.getHeight() - 60);
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

	public void changeLevel() {
		if (level == Level.EASY && spaceship.getScore() >= 5000 && spaceship.getScore() < 10000) {
			nextLevel();
		}
		if (level == Level.MEDIUM && spaceship.getScore() >= 10000 && spaceship.getScore() < 20000) {
			nextLevel();
		}
		if (level == Level.HARD && spaceship.getScore() >= 20000) {
		}

	}

	public void restartScore() {
		spaceship.setScore(0);
		level = Level.EASY;

	}

	public void restartHealth() {
		lifeSpaceship.setLifeSpaceship(3);
	}
	
	public void handleCollisions() {
		collisionActionTransition_spaceship_alien();
		collisionActionTransition_spaceship_benefit();
		collisionActionTransition_laser_alien();
		collisionActionTransition_laser_benefit();
	}

	private boolean checkCollisionGenericFunction(Rectangle agentForm, Rectangle passiveAgentForm) {

		/*
		 * agent : the agent instance is who are doing the collision
		 * 
		 * passiveAgent: who receives the collision
		 * 
		 */

		if (agentForm.intersects(passiveAgentForm))
			return true;
		return false;
	}

	private void collisionActionTransition_spaceship_alien() {

		/*
		 * If collision generate explosion then deduct spaceship life remove alien and
		 * finally turn spaceship invulnerable for 2 seconds {TODO};
		 */

		Rectangle spaceshipForm = spaceship.getBounds();
		Rectangle alienForm;

		for (Alien alien : aliensList.getAliens()) {
			alienForm = alien.getBounds();

			if (checkCollisionGenericFunction(alienForm, spaceshipForm)) {
				alien.isBlowingUp();
				alien.setVisible(false);
			}
		}
	}
	
	private void collisionActionTransition_spaceship_benefit() {

		/*
		 * If collision generate explosion then deduct spaceship life remove alien and
		 * finally turn spaceship invulnerable for 2 seconds {TODO};
		 */

		Rectangle spaceshipForm = spaceship.getBounds();
		Rectangle benefitForm;

		for (Benefits benefit : benefitsList.getBenefits()) {
			benefitForm = benefit.getBounds();

			if (checkCollisionGenericFunction(spaceshipForm, benefitForm)) {
				benefit.setVisible(false);
				spaceship.setScore(spaceship.getScore() + 100);
			}
		}
	}


	private void collisionActionTransition_laser_alien() {

		/*
		 * If collision generate explosion then remove alien and
		 * remove laser;
		 */

		Rectangle laserForm;
		Rectangle alienForm;

		for (Laser laser: laserList.getLaserList()) {
			laserForm = laser.getBounds();
			
			for(Alien alien: aliensList.getAliens()) {
				alienForm = alien.getBounds();
				
				if (checkCollisionGenericFunction(laserForm, alienForm)) {
					alien.isBlowingUp();
					alien.setVisible(false);
					laser.setVisible(false);
					spaceship.setScore(spaceship.getScore() + 100);
				}				
			}

		}
	}
	
	private void collisionActionTransition_laser_benefit() {

		/*
		 * If collision generate explosion then remove alien and
		 * remove laser;
		 */

		Rectangle laserForm;
		Rectangle benefitForm;

		for (Laser laser: laserList.getLaserList()) {
			laserForm = laser.getBounds();
			
			for(Benefits benefit: benefitsList.getBenefits()) {
				benefitForm = benefit.getBounds();
				
				if (checkCollisionGenericFunction(laserForm, benefitForm)) {
					benefit.setVisible(false);
					laser.setVisible(false);
					spaceship.setScore(spaceship.getScore() + 100);
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
			bonus.add(new Benefits(x, y, "images/bonus.png"));
		}
	}

	private void updateSpaceship() {
		spaceship.moveSpaceship();
	}

	private void updateMissile() {
		List<Laser> missile = spaceship.getMissile();
		for (int i = 0; i < missile.size(); i++) {
			Laser m = (Laser) missile.get(i);
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
		}
	}

	private void updateBonus() {
		for (int i = 0; i < bonus.size(); i++) {
			Benefits b = (Benefits) bonus.get(i);
			if (b.isVisible()) {
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