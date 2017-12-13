package com.main;

import java.util.Scanner;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class Main {

	protected static XYSeriesCollection dataset = new XYSeriesCollection();
	protected static int counter = 1;
	
	protected static int generationsNeeded = 1;
	protected static String longestFound = "";
	
	protected static Plot chart;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Set parameters
		Test testParameters = setParameters();
		
		//Draw graph
		chart = new Plot(
				"Results",
				"Evolution of the " + testParameters.getFitnessFunctionName() + "(" + testParameters.getFitnessFunction().getNGenes() + ")" + " fitness function", 
				dataset,
				testParameters
			);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		//Launch simulations
		System.out.println("Number of simulations: ");
		int nSimulations = new Scanner(System.in).nextInt();
		try {
			launchSeveralSimulations(
					testParameters,
					nSimulations
			);
		}
		catch (IllegalArgumentException e) {
			if (e.getMessage() != "FOUND")
				throw e;
		}
		if (longestFound != "") {
			new Log("SOLUTION");
			Log.log(longestFound);
			Log.log(generationsNeeded + " generations needed");
		}
		
	}
	
	
	
	
	
	
	
	
	//Set test parameters
	public static Test setParameters() {
		
		//User input
		System.out.println("Number of generations: ");
		int nGenerations = new Scanner(System.in).nextInt();
		System.out.println("Size of population: ");
		int sizePop = new Scanner(System.in).nextInt();
		System.out.println("Mutation rate: ");
		double mutationRate = new Scanner(System.in).nextDouble();
		System.out.println("Crossover rate: ");
		double crossoverRate = new Scanner(System.in).nextDouble();
		System.out.println("Adult selection mode (1 = FULL, 2 = OVER PRODUCTION, 3 = MIXING): ");
		int adultSelectionMode = new Scanner(System.in).nextInt();
		System.out.println("Parent selection mode (1 = FITNESS PROPORTIONATE, 2 = SIGMA SCALING, 3 = RANK, 4 = TOURNAMENT): ");
		int parentSelectionMode = new Scanner(System.in).nextInt();
		double epsilon = 0;
		double pK = 0;
		if (parentSelectionMode == 4) {
			System.out.println("Probability of random choice: ");
			epsilon = new Scanner(System.in).nextDouble();
			System.out.println("Proportion of competitors: ");
			pK = new Scanner(System.in).nextDouble();
		}
		System.out.println("Problem (1 = One-Max, 2 = One-Max Special, 3 = LOLZ, 4 = Globally Surprising Sequence, 5 = Locally Surprising Sequence): ");
		int problem = new Scanner(System.in).nextInt();
		System.out.println("Number of genes: ");
		int nGenes = new Scanner(System.in).nextInt();
		int nArg2 = 0;
		if (problem >= 3) {
			if (problem == 3)
				System.out.println("Threshold: ");
			else
				System.out.println("Number of symbols: ");
			nArg2 = new Scanner(System.in).nextInt();
		}
		//Fitness function
		FitnessFunction fitnessFunction;
		switch (problem) {
		case 1:
			fitnessFunction = new OneMax(nGenes);
			break;
		case 2:
			fitnessFunction = new OneMaxSpecial(nGenes);
			break;
		case 3:
			fitnessFunction = new Lolz(nGenes, nArg2);
			break;
		case 4:
			fitnessFunction = new GloballySurprisingSequence(nGenes, nArg2);
			break;
		case 5:
			fitnessFunction = new LocallySurprisingSequence(nGenes, nArg2);
			break;
		default:
			throw new IllegalArgumentException("Invalid problem");
		}
		//Initiate a new test
		Test testParameters = new Test(
				nGenerations,
				sizePop,
				mutationRate,
				crossoverRate,
				adultSelectionMode,
				parentSelectionMode,
				epsilon,
				pK,
				fitnessFunction
			);
		
		return testParameters;
		
	}
	
	
	
	//Start evolution simulation
	public static int launchSimulation(Test testParameters, int nSimulations) {
		
		//Prepare all the populations
		Population[] populations = new Population[testParameters.getMaxNumberOfGenerations()];
		
		//Initialize the first population for the test
		populations[0] = new Population(testParameters);
		
		//Best fitness
		int maxFitnessEverReached = populations[0].maxFitness();
		int rankMaxFitnessEverReached = 0; 
		
		//Draw graph
		chart.setVisible(true);
		XYSeries maxFitnesses;
		XYSeries averageFitnesses = new XYSeries("Average fitness");;
		XYSeries sdFitnesses = new XYSeries("Standard deviation in fitness");
		if (nSimulations == 1) {
			maxFitnesses = new XYSeries("Max fitness");
			dataset.addSeries(maxFitnesses);
			dataset.addSeries(averageFitnesses);
			dataset.addSeries(sdFitnesses);
		}
		else {
			maxFitnesses = new XYSeries("Max fitness (run " + counter + ")");
			dataset.addSeries(maxFitnesses);
			counter++;
		}
		
		//Launch evolution
		for (int i = 0 ; i < testParameters.getMaxNumberOfGenerations() - 1 ; i++) {
			//Logging routine
			new Log("Generation n'" + (i+1));
			Log.log("Size: " + populations[i].getPopulationSize());
			Log.log("Best fitness: " + populations[i].maxFitness());
			Log.log("Average fitness: " + populations[i].averageFitness());
			Log.log("Standard deviation: " + populations[i].standardDeviationFitness());
			Log.log("Best phenotype: ");
			Log.log(populations[i].bestGenotype()); //Log.log converts genotype into phenotype representation
			//Additional logging routine for surprising problem
			if (testParameters.getFitnessFunctionName().equals("GloballySurprisingSequence")) {
				Log.log("Best corresponding sequence: ");
				int[] seq = ((GloballySurprisingSequence)(testParameters.getFitnessFunction())).decodeFromGray(populations[i].bestGenotype().phenotype.attributes);
				Log.log(seq);
				Log.log("nFailures: " + ((GloballySurprisingSequence)testParameters.getFitnessFunction()).nOccurencesNotSurprising(seq));
			}
			else if (testParameters.getFitnessFunctionName().equals("LocallySurprisingSequence")) {
				Log.log("Best corresponding sequence: ");
				int[] seq = ((LocallySurprisingSequence)(testParameters.getFitnessFunction())).decodeFromGray(populations[i].bestGenotype().phenotype.attributes);
				Log.log(seq);
				Log.log("nFailures: " + ((LocallySurprisingSequence)testParameters.getFitnessFunction()).nOccurencesNotSurprising(seq));
			}
			//Update max fitness ever reached
			if (populations[i].maxFitness() > maxFitnessEverReached) {
				maxFitnessEverReached = populations[i].maxFitness();
				rankMaxFitnessEverReached = i;
			}
			//Get next generation
			populations[i+1] = populations[i].nextGeneration(); 
			//Update graph data
			maxFitnesses.add(i, (double)populations[i].maxFitness());
			if (nSimulations == 1) {
				averageFitnesses.add(i, populations[i].averageFitness());
				sdFitnesses.add(i, populations[i].standardDeviationFitness());
			}
		}
		
		//Log recap
		new Log("Best solution found");
		Log.log("Fitness: " + maxFitnessEverReached);
		Log.log("Generation: " + rankMaxFitnessEverReached);
		Log.log("Phenotype: ");
		Log.log(populations[rankMaxFitnessEverReached].bestGenotype());
		//Additional logging routine for surprising problem
		if (testParameters.getFitnessFunctionName().equals("GloballySurprisingSequence")) {
			Log.log("Best corresponding sequence: ");
			int[] seq = ((GloballySurprisingSequence)(testParameters.getFitnessFunction())).decodeFromGray(populations[rankMaxFitnessEverReached].bestGenotype().phenotype.attributes);
			Log.log(seq);
			Log.log("nFailures: " + ((GloballySurprisingSequence)testParameters.getFitnessFunction()).nOccurencesNotSurprising(seq));
		}
		else if (testParameters.getFitnessFunctionName().equals("LocallySurprisingSequence")) {
			Log.log("Best corresponding sequence: ");
			int[] seq = ((LocallySurprisingSequence)(testParameters.getFitnessFunction())).decodeFromGray(populations[rankMaxFitnessEverReached].bestGenotype().phenotype.attributes);
			Log.log(seq);
			Log.log("nFailures: " + ((LocallySurprisingSequence)testParameters.getFitnessFunction()).nOccurencesNotSurprising(seq));
		}
		
		return maxFitnessEverReached;
		
	}
	
	
	//Launch multiple simulations
	public static void launchSeveralSimulations(Test testParameters, int nSimulations) {
		for (int i = 0 ; i < nSimulations ; i++) {
			generationsNeeded = 0;
			launchSimulation(testParameters, nSimulations);
		}
	}
	

	


}


