package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.display.Game;

public class Bonus extends Sprite {

	private static final int SPEEDBONUS = 1;

	public Bonus(int x, int y, String image) {
		super(x, y);
		loadImage(image);

	}

	public void bonusMoviment() {
		this.positionY += SPEEDBONUS;

		if ((positionY + sizeHeight >= Game.getHeight()))
			isVisible = false;
	}

	public List<Bonus> bonus = new ArrayList<Bonus>();

	public List<Bonus> getBonus() {
		return bonus;
	}

	public int getX() {
		return positionX;
	}

	public int getY() {
		return positionY;
	}
}
