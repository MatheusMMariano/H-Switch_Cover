package SwitchCover.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;

public class NodeConverter {
	
	private Graph newGraph = new Graph();
	private Transition firstTransitionTemp = new Transition();
	
	public NodeConverter(){
		
	}
	
	//STEP 02
	public void createInicialStates(List<Transition> transitionsList, String typeFile){
		Iterator<Transition> transitionsIterator = transitionsList.iterator();
		
		while(transitionsIterator.hasNext()){
			Transition transition = (Transition)transitionsIterator.next();
			State checkState = transition.getSource();
			
			if(typeFile.equals("xml")){
				if(checkState.getTypeState().equals("inicial")/* && transition.getOutput().equals("0")*/){
					State state = new State();
					if(!(transition.getOutput() == null)) state.setName(transition.getInput()+"/"+transition.getOutput());
					else state.setName(transition.getInput());
					
					state.setTypeState(transition.getSource().getTypeState());
					newGraph.setStateMap(state.getName(), state);
				}
			}
			else{
				if(checkState.getTypeState().equals("inicial") && transition.getOutput().equals("0")){
					State state = new State();
					if(!(transition.getOutput() == null)) state.setName(transition.getInput()+"/"+transition.getOutput());
					else state.setName(transition.getInput());
					
					state.setTypeState(transition.getSource().getTypeState());
					newGraph.setStateMap(state.getName(), state);
				}
			}
			
		}
	}
	
	public boolean checkDuplicationTransition(Graph graph, String transitionName){
		Iterator<String> statesIterator = graph.getIteratorState();
		
		while(statesIterator.hasNext()){
			String stateName = (String) statesIterator.next();
			if(transitionName.equals(stateName)) return true;
		}
		return false;
	}
	
	public void createStatesList(List<Transition> transitionsList, String typeFile){
		//Create the inicial states
		createInicialStates(transitionsList, typeFile);
		
		//Create a iterator list of transitions
		Iterator<Transition> transitionsIterator = transitionsList.iterator();
		
		while(transitionsIterator.hasNext()){
			Transition newTransition = ((Transition)transitionsIterator.next());
			
			//Check if there a state at graph with this name; if there, discards
			if(!checkDuplicationTransition(newGraph, newTransition.getInput())){
				
				State state = new State();
				if(!(newTransition.getOutput() == null)) state.setName(newTransition.getInput()+"/"+newTransition.getOutput());
				else state.setName(newTransition.getInput());
				
				//if(newTransition.getSource().getTypeState().equals("inicial") && newTransition.getInput().equals("0>0")) state.setTypeState("inicial");
				//else state.setTypeState("normal");
				state.setTypeState(newTransition.getSource().getTypeState());
				newGraph.setStateMap(state.getName(), state);
			}
		}
	}
	
	public Graph transitionsConvertedNode(Graph graph, String typeFile){
		List<Transition> transitionsList = new ArrayList<Transition>();
		Iterator<String> stateIterator = graph.getIteratorState();
		
		//Check for a next state
		while(stateIterator.hasNext()){
			//Get transitions of state
			Iterator<Transition> transitionsIterator = graph.getState((String) stateIterator.next()).getTransitionIterator();
			//Add transition at list
			while(transitionsIterator.hasNext()){
				transitionsList.add((Transition)transitionsIterator.next());
			}
		}
		
		//Create a new graph of HashMap of States, at states without transitions
		createStatesList(transitionsList, typeFile);
		
		//Check the old graph
		Iterator<String> sIterator = graph.getIteratorState();
		
		while(sIterator.hasNext()){
			String stateName = (String)sIterator.next();
			State firstState = graph.getState(stateName);
			
			if(firstState.getTransitions() != null){
				Iterator<Transition> transIt = firstState.getTransitionIterator();
				
				//Check if state have son; if have, get the transitions e return a state
				while(transIt.hasNext()){
					firstTransitionTemp = (Transition)transIt.next();
					State secondState = firstTransitionTemp.getDestination();
					Iterator<Transition> transItSecondState = secondState.getTransitionIterator();
					
					while(transItSecondState.hasNext()){
						Transition secondTransitionTemp = (Transition)transItSecondState.next();
						
						//Create a transition
						Transition newTransition = new Transition();
						
						if(secondTransitionTemp.getOutput() == null){ //== NULL
							//Create a transition
							State stateSource = (State)newGraph.getStatesMap().get(firstTransitionTemp.getInput());
							State stateDestination = (State)newGraph.getStatesMap().get(secondTransitionTemp.getInput());
							
							newTransition.setSource(stateSource);
							newTransition.setDestination(stateDestination);
							newTransition.setInput(stateSource.getName()+stateDestination.getName());
							
							//Add transition at first state of the new graph
							State stateNewGraph = (State)newGraph.getStatesMap().get(firstTransitionTemp.getInput());
							stateNewGraph.getTransitions().add(newTransition);				
						}
						else{
							State stateSource = (State)newGraph.getStatesMap().get(firstTransitionTemp.getInput()+"/"+firstTransitionTemp.getOutput());
							State stateDestination = (State)newGraph.getStatesMap().get(secondTransitionTemp.getInput()+"/"+secondTransitionTemp.getOutput());
							
							newTransition.setSource(stateSource);
							newTransition.setDestination(stateDestination);
							newTransition.setInput(stateSource.getName()+stateDestination.getName());
							newTransition.setName(firstTransitionTemp.getName()+"/"+secondTransitionTemp.getName());
							
							//Add transition at first state of the new graph
							State stateNewGraph = (State)newGraph.getStatesMap().get(firstTransitionTemp.getInput()+"/"+firstTransitionTemp.getOutput());
							stateNewGraph.getTransitions().add(newTransition);
						}
					}
				}
			}
		}
		
		return newGraph;
	}
}