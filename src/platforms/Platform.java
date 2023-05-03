package platforms;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import sprites.Doodle;
import sprites.Sprite;

// Sima platform, ha ráugrunk feldob
public class Platform extends Sprite {
	
	// Konstruktor
	public Platform() throws IOException {
		super("platform.png");
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 200);
		getArea().setFrame(getX(),getY(),getWidth(),getHeight());
	}
	
	// Konstruktor a leszármazott speciális platformoknak
	public Platform(String filename) throws IOException {
		super(filename);
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 200);
		getArea().setFrame(getX(),getY(),getWidth(),getHeight());
	}
	
	// Platform pozíciójának beállítása
	@Override
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
		// Az új pozíciókhoz állítjuk a hitarea és area értékeket
		getHitArea().setFrame(getX(), getY(), getWidth(), getHeight() - 20);
		getArea().setFrame(getX(), getY(), getWidth(), getHeight());
	}
	
	// Ellenõrzi hogy ráugrottunk-e
	public boolean jumpedOn(Rectangle r) {
		return this.getHitArea().intersects(r);
	}
	
	// Új platformtípus generálás
	public Platform calculateNewPlatform() throws IOException {
		// Platform típus kalkuláció (1-6)
        // Platform típus meghatározása beállított esélyekkel:
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
        return new Platform(); // Ha valami probléma lenne, vissza adunk egy simát
	}
	
	// Deklaráció a többi speciális platform számára
	public void change() {}
	
	// A platformok alap feldobás funkciója
	public void update(Doodle d) {
		d.setDY(-13) ; // Feldobja a Doodle-t a magasba
	}
}
