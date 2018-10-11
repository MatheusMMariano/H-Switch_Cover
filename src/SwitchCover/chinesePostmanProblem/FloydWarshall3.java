package SwitchCover.chinesePostmanProblem;

import java.util.Arrays;

public class FloydWarshall3 {
	
	private static double INF = Double.POSITIVE_INFINITY;
    // graph represented by an adjacency matrix
    private double[][] graph = {{INF,   1,   1,  1},
								{1,   1, INF,  1},
								{1, INF,  1,  1},
								{1,   1,  1,  INF}};

    private boolean negativeCycle;

    public FloydWarshall3() {
        //this.graph = new double[n][n];
        //initGraph();
    }

    private void initGraph() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    public boolean hasNegativeCycle() {
        return this.negativeCycle;
    }

    // utility function to add edges to the adjacencyList
    public void addEdge(int from, int to, double weight) {
        graph[from][to] = weight;
    }

    // all-pairs shortest path
    public double[][] floydWarshall() {
        double[][] distances;
        int n = this.graph.length;
        distances = Arrays.copyOf(this.graph, n);

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                }
            }

            if (distances[k][k] < 0.0) {
                this.negativeCycle = true;
            }
        }

        return distances;
    }
    
    public static void main(String[] args) {
    	FloydWarshall3 fw = new FloydWarshall3();
    	double[][] result = fw.floydWarshall();
    	
    	for(int i=0; i < result.length; i++) {
    		System.out.print("| ");
    		for(int j=0; j < result.length; j++) {
    			System.out.print(result[i][j]+" | ");
    		}
    		System.out.println();
      	}
    }

}
