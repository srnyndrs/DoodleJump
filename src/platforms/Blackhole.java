package platforms;

import java.io.IOException;
import sprites.Doodle;

// A feketelyuk egy olyan platform mely 
public class Blackhole extends Platform {

	// Konstruktor
	public Blackhole() throws IOException {
		super("blackhole.png");
	}
	
	// Poz�ci� be�ll�t� f�ggv�ny fel�l�r�sa, mert m�s a HitArea
	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
		// Az �j poz�ci�khoz �ll�tjuk
		getHitArea().setFrame(getX() + 25, getY() + 25, getWidth() - 50, getHeight() - 50);
		getArea().setFrame(getX(), getY(), getWidth(), getHeight());
	}
	
	// Az update f�ggv�ny fel�l�r�sa mert nem dobja fel a leveg�be
	@Override
	public void update(Doodle d){
		// A game oszt�ly kezeli, megh�vja a gameover f�ggv�nyt
	}
}
