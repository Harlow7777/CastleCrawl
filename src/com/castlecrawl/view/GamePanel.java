package com.castlecrawl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import com.castlecrawl.data.GameBoardData;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT = 200;
	private Dimension currDim;
	public GameBoardData boardData;
	public JPanel[][] panels;
	
	public GamePanel(GameBoardData boardData, Dimension currDim) {
		super();
		this.currDim = currDim;
		this.boardData = boardData;
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(10,10));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int wall = 17;
		int squarePosX = 14;
		int squarePosY = 17;
		int squareHeight = 1;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(int i = 0; i <= DEFAULT_HEIGHT; i+=20){
			for(int j = 0; j <= DEFAULT_WIDTH; j+=20) {
				try{
					g.setColor(Color.RED);
					String cell = boardData.board[i/20][j/20];
					String encounter = "";
					// cut off encounter when designing the shape
					if(cell.length() > 1) {
						encounter = cell.substring(cell.length()-1);
						cell = cell.substring(0,cell.length()-1);
					}
					if(i/20 == currDim.width && j/20 == currDim.getHeight()) {
						g.drawRect(i+7, j+7, 5, 5);
					} else {
						g.drawRect(i, j, 20, 20);
					}
					
					//add 1 to avoid lines at 0,0(that will be drawn on top of)
					j++;
					i++;
					g.setColor(Color.GREEN);
					//possible cell strings: P, X, (L, R, LR, SL, SR, 4W) + (v, ^, <, >)
					//Directions read as "From the direction", e.g. L^ = "Left from the top"
					switch(cell) {
					
					//Start point
					case "P":
						g.drawRect(j+6, i+6, 5, 5);
						break;
					//Exit point
					case "X":
						g.fillRect(j+6, i+6, 5, 5);
						break;
					case "Lv":
					case "R<":
						g.drawLine(j, i, j+wall, i);
						g.drawLine(j+wall, i, j+wall, i+wall);
						g.fillRect(j, i+squarePosY, 5, squareHeight);
						break;
					case "L^":
					case "R>":
						g.drawLine(j, i, j, i+wall);
						g.drawLine(j, i+wall, j+wall, i+wall);
						g.fillRect(j+squarePosX, i, 5, squareHeight);
						break;
					case "L<":
					case "R^":
						g.drawLine(j+wall, i, j+wall, i+wall);
						g.drawLine(j, i+wall, j+wall, i+wall);
						g.fillRect(j, i, 5, squareHeight);
						break;
					case "L>":
					case "Rv":
						g.drawLine(j, i, j, i+wall);
						g.drawLine(j, i, j+wall, i);
						g.fillRect(j+squarePosX, i+squarePosY, 5, squareHeight);
						break;
					case "Sv":
					case "S^":
						g.drawLine(j, i, j, i+wall);
						g.drawLine(j+wall, i, j+wall, i+wall);
						break;
					case "S<":
					case "S>":
						g.drawLine(j, i, j+wall, i);
						g.drawLine(j, i+wall, j+wall, i+wall);
						break;
					case "SR>":
					case "SL<":
					case "LR^":
						g.fillRect(j, i, 5, squareHeight);
						g.fillRect(j+squarePosX, i, 5, squareHeight);
						g.drawLine(j, i+wall, j+wall, i+wall);
						break;
					case "SR<":
					case "SL>":
					case "LRv":
						g.fillRect(j, i+squarePosY, 5, squareHeight);
						g.fillRect(j+squarePosX, i+squarePosY, 5, squareHeight);
						g.drawLine(j, i, j+wall, i);
						break;
					case "SR^":
					case "SLv":
					case "LR<":
						g.fillRect(j, i, 5, squareHeight);
						g.fillRect(j, i+squarePosY, 5, squareHeight);
						g.drawLine(j+wall, i, j+wall, i+wall);
						
						break;
					case "SRv":
					case "SL^":
					case "LR>":
						g.fillRect(j+squarePosX, i, 5, squareHeight);
						g.fillRect(j+squarePosX, i+squarePosY, 5, squareHeight);
						g.drawLine(j, i, j, i+wall);
						break;
					case "4W^":
					case "4Wv":
					case "4W<":
					case "4W>":
						g.fillRect(j, i, 5, squareHeight);
						g.fillRect(j+squarePosX, i, 5, squareHeight);
						g.fillRect(j, i+squarePosY, 5, squareHeight);
						g.fillRect(j+squarePosX, i+squarePosY, 5, squareHeight);
						break;
					}
					
					switch(encounter) {
					case "M":
						g.drawLine(j+6, i+10, j+6, i+6);
						g.drawLine(j+6, i+6, j+8, i+8);
						g.drawLine(j+8, i+8, j+10, i+6);
						g.drawLine(j+10, i+6, j+10, i+10);
						break;
					//dead monster
					case "Z":
						g.drawLine(j+6, i+10, j+6, i+6);
						g.drawLine(j+6, i+6, j+8, i+8);
						g.drawLine(j+8, i+8, j+10, i+6);
						g.drawLine(j+10, i+6, j+10, i+10);
						g.drawLine(j+4, i+4, j+12, i+12);
						g.drawLine(j+12, i+4, j+4, i+12);
						break;
					case "H":
						g.drawOval(j+6, i+6, 5, 5);
						break;
					case "T":
						g.drawLine(j+8, i+6, j+4, i+12);
						g.drawLine(j+4, i+12, j+12, i+12);
						g.drawLine(j+12, i+12, j+8, i+6);
						break;
					//disarmed trap
					case "D":
						g.drawLine(j+8, i+6, j+4, i+12);
						g.drawLine(j+4, i+12, j+12, i+12);
						g.drawLine(j+12, i+12, j+8, i+6);
						g.drawLine(j+4, i+4, j+12, i+12);
						g.drawLine(j+12, i+4, j+4, i+12);
						break;
					}
					
					j--;
					i--;
				} catch(Exception e) {
				}
			}
		}
	}
	
	public void redraw() {
		this.repaint();
	}
	
	
}
