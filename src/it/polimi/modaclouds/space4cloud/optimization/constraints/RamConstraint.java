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

import it.polimi.modaclouds.space4cloud.optimization.solution.impl.CloudService;
import it.polimi.modaclouds.space4cloud.optimization.solution.impl.Compute;

public class RamConstraint extends ArchitecturalConstraint {


	public RamConstraint(it.polimi.modaclouds.qos_models.schema.Constraint constraint) {
		super(constraint);
	}

	@Override
	public boolean checkConstraint(CloudService resource) {
		if(resource instanceof Compute && 
				checkConstraintDistance(((Compute)resource).getRam()) < 0)
				return true;
		return false;
	}


	public double getMin(){
		return range.getHasMinValue();
	}
	

	public double getMax(){
		return range.getHasMaxValue();
	}
}
