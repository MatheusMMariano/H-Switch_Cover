package SwitchCover.chinesePostmanProblem;

/**
 * Class for finding and printing the optimal Chinese Postman tour of multidigraphs.
 * For more details, read <a href="http://www.uclic.ucl.ac.uk/harold/cpp">http://www.uclic.ucl.ac.uk/harold/cpp</a>.
 *
 * @author Harold Thimbleby, 2001, 2, 3
 */

// Chinese Postman Code
// Harold Thimbleby, 2001-3 

// <tex file="class.tex">
import java.io.*;
import java.util.*;

public class CPP {
	int N; // number of vertices
	int delta[]; // deltas of vertices
	int neg[], pos[]; // unbalanced vertices
	int arcs[][]; // adjacency matrix, counts arcs between vertices
	Vector label[][]; // vectors of labels of arcs (for each vertex pair)
	int f[][]; // repeated arcs in CPT
	float c[][]; // costs of cheapest arcs or paths
	String cheapestLabel[][]; // labels of cheapest arcs
	boolean defined[][]; // whether path cost is defined between vertices
	int path[][]; // spanning tree of the graph
	float basicCost; // total cost of traversing each arc once

	void solve() {
		leastCostPaths();
		checkValid();
		findUnbalanced();
		findFeasible();
		while (improvements())
			;
	}

	// <literal>$\vdots$\\\vbox{}</literal><null>
	// Other declarations are described below
	// <literal>$\vdots$\\</literal>
	// </tex><tex file="constructor.tex">
	// allocate array memory, and instantiate graph object
	CPP(int vertices) {
		if ((N = vertices) <= 0)
			throw new Error("Graph is empty");
		delta = new int[N];
		defined = new boolean[N][N];
		label = new Vector[N][N];
		c = new float[N][N];
		f = new int[N][N];
		arcs = new int[N][N];
		cheapestLabel = new String[N][N];
		path = new int[N][N];
		basicCost = 0;
	}

	// </tex><tex file="addedge.tex">
	CPP addArc(String lab, int u, int v, float cost) {
		if (!defined[u][v])
			label[u][v] = new Vector();
		label[u][v].addElement(lab);
		basicCost += cost;
		if (!defined[u][v] || c[u][v] > cost) {
			c[u][v] = cost;
			cheapestLabel[u][v] = lab;
			defined[u][v] = true;
			path[u][v] = v;
		}
		arcs[u][v]++;
		delta[u]++;
		delta[v]--;
		return this;
	}
	// </tex>

	/**
	 * Floyd-Warshall algorithm Assumes no negative self-cycles. Finds least cost
	 * paths or terminates on finding any non-trivial negative cycle.
	 */
	// <tex file="floyd.tex">
	void leastCostPaths() {
		for (int k = 0; k < N; k++)
			for (int i = 0; i < N; i++)
				if (defined[i][k])
					for (int j = 0; j < N; j++)
						if (defined[k][j] && (!defined[i][j] || c[i][j] > c[i][k] + c[k][j])) {
							path[i][j] = path[i][k];
							c[i][j] = c[i][k] + c[k][j];
							defined[i][j] = true;
							if (i == j && c[i][j] < 0)
								return; // stop on negative cycle
						}
	}

	// </tex><tex file="check.tex">
	void checkValid() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				if (!defined[i][j])
					throw new Error("Graph is not strongly connected");
			if (c[i][i] < 0)
				throw new Error("Graph has a negative cycle");
		}
	}

	// </tex><tex file="cost.tex">
	float cost() {
		return basicCost + phi();
	}

	float phi() {
		float phi = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				phi += c[i][j] * f[i][j];
		return phi;
	}
	// </tex><tex file="degrees.tex">

	void findUnbalanced() {
		int nn = 0, np = 0; // number of vertices of negative/positive delta

		for (int i = 0; i < N; i++)
			if (delta[i] < 0)
				nn++;
			else if (delta[i] > 0)
				np++;

		neg = new int[nn];
		pos = new int[np];
		nn = np = 0;
		for (int i = 0; i < N; i++) // initialise sets
			if (delta[i] < 0)
				neg[nn++] = i;
			else if (delta[i] > 0)
				pos[np++] = i;
	}
	// </tex><tex file="greedy.tex">

	void findFeasible() { // delete next 3 lines to be faster, but non-reentrant
		int delta[] = new int[N];
		for (int i = 0; i < N; i++)
			delta[i] = this.delta[i];

		for (int u = 0; u < neg.length; u++) {
			int i = neg[u];
			for (int v = 0; v < pos.length; v++) {
				int j = pos[v];
				f[i][j] = -delta[i] < delta[j] ? -delta[i] : delta[j];
				delta[i] += f[i][j];
				delta[j] -= f[i][j];
			}
		}
	}

	// </tex><tex file="iterate.tex">
	boolean improvements() {
		CPP residual = new CPP(N);
		for (int u = 0; u < neg.length; u++) {
			int i = neg[u];
			for (int v = 0; v < pos.length; v++) {
				int j = pos[v];
				residual.addArc(null, i, j, c[i][j]);
				if (f[i][j] != 0)
					residual.addArc(null, j, i, -c[i][j]);
			}
		}
		residual.leastCostPaths(); // find a negative cycle
		for (int i = 0; i < N; i++)
			if (residual.c[i][i] < 0) // cancel the cycle (if any)
			{
				int k = 0, u, v;
				boolean kunset = true;
				u = i;
				do // find k to cancel
				{
					v = residual.path[u][i];
					if (residual.c[u][v] < 0 && (kunset || k > f[v][u])) {
						k = f[v][u];
						kunset = false;
					}
				} while ((u = v) != i);
				u = i;
				do // cancel k along the cycle
				{
					v = residual.path[u][i];
					if (residual.c[u][v] < 0)
						f[v][u] -= k;
					else
						f[u][v] += k;
				} while ((u = v) != i);
				return true; // have another go
			}
		return false; // no improvements found
	}

	// </tex><tex file="printCPT.tex">
	static final int NONE = -1; // anything < 0

	int findPath(int from, int f[][]) // find a path between unbalanced vertices
	{
		for (int i = 0; i < N; i++)
			if (f[from][i] > 0)
				return i;
		return NONE;
	}

	void printCPT(int startVertex) {
		int v = startVertex;

		// delete next 7 lines to be faster, but non-reentrant
		int arcs[][] = new int[N][N];
		int f[][] = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				arcs[i][j] = this.arcs[i][j];
				f[i][j] = this.f[i][j];
			}

		while (true) {
			int u = v;
			if ((v = findPath(u, f)) != NONE) {
				f[u][v]--; // remove path
				for (int p; u != v; u = p) // break down path into its arcs
				{
					p = path[u][v];
					System.out.println("Take arc " + cheapestLabel[u][p] + " from " + u + " to " + p);
				}
			} else {
				int bridgeVertex = path[u][startVertex];
				if (arcs[u][bridgeVertex] == 0)
					break; // finished if bridge already used
				v = bridgeVertex;
				for (int i = 0; i < N; i++) // find an unused arc, using bridge last
					if (i != bridgeVertex && arcs[u][i] > 0) {
						v = i;
						break;
					}
				arcs[u][v]--; // decrement count of parallel arcs
				System.out.println("Take arc " + label[u][v].elementAt(arcs[u][v]) + " from " + u + " to " + v); // use
																													// each
																													// arc
																													// label
																													// in
																													// turn
			}
		}
	}
	// </tex>

	static public void main(String args[]) {
		// <tex file="test.tex">
		CPP G = new CPP(4); // create a graph of four vertices

		// add the arcs for the example graph
		G.addArc("a", 0, 1, 1).addArc("b", 0, 2, 1).addArc("c", 1, 2, 1).addArc("d", 1, 3, 1).addArc("e", 2, 3, 1)
				.addArc("f", 3, 0, 1);
		// </tex>
		System.out.println("//<tex file=\"output.tex\">");
		// <tex file="test.tex">

		G.solve(); // find the CPT
		G.printCPT(0); // print it, starting from vertex 0
		System.out.println("Cost = " + G.cost());
		// </tex>
		System.out.println("//</tex>");
		OpenCPP.test();
	}

	// Print arcs and f
	void debugarcf() {
		for (int i = 0; i < N; i++) {
			System.out.print("f[" + i + "]= ");
			for (int j = 0; j < N; j++)
				System.out.print(f[i][j] + " ");
			System.out.print("  arcs[" + i + "]= ");
			for (int j = 0; j < N; j++)
				System.out.print(arcs[i][j] + " ");
			System.out.println();
		}
	}

	// Print out most of the matrices: defined, path and f
	void debug() {
		for (int i = 0; i < N; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < N; j++)
				System.out.print(j + ":" + (defined[i][j] ? "T" : "F") + " " + c[i][j] + " p=" + path[i][j] + " f="
						+ f[i][j] + "; ");
			System.out.println();
		}
	}

	// Print out non zero f elements, and phi
	void debugf() {
		float sum = 0;
		for (int i = 0; i < N; i++) {
			boolean any = false;
			for (int j = 0; j < N; j++)
				if (f[i][j] != 0) {
					any = true;
					System.out.print("f(" + i + "," + j + ":" + label[i][j] + ")=" + f[i][j] + "@" + c[i][j] + "  ");
					sum += f[i][j] * c[i][j];
				}
			if (any)
				System.out.println();
		}
		System.out.println("-->phi=" + sum);
	}

	// Print out cost matrix.
	void debugc() {
		for (int i = 0; i < N; i++) {
			boolean any = false;
			for (int j = 0; j < N; j++)
				if (c[i][j] != 0) {
					any = true;
					System.out.print("c(" + i + "," + j + ":" + label[i][j] + ")=" + c[i][j] + "  ");
				}
			if (any)
				System.out.println();
		}
	}
	// <tex file="class.tex">
}
// </tex>

// <tex file="open.tex">
class OpenCPP {
	class Arc {
		String lab;
		int u, v;
		float cost;

		Arc(String lab, int u, int v, float cost) {
			this.lab = lab;
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
	}

	Vector arcs = new Vector();
	int N;

	OpenCPP(int vertices) {
		N = vertices;
	}

	OpenCPP addArc(String lab, int u, int v, float cost) {
		if (cost < 0)
			throw new Error("Graph has negative costs");
		arcs.addElement(new Arc(lab, u, v, cost));
		return this;
	}

	float printCPT(int startVertex) {
		CPP bestGraph = null, g;
		float bestCost = 0, cost;
		int i = 0;
		do {
			g = new CPP(N + 1);
			for (int j = 0; j < arcs.size(); j++) {
				Arc it = (Arc) arcs.elementAt(j);
				g.addArc(it.lab, it.u, it.v, it.cost);
			}
			cost = g.basicCost;
			g.findUnbalanced(); // initialise g.neg on original graph
			g.addArc("'virtual start'", N, startVertex, cost);
			g.addArc("'virtual end'",
					// graph is Eulerian if neg.length=0
					g.neg.length == 0 ? startVertex : g.neg[i], N, cost);
			g.solve();
			if (bestGraph == null || bestCost > g.cost()) {
				bestCost = g.cost();
				bestGraph = g;
			}
		} while (++i < g.neg.length);
		System.out.println("Open CPT from " + startVertex + " (ignore virtual arcs)");
		bestGraph.printCPT(N);
		return cost + bestGraph.phi();
	}
	// </tex>

	static void test() {
		OpenCPP G = new OpenCPP(4); // create a graph of four vertices
		// add the arcs for the example graph
		G.addArc("a/0", 0, 1, 1).addArc("c/2", 0, 2, 1).addArc("b/1", 1, 2, 1).addArc("f/2", 2, 0, 1).addArc("r/0", 2,
				1, 1);
		int besti = 0;
		float bestCost = 0;
		for (int i = 0; i < 4; i++) {
			System.out.println("Solve from " + i);
			float c = G.printCPT(i);
			System.out.println("Cost = " + c);
			if (i == 0 || c < bestCost) {
				bestCost = c;
				besti = i;
			}
		}
		System.out.println("// <tex file=\"open.tex\">");
		G.printCPT(besti);
		System.out.println("Cost = " + bestCost);
		System.out.println("// </tex>");
	}
	// <tex file="open.tex">

}
// </tex>
