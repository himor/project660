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
    static BufferedReader in;
    
    /**
     * Generate new graph
     * 
     * @throws IOException
     */
    public static void generate() throws IOException
    {
        boolean another_graph = true;
        Generator gen = new Generator();
        in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        while (another_graph) {
            Pair pair = new Pair();
            pair      = getNP();
            Graph g   = getGraph(pair);
            
            g.print_graph();
            saveGraph(g);
            
            System.out.print("Would you like to generate another graph? [y/N]:");
            
            String line = in.readLine();
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
        int n    = -1;
        double p = -1;
        String line;

        while (n < 0) {
            System.out.print("\nn=");
            line = in.readLine();
            try {
                n = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("\nPlease type a numeric value!\n");
            }
        }
        
        while (p < 0) {
            System.out.print("\np=");
            line = in.readLine();
            try {
                p = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("\nPlease type a numeric value!\n");
            }
        }
        
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
        System.out.print("Filename to save this graph [ENTER - don't save]:");
        String line = in.readLine();
        
        if (line.length() > 0) {
            FileOutputStream fout  = new FileOutputStream(line);
            ObjectOutputStream oos = new ObjectOutputStream(fout);   
            oos.writeObject(g);
            oos.close();
        }
    }
    
}
