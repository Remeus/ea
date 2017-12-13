package com.main;

public class Lolz extends FitnessFunction {

	protected int threshold;
	
	public Lolz(int genes, int z) {
		super(genes);
		threshold = z;
	}
	
	//Fitness function for the Lolz problem
	public int fitness(int[] vector) {
		int fitness = 1;
		if (vector.length == 0)
			return 0;
		else {
			for (int i = 1 ; i < vector.length ; i++) {
				if (vector[i] == vector[0])
					fitness++;
				else
					i = vector.length;
			}
			if (vector[0] == 0 && fitness >= threshold)
				fitness = threshold;
			return fitness;
		}
	}
	
}
