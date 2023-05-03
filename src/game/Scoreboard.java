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

// A scoreboard felelõs a dicsõséglista megvalósításáért
public class Scoreboard extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Player> players;
	
	// Konstruktor
	public Scoreboard() {
		players = new ArrayList<>();
		load();
	}
	
	// Scoreboard hozzáadás
	public void add(Player p) {
		// A a név nem nulla és jobb eredményt ért el akkor frissítjük
		if(p.getName() != null && personalHighScore(p)) {
			players.add(p);
			// Rendezés pontszám szerint
			Collections.sort(players, (s0,s1)-> Integer.compare(s0.getScore(), s1.getScore()));
			// Csökkenõ rendezés
			Collections.reverse(players);
			fireTableDataChanged();
		}
		// Elmentjük
		save();
	}
	
	// Jobb eredmény ellenõrzése
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
	
	// Scoreboard betöltése
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
	
	// Scoreboard méretének lekérdezése
	public int size() {
		return players.size();
	}
	
	// Scoreboard mentése
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
	
	// JTable implementációk
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
