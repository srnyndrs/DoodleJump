package sprites;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// Sprite �soszt�ly a j�t�k objektumok megval�s�t�s�hoz
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
	
	// HitArea lek�rdez�se
	public Rectangle getHitArea() { return hitArea; }	
	
	// Area lek�rdez�se
	public Rectangle getArea() { return area; }
	
	// Poz�ci� be�ll�t�sa
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		hitArea = new Rectangle(x,y,width, height);
		area = new Rectangle(x, y, width, height);
	}
	
	// Rajzol� f�ggv�ny
	public void draw(Graphics2D g2) {
		g2.drawImage(texture, getX(), getY(), getWidth(), getHeight(), null);	
	}
	
	// Lek�rdezi a text�r�t
	public BufferedImage getTexture() { return texture; }
	
	// Text�ra megv�ltoztat�sa
	public void changeTexture(String newfilename) throws IOException { 
		filename = newfilename;
		texture = ImageIO.read(getClass().getResource("/images/" + filename));
		height = texture.getHeight();
		width = texture.getWidth();
		hitArea = new Rectangle(x,y,width, height);
		area = new Rectangle(x, y, width, height);
	}
	
	// Ellen�rzi, hogy �tk�z�tt-e a param�terben megadott testtel
	public boolean collideWith(Rectangle r) {
		return area.intersects(r);
	}	
	
	// Magass�g �s sz�less�g lek�rdez� f�ggv�nyek
	public int getHeight() { return height; }	
	public int getWidth() { return width; }
	
	// X �s Y koordin�ta be�ll�t� f�ggv�nyek
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	// X �s Y koordin�ta lek�rdez� f�ggv�nyek
	public int getX() { return x; }
	public int getY() { return y; }
	
	// X �s Y koordin�ta n�vel�/cs�kkent� f�ggv�nyek
	public void changeX(int x) { this.x += x; }
	public void changeY(int y) { this.y += y; }	
}
