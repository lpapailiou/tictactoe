package main.java.root.rounds;

import main.java.application.GridButton;
import main.java.model.Setting;
import main.java.root.Bot;
import main.java.root.Game;

public class MachineRound extends Round {
	
	private Bot bot;	

	public MachineRound(Game game) {
		super(game);
		if (!Setting.cmd()) {
			prepareGui();
		}
		board = new int[9];	
		bot = new Bot(this, lock);
		bot.start();
		bot.block(false);
		bot.requestResume();
	}
	
	@Override
	public void botStep() {
		if (!gameEnded) {
			shortDelay();
			int m = getPolicy();
			
			boolean success = move(m);
			if (!success) {
				end();
			} else {
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
	
	@Override
	protected void end() {
		super.end();
		bot.requestStop();
	}
	
	@Override
	protected void prepareGui() {
		if (!buttons.isEmpty()) {
			for (GridButton b : buttons) {
				b.prepareRound(this);
				b.disable(true);
			}
		}
	}
}
