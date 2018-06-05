package project660;

import java.io.Serializable;

public class Graph implements Serializable {
    private static final long serialVersionUID = 1L;

    Edgenode[] edges; /* adjacency info */
    int[] indegree; /* indegree of each vertex */
    int[] outdegree; /* outdegree of each vertex */
    int nvertices; /* number of vertices in graph */
    int nedges; /* number of edges in graph */
    Boolean directed; /* is the graph directed? */
    public int pathLength;
    double probability;
    int[] parent; /* discovery relation */
    public int MAXV = 0;

    public Graph(int n, double p, Boolean directed) {
        this.MAXV = n;
        this.directed = directed;

        edges = new Edgenode[MAXV + 1];
        outdegree = new int[MAXV + 1];
        indegree = new int[MAXV + 1];

        for (int i = 1; i <= MAXV; i++) {
            this.indegree[i] = 0;
            this.outdegree[i] = 0;
            this.edges[i] = null;
        }

        this.pathLength = MAXV;
        this.probability = p;
    }

    /**
     * Add single vertex
     */
    public void insert_vertex() {
        nvertices++;
    }

    /**
     * Add vertex to the existing graph
     */
    public void addVertex(int n) {
        insert_vertex(n);
        MAXV = nvertices;
        reinitArrays(n);
        recountInOut();
    }

    /**
     * Remove vertex from the existing graph
     * 
     * @param int n Vertex id
     */
    public void removeVertex(int n) {
        Edgenode p = null;
        Edgenode last = null;

        for (int i = 1; i <= this.nvertices; i++) {
            if (n == i) {
                // remove outgoing edges
                this.edges[i] = null;
                continue;
            }

            last = null;
            p = this.edges[i];
            // remove incoming edges
            while (p != null) {
                if (p.y == n) {
                    if (last == null) {
                        edges[i] = p.next;
                    } else {
                        last.next = p.next;
                    }
                }

                last = p;
                p = p.next;
            }
        }

        // rename edges
        for (int i = n + 1; i <= this.nvertices; i++) {
            edges[i - 1] = edges[i];
        }

        nvertices--;
        MAXV = nvertices;
        reinitArrays(nvertices);

        for (int i = 1; i <= this.nvertices; i++) {
            p = edges[i];

            while (p != null) {
                if (p.y > n) {
                    p.y--;
                }

                p = p.next;
            }
        }

        recountInOut();
    }

    private void recountInOut() {
        Edgenode p = null;

        for (int i = 1; i <= this.nvertices; i++) {
            outdegree[i] = 0;
            indegree[i] = 0;
        }

        for (int i = 1; i <= this.nvertices; i++) {
            p = edges[i];

            if (p == null) {
                outdegree[i] = 0;
            } else {

                while (p != null) {
                    outdegree[i]++;
                    indegree[p.y]++;
                    p = p.next;
                }

            }
        }
    }

    /**
     * Reinitialize arrays
     */
    private void reinitArrays(int n) {
        Edgenode[] newedges = new Edgenode[edges.length + n];
        System.arraycopy(edges, 0, newedges, 0, edges.length);
        edges = newedges;

        int[] newindegree = new int[indegree.length + n];
        System.arraycopy(indegree, 0, newindegree, 0, indegree.length);
        indegree = newindegree;

        int[] newoutdegree = new int[outdegree.length + n];
        System.arraycopy(outdegree, 0, newoutdegree, 0, outdegree.length);
        outdegree = newoutdegree;
    }

    /**
     * Add number of vertices
     * 
     * @param int n
     */
    public void insert_vertex(int n) {
        this.nvertices += n;
    }

    /**
     * Create edge
     * 
     * @param x
     * @param y
     * @param directed
     */
    public void insert_edge(int x, int y, Boolean directed) {
        Edgenode p = new Edgenode(); /* temporary pointer */
        p.y = y;
        p.next = this.edges[x];

        this.edges[x] = p; /* insert at head of list */
        this.outdegree[x]++;
        this.indegree[y]++;

        if (!directed) {
            insert_edge(y, x, true);
        } else {
            this.nedges++;
        }
    }

    /**
     * Remove edge
     * 
     * @param int n Vertex id
     */
    public void remove_edge(int x, int y, Boolean directed) {
        Edgenode p = null;
        Edgenode last = null;

        for (int i = 1; i <= this.nvertices; i++) {
            if (x != i) {
                continue;
            }

            last = null;
            p = this.edges[i];

            // remove incoming edges
            while (p != null) {
                if (p.y == y) {
                    if (last == null) {
                        edges[i] = p.next;
                    } else {
                        last.next = p.next;
                    }
                }

                last = p;
                p = p.next;
            }
        }

        // re-count in/out degrees
        for (int i = 1; i <= this.nvertices; i++) {
            outdegree[i] = 0;
            indegree[i] = 0;
        }

        for (int i = 1; i <= this.nvertices; i++) {
            p = edges[i];
            if (p == null) {
                outdegree[i] = 0;
            } else {

                while (p != null) {
                    outdegree[i]++;
                    indegree[p.y]++;
                    p = p.next;
                }

            }
        }
    }

    /**
     * Returns basic information about the graph
     * 
     * @return String
     */
    public String print_graph() {
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
    public String print_graph(boolean full) {
        String s = "", j = "";

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

            s += j.substring(0, j.length() - 2) + "<br>";
            j = "";
        }

        return print_graph() + "<br>" + s;
    }

    /**
     * Return the number of vertices
     * 
     * @return int
     */
    public int getNvertices() {
        return this.nvertices;
    }

    /**
     * Get the edgenode
     * 
     * @param int n Edge number
     * 
     * @return Edgenode
     */
    public Edgenode getEdge(int n) {
        return this.edges[n];
    }

    /**
     * Get all edgenodes
     * 
     * @return array
     */
    public Edgenode[] getEdge() {
        return this.edges;
    }

    /**
     * Get all indegrees
     * 
     * @return array
     */
    public int[] getIndegree() {
        return this.indegree;
    }

    /**
     * Get all outdegrees
     * 
     * @return array
     */
    public int[] getOutdegree() {
        return this.outdegree;
    }

}
