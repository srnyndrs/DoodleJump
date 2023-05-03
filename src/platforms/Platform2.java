package platforms;
import java.io.IOException;

// F�gg�leges ir�nyba mozg� platform
public class Platform2 extends Platform {
	private int direction; // le: 1 || fel: -1
	private boolean created = false;
	private int x_state, y_state;
	
	// Konstruktor
	public Platform2() throws IOException {
		super("platform.png");
		direction = 1;
		x_state = getX();
		y_state = getY();
		this.getArea().setFrame(x_state, y_state, getWidth(), 100);
	}
	
	// A game folyamatosan megh�vja, a platform mozg�s��rt felel�s 
	@Override
	public void change() {		
		if (direction == 1 && getY() + getHeight() == getArea().getY() + getArea().getHeight()) {
            direction = -1;
		} else if (direction == -1 && getY() == getArea().getY()) {
            direction = 1;
        }
		this.changeY(direction);
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 20);
	}
	
	// A platform poz�ci�j�nak be�ll�t�sa
	@Override
	public void setPosition(int x, int y) {
		int temp_x = x - getX();
		int temp_y = y - getY();
		
		getArea().setFrame((int)this.getArea().getX() + temp_x,(int)this.getArea().getY() + temp_y, getWidth(), 100);
		
		this.setX(x);
		this.setY(y);		
		if(!created) {
			x_state = getX();
			y_state = getY();
			created = true;
		}
		// Az �j poz�ci�khoz �ll�tjuk
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 20);
	}
}
