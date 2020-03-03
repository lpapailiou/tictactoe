package com.tictactoe.application;

import com.tictactoe.root.rounds.Round;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GridButton extends Button {
	
	private boolean occupied = false;
	private int id;
	private ImageView tic;
	private ImageView tac;
	private ImageView toe;
	private static final double IMGSIZE = 120;
	private Round round;
	
	public GridButton(int id) {
		this.id = id;
		this.getStyleClass().add("button-default");
		addEvents();
		this.tic = createSymbol("tic.png");
		this.tac = createSymbol("tac.png");
		this.setDisable(true);
	}
	
	
	public ImageView createSymbol(String n) {
		String path = "com/tictactoe/resources/img/";
		path += n;
		final String filePath = path;
		ImageView img = new ImageView(filePath);
		img.setFitWidth(IMGSIZE);
		img.setFitHeight(IMGSIZE);
		return img;
	}
	
	
	private void addEvents() {
		// add event handler for manual gameplay (triggering move)					
		setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY && (!occupied && round != null)) {
				round.step(id);
			}
			event.consume();
		});
		
		this.setOnMouseReleased((MouseEvent e) -> 
			getParent().requestFocus()
		);
	}
	
	public void setSymbol(String s, boolean xStarts) {
		if (s.contentEquals("x") || s.contentEquals("o")) {
			occupied = true;
		}
		if (xStarts) {
			if (s.contentEquals("x")) {
				toe = tic;
			} else if (s.contentEquals("o")) {
				toe = tac;
			}
		} else {
			if (s.contentEquals("x")) {
				toe = tac;
			} else if (s.contentEquals("o")) {
				toe = tic;
			}
		}		
		update();
	}
	
	private void update() {
		if (Platform.isFxApplicationThread()) {
			this.setGraphic(toe);
		} else {
			Platform.runLater(() -> this.setGraphic(toe));
		}
	}
	
	public void prepareRound(Round round) {
		this.round = round;
		if (Platform.isFxApplicationThread()) {
			this.setGraphic(null);
		} else {
			Platform.runLater(() -> this.setGraphic(null));
		}
		occupied = false;
	}
	
	public void setCursor(boolean wait) {
		if (wait) {
			this.setCursor(Cursor.WAIT);
		} else {
			this.setCursor(Cursor.HAND);
		}
	}
	
	public void disable(boolean off) {
		this.setDisable(off);
	}
}
