package game.entities;

public class Life extends Sprite {
	
	private int spaceship_life = 3;
	public Life(int posX, int posY, String imageName) {
		super(posX, posY);
		loadImage(imageName);
	}
	
	
	public int getLifeSpaceship() {
		return spaceship_life;
	}
	
	public void setLifeSpaceship(int value) {
		this.spaceship_life = value;
	}

}
