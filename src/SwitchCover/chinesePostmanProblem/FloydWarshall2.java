package SwitchCover.chinesePostmanProblem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//A Java program for Floyd Warshall All Pairs Shortest
//Path algorithm.

class FloydWarshall2 {
	final static int INF = 99999;

	private int[][] floydWarshall(int graph[][]) {
		int V = graph.length;
		int dist[][] = new int[V][V];
		int i, j, k;

		/*
		 * Initialize the solution matrix same as input graph matrix. Or we can say the
		 * initial values of shortest distances are based on shortest paths considering
		 * no intermediate vertex.
		 */
		for (i = 0; i < V; i++)
			for (j = 0; j < V; j++)
				dist[i][j] = graph[i][j];

		/*
		 * Add all vertices one by one to the set of intermediate vertices. ---> Before
		 * start of a iteration, we have shortest distances between all pairs of
		 * vertices such that the shortest distances consider only the vertices in set
		 * {0, 1, 2, .. k-1} as intermediate vertices. ----> After the end of a
		 * iteration, vertex no. k is added to the set of intermediate vertices and the
		 * set becomes {0, 1, 2, .. k}
		 */
		ArrayList<ArrayList<LinkedList<Integer>>> gra = new ArrayList<ArrayList<LinkedList<Integer>>>(V);
		for(int l=0; l<V; l++) {
			ArrayList<LinkedList<Integer>> column = new ArrayList<LinkedList<Integer>>(V);
			for(int t=0; t<V; t++) column.add(new LinkedList<Integer>());
			gra.add(column);
		}
		
		int count = 0;
		
		for (k = 0; k < V; k++) {
			// Pick all vertices as source one by one
			for (i = 0; i < V; i++) {
				// Pick all vertices as destination for the
				// above picked source
				for (j = 0; j < V; j++) {
					// If vertex k is on the shortest path from
					// i to j, then update the value of dist[i][j]
					//if(i == 5 && j == 0) System.out.println(dist[i][k] + " + "+ dist[k][j] + " < " + dist[i][j] + " / i: " + i + ", j: " + j + ", k: " + k);
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						//System.out.println(dist[i][k] + " + "+ dist[k][j] + " < " + dist[i][j] + " / i: " + i + ", j: " + j + ", k: " + k);
						dist[i][j] = dist[i][k] + dist[k][j];
						//printSolution(dist, graph.length);
						//System.out.println("\n");
					}	
				}
			}
		}
		/*LinkedList<Integer> path = new LinkedList<Integer>();
		ArrayList<LinkedList<Integer>> column = new ArrayList<LinkedList<Integer>>(V);
		for(int t=0; t< V; t++) column.add(null);
		path.add(i);
		path.add(k);
		path.add(k);
		path.add(j);
		column.add(j, path);
		gra.add(i, column);
		System.out.println(">"+ gra.toString()+"\n");*/

		/*for(int l=0; l<V; l++) {
			System.out.print(l+ " : ");
			for(int c=0; c<V; c++) {
				if(gra.get(l) != null && gra.get(l).get(c) != null) System.out.print(gra.get(l).get(c)+" | ");
			}
			System.out.println("");
		}*/
		

		// Print the shortest distance matrix
		//printSolution(dist, graph.length);
		return dist;
	}

	private void printSolution(int dist[][], int V) {
		System.out.println("Following matrix shows the shortest " + "distances between every pair of vertices");
		for (int i = 0; i < V; ++i) {
			for (int j = 0; j < V; ++j) {
				if (dist[i][j] == INF)
					System.out.print("INF ");
				else
					System.out.print(dist[i][j] + "   ");
			}
			System.out.println();
		}
	}

	// Driver program to test above function
	public int[][] main(int[][] g) {
		/*
		 * Let us create the following weighted graph 10
		
		int graph[][] = { {  0,  1,  1}, 
						  {INF,  0,  1}, 
						  {  1,  1,  0}};
		 */
		
		FloydWarshall2 a = new FloydWarshall2();
		return a.floydWarshall(g);
	}
}

// Contributed by Aakash Hasija