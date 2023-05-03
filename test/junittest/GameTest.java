package junittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import game.Game;
import game.Player;
import game.Scoreboard;
import graphics.Main;
import platforms.Platform;
import platforms.PlatformManager;
import sprites.Doodle;
import sprites.Sprite;

// Tesztosztály a játék tesztelésére
public class GameTest {		
	Doodle d;
	Game game;
	Platform p;
	PlatformManager pm;
	Player player;
	Sprite s;
	Scoreboard sb;	
	
	@Before
	public void Before() throws HeadlessException, IOException {
		pm = new PlatformManager();
		game = new Game(new JFrame(), new Main());
		sb = new Scoreboard();
		p = new Platform();
		d = new Doodle();
		player = new Player();
	}
	
	// Nem létezõ fájl esetén IOException-t várunk
	@Test(expected = Exception.class)
	public void FileNotExist() throws IOException {
		s = new Sprite("nem_letezik.png");
	}
	
	// Nem létezõ fájlra sem cserélhetünk
	@Test(expected = Exception.class)
	public void SpriteTextureChangeTest() throws IOException {
		s.changeTexture("nem_letezik.png");
	}
	
	// Elõször 10 platformot spawnolunk le és ezt ellenõrizzük
	@Test
	public void PlatformSpawnCheck() throws IOException {
		pm.spawnPlatforms();
		assertEquals(pm.size(), 10);
	}
	
	// A game thread mûködés közbeni megzavarására exceptiont várunk
	@Test (expected = Exception.class)
	public void GameStartTest() throws InterruptedException {
		game.start();
		Thread.currentThread().wait();
	}
	
	// Két platform ütközik-e
	@Test
	public void OverlapCheck() {
		p.setPosition(50, 50);
		pm.add(p);
		Rectangle rec1 = new Rectangle(50, 50, p.getWidth(), p.getHeight());
		Rectangle rec2 = new Rectangle(50,80, p.getWidth(), p.getHeight());
		assertTrue(pm.overlapCheck(rec1));
		assertFalse(pm.overlapCheck(rec2));
	}
	
	//Platformra ugrottunk-e
	@Test
	public void JumpCheck() {
		d.setPosition(50, 50);
		p.setPosition(25, 50);
		assertTrue(p.jumpedOn(d.getHitArea()));
	}
	
	// Player adatainak helyessége
	@Test
	public void PlayerTest() {
		player.setName("Gipsz Jakab");
		player.setScore(100);
		assertEquals(player.getScore(), 100);
		assertEquals(player.getName(), "Gipsz Jakab");
	}	
	
	// A scoreboard helyességének tesztje
	@Test
	public void ScoreBoardTest() {
		player.setName("Teszt Elek");
		player.setScore(0);
		sb = new Scoreboard();
		sb.add(player);
		assertEquals(sb.getValueAt(sb.size() - 1, 1), "Teszt Elek");
		assertEquals(sb.getValueAt(sb.size() - 1, 2), 0);
	}
	
	// Jobb eredmény változásának tesztelése
	@Test
	public void BetterScoreTest() {
		player.setName("Gipsz Jakab");
		player.setScore(0);
		sb.add(player);
		// Ugyanaz a játékos jobb eredményt ért el
		player.setName("Gipsz Jakab");
		player.setScore(1);
		sb.add(player);
		// A jobb eredményt kell tárolnia
		assertEquals(sb.getValueAt(sb.size() - 2, 1), "Gipsz Jakab");
		assertEquals(sb.getValueAt(sb.size() - 2, 2), 1);
	}
	
	// HitArea beállításának helyessége
	@Test
	public void HitAreaTest() {
		p.setPosition(25, 50); //Platfrom mérete: 30 x 115
		// A hitarea 20-al kevesebb mint az eredeti magasság
		Rectangle r = new Rectangle(25, 50, 115, 30-20);
		assertEquals(r, p.getHitArea());
	}	
}
