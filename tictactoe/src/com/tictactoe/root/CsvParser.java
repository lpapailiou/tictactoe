package com.tictactoe.root;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import com.tictactoe.model.DataNode;

public class CsvParser {
	
	private CsvParser() {
	}
	
	public static List<DataNode> read() {
		ArrayList<DataNode> nodes = new ArrayList<>();
		nodes.addAll(readFile());
		return nodes;
	}
	
	public static List<DataNode> readFile() {
		ArrayList<DataNode> nodes = new ArrayList<>();
		BufferedReader csvReader = null;
		String row;
		try {
			InputStream in = CsvParser.class.getClassLoader().getResourceAsStream("com/tictactoe/resources/data/qTable_library.csv");
			if (in == null) {
				in = CsvParser.class.getClassLoader().getResourceAsStream("com/ch/tictactoe/resources/data/qTable_library.csv");
			}
			InputStreamReader isr = new InputStreamReader(in);
			csvReader = new BufferedReader(isr);

			boolean header = true;
			while ((row = csvReader.readLine()) != null) {
				if (!header) {
					String[] data = row.split(";");
					double[] matrixX = new double[9];
					double[] matrixO = new double[9];
					if (data.length >= 10) {
		
						
						for (int i = 0; i < 9; i++) {
							matrixX[i] = Double.parseDouble(data[i+1]);
							
						}
					}
					if (data.length == 19) {
					   
						for (int i = 0; i < 9; i++) {
							matrixO[i] = Double.parseDouble(data[i+10]);
							
						}
						DataNode n = new DataNode(data[0], matrixX, matrixO);
						nodes.add(n);
					}
				} else {
					header = false;
				}
			}
			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (csvReader != null) {
					csvReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return nodes;
	}
	
		
	public static void write(ArrayList<DataNode> nodes) {
		FileWriter csvWriter = null;
		File file = new File("");
		try {
			csvWriter = new FileWriter(file, false);
			
			csvWriter.append(String.join(",", "board"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_1"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_2"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_3"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_4"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_5"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_6"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_7"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_8"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "x_value_9"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_1"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_2"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_3"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_4"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_5"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_6"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_7"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_8"));
			csvWriter.append(";");
			csvWriter.append(String.join(",", "o_value_9"));
			csvWriter.append(";");
			csvWriter.append("\n");

			for (DataNode n : nodes) {
				csvWriter.append(String.join(",", n.getBoard()));
				csvWriter.append(";");
				double[] arr = n.getxMatrix();
				for (int i = 0; i < arr.length; i++) {
					csvWriter.append(String.join(",", Double.toString(arr[i])));
					csvWriter.append(";");
					
				}
				double[] arr2 = n.getoMatrix();
				for (int i = 0; i < arr2.length; i++) {
					csvWriter.append(String.join(",", Double.toString(arr2[i])));
					if (i < arr2.length-1) {
						csvWriter.append(";");
					}
				}
			csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		processTempFile(file, "tictactoe_expot.csv");
			
	}
	
	private static void processTempFile(File resource, String fileName) {
		try {
			InputStream htmlFile = new FileInputStream(resource); 
			File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
			tempFile.createNewFile();
			Path tempPath = tempFile.toPath();
			Files.copy(htmlFile, tempPath, StandardCopyOption.REPLACE_EXISTING);
			tempFile.deleteOnExit();
			URI url = tempFile.toURI();
			Desktop.getDesktop().browse(url);  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
