package SwitchCover.method;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;

public class FirstSearch {

	private List<String> depthLine = new LinkedList<String>();
	private List<String> breadthLine = new LinkedList<String>();
	
	public FirstSearch(){
		
	}
	
	public List<List<String>> depthFirst(State state, Graph graph){
		depthLine.clear();
		
		List<List<String>> listSequence = new LinkedList<List<String>>();
		List<String> stack = new LinkedList<String>();
		stack.add(state.getName());
		
		while(!stack.isEmpty()){
			depthLine.clear();
			List<String> sequence = depthFirstSearch(graph.getState(stack.get(stack.size()-1)));
			
			if(!sequence.isEmpty()){
				stack.addAll(sequence);
				List<String> ts = new LinkedList<String>();
				ts.addAll(stack);
				listSequence.add(ts);
				//System.out.println(">"+listSequence.toString());
			}
			//System.out.println(">MASOQ: "+sequence.toString());
			stack.remove(stack.size()-1);
			//System.out.println(">"+listSequence.toString());
		}
		graph.refresh(); //não estava antes
		return listSequence;
	}
	
	//BUSCA EM PROFUNDIDADE | DEPTH FIRST SEARCH
		//ONLY PRINT
		private List<String> depthFirstSearch(State state){
			if(!state.getVisited()) state.setVisited(true);
			//System.out.println("\n\t->STATE: "+state.getName());
			for(Transition t: state.getTransitions()){
				//System.out.print("\n\t\t->TRANSITION t.INPUT: "+t.getInput() +" | t.SOURCE: "+ t.getSource().getName()+" = ");
				if(!t.getVisited()){
					//System.out.print("ENTROU! | t.GETSOURCE.GETNAME.SUBSTR: "+t.getSource().getName().substring(0, 1) +" = STATE: "+t.getDestination().getName().substring(0, 1)+"\n");
					t.setVisited(true);				
					depthLine.add(t.getDestination().getName());
					depthFirstSearch(t.getDestination());
					break;
					//if(!depthLine.get(depthLine.size()-1).equals("-"))depthLine.add("-");
					//System.out.println("\n--->>"+t.getDestination().getName()+" / "+t.getInput()+" / "+t.getSource().getName());
					//break; //AQUI!
				}
			}
			return depthLine;
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
				//se N�O foi visitado, pego os filhos
				if(!state.getVisited()){
					state.setVisited(true);
					if(state.getTransitions().size() == 1) listSequence.get(idList).add(state.getTransitions().get(0).getDestination().getName());
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
		graph.refresh(); //não estava antes
		return listSequence;
	}
	
	//BUSCA EM LARGURA
	//ONLY PRINT
	public List<String> breadthFirstSearch(State state){
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
	}
	
	
	
	public List<List<String>> TESTE(State state, State destination, Graph graph){
		breadthLine.clear();
			
		List<List<String>> listSequence = new LinkedList<List<String>>();
		List<String> list = new LinkedList<String>();
		list.add(state.getName());
		listSequence.add(list);
		
		for(int idList = 0; idList < listSequence.size(); idList++){
			for(int idElement = 0; idElement < listSequence.get(idList).size(); idElement++){
				state = graph.getState(listSequence.get(idList).get(idElement));
				//se N�O foi visitado, pego os filhos
				if(!state.getVisited()){
					state.setVisited(true);
					if(state.getTransitions().size() == 1) listSequence.get(idList).add(state.getTransitions().get(0).getDestination().getName());
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
		graph.refresh(); //não estava antes
		return listSequence;
	}
}