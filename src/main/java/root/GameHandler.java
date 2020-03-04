package main.java.root;

import java.text.DecimalFormat;

import main.java.application.TicTacToe;
import main.java.model.Mode;
import main.java.model.Setting;
import main.java.model.Source;

public class GameHandler {
	
	private static TicTacToe tic;
	private boolean promptLock = false;
	protected static int[] statsVal = new int[3];
	protected static int totalRounds = 0;
	
	public GameHandler(TicTacToe tictactoe) {
		tic = tictactoe;
	}
	
	public GameHandler() {}
	
	public void setSource(String inputMode) {
		if (inputMode.contentEquals(Source.LIBRARY.get())) {
			Setting.setSource(Source.LIBRARY);
			Setting.setLearn(false);
		} else if (inputMode.contentEquals(Source.BLANK.get())) {
			Setting.setSource(Source.BLANK);
		}
	}
	
	public void xChosen() {
		Setting.setWhoStarts(true);
	}
	
	public void oChosen() {
		Setting.setWhoStarts(false);
	}
	
	public void setPlayerOne(String inputMode1, String inputMode2) {
		if (inputMode2 == null) {
			inputMode2 = Setting.getPlayerTwo().get();
		}
		if (inputMode1.contentEquals(Mode.MANUAL.get())) {
			Setting.setPlayerOne(Mode.MANUAL);
		} else if (inputMode1.contentEquals(Mode.RANDOM.get())) {
			Setting.setPlayerOne(Mode.RANDOM);
		} else if (inputMode1.contentEquals(Mode.MIN.get())) {
			Setting.setPlayerOne(Mode.MIN);
		} else if (inputMode1.contentEquals(Mode.MAX.get())) {
			Setting.setPlayerOne(Mode.MAX);
		}
		
		if (inputMode1.contentEquals(Mode.MANUAL.get()) || inputMode2.contentEquals(Mode.MANUAL.get())) {
			Setting.setRounds(1);
		}
	}
	
	public void setPlayerTwo(String inputMode1, String inputMode2) {
		if (inputMode1 == null) {
			inputMode1 = Setting.getPlayerOne().get();
		}
		if (inputMode2.contentEquals(Mode.MANUAL.get())) {
			Setting.setPlayerTwo(Mode.MANUAL);
		} else if (inputMode2.contentEquals(Mode.RANDOM.get())) {
			Setting.setPlayerTwo(Mode.RANDOM);
		} else if (inputMode2.contentEquals(Mode.MIN.get())) {
			Setting.setPlayerTwo(Mode.MIN);
		} else if (inputMode2.contentEquals(Mode.MAX.get())) {
			Setting.setPlayerTwo(Mode.MAX);
		}
		if (inputMode1.contentEquals(Mode.MANUAL.get()) || inputMode2.contentEquals(Mode.MANUAL.get())) {
			Setting.setRounds(1);
		} 
	}
	
	public void setSpeed(String x) {
		try {
			int n = Integer.parseInt(x);
			if (n >=0 && n <= 500) {
				Setting.setSpeed(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLearn(boolean learn) {
		Setting.setLearn(learn);
		if (learn) {
			setSource(Source.BLANK.get());
		}
	}
	
	public void setAlpha(String s) {
		try {
			Double d = Double.parseDouble(s);
			if (d >= 0 && d <= 1) {
				Setting.setAlpha(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setGamma(String s) {
		try {
			Double d = Double.parseDouble(s);
			if (d >= 0 && d <= 1) {
				Setting.setGamma(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setEpsilon(String s) {
		try {
			Double d = Double.parseDouble(s);
			if (d >= 0 && d <= 1) {
				Setting.setEpsilon(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setRound(String s) {
		try {
			int d = Integer.parseInt(s);
			if (d >= 1) {
				Setting.setRounds(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getOptions() {
		String s = "[opt] for options\n";
		s += "[stat] for statistics, options: -clear, -print\n";
		s += "[gui] to launch gui\n";
		s += "[q] to quit\n\n";
		
		s += "source: {" + Setting.getSource().get() + "} \t[src] to switch source, options: -lib, -blank\n";
		s += "starts: {" + (Setting.xStarts() ? "x" : "o") + "} \t\t[start] to set starting player, options: -x, -o\n";
		s += "player 1: {" + Setting.getPlayerOne().get() + "} \t[p1] to change player one settings, options: -manual, -random, -max, -min\n";
		s += "player 2: {" + Setting.getPlayerTwo().get() + "} \t[p2] to change player two settings, options: -manual, -random, -max, -min\n";
		s += "delay: {" + Setting.getSpeed() + " ms} \t\t[speed], use <double> option between 0 and 500 to set delays\n";
		s += "learn: {" + Setting.getLearn() + "} \t\t[learn], use <boolean> option\n";
		s += "alpha: {" + Setting.getAlpha() + "} \t\t[alpha], use <double> option between 0 and 1 for learning curve\n";
		s += "gamma: {" + Setting.getGamma() + "} \t\t[gamma], use <double> option between 0 and 1 for reward discount\n";
		s += "epsilon: {" + Setting.getEpsilon() + "} \t\t[epsilon], use <double> option between 0 and 1 for epsilon greedy randomization\n";
		s += "rounds: {" + Setting.getRounds() + "} \t\t[round], use <int> option for round count\n\n";
		s += "[go] to start game\n";
		return s;
	}
	
	public String getStatistics() {
		String[] a = Setting.getStatistics().split("\n");
		String[] b = Setting.getPercent().split("\n");
		
		String s = "";
		s += a[0] + " \t" + b[0] + "\n";
		s += a[1] + " \t" + b[1] + "\n";
		s += a[2].replaceAll("\t", "\t\t") + " \t" + b[2] + "\n";
		return s;
	}
	
	public void clearStatistics() {
		statsVal = new int[3];
		setStatistics(statsVal[0], statsVal[1], statsVal[2]);
		setPercent(statsVal[0], statsVal[1], statsVal[2]);
		totalRounds = 0;
	}
	
	protected void setStatistics(int x, int o, int d) {
		totalRounds++;
		Setting.setStatistics("X wins: \t" + statsVal[0] + "\nO wins: \t" + statsVal[1] + "\ndraws: \t" + statsVal[2]);
	}
	
	protected void setPercent(int x, int o, int d) {
		DecimalFormat df2 = new DecimalFormat("#.#####");
		Setting.setPercent(df2.format(percent(totalRounds, statsVal[0])) + " %\n" + df2.format(percent(totalRounds, statsVal[1])) + " %\n" + df2.format(percent(totalRounds, statsVal[2])) + " %");
	}
	
	protected double percent(double x, double val) {
		return 100.0 / x * val;
	}
	
	public void refreshStats(int x, int o, int d) {
		statsVal[0] += x;
		statsVal[1] += o;
		statsVal[2] += d;
		setStatistics(statsVal[0], statsVal[1], statsVal[2]);
		setPercent(statsVal[0], statsVal[1], statsVal[2]);
	}
	
	public void lastRoundDone() {
		promptLock = false;
	}
	
	public boolean getPromptLock() {
		return promptLock;
	}
	
	public void stop() {
		try {
			tic.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startupGui() {
		tic.startupGui();
	}
	
	public void startNewGame() {
		promptLock = true;
		try {
			new Game(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
