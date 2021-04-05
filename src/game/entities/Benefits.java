package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.display.Game;

public class Benefits extends Sprite {

	private int benefits_speed = 1;
	private List<Benefits> benefitsList = new ArrayList<Benefits>();
	

	public Benefits(int posX, int posY, String image) {
		super(posX, posY);
		loadImage(image);
	}

	public void benefitsMovement() {
		validateBenefitMovement();
		this.positionY += benefits_speed;
	}
	
	private void validateBenefitMovement() {
		if ((positionY + sizeHeight >= Game.getHeight()))
			isVisible = false;
	}

	public List<Benefits> getBenefits() {
		return benefitsList;
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
