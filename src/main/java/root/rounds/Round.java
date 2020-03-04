package main.java.root.rounds;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.java.application.GridButton;
import main.java.model.Mode;
import main.java.model.Setting;
import main.java.root.Game;
import main.java.root.Qnet;

public abstract class Round {
	
	protected static Scanner scanner = new Scanner(System.in);
	protected int[] board;
	protected static int[][] lines = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}}; 
	protected int moveCounter = 0;
	protected int delay = 0;
	protected int result = 0;
	protected boolean gameEnded = false;
	protected Random random = new Random();
	protected Object lock = new Object();
	protected ArrayList<int[]> history = new ArrayList<>();
	
	protected ArrayList<Integer> moveHistory = new ArrayList<>();
	protected ArrayList<GridButton> buttons = new ArrayList<>();
	protected Game game;
	protected boolean xStarts;
	protected Mode p1;
	protected Mode p2;
	protected int epsilon;
	protected int epsilonDim = 100000;
	protected boolean learn;
	protected boolean verboseConsole = false;
	
	public Round(Game game) {
		this.game = game;
		buttons = (ArrayList<GridButton>) Setting.getButtons();
		this.p1 = Setting.getPlayerOne();
		this.p2 = Setting.getPlayerTwo();
		this.xStarts = Setting.xStarts();
		delay = Setting.getSpeed();
		this.epsilon = epsilonDim - ((int) (Setting.getEpsilon() * epsilonDim));
		this.learn = Setting.getLearn();
		this.verboseConsole = Setting.cmd();
	}
	
	public void botStep() {
		
	}
	public void step(int st) {
		
	}
	
	protected int getPolicy() {
		int m = 0;
		int r = random.nextInt(epsilonDim);
		if (moveCounter%2!=0) { // o
			if (epsilon < r) {
				m = Qnet.getRandomPolicy(board);
			} else {
				if (p2 == Mode.MAX) {
					m = Qnet.getMinPolicy(board);
				} else if (p2 == Mode.MIN) {
					m = Qnet.getMaxPolicy(board);
				} else if (p2 == Mode.RANDOM) {
					m = Qnet.getRandomPolicy(board);
				}
			}
		} else {
			if (epsilon < r) {
				m = Qnet.getRandomPolicy(board);
			} else {
				if (p1 == Mode.MAX) {
					m = Qnet.getMaxPolicy(board);
				} else if (p1 == Mode.MIN) {
					m = Qnet.getMinPolicy(board);
				} else if (p1 == Mode.RANDOM) {
					m = Qnet.getRandomPolicy(board);
				}
			}
		}
		return m;
	}
	
	protected synchronized void shortDelay() {
		try {

			Thread.currentThread();
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected boolean checkWinner() {
		if (hasWinner()) {
			String player = "";
			if (xStarts) {
				player = (moveCounter % 2 == 0) ? "o" : "x";
			} else {
				player = (moveCounter % 2 == 0) ? "x" : "o";
			}
			if (verboseConsole) {
				System.out.println("winner is " + player + "!");
			}
			gameEnded = true;
			return true;
		}
		return false;
	}
	
	protected String printBoard(boolean xStarts) {
		StringBuilder bld = new StringBuilder();
		String line = " -------------" + "\n";
		bld.append(line);
		for (int i = 0; i < board.length; i++) {
			bld.append(" , " + board[i] + " ,");
			if ((i+1)%3 == 0) {
				bld.append("\n");
				bld.append(line);
			}
		}
		if (!xStarts) {
			return bld.toString().replaceAll("0", " ").replaceAll(", ,", "|").replaceAll(",", "|").replaceAll("-1", "x").replaceAll("1", "o");
		}
		return bld.toString().replaceAll("0", " ").replaceAll(", ,", "|").replaceAll(",", "|").replaceAll("-1", "o").replaceAll("1", "x");
	}

	protected boolean hasWinner() {
		for (int i = 0; i < lines.length; i++) {
			int lin = 0;
			for (int j = 0; j < 3; j++) {
				lin += (board[lines[i][j]]);
			}
			if (lin == 3) {
				if (xStarts) {
					result = 1;
				} else {
					result = -1;
				}
				return true;
			} else if (lin == -3) {
				if (xStarts) {
					result = -1;
				} else {
					result = 1;
				}
				return true;
			}
		}
		return false;
	}

	protected boolean move(int m) {
		
		if (m >= 0 && m < 10 && board[m] == 0) {
			if(learn) {
				history.add(board.clone());
				moveHistory.add(m);
			}
			board[m] = (moveCounter % 2 == 0) ? 1 : -1;
			
			String sym = (moveCounter % 2 == 0) ? "x" : "o";
			setSymbol(m, sym, xStarts);
			
			moveCounter++;
			return true;
		} 
		return false;
	}
	
	protected void prepareGui() {
		if (!buttons.isEmpty() && !verboseConsole) {
			for (GridButton b : buttons) {
				b.prepareRound(this);
				b.disable(false);
			}
		}
	}
	
	protected void setCursor(boolean wait) {
		if (!buttons.isEmpty() && !verboseConsole) {
			for (GridButton b : buttons) {
				b.setCursor(wait);
			}
		}
	}
		
	protected void setSymbol(int id, String s, boolean xStarts) {
		if (!buttons.isEmpty() && !verboseConsole) {
			buttons.get(id).setSymbol(s, xStarts);
		}
	}
	
	public String prompt() {
		System.out.print("next move: ");
		return scanner.nextLine();
	}
	
	protected void end() {
		if (learn) {
			history.add(board.clone());
			Qnet.feedGame(history, moveHistory, result);
		}
		game.updateResults(result);
		gameEnded = true;
	}

}
