package game.entities;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import game.display.Game;

public class Spaceship extends Sprite {

	static final String SPACESHIP_IMAGE_NOTHRUST = "images/spaceshipnew_nothrust.png";
	static final String SPACESHIP_IMAGE = "images/spaceshipnew_nothrust.png";
	private static final int MAX_SPEED_X = 2;
	private static final int MAX_SPEED_Y = 1;
	private boolean isVisible;
	private int health = 3;
	private int score;

	private List<Missile> missiles;

	private int speed_x; // speed of spaceship in horizontal
	private int speed_y; // speed of spaceship in vertical

	public Spaceship(int posX, int posY) {
		super(posX, posY);

		initSpaceShip();
	}

	private void initSpaceShip() {

		missiles = new ArrayList<Missile>();

		noThrust();

	}

	private void noThrust() {
		loadImage(SPACESHIP_IMAGE_NOTHRUST);
	}

	private void thrust() {
		loadImage(SPACESHIP_IMAGE);
	}

	public List<Missile> getMissile() {
		return missiles;
	}

	public void actionRenderShootingSpaceShip() {
		this.missiles.add(renderMissileWhileShooting());
	}

	private Missile renderMissileWhileShooting() {
		Missile missile = new Missile(positionX + sizeWidth / 2, positionY);

		return missile;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getHealth() {
		return health;
	}

	public void setLife(int i) {
		this.health = i;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void moveSpaceship() {
		positionX = validateMovement(speed_x, positionX, sizeWidth, Game.getWidth());
		positionY = validateMovement(speed_y, positionY, sizeHeight, Game.getHeight());
	}

	private int validateMovement(int speedMovement, int currentPosition, int bounds, int limits) {
		if ((speedMovement < 0 && currentPosition <= 0) || (speedMovement > 0 && currentPosition + bounds >= limits)) {
			speedMovement = 0;
		} else {
			currentPosition += speedMovement;
		}

		return currentPosition;

	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		// Set speed to move to the left
		if (key == KeyEvent.VK_LEFT) {
			speed_x = -1 * MAX_SPEED_X;
		}

		// Set speed to move to the right
		if (key == KeyEvent.VK_RIGHT) {
			speed_x = MAX_SPEED_X;
		}

		// Set speed to move to up and set thrust effect
		if (key == KeyEvent.VK_UP) {
			speed_y = -1 * MAX_SPEED_Y;
			thrust();
		}

		// Set speed to move to down
		if (key == KeyEvent.VK_DOWN) {
			speed_y = MAX_SPEED_Y;
		}
		if (key == KeyEvent.VK_SPACE) {
			actionRenderShootingSpaceShip();
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			speed_x = 0;
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			speed_y = 0;
			noThrust();

		}

	}

}