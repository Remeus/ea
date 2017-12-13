package com.main;

public abstract class FitnessFunction {

	protected int nGenes = 0;
	
	//Working tests
	
	static Test testParameters1 = new Test(
			50, //Maximum number of generations
			20, //Maximum size of a population (20 - 100)
			0.005, //Mutation rate (0.005 - 0.02)
			0.95, //Crossover rate (0.6 - 0.95)
			Test.FULL, //Adult selection mode //Also other modes
			Test.TOURNAMENT, //Parent selection mode // (E, pK) = (0.1, 0.3)
			0.1,
			0.3,
			new OneMax(40) //Fitness function // one-max 40
		);
	
	static Test testParameters2 = new Test(
			50, //Maximum number of generations
			20, //Maximum size of a population (20 - 100)
			0.015, //Mutation rate (0.005 - 0.02)
			0.92, //Crossover rate (0.6 - 0.95)
			Test.MIXING, //Adult selection mode
			Test.FITNESS_PROPORTIONATE, //Parent selection mode //Or sigma-scaling
			0.1,
			0.3,
			new OneMax(40) //Fitness function // one-max 40
		);
	
	static Test testParameters3 = new Test(
			50, //Maximum number of generations
			20, //Maximum size of a population (20 - 100)
			0.01, //Mutation rate (0.005 - 0.02)
			0.92, //Crossover rate (0.6 - 0.95)
			Test.OVER_PRODUCTION, //Adult selection mode
			Test.FITNESS_PROPORTIONATE, //Parent selection mode
			0.1,
			0.3,
			new OneMax(40) //Fitness function // one-max 40
		);
	
	static Test testParameters4 = new Test(
			50, //Maximum number of generations
			20, //Maximum size of a population (20 - 100)
			0.017, //Mutation rate (0.005 - 0.02)
			0.92, //Crossover rate (0.6 - 0.95)
			Test.MIXING, //Adult selection mode
			Test.RANK, //Parent selection mode
			0.1,
			0.3,
			new OneMax(40) //Fitness function // one-max 40
		);
	
	
	static Test testParameters5 = new Test(
			50, //Maximum number of generations
			20, //Maximum size of a population (20 - 100)
			0.01, //Mutation rate (0.005 - 0.02)
			0.92, //Crossover rate (0.6 - 0.95)
			Test.OVER_PRODUCTION, //Adult selection mode
			Test.TOURNAMENT, //Parent selection mode
			0.1,
			0.3,
			new OneMax(100) //Fitness function // one-max 100
		);
	
	static Test testParameters6 = new Test(
			100, //Maximum number of generations
			20, //Maximum size of a population (20 - 100)
			0.02, //Mutation rate (0.005 - 0.02)
			0.7, //Crossover rate (0.6 - 0.95)
			Test.MIXING, //Adult selection mode
			Test.TOURNAMENT, //Parent selection mode
			0.1,
			0.3,
			new Lolz(40, 21) //Fitness function
		);
	
	
	static Test testParameters7 = new Test(
			2000, //Maximum number of generations
			50, //Maximum size of a population (20 - 100)
			0.004, //Mutation rate (0.005 - 0.02) 0.21, 0.22, 0.23
			0.95, //Crossover rate (0.6 - 0.95)
			Test.MIXING, //Adult selection mode
			Test.TOURNAMENT, //Parent selection mode
			0.25,
			0.1,
			new GloballySurprisingSequence(49, 20) //Fitness function
		);
	
	
	static Test testParameters8 = new Test(
			2000, //Maximum number of generations
			50, //Maximum size of a population
			0.0015, //Mutation rate
			0.95, //Crossover rate
			Test.MIXING, //Adult selection mode
			Test.TOURNAMENT, //Parent selection mode
			0.01, //Probability epsilon of random choice in tournament mode
			0.99, //Proportion pK of the population to fight in tournament mode
			new LocallySurprisingSequence(195, 15) //Fitness function
		);
	
	static Test testParameters9 = new Test(
			3000, //Maximum number of generations
			50, //Maximum size of a population
			0.00070, //Mutation rate
			0.95, //Crossover rate
			Test.MIXING, //Adult selection mode
			Test.TOURNAMENT, //Parent selection mode
			0.25, //Probability epsilon of random choice in tournament mode
			0.1, //Proportion pK of the population to fight in tournament mode
			new LocallySurprisingSequence(335, 20) //Fitness function
		);
	
	
	
	//Constructor
	public FitnessFunction(int genes) {
		nGenes = genes;
	}

	
	//Fitness function
	public abstract int fitness(int[] vector);
	
	
	//Getter
	
	public int getNGenes() {
		return nGenes;
	}
	
}
