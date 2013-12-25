package project660;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Stack;

public class Prj660 {
	
	final static boolean DIRECTED = true;
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || (!args[0].equals("generate") && !args[0].equals("analyze") && !args[0].equals("fake"))) {
			System.out.println("Usage: <application> <key>");
			System.out.println("key: generate - generate graphs");
			System.out.println("     analyze - analyze graphs");
			System.exit(0);
		}
		
		/**
		 * for testing purposes
		 */
		if (args[0].equals("fake")) {
			Graph g = new Graph(4, 0, DIRECTED);
			g.insert_vertex(4);
			g.insert_edge(1, 2, DIRECTED);
			g.insert_edge(2, 1, DIRECTED);
			g.insert_edge(2, 3, DIRECTED);
			g.insert_edge(3, 2, DIRECTED);
			g.insert_edge(3, 4, DIRECTED);
			g.insert_edge(4, 3, DIRECTED);
			g.insert_edge(4, 1, DIRECTED);
			g.insert_edge(1, 4, DIRECTED);
			
			FileOutputStream fout = new FileOutputStream("test");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			oos.writeObject(g);
			oos.close();

			g.print_graph(true);
		}
		

		if (args[0].equals("generate")) {
			boolean another_graph = true;
			while (another_graph) {	
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
				System.out.print("\nn=");
				String line = in.readLine();
				int n = Integer.parseInt(line);
				System.out.print("p=");
				line = in.readLine();
				double p = Double.parseDouble(line);
				Graph g = new Graph(n, p, DIRECTED);
				Random rand = new Random();
				g.insert_vertex(n);
				for (int i = 1; i <= n; i++) {
					for (int j = 1; j <= n; j++) {
						if (i == j) continue;
						double rnd = rand.nextDouble();
						if (rnd <= p) {
							g.insert_edge(i, j, DIRECTED);
						}
					}	
				}
				g.print_graph();
				System.out.print("Filename to save this graph:");
				line = in.readLine();
				if (line.length() > 0) {
					FileOutputStream fout = new FileOutputStream(line);
					ObjectOutputStream oos = new ObjectOutputStream(fout);   
					oos.writeObject(g);
					oos.close();
				}
				System.out.print("Would you like to generate another graph? [y/N]:");
				line = in.readLine();
				if (line.length() > 0) {
					if (line.charAt(0) == 'Y' || line.charAt(0) == 'y')
						another_graph = true;
					else another_graph = false;
				} else another_graph = false;
			} // while
		}
		
		if (args[0].equals("analyze")) {
			boolean another_graph = true;
			while (another_graph) {
				BufferedReader _in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
				System.out.print("\nLoad graph [filename]:");
				String _line = _in.readLine();
				if (_line.length() < 1) {
					System.out.println("Done");
					System.exit(0);
				}
				
				/**
				 * Load graph object from the file, if file not found - throws an exception
				 */
				FileInputStream fin = new FileInputStream(_line);
				ObjectInputStream ois = new ObjectInputStream(fin);   
				Graph g = (Graph) ois.readObject();
				ois.close();
				
				/**
				 * analyze graph here
				 */
				g.print_graph();
				// # of nodes with indegree/outdegree 0
				int indegree_zero = 0;
				int outdegree_zero = 0;
				for (int i = 1; i <= g.nvertices; i++) {
					if (g.indegree[i] == 0) indegree_zero++;
					if (g.outdegree[i] == 0) outdegree_zero++;
				}
				System.out.println("Number of nodes with  in-degree=0: " + (int)indegree_zero + " (" + ((double)indegree_zero/g.nvertices*100) + "%)");
				System.out.println("Number of nodes with out-degree=0: " + (int)outdegree_zero + " (" + ((double)outdegree_zero/g.nvertices*100) + "%)");

				// size of a largest weakly connected component
				
				Wcc wcc = new Wcc(g);
				wcc.weak_components();
				wcc.results();
				
				// size of a largest strongly connected component
				
				Scc scc = new Scc(g);
				scc.strong_components();
				scc.results();
				
				// cycles

				Cyclotron lhc = new Cyclotron(g);
				for (int i = 2 ; i <= 2; i ++)		// i = 2 .. 10
					lhc.execute(i);
				
				System.out.print("\nWould you like to analyze another graph? [y/N]:");
				_line = _in.readLine();
				if (_line.length() > 0) {
					if (_line.charAt(0) == 'Y' || _line.charAt(0) == 'y')
						another_graph = true;
					else another_graph = false;
				} else another_graph = false;
			} // while			
		}
		System.out.println("Done");
	}
}
