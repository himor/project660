package project660;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Interactor 
{
    public static String getString(String message) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        System.out.print(message);
        return in.readLine();
    }
    
    public static int getInt(String message) throws IOException
    {
        int n = -1;
        
        while (n < 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            System.out.print(message);
            try {
                n = Integer.parseInt(in.readLine());
            } catch (NumberFormatException e) {
                System.out.print("\n   Please type a numeric value!\n");
            }
        }
        
        return n;
    }
    
    public static double getDouble(String message) throws IOException
    {
        double n = -1;
        
        while (n < 0) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            System.out.print(message);
            try {
                n = Double.parseDouble(in.readLine());
            } catch (NumberFormatException e) {
                System.out.print("\n   Please type a numeric value!\n");
            }
        }
        
        return n;
    }
    
    /**
     * Load file list from the directory
     *  
     * @param String path Directory to parse
     * 
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getFiles(String path)
    {
        HashMap<String, String> m = new HashMap<String, String>();
        
        File folder = new File(path + "/data/");
        File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
              if (listOfFiles[i].isFile()) {
                m.put(listOfFiles[i].getName(), listOfFiles[i].getAbsolutePath());
              }
            }
        
        return m;
    }
    
    /**
     * Load file list from filelist file
     * 
     * @return HashMap<String, FileInfo>
     * @throws IOException 
     */
    public HashMap<String, FileInfo> getFileList(String root) throws IOException
    {
        HashMap<String, FileInfo> m = new HashMap<String, FileInfo>();
                
        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream(root + "filelist.txt");
            objectinputstream = new ObjectInputStream(streamIn);
            FileInfo readCase = null;
            do{
                readCase = (FileInfo) objectinputstream.readObject();
                if(readCase != null){
                    m.put(readCase.getFilename(), readCase);
                }
            }
            while (readCase != null);
            
        } catch (Exception e) {
            
        } finally {
            if(objectinputstream != null){
                objectinputstream .close();
            } 
        }
        return m;    	
    }
    
    /**
     * Add another graph info to filelist
     * 
     * @param FileInfo fi FileInfo
     * 
     * @throws IOException
     */
    public void addToFileList(String root, FileInfo fi) throws IOException
    {
        HashMap<String, FileInfo> m = getFileList(root);
        
        FileOutputStream fout  = new FileOutputStream(root + "filelist.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        
        for (Map.Entry<String, FileInfo> entry : m.entrySet()) {
            oos.writeObject(entry.getValue());            
        }
        
        oos.writeObject(fi);
        oos.close();
    }
    
    /**
     * Delete graph info from filelist
     * 
     * @param FileInfo fi FileInfo
     * 
     * @throws IOException
     */
    public void removeFromFileList(String root, FileInfo fi) throws IOException
    {
        HashMap<String, FileInfo> m = getFileList(root);
        FileOutputStream fout  = new FileOutputStream(root + "filelist.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        
        for (Map.Entry<String, FileInfo> entry : m.entrySet()) {
            if (!entry.getKey().equals(fi.getFilename()))
                oos.writeObject(entry.getValue());            
        }

        oos.close();
    }
    
    /**
     * Update graph info from filelist
     * 
     * @param FileInfo fi FileInfo
     * 
     * @throws IOException
     */
    public void updateFileList(String root, FileInfo fi) throws IOException
    {
        HashMap<String, FileInfo> m = getFileList(root);
        FileOutputStream fout  = new FileOutputStream(root + "filelist.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        
        for (Map.Entry<String, FileInfo> entry : m.entrySet()) {
            if (!entry.getKey().equals(fi.getFilename()))
                oos.writeObject(entry.getValue());
            else
                oos.writeObject(fi);
        }

        oos.close();
    }
    
    /**
     * Graph loader
     * 
     * @param String filename File name
     * 
     * @return Graph
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Graph loadGraph(String filename) throws IOException, ClassNotFoundException
    {
    	/**
         * Load graph object from the file
         */
        ObjectInputStream ois = null;
        Graph g = null;
        FileInputStream fin;
        
        try {
            fin = new FileInputStream(filename);
            ois = new ObjectInputStream(fin);   
            g   = (Graph) ois.readObject();
        } catch (FileNotFoundException e) {
            
        } finally {
            if (ois != null)
            	ois.close();
        }
        
        return g;
    }
    
    
    
}
