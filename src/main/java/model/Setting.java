package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.application.GridButton;
import main.java.root.Qnet;

public class Setting {
	
	private static List<GridButton> b = new ArrayList<>();
	private static Source source = Source.LIBRARY;
	private static Mode p1 = Mode.RANDOM;
	private static Mode p2 = Mode.RANDOM;
	private static boolean xStarts = true;
	private static int speed = 0;
	private static double alpha = 0.25;
	private static double gamma = 0.2;
	private static double epsilon = 0;
	private static boolean learn = false;
	private static int rounds = 1;
	private static boolean cmd = false;
	private static String statistics = "X wins: \t0\nO wins: \t0\ndraws: \t0";
	private static String percent = "0 %\n0 %\n0 %";

	private Setting() {
		
	}
	
	public static void setButtons(List<GridButton> buttons) {
		b = buttons;
	}
	
	public static List<GridButton> getButtons() {
		return b;
	}
	
	public static boolean cmd() {
		return cmd;
	}
	
	public static void setCmd(boolean c) {
		cmd = c;
	}
	
	public static Source getSource() {
		return source;
	}
	
	public static void setSource(Source sour) {
		source = sour;
		if (source == Source.LIBRARY) {
			Qnet.clearData();
			Qnet.getData();
		} else {
			Qnet.clearData();
		}
	}
	
	public static boolean xStarts() {
		return xStarts;
	}
	
	public static void setWhoStarts(boolean xDoes) {
		xStarts = xDoes;
	}
	
	public static Mode getPlayerOne() {
		return p1;
	}
	
	public static Mode getPlayerTwo() {
		return p2;
	}
	
	public static void setPlayerOne(Mode m) {
		p1 = m;
	}
	
	public static void setPlayerTwo(Mode m) {
		p2 = m;
	}
	
	public static int getSpeed() {
		return speed;
	}
	
	public static void setSpeed(int s) {
		speed = s;
	}
	
	public static boolean getLearn() {
		return learn;
	}
	
	public static void setLearn(boolean l) {
		learn = l;
	}
	
	public static double getAlpha() {
		return alpha;
	}
	
	public static void setAlpha(double a) {
		Qnet.setAlpha(a);
		alpha = a;
	}
	
	public static double getGamma() {
		return gamma;
	}
	
	public static void setGamma(double g) {
		Qnet.setGamma(g);
		gamma = g;
	}
	
	public static double getEpsilon() {
		return epsilon;
	}
	
	public static void setEpsilon(double e) {
		epsilon = e;
	}
	
	public static int getRounds() {
		return rounds;
	}
	
	public static void setRounds(int r) {
		rounds = r;
	}
	
	public static String getStatistics() {
		return statistics;
	}
	
	public static void setStatistics(String stats) {
		statistics = stats;
	}
	
	public static String getPercent() {
		return percent;
	}
	
	public static void setPercent(String per) {
		percent = per;
	}

}
