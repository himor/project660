package project660;

import java.util.LinkedList;
import java.util.Queue;

public class Wcc {
    Graph graph;
    int components_found; // Number of weakly connected components found
    Boolean[] processed; // Keeps track of components that have been visited
    Boolean[] found;
    int[] parent;
    int[] wcc; // Number of weakly connected components for each vertex
    Queue<Integer> queue = new LinkedList<Integer>();

    public Wcc(Graph graph) {
        this.graph = graph;
        processed = new Boolean[graph.MAXV + 1];
        found = new Boolean[graph.MAXV + 1];
        parent = new int[graph.MAXV + 1];
        wcc = new int[graph.MAXV + 1];
    }

    /**
     * Method sets initial values in arrays
     */
    public void initialize_search() {
        for (int index = 1; index <= this.graph.nvertices; index++) {
            processed[index] = false;
            found[index] = false;
            parent[index] = -1;
        }
    }

    /**
     * Method clones graph and changes the edges to undirected
     */
    public void reverse_edges() {
        Graph temp = new Graph(graph.nvertices, graph.probability, graph.directed); // Clone graph

        temp.insert_vertex(graph.nvertices);
        Edgenode edgenode;

        for (int index = 1; index <= graph.nvertices; index++) {
            edgenode = graph.edges[index];

            if (edgenode == null) {
                continue;
            }

            while (edgenode != null) { // Make graph undirected
                temp.insert_edge(edgenode.y, index, false);
                edgenode = edgenode.next;
            }
        }

        this.graph = temp;
    }

    /**
     * Method calculates the number of weakly connected components in graph
     */
    public void weak_components() {
        initialize_search();
        reverse_edges();

        for (int index = 1; index <= graph.nvertices; index++) {
            if (found[index] == false) {
                components_found++;
                bfs(index);
            }
        }
    }

    /**
     * Method processes weakly connected components for graph
     */
    public String results() {
        String output = "";
        int lines = 0;
        int[] size = new int[components_found + 1];

        for (int index = 1; index <= this.components_found; index++) {
            size[index] = 0;

            if (index <= lines) {
                output += index + ": ";
            }

            for (int jdex = 1; jdex <= graph.nvertices; jdex++) {
                if (wcc[jdex] == index) {
                    if (index <= lines) {
                        output += jdex + " ";
                    }
                    size[index]++;
                }
            }

            if (index <= lines) {
                output += "<br>";
            }
        }

        // Find largest weakly connected component
        int max = 0;

        for (int index = 1; index <= this.components_found; index++) {
            if (max < size[index]) {
                max = size[index];
            }
        }

        output += "Largest WCC: " + max;
        return output;
    }

    /**
     * Breadth-First Search algorithm
     * 
     * @param start Index at which to begin search
     */
    private void bfs(int start) {
        int currentVertex;
        int nextVertex;
        Edgenode edgenode;
        queue.offer(start);
        found[start] = true;

        while (!queue.isEmpty()) {
            currentVertex = queue.poll();
            wcc[currentVertex] = components_found;
            processed[currentVertex] = true;
            edgenode = graph.edges[currentVertex];

            while (edgenode != null) {
                nextVertex = edgenode.y;

                if (found[nextVertex] == false) {
                    queue.offer(nextVertex);
                    found[nextVertex] = true;
                    parent[nextVertex] = currentVertex;
                }

                edgenode = edgenode.next;
            }
        }
    }

}
