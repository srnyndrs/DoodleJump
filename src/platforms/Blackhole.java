package platforms;

import java.io.IOException;
import sprites.Doodle;

// A feketelyuk egy olyan platform mely 
public class Blackhole extends Platform {

	// Konstruktor
	public Blackhole() throws IOException {
		super("blackhole.png");
	}
	
	// Pozíció beállító függvény felülírása, mert más a HitArea
	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
		// Az új pozíciókhoz állítjuk
		getHitArea().setFrame(getX() + 25, getY() + 25, getWidth() - 50, getHeight() - 50);
		getArea().setFrame(getX(), getY(), getWidth(), getHeight());
	}
	
	// Az update függvény felülírása mert nem dobja fel a levegõbe
	@Override
	public void update(Doodle d){
		// A game osztály kezeli, meghívja a gameover függvényt
	}
}
