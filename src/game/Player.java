package game;

import java.io.Serializable;

// A player oszt�ly a j�t�kosok adatait t�rolja
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int score;
	
	// Konstruktor
	public Player() {
		this.name = null;
		score = 0;
	}
	
	// N�v be�ll�t�sa
	public void setName(String name) { this.name = name; }
	
	// Score be�ll�t�sa
	public void setScore(int score) { this.score = score; }
	
	// N�v lek�rdez�se
	public String getName() { return name; }
	
	//Score lek�rdez�se
	public int getScore() { return score; }
	
}
