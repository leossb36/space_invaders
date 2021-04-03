import java.util.ArrayList;
import java.util.List;

public class Bonus extends Sprite {
	
	private static final int SPEEDBONUS = 1;
	
	public Bonus(int x, int y, String image){
		super(x , y);
		loadImage(image);
		
	}
	public void bonusMoviment(){
		this.y += SPEEDBONUS;
		
		if((y + width >= Game.getHeight()))
    		visible = false;
	}
	public List<Bonus> bonus = new ArrayList<Bonus>();
	
	public List<Bonus>getBonus(){
		return bonus;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}

