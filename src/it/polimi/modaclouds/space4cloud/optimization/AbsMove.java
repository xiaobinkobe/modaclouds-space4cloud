/*******************************************************************************
 * Copyright 2014 Giovanni Paolo Gibilisco
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package it.polimi.modaclouds.space4cloud.optimization;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Solution;

/**
 * @author Michele Ciavotta
 * 
 */
public abstract class AbsMove implements IMove {

	/** The current solution. */
	protected Solution currentSolution = null;

	protected static final Logger logger = LoggerFactory.getLogger(AbsMove.class);
	/*
	 * the generic move can change a set of properties at once as, for example,
	 * in the case of changing the type of VM
	 */

	/** The list of property names. */
	protected ArrayList<String> propertyNames = new ArrayList<>();

	/** The list of property values. */
	protected ArrayList<Object> propertyValues = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.modaclouds.space4cloud.optimization.IMove#apply()
	 */
	@Override
	public abstract Solution apply();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.modaclouds.space4cloud.optimization.IMove#setSolution(it.polimi
	 * .modaclouds.space4cloud.optimization.solution.impl.Solution)
	 */
	@Override
	public IMove setSolution(Solution solution) {
		currentSolution = solution;
		return this;
	}

}
