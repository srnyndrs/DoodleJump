package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import game.Game;
import game.Player;
import game.Scoreboard;

public class Main {	
	private JFrame window;
	private Container con;
	private JPanel titleNamePanel, startPanel, gamePanel, scoresPanel;
	private JLabel titleNameLabel;
	private Scoreboard scoreboard;
	private JTable scoreboardTable;
	private JScrollPane scoreboardPane;
	
	// A main ami elindítja a konstruktort
	public static void main(String[] args) {
		new Main();
	}
	
	// Konstruktor
	public Main() {	
		window = new JFrame();
		scoreboard = new Scoreboard();
		window.setSize(750,800);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(new Color(18, 111, 130));
		window.setLayout(null);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		con = window.getContentPane();
		
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(0, 50, 750, 75);
		titleNamePanel.setBackground(new Color(18, 111, 130));
		
		titleNameLabel = new JLabel("Doodle Jump");
		titleNameLabel.setForeground(Color.WHITE);
		titleNameLabel.setFont(new Font("Arial", Font.BOLD, 60));
		titleNamePanel.add(titleNameLabel, BorderLayout.CENTER);
		
		startPanel = new JPanel();
		startPanel.setBounds(275,240,200,200);
		startPanel.setBackground(new Color(18, 111, 130));
		
		JButton startButton = new JButton("Game");
		startButton.setBackground(new Color(13, 34, 84));
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.setFont(new Font("Arial", Font.BOLD, 30));
		startButton.setForeground(Color.WHITE);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createGameScreen();
				} catch (IOException e1) {
					e1.printStackTrace();
				}			
			}
		});
		startPanel.add(startButton, BorderLayout.SOUTH);
	
		JButton scoreboard = new JButton("Scoreboard");
		scoreboard.setBackground(new Color(13, 34, 84));
		scoreboard.setBorderPainted(false);
		scoreboard.setFocusPainted(false);
		scoreboard.setFont(new Font("Arial", Font.BOLD, 30));
		scoreboard.setForeground(Color.WHITE);
		scoreboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openScoreBoard();		
			}
		});
		startPanel.add(scoreboard, BorderLayout.SOUTH);	
		
		JButton quit = new JButton("Quit");
		quit.setBackground(new Color(13, 34, 84));
		quit.setBorderPainted(false);
		quit.setFocusPainted(false);
		quit.setFont(new Font("Arial", Font.BOLD, 30));
		quit.setForeground(Color.WHITE);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);		
			}
		});
		startPanel.add(quit, BorderLayout.SOUTH);		

		con.add(titleNamePanel);
		con.add(startPanel);
		window.setVisible(true);
	}
	
	// Játék indítása
	public void createGameScreen() throws IOException {
		titleNamePanel.setVisible(false);
		startPanel.setVisible(false);
		
		gamePanel = new Game(window, this);
		gamePanel.setBounds(0,0,750,800); //FONTOS
		gamePanel.setBackground(new Color(18, 111, 130));
		gamePanel.setVisible(true);
		
		con.add(gamePanel);		
	}
	
	// Visszatérés a menübe a játékból
	public void returnMainMenu(Player p) {
		scoreboard.add(p);
		
		gamePanel.setVisible(false);
		gamePanel.repaint();
		con.remove(gamePanel);

		titleNamePanel.setVisible(true);

		startPanel.setVisible(true);
		
		window.revalidate();
		window.repaint();
	}
	
	// Visszatérés a menübe a scoreboard nézetbõl
	public void returnMainMenu() {
		titleNameLabel.setText("Doodle Jump");
		scoresPanel.setVisible(false);
		scoresPanel.repaint();
		con.remove(scoresPanel);

		titleNamePanel.setVisible(true);

		startPanel.setVisible(true);
		window.revalidate();
		window.repaint();
	}
	
	// Scoreboard megtekintése
	public void openScoreBoard() {
		titleNameLabel.setText("Scoreboard");
		titleNamePanel.setVisible(true);		
		startPanel.setVisible(false);
	
		scoreboardTable = new JTable(scoreboard);
		scoreboardTable.setTableHeader(null);
		scoreboardTable.setFillsViewportHeight(true);
		scoreboardTable.setGridColor(new Color(18, 111, 130));
		scoreboardTable.setFont(new Font("Arial", Font.BOLD, 30));
		scoreboardTable.setForeground(Color.WHITE);
		scoreboardTable.setBackground(new Color(18, 111, 130));
		scoreboardTable.setShowGrid(false);
		scoreboardTable.setFillsViewportHeight(true);
		scoreboardTable.setRowHeight(30);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scoreboardTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        scoreboardTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		scoreboardPane = new JScrollPane(scoreboardTable);
		scoreboardPane.setBorder(BorderFactory.createEmptyBorder());
		
		scoresPanel = new JPanel();
		scoresPanel.setBounds(5,150,725,500);
		scoresPanel.setBackground(new Color(18, 111, 130));
		scoresPanel.setLayout(new BorderLayout());
		scoresPanel.setVisible(true);
		
		JButton back = new JButton("Back");
		back.setBackground(new Color(13, 34, 84));
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setFont(new Font("Arial", Font.BOLD, 30));
		back.setForeground(Color.WHITE);
		back.setSize(new Dimension(20, 20));
		back.setVerticalTextPosition(AbstractButton.BOTTOM);
		back.setHorizontalTextPosition(AbstractButton.CENTER);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnMainMenu();		
			}
		});
		scoresPanel.add(scoreboardPane, BorderLayout.NORTH);
		scoresPanel.add(back, BorderLayout.SOUTH);
		
		con.add(scoresPanel);
		window.revalidate();
		window.repaint();		
	}
}