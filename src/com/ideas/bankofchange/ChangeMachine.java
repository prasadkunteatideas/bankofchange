package com.ideas.bankofchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChangeMachine {
	private TreeMap<Integer, Integer> denominationMap=new TreeMap<Integer, Integer>(Collections.reverseOrder());
	private static final List<Integer> validDenominations=new ArrayList<Integer>();
	public ChangeMachine() {
		validDenominations.add(1000);
		validDenominations.add(100);
		validDenominations.add(50);
		validDenominations.add(20);
		validDenominations.add(10);
		validDenominations.add(5);
		validDenominations.add(2);
		validDenominations.add(1);
	}
	public Map<Integer, Integer> getCurrentDenominationsSituation() {
		return denominationMap;
	}

	public boolean loadValidDenominations(final Map<Integer, Integer> myDenominationMap) {
		boolean validDenominationsLoaded=true;
		for (Integer denomination : myDenominationMap.keySet()) {
			if(isDenominationValid(denomination))
				denominationMap.put(denomination, myDenominationMap.get(denomination)+(denominationMap.get(denomination)!=null?denominationMap.get(denomination):0));
			else{
				validDenominationsLoaded=false;
				break;
			}
		}
		return validDenominationsLoaded;
	}
	private boolean isDenominationValid(Integer denomination) {
		return validDenominations.contains(denomination);
	}
	public Map<Integer, Integer> getChange(final Integer myNote) {
		TreeMap<Integer, Integer> returnedChange=new TreeMap<Integer, Integer>();
		if(!isDenominationValid(myNote)){
			returnedChange.put(myNote, 1);
		}
		Map<Integer, Integer> clonedDenominationMap=cloneOriginalDenominationState();
		Integer incomingAmount=myNote;
		for (Integer denomination : denominationMap.keySet()) {
			Integer availableNotesForThisDenomination = denominationMap.get(denomination);
			if(denomination < myNote && availableNotesForThisDenomination>0){
				int numberOfNotesExpectedToReturn=incomingAmount/denomination;
				int numberOfNotesToReturn=numberOfNotesExpectedToReturn<availableNotesForThisDenomination?numberOfNotesExpectedToReturn:availableNotesForThisDenomination;
				returnedChange.put(denomination, numberOfNotesToReturn);
				incomingAmount=incomingAmount-(denomination*numberOfNotesToReturn);
				denominationMap.put(denomination, availableNotesForThisDenomination-numberOfNotesToReturn);
			}
			if(incomingAmount==0)
				break;
		}
		if(incomingAmount!=0){
			returnedChange=new TreeMap<Integer, Integer>();
			returnedChange.put(myNote, 1);
			denominationMap=(TreeMap<Integer, Integer>) clonedDenominationMap;
		}
		return returnedChange;
	}
	private Map<Integer, Integer> cloneOriginalDenominationState() {
		Map<Integer, Integer> clonedMap=new TreeMap<Integer, Integer>();
		for (Integer denomination : denominationMap.descendingKeySet()) {
			clonedMap.put(denomination, denominationMap.get(denomination));
		}
		return clonedMap;
	}
	public List<Integer> getFinishedDenominations() {
		List<Integer> finishedDenominations=new ArrayList<Integer>();
		for (Integer denomination : denominationMap.descendingKeySet()) {
			if(denominationMap.get(denomination)==0)
				finishedDenominations.add(denomination);
		}
		return finishedDenominations;
	}

}