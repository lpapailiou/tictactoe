package com.tictactoe.application;

import com.tictactoe.model.Setting;
import com.tictactoe.root.CmdReader;
import com.tictactoe.root.GameHandler;
import com.tictactoe.root.Qnet;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class TicTacToe extends Application {

	private Stage stage;
	private GameHandler handler = new GameHandler(this);
	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		Qnet.getData(); 
		//Setting.setCmd(false);
		if (!Setting.cmd()) {
			startupGui();
		} else {
			new CmdReader(handler);
		}
	}
	
	public void startupGui() {
		Setting.setCmd(false);
		try {   	
			FXMLLoader loadFrame = new FXMLLoader(TicTacToe.class.getClassLoader().getResource("com/tictactoe/resources/GameFrame.fxml"));
			Parent mainRoot = loadFrame.load();
			Scene scene = new Scene(mainRoot, 800, 400);
			scene.getStylesheets().add(TicTacToe.class.getClassLoader().getResource("com/tictactoe/resources/application.css").toExternalForm());

			stage.setScene(scene);
			stage.setMinWidth(816);
			stage.setMinHeight(440);
			stage.getIcons().add(new Image("com/tictactoe/resources/img/icon.png"));
			stage.setTitle("Tic Tac Toe | machine learning project");
			stage.show();  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		Platform.exit();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		if (System.console() != null) {
			Setting.setCmd(true);
		}
		launch(args);
	}
}
