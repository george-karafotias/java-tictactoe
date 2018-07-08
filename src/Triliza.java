import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Triliza extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private final int PCTURN = 1;
	private final int PERSONTURN = 2;
	private final int PCWIN = 1;
	private final int PERSONWIN = 2;
	private final String PC = "X";
	private final String PERSON = "O";
	private final int BOARDSIZE = 3;
	
	private int pcWins;
	private int personWins;
	private int turn;
	private boolean gameStarted;
	
	private JButton[][] board;
	private JButton startButton;
	private JLabel turnLabel;
	private JLabel pcWinsLabel;
	private JLabel personWinsLabel;
	private Timer timer;
	
	public Triliza() {
		pcWins = 0;
		personWins = 0;
		
		startButton = new JButton("START");
		startButton.addActionListener(this);
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(3,3));
		board = new JButton[3][3];
		for (int i=0; i<BOARDSIZE; i++) {
			for (int j=0; j<BOARDSIZE; j++) {
				board[i][j] = new JButton();
				board[i][j].addActionListener(this);
				boardPanel.add(board[i][j]);
			}
		}
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 40, 10));
		
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(2,3,5,5));
		scorePanel.add(new JLabel("PC: "));
		scorePanel.add(new JLabel(PC));
		pcWinsLabel = new JLabel(Integer.toString(pcWins));
		scorePanel.add(pcWinsLabel);
		scorePanel.add(new JLabel("YOU: "));
		scorePanel.add(new JLabel(PERSON));
		personWinsLabel = new JLabel(Integer.toString(personWins));
		scorePanel.add(personWinsLabel);
		
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new FlowLayout());
		turnPanel.add(new JLabel("NOW PLAYING: "));
		turnLabel = new JLabel("-");
		turnPanel.add(turnLabel);
		
		topPanel.add(scorePanel);
		topPanel.add(startButton);
		topPanel.add(turnPanel);
		
		this.setTitle("TicTacToe");
		this.setLayout(new BorderLayout(10,10));
		this.setSize(450, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(boardPanel, BorderLayout.CENTER);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private String turnToChar(int turn) {
		if (turn == PCTURN)
			return PC;
		return PERSON;
	}
	
	private void startGame() {
		clearGame();
		
		Random rand = new Random();
	    turn = rand.nextInt((2 - 1) + 1) + 1;
	    turnLabel.setText(turnToChar(turn));
	    
	    gameStarted = true;
	    if (turn == PCTURN)
	    	pcTurn();
	}
	
	private void clearGame() {
		for (int i=0; i<BOARDSIZE; i++)
			for (int j=0; j<BOARDSIZE; j++)
				board[i][j].setText("");
		
		turnLabel.setText("-");
	}
	
	private int checkGameOver() {
		String[] board = convertBoard();
		
		int winner = 0;
		if (board[0].equals(PC) && board[1].equals(PC) && board[2].equals(PC))
			winner = PCWIN;
		else if (board[0].equals(PERSON) && board[1].equals(PERSON) && board[2].equals(PERSON))
			winner = PERSONWIN;
		else if (board[3].equals(PC) && board[4].equals(PC) && board[5].equals(PC))
			winner = PCWIN;
		else if (board[3].equals(PERSON) && board[4].equals(PERSON) && board[5].equals(PERSON))
			winner = PERSONWIN;
		else if (board[6].equals(PC) && board[7].equals(PC) && board[8].equals(PC))
			winner = PCWIN;
		else if (board[6].equals(PERSON) && board[7].equals(PERSON) && board[8].equals(PERSON))
			winner = PERSONWIN;
		else if (board[0].equals(PC) && board[3].equals(PC) && board[6].equals(PC))
			winner = PCWIN;
		else if (board[0].equals(PERSON) && board[3].equals(PERSON) && board[6].equals(PERSON))
			winner = PERSONWIN;
		else if (board[1].equals(PC) && board[4].equals(PC) && board[7].equals(PC))
			winner = PCWIN;
		else if (board[1].equals(PERSON) && board[4].equals(PERSON) && board[7].equals(PERSON))
			winner = PERSONWIN;
		else if (board[2].equals(PC) && board[5].equals(PC) && board[8].equals(PC))
			winner = PCWIN;
		else if (board[2].equals(PERSON) && board[5].equals(PERSON) && board[8].equals(PERSON))
			winner = PERSONWIN;
		else if (board[0].equals(PC) && board[4].equals(PC) && board[8].equals(PC))
			winner = PCWIN;
		else if (board[0].equals(PERSON) && board[4].equals(PERSON) && board[8].equals(PERSON))
			winner = PERSONWIN;
		else if (board[2].equals(PC) && board[4].equals(PC) && board[6].equals(PC))
			winner = PCWIN;
		else if (board[2].equals(PERSON) && board[4].equals(PERSON) && board[6].equals(PERSON))
			winner = PERSONWIN;
			
		if (winner == 0) {
			int free = getNumberOfFreeOptions();
			if (free == 0) winner = -1;
		}
		
		return winner;
	}
	
	private void gameIsOver(int winner) {
		gameStarted = false;
		
		String message = "NO WINNER";
		if (winner == PCWIN) {
			message = "YOU LOST.";
			pcWins++;
		} else if (winner == PERSONWIN) {
			message = "YOU WON!";
			personWins++;
		}
			
		JOptionPane.showMessageDialog(this, message);
		
		clearGame();
		pcWinsLabel.setText(Integer.toString(pcWins));
		personWinsLabel.setText(Integer.toString(personWins));
	}
	
	private int getNumberOfFreeOptions() {
		int free = 0;
		for (int i=0; i<BOARDSIZE; i++)
			for (int j=0; j<BOARDSIZE; j++)
				if (board[i][j].getText().length() == 0)
					free++;
		
		return free;
	}
	
	private void pcTurn() {
		if (!gameStarted) return;
		
		turn = PCTURN;
		turnLabel.setText(turnToChar(turn));
		turnLabel.validate();
		
		timer = new Timer(300, this);
		timer.setRepeats(false);
		timer.start();
	}
	
	private String[] convertBoard() {
		String[] currentBoard = new String[BOARDSIZE*BOARDSIZE];
		int index = 0;
		for (int i=0; i<BOARDSIZE; i++) {
			for (int j=0; j<BOARDSIZE; j++) {
				currentBoard[index] = board[i][j].getText();
				index++;
			}
		}
		
		return currentBoard;
	}
	
	private JButton makeSelection(String[] currentBoard) {
		JButton selection = null;
		
		selection = checkHorizontalWinSelection(currentBoard, PC);
		if (selection != null) return selection;
		
		selection = checkVerticalWinSelection(currentBoard, PC);
		if (selection != null) return selection;
		
		selection = checkDiagonalWinSelection(currentBoard, PC);
		if (selection != null) return selection;
		
		selection = checkHorizontalWinSelection(currentBoard, PERSON);
		if (selection != null) return selection;
		
		selection = checkVerticalWinSelection(currentBoard, PERSON);
		if (selection != null) return selection;
		
		selection = checkDiagonalWinSelection(currentBoard, PERSON);
		if (selection != null) return selection;
	
		if (currentBoard[4].length() == 0) return board[1][1];
		if (currentBoard[0].length() == 0) return board[0][0];
		if (currentBoard[2].length() == 0) return board[0][2];
		if (currentBoard[6].length() == 0) return board[2][0];
		if (currentBoard[8].length() == 0) return board[2][2];
		
		for (int i=0; i<BOARDSIZE; i++)
			for (int j=0; j<BOARDSIZE; j++)
				if (board[i][j].getText().length() == 0)
					return board[i][j];
		
		return selection;
	}
	
	private JButton checkHorizontalWinSelection(String[] currentBoard, String player) {
		JButton selection = null;
		
		if (currentBoard[0].equals(player) && currentBoard[1].equals(player) && currentBoard[2].length() == 0)
			selection = board[0][2];
		else if (currentBoard[0].equals(player) && currentBoard[1].length() == 0 && currentBoard[2].equals(player))
			selection = board[0][1];
		else if (currentBoard[0].length() == 0 && currentBoard[1].equals(player) && currentBoard[2].equals(player))
			selection = board[0][0];
		else if (currentBoard[3].equals(player) && currentBoard[4].equals(player) && currentBoard[5].length() == 0)
			selection = board[1][2];
		else if (currentBoard[3].equals(player) && currentBoard[4].length() == 0 && currentBoard[5].equals(player))
			selection = board[1][1];
		else if (currentBoard[3].length() == 0 && currentBoard[4].equals(player) && currentBoard[5].equals(player))
			selection = board[1][0];
		else if (currentBoard[6].equals(player) && currentBoard[7].equals(player) && currentBoard[8].length() == 0)
			selection = board[2][2];
		else if (currentBoard[6].equals(player) && currentBoard[7].length() == 0 && currentBoard[8].equals(player))
			selection = board[2][1];
		else if (currentBoard[6].length() == 0 && currentBoard[7].equals(player) && currentBoard[8].equals(player))
			selection = board[2][0];
		
		return selection;
	}
	
	private JButton checkVerticalWinSelection(String[] currentBoard, String player) {
		JButton selection = null;
		
		if (currentBoard[0].equals(player) && currentBoard[3].equals(player) && currentBoard[6].length() == 0)
			selection = board[2][0];
		else if (currentBoard[0].equals(player) && currentBoard[3].length() == 0 && currentBoard[6].equals(player))
			selection = board[1][0];
		else if (currentBoard[0].length() == 0 && currentBoard[3].equals(player) && currentBoard[6].equals(player))
			selection = board[0][0];
		else if (currentBoard[1].equals(player) && currentBoard[4].equals(player) && currentBoard[7].length() == 0)
			selection = board[2][1];
		else if (currentBoard[1].equals(player) && currentBoard[4].length() == 0 && currentBoard[7].equals(player))
			selection = board[1][1];
		else if (currentBoard[1].length() == 0 && currentBoard[4].equals(player) && currentBoard[7].equals(player))
			selection = board[0][1];
		else if (currentBoard[2].equals(player) && currentBoard[5].equals(player) && currentBoard[8].length() == 0)
			selection = board[2][2];
		else if (currentBoard[2].equals(player) && currentBoard[5].length() == 0 && currentBoard[8].equals(player))
			selection = board[1][2];
		else if (currentBoard[2].length() == 0 && currentBoard[5].equals(player) && currentBoard[8].equals(player))
			selection = board[0][2];
		
		return selection;
	}
	
	private JButton checkDiagonalWinSelection(String[] currentBoard, String player) {
		JButton selection = null;
		
		if (currentBoard[0].equals(player) && currentBoard[4].equals(player) && currentBoard[8].length() == 0)
			selection = board[2][2];
		else if (currentBoard[0].equals(player) && currentBoard[4].length() == 0 && currentBoard[8].equals(player))
			selection = board[1][1];
		else if (currentBoard[0].length() == 0 && currentBoard[4].equals(player) && currentBoard[8].equals(player))
			selection = board[0][0];
		else if (currentBoard[2].equals(player) && currentBoard[4].equals(player) && currentBoard[6].length() == 0)
			selection = board[2][0];
		else if (currentBoard[2].equals(player) && currentBoard[4].length() == 0 && currentBoard[6].equals(player))
			selection = board[1][1];
		else if (currentBoard[2].length() == 0 && currentBoard[4].equals(player) && currentBoard[6].equals(player))
			selection = board[0][2];
		
		return selection;
	}
	
	private void personTurn(int i, int j) {
		if (!gameStarted) return;
		if (turn == PCTURN) return;
		
		board[i][j].setText(PERSON);
		
		int winner = checkGameOver();
		if (winner == 0) {
			pcTurn();
		} else {
			gameIsOver(winner);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			startGame();
		} else if (e.getSource() == timer) {
			String[] currentBoard = convertBoard();		
			JButton selection = makeSelection(currentBoard);
			selection.setText(PC);
			
			int winner = checkGameOver();
			if (winner == 0) {
				turn = PERSONTURN;
				turnLabel.setText(turnToChar(turn));
				turnLabel.validate();
				
				int free = getNumberOfFreeOptions();
				if (free == 1) {
					for (int i=0; i<BOARDSIZE; i++)
						for (int j=0; j<BOARDSIZE; j++)
							if (board[i][j].getText().length() == 0) {
								personTurn(i,j);
								return;
							}
				}
			} else {
				gameIsOver(winner);
			}
		} else {
			for (int i=0; i<BOARDSIZE; i++)
				for (int j=0; j<BOARDSIZE; j++)
					if (e.getSource() == board[i][j] && board[i][j].getText().length() == 0) {
						personTurn(i,j);
						return;
					}
		}
	}
	
	public static void main(String[] args) {
		new Triliza().setVisible(true);
	}
}
