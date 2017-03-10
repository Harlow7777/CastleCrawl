package com.castlecrawl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

import com.castlecrawl.CustomFrame;
import com.castlecrawl.data.GameBoardData;
import com.castlecrawl.data.Monster;

@SuppressWarnings("serial")
public class GameBoard extends CustomFrame{
	
	public GamePanel gridPanel;
	public InfoPanel infoPanel;
	private static final String[] squareTypes = {"L","R","S","LR","SR","SL","4W","R"};
	private static final String[] encounterTypes = {"N", "T", "M", "H"};

	public GameBoard() {
		super("Castle Crawl");
		infoPanel = new InfoPanel(boardData);
		gridPanel = new GamePanel(boardData, new Dimension(playerChar.getCurrX(),playerChar.getCurrY()));		
		initNavigation();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,2));
		start();
	}
	
	public void start() {
		setVisible(true);
		buildGUI();
	}
	
	private void buildGUI(){
		add(infoPanel);
		infoPanel.initText();
		add(gridPanel);
		pack();
	}

	private void initNavigation() {

		infoPanel.up.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int y = playerChar.getCurrY();
				String[] invalidDestTiles = new String[] {"L^", "R^", "LR^", "R>", "SR>", "L<", "SL<"};
				String[] invalidCurrTiles = new String[] {"Lv", "Rv", "LRv", "R<", "L>", "SL>", "SR<", "S<", "S>"};
				System.out.println("moving up");
				move(invalidDestTiles, invalidCurrTiles, 'v', y-1, y == 0, 'y');
			}
			
		});
		
		infoPanel.down.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int y = playerChar.getCurrY();
				String[] invalidDestTiles = new String[] {"Lv", "Rv", "LRv", "R<", "SR<", "L>", "SL>"};
				String[] invalidCurrTiles = new String[] {"L^", "R^", "LR^", "R>", "L<", "SL<", "SR>", "S<", "S>"};
				System.out.println("moving down");
				move(invalidDestTiles, invalidCurrTiles, '^', y+1, y == 9, 'y');
			}
			
		});
		
		infoPanel.left.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = playerChar.getCurrX();
				String[] invalidDestTiles = new String[] {"Lv", "R^", "Sv", "S^", "SLv", "SR^", "L<", "R<", "LR<"};
				String[] invalidCurrTiles = new String[] {"L>", "L^", "R>", "Rv", "LR>", "SL^", "SRv", "S^", "Sv"};
				System.out.println("moving left");
				move(invalidDestTiles, invalidCurrTiles, '>', x-1, x == 0, 'x');
			}
			
		});
		
		infoPanel.right.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = playerChar.getCurrX();
				String[] invalidDestTiles = new String[] {"L^", "Rv", "Sv", "S^", "SL^", "SRv", "L>", "R>", "LR>"};
				String[] invalidCurrTiles = new String[] {"L<", "Lv", "R<", "R^", "LR<", "SLv", "SR^", "Sv", "S^"};
				System.out.println("moving right");
				move(invalidDestTiles, invalidCurrTiles, '<', x+1, x == 9, 'x');
			}
			
		});
	}
	
	public void move(String[] invalidDestTiles, String[] invalidCurrTiles, Character dir, int newPos, boolean edgeCheck, Character axis) {
		infoPanel.notifications.setText("");
		infoPanel.notifications.setBackground(Color.WHITE);
		int y = playerChar.getCurrY();
		int x = playerChar.getCurrX();
		String currTile = boardData.board[y][x];
		String destTile = "";
		if(currTile.length() > 1) 
			currTile = currTile.substring(0, currTile.length()-1);
		if(edgeCheck) {
			infoPanel.notifications.setText("Edge of board");
		} else {
			if(axis == 'x')
				x = newPos;
			if(axis == 'y')
				y = newPos;
			destTile = boardData.board[y][x];
			if(destTile.length() > 1)
				destTile = destTile.substring(0, destTile.length()-1);
		}
		if(edgeCheck || Arrays.asList(invalidDestTiles).contains(destTile) || Arrays.asList(invalidCurrTiles).contains(currTile)){
			System.out.println("Invalid curr/dest tile");
		} else {
			playerChar.setCurrY(y);
			playerChar.setCurrX(x);
			System.out.println("Moving to (" + x + ", " + y + ")");
			generateSquare(dir, new Dimension(x, y));
		}
	}
	
	private void generateSquare(char entrance, Dimension currDim) {
		int x = new Double(currDim.getWidth()).intValue();
		int y = new Double(currDim.getHeight()).intValue();
		Random r = new Random();
		
		// Roll 1D8 to determine type
		String type = Arrays.asList(squareTypes).get(r.nextInt(8));
		// Roll 1D4 to determine encounter( 0 - Nothing, 1 - Trap, 2 - Monster, 3 - Shop)
		String encounter = Arrays.asList(encounterTypes).get(r.nextInt(4));
		String destCell = boardData.board[y][x];
		if(destCell.equals(".")) {
			destCell = type + entrance + encounter;
			System.out.println("Generating square " + destCell + " at X: " + x + ", Y: " + y);
		} else if(destCell.equals("X")) {
			infoPanel.up.setEnabled(false);
			infoPanel.down.setEnabled(false);
			infoPanel.left.setEnabled(false);
			infoPanel.right.setEnabled(false);
			infoPanel.notifications.setText("Congratulations!");
			
			//move to next floor
			//TODO: fix start cell not holding "P" value
			boardData = new GameBoardData(boardData.getExit());
			
			playerChar.setCurrFloor(playerChar.getCurrFloor()+1);
			infoPanel.updatePlayerStats();
			toggleNavigation(true);
			
			encounter = "";
		} else if(destCell.equals("P")) {
			infoPanel.notifications.setText("Entrance");
			remove(gridPanel);
			gridPanel = new GamePanel(boardData, new Dimension(playerChar.getCurrX(),playerChar.getCurrY()));
			add(gridPanel);
			gridPanel.redraw();
			redraw();
			boardData.printBoardData();
			return;
		} else {
			System.out.println("Found square " +destCell+ " at X: " + y + ", Y: " + x);
			encounter = destCell.substring(destCell.length()-1);
		}
		if(!encounter.equals("H")) 
			infoPanel.shop.setEnabled(false);
		remove(gridPanel);
		gridPanel = new GamePanel(boardData, new Dimension(playerChar.getCurrX(),playerChar.getCurrY()));
		add(gridPanel);
		gridPanel.redraw();
		redraw();
		boardData.printBoardData();
		encounter = generateEncounter(encounter);
		destCell = destCell.substring(0, destCell.length()-1) + encounter;
		boardData.board[y][x] = destCell;
	}
	
	private String generateEncounter(String encounter) {
		HashMap<String, Integer> tempItems = playerChar.getItems();
		switch(encounter) {
		case "T":
			// Trap deals 1 hit to player if not disarmed
			if(playerChar.getItems().containsKey("Lockpick") && playerChar.getItems().get("Lockpick") > 0) {
				infoPanel.notifications.setText("Lockpick?");
				if(JOptionPane.showConfirmDialog(this, "Use Lockpick?", "Trap", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					tempItems.put("Lockpick", tempItems.get("Lockpick")-1);
					gridPanel.redraw();
					redraw();
				} else {
					infoPanel.notifications.setText("Trapped!");
					infoPanel.notifications.setBackground(Color.RED);
					playerChar.setCurrHP(playerChar.getCurrHP()-1);
					infoPanel.updatePlayerStats();
				}
			} else {
				infoPanel.notifications.setText("Trapped!");
				infoPanel.notifications.setBackground(Color.RED);
				playerChar.setCurrHP(playerChar.getCurrHP()-1);
				infoPanel.updatePlayerStats();
			}
			if(playerChar.getCurrHP() == 0) {
				infoPanel.notifications.setText(playerChar.getCharacterName() + " died");
				toggleNavigation(false);
				int response = JOptionPane.showConfirmDialog(this, "Restart?", "Game Over", JOptionPane.YES_NO_OPTION);
				if(response == JOptionPane.NO_OPTION) {
					System.exit(ABORT);
				} else {
					//TODO: store score(player stats, current floor)
					System.out.println("Restarting");
					//TODO: fix bug where combatPanel appears after starting new instance
					dispose();
		        	new GameBoard();
				}
			} else {
				return "D";
			}
		case "M":
			Monster monster = generateMonster();
			System.out.println("Entering combat");
			combat(monster);
			return "Z";
		case "H":
			infoPanel.notifications.setText("Shop");
			infoPanel.shop.setEnabled(true);
			this.redraw();
			break;
		}
		return encounter;
	}
	
	public void toggleNavigation(boolean flag) {
		System.out.println("Toggling Navigation: " + flag);
		infoPanel.up.setEnabled(flag);
		infoPanel.down.setEnabled(flag);
		infoPanel.left.setEnabled(flag);
		infoPanel.right.setEnabled(flag);
	}
	
	private Monster generateMonster() {
		infoPanel.notifications.setText("Fight");
		// Roll 1D100 to determine monster
		Random r = new Random();
		int monsterID = r.nextInt(101);
		Monster monster = null;
		System.out.println("Monster ID: " + monsterID);
		if(monsterID >= 0 && monsterID <= 20) {
			monster = new Monster("Giant Rat", 1, 0, 0, 20, null);
		} else if(monsterID > 20 && monsterID <= 40) {
			monster = new Monster("Wolf", 2, 0, 1, 30, null);
		} else if(monsterID > 40 && monsterID <= 50) {
			monster = new Monster("Goblin", 2, 0, 0, 30, "Self Destruct");
		} else if(monsterID > 50 && monsterID <= 60) {
			monster = new Monster("Arachnis", 2, 0, 0, 50, "Poison");
		} else if(monsterID > 60 && monsterID <= 70) {
			monster = new Monster("Minor Slime", 1, 0, 0, 10, null);
		} else if(monsterID > 70 && monsterID <= 80) {
			monster = new Monster("Knight", 2, 0, 1, 50, "Joust");
		} else if(monsterID > 80 && monsterID <= 85) {
			monster = new Monster("Pupper", 1, 0, 0, 0, "Pet");
		} else if(monsterID > 85 && monsterID <= 90) {
			monster = new Monster("Mr. Skeltal", 1, 0, 0, 10, "Doot Doot");
		} else if(monsterID > 90 && monsterID <= 95) {
			monster = new Monster("Dire Slime", 1, 0, 0, 20, "Multiply");
		} else if(monsterID == 96) {
			monster = new Monster("Trader", 1, 0, 0, 0, "Gift");
		} else if(monsterID == 97) {
			monster = new Monster("Dragon", 4, 1, 1, 100, "Firebreath");
		} else if(monsterID == 98) {
			monster = new Monster("Golem", 1, 2, 1, 100, "Stoneskin");
		} else if(monsterID == 99) {
			monster = new Monster("King's Guard", 1, 2, 2, 100, "Rush");
		} else if(monsterID == 100) {
			monster = new Monster("King", 5, 0, 0, 200, "Servitude");
		}
		return monster;
	}
	
	private void combat(Monster monster) {
		toggleNavigation(false);
		CombatFrame combatFrame = new CombatFrame(monster);
		combatFrame.init(playerChar);
		combatFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent arg0) {
		        infoPanel.updatePlayerStats();
		        if(playerChar.getCurrHP() != 0) {
		        	infoPanel.notifications.setText(monster.getMonsterType() + " defeated!");
		        	toggleNavigation(true);
		        	redraw();
		        } else {
		        	dispose();
		        	new GameBoard();
		        }
			}

			@Override
			public void windowClosing(WindowEvent arg0) {}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {}

			@Override
			public void windowOpened(WindowEvent arg0) {}
		});
	}
	
	private void redraw() {
		this.revalidate();
		this.repaint();
	}
}
