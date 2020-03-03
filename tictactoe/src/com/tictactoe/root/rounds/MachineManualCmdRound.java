package com.tictactoe.root.rounds;

import com.tictactoe.root.Game;

public class MachineManualCmdRound extends Round {
	
	protected boolean moveLock = true;

	public MachineManualCmdRound(Game game) {
		super(game);
		board = new int[9];	
		playRound();
	}

	private void playRound() {
		if (!p1.isManual()) {
			botStep();
		} else {
			moveLock = false;
			step();
		}
		
		while (moveCounter <9 && !gameEnded) {
			step();
		}
	}
	
	private void step() {
		if (!gameEnded && !moveLock) {
			int m = 0;
			
			try {
				m = Integer.parseInt(prompt()) - 1;
			} catch (Exception e) {
			}
			
			boolean success = move(m);
			if (success) {
				if (verboseConsole) {
					System.out.println(printBoard(xStarts));
				}
				if (checkWinner() || moveCounter == 9) {
					end();
				} else {
					moveLock = true;
					botStep();
				}
			}
		}
	}
	
	@Override
	public void botStep() {	
		if (!gameEnded) {
			shortDelay();
			int m = getPolicy();
			
			boolean success = move(m);
			if (success) {
				if (verboseConsole) {
					System.out.println("bot plays: " + (m+1));
					System.out.println(printBoard(xStarts));
				}
				if (checkWinner() || moveCounter == 9) {
					end();
				}
			}
			moveLock = false;
		}
	}

}
