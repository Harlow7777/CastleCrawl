package com.castlecrawl.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.castlecrawl.CustomFrame;
import com.castlecrawl.Main;
import com.castlecrawl.data.Monster;
import com.castlecrawl.data.Player;

@SuppressWarnings("serial")
public class CombatFrame extends JDialog{
	
	private Monster monster;
	public JTextArea player = new JTextArea();
	public JButton inventory = new JButton("Inventory");

	public CombatFrame(Monster monster, CustomFrame c) {
		super(c, "Battle");		
		this.setUndecorated(true);
		this.setVisible(true);
		this.monster = monster;
		this.setPreferredSize(new Dimension(400, 200));
		this.setResizable(false);
	}
	
	public void init(Player playerChar, CustomFrame c) {		
		boolean value = false;
		for(Integer i : playerChar.getItems().values()) {
			if(i > 0) {
				value = true;
			}
		}
		if(playerChar.getItems().size() > 0 && value) {
			inventory.setVisible(true);
		} else {
			inventory.setVisible(false);
		}
		inventory.addActionListener( CustomFrame.inventoryView );
		
		JPanel combatPanel = new JPanel();
		combatPanel.setLayout(new GridLayout(2, 3));
		
		player.setEditable(false);
		player.setText(updatePlayerStats("", playerChar));
		
		JTextArea monsterPanel = new JTextArea();
		monsterPanel.setEditable(false);
		monsterPanel.setText(updateMonsterStats("", monster));
		
		JButton defend = new JButton();
		defend.setText("Defend");
		defend.setEnabled(true);
		
		JButton attack = new JButton();
		attack.setText("Attack");
		attack.setEnabled(false);
		attack.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Roll 1D6 and add ATK mod, check against monsters defense
				Random r = new Random();
				int atk = r.nextInt(6) + playerChar.getATK() + 1;
				int def = r.nextInt(6) + monster.getDEF() + 1;
				if(atk > def) {
					System.out.println("Monster takes 1 HIT");
					monster.setCurrHP(monster.getCurrHP()-1);
					monsterPanel.setText(updateMonsterStats("Takes 1 Hit @ " + def, monster));
					player.setText(updatePlayerStats("Deals 1 HIT @ " + atk, playerChar));
					if(monster.getCurrHP() == 0) {
						dispose();
						playerChar.setGold(playerChar.getGold() + monster.getGold());
					}
					
					if(playerChar.getSkills().contains("Poison")) {
						playerChar.removeSkill("Poison");
						monster.addCurse("Poison");
					}
				} else {
					player.setText(updatePlayerStats("Failed @ " + atk, playerChar));
					monsterPanel.setText(updateMonsterStats("Blocked @ " + def, monster));
				}
				defend.setEnabled(true);
				attack.setEnabled(false);
				if(playerChar.getCurses().contains("Poison")) {
					playerChar.removeCurse("Poison");
					playerChar.setCurrHP(playerChar.getCurrHP()-1, c);
				}
			}
			
		});
		
		
		defend.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: potentially make DEF static, only attack rolls adds atk mod then checks against enemy's DEF
				// Roll 1D6 and add DEF mod, check against monsters attack
				Random r = new Random();
				int def = r.nextInt(6) + playerChar.getDEF() + 1;
				int atk = r.nextInt(6) + monster.getATK() + 1;
				if(atk > def) {
					playerChar.setCurrHP(playerChar.getCurrHP()-1, c);
					player.setText(updatePlayerStats("Takes 1 HIT @ " + def, playerChar));
					monsterPanel.setText(updateMonsterStats("Deals 1 HIT @ " + atk, monster));

					if(monster.getSkill().contains("Poison")) {
						playerChar.addCurse("Poison");
						monster.setSkill("");
					}
				} else if(playerChar.getSkills().contains("Payback") && def > atk) {
					System.out.println("Monster takes 1 HIT");
					monster.setCurrHP(monster.getCurrHP()-1);
					monsterPanel.setText(updateMonsterStats("Takes 1 Hit @ " + atk, monster));
					player.setText(updatePlayerStats("Payback @ " + def, playerChar));
					if(monster.getCurrHP() == 0) {
						dispose();
						playerChar.setGold(playerChar.getGold() + monster.getGold());
					}
				} else {
					player.setText(updatePlayerStats("Blocked @ " + def, playerChar));
					monsterPanel.setText(updateMonsterStats("Failed @ " + atk, monster));
				}
				defend.setEnabled(false);
				attack.setEnabled(true);
				if(monster.getCurses().contains("Poison")) {
					monster.setCurrHP(monster.getCurrHP()-1);
					monsterPanel.setText(updateMonsterStats("Poisoned", monster));
					player.setText(updatePlayerStats("Enemy Poisoned" + def, playerChar));
					if(monster.getCurrHP() == 0) {
						dispose();
						playerChar.setGold(playerChar.getGold() + monster.getGold());
					}
				}
			}
			
		});
		
		combatPanel.add(player);		
		combatPanel.add(monsterPanel);
		combatPanel.add(new JPanel());
		combatPanel.add(attack);
		combatPanel.add(defend);
		combatPanel.add(inventory);
		add(combatPanel);
		
		pack();
	}
	
	public String updatePlayerStats(String actionMsg, Player playerChar) {
		StringBuilder str = new StringBuilder();
		str.append(playerChar.getCharacterName() + '\n');
		str.append(playerChar.getCurrHP() + "/" + playerChar.getMaxHP() + " HP" + '\n');
		str.append("ATK: " + playerChar.getATK() + " DEF: " + playerChar.getDEF() + '\n');
		str.append(actionMsg);
		return str.toString();
	}
	
	public String updateMonsterStats(String actionMsg, Monster monster) {
		StringBuilder str = new StringBuilder();
		str.append(monster.getMonsterType() + '\n');
		str.append(monster.getCurrHP() + "/" + monster.getMaxHP() + " HP" + '\n');
		str.append("ATK: " + monster.getATK() + " DEF: " + monster.getDEF() + '\n');
		str.append("Reward: " + monster.getGold() + '\n');
		str.append(actionMsg);
		return str.toString();
	}
}
