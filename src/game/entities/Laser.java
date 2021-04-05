package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.display.Game;

public class Laser extends Sprite {

	private static final String LASER_IMAGE = "images/shot.png";
	private static final int LASER_SPEED = 2;
	private int laser_amount = 1;
	private List<Laser> laserList = new ArrayList<Laser>();
	
	private boolean isVisible;

	public Laser(int posX, int posY) {
		super(posX, posY);

		getLaserImage();

		isVisible = true;
	}

	private void getLaserImage() {
		loadImage(LASER_IMAGE);
	}

	public void validateLaserMovement() {
		if (this.positionY > Game.getHeight())
			isVisible = false;
	}
	
	public void getLaserMovement() {
		validateLaserMovement();
		this.positionY -= LASER_SPEED;
	}
	
	public List<Laser> getLaserList() {
		return laserList;
	}
	
	public void setLaserAmount(int value) {
		this.laser_amount = value;
	}
	
	public int getLaserAmount() {
		return laser_amount;
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
