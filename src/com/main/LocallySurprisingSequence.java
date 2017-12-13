package com.main;

public class LocallySurprisingSequence extends GloballySurprisingSequence {
	
	public LocallySurprisingSequence(int genes, int n) {
		super(genes, n);
	}
	
	public int nOccurencesNotSurprising(int[] sequence) {
		int result = 0;
		if (sequence.length < 3)
			result = 0;
		else {
			for (int k = 0 ; k < sequence.length - 2 ; k++) {
				for (int i = k + 1 ; i < sequence.length - 1 ; i++) {;
					if (sequence[i] == sequence[k]) {
						if (sequence[i + 1] == sequence[k+1]) {
							result++;
						}
					}
				}
			}
		}
		return result;
	}
	
	
}
