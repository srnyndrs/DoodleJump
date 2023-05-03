package platforms;

import java.io.IOException;

import sprites.Doodle;

// Platform amely ugr�s eset�n sz�tt�rik
public class Platform4 extends Platform {
	private boolean broken;	
	
	// Konstruktor
	public Platform4() throws IOException {
		super("platform4_1.png");
		broken = false;
	}
	
	// A game megh�vja r�ugr�sn�l, �gy �sszet�rik
	@Override
	public void update(Doodle d){
		broken = true;
		this.changeY(+1);
		getHitArea().setFrame(0,0,0,0);
		try {
			this.changeTexture("platform4_4.png");
	    } catch (IOException e) { e.printStackTrace(); }
	}
	
	// Ha �sszet�rik leesnek a darabjai
	public void change() {
		if(broken) {
			this.changeY(+1);
		}
	}	
}
