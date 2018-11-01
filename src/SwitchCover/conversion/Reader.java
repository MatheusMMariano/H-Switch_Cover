package SwitchCover.conversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
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
			file.createNewFile();
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(String.valueOf(total));
			bw.newLine();
			bw.close();
		}
		catch(IOException ioe){
			System.out.println("\nFILE ERROR: "+ioe.toString());
		}
	}
}