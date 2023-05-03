package sprites;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// Sprite õsosztály a játék objektumok megvalósításához
public class Sprite {
	private BufferedImage texture;
	private String filename;
	private int height, width;
	private int x, y;
	private Rectangle hitArea;
	private Rectangle area;
	
	// Konstruktor
	public Sprite(String name) throws IOException{
		filename = name;
		texture = ImageIO.read(getClass().getResource("/images/" + filename));
		height = texture.getHeight();
		width = texture.getWidth();
		x = 0;
		y = 0;
		area = new Rectangle(x, y, width, height);
		hitArea = new Rectangle(x, y, width, height);
	}
	
	// HitArea lekérdezése
	public Rectangle getHitArea() { return hitArea; }	
	
	// Area lekérdezése
	public Rectangle getArea() { return area; }
	
	// Pozíció beállítása
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		hitArea = new Rectangle(x,y,width, height);
		area = new Rectangle(x, y, width, height);
	}
	
	// Rajzoló függvény
	public void draw(Graphics2D g2) {
		g2.drawImage(texture, getX(), getY(), getWidth(), getHeight(), null);	
	}
	
	// Lekérdezi a textúrát
	public BufferedImage getTexture() { return texture; }
	
	// Textúra megváltoztatása
	public void changeTexture(String newfilename) throws IOException { 
		filename = newfilename;
		texture = ImageIO.read(getClass().getResource("/images/" + filename));
		height = texture.getHeight();
		width = texture.getWidth();
		hitArea = new Rectangle(x,y,width, height);
		area = new Rectangle(x, y, width, height);
	}
	
	// Ellenõrzi, hogy ütközött-e a paraméterben megadott testtel
	public boolean collideWith(Rectangle r) {
		return area.intersects(r);
	}	
	
	// Magasság és szélesség lekérdezõ függvények
	public int getHeight() { return height; }	
	public int getWidth() { return width; }
	
	// X és Y koordináta beállító függvények
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	// X és Y koordináta lekérdezõ függvények
	public int getX() { return x; }
	public int getY() { return y; }
	
	// X és Y koordináta növelõ/csökkentõ függvények
	public void changeX(int x) { this.x += x; }
	public void changeY(int y) { this.y += y; }	
}
