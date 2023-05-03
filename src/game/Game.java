package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import graphics.Main;
import platforms.Blackhole;
import platforms.PlatformManager;
import sprites.Doodle;
import sprites.Sprite;

// Game class felel�s a j�t�k mechanik�j��rt
public class Game extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	// K�perny� m�retek
	public final int WIDTH = 750;
	public final int HEIGHT = 800;
	public final int SCROLL_HEIGHT = 400;
	// J�t�khoz sz�ks�ges objektumok
	private Doodle doodle;
	private Player player;
	private PlatformManager pm;
	private int score;
	// Megjelen�t�shez sz�ks�ges objektumok
	private JFrame window;
	private Main main;
	private Sprite topbar, background;
	private BufferedImage view;	
	// Thread �s boolean v�ltoz�k
	private boolean isRunning, start = true, gameover;
	private boolean stop = false; 
	private Thread thread;	
	
	// Konstruktor
	public Game(JFrame w, Main m) throws IOException {
		window = w;
		main = m;
		player = new Player();
		addKeyListener(this);
		this.setVisible(true);		
		view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		background = new Sprite("background.png");
		topbar = new Sprite("topbar.png");
		doodle = new Doodle();
		pm = new PlatformManager();
		pm.spawnPlatforms();
		start();		
	}
	
	// J�t�k v�ge f�ggv�ny
	public void gameover(int score) {
		player.setScore(score);
		isRunning = false;
		gameover = true;
		// J�t�k v�g�n megk�rj�k a j�t�kost hogy �rja be a nev�t
		String temp = JOptionPane.showInputDialog(window,"You scored: " + score + "\nEnter your name");
		// Ha a cancel-re nyomott akkor olyanra �ll�tjuk amit tudunk kezelni
		if(temp == null) {
			temp = "";
		}
		// Ha az ok gombra nyomott �s megadta a nev�t akkor be�ll�tjuk
		if(!temp.equals("")) {
			// Ha nem adott meg semmit akkor marad a null n�vnek amit a scoreboard oszt�ly kezel
			player.setName(temp);
		}
		main.returnMainMenu(player);
	}
	
	// A score lek�rdez�se
	public String getScore() {
		return "" + score;
	}	
	
	// A j�t�k logik�ja
	public void update() throws IOException {
		// Ha nincs �ppen meg�ll�tva a j�t�k
		if(!stop) {
			// Balra-jobbra mozg�s, listenerek �ll�tgatj�k
			if(doodle.getRight()) {
				doodle.changeX(+5);
			} else if (doodle.getLeft()) {
				doodle.changeX(-5);
			}
			// Automatikus mozg�s
			doodle.changeDY((float)0.28); // L�gellen�ll�s
			doodle.changeY((int)doodle.getDY()); // Megv�ltozik a magass�g		
		}
		// Ha lees�nk v�ge a j�t�knak
		if (doodle.getY() >= WIDTH && !start) {
			gameover(score);
		}
		// A j�t�k elej�n nem tudunk leesni az els� ugr�sig
		if(start && doodle.getY() >= WIDTH) {
			doodle.setDY(-13);
		}		
		// Ha kimegy�nk balra visszaj�v�nk jobb oldalt
		if(doodle.getX() <= 0) {
			doodle.setX(WIDTH-5);
		}
		// Ha kimegy�nk jobbra visszaj�v�nk bal oldalt
		if(doodle.getX() > WIDTH) {
			doodle.setX(5);
		}		
		// Shiftel�s felfele
		if(doodle.getY() < SCROLL_HEIGHT) {
			// Megn�zz�k mely platformokat kell friss�teni
			for(int i = 0; i < pm.size(); i++) {
				doodle.setY(SCROLL_HEIGHT);
				pm.setPosition(i, pm.getX(i), pm.getY(i) - (int)doodle.getDY());
				// Ha kimegy a k�pb�l �jat gener�lunk
				if(pm.getY(i) >= HEIGHT - 35) {
		        	pm.updatePlatform(i);
		        	score++;
				}
			}
		}		
		// Megvizsg�lja hogy platformra �rkezt�nk-e ha igen aktiv�ljuk
		for(int i = 0; i < pm.size(); i++) {
			// Ha DY > 0, teh�t �ppen nem felfele megy�nk �s platformra urgunk
			if(pm.jumpedOn(i, doodle.getHitArea()) && doodle.getDY() > 0) {
				// Pl: a sima platform feldobja
				pm.getPlatform(i).update(doodle);
				// M�r haladtunk felfele �gyhogy v�ge ha lees�nk
				start = false;
			}
			// Ha feketelyukkal �ritnkezt�nk akkor v�ge a j�t�knak
			if(pm.getPlatform(i).getClass().equals(Blackhole.class) && doodle.intersect(pm.getPlatform(i).getHitArea()) && doodle.getDY() > -13) {
				gameover(score);
			}
			// A speci�lis platformokra megh�vjuk a v�ltoz�st pl: fel-le mozg� platform
			pm.getPlatform(i).change();
		}
	}
	
	// Thread implement�ci�k	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
	}
	
	public void start() {
		try {
			isRunning = true;
			gameover = false;
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public synchronized void run() {
		while(!gameover) {
			try {
				requestFocus();
				draw();
				if(start)
					JOptionPane.showMessageDialog(window,"We are about to start!","Are you ready?",JOptionPane.INFORMATION_MESSAGE); 
				while(isRunning) {
					update();
					draw();
					Thread.sleep(500/60);
				}			
			} catch(Exception e ) {
				e.printStackTrace();
			}
		}
	}
	
	public void draw() {	
		if(!gameover) {
			Graphics g = getGraphics();
			Graphics2D g2 = (Graphics2D) view.getGraphics();
			g2.drawImage(background.getTexture(), 0, 0, WIDTH, HEIGHT, null);
			pm.drawPlatforms(g2);
			doodle.draw(g2);	
			g2.drawImage(topbar.getTexture(), 0, 0, WIDTH, topbar.getHeight(), null);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Comic Sans", Font.BOLD, 20));
			g2.drawString(this.getScore(), 25, 25);		
			g.drawImage(view, 0, 0, WIDTH, HEIGHT, null);
			g.dispose();
		}
	}
	
	// KeyListener implement�ci�k
	@Override
	public void keyTyped(KeyEvent e) {		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !stop) {
			if(!doodle.getFacing().equals("right")) {
				try {
					doodle.changeFacing();
					doodle.changeTexture("doodle_R.png");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			doodle.setRight(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !stop) {
			if(doodle.getFacing().equals("right")) {
				try {
					doodle.changeFacing();
					doodle.changeTexture("doodle_L.png");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			doodle.setLeft(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_P) {
			if(!stop) {
				stop = true;
				isRunning = false;
			} else {
				stop = false;
				isRunning = true;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			doodle.setRight(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			doodle.setLeft(false);
		}
	}	
}
