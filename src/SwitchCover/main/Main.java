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
	private Graph dualGraphBalanced = new Graph();
	
	private NodeConverter node = new NodeConverter();
	private FirstSearch search = new FirstSearch();
	private Balancing balancing = new Balancing();

	public Main() {

	}
	
	public List<String> tratamentFile(List<List<String>> listSequence, String typeFile){
		List<String> sequenceTest = new LinkedList<String>();
		
		//Catch sequence in list and add in a unic String
		for(int i = 0; i < listSequence.size(); i++){
			String test = "";
			if(typeFile.equals("xml")){
				for(int in = 0; in < listSequence.get(i).size(); in++) test = test + listSequence.get(i).get(in)+",";
			}
			else for(int in = 0; in < listSequence.get(i).size(); in++) test = test + (listSequence.get(i).get(in).substring(listSequence.get(i).get(in).lastIndexOf('>')+1, listSequence.get(i).get(in).lastIndexOf('/'))+",");
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
	
	public void firstSearchTestCase(String path, String typeFile, Reader reader, int i){
		Iterator<State> firstSearch = dualGraphConverted.getIteratorStateValue();
		List<List<String>> firstSearchListSequence = new LinkedList<List<String>>();
		
		while(firstSearch.hasNext()){
			State state = firstSearch.next();
			if(state.getTypeState().equals("inicial")){			
				if(i == 1 || i == 4) firstSearchListSequence.addAll(search.breadthFirst(state, dualGraphConverted));
				else if(i == 2 || i == 5) firstSearchListSequence.addAll(search.depthFirst(state, dualGraphConverted));
				break;
			}
		}
		
		if(i == 1 || i == 4){
			if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsbreadth.txt", tratamentFile(firstSearchListSequence, typeFile));
			else reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsbreadth.txt", tratamentFile(firstSearchListSequence, typeFile));
		}
		else if(i == 2 || i == 5){
			if(typeFile.equals("xml")) reader.insertFile(       path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsdepth.txt", tratamentFile(firstSearchListSequence, typeFile));
			else reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tsdepth.txt", tratamentFile(firstSearchListSequence, typeFile));
		}
	}
	
	public void eulerianCycleTestCase(String path, String typeFile, Reader reader){
		GenerateTestCase testCaseEuler = new GenerateTestCase(dualGraphBalanced);
		List<List<State>> testSequenceBreadth = testCaseEuler.inicio();
		List<String> testListSequence = new LinkedList<String>();
		
		for(List<State> listState: testSequenceBreadth){
			if(!listState.isEmpty()){
				String testList = "";
				
				if(typeFile.equals("xml")) for(State state: listState) testList = testList + (state.getName() +",");
				else for(State state: listState) testList = testList + (state.getName().substring(2, 3)+",");
				
				testListSequence.add(testList.substring(0, testList.length()-1));
				break;
			}
		}
		
		if(typeFile.equals("xml")) reader.insertFile(path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tseuler.txt", testListSequence);
		else reader.insertFile("src/"+path.substring(0, path.length() - (path.length() - path.lastIndexOf("/")))+"/tseuler.txt", testListSequence);
	}
	
	private void createGraphTestCase(String path, String typeFile, int i) throws ParserConfigurationException, SAXException, IOException{
		try{
			//Step 01: Create a graph in memory, making 1) the XML parse or 2) catch the a TXT file
			//System.out.println("Step 01");
			Reader reader = new Reader();
			
			if(typeFile.equals("xml")) graph = graph.openXML(path);
			else graph = reader.openTXT(path);
			
			if(graph.getIteratorState().hasNext()){
				//Step 02: Create the new graph with new transitions of the dual graph
				//System.out.println("Step 02");
				dualGraphConverted = node.transitionsConvertedNode(graph, typeFile);
				dualGraphConverted.inicialState();
				
				if(i == 1 || i == 2 || i == 4 || i == 5){ //Breadth ou Depth-First-Search
					//Step 03: Create the test case with the dual graph in Depth or Breadth First Search
					//System.out.println("Step 03");
					firstSearchTestCase(path, typeFile, reader, i);
				}
				else if(i == 3 || i == 6){ //Eulerian Cycle
					//Step 04: Balance graph with Floyd Wharshal
					//System.out.println("Step 04");
					dualGraphBalanced = balancing.inicio(dualGraphConverted);
					
					//Step 05: Generate test case with Eulerian Cycle
					//System.out.println("Step 05");
					eulerianCycleTestCase(path, typeFile, reader);
				}
				else{ //Chinese Postman Problem
					//Step 06: Chinese Postman Problem (CPP)
					//System.out.println("Step 06");
					System.out.println("Chinese Postman Problem (CPP)\n\nOriginal graph");
					ChinesePostmanProblem cpp = new ChinesePostmanProblem();
					System.out.println(graph.showResult());
					cpp.testCasePCC(graph);
					System.out.println("\nBalanced graph");
					System.out.println(graph.showResult());
					System.out.println("------------------------------------------------------------\n");
					//cpp.testCasePCC(dualGraphConverted);
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
		catch(Exception ex){
			System.out.println("ERROR!\n"+ex);
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
								for(File file : pathNumber.listFiles()){ //fsm1, fsm2...
									if(!file.getName().equals(".DS_Store")){
										if(!file.getName().equals("tsdepth.txt") && !file.getName().equals("tsbreadth.txt") && !file.getName().equals("tseuler.txt") && !file.getName().equals("timeDepth.txt") && !file.getName().equals("timeBreadth.txt") && !file.getName().equals("timeEuler.txt")){
											String path = "";
											
											if(typeFile.equals("xml")) path = "./src/SwitchCover/"+pathXMLFile.getName()+"/"+pathMEF.getName()+"/"+pathNumber.getName()+"/"+file.getName();
											else path = "/SwitchCover/"+pathXMLFile.getName()+"/"+pathMEF.getName()+"/"+pathNumber.getName()+"/"+file.getName();
			
											Main main = new Main();
											main.createGraphTestCase(path, typeFile, i);
										}
									}
								}
							}
						}
					}
				}				
			}
		}
	}
	
	public void triangleWithSpaces(int s, int t, int n, int m) {
	    for (int i = 0; i <= n; i++) {
	        for (int k = 0; k < t; k++) {
	            for (int j = 0; j < ((n * 2 + 1) / 2 + 1) * (s - t); j++) {
	                System.out.print(" ");
	            }
	            for (int j = n - i; j > 0; j--) {
	                System.out.print(" ");
	            }
	            for (int j = 0; j < i * 2 + 1; j++) {
	                System.out.print(m);
	            }
	            for (int j = n - i; j > 0; j--) {
	                System.out.print(" ");
	            }
	            System.out.print(" ");
	        }
	        System.out.println();
	    }
	}
	
	public void easteregg(){
		System.out.println("\n---------------------------------------\nPlease understand, I AM ERROR!");
		triangleWithSpaces(2, 1, 2, 0);
	    triangleWithSpaces(2, 2, 2, 0);
	    System.out.println("\n---------------------------------------\n");
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

				if ((r < 1 || r > 8) && r != 55) System.out.println("\n\n[Only numbers between 1 and 8!]");
				else if (r == 8) System.exit(0);
				else if (r == 55) easteregg();
				else return r;
			}
			catch (Exception e) {
				System.out.println("\n\n[Hey, the value don'ts a number of 1 for 8!]");
			}
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		Main main = new Main();
		main.source(main.inicialize());
	}

}