package com.tictactoe.root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.tictactoe.model.DataNode;

public class Qnet {
	
	// https://en.wikipedia.org/wiki/Bellman_equation#The_Bellman_equation
	// https://planspace.org/20191103-q_learning_tic_tac_toe_briefly/
	
	private static Random random = new Random();
	private static ArrayList<DataNode> boardStates = new ArrayList<>();
	private static double alpha = 0.25; // between 0 and 1, defines how strongly the new value will affect the average; learning curve
	private static double gamma = 0.2; // between 0 and 1, defines discount factor. if low, immediate rewards will be denoted higher as future rewards
	
	private Qnet() {
	}
	
	public static void feedGame(ArrayList<int[]> history, ArrayList<Integer> moves, double reward) {
		double nextMaxQ = reward;
		double nextMinQ = reward;
		DataNode lastNode = null;
		for (int i = moves.size()-1; i >= 0; i--) {
			int move = moves.get(i);
			DataNode thisNode = getNode(history.get(i));

			if (lastNode == null) {
				lastNode = getNode(history.get(i+1));
				lastNode.setValue(move, reward, reward);
			}
			nextMaxQ = lastNode.getMaxValue();
			nextMinQ = lastNode.getMinValue();

			updatePolicy(thisNode, nextMaxQ, nextMinQ, move, reward);
			lastNode = thisNode;
		}
	}
	
	public static void getData() {
		clearData();
		try {
			boardStates = (ArrayList<DataNode>) CsvParser.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void clearData() {
		boardStates.clear();
	}
	
	public static void prepareExport() {
		CsvParser.write(boardStates);
	}
	
	public static Integer getMaxPolicy(int[] board) {
		DataNode n = getNode(board);
		List<Integer> moves = n.getMaxPolicy();
		if (!moves.isEmpty()) {
			int index = random.nextInt(moves.size());
			return moves.get(index);
		}
		return -1;
	}
	
	public static Integer getMinPolicy(int[] board) {
		DataNode n = getNode(board);
		List<Integer> moves = n.getMinPolicy();
		if (!moves.isEmpty()) {
			int index = random.nextInt(moves.size());
			return moves.get(index);
		}
		return -1;
	}
	
	public static Integer getRandomPolicy(int[] board) {
		DataNode n = getNode(board);
		List<Integer> moves = n.getRandomPolicy();
		if (!moves.isEmpty()) {
			int index = random.nextInt(moves.size());
			return moves.get(index);
		}
		return -1;
	}
	
	private static void updatePolicy(DataNode node, double nextMaxQ, double nextMinQ, int move, double reward) {
		double newQx = ((1-alpha) * node.getxValue(move)) + (alpha * (reward + (gamma * nextMaxQ)));
		double newQo = ((1-alpha) * node.getoValue(move)) + (alpha * (reward + (gamma * nextMinQ)));
		node.setValue(move, newQx, newQo);
	}
	
	private synchronized static DataNode getNode(int[] board) {
		String b = Arrays.toString(board);
		for (DataNode n : boardStates) {
			if (n.getBoard().contentEquals(b)) {
				return n;
			}
		}
		
		DataNode node = new DataNode(board);
		boardStates.add(node);
		return node;
	}
	
	public static void setAlpha(double a) {
		alpha = a;
	}
	
	public static void setGamma(double g) {
		gamma = g;
	}

}
