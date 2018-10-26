package SwitchCover.chinesePostmanProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;
import SwitchCover.method.FirstSearch;

public class ChinesePostmanProblem {
	
	private static final int INF = 99999;
	
	public ChinesePostmanProblem(){
		
	}
	
	public int sumInTransition(Graph graph, State st){
		Iterator<State> stateList = graph.getIteratorStateValue();
		int total = 0;
		
		while(stateList.hasNext()){
			State state = stateList.next();
			for(Transition t: state.getTransitions()){
				if(t.getDestination().getName().equals(st.getName())) total++;
			}
		}
		return total;
	}
	
	public void show(int[][] matriz){
		for(int i = 0; i < matriz.length; i++){
			for(int j = 0; j < matriz[i].length; j++){
				
				if(matriz[i][j] == INF) System.out.print("  |");
				else if(matriz[i][j] >= 0) System.out.print(" "+matriz[i][j]+"|");
				else System.out.print(matriz[i][j]+"|");
			}
			System.out.print("\n");
		}
	}
	
	public void show(double[][] matriz){
		for(int i = 0; i < matriz.length; i++){
			for(int j = 0; j < matriz[i].length; j++){
				
				if(matriz[i][j] == INF) System.out.print("  |");
				else if(matriz[i][j] >= 0) System.out.print(" "+matriz[i][j]+"|");
				else System.out.print(matriz[i][j]+"|");
			}
			System.out.print("\n");
		}
	}
	
	private void setIdCpp(Graph graph) {
		Iterator<State> stateList = graph.getIteratorStateValue();
		int i = 0;
		
		while(stateList.hasNext()){
			State state = stateList.next();
			state.setIdCPP(i);
			i++;
		}
	}
	
	public int size(Graph graph){
		Iterator<State> itGraph = graph.getIteratorStateValue();
		int i = 0;
		
		while(itGraph.hasNext()){
			i++;
			itGraph.next();
		}
		return i;
	}
	
	public int[][] insertValueMatriz(int[][] matriz, int size){
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++){
				//matriz[r][c] = Integer.MAX_VALUE;
				//if(r == c) matriz[r][c] = 0;
				//else matriz[r][c] = INF;
				matriz[r][c] = INF;
			}
		}
		return matriz;
	}
	
	public int[][] createMatriz(Graph graph, int i){
		int [][] matriz = new int[i][i];
		matriz = insertValueMatriz(matriz, i);
		
		Iterator<State> row = graph.getIteratorStateValue();
		while(row.hasNext()){
			State s = row.next();
			//System.out.print(" "+s.getName()+"|");
			for(Transition t : s.getTransitions()){
				int r = s.getIdCPP();
				int c = t.getDestination().getIdCPP();
				matriz[r][c] = s.getPonderosity();
			}
		}
		//System.out.println("\n");
		return matriz;
	}
	
	private int[][] desbalancedNodes(int[][] matrix){
		int[][] desbalancedNodes = new int[2][matrix.length];
		
		for(int c = 0; c < matrix.length; c++) {
			int sumColumn = 0, sumLine = 0;
			for(int l = 0; l < matrix.length; l++) {
				if(matrix[l][c] != INF) sumColumn = sumColumn + matrix[l][c];
			}
			for(int l = 0; l < matrix.length; l++) {
				if(matrix[c][l] != INF) sumLine = sumLine + matrix[c][l];
			}
			if((sumLine - sumColumn) < 0) desbalancedNodes[0][c] = sumLine - sumColumn;
			else if ((sumLine - sumColumn) > 0) desbalancedNodes[1][c] = sumLine - sumColumn;
		}
		
		return desbalancedNodes;
	}
	
	public boolean checkIfIsBalanced(int[][] desbalancedMatrix) {
		for(int l = 0; l < desbalancedMatrix.length; l++) {
			for(int c = 0; c < desbalancedMatrix[l].length; c++) {
				if(desbalancedMatrix[l][c] != 0) return false;
			}
		}
		return true;
	}

	private List<Object> convertToDouble(int[][] matrixFW, int[][] desbalancedMatrix) {
		List<LinkedList<LinkedList<Integer>>> originalIDMatrix = new LinkedList<LinkedList<LinkedList<Integer>>>();
		List<List<Double>> sub = new LinkedList<>();
	
		for(int i = 0; i < desbalancedMatrix[0].length; i++) {
			int positiveNode = desbalancedMatrix[0][i];
			
			while(positiveNode != 0) {
				List<Double> list = new LinkedList<>();
				LinkedList<LinkedList<Integer>> line = new LinkedList<LinkedList<Integer>>();
				
				for(int t = 0; t < desbalancedMatrix[1].length; t++) {
					int negativeNode = desbalancedMatrix[1][t];
					
					while(negativeNode != 0) {
						list.add((double) matrixFW[i][t]);
						LinkedList<Integer> id = new LinkedList<Integer>();
						id.add(i);
						id.add(t);
						line.add(id);
						negativeNode = negativeNode - 1;
					}
				}
				originalIDMatrix.add(line);
				sub.add(list);
				positiveNode = positiveNode + 1;
			}
		}
		
		if(sub.size() == 0) {
			double[][] subDesbalancedMatrix = new double[matrixFW.length][matrixFW.length];
			for(int k = 0; k < matrixFW.length; k++) {
				for(int l = 0; l < matrixFW.length; l++) {
					subDesbalancedMatrix[k][l] = 0.0; 
				}
			}
			List<Object> a = new ArrayList<Object>();
			a.add(subDesbalancedMatrix);
			a.add(originalIDMatrix);
			return a;
		}
		else {
			double[][] subDesbalancedMatrix = new double[sub.size()][sub.size()];
			for(int k = 0; k < sub.size(); k++) {
				for(int l = 0; l < sub.size(); l++) {
					subDesbalancedMatrix[k][l] = sub.get(k).get(l); 
				}
			}
			List<Object> a = new ArrayList<Object>();
			a.add(subDesbalancedMatrix);
			a.add(originalIDMatrix);
			return a;
		}
	}
	
	private State targetState(Graph graph, int target) {
		Iterator<State> stateList = graph.getIteratorStateValue();
		int i = 0;
		while(stateList.hasNext()) {
			State state = stateList.next();
			if(i == target) {
				return state;
			}
			i=i+1;
		}
		return null;
	}
	
	private List<Transition> breadthFirstSearchByPath(int pathLength, State source, Transition transition, State destination) {
		List<Transition> path = new LinkedList<Transition>();
		
		if(pathLength == 0) {
			if(source.getName().equals(destination.getName())) path.add(transition);
			return path;
		}
		else {
			for(Transition t: source.getTransitions()) {
				path = breadthFirstSearchByPath(pathLength-1, t.getDestination(), t, destination);
				if(path.size() == pathLength) {
					if(transition != null) path.add(transition);
					break;
				}
			}
		}
		return path;
	}
	
	private List<Transition> newPathBalanced(List<Transition> path){
		List<Transition> balancedPath = new LinkedList<Transition>();
		for(Transition t: path) {
			Transition balancedTransition = new Transition(t.getInput(), t.getOutput(), t.getName(), t.getDestination(), t.getSource(), false, t.getCounter());
			balancedPath.add(balancedTransition);
		}
		return balancedPath;
	}
	
	private List<Transition> newPathToGraph(int sourceID, int destinationID, int pathLength, Graph graph) {
		//System.out.println("\nSource: "+ sourceID +", Destination: "+ destinationID+ ", Path length: "+ pathLength);
		State source = targetState(graph, sourceID);
		State destination = targetState(graph, destinationID);
		
		List<Transition> path = breadthFirstSearchByPath(pathLength, source, null, destination);
		Collections.reverse(path);
		List<Transition> balancedPath = newPathBalanced(path);

		return balancedPath;
	}
	
	/*private List<Transition> newPathToGraph(int source, int destination, int pathLength, Graph graph) {
		//System.out.println("\nSource: "+ source +", Destination: "+ destination+ ", Path length: "+ pathLength);
		List<List<String>> searchSequence = new LinkedList<List<String>>();
		List<Transition> newPath = new LinkedList<Transition>();
		FirstSearch search = new FirstSearch();
		State stateTarget = targetState(graph, source);
		State stateDestin = targetState(graph, destination);
		searchSequence = search.TESTE(stateTarget, stateDestin, pathLength, graph);

		for(List<String> sequence: searchSequence) {
			if(sequence.get(sequence.size()-1).equals(targetState(graph, destination).getName())) {
				//System.out.println(sequence);
				
				for(int i = 0; i < pathLength; i++) {
					State stateSource = graph.getState(sequence.get(i));
					State stateDestination = graph.getState(sequence.get(i+1));
					Transition t = stateSource.getTransition(stateDestination);
					//PADRAO: toda aresta balanceada recebe o nome de B+nome_original
					Transition tBalance = new Transition(t.getInput(), t.getOutput(), "B"+t.getName(), t.getDestination(), t.getSource(), false, t.getCounter());
					//stateSource.setTransition(tBalance);
					newPath.add(tBalance);
					//System.out.println(t.getSource().getName()+" -> "+t.getDestination().getName());
				}
			}
		}
		
		return newPath;
	}*/
	
	private void addPathToGraph(List<List<Transition>> newPath, Graph graph) {
		for(int i = 0; i < newPath.size(); i++) {
			for(int j = 0; j < newPath.get(i).size(); j++) {
				newPath.get(i).get(j).getSource().setTransition(newPath.get(i).get(j));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graph testCasePCC(Graph graph){
		//https://www-m9.ma.tum.de/graph-algorithms/spp-floyd-warshall/index_en.html

		//Step 1: Convert graph in a adjacent matrix
		setIdCpp(graph);
		int[][] matrix = createMatriz(graph, size(graph));
		//show(matrix);
		//System.out.println();
		
		//Step 2: Floyd-Warshal - find a minimal path between 2 nodes
		FloydWarshall2 fw = new FloydWarshall2();
		int[][] matrixFW = fw.main(matrix);
		//show(matrixFW);
		//System.out.println();
		
		int[][] desbalancedMatrix = desbalancedNodes(matrix);
		//show(desbalancedMatrix);
		//System.out.println();
		
		if(!checkIfIsBalanced(desbalancedMatrix)) {
			//Step 3: Hungarian method - find the maximal matching between the desbalanced nodes to find a path between then
			//double[][] HMmatrix = HMmatrix(matrixFW, desbalancedMatrix);
			//show(HMmatrix);
			List<Object> list = convertToDouble(matrixFW, desbalancedMatrix);
			//show((double[][]) list.get(0));
			//System.out.println();
			
			HungarianAlgorithm ha = new HungarianAlgorithm((double[][]) list.get(0));
			int[] HMresult = ha.execute();
				
			/*System.out.print("Maximal Matching: ");
			for(int w = 0; w < HMresult.length; w++) {
				System.out.print(HMresult[w] +", ");
			}
			System.out.println("\n");*/
				
			//Step 4: insert a path from a negative node to a positive node (balancing the graph)
			List<LinkedList<LinkedList<Integer>>> originalIDMatrix = (List<LinkedList<LinkedList<Integer>>>) list.get(1);
			double[][] subDesbalancedMatrix = (double[][]) list.get(0);
			List<List<Transition>> newPath = new LinkedList<>();

			for(int id = 0; id < HMresult.length; id++) {
				int source = originalIDMatrix.get(id).get(HMresult[id]).get(0);
				int destination = originalIDMatrix.get(id).get(HMresult[id]).get(1);
				int pathLength = (int) subDesbalancedMatrix[id][HMresult[id]];
				newPath.add(newPathToGraph(source, destination, pathLength, graph.clone()));
			}	
			addPathToGraph(newPath, graph);
		}
		
		return graph;
	}
}
