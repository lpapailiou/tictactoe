package com.tictactoe.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.tictactoe.model.Mode;
import com.tictactoe.model.Setting;
import com.tictactoe.model.Source;
import com.tictactoe.root.GameHandler;
import com.tictactoe.root.Qnet;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameController extends GameHandler implements Initializable {
	
	private ArrayList<GridButton> buttons = new ArrayList<>();
	private String showingAttribute = "showing";
	
	@FXML
	private GridPane boardGrid;
	
	@FXML
	private GridPane settingsGrid;
	
	@FXML
	private Label sourceLabel;
	
	@FXML
	private ComboBox<String> source;
	
	@FXML
	private Label whoStartsLabel;
	
	@FXML
	private RadioButton xStarts;
	
	@FXML
	private RadioButton oStarts;
	
	@FXML
	private Label player1Label;
	
	@FXML
	private ComboBox<String> player1;
	
	@FXML
	private Label player2Label;
	
	@FXML
	private ComboBox<String> player2;
	
	@FXML
	private Label speedLabel;
	
	@FXML
	private Slider speed;
	
	@FXML
	private Label learnLabel;
	
	@FXML
	private RadioButton learn;
	
	@FXML
	private Label alphaLabel;
	
	@FXML
	private TextField alpha;
	
	@FXML
	private Label gammaLabel;
	
	@FXML
	private TextField gamma;
	
	@FXML
	private Label epsilonLabel;
	
	@FXML
	private TextField epsilon;
	
	@FXML
	private Label roundsLabel;
	
	@FXML
	private TextField rounds;
	
	@FXML
	private Button startBut;
	
	@FXML
	private Label statisticsLabel;
	
	@FXML
	private Label statistics;
	
	@FXML
	private Label percent;
	
	@FXML
	private Button clearBut;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupBoard();
		initializeSettings();
		adaptContols();
		setTooltips();
		addActions();
		Platform.runLater(() -> startBut.getParent().requestFocus());
	}
	
	// ---------------------------------- INITIALIZATION OF GUI ----------------------------------
	
	public void setupBoard() {
		double size = 403.0/3.0;
		
		GridButton g0 = new GridButton(0);
		GridButton g1 = new GridButton(1);
		GridButton g2 = new GridButton(2);
		GridButton g3 = new GridButton(3);
		GridButton g4 = new GridButton(4);
		GridButton g5 = new GridButton(5);
		GridButton g6 = new GridButton(6);
		GridButton g7 = new GridButton(7);
		GridButton g8 = new GridButton(8);
	
		boardGrid.add(g0, 0, 0);
		boardGrid.add(g1, 1, 0);
		boardGrid.add(g2, 2, 0);
		boardGrid.add(g3, 0, 1);
		boardGrid.add(g4, 1, 1);
		boardGrid.add(g5, 2, 1);
		boardGrid.add(g6, 0, 2);
		boardGrid.add(g7, 1, 2);
		boardGrid.add(g8, 2, 2);
		
		buttons.add(g0);
		buttons.add(g1);
		buttons.add(g2);
		buttons.add(g3);
		buttons.add(g4);
		buttons.add(g5);
		buttons.add(g6);
		buttons.add(g7);
		buttons.add(g8);
		
		Setting.setButtons(buttons);
		
		for (Node n: boardGrid.getChildren()) {
			if (n instanceof GridButton) {
				GridButton but = (GridButton) n;
				but.setMinSize(size, size);
				but.setMaxSize(size, size);
				but.setPrefSize(size, size);
			}
		}
	}
	
	private void initializeSettings() {
		source.getItems().addAll(Source.LIBRARY.get(), Source.BLANK.get());
		source.getSelectionModel().select(Setting.getSource().get());
		
		if (Setting.xStarts()) {
			xStarts.setSelected(true);
			oStarts.setSelected(false);
		} else {
			xStarts.setSelected(false);
			oStarts.setSelected(true);
		}
		
		player1.getItems().addAll(Mode.MANUAL.get(), Mode.RANDOM.get(), Mode.MIN.get(), Mode.MAX.get());
		player1.getSelectionModel().select(Setting.getPlayerOne().get());
		
		player2.getItems().addAll(Mode.MANUAL.get(), Mode.RANDOM.get(), Mode.MIN.get(), Mode.MAX.get());
		player2.getSelectionModel().select(Setting.getPlayerTwo().get());
		
		speed.setValue(100.0 - (Setting.getSpeed()/5.0));
		speed.setMin(0);
		speed.setMax(100);
		speed.setShowTickMarks(true);
		speed.setMinorTickCount(4);
		speed.setMajorTickUnit(50);
		speed.setBlockIncrement(10);
		speed.setSnapToTicks(false);
		
		learn.setSelected(Setting.getLearn());
		
		alpha.setText("" + Setting.getAlpha());
		gamma.setText("" + Setting.getGamma());
		epsilon.setText("" + Setting.getEpsilon());
		
		rounds.setText("" + Setting.getRounds());
		
		statistics.setText(Setting.getStatistics());
		percent.setText(Setting.getPercent());	
	}
	
	private void adaptContols() {
		if (!learn.isSelected()) {
			alpha.setDisable(true);
			gamma.setDisable(true);
		}
		if (Setting.getSource() == Source.LIBRARY) {
			learn.setDisable(true);
		}
		if (Setting.getPlayerOne().isManual() || Setting.getPlayerTwo().isManual()) {
			rounds.setDisable(true);
		}
		startBut.getParent().requestFocus();
	}
	
	private void setTooltips() {
		Tooltip sourceTooltip = new Tooltip("choose if you want to pull data from a built-in library created by machine learning, or if you want to make your own experiences");
		sourceLabel.setTooltip(sourceTooltip);
		Tooltip whoStartsTooltip = new Tooltip("choose if x or o should go first");
		whoStartsLabel.setTooltip(whoStartsTooltip);
		Tooltip player1Tooltip = new Tooltip("choose how player one should play - either you take control, or pick a certain behavior");
		player1Label.setTooltip(player1Tooltip);
		Tooltip player2Tooltip = new Tooltip("choose how player two should play - either you take control, or pick a certain behavior");
		player2Label.setTooltip(player2Tooltip);
		Tooltip speedTooltip = new Tooltip("choose if the game should have small delays between the moves (0 - 500 ms allowed)");
		speedLabel.setTooltip(speedTooltip);
		Tooltip learnTooltip = new Tooltip("choose if machine learning is active or not");
		learnLabel.setTooltip(learnTooltip);
		Tooltip alphaTooltip = new Tooltip("set alpha parameter for learning curve - the higher the value, the higher the impact (0 - 1 allowed)");
		alphaLabel.setTooltip(alphaTooltip);
		Tooltip gammaTooltip = new Tooltip("set gamma parameter for instant rewards - the higher the value, the higher are immediate rewards valued (0 - 1 allowed)");
		gammaLabel.setTooltip(gammaTooltip);
		Tooltip epsilonTooltip = new Tooltip("set epsilon greedyness. the higher the value, the more random moves are done (0 - 1 allowed)");
		epsilonLabel.setTooltip(epsilonTooltip);
		Tooltip roundsTooltip = new Tooltip("choose how many consecutive games should be played with these settings");
		roundsLabel.setTooltip(roundsTooltip);
		Tooltip startTooltip = new Tooltip("start one game or multiple consecutive games at once");
		startBut.setTooltip(startTooltip);
		Tooltip clearTooltip = new Tooltip("clear the statistics");
		clearBut.setTooltip(clearTooltip);
	}
	
	// ---------------------------------- INITIALIZATION OF ACTIONS ----------------------------------

	private void addActions() {
		addSourceActions();
		addWhoStartsActions();
		addPlayerActions();
		addSpeedActions();
		addLearnAction();
		addLearnControlActions();
		addRoundsAction();
		addStartAction();
		addClearAction();
	}
	
	private void addSourceActions() {
		source.setOnAction((ActionEvent e) -> {
		source.getParent().requestFocus();
		String inputMode = source.getSelectionModel().getSelectedItem();
		setSource(inputMode);
		});
		
		source.setOnMouseClicked((MouseEvent e) -> {
			if (!source.getPseudoClassStates().toString().contains(showingAttribute)) {
				source.getParent().requestFocus();
			}
		});
	}
	
	private void addWhoStartsActions() {
		xStarts.setOnAction((ActionEvent e) -> {
		xStarts.getParent().requestFocus();
		xChosen();
		});
		oStarts.setOnAction((ActionEvent e) -> {
		oStarts.getParent().requestFocus();
		oChosen();
		});
	}
	
	private void addPlayerActions() {
		player1.setOnAction((ActionEvent e) -> {
		player1.getParent().requestFocus();
		String inputMode1 = player1.getSelectionModel().getSelectedItem();
		String inputMode2 = player2.getSelectionModel().getSelectedItem();
		setPlayerOne(inputMode1, inputMode2);
		});
		
		player2.setOnAction((ActionEvent e) -> {
		player2.getParent().requestFocus();
		String inputMode1 = player1.getSelectionModel().getSelectedItem();
		String inputMode2 = player2.getSelectionModel().getSelectedItem();
		setPlayerTwo(inputMode1, inputMode2);
		});
		
		player1.setOnMouseClicked((MouseEvent e) -> {
			if (!player1.getPseudoClassStates().toString().contains(showingAttribute)) {
				player1.getParent().requestFocus();
			}
		});
		
		player2.setOnMouseClicked((MouseEvent e) -> {
			if (!player2.getPseudoClassStates().toString().contains(showingAttribute)) {
				player2.getParent().requestFocus();
			}
		});
	}
	
	private void addSpeedActions() {
		speed.valueProperty().addListener((observable, oldValue, newValue) -> 
			getSliderValue(newValue)
		);
		
		speed.setOnMouseReleased((MouseEvent e) -> 
			speed.getParent().requestFocus()
		);
	}
	
	private void addLearnAction() {
		learn.setOnAction((ActionEvent e) -> {
		learn.getParent().requestFocus();
		Setting.setLearn(learn.isSelected());
		});
		
		learn.setOnAction((ActionEvent e) -> {
		learn.getParent().requestFocus();
		Setting.setLearn(learn.isSelected());
		if (learn.isSelected()) {
			alpha.setDisable(false);
			gamma.setDisable(false);
		} else {
			alpha.setDisable(true);
			gamma.setDisable(true);
		}
		});
	}
	
	private void addLearnControlActions() {
		alpha.textProperty().addListener((o, oldValue, newValue) -> {
			Double result = 0.0;
			boolean valid = false;
			try {
				result = Double.parseDouble(newValue);
				if (result >= 0 && result <= 1) {
					valid = true;
				}
				
			} catch(Exception e) {}
			if (valid) {
				Setting.setAlpha(result);
				Qnet.setAlpha(result);
				alpha.setText(result.toString());
			} else {
				alpha.setText(oldValue);
			}
		});
		
		gamma.textProperty().addListener((o, oldValue, newValue) -> {
			Double result = 0.0;
			boolean valid = false;
			try {
				result = Double.parseDouble(newValue);
				if (result >= 0 && result <= 1) {
					valid = true;
				}
				
			} catch(Exception e) {}
			if (valid) {
				Setting.setGamma(result);
				Qnet.setGamma(result);
				gamma.setText(result.toString());
			} else {
				gamma.setText(oldValue);
			}
		});
		
		epsilon.textProperty().addListener((o, oldValue, newValue) -> {
			Double result = 0.0;
			boolean valid = false;
			try {
				result = Double.parseDouble(newValue);
				if (result >= 0 && result <= 1) {
					valid = true;
				}
				
			} catch(Exception e) {}
			if (valid) {
				Setting.setEpsilon(result);
				epsilon.setText(result.toString());
			} else {
				epsilon.setText(oldValue);
			}
		});
	}
	
	private void addRoundsAction() {
		rounds.textProperty().addListener((o, oldValue, newValue) -> {
			int result = 0;
			boolean valid = false;
			try {
				result = Integer.parseInt(newValue);
				if (result >= 1) {
					valid = true;
				}
				
			} catch(Exception e) {}
			if (valid) {
				Setting.setRounds(result);
				rounds.setText(newValue);
			} else {
				rounds.setText(oldValue);
			}
		});
	}
	
	private void addStartAction() {
		startBut.setOnAction((ActionEvent e) -> {
			startBut.getParent().requestFocus();
		startBut.setDisable(true);
		startNewGame();
		});
	}
	
	private void addClearAction() {
		clearBut.setOnAction((ActionEvent e) -> {
			statistics.getParent().requestFocus();
			clearStatistics();
		});
	}

	// ---------------------------------- GAME HANDLERS ----------------------------------
	
	@Override
	public void setSource(String inputMode) {
		super.setSource(inputMode);
		if (inputMode.contentEquals(Source.LIBRARY.get())) {
			source.setValue(Source.LIBRARY.get());
			learn.setDisable(true);
			learn.setSelected(false);
			alpha.setDisable(true);
			gamma.setDisable(true);
		} else if (inputMode.contentEquals(Source.BLANK.get())) {
			source.setValue(Source.BLANK.get());
			learn.setDisable(false);
		}
	}
	
	@Override
	public void xChosen() {
		super.xChosen();
		xStarts.setSelected(true);
		oStarts.setSelected(false);
	}
	
	@Override
	public void oChosen() {
		super.oChosen();
		xStarts.setSelected(false);
		oStarts.setSelected(true);
	}
	
	@Override
	public void setPlayerOne(String inputMode1, String inputMode2) {
		super.setPlayerOne(inputMode1, inputMode2);
		player1.setValue(Setting.getPlayerOne().get());	
		if (inputMode1.contentEquals(Mode.MANUAL.get()) || inputMode2.contentEquals(Mode.MANUAL.get())) {
			rounds.setText("1");
			rounds.setDisable(true);
			epsilon.setDisable(true);
			
		} else {
			rounds.setDisable(false);
			epsilon.setDisable(false);
		}
		
		if (inputMode1.contentEquals(Mode.MANUAL.get()) && inputMode2.contentEquals(Mode.MANUAL.get())) {
			epsilon.setDisable(true);
		} else {
			epsilon.setDisable(false);
		}
	}
	
	@Override
	public void setPlayerTwo(String inputMode1, String inputMode2) {
		super.setPlayerTwo(inputMode1, inputMode2);
		player2.setValue(Setting.getPlayerTwo().get());
	if (inputMode1.contentEquals(Mode.MANUAL.get()) || inputMode2.contentEquals(Mode.MANUAL.get())) {
		rounds.setText("1");
		rounds.setDisable(true);
		epsilon.setDisable(true);
		
	} else {
		rounds.setDisable(false);
		epsilon.setDisable(false);
	}
	if (inputMode1.contentEquals(Mode.MANUAL.get()) && inputMode2.contentEquals(Mode.MANUAL.get())) {
		epsilon.setDisable(true);
	} else {
		epsilon.setDisable(false);
	}
	}
	
	@Override
	public void setSpeed(String x) {
		super.setSpeed(x);
		speed.setValue(Setting.getSpeed());
	}
	
	private void getSliderValue(Number x) {
		Setting.setSpeed(500 - (x.intValue() * 5));
	}
	
	@Override
	public void setLearn(boolean learn) {
		super.setLearn(learn);
		if (learn) {
			alpha.setDisable(false);
			gamma.setDisable(false);
			this.learn.setDisable(false);
			this.learn.setSelected(learn);
		} else {
			alpha.setDisable(true);
			gamma.setDisable(true);
			this.learn.setSelected(false);
		}
	}
	
	@Override
	public void setAlpha(String s) {
		super.setAlpha(s);
		alpha.setText("" + Setting.getAlpha());
	}
	
	@Override
	public void setGamma(String s) {
		super.setGamma(s);
		gamma.setText("" + Setting.getGamma());
	}
	
	@Override
	public void setEpsilon(String s) {
		super.setEpsilon(s);
		epsilon.setText("" + Setting.getEpsilon());
	}
	
	@Override
	public void setRound(String s) {
		super.setRound(s);
		rounds.setText("" + Setting.getRounds());
	}
	
	@Override
	public void clearStatistics() {
		super.clearStatistics();
		setStatistics(statsVal[0], statsVal[1], statsVal[2]);
		setPercent(statsVal[0], statsVal[1], statsVal[2]);
	}
	
	@Override
	protected void setStatistics(int x, int o, int d) {
		super.setStatistics(x, o, d);
		if (Platform.isFxApplicationThread()) {
			statistics.setText(Setting.getStatistics());
		} else {
			Platform.runLater(() -> statistics.setText(Setting.getStatistics()));
		}
	}
	
	@Override
	protected void setPercent(int x, int o, int d) {
		super.setPercent(x, o, d);
		if (Platform.isFxApplicationThread()) {
			percent.setText(Setting.getPercent());
		} else {
			Platform.runLater(() -> percent.setText(Setting.getPercent()));
		}
	}
	
	@Override
	public void lastRoundDone() {
		super.lastRoundDone();
		startBut.setDisable(false);
	}
	

}
