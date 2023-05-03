package platforms;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// A platformok t�rol�s�t v�gz� PlatformManager class
public class PlatformManager {
	private List<Platform> platforms; // Platform t�rol�
	
	// Konstruktor
	public PlatformManager() {
		platforms = new ArrayList<Platform>();
	}
	
	// Platform hozz�ad�sa
	public void add(Platform p) {
		platforms.add(p);
	}
	
	// X koordin�ta lek�rdez�se a param�terben megadott sorsz�m� taghoz 
	public int getX(int i) {
		return platforms.get(i).getX();
	}
	
	// Y koordin�ta lek�rdez�se a param�terben megadott sorsz�m� taghoz 
	public int getY(int i) {
		return platforms.get(i).getY();
	}
	
	// A param�terben megadott sorsz�m� tag poz�ci�j�nak be�ll�t�sa
	public void setPosition(int i, int x, int y) {
		platforms.get(i).setPosition(x, y);
	}
	
	// Megh�vja param�terben megadott sorsz�m� tag jumpedon f�ggv�ny�t
	public boolean jumpedOn(int i, Rectangle r) {
		return platforms.get(i).jumpedOn(r);
	}
	
	// Plaform lek�rdez�se a param�terben megadott sorsz�m� taghoz
	public Platform getPlatform(int i) { return platforms.get(i); }
	
	// Platformokat k�sz�t a j�t�k elej�n
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
	
	// Megvizsg�l k�t platformot, hogy �tk�znek-e
	public boolean overlapCheck(Rectangle rec) {
		for(int i = 0; i < platforms.size(); i++) {
			if(rec.intersects(platforms.get(i).getArea())) {
				return true;
			}
		}
		return false;
	}
	
	// Az elhagyott platformok respawnol�sa
	public void updatePlatform(int i) throws IOException {
		Platform temp = platforms.get(i).calculateNewPlatform();
		temp.setPosition((int) (Math.random() * 635) , 0);
		if(!overlapCheck(temp.getArea())) {	
			platforms.remove(i);
			add(temp); 
		} else updatePlatform(i);
	}
	
	// Lek�rdezi a platformok sz�m�t
	public int size() { return platforms.size(); }	
	
	// Platformok kirajzol�sa
	public void drawPlatforms(Graphics2D g2) {
		for(int i = 0; i < platforms.size(); i++ ) {
			platforms.get(i).draw(g2);
		}
	}	
}
