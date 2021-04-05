package game.entities;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Sprite {

	protected int positionX;
	protected int positionY;
	protected int sizeWidth;
	protected int sizeHeight;
	protected boolean isVisible;
	protected boolean inCollision;
	protected Image imageRepresentation;
	private static final String EXPLOSION_ANIMATION = "images/explosion.gif";

	public Sprite(int posX, int posY) {
		this.positionX = posX;
		this.positionY = posY;
		this.isVisible = true;
	}

	protected void loadImage(String imageName) {

		ImageIcon imageIcon = new ImageIcon(imageName);
		imageRepresentation = imageIcon.getImage();
		getImageDimensions();
	}

	protected void getImageDimensions() {
		sizeWidth = imageRepresentation.getWidth(null);
		sizeHeight = imageRepresentation.getHeight(null);
	}

	public Image getImage() {
		return imageRepresentation;
	}

	public int getX() {
		return positionX;
	}

	public int getY() {
		return positionY;
	}

	public int getWidth() {
		return sizeWidth;
	}

	public int getHeight() {
		return sizeHeight;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void isBlowingUp() {
		loadImage(EXPLOSION_ANIMATION);
	}

	public void setVisible(Boolean enable) {
		this.isVisible = enable;
	}

	public Rectangle getBounds() {
		return new Rectangle(positionX, positionY, sizeWidth, sizeHeight);
	}
}