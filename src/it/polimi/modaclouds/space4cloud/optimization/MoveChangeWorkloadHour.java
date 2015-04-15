package it.polimi.modaclouds.space4cloud.optimization;

import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Solution;

import java.util.ArrayList;

public class MoveChangeWorkloadHour extends AbsMoveHour {

	private double rate = 0.0;
	private boolean changed = false;

	/**
	 * 
	 * @param sol
	 *            - The Solution in which the resource is located
	 * @param i
	 *            the hour of the solution
	 */
	public MoveChangeWorkloadHour(Solution sol, int i) {
		this.propertyNames = new ArrayList<>();
		this.propertyValues = new ArrayList<>();

		setSolution(sol);
		setHour(i);
	}

	@Override
	public Solution apply() {
		application.changeValues(null, this.propertyNames, this.propertyValues);
		if (changed) {
			currentSolution.setPercentageWorkload(hour, rate);
			rate = 0.0;
			changed = false;
		}

		return this.currentSolution;
	}

	public IMoveHour modifyWorkload(int population, double rate) {
		propertyNames.clear();
		propertyValues.clear();
		propertyNames.add("workload");
		int newPopulation = (int) Math.ceil(population * rate);
//		if (newPopulation == 0)
//			newPopulation=1;
		propertyValues.add(newPopulation);
		this.rate = rate;
		changed = true;
		return this;
	}

}