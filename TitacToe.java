import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

public class TitacToe extends JFrame {
	
	 Player player1 = new Player();
	 Player player2 = new Player();
	 Player currentPlayer;
	 
	 private ScoreBoard scoreBoard;
	 private Board playBoard;
	
	public TitacToe() {
		
		player1.setSymbol("X");
		player2.setSymbol("O");
		
		player1.setName(JOptionPane.showInputDialog(null,"Enter name of first player"));	
		player2.setName(JOptionPane.showInputDialog(null,"Enter name of second player"));
		
		currentPlayer = player1;
		
		
		scoreBoard = new ScoreBoard();
		playBoard = new Board();

		
		setTitle("TitacToe");
		setLocation(500, 200);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setSize(500,500);
		add(scoreBoard, BorderLayout.NORTH);
		add(playBoard,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
	}
	
	
	private class Board extends JPanel implements ActionListener{
		
		private JButton[][] board;

		public Board() {
			
			setLayout(new GridLayout(3,3));
			displayBoard();
			}
			
			
		
		public void displayBoard() {
				board = new JButton[3][3];
			
			for ( int row = 0 ; row <  board.length ; row++ ) {
				for ( int col = 0 ; col < board[row].length ; col++ ) {
					board[row][col] = new JButton();
					board[row][col].setEnabled(true);
					board[row][col].addActionListener(this);
					add(board[row][col]);
					
				}
			}
		}
		
		
		public void clearBoard() {
			
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[row].length; col++) {
					board[row][col].setEnabled(true);
					board[row][col].setText("");
				}	
				
			}
			
		}
		

		public boolean isBoardFull() {
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[row].length;col++) {
					String buttonText = board[row][col].getText();
					if (buttonText.isEmpty()) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		
		public void promptPlayAgain() {
		 	int yesno = JOptionPane.showConfirmDialog(null, "Play Again?", "Yes or No", JOptionPane.YES_NO_OPTION);
		 	if (yesno == JOptionPane.YES_OPTION) {
		 		clearBoard();
		 	} else {
		 		System.exit(EXIT_ON_CLOSE);
		 	}
		}
		
		public boolean checkWinner(String symbol) {
			
			int count;
//-------------------------------------- Check Rows-------------------------------------------------------------------//
			for(int row = 0; row < board.length; row++) {
				count = 0;
				for(int col = 0; col < board[row].length; col++) {
					String symbolString = board[row][col].getText();
					if(symbolString.equals(symbol)) {
						count++;
						if(count == 3) {
							return true;
						}
					}else {
						break;
					}
				}
				
			}
//-------------------------------------- Check Column -------------------------------------------------------------------//
			for(int col = 0; col < board[0].length; col++) {
				count = 0;
				for(int row = 0; row < board.length; row++) {
					String symbolString = board[row][col].getText();
					if(symbolString.equals(symbol)) {
						count++;
						if(count == 3) {
							return true;
						}
					}else {
						break;
					}
				}
				
			}
			
//-------------------------------------- Check Diagonal [0][0] [1][1] [2][2]----------------------------------------------//
			count = 0;
			
			for(int i = 0; i < board.length; i++) {
				String symboString = board[i][i].getText();
				if(symboString.equals(symbol)) {
					count++;
					if(count == 3) {
						return true;
					}
				}else {
					break;
				}
			}
			
			
//-------------------------------------- Check Diagonal [2][0] [1][1] [0][2]----------------------------------------------//
			
			count = 0;
			int row = board.length - 1;
			int col = 0;
			while (row >= 0 && col < board.length) {
				String buttonText = board[row][col].getText();
				if (buttonText.equals(symbol)) {
					count++;
					if (count == 3) {
						return true;
					}
					row--;
					col++;
				} else {
					break;
				}
			}
			
			
			return false;
		}
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton clickedButton = (JButton) e.getSource();
			clickedButton.setText(currentPlayer.getSymbol());
			clickedButton.setEnabled(false);
	
			if(isBoardFull()) {
				JOptionPane.showMessageDialog(null,"The game is a draw");
				promptPlayAgain();			
			}
			
			if(checkWinner(currentPlayer.getSymbol())) {
				winnerCount(currentPlayer);
				JOptionPane.showMessageDialog(null, "The winner is "+currentPlayer.getName());
				promptPlayAgain();
				
			}
				
			switchCurrentPlayer();
				
		}
		
		public void switchCurrentPlayer() {
			if (currentPlayer.equals(player1)) {
				currentPlayer = player2;
			} else {
				currentPlayer = player1;
			}
		
			scoreBoard.setCurrentPlayer(currentPlayer.getName());
			
		}
		
		public void winnerCount(Player currentPlayer) {
			String symbolString = currentPlayer.getSymbol();
			if(symbolString.equals("X")) {
				currentPlayer.addWinNum();
				scoreBoard.setPlayer1Wins(currentPlayer.getWinNum());
			}else {
				currentPlayer.addWinNum();
				scoreBoard.setPlayer2Wins(currentPlayer.getWinNum());
			}
		}
		
		
		
	}
	
	private class ScoreBoard extends JPanel {
		
		JLabel currentPlayer;
		JLabel player1Wins;
		JLabel player2Wins;
		
		public ScoreBoard() {
			
//-------------------------------------- Top Panel-------------------------------------------------------------------//
		JPanel firstPanelPanel = new JPanel();
		
		JLabel playersJLabel = new JLabel(player1.getName().toUpperCase()+" VS "+player2.getName().toUpperCase());
		playersJLabel.setFont(new Font("Serif", Font.BOLD, 30));
		playersJLabel.setForeground(Color.white);
		
		firstPanelPanel.setBackground(new Color(156,39,176));
		firstPanelPanel.add(playersJLabel);
		
//------------------------------------------Middle Panel---------------------------------------------------------------//
		
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(1,3));
		
		JLabel player1Label = new JLabel("      Player 1 Score");
		JLabel separateJLabel = new JLabel("         ☜ ☝ ☞");
		JLabel player2Label = new JLabel("Player 2 Score");
		JLabel[]labels = {player1Label,separateJLabel,player2Label};	
		
		player1Label.setBorder(BorderFactory.createEtchedBorder());
		player2Label.setBorder(BorderFactory.createEtchedBorder());
		player1Label.setForeground(new Color(156,39,176));
		player2Label.setForeground(new Color(156,39,176));
		
		for(int i = 0; i < labels.length; i++) {
			labels[i].setFont(new Font("Serif", Font.BOLD, 20));
			secondPanel.add(labels[i]);			
		}
		secondPanel.setBackground(Color.white);
		
		
//------------------------------------------Third Panel---------------------------------------------------------------//
		JPanel thirdPanel = new JPanel();
		thirdPanel.setLayout(new GridLayout(1,3));
		
		 player1Wins = new JLabel("             "+player1.getWinNum());
		JLabel winLabel = new JLabel("              ★");
		 player2Wins = new JLabel("             "+player2.getWinNum());
		
		JLabel[]scoreLabels = {player1Wins,winLabel,player2Wins};
		
		for(int i = 0; i < scoreLabels.length; i++) {
			scoreLabels[i].setFont(new Font("Serif", Font.BOLD, 20));
			scoreLabels[i].setForeground(new Color(156,39,176));
			thirdPanel.add(scoreLabels[i]);			
		}
		thirdPanel.setBackground(Color.white);
		
		
//------------------------------------------Fourth Panel---------------------------------------------------------------//
		
		JPanel forthPanel = new JPanel();
		forthPanel.setLayout(new GridLayout(1,2));
		forthPanel.setBackground(new Color(156,39,176));
		JLabel currentPlayrJLabel = new JLabel("Current Player:");
		currentPlayrJLabel.setFont(new Font("Serif", Font.BOLD, 20));
		currentPlayrJLabel.setForeground(Color.white);
		
		currentPlayer = new JLabel(player1.getName());
		currentPlayer.setFont(new Font("Serif", Font.BOLD, 20));
		currentPlayer.setForeground(Color.white);
		
		forthPanel.add(currentPlayrJLabel);
		forthPanel.add(currentPlayer);
		
		
//------------------------------------------Main Panel---------------------------------------------------------------//
		
				
		setLayout(new GridLayout(4,0));
		add(firstPanelPanel);
		add(secondPanel);
		add(thirdPanel);
		add(forthPanel);
			
		}
		
		
		private void setCurrentPlayer(String name) {
			
			currentPlayer.setText(name);
			
		}
		
		private void setPlayer1Wins(int wins) {
			
			player1Wins.setText(("             "+wins));		
		}
		
		private void setPlayer2Wins(int wins) {
			
			player2Wins.setText(("             "+wins));		
		}
		
		
		
				
	}
	
	
	
}








	

