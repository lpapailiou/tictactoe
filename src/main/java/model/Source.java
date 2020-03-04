package main.java.model;

public enum Source {
	
	LIBRARY("library"),
	BLANK("blank");
	
	private String name;
	
	Source(String name) {
		this.name = name;
	}
	
	public String get() {
		return name;
	}
}
