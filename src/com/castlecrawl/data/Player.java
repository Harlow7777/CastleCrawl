package com.castlecrawl.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.castlecrawl.CustomFrame;
import com.castlecrawl.view.GameBoard;

public class Player {
	
	private int currX, currY, currHP, maxHP, ATK, DEF, COR, gold, currFloor;
	private String playerName, characterName;
	private HashMap<String, Integer> items;
	private String weaponSlot, shieldSlot, armor1, armor2, armor3, armor4, armor5;
	private List<String> skills, curses;
	
	public List<String> getCurses() {
		return curses;
	}

	public void setCurses(List<String> curses) {
		this.curses = curses;
	}
	
	public void addCurse(String curse) {
		this.getCurses().add(curse);
	}
	
	public void removeCurse(String curse) {
		this.getCurses().remove(curse);
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	
	public void addSkill(String skill) {
		this.getSkills().add(skill);
	}
	
	public void removeSkill(String skill) {
		this.getSkills().remove(skill);
	}

	public String getWeaponSlot() {
		return weaponSlot;
	}

	public void setWeaponSlot(String weaponSlot) {
		String previousWeapon = weaponSlot;
		this.weaponSlot = weaponSlot;
		switch(weaponSlot) {
		case "Longsword":
			this.setATK(this.getATK()+1);
			break;
		case "Flail":
			if(previousWeapon.equals("Longsword")) {
				this.setATK(this.getATK()-1);
			}
			//TODO: add multi enemy support
			break;
		}
	}

	public String getShieldSlot() {
		return shieldSlot;
	}

	public void setShieldSlot(String shieldSlot) {
		String previousShield = shieldSlot;
		this.shieldSlot = shieldSlot;
		switch(shieldSlot) {
		case "Shield":
			this.removeSkill("Payback");
			this.setDEF(this.getDEF()+1);
			break;
		case "SpikeShield":
			if(previousShield.equals("Shield")) {
				this.setDEF(this.getDEF()-1);
			}
			this.addSkill("Payback");
			break;
		}
	}

	public String getArmor1() {
		return armor1;
	}

	public void setArmor1(String armor1) {
		this.armor1 = armor1;
		if(armor1.contains("DarkKnight")) {
			this.setDEF(this.getDEF()-2);
			this.setCOR(this.getCOR()+1);
		}
	}

	public Player() {
		// Roll 2D10s for random starting placement
		Random r = new Random();
		currX = r.nextInt(10);
		currY = r.nextInt(10);
		maxHP = 5;
		currHP = 5;
		ATK = 1;
		DEF = 1;
		gold = 20;
		COR = 0;
		currFloor = 1;
		weaponSlot = "";
		shieldSlot = "";
		armor1 = "";
		skills = new ArrayList<String>();
		curses = new ArrayList<String>();
		
		items = new HashMap<String, Integer>();
	}
	
	public int getCurrFloor() {
		return currFloor;
	}

	public void setCurrFloor(int currFloor) {
		this.currFloor = currFloor;
	}

	public HashMap<String, String> getPlayerData() {
		HashMap<String, String> playerMap = new HashMap<String, String>();
		playerMap.put("CURRX", String.valueOf(this.getCurrX()));
		playerMap.put("Player", this.getPlayerName());
		playerMap.put("Character", this.getCharacterName());
		playerMap.put("CURRY", String.valueOf(this.getCurrY()));
		playerMap.put("HP", String.valueOf(this.getCurrHP()));
		playerMap.put("MAXHP", String.valueOf(this.getMaxHP()));
		playerMap.put("ATK", String.valueOf(this.getATK()));
		playerMap.put("DEF", String.valueOf(this.getDEF()));
		return playerMap;
	}
	
	public int getCOR() {
		return COR;
	}

	public void setCOR(int COR) {
		this.COR = COR;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public HashMap<String, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items = items;
	}

	public int getATK() {
		return ATK;
	}

	public void setATK(int ATK) {
		this.ATK = ATK;
	}

	public int getDEF() {
		return DEF;
	}

	public void setDEF(int DEF) {
		this.DEF = DEF;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP, CustomFrame g) {
		this.currHP = currHP;
		if(currHP == 0) {
			CustomFrame.gameOver(this, g);
		}
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}

}
