package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

// A scoreboard felel�s a dics�s�glista megval�s�t�s��rt
public class Scoreboard extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Player> players;
	
	// Konstruktor
	public Scoreboard() {
		players = new ArrayList<>();
		load();
	}
	
	// Scoreboard hozz�ad�s
	public void add(Player p) {
		// A a n�v nem nulla �s jobb eredm�nyt �rt el akkor friss�tj�k
		if(p.getName() != null && personalHighScore(p)) {
			players.add(p);
			// Rendez�s pontsz�m szerint
			Collections.sort(players, (s0,s1)-> Integer.compare(s0.getScore(), s1.getScore()));
			// Cs�kken� rendez�s
			Collections.reverse(players);
			fireTableDataChanged();
		}
		// Elmentj�k
		save();
	}
	
	// Jobb eredm�ny ellen�rz�se
	public boolean personalHighScore(Player p) {
		boolean new_player = true;
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getName().equals(p.getName())) {
				new_player = false;
				if(players.get(i).getScore() < p.getScore()) {
					players.remove(i);
					return true;
				}
			}
		}
		if(new_player) {
			return true;
		} else return false;
	}
	
	// Scoreboard bet�lt�se
	public void load(){
		try {
			FileInputStream f = new FileInputStream("scoreboard.data");
			ObjectInputStream in = new ObjectInputStream(f);
			players = (List<Player>) in.readObject();
			in.close();						
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problema merult fel a beolvasasnal!");
		}
	}
	
	// Scoreboard m�ret�nek lek�rdez�se
	public int size() {
		return players.size();
	}
	
	// Scoreboard ment�se
	public void save(){
		try {
			FileOutputStream f = new FileOutputStream("scoreboard.data");
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(players);
			out.close();
		} catch(IOException ex) {
			System.out.println("Problema merult fel a mentesnel!");
		}		
	}
	
	// JTable implement�ci�k
	@Override
	public int getRowCount() { return players.size(); }

	@Override
	public int getColumnCount() { return 3; }
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Player player = players.get(rowIndex);
	   	switch(columnIndex) {
	   		 case 0: return rowIndex + 1; 
	    	 case 1: return player.getName();
	    	 default: return player.getScore();
	   	}
	}
}
