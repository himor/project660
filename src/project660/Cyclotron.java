package project660;

/**
 *  Algorithm for finding cycles of size K in directed graphs
 *  UAlbany CSI 660 2013
 *  
 *  @author Mike Gordo <mgordo@live.com>
 */
public class Cyclotron 
{
    Graph g;
    int cycle_size;
    Cycle stack[];
    int ncycles = 0;
    
    public Cyclotron(Graph g) 
    {
        this.g = g;
    }
    
    /**
     * Runs search for cycles
     * 
     * @param size - size of cycles
     */
    public void execute(int size) 
    {
        this.ncycles    = 0;
        this.cycle_size = size;
        Edgenode p;
        
        stack = new Cycle[g.nvertices * 10];
        // NOTE: the size of ^stack^ array should be increased for dense graphs
        
        for (int i = 1; i <= g.nvertices; i++) {
            p = g.edges[i];
//            System.out.print(" ");
            Cycle k = new Cycle(cycle_size);
            sniff (k, i, true);
        }
        
        System.out.println("Number of cycles of size " + size + ": " + ncycles);
        
        for (int i = 0; i < ncycles; i++) {
            System.out.println(stack[i].toString());
        }
        
        System.out.println();
    }
    
    /**
     * Checks if cycle or its variation is in stack already
     * ( cycles 1-2-3-1, 3-1-2-3 and 2-3-1-2 are the same and we want only one of them )
     * 
     * @return boolean
     */
    private boolean checkStack(Cycle z) 
    {
        if (ncycles < 1) 
            return false;
        
        for (int i = 0; i < ncycles; i++) 
        {
            boolean eq = true;
            
            for (int j = 1; j <= z.maximum; j++) {
                if (stack[i].members[j] != z.members[j]) {
                    eq = false;
                }
            }
            
            if (eq) return true;
        }
        
        return false;            
    }
    
    /**
     * Checks if similar cycle is in stack already 
     */
    private boolean find(Cycle z) 
    {
//        System.out.println("FOUND:"+z.toString());
        Cycle[] variants = z.generateVariants();
        
        for (int i = 0; i < variants.length; i++) {
            if (checkStack(variants[i])) return true;
        }
        
        return false;
    }
    
    /**
     * Check all cycles that start on this node
     */
    private void sniff(Cycle cycle, int start, boolean init) 
    {
        if (cycle.isEmpty() && !init) 
            return;
        
//        System.out.println("sniff (" + cycle.toString() + "), " + start);
        boolean yes = false; /* have we got a cycle? */
        yes         = cycle.push(start);
        
        if (yes) {
            /* Yes we have a cycle */
            if (!find(cycle)) 
                stack[ncycles++] = new Cycle(cycle);
            return;
        }
        
        if (cycle.isFull()) {
            /* Cycle is full, but not a real cycle - do rollback */
            cycle.rollback();
            return;
        }
        
        Edgenode p = g.edges[start];
        
        while (p != null) {
            sniff (cycle, p.y, false);
            p = p.next;
        }
        
        cycle.rollback();
        return;
    }
    
    
}
