package com.castlecrawl.data;

import java.awt.Dimension;
import java.util.Random;

public class GameBoardData {
	
	public String[][] board;
	
	private Dimension exitPos;
	private Dimension startPos;
	
	public GameBoardData(Dimension startPos) {
		this.startPos = startPos;
		board = new String[10][10];
		
		//generate exit
		Random r = new Random();
		int exitx = r.nextInt(10);
		int exity = r.nextInt(10);
		
		while(startPos.getWidth() == exitx && startPos.getHeight() == exity) {
			exitx = r.nextInt(10);
			exity = r.nextInt(10);
		}
		
		exitPos = new Dimension(exitx, exity);
		System.out.println("StartPos: " + startPos + ", ExitPos: " + exitPos);
		initBoardData();
	}
	
	public Dimension getExit() {
		return exitPos;
	}
	
	public Dimension getStart() {
		return startPos;
	}
	
	public void initBoardData() {		
		System.out.println("StartPos:" + startPos.getWidth() + ", " + startPos.getHeight());
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(startPos.getWidth() == j && startPos.getHeight() == i) {
					board[i][j] = "P";
				} else if(exitPos.width == j && exitPos.height == i) {
					board[i][j] = "X";
				} else {
					board[i][j] = ".";
				}
			}
		}
	}
	
	public void printBoardData() {
		String space = "";
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				int cellLength = board[i][j].length();
				if(cellLength == 1) {
					space = "  ";
				} else if(cellLength == 2) {
					space = " ";
				} else if(cellLength == 3) {
					space = " ";
				} else {
					space ="";
				}
				System.out.print(space + board[i][j] + space);
				if(cellLength == 4) {
					System.out.print(" ");
				}
			}
			System.out.println("");
		}
	}

}
