package com.castlecrawl.data;

public class Monster {

	private int currHP, maxHP, ATK, DEF, Gold;
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

	public int getATK() {
		return ATK;
	}

	public void setATK(int aTK) {
		ATK = aTK;
	}

	public int getDEF() {
		return DEF;
	}

	public void setDEF(int dEF) {
		DEF = dEF;
	}

	public int getGold() {
		return Gold;
	}

	public void setGold(int gold) {
		Gold = gold;
	}

	public String getMonsterType() {
		return monsterType;
	}

	public void setMonsterType(String monsterType) {
		this.monsterType = monsterType;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	private String monsterType, skill;
	
	public Monster(String monsterType, int HP, int DEF, int ATK, int Gold, String skill) {
		this.monsterType = monsterType;
		this.currHP = HP;
		this.maxHP = HP;
		this.ATK = ATK;
		this.DEF = DEF;
		this.Gold = Gold;
		this.skill = skill;
	}
}
