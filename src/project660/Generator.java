package project660;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Generator 
{
    final static boolean DIRECTED = true;
    
    /**
     * Generate new graph
     * 
     * @throws IOException
     */
    public static void generate() throws IOException
    {
        boolean another_graph = true;
        
        while (another_graph) {
            Pair pair = new Pair();
            pair      = getNP();
            Graph g   = getGraph(pair);
            
            g.print_graph();
            saveGraph(g);
            
            String line = Interactor.getString("Would you like to generate another graph? [y/N]:");
            
            if (line.length() > 0) {
                if (line.charAt(0) == 'Y' || line.charAt(0) == 'y')
                    another_graph = true;
                else another_graph = false;
            } else another_graph = false;
        }
    }
    
    /**
     * Get NP parameters
     * 
     * @return Pair
     * @throws IOException
     */
    private static Pair getNP() throws IOException
    {
        int n    = Interactor.getInt("\nn=");
        double p = Interactor.getDouble("\np=");
        
        return new Pair(n,p);
    }
    
    /**
     * Build the graph
     * 
     * @param pair
     * @return
     */
    private static Graph getGraph(Pair pair)
    {
        Graph g     = new Graph(pair.getN(), pair.getP(), DIRECTED);
        Random rand = new Random();
        
        g.insert_vertex(pair.getN());
        
        for (int i = 1; i <= pair.getN(); i++) {
            for (int j = 1; j <= pair.getN(); j++) {
                if (i == j) continue;
                
                double rnd = rand.nextDouble();
                
                if (rnd <= pair.getP()) {
                    g.insert_edge(i, j, DIRECTED);
                }
            }    
        }
        
        return g;
    }
    
    /**
     * Store graph into the file
     * 
     * @param g
     * @throws IOException
     */
    private static void saveGraph(Graph g) throws IOException
    {
        String line = Interactor.getString("Filename to save this graph [ENTER - don't save]:");
        
        if (line.length() > 0) {
            FileOutputStream fout  = new FileOutputStream(line);
            ObjectOutputStream oos = new ObjectOutputStream(fout);   
            oos.writeObject(g);
            oos.close();
        }
    }
    
}
