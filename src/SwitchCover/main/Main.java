package SwitchCover.main;

import java.io.File;
import java.io.IOException;
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
import SwitchCover.method.Balancing;
import SwitchCover.method.FirstSearch;
import SwitchCover.method.GenerateTestCase;
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
			/*if(typeFile.equals("xml")){
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
						test = test + (listSequence.get(i).get(in) +",");
						//test = test + (listSequence.get(i).get(in).substring(listSequence.get(i).get(in).lastIndexOf('>')+1, listSequence.get(i).get(in).lastIndexOf('/'))+",");
					}
				}
			}*/
			
			for(int in = 0; in < listSequence.get(i).size(); in++) {
				test = test + (listSequence.get(i).get(in) +",");
			}
			sequenceTest.add(test.substring(0, test.length()-1));
		}
		
		//Check if sequence is a Prefix; If is, remove it
		for(int i = 0; i < sequenceTest.size(); i++){
			for(int in = 1; in < sequenceTest.size(); in++){
				if(i != in && sequenceTest.get(i).startsWith(sequenceTest.get(in))) sequenceTest.remove(in);
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
				if(i == 1 || i == 4) firstSearchListSequence.addAll(search.breadthFirst(state, g));
				else if(i == 2 || i == 5) firstSearchListSequence.addAll(search.depthFirst(state, g));
			}
		}
		
		if(i == 1 || i == 4){
			if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsbreadth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));
			else{
				reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsbreadth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));
				//reader.insertFile(path+"/tsbreadth.txt", tratamentFile(firstSearchListSequence, typeFile));
			}
		}
		else if(i == 2 || i == 5){
			if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsdepth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));
			else {
				reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsdepth"+name+".txt", tratamentFile(firstSearchListSequence, typeFile, name));				
			}
		}
	}
	
	public void eulerianCycleTestCase(String path, String typeFile, Reader reader, String name, Graph g){
		GenerateTestCase testCaseEuler = new GenerateTestCase(g);
		List<List<State>> testSequenceBreadth = testCaseEuler.inicio();
		List<String> testListSequence = new LinkedList<String>();
		
		for(List<State> listState: testSequenceBreadth){
			if(!listState.isEmpty()){
				String testList = "";
				
				/*if(typeFile.equals("xml")) {
					for(State state: listState) {
						testList = testList + (state.getName() +",");
					}
				}
				else {
					for(State state: listState) {
						System.out.println(state.getName());
						//testList = testList + (state.getName().substring(2, 3)+",");
						testList = testList + (state.getName()+",");
					}
				}*/
				
				for(State state: listState) {
					testList = testList + state.getName() +",";
				}
				
				testListSequence.add(testList.substring(0, testList.length()-1));
				//break;
			}
		}
		
		if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/"+name+".txt", testListSequence);
		else reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/"+name+".txt", testListSequence);
	}
	
	private void createGraphTestCase(String path, String fileName, String typeFile, int i) throws ParserConfigurationException, SAXException, IOException{
		try{
			//Step 01: Create graph by 1) XML or 2) a TXT file
			Reader reader = new Reader();
			if(typeFile.equals("xml")) graph = graph.openXML(path);
			else graph = reader.openTXT(path);
			
			if(graph.getIteratorState().hasNext()){
				//Step 02: Create new graph with new transitions (dual graph / transition-pair graph)
				dualGraphConverted = node.transitionsConvertedNode(graph, typeFile);
				dualGraphConverted.inicialState();
				
				if(i == 1 || i == 2 || i == 4 || i == 5){
					//Step 03: Create test case with dual graph in Depth or Breadth-First Search
					firstSearchTestCase(path, typeFile, reader, i, "Alltrans", graph.clone());
					firstSearchTestCase(path, typeFile, reader, i, "Alltranspair", dualGraphConverted.clone());
				}
				else if(i == 3 || i == 6){ //Eulerian Cycle
					//Step 04: Balancing graph with Floyd-Warshal and generate test cases with Hierholzer
					eulerianCycleTestCase(path, typeFile, reader, "tseulerAlltrans", balancing.inicio(graph.clone()));
					eulerianCycleTestCase(path, typeFile, reader, "tseulerAlltranspair", balancing.inicio(dualGraphConverted.clone()));
				}
				else{ //Chinese Postman Problem
					//Step 06: Balancing graph with Chinese Postman Problem (CPP) and generate test cases with Hierrholzer
					ChinesePostmanProblem cpp = new ChinesePostmanProblem();
					eulerianCycleTestCase(path, typeFile, reader, "tspccAlltrans", cpp.testCasePCC(graph.clone()));
					eulerianCycleTestCase(path, typeFile, reader, "tspccAlltranspair", cpp.testCasePCC(dualGraphConverted.clone()));
					System.out.println("\n");
				}
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

	private void source(int i) throws ParserConfigurationException, SAXException, IOException {
		File DIR = new File("./src/SwitchCover/");
		String typeFile = "";
		
		if(i >= 1 && i <= 3) typeFile = "xml";
		else typeFile = "file";
			
		for (File pathXMLFile : DIR.listFiles()) { //[XML, TXT]...
			if (pathXMLFile.getName().equals(typeFile)) { // XML or TXT
				for(File pathMEF : pathXMLFile.listFiles()){ //APEX or SWPDC | 4-4-4, 8-8-8...
					//System.out.println(pathMEF.getName());
					if(!pathMEF.getName().equals(".DS_Store")){
						for(File pathNumber : pathMEF.listFiles()){ //1, 2, 3...
							if(!pathNumber.getName().equals(".DS_Store")){
								//if(pathNumber.getName().equals("105")) {
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
										   !file.getName().contains("timePcc")){
											String path = "";
											
											if(typeFile.equals("xml")) path = "./src/SwitchCover/"+pathXMLFile.getName()+"/"+pathMEF.getName()+"/"+pathNumber.getName()+"/"+file.getName();
											else path = "/SwitchCover/"+pathXMLFile.getName()+"/"+pathMEF.getName()+"/"+pathNumber.getName()+"/"+file.getName();

											Main main = new Main();
											main.createGraphTestCase(path, file.getName(), typeFile, i);
										}
									}
								//}
								}
							}
						}
					}
				}				
			}
		}
	}
	
	private int inicialize() {
		while (true) {
			System.out.println(
					"\nHi and welcome the H-Switch Cover program. For more informations, please take a look the README.txt file in source of project."
							+ "\nWould do you like to generate test cases with: \n"
							+ "\n1- [XML] Breadth First Search | 4- [TXT] Breadth First Search"
							+ "\n2- [XML] Depth First Search   | 5- [TXT] Depth First Search"
							+ "\n3- [XML] Eulerian Cycle       | 6- [TXT] Eulerian Cycle"
							+ "\n7- Chinese Postman Problem    | 8- Exit");

			try {
				Scanner input = new Scanner(System.in);
				int r = 0;
				
				try{
					r = Integer.valueOf(input.next());
				}
				finally{
					input.close();
				}

				if (r < 1 || r > 8) System.out.println("\n\n[Only number between 1 and 8!]");
				else if (r == 8) System.exit(0);
				else return r;
			}
			catch (Exception e) {
				System.out.println("\n\n[Only number between 1 and 8!]");
			}
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Main main = new Main();
		main.source(main.inicialize());
	}

}