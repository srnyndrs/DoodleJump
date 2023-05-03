package platforms;

import java.awt.Graphics2D;
import java.io.IOException;

import sprites.Doodle;
import sprites.Sprite;

// Rugót tartalmazó platform
public class Spring extends Platform {
	private Sprite spring;
	
	// Konstruktor
	public Spring() throws IOException {
		super("platform.png");
		spring = new Sprite("spring1.png");
		spring.setPosition(this.getX(), this.getY() - 5);
	}
	
	// Pozíció beállítás
	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.getHitArea().setFrame(getX(), getY() - 5, getWidth(), getHeight() - 20);
		this.getArea().setFrame(getX(),getY(),getWidth(),getHeight());
		spring.setPosition(this.getX(), this.getY() - 5);
	}
	
	// Update függvény felülírása, ugyanis a rugó magasabbra dob
	@Override
	public void update(Doodle d){
		// Megnézzük hogy a rugóra ugrott-e
		if(spring.getHitArea().intersects(d.getHitArea())) {
			d.setDY(-40); // Feldobjuk magasabbra
			try {
				spring.setPosition(spring.getX(), spring.getY() - 30);
				spring.changeTexture("spring2.png");				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			d.setDY(-13); // Ha nem a rugóra ugrott de a platformra akkor csak feldobja		
		}
	}
	
	// Draw függvény felülírása, mert a rugót is rárajzoljuk a platformra
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getTexture(), getX(), getY(), getWidth(), getHeight(), null);
		g2.drawImage(spring.getTexture(), spring.getX(), spring.getY(), spring.getWidth(), spring.getHeight(), null);
	}
	
}
