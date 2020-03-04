package main.java.root.rounds;

import main.java.root.Game;

public class ManualCmdRound extends Round {

	public ManualCmdRound(Game game) {
		super(game);

		board = new int[9];	
		playRound();
	}

	private void playRound() {
		while (moveCounter < 9) {
			step();
			if (gameEnded) {
				break;
			}
		}
	}

	private void step() {
		if (!gameEnded) {
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
				}
			}
		}
	}

}
