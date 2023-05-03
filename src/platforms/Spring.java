package platforms;

import java.awt.Graphics2D;
import java.io.IOException;

import sprites.Doodle;
import sprites.Sprite;

// Rug�t tartalmaz� platform
public class Spring extends Platform {
	private Sprite spring;
	
	// Konstruktor
	public Spring() throws IOException {
		super("platform.png");
		spring = new Sprite("spring1.png");
		spring.setPosition(this.getX(), this.getY() - 5);
	}
	
	// Poz�ci� be�ll�t�s
	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.getHitArea().setFrame(getX(), getY() - 5, getWidth(), getHeight() - 20);
		this.getArea().setFrame(getX(),getY(),getWidth(),getHeight());
		spring.setPosition(this.getX(), this.getY() - 5);
	}
	
	// Update f�ggv�ny fel�l�r�sa, ugyanis a rug� magasabbra dob
	@Override
	public void update(Doodle d){
		// Megn�zz�k hogy a rug�ra ugrott-e
		if(spring.getHitArea().intersects(d.getHitArea())) {
			d.setDY(-40); // Feldobjuk magasabbra
			try {
				spring.setPosition(spring.getX(), spring.getY() - 30);
				spring.changeTexture("spring2.png");				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			d.setDY(-13); // Ha nem a rug�ra ugrott de a platformra akkor csak feldobja		
		}
	}
	
	// Draw f�ggv�ny fel�l�r�sa, mert a rug�t is r�rajzoljuk a platformra
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getTexture(), getX(), getY(), getWidth(), getHeight(), null);
		g2.drawImage(spring.getTexture(), spring.getX(), spring.getY(), spring.getWidth(), spring.getHeight(), null);
	}
	
}
