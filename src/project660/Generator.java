package project660;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Random;

public class Generator implements Runnable {
    final public static boolean DIRECTED = true;

    public Pair pair;
    public String name;
    public String path;

    /**
     * Build the graph
     * 
     * @param pair
     * @return
     */
    private static Graph getGraph(Pair pair) {
        Graph g = new Graph(pair.getN(), pair.getP(), DIRECTED);
        Random rand = new Random();

        g.insert_vertex(pair.getN());

        for (int i = 1; i <= pair.getN(); i++) {
            for (int j = 1; j <= pair.getN(); j++) {
                if (i == j) {
                    continue;
                }

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
    public void saveGraph(Graph g) throws IOException {
        FileOutputStream fout = new FileOutputStream(path + Config.getInstance().dataPath + name);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(g);
        oos.close();

        FileInfo fi = new FileInfo();
        fi.setDate(new Date());
        fi.setFilename(name);
        fi.setFilepath(path + Config.getInstance().dataPath);
        fi.setLocked(false);
        fi.setReport(g.print_graph());

        Interactor i = new Interactor();
        i.updateFileList(path, fi);
    }

    /**
     * Lock graph
     * 
     * @param g
     * @throws IOException
     */
    public void lockGraph(Graph g) throws IOException {
        FileInfo fi = new FileInfo();
        fi.setDate(new Date());
        fi.setFilename(name);
        fi.setFilepath(path + Config.getInstance().dataPath);
        fi.setLocked(true);
        fi.setReport(g.print_graph());

        Interactor i = new Interactor();
        i.updateFileList(path, fi);
    }

    @Override
    public void run() {
        FileInfo fi = new FileInfo();
        fi.setDate(new Date());
        fi.setFilename(name);
        fi.setFilepath(path + Config.getInstance().dataPath);
        fi.setLocked(true);

        Interactor i = new Interactor();
        try {
            i.addToFileList(path, fi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Graph g = getGraph(pair);
        try {
            saveGraph(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
