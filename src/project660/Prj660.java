package project660;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Prj660 extends Menu
{
    public static void main(String[] args) throws Exception 
    {
        /**
         * Display the menu
         */
        display();
        
        /**
         * for testing purposes
         */
        if (choice == COMMAND_FAKE) {
            Graph g = new Graph(4, 0, Generator.DIRECTED);
            g.insert_vertex(4);
            g.insert_edge(1, 2, Generator.DIRECTED);
            g.insert_edge(2, 1, Generator.DIRECTED);
            g.insert_edge(2, 3, Generator.DIRECTED);
            g.insert_edge(3, 2, Generator.DIRECTED);
            g.insert_edge(3, 4, Generator.DIRECTED);
            g.insert_edge(4, 3, Generator.DIRECTED);
            g.insert_edge(4, 1, Generator.DIRECTED);
            g.insert_edge(1, 4, Generator.DIRECTED);
            
            FileOutputStream fout  = new FileOutputStream("test");
            ObjectOutputStream oos = new ObjectOutputStream(fout);   
            oos.writeObject(g);
            oos.close();

            g.print_graph(true);
        }
        

        if (choice == COMMAND_GENERATE) {
            Generator.generate();
        }
        
        if (choice == COMMAND_ANALYZE) {
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
                for (int i = 2 ; i <= 2; i ++)        // i = 2 .. 10
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
