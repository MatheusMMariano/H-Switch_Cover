package SwitchCover.method;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;

public class FirstSearch {

	private List<String> depthLine = new LinkedList<String>();
	private List<Transition> depthFirstTestSuite = new LinkedList<Transition>();
	
	private List<String> breadthLine = new LinkedList<String>();
	
	public FirstSearch(){
		
	}
	
	public List<List<String>> depthFirst(State state, Graph graph){
		depthLine.clear();
		
		//List<List<State>> listSequence = new LinkedList<List<State>>();
		List<List<String>> testSuite = new LinkedList<List<String>>();
		
		//List<State> stack = new LinkedList<State>();
		List<String> testSequence = new LinkedList<String>();
		
		//stack.add(state);
		testSequence.add(state.getName());
		
		while(!testSequence.isEmpty()){
			depthLine.clear();
			List<String> sequence = depthFirstSearch(graph.getState(testSequence.get(testSequence.size()-1)));
			
			if(!sequence.isEmpty()){
				//for(State s: sequence) testSequence.add(s.getName());
				testSequence.addAll(sequence);
				//stack.addAll(sequence);
				
				//List<State> ts = new LinkedList<State>();
				List<String> ts2 = new LinkedList<String>();
				
				ts2.addAll(testSequence);
				//ts.addAll(stack);
				
				//listSequence.add(ts);
				testSuite.add(ts2);
			}
			//stack.remove(stack.size()-1);
			testSequence.remove(testSequence.size()-1);
			//System.out.println();
		}
		graph.refresh(); //não estava antes
		//System.out.println(testSuite);
		return testSuite;
		//return listSequence;
	}
	
	//String.valueOf(t.getInput().charAt(t.getInput().length()-1))
	//BUSCA EM PROFUNDIDADE | DEPTH FIRST SEARCH
	private List<String> depthFirstSearch(State state){
		if(!state.getVisited()) state.setVisited(true);
		for(Transition t: state.getTransitions()){
			if(!t.getVisited()){
				t.setVisited(true);	
				//System.out.print("->"+t.getDestination().getName());
				depthLine.add(t.getDestination().getName());
				depthFirstSearch(t.getDestination());
				break;
			}
		}
		return depthLine;
	}
	
	public List<List<Transition>> dfs(State state, Graph graph){
		depthFirstTestSuite.clear();
		
		List<List<Transition>> listSequence = new LinkedList<List<Transition>>();
		List<Transition> stack = new LinkedList<Transition>();
		state.getTransitions().get(0).setVisited(true);
		stack.add(state.getTransitions().get(0));
		
		while(!stack.isEmpty()){
			depthFirstTestSuite.clear();
			List<Transition> sequence = depthFirstTestSuite(stack.get(stack.size()-1).getDestination());

			if(!sequence.isEmpty()){
				stack.addAll(sequence);
				List<Transition> ts = new LinkedList<Transition>();
				ts.addAll(stack);
				listSequence.add(ts);
			}
			stack.remove(stack.size()-1);
		}
		graph.refresh();
		return listSequence;
	}
	
	private List<Transition> depthFirstTestSuite(State state){
		if(!state.getVisited()) state.setVisited(true);
		for(Transition t: state.getTransitions()){
			if(!t.getVisited()){
				t.setVisited(true);
				depthFirstTestSuite.add(t);
				depthFirstTestSuite(t.getDestination());
				break;
			}
		}
		return depthFirstTestSuite;
	}
	

	public List<List<String>> breadthFirst(State state, Graph graph){
		breadthLine.clear();
		
		List<List<String>> listSequence = new LinkedList<List<String>>();
		List<String> list = new LinkedList<String>();
		list.add(state.getName());
		listSequence.add(list);
		
		for(int idList = 0; idList < listSequence.size(); idList++){
			for(int idElement = 0; idElement < listSequence.get(idList).size(); idElement++){
				state = graph.getState(listSequence.get(idList).get(idElement));
				//se NAO foi visitado, pego os filhos
				if(!state.getVisited()){
					
					state.setVisited(true);
					if(state.getTransitions().size() == 1) listSequence.get(idList).add(state.getTransitions().get(0).getDestination().getName());
					else /*if(state.getTransitions().size() > 1)*/{
						listSequence.get(idList).add(state.getTransitions().get(0).getDestination().getName());
						
						for(int i = 1; i < state.getTransitions().size(); i++){
							List<String> listTransitions = new LinkedList<String>();
							listTransitions.addAll(listSequence.get(idList));
							listTransitions.set(listTransitions.size()-1, state.getTransitions().get(i).getDestination().getName());
							listSequence.add(listTransitions);
						}
					}
				}
			}
		}
		graph.refresh(); //não estava antes
		return listSequence;
	}
	
	public List<List<Transition>> bfs(State state, Graph graph){
		breadthLine.clear();
		List<List<Transition>> testSuite = new LinkedList<List<Transition>>();
		int breadth = 1;
		//System.out.println(state.getName());
		if(!state.getVisited()) {
			state.setVisited(true);
			
			for(Transition transition: state.getTransitions()) {
				List<Transition> sequence = new LinkedList<Transition>();
				sequence.add(transition);
				testSuite.add(sequence);
			}
		}
		
		for(int tra = 0; tra < breadth; tra++) {
			int length = testSuite.size();
			
			for(int seq = 0; seq < length; seq++) {
				state = testSuite.get(seq).get(testSuite.get(seq).size()-1).getDestination();
				
				if(!state.getVisited()) {
					state.setVisited(true);
					
					List<Transition> transitionFromState = state.getTransitions();
					for(int i = 0; i < transitionFromState.size(); i++) {
						if(i == 0) testSuite.get(seq).add(transitionFromState.get(i));
						else {
							List<Transition> newSequence = new LinkedList<Transition>();
							for(int x = 0; x < testSuite.get(seq).size(); x++) {
								newSequence.add(testSuite.get(seq).get(x));
							}
							newSequence.set(newSequence.size()-1, transitionFromState.get(i));
							testSuite.add(newSequence);
						}
					}
					if(testSuite.get(seq).size() > breadth) breadth = breadth + 1;
				}
			}
		}
		
		graph.refresh();
		return testSuite;
	}
	
	public List<List<String>> testSuiteString(List<List<Transition>> testSuite, String criterion){
		List<List<String>> testSuiteCopy = new LinkedList<List<String>>();
		for(List<Transition> sequence: testSuite) {
			List<String> newSequence = new LinkedList<String>();
			for(Transition t: sequence) {
				if(criterion.equals("Alltrans")) newSequence.add(String.valueOf(t.getInput().charAt(t.getInput().length()-1)));
				else {
					newSequence.add(String.valueOf(t.getInput().substring(t.getInput().indexOf(">")+1, t.getInput().indexOf("/")).charAt(0)));
				}
			}
			testSuiteCopy.add(newSequence);
		}
		return testSuiteCopy;
	}
	
	//BUSCA EM LARGURA
	//ONLY PRINT
	/*public List<String> breadthFirstSearch(State state){
		breadthLine.clear(); // não estava antes
		
		List<Transition> list = new ArrayList<Transition>();
		state.setVisited(true);
		list.addAll(state.getTransitions());
		
		while(!list.isEmpty()){
			if(!list.get(0).getVisited()){
				//System.out.print(list.get(0).getDestination().getName().substring(2)+" | ");
				breadthLine.add(list.get(0).getName().substring(0, 1));
				//System.out.println(list.get(0).getSource().getName());
				//breadthLine.add(list.get(0).getName());
				list.get(0).setVisited(true);
				list.addAll(list.get(0).getDestination().getTransitions());
			}
			list.remove(0);
		}
		return breadthLine;
	}*/

	/*public List<List<String>> TESTE(State state, State dest, int length, Graph graph){
		graph.refresh();
		breadthLine.clear();
		//System.out.println("> "+ state.getName()+" -> "+ dest.getName());
		
		List<List<String>> listSequence = new LinkedList<List<String>>();
		List<String> list = new LinkedList<String>();
		list.add(state.getName());
		listSequence.add(list);
		
		for(int idList = 0; idList < listSequence.size(); idList++){
			for(int idElement = 0; idElement < length; idElement++){
				//System.out.println("\n> id list: "+idList+" / id element: "+ idElement+" / list sequence size: "+listSequence.size()+" / list sequence by id list: "+listSequence.get(idList).size()); //ERRO AQUI SO NO DUAL GRAPH
				
				if(idElement < listSequence.get(idList).size()) state = graph.getState(listSequence.get(idList).get(idElement));
				
				if(!state.getVisited()){
					//System.out.println("Estado atual: "+state.getName()+" / Visitado: "+state.getVisited());
					//if(state.getName().equals(dest.getName())) break;
					state.setVisited(true);

					if(state.getTransitions().size() == 1) {
						listSequence.get(idList).add(state.getTransitions().get(0).getDestination().getName());
					}
					else if(state.getTransitions().size() > 1){
						listSequence.get(idList).add(state.getTransitions().get(0).getDestination().getName());
						
						for(int i = 1; i < state.getTransitions().size(); i++){
							List<String> listTransitions = new LinkedList<String>();
							listTransitions.addAll(listSequence.get(idList));
							listTransitions.set(listTransitions.size()-1, state.getTransitions().get(i).getDestination().getName());
							listSequence.add(listTransitions);
						}
					}
				}
			}
		}
		//System.out.println("\n");
		graph.refresh(); //não estava antes
		return listSequence;
	}*/
}