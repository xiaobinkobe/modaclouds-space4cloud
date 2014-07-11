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
package it.polimi.modaclouds.space4cloud.optimization.evaluation;

import it.polimi.modaclouds.space4cloud.db.DatabaseConnectionFailureExteption;
import it.polimi.modaclouds.space4cloud.lqn.LqnResultParser;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Instance;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Solution;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class define a proxy object able to verify if a application has been already evaluated
 * in order to speed up the whole evaluation process
 * */
public class EvaluationProxy extends EvaluationServer {

	private final Map<String, LqnResultParser> map = new HashMap<String, LqnResultParser>();
	private static final Logger profileLogger = LoggerFactory.getLogger("ProfilerLogger");

	int hit = 0;

	// This enable or disable the proxy! If set to "false", the proxy won't act
	// as a proxy, evaluating
	// each and every solution requested. Useful!
	private boolean enabled = true;
	private int miss = 0;

	private int getMiss() {
		return miss;
	}

	public EvaluationProxy() throws DatabaseConnectionFailureExteption {
		super();
	}

	@Override
	public void EvaluateSolution(Solution sol) {
		logger.debug("Entering Proxy");
		this.ProxyIn(sol);
		super.EvaluateSolution(sol);
		this.ProxyOut(sol);
	}

	public int getHit() {
		return hit;
	}

	public void ProxyIn(Solution sol) {
		
		int missedEvaluations = 0;
		if (!enabled)
			return;

		for (Instance instance : sol.getApplications()) {
			if (!instance.isEvaluated()) {
				String str = instance.getHashString();
				if (map.containsKey(str)) {
					hit++;
					LqnResultParser results = map.get(str);
					instance.updateResults(results);
					instance.setEvaluated(true);
				}
				else
					missedEvaluations++;
			}
		}
		profileLogger.info(requestedEvaluations+","+missedEvaluations);
		
	}

	public Solution ProxyOut(Solution sol) {
		if (!enabled)
			return sol;

		for (Instance instance : sol.getApplications()) {
			miss++;
			String hashStr = instance.getHashString();			
			map.put(hashStr, instance.getResultParser());
		}
		return sol;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void showStatistics() {
		super.showStatistics();
		logger.debug("Proxy statistics:");
		logger.debug("Hit count: "+getHit());
		logger.debug("Miss count: "+getMiss());
		
	}

}
