package SwitchCover.chinesePostmanProblem;

import java.util.ArrayList;
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
	
	public int[][] insertValueMatriz(int[][] matriz, int size){
		for(int r = 0; r < size; r++){
			for(int c = 0; c < size; c++){
				//matriz[r][c] = Integer.MAX_VALUE;
				if(r == c) matriz[r][c] = 0;
				else matriz[r][c] = INF;
			}
		}
		return matriz;
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
	
	public int[][] createMatriz(Graph graph, int i){
		int [][] matriz = new int[i][i];
		matriz = insertValueMatriz(matriz, i); //now every vector's point have INTERGER.MAX_VALUE
		
		Iterator<State> row = graph.getIteratorStateValue();
		
		while(row.hasNext()){
			State s = row.next();
			
			for(Transition t : s.getTransitions()){
				//System.out.println(">State: "+s.getIdCPP()+"\n   |- " +t.getDestination().getIdCPP());
				int r = s.getIdCPP();
				int c = t.getDestination().getIdCPP();
				matriz[r][c] = s.getPonderosity();
			}
		}
		
		return matriz;
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
	
	public void showTest(int[][] matriz){
		for(int i = 0; i < matriz.length; i++){
			for(int j = 0; j < matriz.length; j++){
				
				if(matriz[i][j] == INF) System.out.print("  |");
				else if(matriz[i][j] >= 0) System.out.print(" "+matriz[i][j]+"|");
				else System.out.print(matriz[i][j]+"|");
			}
			System.out.print("\n");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testCasePCC(Graph graph){
		
		Iterator<State> stateList = graph.getIteratorStateValue();
		int i = 0;

		while(stateList.hasNext()){
			State state = stateList.next();
			// (saída - entrada) --- getTransitions() = saída / sumInTransition() = entrada
			//state.setPonderosity(state.getTransitions().size() - sumInTransition(graph, state));
			state.setPonderosity(1);
			state.setIdCPP(i);
			i++;
		}
		
		//https://www-m9.ma.tum.de/graph-algorithms/spp-floyd-warshall/index_en.html
		
		//Matriz do grafo
		System.out.println("\n-------------MATRIX--------------");
		int[][] matrix = createMatriz(graph, size(graph));
		/*int[][] matrix = {{0,   1, INF,   1},
						  {1,   0, INF, INF},
						  {1,   1,   0, INF},
						  {INF, 1,   1,   0}};*/
		showTest(matrix);
		
		//Encontrar o caminho minimo partindo de um no para outro
		System.out.println("\n-------------FLOYD WARSHAL--------------");
		FloydWarshall2 fw = new FloydWarshall2();
		int[][] matrixFW = fw.main(matrix);
		
		System.out.println("\n-------------HUNGARIAN METHOD-----------");
		//Encontrar o maximal matching entre os nos desbalanceados (saber quais deles inserir um caminho)
		int[][] desbalancedMatrix = desbalancedNodes(matrix);
		List<Object> list = convertToDouble(matrixFW, desbalancedMatrix);
		HungarianAlgorithm ha = new HungarianAlgorithm((double[][]) list.get(0));	
		int[] HMresult = ha.execute();
		System.out.print("Maximal Matching: ");
		for(int w = 0; w < HMresult.length; w++) {
			System.out.print(HMresult[w] +", ");
		}
		
		//Inserir um caminho partindo de um no negativo pra um positivo
		System.out.println("\n\n-------------BALANCING GRAPH-----------");
		List<LinkedList<LinkedList<Integer>>> originalIDMatrix = (List<LinkedList<LinkedList<Integer>>>) list.get(1);
		double[][] subDesbalancedMatrix = (double[][]) list.get(0);
		List<List<Transition>> newPath = new LinkedList<>();
		for(int id = 0; id < HMresult.length; id++) {
			int source = originalIDMatrix.get(id).get(HMresult[id]).get(0);
			int destination = originalIDMatrix.get(id).get(HMresult[id]).get(1);
			int pathLength = (int) subDesbalancedMatrix[id][HMresult[id]];
			newPath.add(newPathToGraph(source, destination, pathLength, graph));			
		}
		addPathToGraph(newPath, graph);
	}
	
	private void addPathToGraph(List<List<Transition>> newPath, Graph graph) {
		for(int i = 0; i < newPath.size(); i++) {
			for(int j = 0; j < newPath.get(i).size(); j++) {
				newPath.get(i).get(j).getSource().setTransition(newPath.get(i).get(j));
			}
		}
	}
	
	
	private List<Transition> newPathToGraph(int source, int destination, int pathLength, Graph graph) {
		//System.out.println("\nSource: "+ source +", Destination: "+ destination+ ", Path length: "+ pathLength);
		List<List<String>> searchSequence = new LinkedList<List<String>>();
		List<Transition> newPath = new LinkedList<Transition>();
		FirstSearch search = new FirstSearch();
		State stateTarget = targetState(graph, source);
		searchSequence = search.TESTE(stateTarget, pathLength, graph);
		
		for(List<String> sequence: searchSequence) {
			if(sequence.get(sequence.size()-1).equals(targetState(graph, destination).getName())) {
				System.out.println(sequence);
				
				for(int i = 0; i < pathLength; i++) {
					State stateSource = graph.getState(sequence.get(i));
					State stateDestination = graph.getState(sequence.get(i+1));
					Transition t = stateSource.getTransition(stateDestination);
					//PADRAO: toda aresta balanceada recebe o nome de B+nome_original
					Transition tBalance = new Transition(t.getInput(), t.getOutput(), "B"+t.getName(), t.getDestination(), t.getSource(), false, t.getCounter());
					//stateSource.setTransition(tBalance);
					newPath.add(tBalance);
					System.out.println(t.getSource().getName()+" -> "+t.getDestination().getName());
				}
			}
		}
		
		return newPath;
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
	
	private List<Object> convertToDouble(int[][] matrixFW, int[][] desbalancedMatrix) {
		List<LinkedList<LinkedList<Integer>>> originalIDMatrix = new LinkedList<LinkedList<LinkedList<Integer>>>();
		List<List<Double>> sub = new LinkedList<>();
		
		for(int i = 0; i < desbalancedMatrix[0].length; i++) {
			int positiveNode = desbalancedMatrix[0][i];
			while(positiveNode != 0) {
				List<Double> list = new LinkedList<>();
				LinkedList<LinkedList<Integer>> line = new LinkedList<LinkedList<Integer>>();
				for(int t = 0; t < matrixFW[i].length; t++) {
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
		
		double[][] subDesbalancedMatrix = new double[sub.size()][sub.get(0).size()];
		for(int k = 0; k < sub.size(); k++) {
			for(int l = 0; l < sub.get(k).size(); l++) {
				subDesbalancedMatrix[k][l] = sub.get(k).get(l); 
			}
		}
		List<Object> a = new ArrayList<Object>();
		a.add(subDesbalancedMatrix);
		a.add(originalIDMatrix);
		
		return a;
		/*double[][] ma = {{3.0,1.0,2.0},
						 {2.0,3.0,2.0},
						 {2.0,3.0,2.0}};
		
		return ma;*/
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
}
