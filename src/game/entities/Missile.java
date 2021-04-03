package game.entities;

import game.display.Game;

public class Missile extends Sprite {

	private static final String MISSILE_IMAGE = "images/lazer.png";
	private static final int SPEED = 2;

	private boolean isVisible;

	public Missile(int posX, int posY) {
		super(posX, posY);

		initMissile();

		isVisible = true;
	}

	private void initMissile() {

		imageMissile();

	}

	private void imageMissile() {
		loadImage(MISSILE_IMAGE);
	}

	public void missileMoviment() {
		this.positionY -= SPEED;

		if (this.positionY > Game.getHeight())
			isVisible = false;
	}

	@Override
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public int getX() {
		return positionX;
	}

	@Override
	public int getY() {
		return positionY;
	}

}
