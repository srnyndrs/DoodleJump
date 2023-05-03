package platforms;
import java.io.IOException;

// Vízszintes irányba mozgó platform
public class Platform3 extends Platform {
	private int direction; // jobbra: 1 || balra: -1
	private boolean created = false;
	private int x_state, y_state;	
	
	// Konstruktor
	public Platform3() throws IOException {
		super("platform.png");
		direction = 1;
		x_state = getX();
		y_state = getY();
		this.getArea().setFrame(x_state, y_state, 300, getHeight());
	}
	
	// A game folyamatosan meghívja, a platform mozgásáért felelõs 
	@Override
	public void change() {		
		if (direction == 1 && getX() + getWidth() == getArea().getX() + getArea().getWidth()) {
            direction = -1;
		} else if (direction == -1 && getX() == getArea().getX()) {
            direction = 1;
        }
		this.changeX(direction);
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 20);
	}
	
	// A platform pozíciójának beállítása
	@Override
	public void setPosition(int x, int y) {
		int temp_x = x - getX();
		int temp_y = y - getY();
		
		getArea().setFrame((int)this.getArea().getX() + temp_x,(int)this.getArea().getY() + temp_y, 250, getHeight());
		
		this.setX(x);
		this.setY(y);		
		if(!created) {
			x_state = getX();
			y_state = getY();
			created = true;
		}
		// Az új pozíciókhoz állítjuk
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 20);
	}
}
