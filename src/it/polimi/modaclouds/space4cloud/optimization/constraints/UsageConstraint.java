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
package it.polimi.modaclouds.space4cloud.optimization.constraints;

import it.polimi.modaclouds.space4cloud.optimization.solution.IConstrainable;
import it.polimi.modaclouds.space4cloud.optimization.solution.IUtilizationConstrainable;

public class UsageConstraint extends QoSConstraint {

	public UsageConstraint(
			it.polimi.modaclouds.qos_models.schema.Constraint constraint) {
		super(constraint);
		//convert 0.x into x over 100
		this.range.setHasMaxValue(this.range.getHasMaxValue()*100);
		
		
	}

	@Override
	public double checkConstraintDistance(IConstrainable resource) {
		if(!(resource instanceof IUtilizationConstrainable)){
			logger.error("Evaluating a RAM constraint on a wrong resource with id: "+resource.getId()+
					" RAM constraints should be evaluated against "+IUtilizationConstrainable.class+
					", the specified resource is of type: "+resource.getClass());
			return -1;
			}
			return super.checkConstraintDistance(((IUtilizationConstrainable)resource).getUtilization());
	}

}
