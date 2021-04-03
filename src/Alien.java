import java.util.ArrayList;
import java.util.List;

public class Alien extends Sprite {
    
	public static final int SPEED = 1;
	public boolean explosion;
	
	
	public Alien(int x, int y, String image) {
		super(x,y);
		
		loadImage(image);
		visible = true;	
	}
	
	public void movementAlien(){
		this.y += SPEED;	
    	if((y + width >= Game.getHeight())){
    		visible = false;
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
		return x;
	}

	public int getY() {
		return y;
	}
}
