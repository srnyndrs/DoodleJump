package platforms;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// A platformok tárolását végzõ PlatformManager class
public class PlatformManager {
	private List<Platform> platforms; // Platform tároló
	
	// Konstruktor
	public PlatformManager() {
		platforms = new ArrayList<Platform>();
	}
	
	// Platform hozzáadása
	public void add(Platform p) {
		platforms.add(p);
	}
	
	// X koordináta lekérdezése a paraméterben megadott sorszámú taghoz 
	public int getX(int i) {
		return platforms.get(i).getX();
	}
	
	// Y koordináta lekérdezése a paraméterben megadott sorszámú taghoz 
	public int getY(int i) {
		return platforms.get(i).getY();
	}
	
	// A paraméterben megadott sorszámú tag pozíciójának beállítása
	public void setPosition(int i, int x, int y) {
		platforms.get(i).setPosition(x, y);
	}
	
	// Meghívja paraméterben megadott sorszámú tag jumpedon függvényét
	public boolean jumpedOn(int i, Rectangle r) {
		return platforms.get(i).jumpedOn(r);
	}
	
	// Plaform lekérdezése a paraméterben megadott sorszámú taghoz
	public Platform getPlatform(int i) { return platforms.get(i); }
	
	// Platformokat készít a játék elején
	public void spawnPlatforms() throws IOException {
		int height = 800;
		int height_new;
		int new_x;
		for(int i = 0; i < 10; i++) {
			Platform temp = new Platform();
			new_x = (int) (Math.random() * 635);
			height_new = height - ((int)(((Math.random() * 50) + 50)));
			temp.setPosition(new_x, height_new);
			if(!overlapCheck(temp.getArea())) {
				height = height_new;
				add(temp);
			} else i--;			
		}
	}
	
	// Megvizsgál két platformot, hogy ütköznek-e
	public boolean overlapCheck(Rectangle rec) {
		for(int i = 0; i < platforms.size(); i++) {
			if(rec.intersects(platforms.get(i).getArea())) {
				return true;
			}
		}
		return false;
	}
	
	// Az elhagyott platformok respawnolása
	public void updatePlatform(int i) throws IOException {
		Platform temp = platforms.get(i).calculateNewPlatform();
		temp.setPosition((int) (Math.random() * 635) , 0);
		if(!overlapCheck(temp.getArea())) {	
			platforms.remove(i);
			add(temp); 
		} else updatePlatform(i);
	}
	
	// Lekérdezi a platformok számát
	public int size() { return platforms.size(); }	
	
	// Platformok kirajzolása
	public void drawPlatforms(Graphics2D g2) {
		for(int i = 0; i < platforms.size(); i++ ) {
			platforms.get(i).draw(g2);
		}
	}	
}
