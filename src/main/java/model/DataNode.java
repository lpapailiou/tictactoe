package main.java.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DataNode {
	
	private int[] boardgrid;
	private String board;
	private double[] matrixX;
	private double[] matrixO;
	
	public DataNode(int[] board) {
		this.boardgrid = board.clone();
		this.board = Arrays.toString(board);
		this.matrixX = new double[9];
		this.matrixO = new double[9];
	}
	
	public DataNode(String board, double[] m_x, double[] m_o) {
		this.boardgrid = convert(board);
		this.board = board;
		this.matrixX = m_x;
		this.matrixO = m_o;
	}
	
	private int[] convert(String board) {
		int[] arr = new int[9];
		String[] s = board.replace("[", "").replace("]", "").split(",");
	for (int i = 0; i < 9; i++) {
		arr[i] = Integer.parseInt(s[i].trim());
	}
		return arr;
	}
	
	public String getBoard() {
		return board;
	}
	
	public double[] getxMatrix() {
		return matrixX;
	}
	
	public double[] getoMatrix() {
		return matrixO;
	}
	
	public void setValue(int pos, double valuex, double valueo) {
		matrixX[pos] = valuex;
		matrixO[pos] = valueo;
	}
	
	public double getxValue(int pos) {
		return matrixX[pos];
	}
	
	public double getoValue(int pos) {
		return matrixO[pos];
	}
	
	public double getMaxValue() {
		List<Double> arr = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			if (boardgrid[i] == 0) {
				arr.add(matrixX[i]);
			}
		}
		if (!arr.isEmpty()) {
			return Collections.max(arr);
		}
		return 0;
	}
	
	public double getMinValue() {
		List<Double> arr = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			if (boardgrid[i] == 0) {
				arr.add(matrixO[i]);
			}
		}
		if (!arr.isEmpty()) {
			return Collections.min(arr);
		}
		return 0;
	}
	
	public List<Integer> getMaxPolicy() {
		double max = getMaxValue();
		List<Integer> pos = new LinkedList<>();
		for (int i = 0; i < 9; i++) {
			if (matrixX[i] == max && boardgrid[i] == 0) {
				pos.add(i);
			}
		}
		return pos;
	}
	
	public List<Integer> getMinPolicy() {
		double min = getMinValue();
		List<Integer> pos = new LinkedList<>();
		for (int i = 0; i < 9; i++) {
			if (matrixO[i] == min && boardgrid[i] == 0) {
				pos.add(i);
			}
		}
		return pos;
	}
	
	public List<Integer> getRandomPolicy() {
		List<Integer> pos = new LinkedList<>();
		for (int i = 0; i < 9; i++) {
			if (boardgrid[i] == 0) {
				pos.add(i);
			}
		}
		return pos;
	}
	
}
