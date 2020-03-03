package com.tictactoe.model;

public enum Mode {
	
	MANUAL("manual", true),
	RANDOM("random", false),
	MIN("worst play", false),
	MAX("best play", false);
	
	private String name;
	private boolean isManual;
	
	Mode(String name, boolean isManual) {
		this.name = name;
		this.isManual = isManual;
	}
	
	public String get() {
		return name;
	}
	
	public boolean isManual() {
		return isManual;
	}

}
