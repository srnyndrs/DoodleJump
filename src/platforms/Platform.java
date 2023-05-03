package platforms;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import sprites.Doodle;
import sprites.Sprite;

// Sima platform, ha r�ugrunk feldob
public class Platform extends Sprite {
	
	// Konstruktor
	public Platform() throws IOException {
		super("platform.png");
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 200);
		getArea().setFrame(getX(),getY(),getWidth(),getHeight());
	}
	
	// Konstruktor a lesz�rmazott speci�lis platformoknak
	public Platform(String filename) throws IOException {
		super(filename);
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 200);
		getArea().setFrame(getX(),getY(),getWidth(),getHeight());
	}
	
	// Platform poz�ci�j�nak be�ll�t�sa
	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
		// Az �j poz�ci�khoz �ll�tjuk a hitarea �s area �rt�keket
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 20);
		getArea().setFrame(getX(), getY(), getWidth(), getHeight());
	}
	
	// Ellen�rzi hogy r�ugrottunk-e
	public boolean jumpedOn(Rectangle r) {
		return this.getHitArea().intersects(r);
	}
	
	// �j platformt�pus gener�l�s
	public Platform calculateNewPlatform() throws IOException {
		// Platform t�pus kalkul�ci� (1-6)
        // Platform t�pus meghat�roz�sa be�ll�tott es�lyekkel:
        int platformType;
        Random r = new Random();
        platformType = r.nextInt(100);
        if (platformType >= 0 && platformType < 50) // 50%
            return new Platform();
        else if (platformType >= 50 && platformType < 60) // 10%
        	return new Platform4();
        else if (platformType >= 60 && platformType < 74) // 14%
            return new Platform2();
        else if (platformType >= 74 && platformType < 88) // 14%
            return new Platform3();
        else if (platformType >= 88 && platformType < 98) // 10%
        	return new Spring();
        else if (platformType >= 98 && platformType < 100) // 2%
       	 	return new Blackhole();        
        return new Platform(); // Ha valami probl�ma lenne, vissza adunk egy sim�t
	}
	
	// Deklar�ci� a t�bbi speci�lis platform sz�m�ra
	public void change() {}
	
	// A platformok alap feldob�s funkci�ja
	public void update(Doodle d) {
		d.setDY(-13) ; // Feldobja a Doodle-t a magasba
	}
}
