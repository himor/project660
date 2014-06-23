package project660;

import java.io.Serializable;

public class Graph implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    Edgenode[] edges;      /* adjacency info */
    int[] indegree;        /* indegree of each vertex */
    int[] outdegree;       /* outdegree of each vertex */
    int nvertices;         /* number of vertices in graph */
    int nedges;            /* number of edges in graph */
    Boolean directed;      /* is the graph directed? */
    public int pathLength;
    double probability;
    int[] parent;          /* discovery relation */
    public int MAXV = 0;
    
    public Graph(int n, double p, Boolean directed) 
    {
        this.MAXV     = n;
        this.directed = directed;
        
        edges     = new Edgenode[MAXV+1];
        outdegree = new int[MAXV+1];
        indegree  = new int[MAXV+1];
        
        for (int i = 1; i <= MAXV; i++)  {
            this.indegree[i]  = 0;
            this.outdegree[i] = 0;
            this.edges[i]     = null;
        }
        
        this.pathLength  = MAXV;
        this.probability = p;
    }

    /**
     * Add single vertex
     */
    public void insert_vertex() 
    {
        this.nvertices ++;
    }
    
    /**
     * Add number of vertices
     * 
     * @param int n
     */
    public void insert_vertex(int n) 
    {
        this.nvertices += n;
    }
    
    /**
     * Create edge
     * 
     * @param x
     * @param y
     * @param directed
     */
    public void insert_edge(int x, int y, Boolean directed) 
    {
        Edgenode p = new Edgenode(); /* temporary pointer */
        p.y        = y;
        p.next     = this.edges[x];
        
        this.edges[x] = p;           /* insert at head of list */
        this.outdegree[x] ++;
        this.indegree[y] ++;
        
        if (!directed)
            insert_edge(y, x, true);
        else
            this.nedges ++;
    }    
    
    /**
     * Returns basic information about the graph
     * 
     * @return String
     */
    public String print_graph() 
    {
    	String out = "";
    	
        out += "G (E=" + this.nedges;
        out += ", N=" + this.nvertices + ") <br>";
        out += "p=" + this.probability;
        out += " np=" + (this.nvertices * this.probability);
        
        return out;
    }
    
    /**
     * Print full information about the graph
     * 
     * @param Boolean full means nothing
     */
    public String print_graph(boolean full) 
    {
        String s = "", 
               j = "";
        
        Edgenode p = new Edgenode();
        
        for (int i = 1; i <= this.nvertices; i++) {
            p = this.edges[i];
            if (p == null) {
                j += i + ", ";
            } else {
                j += i + " -> ";
                while (p != null) {
                    j += p.y + ", ";
                    p = p.next;
                }
            }
            s += j.substring(0, j.length()-2) + "<br>";
            j = "";
        }
        
        return print_graph() + "<br>" + s;
    }
    
    /**
     * Return the number of vertices
     *  
     * @return int
     */
    public int getNvertices()
    {
        return this.nvertices;
    }
    
    /**
     * Get the edgenode
     * 
     * @param int n Edge number
     * 
     * @return Edgenode
     */
    public Edgenode getEdge(int n)
    {
        return this.edges[n];
    }
    
    /**
     * Get all edgenodes 
     * 
     * @return array
     */
    public Edgenode[] getEdge()
    {
        return this.edges;
    }
    
    /**
     * Get all indegrees
     * 
     * @return array
     */
    public int[] getIndegree()
    {
        return this.indegree;
    }
    
    /**
     * Get all outdegrees
     * 
     * @return array
     */
    public int[] getOutdegree()
    {
        return this.outdegree;
    }
    
}
