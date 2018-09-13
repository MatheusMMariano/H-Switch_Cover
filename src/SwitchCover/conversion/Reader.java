package SwitchCover.conversion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public Graph openTXT(String TXTdir){
		Graph graph = new Graph();
		boolean check = true;
		boolean input = false;
		boolean output = false;
		
		//	 br = convert char to string<-convert bytes to char<-catch in bytes the txt file
		this.br = new BufferedReader(new InputStreamReader(Reader.class.getResourceAsStream(TXTdir)));

		for(String line: readDataFile()){
			//check if line is empty
			if(!line.isEmpty()){
				//catch the state in a iterator
				Iterator<State> iterator = graph.getIteratorStateValue();
				
				//if have a state, check if the source and destination states exist in iterator/graph
				//if it exist, insert the variables 'input' and 'output' as true
				while(iterator.hasNext()){
					State state = iterator.next();
					if(state.getName().equals(line.substring(0, line.indexOf("-")-1))) input = true;
					if(state.getName().equals(line.substring(line.indexOf("-> ")+3, line.length()))) output = true;
				}
				
				//check if state is the first one; if yes, so it is a initial state
				//this condition is check only once
				if(check){
					//maybe I defines the map of initial state like 'initial' instead of state name in future releases
					graph.setStateMap(line.substring(0, line.indexOf("-")-1), new State(line.substring(0, line.indexOf("-")-1), "inicial"));
					check = false;
				}
				//check if the state exist in graph; if exist, don't input it in graph
				else if(!input) {
					graph.setStateMap(line.substring(0, line.indexOf("-")-1), new State(line.substring(0, line.indexOf("-")-1), "normal"));
				}
				if(!output && !line.substring(line.indexOf("-> ")+3, line.length()).equals(line.substring(0, line.indexOf("-")-1))){
					graph.setStateMap(line.substring(line.indexOf("-> ")+3, line.length()), new State(line.substring(line.indexOf("-> ")+3, line.length()), "normal"));
				}
				
				//input the transition in inicial state
				Transition t = new Transition();
				t.setName(line.substring(line.indexOf("-- ")+3, line.indexOf("-> ")-1));
				t.setSource(graph.getState(line.substring(0, line.indexOf("-")-1)));
				t.setDestination(graph.getState(line.substring(line.indexOf("-> ")+3, line.length())));

				t.setInput(line.substring(line.indexOf("-- ")+3, line.indexOf("-- ")+4));
				//t.setInput(line.substring(0, line.indexOf("-")-1)+">"+line.substring(line.indexOf("-- ")+3, line.indexOf("-- ")+4));
				
				t.setOutput(line.substring(line.indexOf("/ ")+2, line.indexOf("/ ")+3));
				//t.setOutput(line.substring(line.indexOf("-> ")+3, line.length()));
				
				t.setVisited(false);
				graph.getState(line.substring(0, line.indexOf("-")-1)).setTransition(t);
			}
			input = false;
			output = false;
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