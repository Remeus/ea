package com.main;

public class OneMaxSpecial extends OneMax {

	protected int[] target;
	
	public OneMaxSpecial(int genes) {
		super(genes);
		target = generateTarget(genes);
		new Log("Target vector generated");
		Log.log(target);
	}
	
	
	//Fitness function for the One-Max problem
	public int fitness(int[] vector) {
		int fitness = 0;
		for (int i = 0 ; i < vector.length ; i++) {
			if (vector[i] == target[i])
				fitness++;
		}
		return fitness;
	}
	
	
	//Create a random target vector
	public int[] generateTarget(int length) {
		return new BinaryVector(length).getCoordinates();
	}
	
}
