
public class Missile extends Sprite {
	
	
    
    private static final int SPEED = 2;
        
    private boolean isVisible;
    
    
    public Missile(int x, int y) {
        super(x, y);
        
        initMissile();
        
        isVisible = true;
    }

    private void initMissile() {
        
        imageMissile();
        
    }
    
    private void imageMissile(){
        loadImage("images/lazer.png"); 
    }
    public void move(){
    	this.y -= SPEED;
    	if(this.y > Game.getHeight())
    		isVisible = false;
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
		return x;
	}

    @Override
	public int getY() {
		return y;
	}
    
}
