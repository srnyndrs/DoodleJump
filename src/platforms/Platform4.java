package platforms;

import java.io.IOException;

import sprites.Doodle;

// Platform amely ugrás esetén széttörik
public class Platform4 extends Platform {
	private boolean broken;	
	
	// Konstruktor
	public Platform4() throws IOException {
		super("platform4_1.png");
		broken = false;
	}
	
	// A game meghívja ráugrásnál, így összetörik
	@Override
	public void update(Doodle d){
		broken = true;
		this.changeY(+1);
		getHitArea().setFrame(0,0,0,0);
		try {
			this.changeTexture("platform4_4.png");
	    } catch (IOException e) { e.printStackTrace(); }
	}
	
	// Ha összetörik leesnek a darabjai
	public void change() {
		if(broken) {
			this.changeY(+1);
		}
	}	
}
