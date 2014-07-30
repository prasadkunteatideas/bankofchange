package com.ideas.bankofchange;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestChangeMachine {
	
	private ChangeMachine changeMachine;

	@Test
	public void testLoadValidDenominations(){
		changeMachine = new ChangeMachine();
		Map<Integer, Integer> myDenominationMap=new HashMap<Integer, Integer>();
		myDenominationMap.put(100, 100);
		myDenominationMap.put(50, 200);
		assertTrue(true==changeMachine.loadValidDenominations(myDenominationMap));
	}
	
	@Test
	public void testLoadInvalidDenominations(){
		changeMachine = new ChangeMachine();
		Map<Integer, Integer> myDenominationMap=new HashMap<Integer, Integer>();
		myDenominationMap.put(100, 100);
		myDenominationMap.put(75, 200);
		assertTrue(false==changeMachine.loadValidDenominations(myDenominationMap));
	}
	
	@Test
	public void testGiveMeAValidChange(){
		loadDenominations();
		Integer myNote=new Integer(100);
		Map<Integer, Integer> changeMap=changeMachine.getChange(myNote);
		assertTrue(2==changeMap.get(50));
		
		myNote=new Integer(50);
		changeMap=changeMachine.getChange(myNote);
		assertTrue(2==changeMap.get(20));
		assertTrue(1==changeMap.get(10));
		

		myNote=new Integer(100);
		changeMap=changeMachine.getChange(myNote);
		assertTrue(1==changeMap.get(100));

		myNote=new Integer(20);
		changeMap=changeMachine.getChange(myNote);
		assertTrue(2==changeMap.get(10));
	}
	
	
	@Test
	public void testHasMachineReducedTheDenomincationsCountAfterChange(){
		loadDenominations();
		Integer myNote=new Integer(100);
		changeMachine.getChange(myNote);

		myNote=new Integer(50);
		changeMachine.getChange(myNote);
		

		myNote=new Integer(100);
		changeMachine.getChange(myNote);
		

		myNote=new Integer(20);
		changeMachine.getChange(myNote);
		Map<Integer, Integer> currentDenominations = changeMachine.getCurrentDenominationsSituation();
		assertTrue(1==currentDenominations.get(100));
		assertTrue(0==currentDenominations.get(50));
		assertTrue(1==currentDenominations.get(20));
		assertTrue(1==currentDenominations.get(10));
		
	}	
	
	@Test
	public void testIsSystemGivingFinishedDenominations(){
		loadDenominations();
		Integer myNote=new Integer(100);
		changeMachine.getChange(myNote);

		myNote=new Integer(50);
		changeMachine.getChange(myNote);
		

		myNote=new Integer(100);
		changeMachine.getChange(myNote);
		

		myNote=new Integer(20);
		changeMachine.getChange(myNote);
		
		List<Integer> finishedDeniminations=changeMachine.getFinishedDenominations();
		assertEquals(1, finishedDeniminations.size());
		assertEquals(50, finishedDeniminations.get(0).intValue());
		
	}	

	
	@Test
	public void testReturnIfMyNoteIsAnInvalidNote(){
		loadDenominations();
		Integer myNote=new Integer(1120);
		Map<Integer, Integer> changeMap=changeMachine.getChange(myNote);
		assertTrue(1==changeMap.get(1120));
	}

	@Test
	public void test_Notes_Are_Correctly_Loaded_For_Same_Denominations_Multiple_Times(){
		changeMachine = new ChangeMachine();
		Map<Integer, Integer> myDenominationMap=new HashMap<Integer, Integer>();
		myDenominationMap.put(100, 1);
		changeMachine.loadValidDenominations(myDenominationMap);
		assertTrue(1==changeMachine.getCurrentDenominationsSituation().get(100));
		myDenominationMap=new HashMap<Integer, Integer>();
		myDenominationMap.put(100, 4);
		changeMachine.loadValidDenominations(myDenominationMap);
		assertTrue(5==changeMachine.getCurrentDenominationsSituation().get(100));
	}
	private void loadDenominations() {
		changeMachine = new ChangeMachine();
		Map<Integer, Integer> myDenominationMap=new HashMap<Integer, Integer>();
		myDenominationMap.put(100, 1);
		myDenominationMap.put(50, 2);
		myDenominationMap.put(20, 3);
		myDenominationMap.put(10, 4);
		changeMachine.loadValidDenominations(myDenominationMap);
	}
}
