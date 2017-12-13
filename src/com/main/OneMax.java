package com.main;

public class OneMax extends FitnessFunction {

	public OneMax(int genes) {
		super(genes);
	}
	
	
	//Fitness function for the One-Max problem
	public int fitness(int[] vector) {
		int fitness = 0;
		for (int binary : vector) {
			fitness += binary;
		}
		return fitness;
	}
	
}
