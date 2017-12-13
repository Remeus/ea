package com.main;

public class Phenotype {
	
	protected int[] attributes;
	protected int phenotypeLength = 0;

	//Create phenotype as a list of integers
	public Phenotype(Genotype genotype) {
		attributes = genotype.getGenes().getCoordinates();
		phenotypeLength = attributes.length;
	}
	
	//Evaluate fitness of phenotype
	public int evaluate() {
		return Population.testParameters.fitnessFunction.fitness(attributes);
	}
	
}
