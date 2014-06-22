package project660;

import java.util.Stack;

public class Scc 
{
    private static final int CROSS      = 1;
    private static final int FORWARD = 2;
    private static final int TREE      = 3;
    private static final int BACK      = 4;

    Graph g;
    Stack<Integer> stack;
    int components_found;
    Boolean[] processed;
    Boolean[] discovered;
    int[] parent;
    
    boolean finished;
    int time;
    
    int[] low;             /* oldest vertex surely in component of v */
    int[] scc;             /* strong component number for each vertex */
    
    int[] entry_time;
    int[] exit_time;
    
    int[] size;
    
    public Scc(Graph g) 
    {
        this.g     = g;
        processed  = new Boolean[g.MAXV+1];
        discovered = new Boolean[g.MAXV+1];
        
        parent = new int[g.MAXV+1];
        low    = new int[g.MAXV+1];
        scc    = new int[g.MAXV+1];
        
        entry_time = new int[g.MAXV+1];
        exit_time  = new int[g.MAXV+1];
    }

    public void initialize_search()    
    {
        for (int i = 1; i <= this.g.nvertices; i++) {
            processed[i] = discovered[i] = false;
            parent[i] = -1;
        }
    }    

    public void strong_components() throws Exception 
    {
        for (int i = 1; i <= this.g.nvertices; i++) {
            low[i] = i;
            scc[i] = -1;
        }
        
        components_found = 0;
        stack              = new Stack<Integer>();
        
        initialize_search();
        
        for (int i = 1; i <= this.g.nvertices; i++)
            if (discovered[i] == false)
                dfs(i);
        
    }
    
    public void results() 
    {
        int lines = 0;
        int max   = 0;
//        System.out.println("\nStrongly Connected Components:\nComponents found:" + this.components_found);
//        System.out.println("First "+lines+" components displayed:");
        
        this.size = new int[components_found+1];
        
        for (int i = 1; i <= this.components_found; i++) {
            size[i] = 0;
            
            if (i <= lines) 
                System.out.print(i + ": ");
            
            for (int j = 1; j <= g.nvertices; j++) {
                //System.out.println("scc["+j+"]="+scc[j]);
                if (scc[j] == i) {
                    if (i <= lines) 
                        System.out.print(j + " ");
                    size[i] ++;
                }
            }
            
            if (i <= lines) 
                System.out.println();
        }
        
        for (int i = 1; i <= this.components_found; i++) {
            if (max < size[i]) 
                max = size[i];
        }
        
        System.out.println("Largest SCC: " + max);
    }
    
    /**
     * DFS
     * @throws Exception 
     */
    private void dfs (int v) throws Exception 
    {
        Edgenode p;     /* temporary pointer */
        int y;             /* successor vertex */
        
        if (finished)
            return; /* allow for search termination */
        
        discovered[v] = true;
        
        time          = time + 1;
        entry_time[v] = time;
        
        process_vertex_early(v);
        
        p = this.g.edges[v];
        
        while (p != null) {
            y = p.y;
            if (discovered[y] == false) {
                parent[y] = v;
                process_edge(v,y);
                dfs(y);
            }
            else if ((!processed[y]) || (this.g.directed))
                process_edge(v,y);
            
            if (finished) 
                return;
            
            p = p.next;
        }
        
        process_vertex_late(v);
        
        time          = time + 1;
        exit_time[v] = time;
        processed[v] = true;
    }
    
    private void process_vertex_early(int v) 
    {
        //System.out.println(stack.capacity() + " " + stack.size());
        stack.push(v);
    }
    
    private void process_vertex_late(int v) throws Exception 
    {
        if (low[v] == v) /* edge (parent[v],v) cuts off scc */
            pop_component(v);
        
        if (parent[v] > -1)
            if (entry_time[low[v]] < entry_time[low[parent[v]]])
                low[parent[v]] = low[v];
    }

    private void pop_component(int v) throws Exception 
    {
        int t;             /* vertex placeholder */
        components_found = components_found + 1;
        scc[v]           = components_found;
        
        while ((t = (int) stack.pop()) != v) {
            scc[t] = components_found;
        }
    }
    
    private void process_edge(int x, int y) 
    {
        int cla; /* edge class */
        cla = edge_classification(x,y);
        
        if (cla == BACK) {
            if (entry_time[y] < entry_time[ low[x] ] )
                low[x] = y;
        }
        
        if (cla == CROSS) {
            if (scc[y] == -1) /* component not yet assigned */
                if (entry_time[y] < entry_time[ low[x] ] )
                    low[x] = y;
        }
    }
    
    private int edge_classification(int x, int y) 
    {
        if (parent[y] == x) 
            return TREE;
        
        if (discovered[y] && !processed[y]) 
            return BACK;
        
        if (processed[y] && (entry_time[y]>entry_time[x]))
            return FORWARD;
        
        if (processed[y] && (entry_time[y]<entry_time[x])) 
            return CROSS;
        
        System.out.println("Warning: unclassified edge " + x + ":" + y);
        return 0;
    }
    
}
