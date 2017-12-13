package com.main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class GloballySurprisingSequence extends FitnessFunction {

	protected int nSymbols;
	protected int nLength;
	
	public GloballySurprisingSequence(int genes, int n) {
		super(genes * (int) Math.ceil(Math.log10(n) / Math.log10(2)));
		nLength = genes;
		nSymbols = n;
	}
	
	
	//We convert to a list of integers and evaluate the fitness, which is linked to the number of coordinates preventing the sequence to be surprising
	public int fitness(int[] vector) {
		int[] decodedVector = decodeFromGray(vector);
		int n = nOccurencesNotSurprising(decodedVector);
		if (n == 0) {
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("global.txt"), "utf-8"))) {
				for (int i = 0 ; i < decodedVector.length ; i++) {
					writer.write(decodedVector[i] + ", ");
					Main.longestFound += decodedVector[i] + ", ";
				}
			}
			catch (Exception e) {
				
			}
			Main.longestFound = Main.longestFound.substring(0, Main.longestFound.length() - 2);
			throw new IllegalArgumentException("FOUND");
		}
		return Math.max((int) (decodedVector.length - n), 0);
	}
	
	
	//Tell if a sequence of int (e.g. {0, 2, 5, 5, 3, 0, 12, 11, 0, 2}) is surprising or not
	public int nOccurencesNotSurprising(int[] sequence) {
		int result = 0;
		if (sequence.length < 3)
			result = 0;
		else {
			for (int k = 0 ; k < sequence.length - 2 ; k++) {
				for (int i = k + 1 ; i < sequence.length - 1 ; i++) {;
					if (sequence[i] == sequence[k]) {
						for (int j = i + 1 ; j < sequence.length ; j++) {
							if (sequence[j] == sequence[k+j-i]) {
								//Log.log("Found sequence: [" + i + " ; " + j + "] = [" + k + " ; " + (k + j - i) + "]");
								result++;
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	
	
	public boolean isLocallySurprising(int[] sequence) {
		if (sequence.length < 3)
			return true;
		else {
			for (int k = 0 ; k < sequence.length - 2 ; k++) {
				for (int i = k + 1 ; i < sequence.length - 1 ; i++) {;
					if (sequence[i] == sequence[k]) {
						if (sequence[i + 1] == sequence[k+1])
							return false;
					}
				}
			}
		}
		return true;
	}
	
	
	//Convert from Grey to Binary
	public int[] decodeFromGray(int[] sequence) {
		int nBitsNeeded = (int) Math.ceil(Math.log10(nSymbols) / Math.log10(2));
		int lengthDecodedSequence = (int) ((double)sequence.length / nBitsNeeded);
		int[] decodedSequence = new int[lengthDecodedSequence];
		int[] decodedFromGrey = new int[sequence.length];
		double ratio = (nSymbols - 1) / (Math.pow(2, nBitsNeeded) - 1);
		for (int i = 0 ; i < lengthDecodedSequence ; i++) {
			String binaryString = new String("");
			for (int j = 0 ; j < nBitsNeeded ; j++) {
				if (j == 0)
					decodedFromGrey[i * nBitsNeeded + j] = sequence[i * nBitsNeeded + j];
				else
					decodedFromGrey[i * nBitsNeeded + j] = decodedFromGrey[i * nBitsNeeded + j - 1] ^ sequence[i * nBitsNeeded + j];
				binaryString += decodedFromGrey[i * nBitsNeeded + j];
			}
			decodedSequence[i] = (int) (Integer.parseInt(binaryString, 2) * ratio);
		}
		return decodedSequence;
	}
	
	
}
