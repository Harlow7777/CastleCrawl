package com.castlecrawl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.castlecrawl.data.GameBoardData;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel{

	public JButton up = new JButton("^");
	public JButton down = new JButton("v");
	public JButton left = new JButton("<");
	public JButton right = new JButton(">");
	public JButton shop = new JButton("Shop");
	public JTextArea notifications = new JTextArea();
	private JTextArea stats = new JTextArea();
	private JTextArea items = new JTextArea();

	public InfoPanel(GameBoardData boardData) {
		super();
		
		this.setVisible(true);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(200, 200));
	}
	
	public void initText() {
		GameBoard g = (GameBoard) SwingUtilities.getWindowAncestor(this);
		
		shop.setBackground(Color.WHITE);
		shop.setEnabled(false);
		shop.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateShopList();
			}
			
		});
		
		JButton inventory = new JButton("Inventory");
		inventory.setBackground(Color.WHITE);
		inventory.addActionListener( g.inventoryView );
		
		JPanel navigation = new JPanel();
		navigation.setBackground(Color.WHITE);
		navigation.setPreferredSize(new Dimension(75, 75));
		
		JPanel notify = new JPanel();
		notify.setPreferredSize(new Dimension(250, 30));

		notifications.setEditable(false);

		JTextArea nameLabel = new JTextArea();
		nameLabel.setEditable(false);
		nameLabel.setText("Player: ");
		
		JTextArea charNameLabel = new JTextArea();
		charNameLabel.setEditable(false);
		charNameLabel.setText("Character: ");
		
		JTextField nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(100, 20));
		nameField.setToolTipText("Your Name");
		
		AbstractAction enterName = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	JTextField source = (JTextField)e.getSource();
		    	if(!source.getText().trim().equals("")){
		    		
		    		g.playerChar.setPlayerName(source.getText());
		    		source.setEnabled(false);
		    		source.setBackground(Color.WHITE);
		    	} else {
		    		source.setBackground(Color.RED);
		    		notifications.setText("Enter a valid name");
		    	}
		    }
		};
		
		AbstractAction enterCharName = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	JTextField source = (JTextField)e.getSource();
		    	if(!source.getText().trim().equals("")){
		    		g.playerChar.setCharacterName(source.getText());
		    		source.setEnabled(false);
		    		source.setBackground(Color.WHITE);
		    	} else {
		    		source.setBackground(Color.RED);
		    		notifications.setText("Enter a valid name");
		    	}
		    }
		};
		
		nameField.addActionListener( enterName );
		
		JTextField charNameField = new JTextField();
		charNameField.setMaximumSize(new Dimension(100, 20));
		charNameField.setToolTipText("Your Character's Name");
		
		charNameField.addActionListener( enterCharName );
		
		updatePlayerStats();
		
		JButton play = new JButton();
		play.setText("Play!");
		play.setToolTipText("Enter player and character name");
		play.setBackground(Color.WHITE);
		play.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!charNameField.isEnabled() && !nameField.isEnabled()) {
					//replace play button with arrow keys
					play.setVisible(false);
					up.setVisible(true);
					down.setVisible(true);
					left.setVisible(true);
					right.setVisible(true);
					notifications.setText("Begin!");
					redraw();
				} else {
					play.setBackground(Color.RED);
				}
			}
			
		});
		
		int buttonSize = 25;
		up.setBackground(Color.WHITE);
		up.setVisible(false);
		up.setPreferredSize(new Dimension(buttonSize,buttonSize));

		down.setBackground(Color.WHITE);
		down.setVisible(false);
		down.setPreferredSize(new Dimension(buttonSize,buttonSize));
		
		left.setBackground(Color.WHITE);
		left.setVisible(false);
		left.setPreferredSize(new Dimension(buttonSize,buttonSize));
		
		right.setBackground(Color.WHITE);
		right.setVisible(false);
		right.setPreferredSize(new Dimension(buttonSize,buttonSize));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		c.gridx = 0;
		this.add(nameLabel, c);
		c.gridx = 2;
		this.add(nameField, c);
		c.gridx = 0;
		c.gridy = 2;
		this.add(charNameLabel, c);
		c.gridx = 2;
		this.add(charNameField, c);
		c.gridx = 0;
		c.gridy = 3;
		this.add(stats, c);
		c.gridx = 2;
		c.fill = GridBagConstraints.VERTICAL;
		this.add(inventory, c);
		c.gridy = 4;
		this.add(shop, c);
		c.gridy = 4;
		c.gridx = 0;
		this.add(play, c);
		this.add(navigation, c);
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.EAST;
		this.add(notify, c);
		
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints navC = new GridBagConstraints();
		navC.gridx = 1;
		navC.gridy = 0;
		navigation.add(up, navC);
		navC.gridy = 2;
		navigation.add(down, navC);
		navC.gridx = 2;
		navC.gridy = 1;
		navigation.add(right, navC);
		navC.gridx = 0;
		navigation.add(left, navC);
		
		notify.add(notifications);
	}
	
	public void generateShopList() {
		GameBoard g = (GameBoard) SwingUtilities.getWindowAncestor(this);
		notifications.setText("Shop");
		shop.setEnabled(false);
		g.toggleNavigation(false);
		
		JFrame shopFrame = new JFrame("Shop");
		shopFrame.setUndecorated(true);
		shopFrame.setResizable(false);
		shopFrame.setVisible(true);
		String[] options = new String[]{"Leave", "Potion 20", "Lockpick 20", "Antidote 20", "Bomb 30", "Longsword 60", "Flail 60", "Shield 60", "Cloak 70", "Compass 80", "Poison 80", "SpikeShield 100", "InvulnerabilityHelm 50", "DarkKnightHelm 100"};
		shopFrame.setLayout(new GridLayout(options.length, 1));

		for(String item : options) {
			String itemName = item.split(" ")[0];
			
			JButton button = new JButton(item);
			button.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int cost = 0;
					if(item.contains(" ")) 
						cost = Integer.valueOf(item.split(" ")[1]);
					int amount = 1;
					if(item.equals("Leave")) {
						g.toggleNavigation(true);
						updatePlayerStats();
						shopFrame.dispose();
						shop.setEnabled(true);
					} else {
						if(g.playerChar.getItems().containsKey(item.split(" ")[0])) {
							amount += g.playerChar.getItems().get(item.split(" ")[0]);
						}
						if(g.playerChar.getGold() >= cost) {
							g.playerChar.getItems().put(item.split(" ")[0], amount);
							g.playerChar.setGold(g.playerChar.getGold()-cost);
							if(itemName.equals("Longsword")) {
								g.playerChar.setATK(g.playerChar.getATK()+1);
							} else if(itemName.equals("Shield")) {
								g.playerChar.setDEF(g.playerChar.getDEF()+1);
							}
						} else {
							notifications.setText("Can't afford");
						}
						shopFrame.dispose();
						generateShopList();
					}
					updatePlayerStats();
				}
			});
			if(item.contains(" ")) {
				if(g.playerChar.getGold() < Integer.valueOf(item.split(" ")[1]))
					button.setEnabled(false);
			}		
			shopFrame.add(button);
		}
		shopFrame.pack();
		updatePlayerStats();
	}
	
	public void updatePlayerStats() {
		GameBoard g = (GameBoard) SwingUtilities.getWindowAncestor(this);

		stats.setEditable(false);
		StringBuilder str = new StringBuilder();
		str.append("Current Floor: " + g.playerChar.getCurrFloor() + '\n');
		str.append("HP: " + g.playerChar.getCurrHP() + "/" + g.playerChar.getMaxHP());
		str.append('\n');
		str.append("ATK: " + g.playerChar.getATK() + " DEF: " + g.playerChar.getDEF());
		str.append('\n');
		str.append("COR: " + g.playerChar.getCOR());
		str.append('\n');
		str.append("Gold: " + g.playerChar.getGold());
		stats.setText(str.toString());
		
		items.setEditable(false);
		str = new StringBuilder();
		for(Entry e : g.playerChar.getItems().entrySet()) {
			str.append(e.getKey() + ":" + e.getValue());
			str.append('\n');
		}
		items.setText("Items: " + '\n' + str.toString());
		this.redraw();
	}
	
	public void redraw() {
		this.repaint();
		System.out.println("InfoPanel has been redrawn");
	}
}
