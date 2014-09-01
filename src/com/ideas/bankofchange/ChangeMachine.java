package com.ideas.bankofchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChangeMachine {
	// testing
	private TreeMap<Integer, Integer> denominationMap = new TreeMap<Integer, Integer>(
			Collections.reverseOrder());
	private static final List<Integer> validDenominations = Arrays.asList(1000,
			100, 50, 20, 10, 5, 2, 1);

	public Map<Integer, Integer> getCurrentDenominationsSituation() {
		return denominationMap;
	}

	public boolean loadValidDenominations(
			final Map<Integer, Integer> myDenominationMap) {
		boolean validDenominationsLoaded = true;
		for (Integer denomination : myDenominationMap.keySet()) {
			if (isDenominationValid(denomination))
				denominationMap
						.put(denomination,
								myDenominationMap.get(denomination)
										+ (denominationMap.get(denomination) != null ? denominationMap
												.get(denomination) : 0));
			else {
				validDenominationsLoaded = false;
				break;
			}
		}
		return validDenominationsLoaded;
	}

	public Map<Integer, Integer> getChange(final Integer myNote) {
		if (!isDenominationValid(myNote)) {
			return returnIncomingNote(myNote);
		}
		TreeMap<Integer, Integer> returnedChange = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> clonedDenominationMap = cloneOriginalDenominationState();
		Integer incomingAmount = myNote;

		for (Integer denomination : denominationMap.keySet()) {
			Integer availableNotesForThisDenomination = denominationMap
					.get(denomination);
			if (canChangeBeGivenFromThisDenomination(myNote, denomination,
					availableNotesForThisDenomination)) {
				int numberOfNotesToReturn = numberOfNotesToReturnForThisDenomination(
						incomingAmount, denomination,
						availableNotesForThisDenomination);
				returnedChange.put(denomination, numberOfNotesToReturn);
				incomingAmount = incomingAmount
						- (denomination * numberOfNotesToReturn);
				denominationMap.put(denomination,
						availableNotesForThisDenomination
								- numberOfNotesToReturn);
			}
			if (incomingAmount == 0)
				break;
		}
		if (incomingAmount != 0) {
			denominationMap = (TreeMap<Integer, Integer>) clonedDenominationMap;
			return returnIncomingNote(myNote);
		}
		return returnedChange;
	}

	public List<Integer> getFinishedDenominations() {
		List<Integer> finishedDenominations = new ArrayList<Integer>();
		for (Integer denomination : denominationMap.descendingKeySet()) {
			if (denominationMap.get(denomination) == 0)
				finishedDenominations.add(denomination);
		}
		return finishedDenominations;
	}

	private boolean isDenominationValid(Integer denomination) {
		return validDenominations.contains(denomination);
	}

	private TreeMap<Integer, Integer> returnIncomingNote(final Integer myNote) {
		TreeMap<Integer, Integer> returnedChange = new TreeMap<Integer, Integer>();
		returnedChange.put(myNote, 1);
		return returnedChange;
	}

	private boolean canChangeBeGivenFromThisDenomination(final Integer myNote,
			Integer denomination, Integer availableNotesForThisDenomination) {
		return denomination < myNote && availableNotesForThisDenomination > 0;
	}

	private int numberOfNotesToReturnForThisDenomination(
			Integer incomingAmount, Integer denomination,
			Integer availableNotesForThisDenomination) {
		int numberOfNotesExpectedToReturn = incomingAmount / denomination;
		int numberOfNotesToReturn = numberOfNotesExpectedToReturn < availableNotesForThisDenomination ? numberOfNotesExpectedToReturn
				: availableNotesForThisDenomination;
		return numberOfNotesToReturn;
	}

	private Map<Integer, Integer> cloneOriginalDenominationState() {
		Map<Integer, Integer> clonedMap = new TreeMap<Integer, Integer>();
		for (Integer denomination : denominationMap.descendingKeySet()) {
			clonedMap.put(denomination, denominationMap.get(denomination));
		}
		return clonedMap;
	}

}
