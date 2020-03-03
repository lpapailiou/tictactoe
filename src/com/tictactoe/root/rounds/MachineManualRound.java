package com.tictactoe.root.rounds;

import com.tictactoe.root.Bot;
import com.tictactoe.root.Game;

public class MachineManualRound extends Round {
	
	private Bot bot;	
	protected boolean moveLock = true;

	public MachineManualRound(Game game) {
		super(game);
		
		prepareGui();
		board = new int[9];	
		bot = new Bot(this, lock);
		bot.start();
		bot.block(false);
		
		if (!p1.isManual()) {
			bot.requestResume();
		} else {
			moveLock = false;
		}
	}
	
	@Override
	public void step(int st) {
		if (!gameEnded && !moveLock) {
			int m = st;
			
			boolean success = move(m);
			if (success) {
				if (verboseConsole) {
					System.out.println("you played: " + (m+1));
					System.out.println(printBoard(xStarts));
				}
				if (checkWinner() || moveCounter == 9) {
					end();
				} else {
					bot.requestResume();
					moveLock = true;
					setCursor(true);
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
				bot.requestPause();
				moveLock = false;
				setCursor(false);
			}
			
		}
	}
	
	@Override
	protected void end() {
		super.end();
		bot.requestStop();
	}

}
