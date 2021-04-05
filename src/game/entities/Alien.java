package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.display.Game;

public class Alien extends Sprite {

	private List<Alien> alienList = new ArrayList<Alien>();
	private int alien_speed = 1;


	public Alien(int posX, int posY, String image) {
		super(posX, posY);

		loadImage(image);
		isVisible = true;
	}

	public void movementAlien() {
		this.positionY += alien_speed;
		if ((positionX + sizeWidth > Game.getHeight())) {
			isVisible = false;
		}
	}

	public List<Alien> getAliens() {
		return alienList;
	}
	
	public void setAlienSpeed(int value) {
		this.alien_speed = value;
	}
	
	public int getAlienSpeed() {
		return alien_speed;
	}
	
	@Override
	public int getX() {
		return positionX;
	}

	@Override
	public int getY() {
		return positionY;
	}
	
	@Override
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
