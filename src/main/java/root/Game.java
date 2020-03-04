package main.java.root;

import java.util.Scanner;

import main.java.model.Setting;
import main.java.root.rounds.MachineManualCmdRound;
import main.java.root.rounds.MachineManualRound;
import main.java.root.rounds.MachineRound;
import main.java.root.rounds.ManualCmdRound;
import main.java.root.rounds.ManualRound;

public class Game {
	
	Scanner scanner = new Scanner(System.in);
	int result = 0;
	private int roundsToGo = 0;
	private GameHandler handler;

	public  Game(GameHandler handler) {
		this.handler = handler;
		roundsToGo = Setting.getRounds();
		round();
	}
	
	private synchronized void round() {
		roundsToGo--;
		if (!Setting.getPlayerOne().isManual() && !Setting.getPlayerTwo().isManual()) {
			new MachineRound(this);
		} else if (Setting.getPlayerOne().isManual() && Setting.getPlayerTwo().isManual()) {
			if (Setting.cmd()) {
				new ManualCmdRound(this);
			} else {
				new ManualRound(this);
			}
		} else {
			if (Setting.cmd()) {
				new MachineManualCmdRound(this);
			} else {
				new MachineManualRound(this);
			}
		}
	}
	
	public void updateResults(int result) {
		int xwinsr = 0;
		int owinsr = 0;
		int drawsr = 0;
		if (result == 1) {
			xwinsr = 1;
		} else if (result == -1) {
			owinsr = 1;
		} else {
			drawsr = 1;
		}
		handler.refreshStats(xwinsr, owinsr, drawsr);
		if (roundsToGo > 0) {
			round();
		} else {
			handler.lastRoundDone();
		}
	}

}
