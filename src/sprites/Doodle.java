package sprites;

import java.awt.Rectangle;
import java.io.IOException;

// Doodle oszt�ly, a karater�nk megval�s�t�s�ra
public class Doodle extends Sprite {
	private float dy;
	private boolean left = false, right = false;
	private String facing = "right";
	
	// Konstruktor
	public Doodle() throws IOException {
		super("doodle_R.png");
		// Default kezd�hely
		setX(375);
		setY(800);
		setDY(-10);
		// Foot area
		setHitAreaRight();
		setArea();
	}
	
	// Megvizsg�lja �rintkezik-e a param�terben megadott ter�lettel
	public boolean intersect(Rectangle r) {
		if(this.getArea().intersects(r))
			return true;
		return false;
	}
	
	// Lek�rdezi, hogy melyik ir�nyba n�z
	public String getFacing() { return facing; }
	
	// A HitArea be�ll�t�sa ha jobbra n�z�nk, ugyanis a l�ba sz�m�t ugr�sokn�l
	public void setHitAreaRight() {
		// Doodle l�ba
		getHitArea().setFrame(getX() + 5, getY() + 80, getWidth() - 35, getHeight() - 80);
	}
	
	// Ha balra n�z�nk akkor a HitArea is v�ltozik
	public void setHitAreaLeft() {
		// Doodle l�ba
		getHitArea().setFrame(getX() + 30 ,getY() + 80, getWidth() - 35, getHeight() - 80);
	}
	
	// Be�ll�tja a Doodle k�rvonal�t
	public void setArea() {
		getArea().setFrame(getX(),getY(),getWidth(),getHeight());
	}
	
	// Megv�ltoztatja az ir�nyt amerre n�z�nk
	public void changeFacing() {
		if(facing.equals("right")) {
			facing = "left";
		} else {
			facing = "right";
		}
	}
	
	// Jobb oldal lek�rdez�se
	public boolean getRight() { return right; }
	
	// Bal oldal lek�rdez�se
	public boolean getLeft() { return left; }
	
	// Bal oldal be�ll�t�sa
	public void setLeft(boolean state) { left = state; }
	
	// Jobb oldal be�ll�t�sa
	public void setRight(boolean state) { right = state; }
	
	// DY mennyis�g lek�rdez�se
	public float getDY() { return dy; }
	
	// DY mennyis�g be�ll�t�sa
	public void setDY(float i) { dy = i; }
	
	// DY mennyis�g n�vel�se/cs�kkent�se
	public void changeDY(float i) { dy += i; }
	
	// X koordin�ta n�vel�se/cs�kkent�se
	public void changeX(int i) {
		int temp = getX() + i;
		setX(temp);
		setArea();
		if(facing.equals("right"))
			setHitAreaRight();
		else setHitAreaLeft();
	}
	
	// Y koordin�ta n�vel�se/cs�kkent�se
	public void changeY(int i) {
		int temp = getY() + i;
		setY(temp);
		setArea();
		if(facing.equals("left"))
			setHitAreaLeft();
		else setHitAreaRight();
	}	
}
