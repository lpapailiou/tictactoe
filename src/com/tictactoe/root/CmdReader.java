package com.tictactoe.root;

import java.util.Scanner;

import com.tictactoe.model.Mode;
import com.tictactoe.model.Source;

public class CmdReader {
	
	private static String wlcm = "\n\n====================== TIC TAC TOE ======================\n\n";
	private GameHandler handler;
	private static Scanner scanner = new Scanner(System.in);
	private boolean running = true;
	
	public CmdReader(GameHandler handler) {
		this.handler = handler;
		System.out.println(wlcm);
		System.out.println(printOptions(handler));
		
		while (running) {
			String in[] = prompt().split(" ");
			parseString(in);
		}
	}
	
	private void parseString(String[] in) {
		for (int i = 0; i < in.length; i++) {
			
			switch (in[i]) {
				case "o": 
				case "opt": 
				case "option": 
				case "options": 
					System.out.println(handler.getOptions());
					break;
				case "stat":
				case "stats":
				case "statistics":
					if (in.length > i+1) {
						if (in[i+1].contentEquals("-clear") || in[i+1].contentEquals("-c")) {
							handler.clearStatistics();
						} else if (in[i+1].contentEquals("-print") || in[i+1].contentEquals("-p")) {
							System.out.println(handler.getStatistics());
						} else {
							invalidOption(in[i + 1]);
						}
					} else {
						missingOption();
					}
					break;
				case "gui":
				case "app":
				case "application":
				case "game":
				case "see":
					running = false;
					System.out.println("see you on the other side ...");
					handler.startupGui();
					break;
				case "src":
				case "source":
				case "library":
				case "lib":
					if (in.length > i+1) {
						if (in[i+1].contentEquals("-lib")) {
							handler.setSource(Source.LIBRARY.get());
						} else if (in[i+1].contentEquals("-blank")) {
							handler.setSource(Source.BLANK.get());
						} else {
							invalidOption(in[i + 1]);
						}
					} else {
						missingOption();
					}
					break;
				case "start":
				case "starts":
				case "starting":
				case "whostarts":
				case "whoStarts":
					if (in.length > i+1) {
						if (in[i+1].contentEquals("-x")) {
							handler.xChosen();
						} else if (in[i+1].contentEquals("-o")) {
							handler.oChosen();
						} else {
							invalidOption(in[i + 1]);
						}
					} else {
						missingOption();
					}
					break;
				case "p1":
				case "pone":
				case "player1":
				case "playerone":
				case "playerOne":
					if (in.length > i+1) {
						if (in[i+1].contentEquals("-manual") || in[i+1].contentEquals("-m")) {
							handler.setPlayerOne(Mode.MANUAL.get(), null);
						} else if (in[i+1].contentEquals("-random") || in[i+1].contentEquals("-r")) {
							handler.setPlayerOne(Mode.RANDOM.get(), null);
						} else if (in[i+1].contentEquals("-max")) {
							handler.setPlayerOne(Mode.MAX.get(), null);
						} else if (in[i+1].contentEquals("-min")) {
							handler.setPlayerOne(Mode.MIN.get(), null);
						} else {
							invalidOption(in[i + 1]);
						}
					} else {
						missingOption();
					}
					break;
				case "p2":
				case "ptwo":
				case "player2":
				case "playertwo":
				case "playerTwo":
					if (in.length > i+1) {
						if (in[i+1].contentEquals("-manual") || in[i+1].contentEquals("-m")) {
							handler.setPlayerTwo(null, Mode.MANUAL.get());
						} else if (in[i+1].contentEquals("-random") || in[i+1].contentEquals("-r")) {
							handler.setPlayerTwo(null, Mode.RANDOM.get());
						} else if (in[i+1].contentEquals("-max")) {
							handler.setPlayerTwo(null, Mode.MAX.get());
						} else if (in[i+1].contentEquals("-min")) {
							handler.setPlayerTwo(null, Mode.MIN.get());
						} else {
							invalidOption(in[i + 1]);
						}
					} else {
						missingOption();
					}
					break;
				case "speed":
				case "delay":
					if (in.length > i+1) {
						if (in[i+1] != null) {
							handler.setSpeed(in[i+1]);
						}
					} else {
						missingOption();
					}
					break;
				case "learn":
				case "train":
					if (in.length > i+1) {
						if (in[i+1].contentEquals("true")) {
							handler.setLearn(true);
						} else if (in[i+1].contentEquals("false")) {
							handler.setLearn(false);
						}
					} else {
						missingOption();
					}
					break;
				case "alpha":
				case "a":
					if (in.length > i+1) {
						handler.setAlpha(in[i+1]);
					} else {
						missingOption();
					}
					break;
				case "gamma":
				case "g":
					if (in.length > i+1) {
						handler.setGamma(in[i+1]);
					} else {
						missingOption();
					}
					break;
				case "epsilon":
				case "e":
					if (in.length > i+1) {
						handler.setEpsilon(in[i+1]);
					} else {
						missingOption();
					}
					break;
				case "round":
				case "r":
				case "rounds":
					if (in.length > i+1) {
						handler.setRound(in[i+1]);
					} else {
						missingOption();
					}
					break;
				case "go":
					handler.startNewGame();
					break;
				case "stop":
				case "quit":
				case "q":
				case "exit":
				case "bye":
				try {
					System.out.println("k thx bye");
					handler.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
					break;
				default:
					break;
			}
		}
	}
	
	private void invalidOption(String s) {
		System.out.println("\nsystem can't find the option " + s + ". try 'opt' to see all available arguments and options.\n");
	}
	
	private void missingOption() {
		System.out.println("\noops, your option seems to be missing. try 'opt' to see all available arguments and options.\n");
	}
	
	public String printHello() {
		return wlcm;
	}
	
	public String printOptions(GameHandler handler) {
		return GameHandler.getOptions();
	}
	
	public String prompt() {
		System.out.print("what shall we do?: ");
		return scanner.nextLine();
	}

}
