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

import it.polimi.modaclouds.qos_models.schema.AggregateFunction;
import it.polimi.modaclouds.qos_models.schema.Range;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Constraint {

	protected String id;
	protected String name;
	protected String resourceId;
	protected Metric metric;
	protected AggregateFunction metricAcggregationFunction;
	protected int priority;
	protected Range range;
	protected static final Logger logger = LoggerFactory
			.getLogger(Constraint.class);

	public Constraint(
			it.polimi.modaclouds.qos_models.schema.Constraint constraint) {
		this.id = constraint.getId();
		this.name = constraint.getName();
		this.resourceId = constraint.getTargetResourceIDRef();
		this.metric = Metric.getMetricFromTag(constraint.getMetric());
		this.priority = (constraint.getPriority() == null ? 0 : constraint
				.getPriority()).intValue();
		this.range = constraint.getRange();
	}

	public Constraint(String id, String name, String resourceId, Metric metric,
			int priority) {
		this.id = id;
		this.name = name;
		this.resourceId = resourceId;
		this.metric = metric;
		this.priority = priority;
	}

	// Positive distance if the constraint has not been fulfilled.
	public double checkConstraintDistance(Object measurement) {
		double value = 0;
		if (measurement instanceof Double)
			value = (Double) measurement;
		else if (measurement instanceof Integer)
			value = ((Integer) measurement).doubleValue();
		else if (measurement instanceof Float)
			value = ((Float) measurement).doubleValue();
		else
			logger.error("Error in casting the value to check");
		if (range.getHasMaxValue() != null)
			return value - range.getHasMaxValue();
		else
			return range.getHasMinValue() - value;
	}

	public Metric getMetric() {
		return metric;
	}

	public int getPriority() {
		return priority;
	}

	public String getResourceID() {
		return resourceId;
	}

	public boolean hasNumericalRange() {
		if (range.getHasMaxValue() != null || range.getHasMinValue() != null)
			return true;
		return false;
	}

}
