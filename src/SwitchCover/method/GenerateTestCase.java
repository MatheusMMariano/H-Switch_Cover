package SwitchCover.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import SwitchCover.graph.Cycle;
import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;

/**
 * 1� fase
 * For em todas as transi��es setando elas como false em visitadas
 * come�ar a percorrer a partir do estado inicial at� achar o estado incial 
 * novamente. 
 * [Fazer depois -> colocar um peso nas transi��es para poder j� no primeiro ciclo
 * passar no maior n�mero de estados poss�veis. ]
 * O primeiro ciclo montado deve ser adicionado em uma lista de MAP<estadoinicial, ciclo1>
 * se existir estados ainda n�o visitados fa�a:
 * pergar ciclo 1 percorrer os estados visitados, se estado for diferente do estado chave, fa�a:
 * pegar o estado e pergunta: tem transi��es de saida desse estado, se sim, come�a a gerar um 
 * novo ciclo a partir desse estado, at� retornar a ele. 
 * quando terminar, adiciona na lista de map de ciclos. 
 * como est� dentro de um for, vai ser perguntado se o proximo estado tem transi��es de saida...
 * e assim vai at� terminar e todos os estados serem visitados.
 * 
 *  2� fase
 *
 * Nesse algoritmo para percorrer ser�o considerado os estados, e n�o as transi��es. Pois as
 * transi��es iniciais j� foram transformadas em estados, assim j� estar� passando por todas 
 * as transi��es. Dessa forma otimiza o tamanho da sequencia de teste, que poderia ser gigantesca. 
 *  
 */

public class GenerateTestCase {
	
	private Graph graph;
	
	private List<State> stateInitialList = new ArrayList<State>();
	private List<State> stateList = new ArrayList<State>();
	private List<String> listTestCase = new ArrayList<String>();
	private List<Cycle> cycleList = new ArrayList<Cycle>();
	private List<List<State>> finalCycle = new ArrayList<List<State>>();
	
	private State stateInitial = new State();
	private boolean caseFound = false;
	private String testCase;
	
	public GenerateTestCase(Graph graph){
		this.graph = graph;
	}
	
	private List<String> eulerianTestSuiteAllTranspairs(List<LinkedList<State>> testSuite){
		List<String> testListSequence = new LinkedList<String>();
		for(List<State> listState: testSuite){
			if(!listState.isEmpty()){
				String testList = "";
				for(State state: listState) {
					String input = state.getName().substring(state.getName().indexOf(">")+1, state.getName().indexOf("/"));
					if(input.length() <= 1) testList = testList + input;
					else testList = testList + input.charAt(0);	
				}
				testListSequence.add(testList);
			}
		}
		return testListSequence;
	}
	
	private List<String> eulerianTestSuiteAllTrans(List<LinkedList<Transition>> testSuite){
		List<String> testListSequence = new LinkedList<String>();
		for(List<Transition> listState: testSuite){
			if(!listState.isEmpty()){
				String testList = "";
				for(Transition t: listState) {
					testList = testList + (String.valueOf(t.getInput().charAt(t.getInput().length()-1)));
				}
			
				testListSequence.add(testList);
			}
		}
		return testListSequence;
	}
	
	private List<State> returnInicialStates(Graph graph){
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		while(statesIterator.hasNext()){
			State state = statesIterator.next();	
			
			if(state.getTypeState().equals("inicial")) stateInitialList.add(state);
		}
		return stateInitialList;
	}
	
	private void setFalseTransitionsStates(){
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		while(statesIterator.hasNext()){
			State state = statesIterator.next();
			state.setVisited(false);
			Iterator<Transition> transitionsIterator = state.getTransitionIterator();
			
			while(transitionsIterator.hasNext()){
				Transition t = transitionsIterator.next();
				t.setVisited(false);
			}
		}
	}

	private boolean checkStateNotVisited(){
		//Iterator<State> stateIterator = graph.getIteratorStateValue();

		//while(stateIterator.hasNext()){
			//State state = stateIterator.next();
			//if(state.getVisited() == false){
				Iterator<State> stateIt = graph.getIteratorStateValue();
				
				while(stateIt.hasNext()){
					State stateTrans = stateIt.next();
				
					if(stateTrans.getVisited() == true){
						Iterator<Transition> transitionIterator = stateTrans.getTransitionIterator();
						
						while(transitionIterator.hasNext()){		
							Transition transition = transitionIterator.next();
							if(transition.getVisited() == false){
								stateInitial = stateTrans;
								return true;
							}
						}
					}
				}
			//}
		//}
		return false;
	}
	
	private List<Cycle> course(State state){
		//System.out.println("\n>>"+state.getName()+": peso = "+state.getPonderosity());
		Iterator<Transition> transitionsIterator = state.getTransitionIterator();
		
		while(transitionsIterator.hasNext() && caseFound == false){
			Transition transition = transitionsIterator.next();
			//System.out.println("  |->"+transition.getSource().getName()+"->"+transition.getDestination().getName()+"["+transition.getName()+"]"+", "+transition.getVisited());
			
			if(transition.getVisited() == false){
				State stateDestination = transition.getDestination();
				stateDestination.setPonderosity(stateDestination.getPonderosity()+1);
				
				if(!stateDestination.equals(stateInitial)){
					if (!stateDestination.equals(state)){
						//System.out.println("     É DIFERENTE!");
						stateList.add(stateDestination);
						transition.setVisited(true);
						stateDestination.setVisited(true);
						testCase = testCase + stateDestination.getName() + ", ";
						//System.out.println("     "+testCase);
						course(stateDestination);
					}
				}
				else{
					//System.out.println("     É IGUAL!");
					transition.setVisited(true);
					//System.out.println("     "+testCase);
					listTestCase.add(testCase);
					
					Cycle cycle = new Cycle();
					State keyState = new State();
					keyState = stateInitial;
					cycle.setStateOrigin(keyState);
					cycle.setCycle(testCase);
					cycle.setStateList(stateList);
					cycleList.add(cycle);

					if(checkStateNotVisited()){
						stateList = new ArrayList<State>();
						stateList.add(stateInitial);
						testCase = new String();
						stateInitial.setVisited(true);
						course(stateInitial);
					}
					caseFound = true;
					break;
				}
			}
		}
		return cycleList;
	}
	
	public List<List<State>> initial(){
		Iterator<State> statesIterator = returnInicialStates(graph).iterator();
		
		while(statesIterator.hasNext()){
			stateInitial = statesIterator.next();
			//System.out.println(stateInitial.getName());
			setFalseTransitionsStates();
			
			stateInitial.setVisited(true);
			stateInitial.setInitialSequence(true);
			stateList = new ArrayList<State>();
			stateList.add(stateInitial);
			caseFound = false;
			
			EulerCycle makeCycle = new EulerCycle(course(stateInitial));
			finalCycle.add(makeCycle.createEulerCycle());
		}
		return finalCycle;
	}
	
	private LinkedList<Transition> createCycle(State currentState, State initalState){
		LinkedList<Transition> cycle = new LinkedList<Transition>();
		
		for(Transition transition: currentState.getTransitions()) {
			if(!transition.getVisited()) {
				transition.setVisited(true);
				cycle.add(transition);
				
				if(transition.getDestination() == initalState) return cycle;
				cycle.addAll(createCycle(transition.getDestination(), initalState));
				break;
			}
		}
		return cycle;
	}
	
	public boolean checkAllTransitionsWasVisited(State state) {
		List<Transition> list = state.getTransitions();
		for(Transition t: list) {
			if(!t.getVisited()) return false;
		}
		return true;
	}
	
	private LinkedList<State> generateEulerianCycleSequence(List<LinkedList<Transition>> cycleSet){
		int i = 0;
		//LinkedList<Transition> eulerianTestSuite = new LinkedList<Transition>();
		LinkedList<State> eulerianSequence = new LinkedList<State>();
		
		while(!cycleSet.isEmpty()){
			List<Transition> cycle = cycleSet.get(i);
			
			if(eulerianSequence.isEmpty()) {
				eulerianSequence.add(cycleSet.get(0).get(0).getSource());
				//eulerianTestSuite.add(cycleSet.get(0).get(0));
				
				for(Transition t: cycle) {
					eulerianSequence.add(t.getDestination());
					//eulerianTestSuite.add(t);
				}
				cycleSet.remove(0);
			}
			else {
				boolean cont = true;
				for(int x = 0; x < cycle.size(); x++) {
					
					for(int id = 1; id < eulerianSequence.size(); id++) {
						if(cycle.get(x).getSource().equals(eulerianSequence.get(id))) {
							List<State> statesCycle = new LinkedList<State>();
							List<Transition> transitionsCycle = new LinkedList<Transition>();
							
							for(Transition t: cycle) {
								statesCycle.add(t.getDestination());
								transitionsCycle.add(t);
							}
							eulerianSequence.addAll(id+1, statesCycle);
							//eulerianTestSuite.addAll(id+1,transitionsCycle);
							
							cycleSet.remove(0);
							i = i - 1;
							cont = false;
							break;
						}
					}
					if(!cont) break;
				}
				i=i+1;
			}
			
			if(i >= cycleSet.size()) i = 0;
		}
		
		eulerianSequence.removeLast();
		//eulerianTestSuite.removeLast();
		return eulerianSequence;
	}
	
	private LinkedList<State> generateEulerianCycle(State initialState){
		List<LinkedList<Transition>> cycleSet = new LinkedList<LinkedList<Transition>>();
		
		//First is check if is possible get the eulerian cycle only by initial state
		while(!checkAllTransitionsWasVisited(initialState)) {
			cycleSet.add(createCycle(initialState, initialState));
		}
		
		//If have states with transitions not visited yet, so another states of graph are checked
		Iterator<State> stateIterator = graph.getIteratorStateValue();
		while(stateIterator.hasNext()) {
			State state = stateIterator.next();
			while(!checkAllTransitionsWasVisited(state)) {
				cycleSet.add(createCycle(state, state));
			}
		}
		
		return generateEulerianCycleSequence(cycleSet);
	}
	
	public List<String> eulerianCycle(){
		List<LinkedList<State>> eulerianCycleByEachInitialState = new LinkedList<LinkedList<State>>();
		Iterator<State> initalStateIterator = returnInicialStates(graph.clone()).iterator();
		
		while(initalStateIterator.hasNext()) {
			setFalseTransitionsStates();
			State initialState = initalStateIterator.next();
			eulerianCycleByEachInitialState.add(generateEulerianCycle(initialState));
		}
		
		return eulerianTestSuiteAllTranspairs(eulerianCycleByEachInitialState);
	}
	
	private LinkedList<Transition> generateEulerianCycleTestSuite(List<LinkedList<Transition>> cycleSet){
		int i = 0;
		LinkedList<Transition> eulerianTestSuite = new LinkedList<Transition>();
		LinkedList<State> eulerianSequence = new LinkedList<State>();
		
		while(!cycleSet.isEmpty()){
			List<Transition> cycle = cycleSet.get(i);
			
			if(eulerianSequence.isEmpty()) {
				eulerianSequence.add(cycleSet.get(0).get(0).getSource());
				eulerianTestSuite.add(cycleSet.get(0).get(0));
				
				for(Transition t: cycle) {
					eulerianSequence.add(t.getDestination());
					eulerianTestSuite.add(t);
				}
				cycleSet.remove(0);
			}
			else {
				boolean cont = true;
				for(int x = 0; x < cycle.size(); x++) {
					
					for(int id = 1; id < eulerianSequence.size(); id++) {
						if(cycle.get(x).getSource().equals(eulerianSequence.get(id))) {
							List<State> statesCycle = new LinkedList<State>();
							List<Transition> transitionsCycle = new LinkedList<Transition>();
							
							for(Transition t: cycle) {
								statesCycle.add(t.getDestination());
								transitionsCycle.add(t);
							}
							eulerianSequence.addAll(id+1, statesCycle);
							eulerianTestSuite.addAll(id+1,transitionsCycle);
							
							cycleSet.remove(0);
							i = i - 1;
							cont = false;
							break;
						}
					}
					if(!cont) break;
				}
				i=i+1;
			}
			
			if(i >= cycleSet.size()) i = 0;
		}
		
		eulerianSequence.removeLast();
		eulerianTestSuite.removeLast();
		return eulerianTestSuite;
	}
	
	private LinkedList<Transition> generateEulerianCycleTestSuite(State initialState){
		List<LinkedList<Transition>> cycleSet = new LinkedList<LinkedList<Transition>>();
		
		//First is check if is possible get the eulerian cycle only by initial state
		while(!checkAllTransitionsWasVisited(initialState)) {
			cycleSet.add(createCycle(initialState, initialState));
		}
		
		//If have states with transitions not visited yet, so another states of graph are checked
		Iterator<State> stateIterator = graph.getIteratorStateValue();
		while(stateIterator.hasNext()) {
			State state = stateIterator.next();
			while(!checkAllTransitionsWasVisited(state)) {
				cycleSet.add(createCycle(state, state));
			}
		}
		return generateEulerianCycleTestSuite(cycleSet);
	}
	
	public List<String> eulerianCycleTestSuite(){
		List<LinkedList<Transition>> eulerianCycleByEachInitialState = new LinkedList<LinkedList<Transition>>();
		Iterator<State> initalStateIterator = returnInicialStates(graph.clone()).iterator();
		
		while(initalStateIterator.hasNext()) {
			setFalseTransitionsStates();
			State initialState = initalStateIterator.next();
			eulerianCycleByEachInitialState.add(generateEulerianCycleTestSuite(initialState));
		}
		
		return eulerianTestSuiteAllTrans(eulerianCycleByEachInitialState);
	}
}