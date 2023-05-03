package sprites;

import java.awt.Rectangle;
import java.io.IOException;

// Doodle osztály, a karaterünk megvalósítására
public class Doodle extends Sprite {
	private float dy;
	private boolean left = false, right = false;
	private String facing = "right";
	
	// Konstruktor
	public Doodle() throws IOException {
		super("doodle_R.png");
		// Default kezdõhely
		setX(375);
		setY(800);
		setDY(-10);
		// Foot area
		setHitAreaRight();
		setArea();
	}
	
	// Megvizsgálja érintkezik-e a paraméterben megadott területtel
	public boolean intersect(Rectangle r) {
		if(this.getArea().intersects(r))
			return true;
		return false;
	}
	
	// Lekérdezi, hogy melyik irányba néz
	public String getFacing() { return facing; }
	
	// A HitArea beállítása ha jobbra nézünk, ugyanis a lába számít ugrásoknál
	public void setHitAreaRight() {
		// Doodle lába
		getHitArea().setFrame(getX() + 5, getY() + 80, getWidth() - 35, getHeight() - 80);
	}
	
	// Ha balra nézünk akkor a HitArea is változik
	public void setHitAreaLeft() {
		// Doodle lába
		getHitArea().setFrame(getX() + 30 ,getY() + 80, getWidth() - 35, getHeight() - 80);
	}
	
	// Beíllítja a Doodle körvonalát
	public void setArea() {
		getArea().setFrame(getX(),getY(),getWidth(),getHeight());
	}
	
	// Megváltoztatja az irányt amerre nézünk
	public void changeFacing() {
		if(facing.equals("right")) {
			facing = "left";
		} else {
			facing = "right";
		}
	}
	
	// Jobb oldal lekérdezése
	public boolean getRight() { return right; }
	
	// Bal oldal lekérdezése
	public boolean getLeft() { return left; }
	
	// Bal oldal beállítása
	public void setLeft(boolean state) { left = state; }
	
	// Jobb oldal beállítása
	public void setRight(boolean state) { right = state; }
	
	// DY mennyiség lekérdezése
	public float getDY() { return dy; }
	
	// DY mennyiség beállítása
	public void setDY(float i) { dy = i; }
	
	// DY mennyiség növelése/csökkentése
	public void changeDY(float i) { dy += i; }
	
	// X koordináta növelése/csökkentése
	public void changeX(int i) {
		int temp = getX() + i;
		setX(temp);
		setArea();
		if(facing.equals("right"))
			setHitAreaRight();
		else setHitAreaLeft();
	}
	
	// Y koordináta növelése/csökkentése
	public void changeY(int i) {
		int temp = getY() + i;
		setY(temp);
		setArea();
		if(facing.equals("left"))
			setHitAreaLeft();
		else setHitAreaRight();
	}	
}
