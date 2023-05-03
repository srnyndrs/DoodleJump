package game;

import java.io.Serializable;

// A player osztály a játékosok adatait tárolja
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int score;
	
	// Konstruktor
	public Player() {
		this.name = null;
		score = 0;
	}
	
	// Név beállítása
	public void setName(String name) { this.name = name; }
	
	// Score beállítása
	public void setScore(int score) { this.score = score; }
	
	// Név lekérdezése
	public String getName() { return name; }
	
	//Score lekérdezése
	public int getScore() { return score; }
	
}
