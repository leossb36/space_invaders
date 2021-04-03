import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Spaceship extends Sprite {
        
    private static final int MAX_SPEED_X = 2;
    private static final int MAX_SPEED_Y = 1;
    private boolean isVisible;
    private int health = 3;
    private int score;

    
    private List<Missile> missile;
    
    private int speed_x; // speed of spaceship in horizontal 
    private int speed_y; // speed of spaceship in vertical
    
    public Spaceship(int x, int y) {
        super(x, y);
                
        missile = new ArrayList<Missile>();
               
        initSpaceShip();
    }
    
        private void initSpaceShip() {
        
        noThrust();
        
    }
    
    private void noThrust(){
        loadImage("images/spaceshipnew_nothrust.png"); 
    }
    
    private void thrust(){
        loadImage("images/spaceshipnew.png"); 
    }
    
    public List<Missile> getMissile() {
		return missile;
	}

	public void shoot(){
    	this.missile.add(new Missile(x - 3 + width/2, y + height/2));
    }

    public void moveSpaceship() {
        
        // Limits the movement of the spaceship to the side edges.
        if((speed_x < 0 && x <= 0) || (speed_x > 0 && x + width >= Game.getWidth())){
            speed_x = 0;
        }
        
        // Moves the spaceship on the horizontal axis
        x += speed_x;
        
        // Limits the movement of the spaceship to the vertical edges.
        if((speed_y < 0 && y <= 0) || (speed_y > 0 && y + height >= Game.getHeight())){
            speed_y = 0;
        }

        // Moves the spaceship on the vertical axis
        y += speed_y;
        
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        // Set speed to move to the left
        if (key == KeyEvent.VK_LEFT) { 
            speed_x = -1 * MAX_SPEED_X;
        }

        // Set speed to move to the right
        if (key == KeyEvent.VK_RIGHT) {
            speed_x = MAX_SPEED_X;
        }
        
        // Set speed to move to up and set thrust effect
        if (key == KeyEvent.VK_UP) {
            speed_y = -1 * MAX_SPEED_Y;
            thrust();
        }
        
        // Set speed to move to down
        if (key == KeyEvent.VK_DOWN) {
            speed_y = MAX_SPEED_Y;
        }
        if (key == KeyEvent.VK_SPACE){
        	shoot();
        }
    }
    
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            speed_x = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            speed_y = 0;
            noThrust();
            
        }
        
     }

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public int getHealth(){
		return health;
		}
	    
	public void setLife(int i){
		this.health = i;
		}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
    
}  