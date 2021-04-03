package game.entities;
import java.util.ArrayList;
import java.util.List;

import game.display.Game;

public class Alien extends Sprite {
    
	public static final int SPEED = 1;
	public boolean explosion;
	
	
	public Alien(int x, int y, String image) {
		super(x,y);
		
		loadImage(image);
		isVisible = true;	
	}
	
	public void movementAlien(){
		this.positionY += SPEED;	
    	if((positionX + sizeWidth > Game.getHeight())){
    		isVisible = false;
    	}
	}
	
	public synchronized void alienExplosion(){
		loadImage("images/explosion.png");
		explosion = true;
	}
	
	public List<Alien> aliens = new ArrayList<Alien>();

	
	public List<Alien> getAliens() {
		return aliens;
	}

	public int getX() {
		return positionX;
	}

	public int getY() {
		return positionY;
	}
}
