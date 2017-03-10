package com.castlecrawl.data;

import java.util.HashMap;
import java.util.Random;

public class Player {
	
	private int currX, currY, currHP, maxHP, ATK, DEF, COR, gold, currFloor;
	private String playerName, characterName;
	private HashMap<String, Integer> items;
	
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

	public void setCurrHP(int currHP) {
		this.currHP = currHP;
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
