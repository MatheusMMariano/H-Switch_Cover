package SwitchCover.main;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import SwitchCover.chinesePostmanProblem.ChinesePostmanProblem;
import SwitchCover.conversion.Reader;
import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;
import SwitchCover.method.Balancing;
import SwitchCover.method.FirstSearch;
import SwitchCover.method.GenerateTestCase;
import SwitchCover.method.GeraCasosTeste;
import SwitchCover.method.NodeConverter;

public class Main {
	
	private Graph graph = new Graph();
	private Graph dualGraphConverted = new Graph();
	//private Graph dualGraphBalanced = new Graph();
	
	private NodeConverter node = new NodeConverter();
	private FirstSearch search = new FirstSearch();
	private Balancing balancing = new Balancing();

	public Main() {

	}
	
	public List<String> tratamentFile(List<List<String>> listSequence, String typeFile, String name){
		List<String> sequenceTest = new LinkedList<String>();
		
		//Catch sequence in list and add in a unic String
		for(int i = 0; i < listSequence.size(); i++){
			String test = "";
			if(typeFile.equals("xml")){
				for(int in = 0; in < listSequence.get(i).size(); in++) {
					test = test + listSequence.get(i).get(in)+",";
				}
			}
			else {
				if(name.equals("Alltrans")) {
					for(int in = 0; in < listSequence.get(i).size(); in++) {
						test = test + (listSequence.get(i).get(in) +",");
					}
				}
				else {
					for(int in = 0; in < listSequence.get(i).size(); in++) {
						//test = test + (listSequence.get(i).get(in) +",");
						test = test + (listSequence.get(i).get(in).substring(listSequence.get(i).get(in).indexOf(">")+1, listSequence.get(i).get(in).indexOf("/"))+",");
						//test = test + (listSequence.get(i).get(in).substring(listSequence.get(i).get(in).lastIndexOf('>')+1, listSequence.get(i).get(in).lastIndexOf('/'))+",");
					}
				}
			}
			
			//for(int in = 0; in < listSequence.get(i).size(); in++) {
			//	test = test + (listSequence.get(i).get(in)+",");
			//}
			sequenceTest.add(test.substring(0, test.length()-1));
		}
		
		//Check if sequence is prefix; If is, remove it
		for(int i = 0; i < sequenceTest.size(); i++){
			for(int in = 1; in < sequenceTest.size(); in++){
				if(i != in) {
					if (i < sequenceTest.size() && sequenceTest.get(i).startsWith(sequenceTest.get(in))) {
						sequenceTest.remove(in);
					}
				}
			}
		}

		return sequenceTest;
	}
	
	public void firstSearchTestCase(String path, String typeFile, Reader reader, int i, String name, Graph g){
		Iterator<State> firstSearch = g.getIteratorStateValue();
		List<List<String>> firstSearchListSequence = new LinkedList<List<String>>();
		
		while(firstSearch.hasNext()){
			State state = firstSearch.next();
			if(state.getTypeState().equals("inicial")){
				if(i == 1 || i == 5) firstSearchListSequence.addAll(search.breadthFirst(state, g));
				else if(i == 2 || i == 6) firstSearchListSequence.addAll(search.depthFirst(state, g));
			}
		}
		
		if(i == 1 || i == 5){
			if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsbreadth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));
			else{
				reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsbreadth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));
				//reader.insertFile(path+"/tsbreadth.txt", tratamentFile(firstSearchListSequence, typeFile));
			}
		}
		else if(i == 2 || i == 6){
			if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsdepth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));
			else {
				reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsdepth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));				
			}
		}
	}
	
	public void eulerianCycleTestCase(String path, String typeFile, Reader reader, String name, Graph graphBalanced, boolean typeGraph){
		GenerateTestCase testCaseBreadthFirstSearch = new GenerateTestCase(graphBalanced);
		List<LinkedList<State>> testSequenceBreadth = testCaseBreadthFirstSearch.eulerianCycle();
		//List<List<State>> testSequenceBreadth = testCaseBreadthFirstSearch.initial();
		//GeraCasosTeste testCaseBreadthFirstSearch = new GeraCasosTeste(graphBalanced);
		//List<List<State>> testSequenceBreadth = testCaseBreadthFirstSearch.inicio();
		
		List<String> testListSequence = new LinkedList<String>();
		
		for(List<State> listState: testSequenceBreadth){
			if(!listState.isEmpty()){
				String testList = "";
				for(State state: listState) {
					if(typeGraph){
						testList = testList + (state.getName()+",");
					}
					else{
						
						String input = state.getName().substring(state.getName().indexOf(">")+1, state.getName().indexOf("/"));
						testList = testList + input +",";
						//testList = testList + state.getName() +",";
					}
				}
				
				//testList = testList + " = " + testList.length();
				//test.substring(0, test.length()-1)
				testListSequence.add(testList.substring(0, testList.length()-1));
			}
		}
		/*if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/"+name+".txt", testListSequence);
		else reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/"+name+".txt", testListSequence);*/
		
		if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.lastIndexOf("/"))+"/"+name+".txt", testListSequence);
		else reader.insertFile("src/"+path.substring(0, path.lastIndexOf("/"))+"/"+name+".txt", testListSequence);		
	}

	private Graph preProcess(Graph graph) {
		Iterator<State> stateList = graph.getIteratorStateValue();
		List<State> _stateList = new LinkedList<State>();
		
		while(stateList.hasNext()) {
			State state = stateList.next();
			List<Transition> transitionList = state.getTransitions();
			
			for(int i = 0; i < transitionList.size(); i++) {
				for(int j = i+1; j < transitionList.size(); j++) {
					if(transitionList.get(i).getDestination().getName().equals(transitionList.get(j).getDestination().getName())) {
						
						Transition t = transitionList.get(j);
						//State _state = new State(state.getName()+"."+t.getInput(), "normal", true, state);
						//System.out.println(t.getInput());
						State _state = new State(state.getName()+"."+j, "normal", true, state);
						Transition _transition = new Transition("0", t.getOutput(), t.getName(), t.getDestination(), _state, false, 0);
						_state.setTransition(_transition);
						t.setDestination(_state);
						_stateList.add(_state);
					}
				}
			}
		}
		
		for(int i = 0; i < _stateList.size(); i++) {
			graph.setStateMap(_stateList.get(i).getName(), _stateList.get(i));
		}
		return graph;
	}
	
	private Graph posProcess(Graph graph) {
		Iterator<State> stateList = graph.getIteratorStateValue();
		
		while(stateList.hasNext()) {
			State state = stateList.next();
			if(state.getPre_process()) {
				State source = state.getPre_processStateSource();
				State destin = state.getTransitions().get(0).getDestination();
				
				for(Transition transition: source.getTransitions()) {
					if(transition.getDestination().getName().equals(state.getName())) {
						transition.setDestination(destin);
						break;
					}
				}
				stateList.remove();
			}
		}
		
		return graph;
	}
	
	private void count(Graph graph) {
		int count = 0;
		Iterator<State> list = graph.getIteratorStateValue();
		while(list.hasNext()) {
			State state = list.next();
			count = count + state.getTransitions().size();
		}
		System.out.println(count);
	}
	
	private void createGraphTestCase(String path, String fileName, String typeFile, int i, int criterion) throws ParserConfigurationException, SAXException, IOException{
		try{
			//Step 01: Create graph by 1) XML or 2) a TXT file
			Reader reader = new Reader();
			
			if(typeFile.equals("xml")) graph = graph.openXML(path);
			else graph = reader.generateTXTGraph(path);
			
			if(graph.isConexo()) {
				if(graph.getIteratorState().hasNext()){
					if(i == 1 || i == 2 || i == 5 || i == 6){
						//Step 02.A: Create test case with dual graph in Depth or Breadth-First Search
						if(criterion == 0) {
							graph.inicialState();
							firstSearchTestCase(path, typeFile, reader, i, "Alltrans", graph.clone());
						}
						else{
							//Step 02.B: Create new graph with new transitions (dual graph / transition-pair graph)
							dualGraphConverted = node.transitionsConvertedNode(graph, typeFile);
							dualGraphConverted.inicialState();
							firstSearchTestCase(path, typeFile, reader, i, "Alltranspair", dualGraphConverted.clone());
						}
					}
					else if(i == 3 || i == 7){ //Eulerian Cycle
						//Step 04: Balancing graph with a H-Switch Cover heuristic and generate test cases with Hierholzer
						//if is true, so is a normal graph; else is a dual graph
						if(criterion == 0) {
							graph.inicialState();
							eulerianCycleTestCase(path, typeFile, reader, "tseulerAlltrans", balancing.inicio(graph.clone()), true);
						}
						else{
							dualGraphConverted = node.transitionsConvertedNode(graph, typeFile);
							dualGraphConverted.inicialState();
							//Graph g = balancing.inicio(dualGraphConverted.clone());
							//System.out.println(g.showResult());
							eulerianCycleTestCase(path, typeFile, reader, "tseulerAlltranspair", balancing.inicio(dualGraphConverted.clone()), false);
						}
					}
					else if(i == 4 || i == 8){ //Chinese Postman Problem
						//Step 06: Balancing graph with Chinese Postman Problem (CPP) and generate test cases with Hierholzer
						ChinesePostmanProblem cpp = new ChinesePostmanProblem();
						
						if(criterion == 0) {
							graph.inicialState();
							eulerianCycleTestCase(path, typeFile, reader, "tspccAlltrans", posProcess(cpp.testCasePCC(preProcess(graph.clone()))), true);
						}
						else {
							dualGraphConverted = node.transitionsConvertedNode(graph, typeFile);
							dualGraphConverted.inicialState();
							//Graph g = posProcess(cpp.testCasePCC(preProcess(dualGraphConverted.clone())));
							//System.out.println(g.showResult());
							eulerianCycleTestCase(path, typeFile, reader, "tspccAlltranspair", posProcess(cpp.testCasePCC(preProcess(dualGraphConverted.clone()))), false);
						}
					}
				}
			}
			else {
				System.out.println("This graph isn't conexo! -> "+path);
			}
		}
		catch(NullPointerException np){
			System.out.println("ERROR! File is empty.\n"+np);
		}
		catch(IOException io){
			System.out.println("ERROR! Problem in reader XML or TXT file.\n"+io);
		}
		catch(SAXException se){
			System.out.println("ERROR! Problem in reader XML file in SAXHandler.\n"+se);
		}
		
	}

	private void source(int i, int criterion, String typeFileName) throws ParserConfigurationException, SAXException, IOException {
		File DIR = new File("./src/SwitchCover/");
		String typeFile = "";
		
		if(i >= 1 && i <= 4) typeFile = "xml";
		else typeFile = "file";
		
		for (File pathXMLFile : DIR.listFiles()) { //[XML, TXT]...
			if (pathXMLFile.getName().equals(typeFile)) { // XML or TXT
				for(File pathMEF : pathXMLFile.listFiles()){ //APEX or SWPDC | 4-4-4, 8-8-8...
					//System.out.println(pathMEF.getName());
					if(!pathMEF.getName().equals(".DS_Store")){
						//if(pathMEF.getName().equals("swpdc")) {
						for(File pathNumber : pathMEF.listFiles()){ //1, 2, 3...
							if(!pathNumber.getName().equals(".DS_Store")){
								//if(pathNumber.getName().equals("11")) {
								//System.out.println(pathNumber);
								for(File file : pathNumber.listFiles()){ //fsm1, fsm2...
									if(!file.getName().equals(".DS_Store")){
										if(!file.getName().contains("tsdepth") && 
										   !file.getName().contains("tsbreadth") && 
										   !file.getName().contains("tseuler") &&
										   !file.getName().contains("tspcc") && 
										   !file.getName().contains("timeDepth") && 
										   !file.getName().contains("timeBreadth") && 
										   !file.getName().contains("timeEuler") &&
										   !file.getName().contains("timeCPP")){
											String path = "";
											
											if(typeFile.equals("xml")) path = "./src/SwitchCover/"+pathXMLFile.getName()+"/"+pathMEF.getName()+"/"+pathNumber.getName()+"/"+file.getName();
											else path = "/SwitchCover/"+pathXMLFile.getName()+"/"+pathMEF.getName()+"/"+pathNumber.getName()+"/"+file.getName();
											System.out.println(path);
											Main main = new Main();
											main.createGraphTestCase(path, file.getName(), typeFile, i, criterion);
											
											/*long duracaoEmMilissegundos = 0;
											for(int ti = 0; ti < 100; ti++) {
												Instant start = Instant.now();
												main.createGraphTestCase(path, file.getName(), typeFile, i, criterion);
												Instant stop = Instant.now();
												
												Duration duration = Duration.between(start, stop);
												duracaoEmMilissegundos = duracaoEmMilissegundos + duration.toMillis();
											}
												
											/*Reader read = new Reader();
											//reader.insertFile(+"/"+name+".txt", testListSequence);
											
											if(typeFile.equals("xml")) {
												System.out.println(path.substring(0, path.lastIndexOf("/"))+"/time"+typeFileName+".txt"+" TIME (miliseconds): "+ (duracaoEmMilissegundos/100));
												read.insertFile(path.substring(0, path.lastIndexOf("/"))+"/time"+typeFileName+".txt", duracaoEmMilissegundos);
											}
											else {
												System.out.println("src/"+path.substring(0, path.lastIndexOf("/"))+"/time"+typeFileName+".txt"+" TIME (miliseconds): "+ (duracaoEmMilissegundos/100));
												read.insertFile("src/"+path.substring(0, path.lastIndexOf("/"))+"/time"+typeFileName+".txt", duracaoEmMilissegundos);
											}*/
										}
									}
								}
								//}
							}
						}
					}
				}				
			//}
			}
		}
	}
	
	private int inicialize() {
		while (true) {
			System.out.print(
					"\nHi and welcome the H-Switch Cover program. For more informations, please take a look the README.txt file in source of project."
							+ "\nWould do you like to generate test cases with: \n"
							+ "\n1- [XML] Breadth First Search    | 5- [TXT] Breadth First Search"
							+ "\n2- [XML] Depth First Search      | 6- [TXT] Depth First Search"
							+ "\n3- [XML] Eulerian Cycle          | 7- [TXT] Eulerian Cycle"
							+ "\n4- [XML] Chinese Postman Problem | 8- [TXT] Chinese Postman Problem"
							+ "\n0- [Exit] ");

			try {
				Scanner input = new Scanner(System.in);
				int r = 0;
				
				try{
					r = Integer.valueOf(input.next());
				}
				finally{
					input.close();
				}

				if (r < 0 || r > 8) System.out.println("\n\n[Only number between 0 and 8!]");
				else if (r == 0) System.exit(0);
				else return r;
			}
			catch (Exception e) {
				System.out.println("\n\n[Only number between 0 and 8!]");
			}
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Main main = new Main();
		//main.source(main.inicialize());
		//main.source(3, 1, "EulerianAlltranspair");
		//main.source(4, 1, "CPPAlltranspair"); // 0: all transitions, 1: all transitions-pairs
		
		for(int x = 0 ; x < 2; x++) { // 0: all transitions, 1: all transitions-pairs
			String criterion = "";
			
			if(x == 0) criterion = "Alltrans";
			else criterion = "Alltranspair";
			
			for(int i = 1; i <= 8; i++) {
				String typeFileName = "";
				
				if(i == 1 || i == 5) typeFileName = "Breadth"+criterion;
				else if(i == 2 || i == 6) typeFileName = "Depth"+criterion;
				else if(i == 3 || i == 7) typeFileName = "Eulerian"+criterion;
				if(i == 4 || i == 8) typeFileName = "CPP"+criterion;
					
				main.source(i, x, typeFileName);
			}
		}
	}

}