package com.castlecrawl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.castlecrawl.data.GameBoardData;
import com.castlecrawl.data.Player;
import com.castlecrawl.view.CombatFrame;
import com.castlecrawl.view.InfoPanel;

@SuppressWarnings("serial")
public class CustomFrame extends JFrame{

	public static ActionListener inventoryView;
	public Player playerChar = new Player();
	public GameBoardData boardData = new GameBoardData(new Dimension(playerChar.getCurrX(), playerChar.getCurrY()));
	
	public CustomFrame(String title) {
		super(title);
		
		inventoryView = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean value = false;
				for(Integer i : playerChar.getItems().values()) {
					if(i > 0) {
						value = true;
					}
				}
				
				JButton b = (JButton) arg0.getSource();
				String parentPanel = b.getParent().getClass().getSimpleName();
				System.out.println(parentPanel);
				
				if(playerChar.getItems().entrySet().size() > 0 && value) {
					JFrame inv = new JFrame();
					inv.setVisible(true);
					inv.setLayout(new GridLayout(playerChar.getItems().entrySet().size(), 1));
					
					for(Entry e: playerChar.getItems().entrySet()) {
						if(Integer.valueOf(e.getValue().toString()) > 0) {
							JButton button = new JButton();
							button.setText(e.getKey().toString() + " " + e.getValue().toString());
							inv.add(button);
							button.addActionListener( new ActionListener() {
		
								@Override
								public void actionPerformed(ActionEvent arg0) {
									InfoPanel i = null;
									CombatFrame c = null;
									if(parentPanel.equals("InfoPanel")) {
										i = (InfoPanel) b.getParent();
									} else {
										//TODO: fix getParent returning JPanel, should be returning combatframe
										c = (CombatFrame) b.getParent().getParent().getParent().getParent();
									}
									switch(e.getKey().toString()) {
									case "Potion":
										if(playerChar.getCurrHP() == playerChar.getMaxHP()) {
											if(parentPanel.equals("InfoPanel")) {
												i.notifications.setText("HP already Max");
											} else if(parentPanel.equals("JPanel")) {
												System.out.println("HP already Max");
											}
										} else {
											playerChar.setCurrHP(playerChar.getCurrHP()+1);
											playerChar.getItems().put("Potion", Integer.valueOf(e.getValue().toString())-1);
											if(parentPanel.equals("InfoPanel")) {
												i.updatePlayerStats();
											} else {
												c.player.setText(c.updatePlayerStats("Healed for 1 HP", playerChar));
											}
											inv.dispose();
										}
									}
								}
								
							});
						}
					}
					inv.pack();
				} else {
					if(parentPanel.equals("InfoPanel")) {
						InfoPanel i = (InfoPanel) b.getParent();
						i.notifications.setBackground(Color.RED);
						i.notifications.setText("No Items");
					}
				}
			}
			
		};	
	}
}
