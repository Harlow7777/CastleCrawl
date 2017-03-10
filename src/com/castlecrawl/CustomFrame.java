package com.castlecrawl;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.castlecrawl.data.GameBoardData;
import com.castlecrawl.data.Player;

@SuppressWarnings("serial")
public class CustomFrame extends JFrame{

	public Player playerChar = new Player();
	public GameBoardData boardData = new GameBoardData(new Dimension(playerChar.getCurrX(), playerChar.getCurrY()));
	
	public CustomFrame(String title) {
		super(title);
	}
}
