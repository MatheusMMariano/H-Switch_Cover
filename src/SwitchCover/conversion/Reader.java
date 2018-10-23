package SwitchCover.conversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;


public class Reader {
	
	private BufferedReader br;
	private BufferedWriter bw;
	
	public Reader(){
		
	}
	
	private String[] readDataFile(){
		try{
			StringBuilder sb = new StringBuilder();
			String inicialState = null;
			boolean checkInicial = false;
			boolean checkIsStronglyConnected = false;
			
			while(this.br.ready()){
				String line = this.br.readLine();
				if(!line.isEmpty()){
					sb.append(line).append("!");
					if(!checkInicial){
						inicialState = sb.substring(0, sb.indexOf("-")-1);
						checkInicial = true;
					}
					if(inicialState.equals(line.substring(line.indexOf("-> ")+3, line.length()))) checkIsStronglyConnected = true;
				}
			}

			if(checkIsStronglyConnected) return sb.toString().trim().split("!");
			else return new String[0];
		}
		catch(IOException ex){
			return new String[0];
		}
	}
	
	private void close(){
		try{
			this.br.close();
		}
		catch(IOException ex){
			System.err.println("Failure to complete resource");
		}
	}
	
	/*public Graph openTXT(String TXTdir){
		Graph graph = new Graph();
		boolean check = true;
		boolean input = false;
		boolean output = false;
		
		//br = convert char to string<-convert bytes to char<-catch in bytes the txt file
		this.br = new BufferedReader(new InputStreamReader(Reader.class.getResourceAsStream(TXTdir)));

		for(String line: readDataFile()){
			System.out.println(line);
			//check if line is empty
			if(!line.isEmpty()){
				//catch the state in a iterator
				Iterator<State> iterator = graph.getIteratorStateValue();
				//if have a state, check if the source and destination states exist in iterator/graph
				//if it exist, insert the variables 'input' and 'output' as true
				while(iterator.hasNext()){
					State state = iterator.next();
					//if(state.getName().equals(line.substring(0, line.indexOf("-")-1))) input = true;
					//if(state.getName().equals(line.substring(line.indexOf("-> ")+3, line.length()))) output = true;
					if(state.getName().equals(line.substring(0, 1))) input = true;
					if(state.getName().equals(line.substring(line.length()-1, line.length()))) output = true;
				}
				
				//check if state is the first one; if yes, so it is a initial state
				//this condition is check only once
				if(check){
					//maybe I defines the map of initial state like 'initial' instead of state name in future releases
					//graph.setStateMap(line.substring(0, line.indexOf("-")-1), new State(line.substring(0, line.indexOf("-")-1), "inicial"));
					
					//graph.setStateMap(line.substring(0, 1), new State(line.substring(0, 1), "inicial"));
					String initialState = line.substring(0, line.lastIndexOf(" --"));
					graph.setStateMap(initialState, new State(initialState, "inicial"));
					check = false;
				}
				//check if the state exist in graph; if exist, don't input it in graph
				else if(!input) {
					//graph.setStateMap(line.substring(0, line.indexOf("-")-1), new State(line.substring(0, line.indexOf("-")-1), "normal"));

					//graph.setStateMap(line.substring(0, 1), new State(line.substring(0, 1), "normal"));
					String newState = line.substring(0, line.lastIndexOf(" --"));
					graph.setStateMap(newState, new State(newState, "normal"));
				}
				if(!output && !line.substring(line.indexOf("-> ")+3, line.length()).equals(line.substring(0, line.indexOf("-")-1))){
					//graph.setStateMap(line.substring(line.indexOf("-> ")+3, line.length()), new State(line.substring(line.indexOf("-> ")+3, line.length()), "normal"));
					
					//graph.setStateMap(line.substring(line.length()-1, line.length()), new State(line.substring(line.length()-1, line.length()), "normal"));
					String newState = line.substring(line.indexOf(">")+2);
					graph.setStateMap(newState, new State(newState, "normal"));
				}
				//input the transition in inicial state

//				Transition t = new Transition();
//				t.setName(line.substring(line.indexOf("-- ")+3, line.indexOf("-> ")-1));
//				t.setSource(graph.getState(line.substring(0, line.indexOf("-")-1)));
//				t.setDestination(graph.getState(line.substring(line.indexOf("-> ")+3, line.length())));
//
//				t.setInput(line.substring(line.indexOf("-- ")+3, line.indexOf("-- ")+4));
//				//t.setInput(line.substring(0, line.indexOf("-")-1)+">"+line.substring(line.indexOf("-- ")+3, line.indexOf("-- ")+4));
//				
//				t.setOutput(line.substring(line.indexOf("/ ")+2, line.indexOf("/ ")+3));
//				//t.setOutput(line.substring(line.indexOf("-> ")+3, line.length()));
//				
//				t.setVisited(false);
//				graph.getState(line.substring(0, line.indexOf("-")-1)).setTransition(t);


//				//A LINHA DE BAIXO ESTAVA ANTES. FUNCIONAVA MAS ESTAVA ERRADO!
//				//PARA ESTADOS/NOME DA TRANSICAO COM MAIS DE 1 CARACTER ELE NAO PEGA O NOME
//				Transition t = new Transition();
//				t.setName(line.substring(5, 10));
//				t.setSource(graph.getState(line.substring(0, 1)));
//				t.setDestination(graph.getState(line.substring(line.length()-1, line.length())));
//				t.setInput(line.substring(0, 1)+">"+line.substring(5, 6));
//				t.setOutput(line.substring(9, 10));
//				t.setVisited(false);
//				graph.getState(line.substring(0, 1)).setTransition(t);
				
				Transition t = new Transition();
				t.setName(line.substring(line.lastIndexOf("-- ")+3, line.lastIndexOf(" ->")));
				//System.out.println("Name: "+ line.substring(line.lastIndexOf("-- ")+3, line.lastIndexOf(" ->")));
				t.setSource(graph.getState(line.substring(0, line.lastIndexOf(" --"))));
				//System.out.println("Source: "+graph.getState(line.substring(0, line.lastIndexOf(" --"))).getName());
				t.setDestination(graph.getState(line.substring(line.indexOf(">")+2)));
				//System.out.println("->>>>"+line.substring(line.indexOf(">")+2)+"h"+line.substring(line.length()-1, line.length()));
				//System.out.println("Destination: "+graph.getState(line.substring(line.indexOf(">")+2)).getName());
				t.setInput(line.substring(0, line.lastIndexOf(" --"))+">"+line.substring(line.lastIndexOf("-- ")+3, line.lastIndexOf(" /")));
				//System.out.println("Input: "+line.substring(0, line.lastIndexOf(" --"))+">"+line.substring(line.lastIndexOf("-- ")+3, line.lastIndexOf(" /")));
				t.setOutput(line.substring(line.lastIndexOf("/ ")+2, line.lastIndexOf(" ->")));
				//System.out.println("Output: "+line.substring(line.lastIndexOf("/ ")+2, line.lastIndexOf(" ->")));
				t.setVisited(false);
				//System.out.println("\n");
				graph.getState(line.substring(0, line.lastIndexOf(" --"))).setTransition(t);
			}
			input = false;
			output = false;
		}
		close();
		return graph;	
	}*/
	
	public boolean stateIsPresentInGraph(Graph graph, String stateName) {
		HashMap<String, State> stateHashMap = graph.getStatesMap();
		if(stateHashMap.get(stateName) == null) return false;
		else return true;
	}
	
	public State addSourceState(Graph graph, String line, String typeFile) { //typeFile = [normal, inicial]
		String stateName = line.substring(0, line.lastIndexOf(" --"));
		State state = new State(stateName, typeFile);
		graph.setStateMap(stateName, state);
		
		return state;
	}
	
	public State addDestinationState(Graph graph, String line, String typeFile) { //typeFile = [normal, inicial]
		String stateName = line.substring(line.indexOf(">")+2);
		State state = new State(stateName, typeFile);
		graph.setStateMap(stateName, state);
		
		return state;
	}
	
	public void addTransition(Graph graph, State source, State destination, String line) {
		Transition transition = new Transition();

		transition.setName(line.substring(line.lastIndexOf("-- ")+3, line.lastIndexOf(" ->")));
		transition.setSource(source);
		transition.setDestination(destination);
		transition.setInput(line.substring(0, line.lastIndexOf(" --"))+">"+line.substring(line.lastIndexOf("-- ")+3, line.lastIndexOf(" /")));
		transition.setOutput(line.substring(line.lastIndexOf("/ ")+2, line.lastIndexOf(" ->")));
		transition.setVisited(false);
		transition.setCounter(0);
		
		source.setTransition(transition);
	}
	
	public Graph generateTXTGraph(String path){
		this.br = new BufferedReader(new InputStreamReader(Reader.class.getResourceAsStream(path)));
		Graph graph = new Graph();
		boolean initial = false;
		
		for(String line: readDataFile()){
			State source = null, destination = null;
			
			if(!initial) {
				initial = true;
				source = addSourceState(graph, line, "inicial");
			}
			else {
				String newSourceStateName = line.substring(0, line.lastIndexOf(" --"));
				if(!stateIsPresentInGraph(graph, newSourceStateName)) source = addSourceState(graph, line, "normal");
				else source = graph.getState(newSourceStateName);
			}
			
			String newDestinationStateName = line.substring(line.indexOf(">")+2);
			if(!stateIsPresentInGraph(graph, newDestinationStateName)) destination = addDestinationState(graph, line, "normal");
			else destination = graph.getState(newDestinationStateName);
			
			addTransition(graph, source, destination, line);
		}
		
		close();
		return graph;
	}
	
	public void insertFile(String nameFile, List<String> dataList){
		File file = new File(nameFile);
		
		try{
			file.createNewFile();
			bw = new BufferedWriter(new FileWriter(file));
			
			for(String data: dataList){
				bw.write(data);
				bw.newLine();
			}
			bw.close();
		}
		catch(IOException ioe){
			System.out.println("\nFILE ERROR: "+ioe.toString());
		}
	}
	
	public void insertFile(String nameFile, double total){
		File file = new File(nameFile);

		try{
			//if(!file.exists()) file.createNewFile();
			file.createNewFile();
			
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(String.valueOf(total));
			//bw.newLine();
			bw.close();
		}
		catch(IOException ioe){
			System.out.println("\nFILE ERROR: "+ioe.toString());
		}
	}
}