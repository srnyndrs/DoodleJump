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

// Game class felelõs a játék mechanikájáért
public class Game extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	// Képernyõ méretek
	public final int WIDTH = 750;
	public final int HEIGHT = 800;
	public final int SCROLL_HEIGHT = 400;
	// Játékhoz szükséges objektumok
	private Doodle doodle;
	private Player player;
	private PlatformManager pm;
	private int score;
	// Megjelenítéshez szükséges objektumok
	private JFrame window;
	private Main main;
	private Sprite topbar, background;
	private BufferedImage view;	
	// Thread és boolean változók
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
	
	// Játék vége függvény
	public void gameover(int score) {
		player.setScore(score);
		isRunning = false;
		gameover = true;
		// Játék végén megkérjük a játékost hogy írja be a nevét
		String temp = JOptionPane.showInputDialog(window,"You scored: " + score + "\nEnter your name");
		// Ha a cancel-re nyomott akkor olyanra állítjuk amit tudunk kezelni
		if(temp == null) {
			temp = "";
		}
		// Ha az ok gombra nyomott és megadta a nevét akkor beállítjuk
		if(!temp.equals("")) {
			// Ha nem adott meg semmit akkor marad a null névnek amit a scoreboard osztály kezel
			player.setName(temp);
		}
		main.returnMainMenu(player);
	}
	
	// A score lekérdezése
	public String getScore() {
		return "" + score;
	}	
	
	// A játék logikája
	public void update() throws IOException {
		// Ha nincs éppen megállítva a játék
		if(!stop) {
			// Balra-jobbra mozgás, listenerek állítgatják
			if(doodle.getRight()) {
				doodle.changeX(+5);
			} else if (doodle.getLeft()) {
				doodle.changeX(-5);
			}
			// Automatikus mozgás
			doodle.changeDY((float)0.28); // Légellenállás
			doodle.changeY((int)doodle.getDY()); // Megváltozik a magasság		
		}
		// Ha leesünk vége a játéknak
		if (doodle.getY() >= WIDTH && !start) {
			gameover(score);
		}
		// A játék elején nem tudunk leesni az elsõ ugrásig
		if(start && doodle.getY() >= WIDTH) {
			doodle.setDY(-13);
		}		
		// Ha kimegyünk balra visszajövünk jobb oldalt
		if(doodle.getX() <= 0) {
			doodle.setX(WIDTH-5);
		}
		// Ha kimegyünk jobbra visszajövünk bal oldalt
		if(doodle.getX() > WIDTH) {
			doodle.setX(5);
		}		
		// Shiftelés felfele
		if(doodle.getY() < SCROLL_HEIGHT) {
			// Megnézzük mely platformokat kell frissíteni
			for(int i = 0; i < pm.size(); i++) {
				doodle.setY(SCROLL_HEIGHT);
				pm.setPosition(i, pm.getX(i), pm.getY(i) - (int)doodle.getDY());
				// Ha kimegy a képbõl újat generálunk
				if(pm.getY(i) >= HEIGHT - 35) {
		        	pm.updatePlatform(i);
		        	score++;
				}
			}
		}		
		// Megvizsgálja hogy platformra érkeztünk-e ha igen aktiváljuk
		for(int i = 0; i < pm.size(); i++) {
			// Ha DY > 0, tehát éppen nem felfele megyünk és platformra urgunk
			if(pm.jumpedOn(i, doodle.getHitArea()) && doodle.getDY() > 0) {
				// Pl: a sima platform feldobja
				pm.getPlatform(i).update(doodle);
				// Már haladtunk felfele úgyhogy vége ha leesünk
				start = false;
			}
			// Ha feketelyukkal éritnkeztünk akkor vége a játéknak
			if(pm.getPlatform(i).getClass().equals(Blackhole.class) && doodle.intersect(pm.getPlatform(i).getHitArea()) && doodle.getDY() > -13) {
				gameover(score);
			}
			// A speciális platformokra meghívjuk a változást pl: fel-le mozgó platform
			pm.getPlatform(i).change();
		}
	}
	
	// Thread implementációk	
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
	
	// KeyListener implementációk
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
