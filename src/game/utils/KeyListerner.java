package game.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.entities.Spaceship;

public class KeyListerner extends KeyAdapter {
	
	private Spaceship spaceship;

	@Override
	public void keyPressed(KeyEvent e) {
		spaceship.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		spaceship.keyReleased(e);
	}

}