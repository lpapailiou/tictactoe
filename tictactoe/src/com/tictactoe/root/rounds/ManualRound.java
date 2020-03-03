package com.tictactoe.root.rounds;

import com.tictactoe.root.Game;

public class ManualRound extends Round {
	
	public ManualRound(Game game) {
		super(game);
		
		prepareGui();
		board = new int[9];	
	}
		
	@Override
	public void step(int st) {
		if (!gameEnded) {
			int m = st;
			
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
		}
	}
}
