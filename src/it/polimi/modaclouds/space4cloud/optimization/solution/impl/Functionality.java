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
package it.polimi.modaclouds.space4cloud.optimization.solution.impl;

import it.polimi.modaclouds.space4cloud.lqn.LqnResultParser;
import it.polimi.modaclouds.space4cloud.optimization.solution.IResponseTimeConstrainable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Functionality implements Cloneable, IResponseTimeConstrainable,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5621349760322110134L;
	private String name;
	private String id;
	private String entryLevelCallID = null;
	private double responseTime;
	private Component container;
	private HashMap<String, Functionality> externalCalls = new HashMap<String, Functionality>();

	public Functionality(String name, String id, String entryLevelCallID) {
		this.name = name;
		this.id = id;
		this.entryLevelCallID = entryLevelCallID;
	}

	public void addExternalCall(String id, Functionality fun) {
		externalCalls.put(id, fun);
	}

	@Override
	public Functionality clone() {
		Functionality f;
		try {
			f = (Functionality) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			f = new Functionality(name, id, entryLevelCallID);
		}
		f.setContainer(null);

		f.setExternalCalls(new HashMap<String, Functionality>(
				getExternalCalls().size()));
		for (String s : externalCalls.keySet())
			f.addExternalCall(s, externalCalls.get(s));
		return f;
	}

	public Component getContainer() {
		return container;

	}

	public String getEntryLevelCallID() {
		return entryLevelCallID;
	}

	/**
	 * 
	 * @return a hashmap with the functionality called DIRECTLY
	 */
	public HashMap<String, Functionality> getExternalCalls() {
		return externalCalls;
	}

	/**
	 * 
	 * @return a list with all the functionalities called for the execution.
	 */
	public List<Functionality> getExternalCallTrace() {
		ArrayList<Functionality> calls = new ArrayList<>();
		for (Functionality f : externalCalls.values()) {
			// add the direct external call
			calls.add(f);
			// add the indirect calls
			calls.addAll(f.getExternalCallTrace());
		}
		return calls;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public double getResponseTime() {
		return responseTime;
	}

	public void setContainer(Component comp) {
		this.container = comp;
	}

	public void setEntryLevelCallID(String entryLevelCallID) {
		this.entryLevelCallID = entryLevelCallID;
	}

	private void setExternalCalls(HashMap<String, Functionality> externalCalls) {
		this.externalCalls = externalCalls;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	public void show(String prefix) {
		System.out.println(prefix + "Functionality name: " + getName()
				+ " id: " + getId() + " callId: " + getEntryLevelCallID()
				+ " response time: " + getResponseTime());
	}

	@Override
	public void update(LqnResultParser parser) {
		if (parser.getResponseTime(name) > 0)
			responseTime = parser.getResponseTime(name);
		else
			responseTime = -1;
		// System.err.println("Functionality "+getName()+" not found in the results");

	}
}
